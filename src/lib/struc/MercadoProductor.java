package lib.struc;

import java.util.Date;

public class MercadoProductor {
	
	private int id;
	private String codProductor;
	private String codParcela;
	private String codTurno;
	private String idVariedad;
	private String codVariedad;
	private String idMercado;
	private String mercado;
	private Date creado;
	private Date modificado;
	private int idUser;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodParcela() {
		return codParcela;
	}
	public void setCodParcela(String codParcela) {
		this.codParcela = codParcela;
	}
	public String getIdVariedad() {
		return idVariedad;
	}
	public void setIdVariedad(String idVariedad) {
		this.idVariedad = idVariedad;
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
	public String getCodVariedad() {
		return codVariedad;
	}
	public void setCodVariedad(String codVariedad) {
		this.codVariedad = codVariedad;
	}
	public String getCodProductor() {
		return codProductor;
	}
	public void setCodProductor(String codProductor) {
		this.codProductor = codProductor;
	}
	public String getCodTurno() {
		return codTurno;
	}
	public void setCodTurno(String codTurno) {
		this.codTurno = codTurno;
	}
	public String getIdMercado() {
		return idMercado;
	}
	public void setIdMercado(String idMercado) {
		this.idMercado = idMercado;
	}
	public String getMercado() {
		return mercado;
	}
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}
	
	
	
	
}
