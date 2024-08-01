<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<li class="dropdown dropdown-extended dropdown-notification"
	id="header_notification_bar"><a><span class="username username-hide-on-mobile" style="color: white;">${temporada} </span><input type="hidden" value="${idTemporada}" id="idTempActual"/>   </a>

	
		</li>
		

	
<c:if test="${alarmas !=0}">
 <li class="dropdown dropdown-extended dropdown-notification"
	id="header_notification_bar"><a href="javascript:;"
	class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
	data-close-others="true"> <i class="icon-bell"></i> <span
		class="badge badge-danger"> ${alarmas}</span>
</a>
	<ul class="dropdown-menu">
		<li class="external">
			<h3>
				<span class="bold">${alarmas} Alertas</span>
			</h3> <a href="homePage">ver</a>
		</li>
		<li>
			<ul class="dropdown-menu-list scroller" style="height: 250px;"
				data-handle-color="#637283">
				
				<c:if test="${alarmaComponente !=0}">
				<li><a href="/restPesticida/webApp/page/proceso.alarmaComponente"> <span class="details"> <span
							class="label label-sm label-icon label-success"> <i
								class="fa fa-plus"></i>
						</span>${alarmaComponente} componentes  no encontradeo
					</span>
				</a></li>
				</c:if>
				<c:if test="${alarmaProductor!=0}">
				<li><a href="/restPesticida/webApp/page/proceso.alarmaProductor"> <span class="details"> <span
							class="label label-sm label-icon label-success"> <i
								class="fa fa-plus"></i>
						</span>${alarmaProductor} cod. productor no encontrado
					</span>
				</a></li>
				</c:if>
				<c:if test="${alarmaProductor!=0}">
				<li><a href="/restPesticida/webApp/pageAdm/informe.estadoProductor"> <span class="details"> <span
							class="label label-sm label-icon label-success"> <i
								class="fa fa-plus"></i>
						</span>${alarmaProductor} nuevos bloqueos
					</span>
				</a></li>
				</c:if>

			</ul>
		</li>
	</ul></li> 
</c:if>