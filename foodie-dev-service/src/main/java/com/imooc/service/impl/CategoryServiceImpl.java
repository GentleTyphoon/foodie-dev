package com.imooc.service.impl;

import com.imooc.mapper.CategoryMapper;
import com.imooc.pojo.Category;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author wuweifu
 * @Date 2020/5/4 3:10 下午
 * @Version V1.0
 * @Description:
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> queryAllRootLevelCat() {

        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        /** type = 1 为根结点 */
        criteria.andEqualTo("type", 1);

        List<Category> categories = categoryMapper.selectByExample(example);

        return categories;
    }
}
