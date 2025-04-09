package lib.struc;

import java.util.Date;

public class habilitacionManual {
	
	private int id;
	private String productor;
	private String etapa;
	private String campo;
	private String idVariedad;
	private String mercado;
	private String habilitado;
	private Date creado;
	private Date modificado;
	private int idUser;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductor() {
		return productor;
	}
	public void setProductor(String productor) {
		this.productor = productor;
	}
	public String getEtapa() {
		return etapa;
	}
	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
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
	public String getIdVariedad() {
		return idVariedad;
	}
	public void setIdVariedad(String idVariedad) {
		this.idVariedad = idVariedad;
	}
	public String getMercado() {
		return mercado;
	}
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}
	
	
	
}
