package com.dapang.recharge.pojo.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 供货商表
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 11:05:17
 */
@Data
public class SupplierPO extends BasePO {

    /**
     * 供货商名称
     */
    private String name;

    /**
     * 供货商汇率
     */
    private BigDecimal rate;

    /**
     * 联系信息
     */
    private String contactInfo;

    /**
     * 供货商地址
     */
    private String address;

    /**
     * 供货商状态 0:active; 1:inactive
     */
    private Integer status;
}
