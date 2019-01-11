package cn.itrip.auth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.itrip.auth.service.TokenService;
import cn.itrip.auth.service.UserService;
import cn.itrip.beans.dto.Dto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户登录控制器
 * @author hduser
 *
 */
@Controller
@RequestMapping(value = "/api")
public class LoginController {
/*
	@Resource
	private UserService userService;

	@Resource
	private TokenService tokenService;

	@RequestMapping(value="/dologin",method=RequestMethod.POST,produces= "application/json")
	public @ResponseBody
	Dto dologin(
			@RequestParam
			String name,
			@RequestParam
			String password,
			HttpServletRequest request) {
		return null;
	}


	@RequestMapping(value="/logout",method=RequestMethod.GET,produces="application/json",headers="token")
	public @ResponseBody Dto logout(HttpServletRequest request){
		return null;
	}*/
}
