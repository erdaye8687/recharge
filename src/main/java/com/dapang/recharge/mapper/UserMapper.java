package com.dapang.recharge.mapper;

import com.dapang.recharge.pojo.po.UserPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 09:14:25
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {

}
