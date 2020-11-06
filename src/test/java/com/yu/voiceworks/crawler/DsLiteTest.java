package com.yu.voiceworks.crawler;

import com.yu.voiceworks.entity.VoiceWork;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DsLiteTest {

    @Test
    void getMetaMassage() {
        VoiceWork voiceWork = DsLite.getStaticMetaData("" + 303067);
        System.out.println(voiceWork);
        Assertions.assertNotEquals(0,voiceWork.getTitle().length());
    }
}