package com.ystech.csss.action;

import com.ystech.core.dao.Page;
import com.ystech.core.excel.CityShopExcel;
import com.ystech.core.img.FileNameUtil;
import com.ystech.core.util.DatabaseUnitHelper;
import com.ystech.core.util.ParamUtil;
import com.ystech.core.web.BaseController;
import com.ystech.csss.model.CityShop;
import com.ystech.csss.model.CsssShop;
import com.ystech.csss.model.ProviceShop;
import com.ystech.csss.service.CityShopManageImpl;
import com.ystech.csss.service.CsssShopManageImpl;
import com.ystech.csss.service.ProviceShopManageImpl;
import com.ystech.xwqr.model.sys.Area;
import com.ystech.xwqr.service.sys.AreaManageImpl;
import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("cityShopAction")
@Scope("prototype")
public class CityShopAction extends BaseController {
	private File file;
	private String fileFileName;
	private String fileContentType;
	private String[] allowFiles = { ".xls", ".xlsx" };
	private int maxSize = 10240;
	private CityShopManageImpl cityShopManageImpl;
	private AreaManageImpl areaManageImpl;
	private CsssShopManageImpl csssShopManageImpl;
	private ProviceShopManageImpl proviceShopManageImpl;
	private CityShop cityShop;

	@Resource
	public void setAreaManageImpl(AreaManageImpl areaManageImpl) {
		this.areaManageImpl = areaManageImpl;
	}

	@Resource
	public void setCityShopManageImpl(CityShopManageImpl cityShopManageImpl) {
		this.cityShopManageImpl = cityShopManageImpl;
	}

	@Resource
	public void setCsssShopManageImpl(CsssShopManageImpl csssShopManageImpl) {
		this.csssShopManageImpl = csssShopManageImpl;
	}

	@Resource
	public void setProviceShopManageImpl(ProviceShopManageImpl proviceShopManageImpl) {
		this.proviceShopManageImpl = proviceShopManageImpl;
	}

	public CityShop getCityShop() {
		return this.cityShop;
	}

	public void setCityShop(CityShop cityShop) {
		this.cityShop = cityShop;
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
		String username = request.getParameter("title");
		try {
			Page<CityShop> page = this.cityShopManageImpl.pagedQuerySql(pageNo.intValue(), pageSize.intValue(),
					CityShop.class, "select * from csss_CityShop order by num ", new Object[0]);
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
			List<ProviceShop> proviceShops = this.proviceShopManageImpl.getAll();
			request.setAttribute("proviceShops", proviceShops);
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
				CityShop cityShop2 = (CityShop) this.cityShopManageImpl.get(dbid);
				request.setAttribute("cityShop", cityShop2);
			}
			List<ProviceShop> proviceShops = this.proviceShopManageImpl.getAll();
			request.setAttribute("proviceShops", proviceShops);
		} catch (Exception localException) {
		}
		return "edit";
	}

	public void save() throws Exception {
		HttpServletRequest request = getRequest();
		Integer proviceId = ParamUtil.getIntParam(request, "proviceId", -1);
		Integer cityId = ParamUtil.getIntParam(request, "cityId", -1);
		Integer areaId = ParamUtil.getIntParam(request, "areaId", -1);
		Integer proviceShopId = ParamUtil.getIntParam(request, "proviceShopId", -1);
		try {
			if (proviceId.intValue() > 0) {
				Area area = (Area) this.areaManageImpl.get(proviceId);
				if (area != null) {
					this.cityShop.setProvice(area.getName());
				}
			}
			if (cityId.intValue() > 0) {
				Area area = (Area) this.areaManageImpl.get(cityId);
				if (area != null) {
					this.cityShop.setCity(area.getName());
				}
			}
			if (areaId.intValue() > 0) {
				Area area = (Area) this.areaManageImpl.get(areaId);
				if (area != null) {
					this.cityShop.setAreaStr(area.getName());
				}
			}
			if (proviceShopId.intValue() < 0) {
				renderErrorMsg(new Throwable("请选择省公司"), "");
				return;
			}
			ProviceShop proviceShop = (ProviceShop) this.proviceShopManageImpl.get(proviceShopId);
			this.cityShop.setProviceShop(proviceShop);
			Integer dbid = this.cityShop.getDbid();
			if ((dbid == null) || (dbid.intValue() <= 0)) {
				this.cityShop.setCreateDate(new java.util.Date());
				this.cityShop.setModifyDate(new java.util.Date());
				this.cityShopManageImpl.save(this.cityShop);
			} else {
				CityShop cityShop2 = (CityShop) this.cityShopManageImpl.get(dbid);
				cityShop2.setAddress(this.cityShop.getAddress());
				cityShop2.setNo(this.cityShop.getNo());
				cityShop2.setAreaStr(this.cityShop.getAreaStr());
				cityShop2.setCity(this.cityShop.getCity());
				cityShop2.setModifyDate(new java.util.Date());
				cityShop2.setName(this.cityShop.getName());
				cityShop2.setNote(this.cityShop.getNote());
				cityShop2.setNum(this.cityShop.getNum());
				cityShop2.setProvice(this.cityShop.getProvice());

				cityShop2.setProviceShop(this.cityShop.getProviceShop());
				this.cityShopManageImpl.save(cityShop2);
			}
		} catch (Exception e) {
			this.log.error(e);
			e.printStackTrace();
			renderErrorMsg(e, "");
			return;
		}
		renderMsg("/cityShop/queryList", "保存数据成功！");
	}

	public String saveImportExcel() throws Exception {
		HttpServletRequest request = getRequest();
		File dataFile = null;
		DatabaseUnitHelper databaseUnitHelper = new DatabaseUnitHelper();
		Connection jdbcUpdate = databaseUnitHelper.getJdbcConnection();
		jdbcUpdate.setAutoCommit(false);
		String insertSeql = "INSERT INTO `csss_cityshop` \t(no,name,provice,city,areaStr,address,pointx,pointy,proviceShopId,note,createDate,modifyDate,num)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement prepareStatement = jdbcUpdate.prepareStatement(insertSeql);
		String sql = "SELECT * FROM csss_cityshop ORDER BY num DESC LIMIT 1";
		try {
			int lastNum = 0;
			List<CityShop> cityShops = this.cityShopManageImpl.executeSql(sql, null);
			if ((cityShops != null) && (cityShops.size() > 0)) {
				lastNum = ((CityShop) cityShops.get(0)).getNum().intValue();
			}
			Long startTime = Long.valueOf(System.currentTimeMillis());
			if ((this.file != null) && (!this.file.getName().trim().equals(""))) {
				dataFile = FileNameUtil.getResourceFile(this.fileFileName);
				FileUtils.copyFile(this.file, dataFile);
				boolean checkFileType = checkFileType(dataFile.getName());
				if (checkFileType) {
					CityShopExcel cityShopExcel = new CityShopExcel(this.areaManageImpl, this.proviceShopManageImpl);
					boolean validateDocument = CityShopExcel.validateDocument(dataFile);
					if (validateDocument) {
						boolean validateForm = CityShopExcel.validateForm(dataFile);
						if (validateForm) {
							List<StringBuffer> errorMessges = cityShopExcel.validateFactoryOrder(dataFile);
							Object sdf;
							if ((errorMessges == null) || (errorMessges.size() <= 0)) {
								List<CityShop> cityShop2s = cityShopExcel.getCityShop(dataFile, lastNum);
								for (CityShop cityShop : cityShop2s) {
									prepareStatement.setString(1, cityShop.getNo());
									prepareStatement.setString(2, cityShop.getName());
									prepareStatement.setString(3, cityShop.getProvice());
									prepareStatement.setString(4, cityShop.getCity());
									prepareStatement.setString(5, cityShop.getAreaStr());
									prepareStatement.setString(6, cityShop.getAddress());
									prepareStatement.setString(7, null);
									prepareStatement.setString(8, null);
									prepareStatement.setInt(9, cityShop.getProviceShop().getDbid().intValue());
									prepareStatement.setString(10, cityShop.getNote());
									prepareStatement.setDate(11, new java.sql.Date(new java.util.Date().getTime()));
									prepareStatement.setDate(12, new java.sql.Date(new java.util.Date().getTime()));
									prepareStatement.setInt(13, cityShop.getNum().intValue());
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
					CityShop cityShop = (CityShop) this.cityShopManageImpl.get(dbid);
					List<CsssShop> csssShops = this.csssShopManageImpl.findBy("cityShop.dbid", dbid);
					if ((csssShops != null) && (csssShops.size() > 0)) {
						renderErrorMsg(new Throwable("删除数据失败，【" + cityShop.getName() + "】地市公司存在引用。"), "");
						return;
					}
					this.cityShopManageImpl.deleteById(dbid);
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.log.error(e);
				renderErrorMsg(e, "");
				return;
			}
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return;
		}

		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/cityShop/queryList" + query, "删除数据成功！");
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
					for (Area area :  areas) {
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
