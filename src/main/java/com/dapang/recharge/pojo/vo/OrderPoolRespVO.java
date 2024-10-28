package com.dapang.recharge.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OrderPoolRespVO {

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 渠道 （0移动 1电信 2联通）
     */
    private Integer channel;

    /**
     * 充值金额
     */
    private BigDecimal rechargeAmount;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单状态（0待领取、1待处理、2充值完成、3确认完成、4拒绝）
     */
    private Integer status;

    /**
     * 待领取的订单数量
     */
    private Integer orderCount;
}
