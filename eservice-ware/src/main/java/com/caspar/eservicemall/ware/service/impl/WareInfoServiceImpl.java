package com.caspar.eservicemall.ware.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.TypeReference;
import com.caspar.eservicemall.common.utils.R;
import com.caspar.eservicemall.ware.feign.MemberFeignService;
import com.caspar.eservicemall.ware.vo.FareVO;
import com.caspar.eservicemall.ware.vo.MemberAddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caspar.eservicemall.common.utils.PageUtils;
import com.caspar.eservicemall.common.utils.Query;

import com.caspar.eservicemall.ware.dao.WareInfoDao;
import com.caspar.eservicemall.ware.entity.WareInfoEntity;
import com.caspar.eservicemall.ware.service.WareInfoService;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {
    @Autowired
    MemberFeignService memberFeignService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.eq("id",key).or().like("address",key).
                    or().like("name",key).
                    or().like("areacode",key);
        }
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    /**
     * 获取运费
     * @param addrId 会员收货地址ID
     */
    @Override
    public FareVO getFare(Long addrId) {
        FareVO fareVo = new FareVO();
        //收获地址的详细信息
        R addrInfo = memberFeignService.info(addrId);
        MemberAddressVO memberAddressVo = addrInfo.getData("memberReceiveAddress", new TypeReference<MemberAddressVO>() {
        });
        if (memberAddressVo != null) {
            String phone = memberAddressVo.getPhone();
            //截取用户手机号码最后一位作为我们的运费计算
            //1558022051
            String fare = phone.substring(phone.length() - 1);
            BigDecimal bigDecimal = new BigDecimal(fare);
            fareVo.setFare(bigDecimal);
            fareVo.setAddress(memberAddressVo);
            return fareVo;
        }
        return null;
    }

}