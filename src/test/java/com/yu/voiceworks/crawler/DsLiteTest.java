package com.yu.voiceworks.crawler;

import com.yu.voiceworks.entity.VoiceWork;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DsLiteTest {
    private static String id = "305514";

    @Test
    void getMetaMassage() {
        VoiceWork voiceWork = DsLite.getStaticMetaData(id);
        System.out.println(voiceWork);
        Assertions.assertNotEquals(0,voiceWork.getTitle().length());
    }

    @Test
    void getDynamicData() {
        DsLite.getDynamicData(id);

    }

    @Test
    void getImage() {
        DsLite.getImage(id);
    }
}