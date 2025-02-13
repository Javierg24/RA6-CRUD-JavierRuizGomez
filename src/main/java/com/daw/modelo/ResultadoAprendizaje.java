/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.daw.modelo;

/**
 *
 * @author Javier Ruiz Gomez
 */
public class ResultadoAprendizaje {
 private int idResultado;
    private int idAsignatura;
    private String nombre;

    public ResultadoAprendizaje(int idResultado, int idAsignatura, String nombre) {
        this.idResultado = idResultado;
        this.idAsignatura = idAsignatura;
        this.nombre = nombre;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ResultadoAprendizaje{" + "idResultado=" + idResultado + ", idAsignatura=" + idAsignatura + ", nombre=" + nombre + '}';
    }
    
    
}
