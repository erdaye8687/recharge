package com.dapang.recharge.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dapang.recharge.pojo.po.BasePO;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author cainiao
 * @since 2024-10-28 08:33:02
 */
@Getter
@Setter
@Accessors(chain = true)
public class Product extends BasePO {

    private static final long serialVersionUID = 1L;

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
    private Byte channel;

    /**
     * 充值金额
     */
    private BigDecimal rechargeAmount;

    /**
     * 商品单价
     */
    private BigDecimal price;

    /**
     * 商品状态（0active/1inactive）
     */
    private Byte status;
}
