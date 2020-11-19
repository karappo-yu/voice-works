package com.yu.voiceworks.web.controller;

import com.yu.voiceworks.entity.po.WorkInfo;
import com.yu.voiceworks.entity.vo.PageForm;
import com.yu.voiceworks.entity.vo.PageVo;
import com.yu.voiceworks.entity.vo.ViewResult;
import com.yu.voiceworks.entity.vo.VoiceWorkVo;
import com.yu.voiceworks.filesystem.FileService;
import com.yu.voiceworks.service.imp.WorkServiceImp;
import com.yu.voiceworks.utils.RegexUtil;
import com.yu.voiceworks.utils.ResultVoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;


@RestController
@RequestMapping("/work")
@Validated
public class WorkController {


    private final WorkServiceImp workServiceImp;

    private final FileService fileService;

    @GetMapping("/{rjCode}")
    public ViewResult<VoiceWorkVo> getWortById(@PathVariable("rjCode")
                                               @Pattern(regexp = RegexUtil.RJ_CODE_PATTERN,message = "RJ号格式为6位数字") String rjCode){

        VoiceWorkVo res = workServiceImp.getVoiceWorkVo(rjCode);

        return ResultVoUtil.ok(res);
    }

    @DeleteMapping("/{rjCode}")
    @Transactional
    public ViewResult<String> deleteById(
            @PathVariable("rjCode")
            @Pattern(regexp = RegexUtil.RJ_CODE_PATTERN,message = "RJ号格式为6位数字") String rjCode
    ){
        fileService.deleteDirById(rjCode);
        workServiceImp.deleteRecordById(rjCode);
        return ResultVoUtil.ok("删除成功");
    }

    /**
     * 只返回基本信息
     * @param search
     * @param pageForm
     * @return
     */
    @GetMapping("/list")
    public ViewResult<PageVo<WorkInfo>> getAll(String search,
                                             PageForm pageForm
    ){

        PageVo<WorkInfo> res = workServiceImp.getWorksByCondition(search, pageForm);

        return ResultVoUtil.ok(res);
    }
    @Autowired
    public WorkController(WorkServiceImp workServiceImp,FileService fileService) {
        this.workServiceImp = workServiceImp;
        this.fileService = fileService;
    }
}

