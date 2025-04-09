package lib.struc;

import java.util.ArrayList;

public class cargaManual {
	private String idCargaManual;
	private String fecha;
	private int idUsuario;
	private String laboratorio;
	private String identificador;
	private String codProductor;
	private String dfa;
	private int idEspecie;
	private int idTemporada;
	private String codParcela;
	private String idVariedad;
	private String codTurno;
	private String especie;
	private String campo;
	private int tipo;
	private ArrayList<cargaManualDetalle> detalle;
	
	public String getIdCargaManual() {
		return idCargaManual;
	}
	public void setIdCargaManual(String idCargaManual) {
		this.idCargaManual = idCargaManual;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getCodProductor() {
		return codProductor;
	}
	public void setCodProductor(String codProductor) {
		this.codProductor = codProductor;
	}
	public String getDfa() {
		return dfa;
	}
	public void setDfa(String dfa) {
		this.dfa = dfa;
	}
	public int getIdEspecie() {
		return idEspecie;
	}
	public void setIdEspecie(int idEspecie) {
		this.idEspecie = idEspecie;
	}
	public int getIdTemporada() {
		return idTemporada;
	}
	public void setIdTemporada(int idTemporada) {
		this.idTemporada = idTemporada;
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
	public String getCodTurno() {
		return codTurno;
	}
	public void setCodTurno(String codTurno) {
		this.codTurno = codTurno;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public ArrayList<cargaManualDetalle> getDetalle() {
		return detalle;
	}
	public void setDetalle(ArrayList<cargaManualDetalle> detalle) {
		this.detalle = detalle;
	}

	
	
}
