package com.ystech.csss.action;

import com.ystech.core.dao.Page;
import com.ystech.core.excel.ScannRecordExportExcel;
import com.ystech.core.excel.ScannRecordReportExcel;
import com.ystech.core.util.DatabaseUnitHelper;
import com.ystech.core.util.DateUtil;
import com.ystech.core.util.ParamUtil;
import com.ystech.core.web.BaseController;
import com.ystech.csss.model.CsssShop;
import com.ystech.csss.model.ReportData;
import com.ystech.csss.model.ScannRecord;
import com.ystech.csss.service.CsssShopManageImpl;
import com.ystech.csss.service.ScannRecordManageImpl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("scannRecordAction")
@Scope("prototype")
public class ScannRecordAction
  extends BaseController
{
  private ScannRecordManageImpl scannRecordManageImpl;
  private CsssShopManageImpl csssShopManageImpl;
  private ScannRecordExportExcel scannRecordExportExcel;
  private ScannRecordReportExcel scannRecordReportExcel;
  
  @Resource
  public void setScannRecordManageImpl(ScannRecordManageImpl scannRecordManageImpl)
  {
    this.scannRecordManageImpl = scannRecordManageImpl;
  }
  
  @Resource
  public void setCsssShopManageImpl(CsssShopManageImpl csssShopManageImpl)
  {
    this.csssShopManageImpl = csssShopManageImpl;
  }
  
  @Resource
  public void setScannRecordExportExcel(ScannRecordExportExcel scannRecordExportExcel)
  {
    this.scannRecordExportExcel = scannRecordExportExcel;
  }
  
  @Resource
  public void setScannRecordReportExcel(ScannRecordReportExcel scannRecordReportExcel)
  {
    this.scannRecordReportExcel = scannRecordReportExcel;
  }
  
  public String queryList()  throws Exception{
	    HttpServletRequest request = getRequest();
	    Integer pageSize = ParamUtil.getIntParam(request, "pageSize", 10);
	    Integer pageNo = ParamUtil.getIntParam(request, "currentPage", 1);
	    Integer csssShopId = ParamUtil.getIntParam(request, "csssShopId", -1);
	    String staffName = request.getParameter("staffName");
	    String startTime = request.getParameter("startTime");
	    String endTime = request.getParameter("endTime");
	    try
	    {
		      List<CsssShop> csssShops = this.csssShopManageImpl.getAll();
		      request.setAttribute("csssShops", csssShops);
		      String sql = "select * from csss_scannRecord where 1=1 ";
		      List params = new ArrayList();
		      if (csssShopId.intValue() > 0)
		      {
		        sql = sql + " and shopId=?";
		        params.add(csssShopId);
		      }
		      if ((staffName != null) && (staffName.trim().length() > 0))
		      {
		        sql = sql + " and staffName like ?";
		        params.add("%" + staffName + "%");
		      }
		      if ((startTime != null) && (startTime.trim().length() > 0))
		      {
		        sql = sql + " and scannDate>= ? ";
		        params.add(DateUtil.string2Date(startTime));
		      }
		      if ((endTime != null) && (endTime.trim().length() > 0))
		      {
		        sql = sql + " and scannDate< ? ";
		        params.add(DateUtil.nextDay(endTime));
		      }
		      Page<ScannRecord> page = this.scannRecordManageImpl.pagedQuerySql(pageNo.intValue(), pageSize.intValue(), ScannRecord.class, sql, params.toArray());
		      request.setAttribute("page", page);
	    }
	    catch (Exception localException) {
	    	
	    }
	    return "list";
  }
  
  public void downExportExcel() throws Exception{
	    HttpServletRequest request = getRequest();
	    HttpServletResponse response = getResponse();
	    String staffName = request.getParameter("staffName");
	    String startTime = request.getParameter("startTime");
	    String endTime = request.getParameter("endTime");
	    Integer csssShopId = ParamUtil.getIntParam(request, "csssShopId", -1);
	    try
	    {
		      String fileName = "";
		      if ((startTime != null) && (startTime.trim().length() > 0)) {
		        fileName = fileName + startTime;
		      }
		      if ((endTime != null) && (endTime.trim().length() > 0)) {
		        fileName = fileName + "至" + endTime;
		      } else {
		        fileName = fileName + "至" + DateUtil.format(new Date());
		      }
		      String sql = "select * from csss_scannRecord where 1=1 ";
		      List params = new ArrayList();
		      if (csssShopId.intValue() > 0)
		      {
		        sql = sql + " and shopId=?";
		        params.add(csssShopId);
		      }
		      if ((staffName != null) && (staffName.trim().length() > 0))
		      {
		        sql = sql + " and staffName like ?";
		        params.add("%" + staffName + "%");
		      }
		      if ((startTime != null) && (startTime.trim().length() > 0))
		      {
		        sql = sql + " and scannDate>= ? ";
		        params.add(DateUtil.string2Date(startTime));
		      }
		      if ((endTime != null) && (endTime.trim().length() > 0))
		      {
		        sql = sql + " and scannDate< ? ";
		        params.add(DateUtil.nextDay(endTime));
		      }
		      List<ScannRecord> scannRecords = this.scannRecordManageImpl.executeSql(sql, params.toArray());
		      String file = this.scannRecordExportExcel.writeExcel(fileName, scannRecords);
		      downFile(request, response, file);
	    }catch (Exception e){
		      e.printStackTrace();
		      this.log.error(e);
	    }
  }
  
  public String report() throws Exception{
	    HttpServletRequest request = getRequest();
	    String cName = request.getParameter("cName");
	    String startTime = request.getParameter("startTime");
	    String endTime = request.getParameter("endTime");
	    try
	    {
		      List<CsssShop> csssShops = this.csssShopManageImpl.getAll();
		      request.setAttribute("csssShops", csssShops);
		      
		      String countSql = "SELECT shop.dbid as dbid,ps.`no` AS psNo,ps.`name` AS psName,ccs.`no` AS csNo,ccs.`name` AS csName,shop.`no` AS shopNo,shop.`name` AS shopName,COUNT(shop.dbid) AS num "
		      		+ "FROM "
		      		+ "csss_scannrecord csr,csss_cityshop ccs,csss_proviceshop ps,csss_shop shop "
		      		+ "where   "
		      		+ "csr.shopId=shop.dbid AND shop.cityShopId=ccs.dbid AND shop.proviceShopId=ps.dbid ";
		      if (null!=cName&&cName.trim().length()>0) {
		        countSql = countSql + " AND shop.name like '%" + cName+"%'";
		      }
		      if ((startTime != null) && (startTime.trim().length() > 0)) {
		        countSql = countSql + " AND csr.scannDate>='" + startTime + "' ";
		      }
		      if ((endTime != null) && (endTime.trim().length() > 0)) {
		        countSql = countSql + " AND csr.scannDate<='" + endTime + "' ";
		      }
		      countSql = 
		      
		      countSql + "GROUP BY shopId " + "ORDER BY num DESC";
		      List<ReportData> reportDatas = exceuteReprotSql(countSql,1);
		      request.setAttribute("reportDatas", reportDatas);
	    }
	    catch (Exception e){
		      e.printStackTrace();
		      this.log.error(e);
	    }
	    return "report";
  }
  /**
   * 统计员工数据
   * @return
   * @throws Exception
   */
  public String reportStaff() throws Exception{
	  HttpServletRequest request = getRequest();
	  Integer csssShopId = ParamUtil.getIntParam(request, "csssShopId", -1);
	  String startTime = request.getParameter("startTime");
	  String endTime = request.getParameter("endTime");
	  String staffName = request.getParameter("staffName");
	  try
	  {
		  List<CsssShop> csssShops = this.csssShopManageImpl.getAll();
		  request.setAttribute("csssShops", csssShops);
		  
		  String countSql = "SELECT staff.dbid as dbid,ps.`no` AS psNo,ps.`name` AS psName,ccs.`no` AS csNo,"
		  		+ "ccs.`name` AS csName,shop.`no` AS shopNo,shop.`name` AS shopName,COUNT(staff.dbid) AS num,staff.name as staffName "
				  + "FROM "
				  + "csss_scannrecord csr,csss_cityshop ccs,csss_proviceshop ps,csss_shop shop,csss_Staff staff "
				  + "where   "
				  + "csr.shopId=shop.dbid AND shop.cityShopId=ccs.dbid AND shop.proviceShopId=ps.dbid and staff.dbid=csr.staffId ";
		  if (null!=csssShopId&&csssShopId>0) {
			  countSql = countSql + " AND shop.dbid ="+csssShopId+" ";
		  }
		  if ((staffName != null) && (staffName.trim().length() > 0))
	      {
			  countSql = countSql + " and staff.name like '%"+staffName+"%'";
	      }
		  if ((startTime != null) && (startTime.trim().length() > 0)) {
			  countSql = countSql + " AND csr.scannDate>='" + startTime + "' ";
		  }
		  if ((endTime != null) && (endTime.trim().length() > 0)) {
			  countSql = countSql + " AND csr.scannDate<='" + endTime + "' ";
		  }
		  countSql = 
				  
				  countSql + "GROUP BY staff.dbid " + "ORDER BY num DESC";
		  List<ReportData> reportDatas = exceuteReprotSql(countSql,2);
		  request.setAttribute("reportDatas", reportDatas);
	  }
	  catch (Exception e){
		  e.printStackTrace();
		  this.log.error(e);
	  }
	  return "reportStaff";
  }
  	/**
	* 功能描述：报表明细
	* 参数描述：
	* 逻辑描述：
	* @return
	* @throws Exception
	*/
	public String reportDetial() throws Exception {
		HttpServletRequest request = getRequest();
	    Integer pageSize = ParamUtil.getIntParam(request, "pageSize", 10);
	    Integer pageNo = ParamUtil.getIntParam(request, "currentPage", 1);
	    Integer staffId = ParamUtil.getIntParam(request, "staffId", -1);
	    String staffName = request.getParameter("staffName");
	    String startTime = request.getParameter("startTime");
	    String endTime = request.getParameter("endTime");
	    try
	    {
		      String sql = "select * from csss_scannRecord where 1=1 ";
		      List params = new ArrayList();
		      if (staffId>0)
		      {
		        sql = sql + " and staffId=?";
		        params.add(staffId);
		      }
		      if ((staffName != null) && (staffName.trim().length() > 0))
		      {
		        sql = sql + " and staffName like ?";
		        params.add("%" + staffName + "%");
		      }
		      if ((startTime != null) && (startTime.trim().length() > 0))
		      {
		        sql = sql + " and scannDate>= ? ";
		        params.add(DateUtil.string2Date(startTime));
		      }
		      if ((endTime != null) && (endTime.trim().length() > 0))
		      {
		        sql = sql + " and scannDate< ? ";
		        params.add(DateUtil.nextDay(endTime));
		      }
		      Page<ScannRecord> page = scannRecordManageImpl.pagedQuerySql(pageNo.intValue(), pageSize.intValue(), ScannRecord.class, sql, params.toArray());
		      request.setAttribute("page", page);
	    }
	      catch (Exception e){
		      e.printStackTrace();
		      log.error(e);
	    }
		return "reportDetial";
	}
	public void downReportExcel() throws Exception{
	    HttpServletRequest request = getRequest();
	    Integer csssShopId = ParamUtil.getIntParam(request, "csssShopId", -1);
	    String startTime = request.getParameter("startTime");
	    String endTime = request.getParameter("endTime");
	    HttpServletResponse response = getResponse();
	    try
	    {
	      String fileName = "";
	      if ((startTime != null) && (startTime.trim().length() > 0)) {
	        fileName = fileName + startTime;
	      }
	      if ((endTime != null) && (endTime.trim().length() > 0)) {
	        fileName = fileName + "至" + endTime;
	      } else {
	        fileName = fileName + "至" + DateUtil.format(new Date());
	      }
	      List<CsssShop> csssShops = this.csssShopManageImpl.getAll();
	      request.setAttribute("csssShops", csssShops);
	      
	      String countSql = "SELECT shop.dbid as dbid,ps.`no` AS psNo,ps.`name` AS psName,ccs.`no` AS csNo,ccs.`name` AS csName,shop.`no` AS shopNo,shop.`name` AS shopName,COUNT(shop.dbid) AS num FROM csss_scannrecord csr,csss_cityshop ccs,csss_proviceshop ps,csss_shop shop where   csr.shopId=shop.dbid AND shop.cityShopId=ccs.dbid AND shop.proviceShopId=ps.dbid ";
	      if (csssShopId.intValue() > 0) {
	        countSql = countSql + " AND cs.dbid=" + csssShopId;
	      }
	      if ((startTime != null) && (startTime.trim().length() > 0)) {
	        countSql = countSql + " AND csr.scannDate>='" + startTime + "' ";
	      }
	      if ((endTime != null) && (endTime.trim().length() > 0)) {
	        countSql = countSql + " AND csr.scannDate<'" + endTime + "' ";
	      }
	      countSql = 
	      
	        countSql + "GROUP BY shopId " + "ORDER BY num DESC";
	      List<ReportData> reportDatas = exceuteReprotSql(countSql,1);
	      String file = scannRecordReportExcel.writeExcel(fileName, reportDatas);
	      downFile(request, response, file);
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      this.log.error(e);
	    }
  }
  
  public void delete() throws Exception{
	    HttpServletRequest request = getRequest();
	    Integer[] dbids = ParamUtil.getIntArraryByDbids(request, "dbids");
	    if ((dbids != null) && (dbids.length > 0))
	    {
	      try
	      {
	        Integer[] arrayOfInteger1;
	        int j = (arrayOfInteger1 = dbids).length;
	        for (int i = 0; i < j; i++)
	        {
	          Integer dbid = arrayOfInteger1[i];
	          this.scannRecordManageImpl.deleteById(dbid);
	        }
	      }
	      catch (Exception e)
	      {
	        e.printStackTrace();
	        this.log.error(e);
	        renderErrorMsg(e, "");
	        return;
	      }
	    }
	    else
	    {
	      renderErrorMsg(new Throwable("未选中数据！"), "");
	      return;
	    }
	    String query = ParamUtil.getQueryUrl(request);
	    renderMsg("/scannRecord/queryList" + query, "删除数据成功！");
  }
  
  public List<ReportData> exceuteReprotSql(String sql,int type)
  {
	    List<ReportData> counts = new ArrayList();
	    try
	    {
	      DatabaseUnitHelper databaseUnitHelper = new DatabaseUnitHelper();
	      Connection jdbcConnection = databaseUnitHelper.getJdbcConnection();
	      Statement createStatement = jdbcConnection.createStatement();
	      ResultSet resultSet = createStatement.executeQuery(sql);
	      resultSet.last();
	      resultSet.beforeFirst();
	      while (resultSet.next())
	      {
	    	    Integer  dbid = resultSet.getInt("dbid");
		        String psNo = resultSet.getString("psNo");
		        String psName = resultSet.getString("psName");
		        String csNo = resultSet.getString("csNo");
		        String csName = resultSet.getString("csName");
		        String shopNo = resultSet.getString("shopNo");
		        String shopName = resultSet.getString("shopName");
		        Integer num = Integer.valueOf(resultSet.getInt("num"));
		        ReportData reportData = new ReportData();
		        reportData.setDbid(dbid);
		        reportData.setPsNo(psNo);
		        reportData.setPsName(psName);
		        reportData.setCsNo(csNo);
		        reportData.setCsName(csName);
		        reportData.setShopNo(shopNo);
		        reportData.setShopName(shopName);
		        reportData.setNum(num);
		        if(type==2){
		        	String staffName = resultSet.getString("staffName");
		        	reportData.setStaffName(staffName);
		        }
		        counts.add(reportData);
	      }
	      createStatement.close();
	      jdbcConnection.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      return null;
	    }
	    return counts;
  }
}
