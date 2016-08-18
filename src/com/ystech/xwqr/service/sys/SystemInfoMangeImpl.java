package com.ystech.xwqr.service.sys;

import org.springframework.stereotype.Component;

import com.ystech.core.dao.HibernateEntityDao;
import com.ystech.xwqr.model.sys.SystemInfo;

@Component("systemInfoMangeImpl")
public class SystemInfoMangeImpl extends HibernateEntityDao<SystemInfo>{

}
