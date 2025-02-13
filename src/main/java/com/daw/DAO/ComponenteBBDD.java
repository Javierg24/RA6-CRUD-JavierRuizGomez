/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daw.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Javier Ruiz Gomez
 */
public class ComponenteBBDD {

    private Connection conexion;
    private String sentencia;
    private ResultSet cursor;

    public ComponenteBBDD() {
    }

    public boolean setConexion(Properties properties) {
        try {
            Class.forName(properties.getProperty("DRIVER"));
            String usuario = properties.getProperty("USER");
            String contraseña = properties.getProperty("PASSWORD");
            String url = properties.getProperty("URL");
            this.conexion = DriverManager.getConnection(url, usuario, contraseña);
            return true;
        } catch (SQLException sql) {
            return false;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    public Connection getConexion() {
        return this.conexion;
    }

    public boolean cerrarConexion() {
        try {
            this.conexion.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean ejecutarConsulta() {
        try {
            PreparedStatement consulta = this.conexion.prepareStatement(sentencia,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.cursor = consulta.executeQuery();
            return true;
        } catch (SQLException sql) {
            return false;
        }
    }

    public boolean ejecutarConsultaActualizable() {
        try {
            PreparedStatement consulta = this.conexion.prepareStatement(sentencia,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            this.cursor = consulta.executeQuery();
            return true;
        } catch (SQLException sql) {
            return false;
        }
    }

    public void setConsulta(String sql) {
        this.sentencia = sql;
    }

    public ResultSet getCursor() {
        return this.cursor;
    }

    public boolean cerrarCursor() {
        try {
            this.cursor.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
