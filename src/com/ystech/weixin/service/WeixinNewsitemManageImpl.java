package com.ystech.weixin.service;

import org.springframework.stereotype.Component;

import com.ystech.core.dao.HibernateEntityDao;
import com.ystech.weixin.model.WeixinNewsitem;

@Component("weixinNewsitemManageImpl")
public class WeixinNewsitemManageImpl extends HibernateEntityDao<WeixinNewsitem>{

}
