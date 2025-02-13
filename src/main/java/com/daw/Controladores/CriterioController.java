/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daw.Controladores;

import com.daw.DAO.ComponenteBBDD;
import com.daw.DAO.CriterioEvaluacionDAO;
import com.daw.modelo.CriterioEvaluacion;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Javier Ruiz Gomez
 */
public class CriterioController {

    ComponenteBBDD componenteBBDD;
    CriterioEvaluacionDAO criteriosDAO;

    public CriterioController() {
        this.componenteBBDD = new ComponenteBBDD();
        this.criteriosDAO = new CriterioEvaluacionDAO(componenteBBDD);
    }

    public void agregarCriterio(int idCriterio, int idResultado, int idAsignatura, BigDecimal porcentaje, String nombre) {
        CriterioEvaluacion nuevoCriterio = new CriterioEvaluacion(idCriterio, idResultado, idAsignatura, porcentaje, nombre);
        criteriosDAO.insert(nuevoCriterio);
    }

    public void editarCriterio(int idCriterio, int idResultado, int idAsignatura, BigDecimal porcentaje, String nombre) {
        CriterioEvaluacion criterioEditado = new CriterioEvaluacion(idCriterio, idResultado, idAsignatura, porcentaje, nombre);
        criteriosDAO.update(criterioEditado, idCriterio);
    }

    public void eliminarCriterio(int idCriterio) {
        criteriosDAO.delete(idCriterio);
    }

    public List<Object> obtenerCriterios() {
        return criteriosDAO.select();
    }

    public String[] obtenerAsignaturas() {
        // Llamamos al método DAO para obtener las asignaturas
        return criteriosDAO.selectAsignaturas();
    }

    public String[] obtenerRA(String asignatura) {
        return criteriosDAO.selectRA(asignatura);
    }

    public List<Object> obtenerCriterios(String nomAsignatura, String nomResultado) {
        return criteriosDAO.selectPorAsignaturaYResultado(nomAsignatura, nomResultado);
    }

    public int obtenerIdAsignatura(String nombreAsignatura) {
        return criteriosDAO.obtenerIdAsignaturaPorNombre(nombreAsignatura);
    }

    public int obtenerIdRA(String nombreRA) {
        return criteriosDAO.obtenerIdResultadoPorNombre(nombreRA);
    }
}
