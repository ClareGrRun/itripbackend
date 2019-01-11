package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisAPI;
import cn.itrip.dao.user.ItripUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
    @Resource
    private ItripUserMapper itripUserMapper;
    @Resource
    private RedisAPI redisAPI;
    @Resource
    private MailService mailService;
    @Resource
    private SmsService smsService;


    /**
     * 邮箱注册
     * @param itripUser
     * @throws Exception
     */
    @Override
    public void itripxCreateUserByMail(ItripUser itripUser) throws Exception{
        //添加用户
        itripUserMapper.insertItripUser(itripUser);
        //生成激活码
        String activationCode = MD5.getMd5(new Date().toLocaleString(),32);
        //发送邮件
        mailService.sendActivationMail(itripUser.getUserCode(),activationCode);
        //激活码存入redis
        redisAPI.set("activation"+itripUser.getUserCode(),30*60,activationCode);
    }

    /**
     * 手机注册
     * @param itripUser
     * @throws Exception
     */
    public void itripxCreateUserByPhone(ItripUser itripUser) throws Exception {
        itripUserMapper.insertItripUser(itripUser);
        int code = MD5.getRandomCode();
        smsService.send(itripUser.getUserCode(),"1",new String[]{String.valueOf(code),"1"});
        redisAPI.set("activation"+itripUser.getUserCode(),2*60,String.valueOf(code));
    }

    /**
     * 邮箱激活
     * @param mail
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public boolean activate(String mail, String code) throws Exception {
        String value = redisAPI.get("activation" + mail);
        if(value.equals(code)){
            Map<String ,Object> map = new HashMap<>();
            map.put("userCode",mail);
            List<ItripUser> users = itripUserMapper.getItripUserListByMap(map);
            if(users.size()>0){
                ItripUser user = users.get(0);
                user.setActivated(1);
                user.setUserType(0);
                user.setFlatID(user.getId());
                itripUserMapper.updateItripUser(user);
                return  true;
            }
        }else {
            return false;
        }
        return  false;
    }

    /**
     * 手机激活
     * @param phoneNum
     * @param code
     * @return
     * @throws Exception
     */
    public boolean validatePhone(String phoneNum,String code)throws  Exception{
        String value = redisAPI.get("activation"+phoneNum);
        if(null!=value&&value.equals(code)){
            Map<String,Object> map = new HashMap<>();
            map.put("userCode",phoneNum);
            List<ItripUser> users = itripUserMapper.getItripUserListByMap(map);
            if(users.size()>0){
                ItripUser user = users.get(0);
                user.setActivated(1);
                user.setUserType(0);
                user.setFlatID(user.getId());
                itripUserMapper.updateItripUser(user);
                return  true;
            }else{
                return false;
            }
        }
        return false;
    }
    /**
     * 重名验证
     * @param userCode
     * @return
     * @throws Exception
     */
    @Override
    public ItripUser findUserByUserCode(String userCode) throws Exception {
        Map<String ,Object> map = new HashMap<>();
        map.put("userCode",userCode);
        List<ItripUser> users = itripUserMapper.getItripUserListByMap(map);
        if(users.size()>0){
            return users.get(0);
        }else{
            return null;
        }
    }
}
