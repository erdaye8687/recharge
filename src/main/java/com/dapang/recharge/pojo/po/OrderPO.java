package com.dapang.recharge.pojo.po;

import java.math.BigDecimal;

import lombok.Data;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 11:16:39
 */
@Data
public class OrderPO extends BasePO {

    /**
     * 代理ID
     */
    private Long userId;

    /**
     * 供货商ID
     */
    private Long supplierId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单状态（0待领取、1待处理、2充值完成、3确认完成、4拒绝）
     */
    private Byte status;

    /**
     * 代理提成
     */
    private BigDecimal commission;

    /**
     * 汇率
     */
    private BigDecimal currencyRate;
}
