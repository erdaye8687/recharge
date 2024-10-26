package com.dapang.recharge.controller;

import com.dapang.recharge.common.pojo.Result;
import com.dapang.recharge.pojo.vo.AuthLoginReqVO;
import com.dapang.recharge.pojo.vo.AuthLoginRespVO;
import com.dapang.recharge.service.AdminAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.dapang.recharge.common.pojo.Result.success;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 09:14:25
 */
@RestController
@RequestMapping("/recharge/user")
@Validated
@Slf4j
public class UserController {

    @Autowired
    private AdminAuthService adminAuthService;

    @PostMapping("/login")
    public Result<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return success(adminAuthService.login(reqVO.getUsername(), reqVO.getPassword()));
    }
}
