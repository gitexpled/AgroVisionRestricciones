package lib.struc;

import java.util.Date;

public class Temporada {

	private int idTemporada;
	private int idUser;
	private String usuario;
	private String temporada;
	private Date creado;
	
	public int getIdTemporada() {
		return idTemporada;
	}
	public void setIdTemporada(int idTemporada) {
		this.idTemporada = idTemporada;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public Date getCreado() {
		return creado;
	}
	public void setCreado(Date creado) {
		this.creado = creado;
	}
	public String getTemporada() {
		return temporada;
	}
	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	
}
