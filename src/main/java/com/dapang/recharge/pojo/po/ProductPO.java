package com.dapang.recharge.pojo.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author cainiao
 * @since 2024-10-27 09:25:30
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("product")
public class ProductPO extends BasePO {

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品类型
     */
    private String type;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 渠道 （0移动 1电信 2联通）
     */
    private Integer channel;

    /**
     * 充值金额
     */
    private BigDecimal rechargeAmount;

    /**
     * 商品单价
     */
    private BigDecimal price;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 商品状态（0 active/1 inactive）
     */
    private Integer status;
}
