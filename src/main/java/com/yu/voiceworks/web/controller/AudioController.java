package com.yu.voiceworks.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/stream")
@Controller
public class AudioController {


    @RequestMapping("/test")
    public String test(){
        System.out.println("ddd");
        return "forward:/mp3/%234-A.ちかと３Ｐ.mp3";
    }
}
