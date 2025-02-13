/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daw.Controladores;

import com.daw.DAO.ComponenteBBDD;
import com.daw.DAO.ResultadoAprendizajeDAO;
import com.daw.modelo.ResultadoAprendizaje;
import java.util.List;

/**
 *
 * @author Javier Ruiz Gomez
 */
public class RAController {

    ComponenteBBDD componenteBBDD;
    ResultadoAprendizajeDAO raDAO;

    public RAController() {
        this.componenteBBDD = new ComponenteBBDD();
        this.raDAO = new ResultadoAprendizajeDAO(componenteBBDD);
    }

    public List<Object> seleccionarPorAsignatura(String nombreAsignatura) {
        return raDAO.selectPorAsignatura(nombreAsignatura);
    }

    public String[] obtenerAsignaturas() {
        // Llamamos al método DAO para obtener las asignaturas
        return raDAO.selectAsignaturas();
    }

    public void aniadir(int idResultado, int idAsignatura, String nombre) {
        ResultadoAprendizaje nuevoResultado = new ResultadoAprendizaje(idResultado, idAsignatura, nombre);
        raDAO.insert(nuevoResultado);
    }

    public void editar(int idResultado, int idAsignatura, String nombre) {
        ResultadoAprendizaje nuevoResultado = new ResultadoAprendizaje(idResultado, idAsignatura, nombre);
        raDAO.update(nuevoResultado, idResultado);
    }

    public void eliminar(int idResultado) {
        raDAO.delete(idResultado);
    }

    public List<Object> obtenerRA() {
        return raDAO.select();
    }

    public int obtenerIdAsignatura(String nombreAsignatura) {
        return raDAO.obtenerIdAsignaturaPorNombre(nombreAsignatura);
    }

}
