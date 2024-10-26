package com.dapang.recharge.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

import lombok.Data;

/**
 * <p>
 * 交易流水表
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 11:16:40
 */
@Data
@TableName("order_detail")
public class OrderDetailPO extends BasePO {

    /**
     * 代理ID或供货商ID
     */
    private Long userId;

    /**
     * 对应订单ID
     */
    private Long orderId;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易前余额
     */
    private BigDecimal balanceBefore;

    /**
     * 交易后余额
     */
    private BigDecimal balanceAfter;

    /**
     * 交易类型（充值、提现等）
     */
    private Byte type;

    /**
     * 交易状态（0待领取、1待处理、2充值完成、3确认完成、4拒绝）
     */
    private Byte status;
}
