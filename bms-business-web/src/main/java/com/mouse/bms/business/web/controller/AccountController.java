package com.mouse.bms.business.web.controller;

import com.mouse.bms.business.api.request.RegisterInfoRequest;
import com.mouse.bms.business.api.service.AccountService;
import com.mouse.bms.business.common.response.PlainResult;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * CopyRight(C),mouse
 *
 * @author : mouse
 * @fileName : AccountController
 * @date : 2019/4/6 10:33
 * @description :
 */
@RestController
@RequestMapping("/")
@Api
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private AccountService accountService;

    @ApiOperation(value = "登录接口")
    @PostMapping("/login")
    public PlainResult<Boolean> login(@RequestParam String username, @RequestParam String password,
                                      @RequestParam Short type) {
        LOGGER.info("AccountController | login, username:{}, password:{}, type:{}.", username, password, type);
        return accountService.login(username, password, type);
    }

    @ApiOperation(value = "注册接口")
    @PostMapping("/register")
    public PlainResult<Boolean> register(@RequestBody RegisterInfoRequest registerInfoRequest) {
        LOGGER.info("AccountController | register, registerInfoRequest:{}.", registerInfoRequest);
        return accountService.register(registerInfoRequest);
    }

    @ApiOperation(value = "不同用户看到不同接口")
    @GetMapping("/article")
    public String article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "You are already logged in";
        } else {
            return "You are guest";
        }
    }

    @ApiOperation(value = "需要认证接口")
    @GetMapping("/require_auth")
    @RequiresAuthentication
    public String requireAuth() {
        return "You are authenticated";
    }

    @ApiOperation(value = "需要admin角色接口")
    @GetMapping("/require_role")
    @RequiresRoles("system")
    public String requireRole() {
        return "You are visiting require_role";
    }

    @ApiOperation(value = "需要view和edit权限接口")
    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public String requirePermission() {
        return "You are visiting permission require edit,view";
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unauthorized() {
        return "Unauthorized";
    }

}
