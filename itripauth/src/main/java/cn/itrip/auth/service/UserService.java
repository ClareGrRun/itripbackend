package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

public interface UserService {
    public Integer insertItripUser(ItripUser itripUser) throws Exception;
}
