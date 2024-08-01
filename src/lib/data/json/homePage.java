package lib.data.json;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lib.db.homePageDB;
import lib.security.session;
import lib.struc.TemporadaZona;
import lib.struc.grafico;




@Controller
public class homePage {
	

	
	
	
	@RequestMapping(value = "/homePage/productorZona/{id}/{mercado}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ArrayList<grafico> productorZona(@PathVariable int id,@PathVariable int mercado,HttpServletRequest request,HttpSession httpSession)  {
	
		ArrayList<grafico> data = new ArrayList<grafico>();
		
		
		session ses = new session(httpSession);
		
		
		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
		
		
		if (!ses.isValid()) {
			
			
			try {
				System.out.println("idEspecie: ");
				try {
					id=id*1;
					if (id<=1)
					{
						id=1;
					}
						
				} catch (Exception e) {
					id=1;
				}
				try {
					mercado=mercado*1;
					if (mercado<=1)
					{
					//	mercado=1;
					}
						
				} catch (Exception e) {
					mercado=1;
				}
				//data=homePageDB.getProductorZona(id, mercado);
				System.out.println(data.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return data;
		


	}
	
	@RequestMapping("/homePage/temporadaZona/{id}")
	public @ResponseBody ArrayList<TemporadaZona> getTemporadaZona(@PathVariable int id,HttpServletRequest request,HttpSession httpSession)  {
		ArrayList<TemporadaZona> data=new  ArrayList<TemporadaZona>();
		session ses = new session(httpSession);
		if (!ses.isValid()) {
			try {
				data =homePageDB.getTemporadaZona();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}
	
	@RequestMapping(value = "/homePage/productorMercado/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ArrayList<grafico> productorMercado(@PathVariable int id,HttpServletRequest request,HttpSession httpSession)  {
	System.out.println( request.getServletPath());
		ArrayList<grafico> data = new ArrayList<grafico>();
		
		
		session ses = new session(httpSession);
		
		
		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
		
		
		if (!ses.isValid()) {
			
			
			try {
				System.out.println("idEspecie: ");
				try {
					id=id*1;
					if (id<=1)
					{
						id=1;
					}
						
				} catch (Exception e) {
					id=1;
				}
				//data=homePageDB.getProductorMercado(id);
				System.out.println(data.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return data;
		


	}
	@RequestMapping(value = "/homePage/top10Producto/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ArrayList<grafico> productorZona(@PathVariable int id,HttpServletRequest request,HttpSession httpSession)  {
	
		ArrayList<grafico> data = new ArrayList<grafico>();
		
		
		session ses = new session(httpSession);
		
		
		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
		
		
		if (!ses.isValid()) {
			
			
			try {
				System.out.println("idEspecie: ");
				try {
					id=id*1;
					if (id<=1)
					{
						id=1;
					}
						
				} catch (Exception e) {
					id=1;
				}
				
				//data=homePageDB.getTop10Producto(id, 1);
				System.out.println(data.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return data;
		


	}
	@RequestMapping(value = "/homePage/getProductor5/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ArrayList<grafico> getProductor5(@PathVariable int id,HttpServletRequest request,HttpSession httpSession)  {
	
		ArrayList<grafico> data = new ArrayList<grafico>();
		
		
		session ses = new session(httpSession);
		
		
		System.out.println("GET:::::::::::::::::::::::::::::::::::::::: ");
		
		
		if (!ses.isValid()) {
			
			
			try {
				System.out.println("idEspecie: ");
				try {
					id=id*1;
					if (id<=1)
					{
						id=1;
					}
						
				} catch (Exception e) {
					id=1;
				}
				
				//data=homePageDB.getProductor5(id, 1);
				System.out.println(data.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return data;
		


	}
}
