package com.caspar.eservicemall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.caspar.eservicemall.common.entity.order.OmsCommonOrderEntity;
import com.caspar.eservicemall.common.to.mq.SeckillOrderTO;
import com.caspar.eservicemall.common.to.seckill.SeckillOrderTo;
import com.caspar.eservicemall.common.utils.PageUtils;
import com.caspar.eservicemall.common.vo.order.OrderConfirmVO;
import com.caspar.eservicemall.common.vo.order.OrderSubmitVO;
import com.caspar.eservicemall.common.vo.order.PayVO;
import com.caspar.eservicemall.common.vo.order.SubmitOrderResponseVO;
import com.caspar.eservicemall.order.entity.OmsOrderEntity;
import com.caspar.eservicemall.order.entity.OmsPaymentInfoEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-02-27 01:02:23
 */
public interface OmsOrderService extends IService<OmsOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVO getOrderConfirmData() throws Exception;

    SubmitOrderResponseVO submitOrder(OrderSubmitVO vo) throws Exception;

    OmsOrderEntity getOrderByOrderSn(String orderSn);

    void closeOrder(OmsCommonOrderEntity order);

    /**
     * 获取订单支付的详细信息
     */
    PayVO getOrderPay(String orderSn);

    void handlePayResult(Integer orderStatus, Integer payCode, OmsPaymentInfoEntity paymentInfo);

    PageUtils queryPageWithItem(Map<String, Object> params);

    public void createSeckillOrder(SeckillOrderTo order);
}

