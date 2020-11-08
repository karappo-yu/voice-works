package com.yu.voiceworks.service.imp;

import com.yu.voiceworks.entity.vo.TrackFile;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TrackServiceImpTest {

    @Test
    public void getTrackFilesByPath() {
        List<TrackFile> trackFiles = new TrackServiceImp().getTrackFilesByPath("F:\\voice\\RJ302885");
        System.out.println("trackFiles = " + trackFiles);
    }
}