package cn.itrip.auth.controller;

import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.itrip.auth.service.TokenService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.vo.ItripTokenVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Token控制器
 * @author hduser
 *
 */
@Api
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
	@ApiOperation(value = "客户端置换token",notes = "提供客户端置换token操作，服务器需要获取客户端header中的token串",httpMethod = "POST")
	@ApiImplicitParam(name = "token", value = "令牌", required = true,dataType = "string",paramType = "header")
	@RequestMapping(value = "/retoken", method = RequestMethod.POST,produces= "application/json",headers = "token")
	public @ResponseBody
	Dto replace(HttpServletRequest request) {
		String token = null;
		try {
			token = this.tokenService.reloadToken(request.getHeader("user-agent"),request.getHeader("token"));
			ItripTokenVO vo = new ItripTokenVO(token,Calendar.getInstance().getTimeInMillis()+2*60*60*1000,Calendar.getInstance().getTimeInMillis());
			return DtoUtil.returnDataSuccess(vo);
		} catch (Exception e) {
			e.printStackTrace();
			return DtoUtil.returnFail(e.getLocalizedMessage(),ErrorCode.AUTH_UNKNOWN);
		}

	}
}
