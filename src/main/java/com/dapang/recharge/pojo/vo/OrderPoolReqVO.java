package com.dapang.recharge.pojo.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrderPoolReqVO {

    /**
     * 商品ID
     * 不能为空
     */
    @NotNull(message = "商品ID不能为空")
    private Long productId;
    @NotNull(message = "订单号不能为空")
    @Min(value = 1, message = "订单数量必须大于0")
    private Long orderNum;
}
