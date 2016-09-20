package com.ystech.csss.service;

import com.ystech.core.dao.HibernateEntityDao;
import com.ystech.csss.model.ScannRecord;
import org.springframework.stereotype.Component;

@Component("scannRecordManageImpl")
public class ScannRecordManageImpl  extends HibernateEntityDao<ScannRecord>{
}

