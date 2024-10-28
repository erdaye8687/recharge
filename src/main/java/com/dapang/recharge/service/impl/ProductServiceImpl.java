package com.dapang.recharge.service.impl;

import com.dapang.recharge.pojo.po.ProductPO;
import com.dapang.recharge.mapper.ProductMapper;
import com.dapang.recharge.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author cainiao
 * @since 2024-10-27 09:25:30
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductPO> implements ProductService {

}
