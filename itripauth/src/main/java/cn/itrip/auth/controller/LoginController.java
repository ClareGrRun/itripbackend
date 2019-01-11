package cn.itrip.auth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.itrip.auth.service.TokenService;
import cn.itrip.auth.service.UserService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.ItripTokenVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.ErrorCode;
import cn.itrip.common.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.util.Password;

import java.util.Calendar;

/**
 * 用户登录控制器
 * @author hduser
 *
 */
@Api
@Controller
@RequestMapping(value = "/api")
public class LoginController {

	@Resource
	private UserService userService;
	@Resource
	private TokenService tokenService;

	@ApiOperation(value = "登录",notes = "根据用户名、密码进行统一认证",httpMethod = "POST",response = ItripUser.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "form")
	})
	@RequestMapping(value="/dologin",method=RequestMethod.POST,produces= "application/json")
	public @ResponseBody
	Dto dologin(
			@RequestParam
			String name,
			@RequestParam
			String password,
			HttpServletRequest request) {
	    try {
            ItripUser user = userService.login(name, MD5.getMd5(password,32));
            if(EmptyUtils.isNotEmpty(user)){
                String userAgent = request.getHeader("user-agent");
                String token = tokenService.generateToken(userAgent,user);
                tokenService.save(token,user);
                ItripTokenVO vo = new ItripTokenVO(token,Calendar.getInstance().getTimeInMillis()+2*60*60*1000,Calendar.getInstance().getTimeInMillis());
                return DtoUtil.returnDataSuccess(vo);
            }else{
                return DtoUtil.returnFail("用户名或密码错误!", ErrorCode.AUTH_AUTHENTICATION_FAILED);
            }
        }catch (Exception e){
	        e.printStackTrace();
	        return  DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_AUTHENTICATION_FAILED);
        }
	}

	@ApiOperation(value = "注销",notes = "客户端需在header中发送token",httpMethod = "GET")
	@ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String", paramType = "header")
	@RequestMapping(value="/logout",method=RequestMethod.GET,produces="application/json",headers="token")
	public @ResponseBody Dto logout(HttpServletRequest request){
		String token = request.getHeader("token");
			try {
				if(tokenService.validate(request.getHeader("user-Agent"),token)){
					tokenService.delete(token);
					return DtoUtil.returnSuccess();
				}else{
					return DtoUtil.returnFail("token无效",ErrorCode.AUTH_TOKEN_INVALID);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return DtoUtil.returnFail("退出失败",ErrorCode.AUTH_TOKEN_INVALID);
			}
	}
}
