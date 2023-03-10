package com.caspar.eservicemall.ware.dao;

import com.caspar.eservicemall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * εεεΊε­
 * 
 * @author casparZheng
 * @email 824394795@qq.com
 * @date 2023-02-27 02:51:38
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    Long getSkuStock(Long skuId);

    void addStock(Long skuId, Long wareId, Integer skuNum);
}
