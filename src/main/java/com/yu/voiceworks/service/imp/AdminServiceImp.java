package com.yu.voiceworks.service.imp;

import com.yu.voiceworks.crawler.DsLite;
import com.yu.voiceworks.entity.VoiceWork;
import com.yu.voiceworks.entity.VoiceWorkDynamic;
import com.yu.voiceworks.entity.po.*;
import com.yu.voiceworks.filesystem.FileScanner;
import com.yu.voiceworks.repository.*;
import com.yu.voiceworks.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminServiceImp implements AdminService {

    private WorkDirRepository workDirRepository;


    private WorkInfoRepository workInfoRepository;


    private CircleRepository circleRepository;

    private SeriesRepository seriesRepository;

    private TagRepository tagRepository;

    private CvRepository cvRepository;

    private WorkCvRepository workCvRepository;

    private RateCountDetailRepository rateCountDetailRepository;

    private RankRepository rankRepository;

    private WorkDynRepository workDynRepository;

    private WorkTagRepository workTagRepository;

    private WorkServiceImp workServiceImp;

    private DsLite dsLite;

    private FileScanner fileScanner;


    /**
     *
     */
    @Override
    @Transactional
    public void updateDataBase() {
        List<WorkDir> updateList = new LinkedList<>();

        List<WorkDir> oldList = new LinkedList<>();
        //扫描本地目录
        Set<WorkDir> workDirs = fileScanner.scanBasePath();
        //对比数据库记录
        check(updateList, oldList, workDirs);
        //爬取音声作品静态数据
        if (updateList.size()>0) {
            log.info("[开始爬取音声静态数据]...................");
            List<VoiceWork> voiceWorkList = updateList.stream().map(WorkDir::getWorkId)
                    .map(dsLite::getMetaData)
                    .collect(Collectors.toList());
            //爬取音声作品动态数据
            log.info("[开始爬取音声动态数据]...................");
            List<VoiceWorkDynamic> voiceWorkDynamicList = updateList.stream().map(WorkDir::getWorkId)
                    .map(dsLite::getDynamicData).collect(Collectors.toList());
            log.info("[开始插入数据到数据库]...................");
            //插入voiceWork数据
            saveVoiceWorks(voiceWorkList);
            //插入voiceWorkDynamic
            saveWorkDynamics(voiceWorkDynamicList);
            //插入updateList到数据库
            workDirRepository.saveAll(updateList);
            //下载音声图片到本地
            log.info("[开始下载音声图片到本地]...................");
            downloadImages(updateList);
        }else {
            log.info("[没有需要更新的数据]...................");
        }
    }
    //删除数据库中有记录，本地却没有文件夹的记录
    @Transactional
    public int cleanDatabaseData(){
        List<String> toDelIds = new LinkedList<>();
        //查找workDir，查看路径是否还存在
        List<WorkDir> all = workDirRepository.findAll();
        all.forEach(f->{
            if (!isPathDirExist(f.getDirPath())){
                String workId = f.getWorkId();
                String path = f.getDirPath();
                log.info("[RJ{}]本地不存在，加入删除列表，路径：{}",workId,path);
                toDelIds.add(workId);
            }
        });
        toDelIds.forEach(workServiceImp::deleteRecordById);
        log.info("[已经删除数据库中本地已经不存在的{}条记录]",toDelIds.size());
        return toDelIds.size();
        // 删除图片，暂时不清空图片
    }

    public boolean isPathDirExist(String path) {
        File file = new File(path);
        if (file.exists()){
            if (file.isFile()){
                return false;
            }
            return true;
        }
        return false;
    }
    private void downloadImages(List<WorkDir> updateList) {
        updateList.stream().map(WorkDir::getWorkId).forEach(dsLite::getImage);
    }

    @Transactional
    public void saveWorkDynamics(List<VoiceWorkDynamic> voiceWorkDynamicList) {
        voiceWorkDynamicList.forEach(this::saveWorkDynamic);
    }

    @Transactional
    public void saveWorkDynamic(VoiceWorkDynamic voiceWorkDynamic) {
        //准备插入的数据
        WorkDyn workDyn = voiceWorkDynamic.toWorkDyn();

        List<RateCountDetail> countDetailList = voiceWorkDynamic.getRateCountDetail();
        countDetailList.forEach(i->i.setWorkId(workDyn.getWorkId())); //为每个都添加workId

        List<Rank> rankList = voiceWorkDynamic.getRank().stream().map(i -> {
            i.setWorkId(workDyn.getWorkId());                   //为每个都添加workID
            return i;
        })
                .collect(Collectors.toList());

        //插入数据
        workDynRepository.save(workDyn);
        rateCountDetailRepository.saveAll(countDetailList);
        rankRepository.saveAll(rankList);
    }

    @Transactional
    public void  saveVoiceWork(VoiceWork one){
        //准备插入的数据
        WorkInfo workInfo = one.toWorkInfo();
        Circle circle = one.getCircle();
        Series series = one.getSeries();
        Set<Tag> tags = one.getTags();
        List<WorkTag> workTagList = tags.stream().map(i->new WorkTag(i.getTagId(),one.getId()))
                .collect(Collectors.toList());
        Set<Cv> cvs = one.getCvs();
        List<WorkCv> workCvList = cvs.stream().map(i->new WorkCv(i.getCvId(),one.getId()))
                .collect(Collectors.toList());

        //存入数据库
        workInfoRepository.save(workInfo);
        circleRepository.save(circle);
        if (null!=series){
            seriesRepository.save(series);
        }
        tagRepository.saveAll(tags);
        workTagRepository.saveAll(workTagList);
        cvRepository.saveAll(cvs);
        workCvRepository.saveAll(workCvList);

    }

    @Transactional
    public void saveVoiceWorks(List<VoiceWork> voiceWorkList) {
        voiceWorkList.forEach(this::saveVoiceWork);
    }



    private void check(List<WorkDir> updateList, List<WorkDir> oldList, Set<WorkDir> workDirs) {
        log.info("[开始对比数据库记录]............");
        int cleanCount = cleanDatabaseData();//清空本地不存在的记录
        int upCount = 0;
        for (WorkDir workDir : workDirs) {
            if (!workDirRepository.existsById(workDir.getWorkId())) {
                log.info("新加入[RJ{}]，路径：{}",workDir.getWorkId(),workDir.getDirPath());    //通过查找数据库中是否已存在此记录，不存在加入更新列表
                updateList.add(workDir);
            }else {
                log.info("已经存在[RJ{}]，路径：{}",workDir.getWorkId(),workDir.getDirPath()); //如果已经存在，则查出path对比,如果path不一致，也加入更新列表
                String dirPath = workDirRepository.findById(workDir.getWorkId()).get().getDirPath();
                if (!StringUtils.equals(dirPath,workDir.getDirPath())){
                    upCount++;
                    log.info("已经存在[RJ{}]，原路径{},路径更新为：{}",workDir.getWorkId(),dirPath,workDir.getDirPath());
                    workDirRepository.save(workDir);
                }
                oldList.add(workDir);
            }
        }
        log.info("[对比数据库记录完毕]，新加入列表{}个，原有列表{}个中更新{}个的路径,删除数据库中{}条记录", updateList.size(), oldList.size()+cleanCount,upCount,cleanCount);
    }
    @Autowired
    public AdminServiceImp(WorkDirRepository workDirRepository,
                           WorkInfoRepository workInfoRepository,
                           CircleRepository circleRepository,
                           SeriesRepository seriesRepository,
                           TagRepository tagRepository,
                           CvRepository cvRepository,
                           WorkCvRepository workCvRepository,
                           RateCountDetailRepository rateCountDetailRepository,
                           RankRepository rankRepository,
                           WorkDynRepository workDynRepository,
                           WorkTagRepository workTagRepository,
                           WorkServiceImp workServiceImp,
                           DsLite dsLite,
                           FileScanner fileScanner
                           ) {
        this.workDirRepository = workDirRepository;
        this.workInfoRepository = workInfoRepository;
        this.circleRepository = circleRepository;
        this.seriesRepository = seriesRepository;
        this.tagRepository = tagRepository;
        this.cvRepository = cvRepository;
        this.workCvRepository = workCvRepository;
        this.rateCountDetailRepository = rateCountDetailRepository;
        this.rankRepository = rankRepository;
        this.workDynRepository = workDynRepository;
        this.workTagRepository = workTagRepository;
        this.workServiceImp = workServiceImp;
        this.dsLite = dsLite;
        this.fileScanner = fileScanner;
    }
}
