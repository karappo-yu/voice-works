package com.yu.voiceworks.filesystem;

import com.yu.voiceworks.entity.po.WorkDir;
import com.yu.voiceworks.repository.WorkDirRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
@Slf4j
public class FileService {

    private WorkDirRepository workDirRepository;

    @Autowired
    public void setWorkDirRepository(WorkDirRepository workDirRepository) {
        this.workDirRepository = workDirRepository;
    }

    public void deleteDirById(String rjCode){
        Optional<WorkDir> byId = workDirRepository.findById(rjCode);
        if (byId.isPresent()){
            WorkDir workDir = byId.get();

            String path = workDir.getDirPath();

            File file = new File(path);
            if (file.exists()&&file.isDirectory()){
                delAllFile(file);
            }
        }

    }
    /**
     * 删除文件或文件夹
     * @param directory
     */
    public  void delAllFile(File directory){
        if (!directory.isDirectory()){
            directory.delete();
        } else{
            File [] files = directory.listFiles();

            // 空文件夹
            if (files.length == 0){
                directory.delete();
                log.info("删除" + directory.getAbsolutePath());
                return;
            }

            // 删除子文件夹和子文件
            for (File file : files){
                if (file.isDirectory()){
                    delAllFile(file);
                } else {
                    file.delete();
                    log.info("删除" + file.getAbsolutePath());
                }
            }

            // 删除文件夹本身
            directory.delete();
            log.info("删除" + directory.getAbsolutePath());
        }
    }
}
