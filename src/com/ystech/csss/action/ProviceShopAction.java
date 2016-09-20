package com.ystech.csss.action;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ystech.core.dao.Page;
import com.ystech.core.excel.ProviceShopExcel;
import com.ystech.core.img.FileNameUtil;
import com.ystech.core.util.DatabaseUnitHelper;
import com.ystech.core.util.ParamUtil;
import com.ystech.core.web.BaseController;
import com.ystech.csss.model.CityShop;
import com.ystech.csss.model.ProviceShop;
import com.ystech.csss.service.CityShopManageImpl;
import com.ystech.csss.service.ProviceShopManageImpl;
import com.ystech.xwqr.model.sys.Area;
import com.ystech.xwqr.service.sys.AreaManageImpl;

import net.sf.json.JSONObject;

@Component("proviceShopAction")
@Scope("prototype")
public class ProviceShopAction extends BaseController {
	private File file;
	private String fileFileName;
	private String fileContentType;
	private String[] allowFiles = { ".xls", ".xlsx" };
	private int maxSize = 10240;
	private ProviceShopManageImpl proviceShopManageImpl;
	private CityShopManageImpl cityShopManageImpl;
	private AreaManageImpl areaManageImpl;
	private ProviceShop proviceShop;

	public ProviceShop getProviceShop() {
		return this.proviceShop;
	}

	public void setProviceShop(ProviceShop proviceShop) {
		this.proviceShop = proviceShop;
	}

	@Resource
	public void setProviceShopManageImpl(ProviceShopManageImpl proviceShopManageImpl) {
		this.proviceShopManageImpl = proviceShopManageImpl;
	}

	@Resource
	public void setAreaManageImpl(AreaManageImpl areaManageImpl) {
		this.areaManageImpl = areaManageImpl;
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

	@Resource
	public void setCityShopManageImpl(CityShopManageImpl cityShopManageImpl) {
		this.cityShopManageImpl = cityShopManageImpl;
	}

	public String queryList() throws Exception {
		HttpServletRequest request = getRequest();
		Integer pageSize = ParamUtil.getIntParam(request, "pageSize", 10);
		Integer pageNo = ParamUtil.getIntParam(request, "currentPage", 1);
		String username = request.getParameter("title");
		try {
			Page<ProviceShop> page = this.proviceShopManageImpl.pagedQuerySql(pageNo.intValue(), pageSize.intValue(),
					ProviceShop.class, "select * from csss_ProviceShop order by num ", new Object[0]);
			request.setAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}

	public String add() throws Exception {
		HttpServletRequest request = getRequest();
		try {
			List<Area> provices = this.areaManageImpl.find("from Area where area.dbid IS NULL", new Object[0]);
			request.setAttribute("provices", provices);

			Area provice = (Area) provices.get(0);
			List<Area> cityAreas = this.areaManageImpl.find("from Area where area.dbid=?",
					new Object[] { provice.getDbid() });
			request.setAttribute("cityAreas", cityAreas);
			if ((cityAreas != null) && (cityAreas.size() > 0)) {
				List<Area> areas = this.areaManageImpl.find("from Area where area.dbid=?",
						new Object[] { ((Area) cityAreas.get(0)).getDbid() });
				request.setAttribute("areas", areas);
			}
		} catch (Exception localException) {
		}
		return "edit";
	}

	public String importExcel() throws Exception {
		HttpServletRequest request = getRequest();

		return "importExcel";
	}

	public String edit() throws Exception {
		HttpServletRequest request = getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		try {
			List<Area> provices = this.areaManageImpl.find("from Area where area.dbid IS NULL", new Object[0]);
			request.setAttribute("provices", provices);

			Area provice = (Area) provices.get(0);
			List<Area> cityAreas = this.areaManageImpl.find("from Area where area.dbid=?",
					new Object[] { provice.getDbid() });
			request.setAttribute("cityAreas", cityAreas);
			if ((cityAreas != null) && (cityAreas.size() > 0)) {
				List<Area> areas = this.areaManageImpl.find("from Area where area.dbid=?",
						new Object[] { ((Area) cityAreas.get(0)).getDbid() });
				request.setAttribute("areas", areas);
			}
			if (dbid.intValue() > 0) {
				ProviceShop proviceShop = (ProviceShop) this.proviceShopManageImpl.get(dbid);
				request.setAttribute("proviceShop", proviceShop);
			}
		} catch (Exception localException) {
		}
		return "edit";
	}

	public void save() throws Exception {
		HttpServletRequest request = getRequest();
		Integer proviceId = ParamUtil.getIntParam(request, "proviceId", -1);
		Integer cityId = ParamUtil.getIntParam(request, "cityId", -1);
		Integer areaId = ParamUtil.getIntParam(request, "areaId", -1);
		try {
			if (proviceId.intValue() > 0) {
				Area area = (Area) this.areaManageImpl.get(proviceId);
				if (area != null) {
					this.proviceShop.setProvice(area.getName());
				}
			}
			if (cityId.intValue() > 0) {
				Area area = (Area) this.areaManageImpl.get(cityId);
				if (area != null) {
					this.proviceShop.setCity(area.getName());
				}
			}
			if (areaId.intValue() > 0) {
				Area area = (Area) this.areaManageImpl.get(areaId);
				if (area != null) {
					this.proviceShop.setAreaStr(area.getName());
				}
			}
			Integer dbid = this.proviceShop.getDbid();
			if ((dbid == null) || (dbid.intValue() <= 0)) {
				List<ProviceShop> proviceShops = this.proviceShopManageImpl.find("from ProviceShop where no=? ",
						new Object[] { this.proviceShop.getNo() });
				if ((proviceShops != null) && (proviceShops.size() > 0)) {
					renderErrorMsg(new Throwable("省公司已经存在，请勿重复添加"), "");
					return;
				}
				this.proviceShop.setCreateDate(new java.util.Date());
				this.proviceShop.setModifyDate(new java.util.Date());
				this.proviceShopManageImpl.save(this.proviceShop);
			} else {
				ProviceShop proviceShop2 = (ProviceShop) this.proviceShopManageImpl.get(dbid);
				List<ProviceShop> proviceShops = this.proviceShopManageImpl.find("from ProviceShop where no=? ",
						new Object[] { this.proviceShop.getNo() });
				if ((proviceShops != null) && (proviceShops.size() > 0)) {
					ProviceShop proviceShop3 = (ProviceShop) proviceShops.get(0);
					if (proviceShop2.getDbid().intValue() != proviceShop3.getDbid().intValue()) {
						renderErrorMsg(new Throwable("省公司已经存在，请勿重复添加"), "");
						return;
					}
				}
				proviceShop2.setNo(this.proviceShop.getNo());
				proviceShop2.setAddress(this.proviceShop.getAddress());
				proviceShop2.setAreaStr(this.proviceShop.getAreaStr());
				proviceShop2.setCity(this.proviceShop.getCity());
				proviceShop2.setModifyDate(new java.util.Date());
				proviceShop2.setName(this.proviceShop.getName());
				proviceShop2.setNote(this.proviceShop.getNote());
				proviceShop2.setNum(this.proviceShop.getNum());
				proviceShop2.setProvice(this.proviceShop.getProvice());
				this.proviceShopManageImpl.save(proviceShop2);
			}
		} catch (Exception e) {
			this.log.error(e);
			e.printStackTrace();
			renderErrorMsg(e, "");
			return;
		}
		renderMsg("/proviceShop/queryList", "保存数据成功！");
	}

	public String saveImportExcel() throws Exception {
		HttpServletRequest request = getRequest();
		File dataFile = null;
		DatabaseUnitHelper databaseUnitHelper = new DatabaseUnitHelper();
		Connection jdbcUpdate = databaseUnitHelper.getJdbcConnection();
		jdbcUpdate.setAutoCommit(false);
		String insertSeql = "INSERT INTO `csss_proviceShop` \t(no,name,provice,city,areaStr,address,pointx,pointy,note,createDate,modifyDate,num)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement prepareStatement = jdbcUpdate.prepareStatement(insertSeql);
		String sql = "SELECT * FROM csss_proviceShop ORDER BY num DESC LIMIT 1";
		try {
			int lastNum = 0;
			List<ProviceShop> proviceShops = this.proviceShopManageImpl.executeSql(sql, null);
			if ((proviceShops != null) && (proviceShops.size() > 0)) {
				lastNum = ((ProviceShop) proviceShops.get(0)).getNum().intValue();
			}
			Long startTime = Long.valueOf(System.currentTimeMillis());
			if ((this.file != null) && (!this.file.getName().trim().equals(""))) {
				dataFile = FileNameUtil.getResourceFile(this.fileFileName);
				FileUtils.copyFile(this.file, dataFile);
				boolean checkFileType = checkFileType(dataFile.getName());
				if (checkFileType) {
					ProviceShopExcel proviceShopExcel = new ProviceShopExcel(this.areaManageImpl);
					boolean validateDocument = ProviceShopExcel.validateDocument(dataFile);
					if (validateDocument) {
						boolean validateForm = ProviceShopExcel.validateForm(dataFile);
						if (validateForm) {
							List<StringBuffer> errorMessges = proviceShopExcel.validateFactoryOrder(dataFile);
							Object sdf;
							if ((errorMessges == null) || (errorMessges.size() <= 0)) {
								List<ProviceShop> csssShops1 = proviceShopExcel.getProviceShop(dataFile, lastNum);
								for (ProviceShop csssShop : csssShops1) {
									prepareStatement.setString(1, csssShop.getNo());
									prepareStatement.setString(2, csssShop.getName());
									prepareStatement.setString(3, csssShop.getProvice());
									prepareStatement.setString(4, csssShop.getCity());
									prepareStatement.setString(5, csssShop.getAreaStr());
									prepareStatement.setString(6, csssShop.getAddress());
									prepareStatement.setString(7, null);
									prepareStatement.setString(8, null);
									prepareStatement.setString(9, csssShop.getNote());
									prepareStatement.setDate(10, new java.sql.Date(new java.util.Date().getTime()));
									prepareStatement.setDate(11, new java.sql.Date(new java.util.Date().getTime()));
									prepareStatement.setInt(12, csssShop.getNum().intValue());
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

	public void delete() throws Exception {
		HttpServletRequest request = getRequest();
		Integer[] dbids = ParamUtil.getIntArraryByDbids(request, "dbids");
		if ((dbids != null) && (dbids.length > 0)) {
			try {
				Integer[] arrayOfInteger1;
				int j = (arrayOfInteger1 = dbids).length;
				for (int i = 0; i < j; i++) {
					Integer dbid = arrayOfInteger1[i];
					ProviceShop proviceShop = (ProviceShop) this.proviceShopManageImpl.get(dbid);
					List<CityShop> cityShops = this.cityShopManageImpl.findBy("proviceShop.dbid", dbid);
					if ((cityShops != null) && (cityShops.size() > 0)) {
						renderErrorMsg(new Throwable("删除数据失败，【" + proviceShop.getName() + "】省店铺存在引用。"), "");
						return;
					}
					this.proviceShopManageImpl.deleteById(dbid);
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
		renderMsg("/proviceShop/queryList" + query, "删除数据成功！");
	}

	public void ajaxCity() throws Exception {
		HttpServletRequest request = getRequest();
		Integer parentId = ParamUtil.getIntParam(request, "parentId", -1);
		try {
			JSONObject object = new JSONObject();
			if (parentId.intValue() > 0) {
				List<Area> citys = this.areaManageImpl.findBy("area.dbid", parentId);
				StringBuffer cityBuffer = new StringBuffer();
				StringBuffer areaBuffer = new StringBuffer();
				if ((citys != null) && (citys.size() > 0)) {
					cityBuffer.append(
							"<select id='cityId' name='cityId' class='largeX text' onchange='ajaxArea(this)' checkType='integer,1' tip='请选择城市'>");
					for (Area area : citys) {
						cityBuffer.append("<option value='" + area.getDbid() + "'>" + area.getName() + "</option>");
					}
					cityBuffer.append("</select> ");
					cityBuffer.append("<span style=\"color: red;\">*</span>");
					object.put("city", cityBuffer.toString());

					Area city = (Area) citys.get(0);
					List<Area> areas = this.areaManageImpl.findBy("area.dbid", city.getDbid());
					areaBuffer.append(
							"<select id='areaId' name='areaId' class='largeX text' checkType='integer,1' tip='请选择区/县'>");
					for (Area area : areas) {
						areaBuffer.append("<option value='" + area.getDbid() + "'>" + area.getName() + "</option>");
					}
					areaBuffer.append("</select>");
					areaBuffer.append("<span style=\"color: red;\">*</span>");
					object.put("area", areaBuffer.toString());

					renderJson(object.toString());
				} else {
					renderText("error");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.log.error(e);
			renderText("error");
			return;
		}
	}

	public void ajaxArea() throws Exception {
		HttpServletRequest request = getRequest();
		Integer parentId = ParamUtil.getIntParam(request, "parentId", -1);
		try {
			JSONObject object = new JSONObject();
			if (parentId.intValue() > 0) {
				List<Area> citys = this.areaManageImpl.findBy("area.dbid", parentId);
				StringBuffer areaBuffer = new StringBuffer();
				if ((citys != null) && (citys.size() > 0)) {
					areaBuffer.append(
							"<select id='areaStr' name='csssShop.areaStr' class='largeX text' checkType='String,1' tip='请选择区/县'>");
					for (Area area : citys) {
						areaBuffer.append("<option value='" + area.getName() + "'>" + area.getName() + "</option>");
					}
					areaBuffer.append("</select>");
					areaBuffer.append("<span style=\"color: red;\">*</span>");
					object.put("area", areaBuffer.toString());

					renderJson(object.toString());
				} else {
					renderText("error");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.log.error(e);
			renderText("error");
			return;
		}
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
