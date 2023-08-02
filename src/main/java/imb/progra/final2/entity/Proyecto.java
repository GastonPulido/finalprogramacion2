package imb.progra.final2.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFinalEsperada;
    private String equipoAsignado;
    private String estado;
    
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = true)
    private Categoria categoria;

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinalEsperada() {
        return fechaFinalEsperada;
    }

    public void setFechaFinalEsperada(LocalDate fechaFinalEsperada) {
        this.fechaFinalEsperada = fechaFinalEsperada;
    }

    public String getEquipoAsignado() {
        return equipoAsignado;
    }

    public void setEquipoAsignado(String equipoAsignado) {
        this.equipoAsignado = equipoAsignado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
