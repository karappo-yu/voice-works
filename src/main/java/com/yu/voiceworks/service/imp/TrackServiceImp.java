package com.yu.voiceworks.service.imp;

import com.yu.voiceworks.enums.FileType;
import com.yu.voiceworks.entity.po.WorkDir;
import com.yu.voiceworks.entity.vo.TrackFile;
import com.yu.voiceworks.enums.SSYKExceptionEnum;
import com.yu.voiceworks.exception.SSYKException;
import com.yu.voiceworks.repository.WorkDirRepository;
import com.yu.voiceworks.utils.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TrackServiceImp {

    @Autowired
    private WorkDirRepository workDirRepository;

    public List<TrackFile> getTrackFilesByRjCode(String rjCode) {
        Optional<WorkDir> workDirOptional = workDirRepository.findById(rjCode);
        if (workDirOptional.isPresent()) {
            String dirPath = workDirOptional.get().getDirPath();
            return getTrackFilesByPath(dirPath);
        }
        throw new SSYKException(SSYKExceptionEnum.NO_SUCH_WORK);
    }

    public List<TrackFile> getTrackFilesByPath(String dirPath) {
        File[] files = new File(dirPath).listFiles();
        if (null==files||files.length==0){
            throw new SSYKException(SSYKExceptionEnum.DIR_IS_EMPTY);
        }
        List<TrackFile> trackFileList = new LinkedList<>();
        Arrays.stream(files).forEach(
                f-> {
                    TrackFile trackFile = getTrackFile(f);
                    if (null!=trackFile){
                        trackFileList.add(trackFile);
                    }
                }
        );
        if (trackFileList.size()>0){
            return trackFileList;
        }
        return null;
    }

    public TrackFile getTrackFile(File file) {
        if (!file.exists()) {
            return null;
        };
        String fileName = file.getName();
        TrackFile trackFile = null;
        if (file.isFile()) {
            if (RegexUtil.isMusicFile(fileName)) {
                trackFile = new TrackFile();
                trackFile.setTitle(fileName);
                trackFile.setType(FileType.FILE.name());
                trackFile.setHash(String.valueOf(file.hashCode()));
                trackFile.setChildren(null);
            }
            //TODO 不是支持格式的文件先无视
        } else if (file.isDirectory()) {
            File[] array = file.listFiles();
            if (array==null||array.length<=0){
                return null;
            }//如果是空文件夹，直接返回空
            trackFile = new TrackFile();
            trackFile.setTitle(fileName);
            trackFile.setType(FileType.FOLDER.name());
            trackFile.setHash(String.valueOf(file.hashCode()));
            List<TrackFile> children = new LinkedList<>();
            Arrays.stream(array).forEach(
                    f->{
                        TrackFile trackFile1 = getTrackFile(f);
                        if (null!=trackFile1){
                            children.add(trackFile1);
                        }
                    }
            );
            if (children.size()>0){
                trackFile.setChildren(children);
            }
        }
        return trackFile;
    }
}
