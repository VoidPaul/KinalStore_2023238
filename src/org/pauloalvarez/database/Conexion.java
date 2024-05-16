package org.pauloalvarez.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Paulo Alvarez
 */
public class Conexion {

    private Connection conexion;
    private static Conexion instancia;

    public Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbkinalstore?useSSL=false&serverTimezone=UTC", "2023238_IN5BV", "abc123!!");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static synchronized Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
}
