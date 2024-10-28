package com.dapang.recharge.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dapang.recharge.common.pojo.Result;
import com.dapang.recharge.pojo.vo.OrderPoolReqVO;
import com.dapang.recharge.pojo.vo.OrderPoolRespVO;
import com.dapang.recharge.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

import java.util.List;

import static com.dapang.recharge.common.pojo.Result.success;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author cainiao
 * @since 2024-10-20 11:16:39
 */
@RestController
@RequestMapping("/recharge/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单池列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/pool")
    public Result<Page<OrderPoolRespVO>> getOrderPool(@RequestParam(defaultValue = "1") @Min(1) Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        return success(orderService.findOrderPool(pageNum, pageSize));
    }

    @PostMapping("/extract")
    public Result extractOrders(@RequestBody List<OrderPoolReqVO> orderPoolReqs) {
        orderService.extractOrders(orderPoolReqs);
        return success(true);
    }

}
