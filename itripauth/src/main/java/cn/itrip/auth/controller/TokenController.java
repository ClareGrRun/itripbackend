package cn.itrip.auth.controller;

import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.itrip.auth.service.TokenService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.ErrorCode;
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

	@RequestMapping(value = "/retoken", method = RequestMethod.POST,produces= "application/json",headers = "token")
	public @ResponseBody
	Dto replace(HttpServletRequest request) {
			try {
				boolean result = tokenService.validate(request.getHeader("user-agent"),request.getHeader("token"));
				if(result){
					return DtoUtil.returnSuccess("token有效");
				}else{
					return DtoUtil.returnSuccess("token无效");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return 	DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_UNKNOWN);
			}
	}
}
