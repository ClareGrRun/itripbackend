package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

public interface UserService {
    public void itripxCreateUserByMail(ItripUser itripUser) throws Exception;

    public void itripxCreateUserByPhone(ItripUser itripUser)throws Exception;

    public boolean activate(String mail,String code) throws Exception;

    public ItripUser findUserByUserCode(String userCode) throws  Exception;

    public boolean validatePhone(String phoneNum,String code)throws  Exception;
}
