package com.dapang.recharge.service.impl;

import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dapang.recharge.common.enums.OrderStatusEnum;
import com.dapang.recharge.common.exception.ErrorCode;
import com.dapang.recharge.common.util.JwtTokenUtil;
import com.dapang.recharge.common.util.ServletUtils;
import com.dapang.recharge.common.util.WebFrameworkUtils;
import com.dapang.recharge.pojo.po.BasePO;
import com.dapang.recharge.pojo.po.OrderPO;
import com.dapang.recharge.mapper.OrderMapper;
import com.dapang.recharge.pojo.vo.OrderPoolReqVO;
import com.dapang.recharge.pojo.vo.OrderPoolRespVO;
import com.dapang.recharge.service.OrderDetailService;
import com.dapang.recharge.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.dapang.recharge.common.constant.ErrorCodeConstants.ORDER_NUM_INSUFFICIENT;
import static com.dapang.recharge.common.constant.ErrorCodeConstants.ORDER_UPDATE_STATUS_FAILED;
import static com.dapang.recharge.common.exception.util.ServiceExceptionUtil.exception;

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

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public Page<OrderPoolRespVO> findOrderPool(Integer pageNum, Integer pageSize) {
        Page<OrderPoolRespVO> page = new Page<>(pageNum, pageSize);
        Long count = baseMapper.selectOrderPoolCount();
        List<OrderPoolRespVO> orderPoolRespVOS = baseMapper.selectOrderPoolList(page.offset(), pageSize);
        page.setRecords(orderPoolRespVOS);
        page.setTotal(count);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void extractOrders(List<OrderPoolReqVO> orderPoolReqs) {
        if (CollectionUtils.isEmpty(orderPoolReqs)) {
            return;
        }
        orderPoolReqs.stream().filter(req -> req.getOrderNum() > 0)
                .forEach(req -> {
                    Long productId = req.getProductId();
                    Long orderNum = req.getOrderNum();
                    // 使用悲观锁查询“待领取”状态的订单
                    List<OrderPO> availableOrders = baseMapper.selectPendingOrdersByProductIdWithLock(productId, orderNum);

                    // 检查是否有足够的待领取订单数量，不够则抛出异常
                    if (availableOrders.size() < req.getOrderNum()) {
                        throw exception(ORDER_NUM_INSUFFICIENT);
                    }
                    List<Long> orderIds = availableOrders.stream().map(BasePO::getId).collect(Collectors.toList());
                    // 更新订单状态为“待处理”
                    OrderPO orderUpdate = new OrderPO()
                            .setStatus(OrderStatusEnum.PENDING.getValue())
                            .setSupplierId(WebFrameworkUtils.getLoginUserId(ServletUtils.getRequest()));
                    int updateNum = baseMapper.update(orderUpdate, new LambdaQueryWrapper<OrderPO>().in(OrderPO::getId, orderIds));
                    if (updateNum != orderNum) {
                        throw exception(ORDER_UPDATE_STATUS_FAILED);
                    }
                    // TODO 添加订单明细
                });


    }
}
