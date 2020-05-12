package com.imooc.service.impl;

import com.imooc.enums.YesOrNo;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author wuweifu
 * @Date 2020/5/11 11:26 下午
 * @Version V1.0
 * @Description:
 **/
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);

        return userAddressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {

        Integer isDefault = 0;

        /** 判断用户是否存在至少一个收货地址，若没有，则新增为默认收货地址 */
        List<UserAddress> userAddresses = this.queryAll(addressBO.getUserId());
        if (userAddresses == null || userAddresses.isEmpty() || userAddresses.size() == 0) {
            isDefault = 1;
        }

        String addressId = sid.nextShort();

        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);

        userAddress.setId(addressId);
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {

        String addressId = addressBO.getAddressId();

        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);

        userAddress.setId(addressId);
        userAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);

        userAddressMapper.delete(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress2Default(String userId, String addressId) {

        /** 查找现存默认地址 -> 改为非默认 */
        UserAddress queryUserAddress = new UserAddress();
        queryUserAddress.setUserId(userId);
        queryUserAddress.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> list = userAddressMapper.select(queryUserAddress);
        for (UserAddress userAddress : list) {
            userAddress.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(userAddress);
        }


        /** 根据ID修改为默认 */
        UserAddress defaultUserAddress = new UserAddress();
        defaultUserAddress.setIsDefault(YesOrNo.YES.type);
        defaultUserAddress.setId(addressId);
        defaultUserAddress.setUserId(userId);
        userAddressMapper.updateByPrimaryKeySelective(defaultUserAddress);
    }

}
