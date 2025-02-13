/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.daw.modelo;

import java.math.BigDecimal;

/**
 *
 * @author Javier Ruiz Gomez
 */
public class CriterioEvaluacion {
 private int idCriterio;
    private int idResultado;
    private int idAsignatura;
    private BigDecimal porcentaje;
    private String nombre;

    public CriterioEvaluacion(int idCriterio, int idResultado, int idAsignatura, BigDecimal porcentaje, String nombre) {
        this.idCriterio = idCriterio;
        this.idResultado = idResultado;
        this.idAsignatura = idAsignatura;
        this.porcentaje = porcentaje;
        this.nombre = nombre;
    }

    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }

    public int getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(int idResultado) {
        this.idResultado = idResultado;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "CriterioEvaluacion{" + "idCriterio=" + idCriterio + ", idResultado=" + idResultado + ", idAsignatura=" + idAsignatura + ", porcentaje=" + porcentaje + ", nombre=" + nombre + '}';
    }        
}
