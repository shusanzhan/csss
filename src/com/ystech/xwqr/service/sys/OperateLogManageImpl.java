/**
 * 
 */
package com.ystech.xwqr.service.sys;

import org.springframework.stereotype.Component;

import com.ystech.core.dao.HibernateEntityDao;
import com.ystech.xwqr.model.sys.OperateLog;

/**
 * @author shusanzhan
 * @date 2013-6-22
 */
@Component("operateLogManageImpl")
public class OperateLogManageImpl extends HibernateEntityDao<OperateLog>{

}
