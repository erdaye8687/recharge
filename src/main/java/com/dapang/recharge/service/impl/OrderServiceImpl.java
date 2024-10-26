package com.dapang.recharge.service.impl;

import com.dapang.recharge.pojo.po.OrderPO;
import com.dapang.recharge.mapper.OrderMapper;
import com.dapang.recharge.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 11:16:39
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderPO> implements OrderService {

}
