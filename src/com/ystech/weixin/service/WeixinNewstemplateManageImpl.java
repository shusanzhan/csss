package com.ystech.weixin.service;

import org.springframework.stereotype.Component;

import com.ystech.core.dao.HibernateEntityDao;
import com.ystech.weixin.model.WeixinNewstemplate;

@Component("weixinNewstemplateManageImpl")
public class WeixinNewstemplateManageImpl extends HibernateEntityDao<WeixinNewstemplate>{

}
