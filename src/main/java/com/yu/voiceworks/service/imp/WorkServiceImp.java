package com.yu.voiceworks.service.imp;

import com.yu.voiceworks.entity.po.*;
import com.yu.voiceworks.entity.vo.PageForm;
import com.yu.voiceworks.entity.vo.PageVo;
import com.yu.voiceworks.entity.vo.VoiceWorkVo;
import com.yu.voiceworks.enums.SSYKExceptionEnum;
import com.yu.voiceworks.exception.SSYKException;
import com.yu.voiceworks.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WorkServiceImp {

    private final WorkDirRepository workDirRepository;

    private final WorkInfoRepository workInfoRepository;

    private final WorkCvRepository workCvRepository;

    private final WorkDynRepository workDynRepository;

    private final WorkTagRepository workTagRepository;

    private final CircleRepository circleRepository;

    private final TagRepository tagRepository;

    private final CvRepository cvRepository;

    private final RateCountDetailRepository rateCountDetailRepository;

    private final RankRepository rankRepository;

    @Transactional
    public void deleteRecordById(String id) {
        //删除WorkDir
        if (workDirRepository.existsById(id)) {
            workDirRepository.deleteById(id);
        }
        //workInfo
        if (workInfoRepository.existsById(id)){
            workInfoRepository.deleteById(id);
        }
        //workDyn
        if (workDynRepository.existsById(id)){
            workDynRepository.deleteById(id);
        }
        //workTag
        workCvRepository.deleteByWorkId(id);
        workTagRepository.deleteByWorkId(id);

        rateCountDetailRepository.deleteByWorkId(id);
        rankRepository.deleteByWorkId(id);
        log.info("删除数据库记录:  RJ"+id);
    }
    public VoiceWorkVo getVoiceWorkVo(String rjCode) {
        //查询基本信息
        Optional<WorkInfo> workInfoOptional = workInfoRepository.findById(rjCode);
        if (!workInfoOptional.isPresent()){
            throw new SSYKException(SSYKExceptionEnum.NO_SUCH_WORK);
        }
        WorkInfo workInfo = workInfoOptional.get();

        //查询动态信息数据
        WorkDyn workDyn = null;
        Optional<WorkDyn> workDynOptional = workDynRepository.findById(rjCode);
        if (workDynOptional.isPresent()){
            workDyn = workDynOptional.get();
        }


        //查询社团
        Circle circle = null;
        Optional<Circle> circleOptional = circleRepository.findById(workInfo.getCircleId());
        if (circleOptional.isPresent()){
            circle = circleOptional.get();
        }


        //查询rateCountDetail
        List<RateCountDetail> rateCountDetails =  rateCountDetailRepository.findByWorkId(rjCode);

        //查询rank
        List<Rank> ranks = rankRepository.findByWorkId(rjCode);

        //查询tags
        List<Tag> tags = findTagsByWorkId(rjCode);

        //查询cv
        List<Cv> cvs = finCvsByWorkId(rjCode);

        return buildVoiceWorkVo(workInfo, workDyn, circle, ranks, tags, cvs,rateCountDetails);
    }

    public VoiceWorkVo buildVoiceWorkVo(WorkInfo workInfo,
                                        WorkDyn workDyn,
                                        Circle circle,
                                        List<Rank> ranks,
                                        List<Tag> tags,
                                        List<Cv> cvs,
                                        List<RateCountDetail> rateCountDetails) {
        return VoiceWorkVo.builder()
                .id(workInfo.getWorkId())
                .title(workInfo.getWorkTitle())
                .circle(circle)
                .nsfw(true) //TODO
                .release(workInfo.getWorkRelease())
                .dlCount(workDyn.getDlCount())
                .price((double) workDyn.getPrice())
                .reviewCount(workDyn.getReviewCount())
                .rateCount(workDyn.getRateCount())
                .rateCountDetail(rateCountDetails)
                .rateAverage2dp(workDyn.getRateAverage2Dp())
                .rank(ranks)
                .tags(tags)
                .vas(cvs).build();
    }

    public List<Cv> finCvsByWorkId(String rjCode) {
        List<WorkCv> workCvs = workCvRepository.findByWorkId(rjCode);
        List<String> cvIds = workCvs.stream().map(WorkCv::getCvId).collect(Collectors.toList());
        return cvRepository.findAllById(cvIds);
    }

    public List<Tag> findTagsByWorkId(String rjCode) {
        List<WorkTag> workTags = workTagRepository.findByWorkId(rjCode);
        List<String> tagIds = workTags.stream().map(WorkTag::getTagId).collect(Collectors.toList());
        return tagRepository.findAllById(tagIds);
    }

    @Autowired
    public WorkServiceImp(WorkDirRepository workDirRepository,
                          WorkInfoRepository workInfoRepository,
                          WorkCvRepository workCvRepository,
                          WorkDynRepository workDynRepository,
                          WorkTagRepository workTagRepository,
                          CircleRepository circleRepository,
                          TagRepository tagRepository,
                          CvRepository cvRepository,
                          RateCountDetailRepository rateCountDetailRepository,
                          RankRepository rankRepository) {
        this.workDirRepository = workDirRepository;
        this.workInfoRepository = workInfoRepository;
        this.workCvRepository = workCvRepository;
        this.workDynRepository = workDynRepository;
        this.workTagRepository = workTagRepository;
        this.circleRepository = circleRepository;
        this.tagRepository = tagRepository;
        this.cvRepository = cvRepository;
        this.rateCountDetailRepository = rateCountDetailRepository;
        this.rankRepository = rankRepository;
    }

    /**
     * @param search
     * @param pageForm
     * @return
     */
    public PageVo<WorkInfo> getWorksByCondition(String search,
                                      PageForm pageForm) {
        if (StringUtils.isBlank(pageForm.getSort())){
            pageForm.setSort("workRelease");
        }
        if (StringUtils.isBlank(pageForm.getOrder())){
            pageForm.setOrder(Sort.Direction.DESC.name());
        }
        Page<WorkInfo> all = workInfoRepository.findAll(pageForm.toPageable());
        PageVo<WorkInfo> page = new PageVo<WorkInfo>();
        page.setTotal(all.getTotalElements());
        page.setPageSize(all.getSize());
        page.setPage(all.getNumber()+1);
        page.setContent(all.getContent());
        return page;
    }
}
