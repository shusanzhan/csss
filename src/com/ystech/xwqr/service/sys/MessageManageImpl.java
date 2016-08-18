/**
 * 
 */
package com.ystech.xwqr.service.sys;

import org.springframework.stereotype.Component;

import com.ystech.core.dao.HibernateEntityDao;
import com.ystech.xwqr.model.sys.Message;

/**
 * @author shusanzhan
 * @date 2014-3-19
 */
@Component("messageManageImpl")
public class MessageManageImpl extends HibernateEntityDao<Message>{
	
}
