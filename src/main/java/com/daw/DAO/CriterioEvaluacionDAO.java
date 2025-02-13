/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daw.DAO;

import com.daw.modelo.Asignatura;
import com.daw.modelo.CriterioEvaluacion;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar los criterios de evaluación.
 *
 * @author
 */
public class CriterioEvaluacionDAO extends Service {

    public CriterioEvaluacionDAO(ComponenteBBDD componenteBBDD) {
        super(componenteBBDD);
    }

    @Override
    public List<Object> select() {
        List<Object> criterios = new ArrayList<>();
        try {
            String query = "SELECT id_criterio, id_resultado, id_asignatura, porcentaje, nombre FROM CRITERIOS";
            this.componenteBBDD.setConsulta(query);

            if (this.componenteBBDD.ejecutarConsulta()) {
                ResultSet cursor = this.componenteBBDD.getCursor();
                while (cursor.next()) {
                    CriterioEvaluacion criterio = new CriterioEvaluacion(
                            cursor.getInt("id_criterio"),
                            cursor.getInt("id_resultado"),
                            cursor.getInt("id_asignatura"),
                            cursor.getBigDecimal("porcentaje"),
                            cursor.getString("nombre")
                    );
                    criterios.add(criterio);
                }
                this.componenteBBDD.cerrarCursor();
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener los criterios de evaluación: " + ex.getMessage());
        }
        return criterios;
    }

    public String[] selectAsignaturas() {
        List<String> asignaturas = new ArrayList<>();

        try {
            // Consulta para obtener todos los nombres de asignaturas
            String query = "SELECT nombre FROM ASIGNATURAS";

            this.componenteBBDD.setConsulta(query);

            if (this.componenteBBDD.ejecutarConsulta()) {
                ResultSet rs = this.componenteBBDD.getCursor();

                while (rs.next()) {
                    // Obtener el nombre de la asignatura y añadirlo a la lista
                    asignaturas.add(rs.getString("nombre"));
                }

                this.componenteBBDD.cerrarCursor();  // Cerrar el cursor después de usarlo
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener las asignaturas: " + ex.getMessage());
        }

        // Convertir la lista en un arreglo de String y devolverlo
        return asignaturas.toArray(new String[0]);
    }

    public String[] selectRA(String asignatura) {
        List<String> resultados = new ArrayList<>();

        try {
            // Consulta para obtener los nombres de los resultados de aprendizaje de una asignatura específica
            String query = "SELECT ra.nombre FROM RESULTADOS ra "
                    + "JOIN ASIGNATURAS a ON ra.id_asignatura = a.id_asignatura "
                    + "WHERE a.nombre = ?";

            this.componenteBBDD.setConsulta(query);
            PreparedStatement stmt = this.componenteBBDD.getConexion().prepareStatement(query);
            stmt.setString(1, asignatura);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resultados.add(rs.getString("nombre"));
            }

        } catch (SQLException ex) {
            System.out.println("Error al obtener los resultados de aprendizaje: " + ex.getMessage());
        }

        // Convertir la lista en un arreglo de String y devolverlo
        return resultados.toArray(new String[0]);
    }

    public List<Object> selectPorAsignaturaYResultado(String nombreAsignatura, String nombreResultado) {
        List<Object> criterios = new ArrayList<>();

        try {
            String query = "SELECT c.id_criterio, c.id_resultado, c.id_asignatura, c.porcentaje, c.nombre "
                    + "FROM CRITERIOS c "
                    + "JOIN RESULTADOS r ON c.id_resultado = r.id_resultado "
                    + "JOIN ASIGNATURAS a ON c.id_asignatura = a.id_asignatura "
                    + "WHERE a.nombre = ? AND r.nombre = ?";

            this.componenteBBDD.setConsulta(query);
            PreparedStatement stmt = this.componenteBBDD.getConexion().prepareStatement(query);
            stmt.setString(1, nombreAsignatura);
            stmt.setString(2, nombreResultado);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CriterioEvaluacion criterio = new CriterioEvaluacion(
                        rs.getInt("id_criterio"),
                        rs.getInt("id_resultado"),
                        rs.getInt("id_asignatura"),
                        rs.getBigDecimal("porcentaje"),
                        rs.getString("nombre")
                );
                criterios.add(criterio);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener los criterios de evaluación: " + ex.getMessage());
        }

        return criterios;
    }

    public List<Object> selectPorAsignatura(String condicion) {
        List<Object> asignaturas = new ArrayList<>();
        try {
            String query = "SELECT * FROM asignaturas WHERE id_asignatura = " + condicion;  // Consulta para obtener todas las asignaturas
            this.componenteBBDD.setConsulta(query);
            if (this.componenteBBDD.ejecutarConsulta()) {
                ResultSet rs = this.componenteBBDD.getCursor();
                while (rs.next()) {
                    // Crear un objeto Asignatura (o el tipo que uses para representarlas) y añadirlo a la lista
                    Asignatura asignatura = new Asignatura();
                    asignatura.setIdAsignatura(rs.getInt("id_asignatura"));
                    asignatura.setNombre(rs.getString("nombre"));
                    asignaturas.add(asignatura);
                }
                this.componenteBBDD.cerrarCursor();  // Cerrar el cursor después de usarlo
            }
        } catch (SQLException ex) {
            System.out.println("Error al seleccionar asignaturas: " + ex.getMessage());
        }
        return asignaturas;
    }

    @Override
    public boolean delete(int id) {
        try {
            String query = "DELETE FROM CRITERIOS WHERE id_criterio = ?";
            this.componenteBBDD.setConsulta(query);
            PreparedStatement preparedStatement = this.componenteBBDD.getConexion().prepareStatement(query);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.out.println("Error al eliminar el criterio de evaluación: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean insert(Object o) {
        if (o instanceof CriterioEvaluacion) {
            CriterioEvaluacion criterio = (CriterioEvaluacion) o;
            try {
                String query = "INSERT INTO CRITERIOS (id_criterio, id_resultado, id_asignatura, porcentaje, nombre) VALUES (?, ?, ?, ?, ?)";
                this.componenteBBDD.setConsulta(query);
                PreparedStatement preparedStatement = this.componenteBBDD.getConexion().prepareStatement(query);
                preparedStatement.setInt(1, criterio.getIdCriterio());
                preparedStatement.setInt(2, criterio.getIdResultado());
                preparedStatement.setInt(3, criterio.getIdAsignatura());
                preparedStatement.setBigDecimal(4, criterio.getPorcentaje());
                preparedStatement.setString(5, criterio.getNombre());
                int result = preparedStatement.executeUpdate();
                return result > 0;
            } catch (SQLException ex) {
                System.out.println("Error al insertar el criterio de evaluación: " + ex.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean update(Object o, int id) {
        if (o instanceof CriterioEvaluacion) {
            CriterioEvaluacion criterio = (CriterioEvaluacion) o;
            try {
                String query = "UPDATE CRITERIOS SET id_resultado = ?, id_asignatura = ?, porcentaje = ?, nombre = ? WHERE id_criterio = ?";
                this.componenteBBDD.setConsulta(query);
                PreparedStatement preparedStatement = this.componenteBBDD.getConexion().prepareStatement(query);
                preparedStatement.setInt(1, criterio.getIdResultado());
                preparedStatement.setInt(2, criterio.getIdAsignatura());
                preparedStatement.setBigDecimal(3, criterio.getPorcentaje());
                preparedStatement.setString(4, criterio.getNombre());
                preparedStatement.setInt(5, id);
                int result = preparedStatement.executeUpdate();
                return result > 0;
            } catch (SQLException ex) {
                System.out.println("Error al actualizar el criterio de evaluación: " + ex.getMessage());
                return false;
            }
        }
        return false;
    }

    public static void mostrarCriterios(ComponenteBBDD com, CriterioEvaluacionDAO cedao) {
        List<Object> lista = cedao.select();
        for (Object obj : lista) {
            if (obj instanceof CriterioEvaluacion) { // Asegurarse de que sea del tipo correcto
                CriterioEvaluacion criterioEvaluacion = (CriterioEvaluacion) obj;
                System.out.println(criterioEvaluacion.toString());
            } else {
                System.out.println("Elemento desconocido en la lista: " + obj);
            }
        }
    }

    public int obtenerIdAsignaturaPorNombre(String nombreAsignatura) {
        int idAsignatura = -1;

        try {
            String query = "SELECT id_asignatura FROM ASIGNATURAS WHERE nombre = ?";
            this.componenteBBDD.setConsulta(query);

            PreparedStatement stmt = this.componenteBBDD.getConexion().prepareStatement(query);
            stmt.setString(1, nombreAsignatura);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idAsignatura = rs.getInt("id_asignatura");
            }

            rs.close(); // Cerrar el ResultSet después de usarlo

        } catch (SQLException e) {
            System.out.println("Error al obtener ID de la asignatura: " + e.getMessage());
        }

        return idAsignatura;
    }

    public int obtenerIdResultadoPorNombre(String nombreResultado) {
        int idResultado = -1;
        try {
            String query = "SELECT id_resultado FROM RESULTADOS WHERE nombre = ?";
            this.componenteBBDD.setConsulta(query);
            PreparedStatement stmt = this.componenteBBDD.getConexion().prepareStatement(query);
            stmt.setString(1, nombreResultado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idResultado = rs.getInt("id_resultado");
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener ID del resultado: " + e.getMessage());
        }
        return idResultado;
    }

    public static void main(String[] args) {
        CriterioEvaluacion actCriterioEvaluacion = new CriterioEvaluacion(2, 1, 1, BigDecimal.valueOf(20), "Teoria");
        CriterioEvaluacion insCriterioEvaluacion = new CriterioEvaluacion(6, 1, 1, BigDecimal.valueOf(60), "Examen");
        ComponenteBBDD componenteBBDD = new ComponenteBBDD();
        CriterioEvaluacionDAO criterioEvaluacionDAO = new CriterioEvaluacionDAO(componenteBBDD);
        mostrarCriterios(componenteBBDD, criterioEvaluacionDAO);
        criterioEvaluacionDAO.delete(1);
        mostrarCriterios(componenteBBDD, criterioEvaluacionDAO);
        criterioEvaluacionDAO.update(actCriterioEvaluacion, 2);
        mostrarCriterios(componenteBBDD, criterioEvaluacionDAO);
        criterioEvaluacionDAO.insert(insCriterioEvaluacion);
        String[] RA = criterioEvaluacionDAO.selectRA("Literatura");
        for (Object obj : RA) {
            System.out.println(obj);
        }
        List<Object> ListaCriterios = criterioEvaluacionDAO.selectPorAsignaturaYResultado("Literatura", "RA1");
        for (Object obj : ListaCriterios) {
            if (obj instanceof CriterioEvaluacion) {
                CriterioEvaluacion criterioEvaluacion = (CriterioEvaluacion) obj;
                System.out.println(criterioEvaluacion.toString());
            } else {
                System.out.println("Elemento desconocido en la lista: " + obj);
            }
        }
        int id = criterioEvaluacionDAO.obtenerIdResultadoPorNombre("RA1");
        System.out.println(id);
    }
}
