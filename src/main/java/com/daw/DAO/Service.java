/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daw.DAO;

import java.io.File;
import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

;

/**
 *
 * @author Javier Ruiz Gomez
 */
public abstract class Service {

    ComponenteBBDD componenteBBDD;

    public Service(ComponenteBBDD componenteBBDD) {
        makeConnection();
    }

    public void makeConnection() {
        Properties properties = new Properties();
        this.componenteBBDD = new ComponenteBBDD();
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("propiedades.properties");
            if (input == null) {
                System.out.println("No se encontró el archivo propiedades.properties en WEB-INF/properties.");
                return;
            }
            properties.load(input);
            boolean isConnected = componenteBBDD.setConexion(properties);
            if (isConnected) {
                System.out.println("Conexión a la base de datos establecida con éxito.");
            } else {
                System.out.println("Error al establecer la conexión con la base de datos.");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el archivo de configuración: " + e.getMessage());
        }
    }

    public boolean cerrarConexion() {
        if (this.componenteBBDD.cerrarCursor()) {
            return this.componenteBBDD.cerrarConexion();
        } else {
            return false;
        }
    }
        

    public abstract List<Object> select();        

    public abstract boolean delete(int id);

    public abstract boolean insert(Object o);

    public abstract boolean update(Object o, int id);

}
