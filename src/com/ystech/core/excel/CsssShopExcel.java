package com.ystech.core.excel;

import com.ystech.csss.model.CityShop;
import com.ystech.csss.model.CsssShop;
import com.ystech.csss.service.CityShopManageImpl;
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

public class CsssShopExcel
{
  public static String[] headers = {
    "序号", "编号", "店名", "省", "市", "区", "地址", "地市公司编号", "备注" };
  private AreaManageImpl areaManageImpl;
  private CityShopManageImpl cityShopManageImpl;
  
  public CsssShopExcel(AreaManageImpl areaManageImpl, CityShopManageImpl cityShopManageImpl)
  {
    this.areaManageImpl = areaManageImpl;
    this.cityShopManageImpl = cityShopManageImpl;
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
                errorMessage.append("店面编号错误,");
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
                errorMessage.append("店面名称错误,");
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
                errorMessage.append("店面所在省份错误，");
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
                errorMessage.append("店面所在城市错误，");
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
              if (!areaStrStatus.booleanValue()) {
                errorMessage.append("店面所在区域错误，");
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
                  List<CityShop> cityShops = this.cityShopManageImpl.findBy("no", cityShopCellValue);
                  if ((cityShops != null) && (cityShops.size() > 0)) {
                    cityShopStatus = true;
                  }
                }
              }
              if (!cityShopStatus) {
                errorMessage.append("地市公司编号不能为空，");
              }
              if ((!noStatus.booleanValue()) || (!nameStatus.booleanValue()) || (!sqrNoStatus.booleanValue()) || (!cityShopStatus)) {
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
  
  public List<CsssShop> getCsssShop(File file, int lastNum)
    throws IOException
  {
    List<CsssShop> csssStaffs = new ArrayList();
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
              CsssShop csssShop = new CsssShop();
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
              if (noCell != null)
              {
                String noValue = noCell.getStringCellValue();
                if (noValue != null) {
                  csssShop.setNo(noValue);
                }
              }
              HSSFCell carSeriyCell = row.getCell(2);
              Boolean nameStatus = Boolean.valueOf(false);
              if (carSeriyCell != null)
              {
                String carSeriyCellValue = carSeriyCell.getStringCellValue();
                if (carSeriyCellValue != null) {
                  csssShop.setName(carSeriyCellValue);
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
                    csssShop.setProvice(((Area)areas.get(0)).getName());
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
                    csssShop.setCity(((Area)areas.get(0)).getName());
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
                    csssShop.setAreaStr(((Area)areas.get(0)).getName());
                  }
                }
              }
              HSSFCell addressCell = row.getCell(6);
              boolean addressStatus = false;
              if (addressCell != null)
              {
                String addressValue = addressCell.getStringCellValue();
                if (addressValue != null) {
                  csssShop.setAddress(addressValue);
                }
              }
              HSSFCell cityShopCell = row.getCell(7);
              if (cityShopCell != null)
              {
                String cityShopCellValue = cityShopCell.getStringCellValue();
                if (cityShopCellValue != null)
                {
                  List<CityShop> cityShops = this.cityShopManageImpl.findBy("no", cityShopCellValue);
                  if ((cityShops != null) && (cityShops.size() > 0))
                  {
                    CityShop cityShop = (CityShop)cityShops.get(0);
                    csssShop.setCityShop(cityShop);
                    if (cityShop.getProviceShop() != null) {
                      csssShop.setProviceShop(cityShop.getProviceShop());
                    }
                  }
                }
              }
              HSSFCell noteCell = row.getCell(8);
              if (noteCell != null)
              {
                String noteValue = noteCell.getStringCellValue();
                if (noteValue != null) {
                  csssShop.setNote(noteValue);
                }
              }
              int last = rowJ + lastNum;
              csssShop.setNum(Integer.valueOf(last));
              csssShop.setCreateDate(new Date());
              csssShop.setModifyDate(new Date());
              csssStaffs.add(csssShop);
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
    return csssStaffs;
  }
}
