/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daw.DAO;

import com.daw.modelo.Asignatura;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsignaturaDAO extends Service {

    public AsignaturaDAO(ComponenteBBDD componenteBBDD) {
        super(componenteBBDD);

    }

    public List<Object> select() {
        List<Object> asignaturas = new ArrayList<>();
        try {
            String query = "SELECT * FROM asignaturas";  // Consulta para obtener todas las asignaturas
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

    public boolean delete(int id) {
        try {
            // Crear la consulta SQL para seleccionar la asignatura con el id especificado
            String query = "SELECT * FROM ASIGNATURAS WHERE id_asignatura = " + id;
            this.componenteBBDD.setConsulta(query);

            // Ejecutar la consulta actualizable
            if (this.componenteBBDD.ejecutarConsultaActualizable()) {
                // Obtener el cursor (ResultSet) desde ComponenteBBDD
                ResultSet resultSet = this.componenteBBDD.getCursor();

                // Verificar si existe algún registro con el id proporcionado
                while (resultSet.next()) {
                    // Si se encuentra el registro, eliminarlo
                    resultSet.deleteRow();
                    System.out.println("Asignatura con ID " + id + " eliminada con éxito.");
                    return true;
                }
            } else {
                System.out.println("No se encontró la asignatura con ID: " + id);
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Error al eliminar asignatura: " + ex.getMessage());
            return false;
        }
        return false;
    }

    public boolean insert(Object o) {
        try {
            Asignatura asignatura = (Asignatura) o;
            String query = "INSERT INTO ASIGNATURAS (id_asignatura, nombre) VALUES (?, ?)";
            this.componenteBBDD.setConsulta(query);
            PreparedStatement preparedStatement = this.componenteBBDD.getConexion().prepareStatement(query);
            preparedStatement.setInt(1, asignatura.getIdAsignatura());
            preparedStatement.setString(2, asignatura.getNombre());
            int result = preparedStatement.executeUpdate();
            return result > 0;  // Retorna verdadero si se insertó correctamente
        } catch (SQLException ex) {
            System.out.println("Error al insertar asignatura: " + ex.getMessage());
            return false;
        }
    }

    public boolean update(Object o, int id) {
        try {
            Asignatura asignatura = (Asignatura) o;
            String query = "UPDATE ASIGNATURAS SET nombre = ? WHERE id_asignatura = ?";
            this.componenteBBDD.setConsulta(query);
            PreparedStatement preparedStatement = this.componenteBBDD.getConexion().prepareStatement(query);
            preparedStatement.setString(1, asignatura.getNombre());
            preparedStatement.setInt(2, id);
            int result = preparedStatement.executeUpdate();
            return result > 0;  // Retorna verdadero si se actualizó correctamente
        } catch (SQLException ex) {
            System.out.println("Error al actualizar asignatura: " + ex.getMessage());
            return false;
        }
    }

    public static void mostrarAsignaturas(ComponenteBBDD com, AsignaturaDAO as) {
        List<Object> lista = as.select();
        for (Object obj : lista) {
            if (obj instanceof Asignatura) { // Asegurarse de que sea del tipo correcto
                Asignatura asignatura = (Asignatura) obj;
                System.out.println(asignatura.toString());
            } else {
                System.out.println("Elemento desconocido en la lista: " + obj);
            }
        }
    }

    public static void main(String[] args) {
        Asignatura asignatura = new Asignatura(1, "Geografía");
        ComponenteBBDD componenteBBDD = new ComponenteBBDD();
        AsignaturaDAO asignaturaDAO = new AsignaturaDAO(componenteBBDD);
        mostrarAsignaturas(componenteBBDD, asignaturaDAO);
        /*
        if(asignaturaDAO.insert(asignatura)){
            System.out.println("Insertado Correctamente");
        }
         */
        asignaturaDAO.update(asignatura, 1);
    }

}
