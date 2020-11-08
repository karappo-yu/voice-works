package com.yu.voiceworks.web.controller;

import com.yu.voiceworks.entity.vo.TrackFile;
import com.yu.voiceworks.service.imp.TrackServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("track")
public class TrackController {

    private TrackServiceImp trackServiceImp;



    @RequestMapping("/{rjCode}")
    public List<TrackFile> getAllFiles(@PathVariable("rjCode") String rjCode){
        //TODO 检查rjCode是否合法
        List<TrackFile> trackFileList = trackServiceImp.getTrackFilesByRjCode(rjCode);

        return  trackFileList;
    }

    @Autowired
    public TrackController(TrackServiceImp trackServiceImp) {
        this.trackServiceImp = trackServiceImp;
    }
}
