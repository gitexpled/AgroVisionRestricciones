package lib.struc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LimiteExcel {

    @JsonProperty("Especies")
    private String especies;

    @JsonProperty("Fuentes")
    private String fuentes;

    @JsonProperty("Ingrediente_Activo")
    private String ingredienteActivo;

    @JsonProperty("LMR")
    private Integer lmr;

    @JsonProperty("Mercados")
    private String mercados;

    private int idUsuario;
    
    public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	// Getters y Setters
    public String getEspecies() {
        return especies;
    }

    public void setEspecies(String especies) {
        this.especies = especies;
    }

    public String getFuentes() {
        return fuentes;
    }

    public void setFuentes(String fuentes) {
        this.fuentes = fuentes;
    }

    public String getIngredienteActivo() {
        return ingredienteActivo;
    }

    public void setIngredienteActivo(String ingredienteActivo) {
        this.ingredienteActivo = ingredienteActivo;
    }

    public Integer getLmr() {
        return lmr;
    }

    public void setLmr(Integer lmr) {
        this.lmr = lmr;
    }

    public String getMercados() {
        return mercados;
    }

    public void setMercados(String mercados) {
        this.mercados = mercados;
    }

    @Override
    public String toString() {
        return "LimiteExcel{" +
                "especies='" + especies + '\'' +
                ", fuentes='" + fuentes + '\'' +
                ", ingredienteActivo='" + ingredienteActivo + '\'' +
                ", lmr=" + lmr +
                ", mercados='" + mercados + '\'' +
                '}';
    }
}