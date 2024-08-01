package lib.struc;

import java.util.Date;

public class user {
	
	private int id;
	private String nombre;
	private String apellido;
	private String user;
	private String password;
	private Date creacion;
	private Date baja;
	private int estado;
	private int idPerfil;
	private String mail;


	public int getId() {
		return id;
	}

	public void setId(int value) {
		this.id = value;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String value) {
		this.nombre = value;
	}
	
	public String getApellido() {
		return apellido;
	}

	public void setApellido(String value) {
		this.apellido = value;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String value) {
		this.user = value;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String value) {
		this.password = value;
	}
	public Date getCreacion() {
		return creacion;
	}

	public void setCreacion(Date value) {
		this.creacion = value;
	}
	public Date getBaja() {
		return baja;
	}

	public void setBaja(Date value) {
		this.baja = value;
	}
	
	
	
	

	public int getEstado() {
		return estado;
	}

	public void setEstado(int value) {
		this.estado = value;
	}

	public int getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(int value) {
		this.idPerfil = value;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
}
