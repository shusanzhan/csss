package com.ystech.weixin.service;

import org.springframework.stereotype.Component;

import com.ystech.core.dao.HibernateEntityDao;
import com.ystech.weixin.model.WeixinReceivetext;

@Component("weixinReceivetextManageImpl")
public class WeixinReceivetextManageImpl extends HibernateEntityDao<WeixinReceivetext>{

}
