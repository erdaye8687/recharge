package com.dapang.recharge.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dapang.recharge.pojo.po.OrderPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dapang.recharge.pojo.vo.OrderPoolReqVO;
import com.dapang.recharge.pojo.vo.OrderPoolRespVO;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 11:16:39
 */
public interface OrderService extends IService<OrderPO> {

    Page<OrderPoolRespVO> findOrderPool(Integer pageNum, Integer pageSize);

    /**
     * 提取订单
     * @param orderPoolReqVO
     */
    void extractOrders(List<OrderPoolReqVO> orderPoolReqVO);
}
