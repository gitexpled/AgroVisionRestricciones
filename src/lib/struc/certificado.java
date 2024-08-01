package lib.struc;

import java.util.Date;

public class certificado {
	
	private int id;
	private String nombre;
	private String mercado;
	private String idmercado;
	private String idcertificado;
	
	public String getIdcertificado() {
		return idcertificado;
	}

	public void setIdcertificado(String idcertificado) {
		this.idcertificado = idcertificado;
	}

	public String getMercado() {
		return mercado;
	}

	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	public int getId() {
		return id;
	}

	public void setId(int value) {
		this.id = value;
	}
	public String getNombre() {
		return nombre;
	}

	public String getIdmercado() {
		return idmercado;
	}

	public void setIdmercado(String idmercado) {
		this.idmercado = idmercado;
	}

	public void setNombre(String value) {
		this.nombre = value;
	}

}
