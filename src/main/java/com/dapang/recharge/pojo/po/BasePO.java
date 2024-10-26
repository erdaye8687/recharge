package com.dapang.recharge.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author liangjy
 * @Description TODO
 * @createTime 2024/10/20
 */
@Data
public class BasePO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    private String creator;
    private String operator;
    @TableLogic
    private Integer isDeleted;
}
