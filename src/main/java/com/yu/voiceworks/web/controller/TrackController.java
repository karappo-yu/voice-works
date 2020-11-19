package com.yu.voiceworks.web.controller;

import com.yu.voiceworks.entity.vo.TrackFile;
import com.yu.voiceworks.service.imp.TrackServiceImp;
import com.yu.voiceworks.utils.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("track")
@Validated
public class TrackController {

    private TrackServiceImp trackServiceImp;



    @RequestMapping("/{rjCode}")
    public List<TrackFile> getAllFiles(@PathVariable("rjCode") @Pattern(regexp = RegexUtil.RJ_CODE_PATTERN) String rjCode){
        List<TrackFile> trackFileList = trackServiceImp.getTrackFilesByRjCode(rjCode);

        return  trackFileList;
    }

    @Autowired
    public TrackController(TrackServiceImp trackServiceImp) {
        this.trackServiceImp = trackServiceImp;
    }
}
