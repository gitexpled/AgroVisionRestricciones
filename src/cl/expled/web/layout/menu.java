package cl.expled.web.layout;

import java.util.ArrayList;
import java.util.Iterator;

import lib.db.systemMenuDB;
import lib.struc.systemMenu;

public class menu {
/*
	mMantenedor += "<li class='nav-item   " + informeRoot + "'><a href='javascript:;'";
	mMantenedor += "		class='nav-link nav-toggle'> <i class='icon-layers'></i> <span";
	mMantenedor += "			class='title'>Informes</span> <span class='arrow " + informeRow
			+ "'></span>";
	mMantenedor += "	</a>";
	mMantenedor += "		<ul class='sub-menu'>";
	mMantenedor += "			<li class='nav-item  " + informeEstadoProductor + "'><a href='estadoProductor'";
	mMantenedor += "				class='nav-link '> <span class='title'>Estado Productor</span>";
	mMantenedor += "			</a></li>";
	mMantenedor += "			<li class='nav-item  " + mantencionLibro + "'><a href='homePage'";
	mMantenedor += "				class='nav-link '> <span class='title'>Exportar retricciones SAP</span>";
	mMantenedor += "			</a></li>";
	mMantenedor += "			<li class='nav-item  " + mantencionLibro + "'><a href='homePage'";
	mMantenedor += "				class='nav-link '> <span class='title'>Restriciones Productor</span>";
	mMantenedor += "			</a></li>";
	mMantenedor += "	</ul></li>";
	
	*/
	public String[] create(int idPadre,String selectMenu, int perfil)
	{
		String[] res={"",""};
		String menu="";
		selectMenu=selectMenu.replace(".jsp", "");
		//System.out.println("SELECT MENU:"+selectMenu);
		try {
			ArrayList<systemMenu> datos= systemMenuDB.getMenu(idPadre,perfil);
			
			Iterator<systemMenu> f = datos.iterator();
			//mantencionRoot = "start active open";class="nav-link nav-toggle"
			//mantencionRow = "open";
			//mantencionFuente = "start active open";

			while (f.hasNext()) {
				systemMenu row = f.next();
				String menuRoot="";
				String menuRow="";
				String menuActive="";

				if (row.getCount()>0)
				{
					
				String[] array=	create(row.getIdMenu(),selectMenu,perfil);
				if (array[0].equals("Y"))
				{
					menuRoot = "start active open";
					menuRow = "open";
					res[0]="Y";
				}
				String icono="";
				if (row.getIcono()!=null)
					icono="<i class='"+row.getIcono()+"'></i>";
				System.out.println(row.getMenu()+" -> "+res[0]);
				menu += "<li class='nav-item " + menuRoot + "'><a href='javascript:;'";
				menu += "class='nav-link nav-toggle'>"+icono+"  <span";
				menu += " class='title'>"+row.getMenu()+"</span> <span class='arrow " + menuRow+ "'></span></a>";
				menu += "		<ul class='sub-menu'>";
				menu+=array[1];

				menu += "</ul>";
				menu += "</li>";
				}
				else
				{
					if (row.getUrl().equals(selectMenu))
					{
						res[0]="Y";
						menuActive="start active open";
					}
					//System.out.println("-->"+ row.getMenu()+" -> "+res[0]+"..."+selectMenu);
					if (row.getAdm().equals("Y"))
						menu += "			<li class='nav-item  " + menuActive + "'><a href='/AgroVisionRestricciones/webApp/pageAdm/"+row.getUrl()+"'";
					else
						menu += "			<li class='nav-item  " + menuActive + "'><a href='/AgroVisionRestricciones/webApp/page/"+row.getUrl()+"'";
					menu += "				class='nav-link '> <span class='title'>"+row.getMenu()+"</span>";
					menu += "			</a></li>";
				}
				
				
			
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		res[1]=menu;
		return res;
		
	}
}
