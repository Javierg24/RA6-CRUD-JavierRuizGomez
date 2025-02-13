/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daw.Controladores;

import com.daw.DAO.AsignaturaDAO;
import com.daw.DAO.ComponenteBBDD;
import com.daw.modelo.Asignatura;
import java.util.List;

/**
 *
 * @author Javier Ruiz Gomez
 */
public class AsignaturasController {

    ComponenteBBDD componenteBBDD;
    AsignaturaDAO asignaturaDAO;

    public AsignaturasController() {
        this.componenteBBDD = new ComponenteBBDD();
        this.asignaturaDAO = new AsignaturaDAO(componenteBBDD);
    }


    public void agregarAsignatura(int idAsignatura, String nombre) {
        Asignatura nuevaAsignatura = new Asignatura(idAsignatura, nombre);
        asignaturaDAO.insert(nuevaAsignatura);
    }

    public void eliminarAsignatura(int idAsignatura) {
        asignaturaDAO.delete(idAsignatura);
    }

    public void editarAsignatura(int idAsignatura, String nombre) {
        Asignatura asignaturaEditada = new Asignatura(idAsignatura, nombre);
        asignaturaDAO.update(asignaturaEditada, idAsignatura);
    }
    
    public List<Object> obtenerAsignaturas(){
        return asignaturaDAO.select();
    }

}
