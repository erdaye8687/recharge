package com.dapang.recharge.common.enums;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 全局用户类型枚举
 */
@AllArgsConstructor
@Getter
public enum OrderStatusEnum implements IntArrayValuable {
    // 0待领取、1待处理、2待充值、3待确认、4拒绝
    WAIT_RECEIVE(0, "待领取"),
    PENDING(1, "待处理"),
    WAIT_RECHARGE(2, "待充值"),
    WAIT_CONFIRM(3, "待确认"),
    REFUSE(4, "拒绝");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(OrderStatusEnum::getValue).toArray();

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;

    public static OrderStatusEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getValue().equals(value), OrderStatusEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
