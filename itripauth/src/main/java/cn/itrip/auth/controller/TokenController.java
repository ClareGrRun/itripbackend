package cn.itrip.auth.controller;

import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.itrip.auth.service.TokenService;
import cn.itrip.beans.dto.Dto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Token控制器
 * @author hduser
 *
 */
@Controller
@RequestMapping(value = "/api")
public class TokenController {

	@Resource
	private TokenService tokenService;
	/**
	 * 置换token
	 * 
	 * @return 新的token信息
	 */
	
	@RequestMapping(value = "/retoken", method = RequestMethod.POST,produces= "application/json")
	public @ResponseBody
	Dto replace(HttpServletRequest request) {
		/*
		 * 请求格式 
		 * $.ajax({
				headers:{
				          Accept:"application/json;charset=utf-8",
				          Content-Type:"application/json;charset=utf-8",
				          token:"lkfajsdlkfjawoier29"// 从cookie中获取
				},
				type："post",
				.....
				})
		 * 
		 */
		return null;
	}
}
