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
  
  public String queryList()
    throws Exception
  {
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
    catch (Exception localException) {}
    return "list";
  }
  
  public void downExportExcel()
    throws Exception
  {
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
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error(e);
    }
  }
  
  public String report()
    throws Exception
  {
    HttpServletRequest request = getRequest();
    Integer csssShopId = ParamUtil.getIntParam(request, "csssShopId", -1);
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    try
    {
      List<CsssShop> csssShops = this.csssShopManageImpl.getAll();
      request.setAttribute("csssShops", csssShops);
      
      String countSql = "SELECT ps.`no` AS psNo,ps.`name` AS psName,ccs.`no` AS csNo,ccs.`name` AS csName,shop.`no` AS shopNo,shop.`name` AS shopName,COUNT(shop.dbid) AS num FROM csss_scannrecord csr,csss_cityshop ccs,csss_proviceshop ps,csss_shop shop where   csr.shopId=shop.dbid AND shop.cityShopId=ccs.dbid AND shop.proviceShopId=ps.dbid ";
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
      List<ReportData> reportDatas = exceuteReprotSql(countSql);
      request.setAttribute("reportDatas", reportDatas);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error(e);
    }
    return "report";
  }
  
  public void downReportExcel()
    throws Exception
  {
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
      
      String countSql = "SELECT ps.`no` AS psNo,ps.`name` AS psName,ccs.`no` AS csNo,ccs.`name` AS csName,shop.`no` AS shopNo,shop.`name` AS shopName,COUNT(shop.dbid) AS num FROM csss_scannrecord csr,csss_cityshop ccs,csss_proviceshop ps,csss_shop shop where   csr.shopId=shop.dbid AND shop.cityShopId=ccs.dbid AND shop.proviceShopId=ps.dbid ";
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
      List<ReportData> reportDatas = exceuteReprotSql(countSql);
      String file = this.scannRecordReportExcel.writeExcel(fileName, reportDatas);
      downFile(request, response, file);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error(e);
    }
  }
  
  public void delete()
    throws Exception
  {
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
  
  public List<ReportData> exceuteReprotSql(String sql)
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
        String psNo = resultSet.getString("psNo");
        String psName = resultSet.getString("psName");
        String csNo = resultSet.getString("csNo");
        String csName = resultSet.getString("csName");
        String shopNo = resultSet.getString("shopNo");
        String shopName = resultSet.getString("shopName");
        Integer num = Integer.valueOf(resultSet.getInt("num"));
        ReportData reportData = new ReportData();
        reportData.setPsNo(psNo);
        reportData.setPsName(psName);
        reportData.setCsNo(csNo);
        reportData.setCsName(csName);
        reportData.setShopNo(shopNo);
        reportData.setShopName(shopName);
        reportData.setNum(num);
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
