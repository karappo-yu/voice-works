package com.yu.voiceworks.web.controller;

import com.yu.voiceworks.entity.vo.ViewResult;
import com.yu.voiceworks.service.AdminService;
import com.yu.voiceworks.utils.ResultVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("update")
    public ViewResult<String> update(){
        adminService.updateDataBase();

        return ResultVoUtil.ok("更新成功");
    }



}
