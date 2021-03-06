package cn.itrip.auth.controller;

import java.util.regex.Pattern;

import javax.annotation.Resource;

import cn.itrip.auth.service.UserService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.userinfo.ItripUserVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.ErrorCode;
import cn.itrip.common.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;
import io.swagger.models.Scheme;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 用户管理控制器
 * @author hduser
 *
 */
@Api
@Controller
@RequestMapping(value = "/api")
public class UserController {
	@Resource
	private UserService userService;


	@RequestMapping("/register")
	public String showRegisterForm() {
		return "register";
	}
	
	/**
	 * 使用邮箱注册 
	 * @param userVO
	 * @return
	 */
	@ApiOperation(value = "使用邮箱注册",notes = "使用邮箱注册",httpMethod = "POST")
	@RequestMapping(value="/doregister",method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody
	Dto doRegister(@RequestBody ItripUserVO userVO){
		//1.验证邮箱
		if(!validEmail(userVO.getUserCode())){
			return DtoUtil.returnFail("请输入正确的邮箱!", ErrorCode.AUTH_ILLEGAL_USERCODE);
		}
		ItripUser user = new ItripUser();
		user.setUserCode(userVO.getUserCode());
		user.setUserName(userVO.getUserName());
		try {
		if(null!=userService.findUserByUserCode(user.getUserCode())){
			return DtoUtil.returnFail("用户名已存在",ErrorCode.AUTH_USER_ALREADY_EXISTS);
		}else{
				user.setUserPassword(MD5.getMd5(userVO.getUserPassword(),32));
				userService.itripxCreateUserByMail(user);
				return DtoUtil.returnSuccess();
			}
		}catch (Exception e){
			e.printStackTrace();
			return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
		}
	}
	
	/**
	 * 使用手机注册
	 * @param userVO
	 * @return
	 */
	@ApiOperation(value = "使用手机注册",notes = "使用手机注册",httpMethod = "POST")
	@RequestMapping(value="/registerbyphone",method=RequestMethod.POST,produces = "application/json")
	public @ResponseBody Dto registerByPhone(			
			@RequestBody ItripUserVO userVO){
		if(!validPhone(userVO.getUserCode())){
			return DtoUtil.returnFail("请输入正确的手机号码！",ErrorCode.AUTH_ILLEGAL_USERCODE);
		}
		ItripUser user  = new ItripUser();
		user.setUserCode(userVO.getUserCode());
		user.setUserName(userVO.getUserName());
		try {
			if(null!=userService.findUserByUserCode(user.getUserCode())){
				return DtoUtil.returnFail("用户名已存在",ErrorCode.AUTH_USER_ALREADY_EXISTS);
			}else{
				user.setUserPassword(MD5.getMd5(userVO.getUserPassword(),32));
				userService.itripxCreateUserByPhone(user);
				return DtoUtil.returnSuccess();
			}
		}catch (Exception e){
			e.printStackTrace();
			return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
		}
	}

	/**
	 * 检查用户是否已注册
	 * @param name
	 * @return
	 */
	@ApiOperation(value = "用户名验证",notes = "验证是否已存在改用户名",httpMethod = "GET")
	@ApiImplicitParam(name = "name",value = "被检查的用户",required = true,dataType = "string",paramType = "query")
	@RequestMapping(value="/ckusr",method=RequestMethod.GET,produces= "application/json")
	public @ResponseBody
	Dto checkUser(@RequestParam String name) {
		try {
			if(null!=userService.findUserByUserCode(name)){
				return DtoUtil.returnFail("用户名已存在!",ErrorCode.AUTH_USER_ALREADY_EXISTS);
			}else{
				return DtoUtil.returnSuccess("用户名可用!");
			}
		}catch (Exception e){
			e.printStackTrace();
			return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
		}
	}

	/**
	 * 邮箱激活
	 * @param user
	 * @param code
	 * @return
	 */
	@ApiOperation(value = "邮箱注册用户激活",notes = "邮箱激活",httpMethod = "PUT")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user",value = "注册邮箱地址",required = true,dataType = "string",paramType = "query"),
		@ApiImplicitParam(name = "code",value = "激活码",required = true,dataType = "string",paramType = "query")
	})
	@RequestMapping(value="/activate",method=RequestMethod.PUT,produces= "application/json")
	public @ResponseBody Dto activate(			
			@RequestParam String user,			
			@RequestParam String code){
		try {
			if(userService.activate(user,code)){
				return DtoUtil.returnSuccess("激活成功");
			}else{
				return DtoUtil.returnSuccess("激活失败");
			}
		}catch (Exception e){
			e.printStackTrace();
			return  DtoUtil.returnFail("激活失败",ErrorCode.AUTH_UNKNOWN);
		}
			
	}

	/**
	 * 手机激活
	 * @param user
	 * @param code
	 * @return
	 */
	@ApiOperation(value = "手机注册用户激活",notes = "邮箱激活",httpMethod = "PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "user",value = "注册手机",required = true,dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "code",value = "激活码",required = true,dataType = "string",paramType = "query")
	})
	@RequestMapping(value="/validatephone",method=RequestMethod.PUT,produces= "application/json")
	public @ResponseBody Dto validatePhone(			
			@RequestParam String user,			
			@RequestParam String code){
		try {
			if(userService.validatePhone(user,code)){
				return DtoUtil.returnSuccess("激活成功");
			}else{
				return DtoUtil.returnSuccess("激活失败");
			}
		}catch (Exception e){
			e.printStackTrace();
			return  DtoUtil.returnFail("激活失败",ErrorCode.AUTH_UNKNOWN);
		}
	} 
	
	
	/**			 *
	 * 合法E-mail地址：     
	 * 1. 必须包含一个并且只有一个符号“@”    
	 * 2. 第一个字符不得是“@”或者“.”
	 * 3. 不允许出现“@.”或者.@   
	 * 4. 结尾不得是字符“@”或者“.”    
	 * 5. 允许“@”前的字符中出现“＋” 
	 * 6. 不允许“＋”在最前面，或者“＋@” 
	 */
	private boolean validEmail(String email){
		
		String regex="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"  ;			
		return Pattern.compile(regex).matcher(email).find();			
	}
	/**
	 * 验证是否合法的手机号
	 * @param phone
	 * @return
	 */
	private boolean validPhone(String phone) {
		String regex="^1[3578]{1}\\d{9}$";
		return Pattern.compile(regex).matcher(phone).find();
	}
}
