package com.yu.voiceworks.web.controller;

import com.yu.voiceworks.crawler.DsLite;
import com.yu.voiceworks.entity.VoiceWork;
import com.yu.voiceworks.entity.VoiceWorkDir;
import com.yu.voiceworks.filesystem.FileScanner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("list")
    public List<VoiceWork> list(){
        List<VoiceWork> voiceWorks = new LinkedList<>();
        Set<VoiceWorkDir> voiceWorkDirs = FileScanner.scanBasePath();
        for (VoiceWorkDir voiceWorkDir : voiceWorkDirs) {
            VoiceWork voiceWork = DsLite.getStaticMetaData(voiceWorkDir.getId());
            voiceWorks.add(voiceWork);
        }
        return voiceWorks;
    }
}
