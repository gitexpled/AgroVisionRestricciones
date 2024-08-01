package lib.struc;

import java.util.Date;

public class Diccionario {

	private int idDiccionario;
	private String codProducto;
	private String codReemplazo;
	private Date creado;
	private Date modificado;
	private int idUser;
	
	public String getCodProducto() {
		return codProducto;
	}
	public void setCodProducto(String codProducto) {
		this.codProducto = codProducto;
	}
	public String getCodReemplazo() {
		return codReemplazo;
	}
	public void setCodReemplazo(String codReemplazo) {
		this.codReemplazo = codReemplazo;
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
	public int getIdDiccionario() {
		return idDiccionario;
	}
	public void setIdDiccionario(int idDiccionario) {
		this.idDiccionario = idDiccionario;
	}
	
	
}
