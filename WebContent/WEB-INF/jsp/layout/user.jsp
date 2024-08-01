<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<li class="dropdown dropdown-user"><a href="javascript:;"
	class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
	data-close-others="true"> <span
		class="username username-hide-on-mobile">${nombre}</span> <input
		type="hidden" value="${idUser}" id="idUserPefil" /> <i
		class="fa fa-angle-down"></i>
</a>
<c:set var="myContext" value="${pageContext.request.contextPath}"/>
	<ul class="dropdown-menu dropdown-menu-default">
		<li><a href="${myContext}/webApp/page/perfil"> <i
				class="icon-user"></i> Mi Perfil
		</a></li>
		<li class="divider"></li>

		<li><a href="${myContext}/webApp/exit.jsp"> <i
				class="icon-key"></i> Log Out
		</a></li>
	</ul></li>