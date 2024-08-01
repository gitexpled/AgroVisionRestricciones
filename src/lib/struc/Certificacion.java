package lib.struc;

import java.util.Date;

public class Certificacion {

	private int idCertificaciones;
	private int idUser;
	private String certificacionesCol;
	private String prefijo;
	private Date creado;
	private Date modificado;
	
	public int getIdCertificaciones() {
		return idCertificaciones;
	}
	public void setIdCertificaciones(int idCertificaciones) {
		this.idCertificaciones = idCertificaciones;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getCertificacionesCol() {
		return certificacionesCol;
	}
	public void setCertificacionesCol(String certificacionesCol) {
		this.certificacionesCol = certificacionesCol;
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
	public String getPrefijo() {
		return prefijo;
	}
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	
}
