package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.dao.user.ItripUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
    @Resource
    private ItripUserMapper itripUserMapper;


    @Override
    public Integer insertItripUser(ItripUser itripUser) throws Exception{
        return itripUserMapper.insertItripUser(itripUser);
    }
}
