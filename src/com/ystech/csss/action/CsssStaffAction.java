package com.ystech.csss.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ystech.core.dao.Page;
import com.ystech.core.excel.CsssStaffExcel;
import com.ystech.core.img.FileNameUtil;
import com.ystech.core.security.SecurityUserHolder;
import com.ystech.core.util.DatabaseUnitHelper;
import com.ystech.core.util.DateUtil;
import com.ystech.core.util.Md5;
import com.ystech.core.util.ParamUtil;
import com.ystech.core.util.PathUtil;
import com.ystech.core.web.BaseController;
import com.ystech.csss.model.CsssShop;
import com.ystech.csss.model.CsssStaff;
import com.ystech.csss.service.CsssShopManageImpl;
import com.ystech.csss.service.CsssStaffManageImpl;
import com.ystech.csss.service.CsssStaffQrcodeUtil;
import com.ystech.weixin.core.util.WeixinUtil;
import com.ystech.weixin.model.WeixinAccesstoken;
import com.ystech.weixin.model.WeixinAccount;
import com.ystech.weixin.service.WeixinAccesstokenManageImpl;

@Component("csssStaffAction")
@Scope("prototype")
public class CsssStaffAction extends BaseController {
	private static String zipPath="/archives/zipPath";
	private File file;
	private String fileFileName;
	private String fileContentType;
	private String[] allowFiles = { ".xls", ".xlsx" };
	private int maxSize = 10240;
	private CsssStaffManageImpl csssStaffManageImpl;
	private CsssStaff csssStaff;
	private CsssShopManageImpl csssShopManageImpl;
	private WeixinAccesstokenManageImpl weixinAccesstokenManageImpl;

	public CsssStaff getCsssStaff() {
		return this.csssStaff;
	}

	public void setCsssStaff(CsssStaff csssStaff) {
		this.csssStaff = csssStaff;
	}

	@Resource
	public void setCsssStaffManageImpl(CsssStaffManageImpl csssStaffManageImpl) {
		this.csssStaffManageImpl = csssStaffManageImpl;
	}

	@Resource
	public void setCsssShopManageImpl(CsssShopManageImpl csssShopManageImpl) {
		this.csssShopManageImpl = csssShopManageImpl;
	}

	@Resource
	public void setWeixinAccesstokenManageImpl(WeixinAccesstokenManageImpl weixinAccesstokenManageImpl) {
		this.weixinAccesstokenManageImpl = weixinAccesstokenManageImpl;
	}

	public File getFile() {
		return this.file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return this.fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return this.fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String queryList() throws Exception {
		HttpServletRequest request = getRequest();
		Integer pageSize = ParamUtil.getIntParam(request, "pageSize", 10);
		Integer pageNo = ParamUtil.getIntParam(request, "currentPage", 1);
		Integer csssShopId = ParamUtil.getIntParam(request, "csssShopId", -1);
		try {
			List<CsssShop> csssShops = this.csssShopManageImpl.getAll();
			request.setAttribute("csssShops", csssShops);

			String sql = "select * from csss_staff where 1=1 ";
			List params = new ArrayList();
			if (csssShopId.intValue() > 0) {
				sql = sql + " and shopId=?";
				params.add(csssShopId);
			}
			Page<CsssStaff> page = this.csssStaffManageImpl.pagedQuerySql(pageNo.intValue(), pageSize.intValue(),
					CsssStaff.class, sql, params.toArray());
			request.setAttribute("page", page);
		} catch (Exception localException) {
		}
		return "list";
	}

	public String importExcel() throws Exception {
		HttpServletRequest request = getRequest();

		return "importExcel";
	}

	public String add() throws Exception {
		HttpServletRequest request = getRequest();
		try {
			List<CsssShop> csssShops = this.csssShopManageImpl.getAll();
			request.setAttribute("csssShops", csssShops);
		} catch (Exception localException) {
		}
		return "edit";
	}

	public String edit() throws Exception {
		HttpServletRequest request = getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		try {
			if (dbid.intValue() > 0) {
				CsssStaff csssStaff2 = (CsssStaff) this.csssStaffManageImpl.get(dbid);
				request.setAttribute("csssStaff", csssStaff2);
			}
			List<CsssShop> csssShops = this.csssShopManageImpl.getAll();
			request.setAttribute("csssShops", csssShops);
		} catch (Exception localException) {
		}
		return "edit";
	}

	public void save() throws Exception {
		HttpServletRequest request = getRequest();
		Integer csssShopId = ParamUtil.getIntParam(request, "csssShopId", -1);
		try {
			if (csssShopId.intValue() > 0) {
				CsssShop csssShop = (CsssShop) this.csssShopManageImpl.get(csssShopId);
				this.csssStaff.setCsssShop(csssShop);
			} else {
				renderErrorMsg(new Throwable("保存错误，请选择门店"), "");
				return;
			}
			Integer dbid = this.csssStaff.getDbid();
			if ((dbid == null) || (dbid.intValue() <= 0)) {
				String formatFile = DateUtil.formatFile(new java.util.Date());
				String calcMD5 = Md5.calcMD5(formatFile);
				List<CsssStaff> csssStaffs = this.csssStaffManageImpl.find("from CsssStaff where mobilePhone=? ",
						new Object[] { this.csssStaff.getMobilePhone() });
				if ((csssStaffs != null) && (csssStaffs.size() > 0)) {
					renderErrorMsg(new Throwable("店员已经存在，请勿重复添加"), "");
					return;
				}
				this.csssStaff.setCreateDate(new java.util.Date());
				this.csssStaff.setModifyDate(new java.util.Date());
				this.csssStaff.setLeaderNum(Integer.valueOf(0));
				this.csssStaff.setScannNum(Integer.valueOf(0));
				this.csssStaff.setSceneStr(calcMD5);
				this.csssStaff.setQrCodeStatus(CsssStaff.COMM);
				this.csssStaffManageImpl.save(csssStaff);
				renderMsg(""+csssStaff.getDbid(), "保存数据成功,正在生成员工二维码！");
			} else {
				CsssStaff csssStaff2 = (CsssStaff) this.csssStaffManageImpl.get(dbid);
				List<CsssStaff> csssStaffs = this.csssStaffManageImpl.find("from CsssStaff where mobilePhone=? ",
						new Object[] { this.csssStaff.getMobilePhone() });
				if ((csssStaffs != null) && (csssStaffs.size() > 0)) {
					CsssStaff csssStaff3 = (CsssStaff) csssStaffs.get(0);
					if (csssStaff3.getDbid().intValue() != csssStaff2.getDbid().intValue()) {
						renderErrorMsg(new Throwable("店员已经存在，请勿重复添加"), "");
						return;
					}
				}
				csssStaff2.setCsssShop(this.csssStaff.getCsssShop());
				csssStaff2.setEmail(this.csssStaff.getEmail());
				csssStaff2.setMobilePhone(this.csssStaff.getMobilePhone());
				csssStaff2.setModifyDate(new java.util.Date());
				csssStaff2.setName(this.csssStaff.getName());
				csssStaff2.setNote(this.csssStaff.getNote());
				csssStaff2.setNo(this.csssStaff.getNo());
				csssStaffManageImpl.save(csssStaff2);
				renderMsg("/csssStaff/queryList", "更新数据成功！");
			}
		} catch (Exception e) {
			this.log.error(e);
			e.printStackTrace();
			renderErrorMsg(e, "");
			return;
		}
	}

	public void delete() throws Exception {
		HttpServletRequest request = getRequest();
		Integer[] dbids = ParamUtil.getIntArraryByDbids(request, "dbids");
		if ((dbids != null) && (dbids.length > 0)) {
			try {
				Integer[] arrayOfInteger1;
				int j = (arrayOfInteger1 = dbids).length;
				for (int i = 0; i < j; i++) {
					Integer dbid = arrayOfInteger1[i];
					this.csssStaffManageImpl.deleteById(dbid);
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.log.error(e);
				renderErrorMsg(e, "");
				return;
			}
		} else {
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/csssStaff/queryList" + query, "删除数据成功！");
	}

	public String saveImportExcel() throws Exception {
		HttpServletRequest request = getRequest();
		File dataFile = null;
		DatabaseUnitHelper databaseUnitHelper = new DatabaseUnitHelper();
		Connection jdbcUpdate = databaseUnitHelper.getJdbcConnection();
		jdbcUpdate.setAutoCommit(false);
		String insertSeql = "INSERT INTO `csss_staff` \t(name,sex,mobilePhone,email,shopId,createDate,modifyDate,qrCodeStatus,qrCode,qrCodeDate,scannNum,leaderNum,note,sceneStr,ticket,no)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement prepareStatement = jdbcUpdate.prepareStatement(insertSeql);
		try {
			Long startTime = Long.valueOf(System.currentTimeMillis());
			if ((this.file != null) && (!this.file.getName().trim().equals(""))) {
				dataFile = FileNameUtil.getResourceFile(this.fileFileName);
				FileUtils.copyFile(this.file, dataFile);
				boolean checkFileType = checkFileType(dataFile.getName());
				if (checkFileType) {
					CsssStaffExcel csssStaffExcel = new CsssStaffExcel(this.csssShopManageImpl);
					boolean validateDocument = CsssStaffExcel.validateDocument(dataFile);
					if (validateDocument) {
						boolean validateForm = CsssStaffExcel.validateForm(dataFile);
						if (validateForm) {
							List<StringBuffer> errorMessges = csssStaffExcel.validateFactoryOrder(dataFile);
							Object sdf;
							if ((errorMessges == null) || (errorMessges.size() <= 0)) {
								List<CsssStaff> csssStaffs = csssStaffExcel.getCsssStaff(dataFile);
								for (CsssStaff csssStaff : csssStaffs) {
									prepareStatement.setString(1, csssStaff.getName());
									prepareStatement.setString(2, csssStaff.getSex());
									prepareStatement.setString(3, csssStaff.getMobilePhone());
									prepareStatement.setString(4, csssStaff.getEmail());
									prepareStatement.setInt(5, csssStaff.getCsssShop().getDbid().intValue());
									prepareStatement.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
									prepareStatement.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
									prepareStatement.setInt(8, CsssStaff.COMM.intValue());
									prepareStatement.setString(9, null);
									prepareStatement.setString(10, null);
									prepareStatement.setInt(11, 0);
									prepareStatement.setInt(12, 0);
									prepareStatement.setString(13, csssStaff.getNote());
									prepareStatement.setString(14, csssStaff.getSceneStr());
									prepareStatement.setString(15, csssStaff.getTicket());
									prepareStatement.setString(16, csssStaff.getNo());
									prepareStatement.addBatch();
								}
								prepareStatement.executeBatch();
								jdbcUpdate.commit();

								prepareStatement.close();
								jdbcUpdate.close();
								Long endTime = Long.valueOf(System.currentTimeMillis());
								sdf = new SimpleDateFormat("HH:mm:ss:SS");
								System.out.println("用时：" + ((SimpleDateFormat) sdf)
										.format(new java.util.Date(endTime.longValue() - startTime.longValue())));
							} else {
								String str = "<table>";
								for (sdf = errorMessges.iterator(); ((Iterator) sdf).hasNext();) {
									StringBuffer string = (StringBuffer) ((Iterator) sdf).next();
									str = str + "<tr><td>" + string.toString() + "</td></tr>";
								}
								str = str + "</table>";
								request.setAttribute("error", str);
								return "error";
							}
						} else {
							request.setAttribute("error", "文件模块错误!");
							return "error";
						}
					} else {
						request.setAttribute("error", "文件内容为空!");
						return "error";
					}
				} else {
					request.setAttribute("error", "上传文件类型错误!");
					return "error";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "上传失败!");
			return "error";
		}
		if ((dataFile == null) && (!dataFile.exists())) {
			request.setAttribute("error", "上传失败!");
			return "error";
		}
		request.setAttribute("success", "上传数据成功!");
		return "success";
	}

	public void createQrCode() throws Exception {
		HttpServletRequest request = getRequest();
		Integer csssStaffDbid = ParamUtil.getIntParam(request, "csssStaffDbid", -1);
		try {
			CsssStaff csssStaff2 = (CsssStaff) this.csssStaffManageImpl.get(csssStaffDbid);
			if ((csssStaff2 == null) || (csssStaff2.getDbid().intValue() < 0)) {
				renderMsg("/csssStaff/queryList", "生成二维码失败，无店员信息！");
				return;
			}
			String sceneStr = csssStaff2.getSceneStr();
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();

			WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(this.weixinAccesstokenManageImpl,
					weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
			String ticket = CsssStaffQrcodeUtil.getQrCodeTicket(accessToken, sceneStr);
			if ((ticket == null) || (ticket.trim().length() < 0)) {
				renderMsg("/csssStaff/queryList", "生成二维码失败，微信端发生错误！");
				return;
			}
			csssStaff2.setTicket(ticket);
			csssStaff2.setSceneStr(sceneStr);
			String ticketEncode = URLEncoder.encode(ticket, "UTF-8");
			String qrCode = CsssStaffQrcodeUtil.getQrCodePath(ticketEncode, sceneStr);
			csssStaff2.setQrCode(qrCode);
			csssStaff2.setQrCodeStatus(CsssStaff.YEAS);
			csssStaff2.setQrCodeDate(new java.util.Date());
			this.csssStaffManageImpl.save(csssStaff2);
		} catch (Exception e) {
			e.printStackTrace();
			this.log.error(e);
			renderMsg("/csssStaff/queryList", "生成二维码失败，微信端发生错误！");
			return;
		}
		renderMsg("/csssStaff/queryList", "生成二维码成功");
		return ;
	}

	public void createQrCodeByDbid() throws Exception {
		HttpServletRequest request = getRequest();
		Integer csssStaffDbid = ParamUtil.getIntParam(request, "csssStaffDbid", -1);
		try {
			CsssStaff csssStaff2 = (CsssStaff) this.csssStaffManageImpl.get(csssStaffDbid);
			if ((csssStaff2 == null) || (csssStaff2.getDbid().intValue() < 0)) {
				renderErrorMsg(new Throwable("生成二维码失败，无店员信息！"), "");
				return;
			}
			String sceneStr = csssStaff2.getSceneStr();
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();

			WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(this.weixinAccesstokenManageImpl,
					weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
			String ticket = CsssStaffQrcodeUtil.getQrCodeTicket(accessToken, sceneStr);
			if ((ticket == null) || (ticket.trim().length() < 0)) {
				renderErrorMsg(new Throwable("生成二维码失败，微信端发生错误！"), "");
				return;
			}
			csssStaff2.setTicket(ticket);
			csssStaff2.setSceneStr(sceneStr);
			String ticketEncode = URLEncoder.encode(ticket, "UTF-8");
			String qrCode = CsssStaffQrcodeUtil.getQrCodePath(ticketEncode, sceneStr);
			csssStaff2.setQrCodeStatus(CsssStaff.YEAS);
			csssStaff2.setQrCode(qrCode);
			csssStaff2.setQrCodeDate(new java.util.Date());
			this.csssStaffManageImpl.save(csssStaff2);
		} catch (Exception e) {
			e.printStackTrace();
			this.log.error(e);
			renderErrorMsg(new Throwable("生成二维码失败，微信端发生错误！"), "");
			return;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/csssStaff/queryList" + query, "生成二维码成功");
	}

	private boolean checkFileType(String fileName) {
		Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
		while (type.hasNext()) {
			String ext = (String) type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 功能描述：批量生成二维码
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void qrBatchCreate() throws Exception {
		try {
			List<CsssStaff> csssStaffs = csssStaffManageImpl.findBy("qrCodeStatus", CsssStaff.COMM);
			if(null==csssStaffs||csssStaffs.size()<=0){
				log.error("无店员需要生成二维码");
				renderErrorMsg(new Throwable("无店员需要生成二维码！"), "");
			}
			for (CsssStaff csssStaff : csssStaffs) {
				String sceneStr = csssStaff.getSceneStr();
				WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();

				WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(this.weixinAccesstokenManageImpl,	weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
				String ticket = CsssStaffQrcodeUtil.getQrCodeTicket(accessToken, sceneStr);
				if ((ticket == null) || (ticket.trim().length() < 0)) {
					renderErrorMsg(new Throwable("生成二维码失败，微信端发生错误！"), "");
					return;
				}
				csssStaff.setTicket(ticket);
				csssStaff.setSceneStr(sceneStr);
				String ticketEncode = URLEncoder.encode(ticket, "UTF-8");
				String qrCode = CsssStaffQrcodeUtil.getQrCodePath(ticketEncode, sceneStr);
				csssStaff.setQrCodeStatus(CsssStaff.YEAS);
				csssStaff.setQrCode(qrCode);
				csssStaff.setQrCodeDate(new java.util.Date());
				csssStaffManageImpl.save(csssStaff);
			}
		} catch (Exception e) {
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("", "批量生产二维码成功！");
		return;
	}
	/**
	 * 功能描述：打包下载二维码
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void download() throws Exception {
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		Integer csssShopId = ParamUtil.getIntParam(request, "csssShopId", -1);
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Integer type = ParamUtil.getIntParam(request, "type", -1);
		String fileName="";
		try {
			if(null!=startTime&&startTime.trim().length()>0){
				fileName=fileName+""+startTime;
			}
			
			if(null!=endTime&&endTime.trim().length()>0){
				fileName=fileName+"_"+endTime;
			}else{
				fileName=fileName+"_"+DateUtil.format(new Date());
			}
			List<CsssShop> csssShops = this.csssShopManageImpl.getAll();
			request.setAttribute("csssShops", csssShops);

			String sql = "select * from csss_staff where 1=1 ";
			List params = new ArrayList();
			if (csssShopId.intValue() > 0) {
				sql = sql + " and shopId=?";
				params.add(csssShopId);
			}
			List<CsssStaff> csssStaffs = csssStaffManageImpl.executeSql(sql, params.toArray());
			log.error("数据查询成功");
			String zipFilePath=zipPath;
			log.error("数据打包下载开始");
			boolean fileToZip = fileToZipByImage(csssStaffs, zipFilePath, fileName);
			log.error("数据打包结果"+fileToZip);
			if(fileToZip==true){
				String filePath=zipPath+"/"+fileName+".zip";
				log.error("开始下载文件："+filePath);
				downFile(request, response, filePath);
				log.error("下载文件结束");
				//删除文件夹
				File file=new File(filePath);
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	 /** 
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下 
     * @param sourceFilePath :待压缩的文件路径 
     * @param zipFilePath :压缩后存放路径 
     * @param fileName :压缩后文件的名称 
     * @return 
     */  
    public boolean fileToZipByImage(List<CsssStaff> csssStaffs,String zipFilePath,String fileName){  
        boolean flag = false;  
        FileInputStream fis = null;  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
        if(null==csssStaffs||csssStaffs.size()<=0){
        	log.error("该客户还未上传照片信息.");  
        }
        try {  
        	File file = new File(zipFilePath);
			if (!file.exists()) {
				FileUtils.forceMkdir(file);
			}
            File zipFile = new File(zipFilePath + "/" + fileName +".zip");  
            if(zipFile.exists()){  
            	log.error(zipFilePath + "目录下存在名字为:" + fileName +".zip" +"打包文件.");  
                return true;
            }else{  
            		File[] sourceFiles = new File[csssStaffs.size()];
            		int j=0;
            		for (CsssStaff csssStaff : csssStaffs) {
            			String immPath=PathUtil.getWebRootPath();
            			File image=new File(immPath+"/"+csssStaff.getQrCode());
            			//获取重命名文件
            			File reNameFile = qrRename(csssStaff, immPath);
            			//复制文件
            			String message = copyFile(image, reNameFile);
            			log.error("文件"+j+":"+message);
            			
						sourceFiles[j]=reNameFile;
						j++;
					}
                    fos = new FileOutputStream(zipFile);  
                    zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                    byte[] bufs = new byte[1024*10];  
                    for(int i=0;i<sourceFiles.length;i++){  
                        //创建ZIP实体，并添加进压缩包  new String(name.getBytes(), "utf-8")
                        ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                        zos.putNextEntry(zipEntry);  
                        //读取待压缩的文件并写进压缩包里  
                        fis = new FileInputStream(sourceFiles[i]);  
                        bis = new BufferedInputStream(fis, 1024*10);  
                        int read = 0;  
                        while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                            zos.write(bufs,0,read);  
                        }  
                    }  
                    zos.setEncoding("gbk");
                    zos.closeEntry();
                    zos.close();
                    flag = true;  
            }
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        } finally{  
            //关闭流  
            try {  
                if(null != bis) bis.close();  
                if(null != zos) zos.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            }  
        }  
        return flag;  
    }
    
    /**
     * 功能描述：重命名二维码名称
     * @param csssStaff
     * @param immPath
     * 命名规则：店铺编号+员工编号
     * @return
     */
	private File qrRename(CsssStaff csssStaff, String immPath) {
		//店铺编号
		String shopNo="MW00";
		CsssShop csssShop = csssStaff.getCsssShop();
		if(null!=csssShop){
			if(null!=csssShop.getNo()){
				shopNo=csssShop.getNo();
			}
		}
		
		//员工编号
		String csssStaffNo = csssStaff.getNo();
		if(null==csssStaffNo){
			csssStaffNo=csssStaff.getName();
		}
		if(null==csssStaffNo){
			csssStaffNo=DateUtil.generatedName(new Date());
		}
		File immpathDir=new File(immPath);
		if(immpathDir.exists()){
			immpathDir.mkdirs();
		}
		File reNameFile=new File(immPath+System.getProperty("file.separator")+shopNo+"_"+csssStaffNo+".jpg");
		return reNameFile;
	}  

	/**
     * 功能描述：复制数据
     * @param sourceFile
     * @param copyFile
     * @return
     */
    private String copyFile(File sourceFile,File copyFile){
    	String mes="复制数据成功";
    	try {
    		 FileInputStream is = new FileInputStream(sourceFile);  
             FileOutputStream os = new FileOutputStream(copyFile); 
             byte[] buf = new byte[1024];
             while (is.read(buf) != -1) {  
                 os.write(buf);  
             }  
		} catch (Exception e) {
			mes="复制数据失败："+e;
		}
    	return mes;
    }
}
