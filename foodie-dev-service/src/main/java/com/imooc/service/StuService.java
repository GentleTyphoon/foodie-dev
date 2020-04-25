package com.imooc.service;

import com.imooc.pojo.Stu;

/**
 * 测试 步骤一 · 2-21 基于通用Mapper基于Rest编写api接口-1
 */
public interface StuService {

    /** 查询 */
    public Stu getStuInfo(int id);

    /** 保存 */
    public void saveStu();

    /** 更新 */
    public void updateStu(int id);

    /** 删除 */
    public void deleteStu(int id);

}
