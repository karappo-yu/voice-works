package com.yu.voiceworks.service.imp;

import com.yu.voiceworks.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminServiceImpTest {
    @Autowired
    AdminServiceImp adminServiceImp;
    @Test
    public void updateDataBase() {
        adminServiceImp.updateDataBase();
    }}