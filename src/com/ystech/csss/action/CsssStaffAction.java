package com.ystech.csss.action;

import java.io.File;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
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
				this.csssStaffManageImpl.save(this.csssStaff);
				renderMsg("/csssStaff/queryList", "保存数据成功,正在生成员工二维码！");
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
				this.csssStaffManageImpl.save(csssStaff2);
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
}
