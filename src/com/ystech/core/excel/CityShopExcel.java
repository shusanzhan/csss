package com.ystech.core.excel;

import com.ystech.csss.model.CityShop;
import com.ystech.csss.model.ProviceShop;
import com.ystech.csss.service.ProviceShopManageImpl;
import com.ystech.xwqr.model.sys.Area;
import com.ystech.xwqr.service.sys.AreaManageImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CityShopExcel
{
  public static String[] headers = {
    "序号", "编号", "店名", "省", "市", "区", "地址", "省公司编号", "备注" };
  private AreaManageImpl areaManageImpl;
  private ProviceShopManageImpl proviceShopManageImpl;
  
  public CityShopExcel(AreaManageImpl areaManageImpl, ProviceShopManageImpl proviceShopManageImpl)
  {
    this.areaManageImpl = areaManageImpl;
    this.proviceShopManageImpl = proviceShopManageImpl;
  }
  
  public static boolean validateDocument(File file)
  {
    try
    {
      InputStream is = new FileInputStream(file);
      HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
      int sheehtsNum = hssfWorkbook.getNumberOfSheets();
      if (sheehtsNum > 0)
      {
        boolean state = false;
        for (int i = 0; i < sheehtsNum; i++)
        {
          HSSFSheet sheet = hssfWorkbook.getSheetAt(i);
          int lastRowNum = sheet.getLastRowNum();
          if (lastRowNum >= 1)
          {
            state = true;
            break;
          }
          state = false;
        }
        if (state) {
          return true;
        }
        return false;
      }
      System.out.println("文档内容为空！");
      return false;
    }
    catch (FileNotFoundException e)
    {
      System.out.println("找不到文档！");
      e.printStackTrace();
      return false;
    }
    catch (IOException e)
    {
      System.out.println("文档IO发送错误！");
    }
    return false;
  }
  
  public static boolean validateForm(File file)
    throws IOException
  {
    try
    {
      InputStream is = new FileInputStream(file);
      HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
      int sheehtsNum = hssfWorkbook.getNumberOfSheets();
      if (sheehtsNum > 0)
      {
        for (int i = 0; i < sheehtsNum; i++)
        {
          HSSFSheet sheet = hssfWorkbook.getSheetAt(i);
          int lastRowNum = sheet.getLastRowNum();
          if (lastRowNum >= 1) {
            for (int rowJ = 0; rowJ < lastRowNum; rowJ++)
            {
              if (rowJ >= 1) {
                break;
              }
              HSSFRow row = sheet.getRow(rowJ);
              short lastCellNum = row.getLastCellNum();
              if (lastCellNum == headers.length)
              {
                for (int colJ = 0; colJ < lastCellNum; colJ++)
                {
                  HSSFCell cell = row.getCell(colJ);
                  String value = cell.getStringCellValue();
                  for (int headerJ = 0; headerJ < lastCellNum; headerJ++) {
                    if (value.equals(headers[headerJ])) {
                      break;
                    }
                  }
                }
              }
              else
              {
                System.out.println("sheet" + rowJ + "中文档模板的头部不一致！");
                return false;
              }
            }
          } else {
            System.out.println("sheet" + i + "为空！");
          }
        }
      }
      else
      {
        System.out.println("文档内容为空！");
        return false;
      }
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    return true;
  }
  
  public List<StringBuffer> validateFactoryOrder(File file)
    throws IOException
  {
    List<StringBuffer> errorMessages = new ArrayList();
    try
    {
      InputStream is = new FileInputStream(file);
      HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
      int sheehtsNum = hssfWorkbook.getNumberOfSheets();
      if (sheehtsNum > 0) {
        for (int i = 0; i < sheehtsNum; i++)
        {
          HSSFSheet sheet = hssfWorkbook.getSheetAt(i);
          int lastRowNum = sheet.getLastRowNum();
          if (lastRowNum >= 1) {
            for (int rowJ = 1; rowJ <= lastRowNum; rowJ++)
            {
              StringBuffer errorMessage = new StringBuffer();
              HSSFRow row = sheet.getRow(rowJ);
              
              HSSFCell nameCell = row.getCell(0);
              if (nameCell == null) {
                break;
              }
              nameCell.setCellType(1);
              String errorSn = nameCell.getStringCellValue();
              if ((errorSn == null) || (errorSn.trim().length() <= 0)) {
                break;
              }
              errorMessage.append("第" + errorSn + "行:");
              
              HSSFCell noCell = row.getCell(1);
              Boolean noStatus = Boolean.valueOf(false);
              if (noCell != null)
              {
                String noValue = noCell.getStringCellValue();
                if (noValue != null) {
                  noStatus = Boolean.valueOf(true);
                }
              }
              if (!noStatus.booleanValue()) {
                errorMessage.append("地市公司编号错误,");
              }
              HSSFCell carSeriyCell = row.getCell(2);
              Boolean nameStatus = Boolean.valueOf(false);
              if (carSeriyCell != null)
              {
                String carSeriyCellValue = carSeriyCell.getStringCellValue();
                if (carSeriyCellValue != null) {
                  nameStatus = Boolean.valueOf(true);
                }
              }
              if (!nameStatus.booleanValue()) {
                errorMessage.append("地市公司名称错误,");
              }
              HSSFCell sqrNoCell = row.getCell(3);
              Boolean sqrNoStatus = Boolean.valueOf(false);
              if (sqrNoCell != null)
              {
                String sqrNoCellValue = sqrNoCell.getStringCellValue();
                if ((sqrNoCellValue != null) && (sqrNoCellValue.trim().length() > 0))
                {
                  List<Area> areas = this.areaManageImpl.findBy("name", sqrNoCellValue);
                  if ((areas != null) && (areas.size() > 0)) {
                    sqrNoStatus = Boolean.valueOf(true);
                  }
                }
              }
              if (!sqrNoStatus.booleanValue()) {
                errorMessage.append("地市公司所在省份错误，");
              }
              HSSFCell cityCel = row.getCell(4);
              Boolean cityStatus = Boolean.valueOf(false);
              if (cityCel != null)
              {
                String cityValue = cityCel.getStringCellValue();
                if (cityValue != null)
                {
                  List<Area> areas = this.areaManageImpl.findBy("name", cityValue);
                  if ((areas != null) && (areas.size() > 0)) {
                    cityStatus = Boolean.valueOf(true);
                  }
                }
              }
              if (!cityStatus.booleanValue()) {
                errorMessage.append("地市公司所在城市错误，");
              }
              HSSFCell areaStrCell = row.getCell(5);
              Boolean areaStrStatus = Boolean.valueOf(false);
              if (areaStrCell != null)
              {
                String areaStrValue = areaStrCell.getStringCellValue();
                if (areaStrValue != null)
                {
                  List<Area> areas = this.areaManageImpl.findBy("name", areaStrValue);
                  if ((areas != null) && (areas.size() > 0)) {
                    areaStrStatus = Boolean.valueOf(true);
                  }
                }
              }
              HSSFCell addressCell = row.getCell(6);
              boolean addressStatus = false;
              if (addressCell != null)
              {
                String addressValue = addressCell.getStringCellValue();
                if (addressValue != null) {
                  addressStatus = true;
                }
              }
              if (!addressStatus) {
                errorMessage.append("门店所在地址不能为空，");
              }
              HSSFCell cityShopCell = row.getCell(7);
              boolean cityShopStatus = false;
              if (cityShopCell != null)
              {
                String cityShopCellValue = cityShopCell.getStringCellValue();
                if (cityShopCellValue != null)
                {
                  List<ProviceShop> proviceShops = this.proviceShopManageImpl.findBy("no", cityShopCellValue);
                  if ((proviceShops != null) && (proviceShops.size() > 0)) {
                    cityShopStatus = true;
                  }
                }
              }
              if (!cityShopStatus) {
                errorMessage.append("地市公司编号不能为空，");
              }
              if ((!noStatus.booleanValue()) || (!nameStatus.booleanValue()) || (!sqrNoStatus.booleanValue()) || (!cityStatus.booleanValue()) || (!cityShopStatus)) {
                errorMessages.add(errorMessage);
              }
            }
          } else {
            System.out.println("sheet" + i + "为空！");
          }
        }
      } else {
        System.out.println("文档内容为空！");
      }
      is.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    return errorMessages;
  }
  
  public List<CityShop> getCityShop(File file, int lastNum)
    throws IOException
  {
    List<CityShop> cityShops = new ArrayList();
    try
    {
      InputStream is = new FileInputStream(file);
      HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
      int sheehtsNum = hssfWorkbook.getNumberOfSheets();
      if (sheehtsNum > 0) {
        for (int i = 0; i < sheehtsNum; i++)
        {
          HSSFSheet sheet = hssfWorkbook.getSheetAt(i);
          int lastRowNum = sheet.getLastRowNum();
          if (lastRowNum >= 1) {
            for (int rowJ = 1; rowJ <= lastRowNum; rowJ++)
            {
              CityShop cityShop = new CityShop();
              HSSFRow row = sheet.getRow(rowJ);
              
              HSSFCell nameCell = row.getCell(0);
              if (nameCell == null) {
                break;
              }
              nameCell.setCellType(1);
              String errorSn = nameCell.getStringCellValue();
              if ((errorSn == null) || (errorSn.trim().length() <= 0)) {
                break;
              }
              HSSFCell noCell = row.getCell(1);
              Boolean noStatus = Boolean.valueOf(false);
              if (noCell != null)
              {
                String noValue = noCell.getStringCellValue();
                if (noValue != null) {
                  cityShop.setNo(noValue);
                }
              }
              HSSFCell carSeriyCell = row.getCell(2);
              Boolean nameStatus = Boolean.valueOf(false);
              if (carSeriyCell != null)
              {
                String carSeriyCellValue = carSeriyCell.getStringCellValue();
                if (carSeriyCellValue != null) {
                  cityShop.setName(carSeriyCellValue);
                }
              }
              HSSFCell sqrNoCell = row.getCell(3);
              Boolean sqrNoStatus = Boolean.valueOf(false);
              if (sqrNoCell != null)
              {
                String sqrNoCellValue = sqrNoCell.getStringCellValue();
                if ((sqrNoCellValue != null) && (sqrNoCellValue.trim().length() > 0))
                {
                  List<Area> areas = this.areaManageImpl.findBy("name", sqrNoCellValue);
                  if ((areas != null) && (areas.size() > 0)) {
                    cityShop.setProvice(((Area)areas.get(0)).getName());
                  }
                }
              }
              HSSFCell cityCel = row.getCell(4);
              Boolean cityStatus = Boolean.valueOf(false);
              if (cityCel != null)
              {
                String cityValue = cityCel.getStringCellValue();
                if (cityValue != null)
                {
                  List<Area> areas = this.areaManageImpl.findBy("name", cityValue);
                  if ((areas != null) && (areas.size() > 0)) {
                    cityShop.setCity(((Area)areas.get(0)).getName());
                  }
                }
              }
              HSSFCell areaStrCell = row.getCell(5);
              Boolean areaStrStatus = Boolean.valueOf(false);
              if (areaStrCell != null)
              {
                String areaStrValue = areaStrCell.getStringCellValue();
                if (areaStrValue != null)
                {
                  List<Area> areas = this.areaManageImpl.findBy("name", areaStrValue);
                  if ((areas != null) && (areas.size() > 0)) {
                    cityShop.setAreaStr(((Area)areas.get(0)).getName());
                  }
                }
              }
              HSSFCell addressCell = row.getCell(6);
              boolean addressStatus = false;
              if (addressCell != null)
              {
                String addressValue = addressCell.getStringCellValue();
                if (addressValue != null) {
                  cityShop.setAddress(addressValue);
                }
              }
              HSSFCell cityShopCell = row.getCell(7);
              if (cityShopCell != null)
              {
                String cityShopCellValue = cityShopCell.getStringCellValue();
                if (cityShopCellValue != null)
                {
                  List<ProviceShop> proviceShops = this.proviceShopManageImpl.findBy("no", cityShopCellValue);
                  if ((proviceShops != null) && (proviceShops.size() > 0))
                  {
                    ProviceShop proviceShop = (ProviceShop)proviceShops.get(0);
                    cityShop.setProviceShop(proviceShop);
                  }
                }
              }
              HSSFCell noteCell = row.getCell(8);
              if (noteCell != null)
              {
                String noteValue = noteCell.getStringCellValue();
                if (noteValue != null) {
                  cityShop.setNote(noteValue);
                }
              }
              int last = rowJ + lastNum;
              cityShop.setNum(Integer.valueOf(last));
              cityShop.setCreateDate(new Date());
              cityShop.setModifyDate(new Date());
              cityShops.add(cityShop);
            }
          } else {
            System.out.println("sheet" + i + "为空！");
          }
        }
      } else {
        System.out.println("文档内容为空！");
      }
      is.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    return cityShops;
  }
}
