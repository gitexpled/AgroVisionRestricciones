package lib.struc;

import java.util.Date;

public class BloqueoParcela {
	
	private int idBloqueo;
	private String codProductor;
	private String codParcela;
	private String comentario;
	private Date creado;
	private String estado;
	private String idVariedad;
	private int idMercado;
	
	public int getIdMercado() {
		return idMercado;
	}
	public void setIdMercado(int idMercado) {
		this.idMercado = idMercado;
	}
	public String getIdVariedad() {
		return idVariedad;
	}
	public void setIdVariedad(String idVariedad) {
		this.idVariedad = idVariedad;
	}
	public int getIdBloqueo() {
		return idBloqueo;
	}
	public void setIdBloqueo(int idBloqueo) {
		this.idBloqueo = idBloqueo;
	}
	public String getCodProductor() {
		return codProductor;
	}
	public void setCodProductor(String codProductor) {
		this.codProductor = codProductor;
	}
	public String getCodParcela() {
		return codParcela;
	}
	public void setCodParcela(String codParcela) {
		this.codParcela = codParcela;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Date getCreado() {
		return creado;
	}
	public void setCreado(Date creado) {
		this.creado = creado;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	

}
