package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result queryTypeShop() {
        //redis查询
        List<String> shop_type = stringRedisTemplate.opsForList().range("shop_type", 0, -1);
        //redis输出
        if (!shop_type.isEmpty()) {
            //redis转换
            List<ShopType> shopTypes = shop_type.stream().map(item -> {
                return JSONUtil.toBean(item, ShopType.class);
            }).collect(Collectors.toList());
            return Result.ok(shopTypes);
        }
        //数据库查询
        List<ShopType> shopTypes = query().orderByAsc("sort").list();
        //数据库转换
        List<String> collect = shopTypes.stream().map(item -> {
            return JSONUtil.toJsonStr(item);
        }).collect(Collectors.toList());
        //写入redis
        stringRedisTemplate.opsForList().rightPushAll("shop_type",collect);
        return Result.ok(shopTypes);
    }

}
