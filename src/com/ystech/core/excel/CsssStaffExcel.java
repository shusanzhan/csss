package com.ystech.core.excel;

import com.ystech.csss.model.CsssShop;
import com.ystech.csss.model.CsssStaff;
import com.ystech.csss.service.CsssShopManageImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CsssStaffExcel
{
  public static String[] headers = {"序号", "编号", "姓名", "性别", "移动电话", "邮箱", "店编号", "备注" };
  private CsssShopManageImpl csssShopManageImpl;
  
  public CsssStaffExcel(CsssShopManageImpl csssShopManageImpl)
  {
    this.csssShopManageImpl = csssShopManageImpl;
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
          if (lastRowNum > 1)
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
          if (lastRowNum > 1) {
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
  
  public List<StringBuffer> validateFactoryOrder(File file) throws IOException {
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
                errorMessage.append("编号称错误,");
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
                errorMessage.append("店员称错误,");
              }
              HSSFCell sexCell = row.getCell(3);
              Boolean sexStatus = Boolean.valueOf(false);
              if (sexCell != null)
              {
                String sexValue = sexCell.getStringCellValue();
                if ((sexValue != null) && (sexValue.trim().length() > 0)) {
                  sexStatus = Boolean.valueOf(true);
                }
              }
              if (!sexStatus.booleanValue()) {
                errorMessage.append("性别不能为空，");
              }
              HSSFCell mobilePhoneCell = row.getCell(4);
              Boolean mobilePhoneStatus = Boolean.valueOf(false);
              if (mobilePhoneCell != null)
              {
                mobilePhoneCell.setCellType(1);
                String cityValue = mobilePhoneCell.getStringCellValue();
                if (cityValue != null) {
                  mobilePhoneStatus = Boolean.valueOf(true);
                }
              }
              if (!mobilePhoneStatus.booleanValue()) {
                errorMessage.append("联系电话不能为空，");
              }
              HSSFCell shopCell = row.getCell(6);
              boolean shopStatus = false;
              if (shopCell != null)
              {
                String shopeName = shopCell.getStringCellValue();
                if (shopeName != null)
                {
                  List<CsssShop> csssShops = this.csssShopManageImpl.findBy("no", shopeName);
                  if ((csssShops != null) && (csssShops.size() > 0)) {
                    shopStatus = true;
                  }
                }
              }
              if (!shopStatus) {
                errorMessage.append("门店信息错误，请先填写门店信息后导入，");
              }
              if ((!shopStatus) || (!nameStatus.booleanValue()) || (!sexStatus.booleanValue()) || (!mobilePhoneStatus.booleanValue())) {
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
  
  public List<CsssStaff> getCsssStaff(File file)
    throws IOException
  {
    List<CsssStaff> csssStaffs = new ArrayList();
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
              CsssStaff csssStaff = new CsssStaff();
              HSSFRow row = sheet.getRow(rowJ);
              
              HSSFCell sqNo = row.getCell(0);
              if (sqNo == null) {
                break;
              }
              sqNo.setCellType(1);
              String errorSn = sqNo.getStringCellValue();
              if ((errorSn == null) || (errorSn.trim().length() <= 0)) {
                break;
              }
              HSSFCell noCell = row.getCell(1);
              if (noCell != null)
              {
                String noValue = noCell.getStringCellValue();
                if (noValue != null) {
                  csssStaff.setNo(noValue);
                }
              }
              HSSFCell nameCell = row.getCell(2);
              if (nameCell != null)
              {
                String nameValue = nameCell.getStringCellValue();
                if (nameValue != null) {
                  csssStaff.setName(nameValue);
                }
              }
              HSSFCell sexCell = row.getCell(3);
              Boolean sqrNoStatus = Boolean.valueOf(false);
              if (sexCell != null)
              {
                String sex = sexCell.getStringCellValue();
                if ((sex != null) && (sex.trim().length() > 0)) {
                  csssStaff.setSex(sex);
                }
              }
              HSSFCell mobilePhoneCell = row.getCell(4);
              if (mobilePhoneCell != null)
              {
                mobilePhoneCell.setCellType(1);
                String mobilePhone = mobilePhoneCell.getStringCellValue();
                if (mobilePhone != null) {
                  csssStaff.setMobilePhone(mobilePhone);
                }
              }
              HSSFCell emailCell = row.getCell(5);
              if (emailCell != null)
              {
                String email = emailCell.getStringCellValue();
                if (email != null) {
                  csssStaff.setEmail(email);
                }
              }
              else
              {
                csssStaff.setEmail("");
              }
              HSSFCell shopeCell = row.getCell(6);
              Boolean areaStrStatus = Boolean.valueOf(false);
              if (shopeCell != null)
              {
                String shopeName = shopeCell.getStringCellValue();
                if (shopeName != null)
                {
                  List<CsssShop> csssShops = this.csssShopManageImpl.findBy("no", shopeName);
                  if ((csssShops != null) && (csssShops.size() > 0)) {
                    csssStaff.setCsssShop((CsssShop)csssShops.get(0));
                  }
                }
              }
              HSSFCell noteCell = row.getCell(7);
              if (noteCell != null)
              {
                String noteValue = noteCell.getStringCellValue();
                if (noteValue != null) {
                  csssStaff.setNote(noteValue);
                }
              }
              csssStaffs.add(csssStaff);
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
