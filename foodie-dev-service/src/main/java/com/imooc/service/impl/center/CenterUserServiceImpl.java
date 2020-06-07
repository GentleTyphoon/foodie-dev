package com.imooc.service.impl.center;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.service.center.CenterUserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * @Author wuweifu
 * @Date 2020/4/25 8:12 下午
 * @Version V1.0
 * @Description: 用户接口实现
 **/
@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {

        Users users = usersMapper.selectByPrimaryKey(userId);
        users.setPassword("");

        return users;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {

        Users users = new Users();

        BeanUtils.copyProperties(centerUserBO, users);

        users.setId(userId);
        users.setUpdatedTime(new Date());

        usersMapper.updateByPrimaryKeySelective(users);

        return queryUserInfo(userId);
    }
}
