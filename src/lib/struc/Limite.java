package lib.struc;

import java.util.Date;

public class Limite {

	private int idLimite;
	private String codProducto;
	private int idMercado;
	private int idTipoProducto;
	private int idFuente;
	private String limite;
	private Date creado;
	private Date modificado;
	
	
	private String mercado;
	private String tipoProducto;
	private String fuente;

	private int idEspecie;
	
	
	public int getIdLimite() {
		return idLimite;
	}
	public void setIdLimite(int idLimite) {
		this.idLimite = idLimite;
	}
	public String getCodProducto() {
		return codProducto;
	}
	public void setCodProducto(String codProducto) {
		this.codProducto = codProducto;
	}
	public int getIdMercado() {
		return idMercado;
	}
	public void setIdMercado(int idMercado) {
		this.idMercado = idMercado;
	}
	public int getIdTipoProducto() {
		return idTipoProducto;
	}
	public void setIdTipoProducto(int idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}
	public int getIdFuente() {
		return idFuente;
	}
	public void setIdFuente(int idFuente) {
		this.idFuente = idFuente;
	}
	public String getLimite() {
		return limite;
	}
	public void setLimite(String limite) {
		this.limite = limite;
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
	public String getMercado() {
		return mercado;
	}
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public String getFuente() {
		return fuente;
	}
	public void setFuente(String fuente) {
		this.fuente = fuente;
	}
	public int getIdEspecie() {
		return idEspecie;
	}
	public void setIdEspecie(int idEspecie) {
		this.idEspecie = idEspecie;
	}
	
	
}
