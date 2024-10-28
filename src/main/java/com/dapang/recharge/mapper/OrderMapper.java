package com.dapang.recharge.mapper;

import com.dapang.recharge.pojo.po.OrderPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dapang.recharge.pojo.vo.OrderPoolRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 11:16:39
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderPO> {

    /**
     * 查询订单池列表
     * 查询订单状态为待领取0的订单，并按照充值金额和运营商分组获取数量并排序
     * @param offset
     * @param pageSize
     * @return
     */
    @Select("SELECT product_id AS productId, channel, recharge_amount AS rechargeAmount, COUNT(*) AS orderCount " +
            "FROM `order` " +
            "WHERE status = 0 " +
            "GROUP BY product_id, channel, recharge_amount " +
            "ORDER BY recharge_amount ASC, channel ASC " +
            "LIMIT #{offset}, #{pageSize}")
    List<OrderPoolRespVO> selectOrderPoolList(@Param("offset") long offset, @Param("pageSize") Integer pageSize);

    @Select("SELECT COUNT(*) FROM ( " +
            "SELECT 1 " +
            "FROM `order` " +
            "WHERE status = 0 " +
            "GROUP BY product_id, channel, recharge_amount " +
            ") AS groupedOrders")
    Long selectOrderPoolCount();

    /**
     * 使用悲观锁查询“待领取”状态的订单
     *
     * @param productId
     * @param orderNum
     * @return
     */
    @Select("SELECT * FROM `order` WHERE status = 0 AND product_id = #{productId} ORDER BY id LIMIT #{orderNum} FOR UPDATE")
    List<OrderPO> selectPendingOrdersByProductIdWithLock(Long productId, Long orderNum);
}
