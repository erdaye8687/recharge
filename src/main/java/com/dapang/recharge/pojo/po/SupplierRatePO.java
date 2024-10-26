package com.dapang.recharge.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 供货商汇率表
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 11:05:18
 */
@Data
@TableName("supplier_rate")
public class SupplierRatePO extends BasePO {

    /**
     * 供货商ID
     */
    private Long supplierId;

    /**
     * 汇率
     */
    private BigDecimal rate;

    /**
     * 生效日期
     */
    private Date effectiveDate;

    /**
     * 汇率状态 0:active; 1:inactive
     */
    private Integer status;
}
