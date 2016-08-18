<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ystech" uri="/WEB-INF/ystech-tld/ystech.tld" %>  
<%@ taglib prefix="s" uri="/struts-tags" %>  
<c:set var="ctx" value="${pageContext.request.contextPath=='/'?'':pageContext.request.contextPath}"/>
<c:set var="ysshop" value="http://www.ysshop.ystech.co/"/>
<c:set var="checked" value="checked=\"checked\"" />
<c:set var="selected" value="selected=\"selected\"" />
<jsp:useBean id="now" class="java.util.Date" />  
<%
response.setHeader("Pragma", "No-cache"); 
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0); 
%>
