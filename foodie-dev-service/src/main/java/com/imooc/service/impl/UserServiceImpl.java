package com.imooc.service.impl;

import com.imooc.enums.Sex;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @Author wuweifu
 * @Date 2020/4/25 8:12 下午
 * @Version V1.0
 * @Description: 用户接口实现
 **/
@Service
public class UserServiceImpl implements UserService {

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Autowired
    private Sid sid;

    @Autowired
    private UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {

        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);

        Users users = usersMapper.selectOneByExample(example);

        return users != null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBo userBo) {

        String userId = sid.nextShort();

        Users users = new Users();
        users.setId(userId);
        users.setUsername(userBo.getUsername());
        try {
            users.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /** 初始昵称为用户名 */
        users.setNickname(userBo.getUsername());
        /** 设置初始头像 */
        users.setFace(USER_FACE);
        /** 设置初始生日 */
        users.setBirthday(DateUtil.stringToDate("1970-01-01"));
        /** 设置初始性别 */
        users.setSex(Sex.SECRET.type);

        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());

        usersMapper.insert(users);

        return users;
    }

}
