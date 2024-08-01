package lib.struc;

import java.util.Date;

public class Parcela {
	private String codigo;
	private String codigoProductor;
	private String nombre;
	private Date creado;
	private Date modificado;
	private int idUser;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCodigoProductor() {
		return codigoProductor;
	}
	public void setCodigoProductor(String codigoProductor) {
		this.codigoProductor = codigoProductor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getCreado() {
		return creado;
	}
	public void setCreado(Date creado) {
		this.creado = creado;
	}
	public Date getModificado() {
		return modificado;
	}
	public void setModificado(Date modificado) {
		this.modificado = modificado;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	
	
	
}
