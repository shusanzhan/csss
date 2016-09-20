package com.ystech.core.excel;

import com.ystech.core.util.DateUtil;
import com.ystech.core.util.PathUtil;
import com.ystech.csss.model.CityShop;
import com.ystech.csss.model.CsssShop;
import com.ystech.csss.model.CsssStaff;
import com.ystech.csss.model.ProviceShop;
import com.ystech.csss.model.ScannRecord;
import com.ystech.weixin.model.WeixinGzuserinfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

@Component("scannRecordExportExcel")
public class ScannRecordExportExcel
{
  public String writeExcel(String fileName, List<ScannRecord> scannRecords) throws IOException {
    Workbook wb = new HSSFWorkbook();
    String filePath = getFilePath(fileName);
    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
    Sheet sheet = getSheet(wb, fileName);
    
    CellStyle cellStyle = getTitleStyle(wb);
    
    CellStyle quesionStyle = getQuesionStyle(wb);
    
    Row rowTitle = getRowTitle(sheet, cellStyle);
    for (int i = 0; i < scannRecords.size(); i++)
    {
      CellStyle contentStyle = getContentStyle(wb);
      ScannRecord scannRecord = (ScannRecord)scannRecords.get(i);
      getRowValue(sheet, contentStyle, scannRecord, i + 1);
    }
    wb.write(fileOutputStream);
    
    fileOutputStream.close();
    return filePath;
  }
  
  public Row getRowValue(Sheet sheet, CellStyle cellStyle, ScannRecord scannRecord, int i)
  {
    if (scannRecord == null) {
      return null;
    }
    Row row = sheet.createRow(i);
    row.setHeightInPoints(28.0F);
    Cell xuehao = row.createCell(0);
    xuehao.setCellValue(i);
    CsssShop csssShop = scannRecord.getCsssShop();
    if (csssShop == null) {
      return null;
    }
    ProviceShop proviceShop = csssShop.getProviceShop();
    
    Cell proviceShopNo = row.createCell(1);
    proviceShopNo.setCellStyle(cellStyle);
    Cell proviceShopName = row.createCell(2);
    proviceShopName.setCellStyle(cellStyle);
    if (proviceShop != null)
    {
      proviceShopNo.setCellValue(proviceShop.getNo());
      proviceShopName.setCellValue(proviceShop.getName());
    }
    else
    {
      proviceShopNo.setCellValue("");
      proviceShopName.setCellValue("");
    }
    CityShop cityShop = csssShop.getCityShop();
    
    Cell cityNo = row.createCell(3);
    cityNo.setCellStyle(cellStyle);
    Cell cityName = row.createCell(4);
    cityName.setCellStyle(cellStyle);
    if (proviceShop != null)
    {
      cityNo.setCellValue(cityShop.getNo());
      cityName.setCellValue(cityShop.getName());
    }
    else
    {
      cityNo.setCellValue("");
      cityName.setCellValue("");
    }
    Cell shopNo = row.createCell(5);
    shopNo.setCellStyle(cellStyle);
    if (scannRecord.getCsssShop() != null) {
      shopNo.setCellValue(scannRecord.getCsssShop().getNo());
    } else {
      shopNo.setCellValue("");
    }
    Cell xiaoshoubumen = row.createCell(6);
    xiaoshoubumen.setCellStyle(cellStyle);
    if (scannRecord.getCsssShop() != null) {
      xiaoshoubumen.setCellValue(scannRecord.getCsssShop().getName());
    } else {
      xiaoshoubumen.setCellValue("");
    }
    Cell staffName = row.createCell(7);
    staffName.setCellStyle(cellStyle);
    if (scannRecord.getStaffName() != null) {
      staffName.setCellValue(scannRecord.getStaffName());
    } else {
      staffName.setCellValue("");
    }
    Cell mobilePhone = row.createCell(8);
    mobilePhone.setCellStyle(cellStyle);
    if (scannRecord.getCsssStaff() != null)
    {
      CsssStaff csssStaff = scannRecord.getCsssStaff();
      if (csssStaff.getMobilePhone() != null) {
        mobilePhone.setCellValue(csssStaff.getMobilePhone());
      }
    }
    else
    {
      mobilePhone.setCellValue("");
    }
    Cell nikeNameCell = row.createCell(9);
    nikeNameCell.setCellStyle(cellStyle);
    if (scannRecord.getWeixinGzuserinfo() != null)
    {
      WeixinGzuserinfo weixinGzuserinfo = scannRecord.getWeixinGzuserinfo();
      if (weixinGzuserinfo != null) {
        nikeNameCell.setCellValue(weixinGzuserinfo.getNickname());
      }
    }
    else
    {
      nikeNameCell.setCellValue("");
    }
    Cell applyDate = row.createCell(10);
    CellStyle memberNameCell = applyDate.getCellStyle();
    memberNameCell.setAlignment((short)6);
    applyDate.setCellStyle(memberNameCell);
    applyDate.setCellStyle(cellStyle);
    if (scannRecord.getScannDate() != null) {
      applyDate.setCellValue(DateUtil.format(scannRecord.getScannDate()));
    } else {
      applyDate.setCellValue("");
    }
    Cell scannTypeCell = row.createCell(11);
    scannTypeCell.setCellStyle(cellStyle);
    if (scannRecord.getScannType() != null)
    {
      Integer scannType = scannRecord.getScannType();
      if (scannType.intValue() == 1) {
        scannTypeCell.setCellValue("关注扫描");
      }
      if (scannType.intValue() == 2) {
        scannTypeCell.setCellValue("多次扫描");
      }
    }
    else
    {
      scannTypeCell.setCellValue("");
    }
    return row;
  }
  
  public String getFilePath(String fileName)
  {
    String rootPath = PathUtil.getWebRootPath();
    rootPath = rootPath + System.getProperty("file.separator") + 
      "archives" + System.getProperty("file.separator") + 
      "excel";
    File filePath = new File(rootPath);
    if (!filePath.exists()) {
      try
      {
        filePath.mkdir();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    rootPath = rootPath + System.getProperty("file.separator") + fileName + "扫描记录.xls";
    return rootPath;
  }
  
  public static Sheet getSheet(Workbook wb, String sheetName)
  {
    Sheet sheet = wb.createSheet(sheetName);
    return sheet;
  }
  
  public Row getRowTitle(Sheet sheet, CellStyle cellStyle)
  {
    Row row = sheet.createRow(0);
    row.setHeightInPoints(32.0F);
    sheet.setColumnWidth(0, 1280);
    sheet.setColumnWidth(1, 4096);
    sheet.setColumnWidth(2, 5632);
    sheet.setColumnWidth(3, 4096);
    sheet.setColumnWidth(4, 7168);
    sheet.setColumnWidth(5, 4096);
    sheet.setColumnWidth(6, 7168);
    sheet.setColumnWidth(7, 4096);
    sheet.setColumnWidth(8, 4096);
    sheet.setColumnWidth(9, 4096);
    sheet.setColumnWidth(10, 4096);
    sheet.setColumnWidth(11, 4096);
    
    Cell xuehao = row.createCell(0);
    xuehao.setCellStyle(cellStyle);
    xuehao.setCellValue("序号");
    
    Cell xiaoshouwangdian = row.createCell(1);
    xiaoshouwangdian.setCellStyle(cellStyle);
    xiaoshouwangdian.setCellValue("省公司");
    
    Cell xiaoshoubumen = row.createCell(2);
    xiaoshoubumen.setCellStyle(cellStyle);
    xiaoshoubumen.setCellValue("省市公司描述");
    
    Cell chexingbumen = row.createCell(3);
    chexingbumen.setCellStyle(cellStyle);
    chexingbumen.setCellValue("地市公司");
    
    Cell carSeriyCell = row.createCell(4);
    carSeriyCell.setCellStyle(cellStyle);
    carSeriyCell.setCellValue("地市公司描述");
    
    Cell shopName = row.createCell(5);
    shopName.setCellStyle(cellStyle);
    shopName.setCellValue("便利店");
    
    Cell shopDes = row.createCell(6);
    shopDes.setCellStyle(cellStyle);
    shopDes.setCellValue("地市公司描述");
    
    Cell staff = row.createCell(7);
    staff.setCellStyle(cellStyle);
    staff.setCellValue("店员");
    
    Cell mobilePhone = row.createCell(8);
    mobilePhone.setCellStyle(cellStyle);
    mobilePhone.setCellValue("联系电话");
    
    Cell nikName = row.createCell(9);
    nikName.setCellStyle(cellStyle);
    nikName.setCellValue("昵称");
    
    Cell xiaoshouriqiCell = row.createCell(10);
    xiaoshouriqiCell.setCellStyle(cellStyle);
    xiaoshouriqiCell.setCellValue("扫描时间");
    
    Cell danganshangchuagnriqi = row.createCell(11);
    danganshangchuagnriqi.setCellStyle(cellStyle);
    danganshangchuagnriqi.setCellValue("扫描类型");
    
    return row;
  }
  
  public static CellStyle getTitleStyle(Workbook wb)
  {
    CellStyle cellStyle = wb.createCellStyle();
    Font font = wb.createFont();
    cellStyle.setFillBackgroundColor((short)18);
    cellStyle.setAlignment((short)2);
    cellStyle.setVerticalAlignment((short)1);
    font.setFontHeightInPoints((short)10);
    font.setBoldweight((short)700);
    cellStyle.setFont(font);
    
    cellStyle.setBorderTop((short)1);
    cellStyle.setBorderBottom((short)1);
    cellStyle.setBorderLeft((short)1);
    cellStyle.setBorderRight((short)1);
    
    cellStyle.setTopBorderColor((short)8);
    cellStyle.setBottomBorderColor((short)8);
    cellStyle.setLeftBorderColor((short)8);
    cellStyle.setRightBorderColor((short)8);
    
    cellStyle.setFillBackgroundColor((short)22);
    cellStyle.setFillForegroundColor((short)22);
    cellStyle.setFillPattern((short)1);
    return cellStyle;
  }
  
  public static CellStyle getContentStyle(Workbook wb)
  {
    CellStyle cellStyle = wb.createCellStyle();
    
    cellStyle.setBorderTop((short)1);
    cellStyle.setBorderBottom((short)1);
    cellStyle.setBorderLeft((short)1);
    cellStyle.setBorderRight((short)1);
    
    cellStyle.setTopBorderColor((short)8);
    cellStyle.setBottomBorderColor((short)8);
    cellStyle.setLeftBorderColor((short)8);
    cellStyle.setRightBorderColor((short)8);
    cellStyle.setAlignment((short)2);
    cellStyle.setVerticalAlignment((short)1);
    cellStyle.setWrapText(true);
    return cellStyle;
  }
  
  public static CellStyle getQuesionStyle(Workbook wb)
  {
    CellStyle cellStyle = wb.createCellStyle();
    
    cellStyle.setFillBackgroundColor((short)18);
    
    cellStyle.setAlignment((short)1);
    cellStyle.setBorderBottom((short)2);
    cellStyle.setBottomBorderColor((short)8);
    cellStyle.setBorderLeft((short)2);
    cellStyle.setLeftBorderColor((short)8);
    cellStyle.setBorderRight((short)2);
    cellStyle.setRightBorderColor((short)8);
    cellStyle.setBorderTop((short)2);
    cellStyle.setTopBorderColor((short)8);
    cellStyle.setWrapText(true);
    
    return cellStyle;
  }
}
