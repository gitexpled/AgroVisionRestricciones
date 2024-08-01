package lib.struc;

import java.util.Date;

public class Turno {
	
	private int idTurno;
	private String codTurno;
	private String codProductor;
	private String codParcela;
	private Date creado;
	private Date modificado;
	private int idUser;
	private String nombre;
	
	
	
	public int getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}
	public String getCodTurno() {
		return codTurno;
	}
	public void setCodTurno(String codTurno) {
		this.codTurno = codTurno;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
	
}
