/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectointegrador.Config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author sofim
 */
public final class DatabaseConnection {

    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    static {
        loadProperties();
        loadDriver();
    }

    private static void loadProperties() {
        Properties props = new Properties();

        // Busca el archivo en el paquete Config
        // Primero intenta buscar en el paquete Config
        String resourcePath = "proyectointegrador/Config/db.properties";
        InputStream input = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream(resourcePath);
        
        // Si no lo encuentra, intenta en la raíz del classpath
        if (input == null) {
            input = DatabaseConnection.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");
        }

        try {
            if (input == null) {
                throw new RuntimeException("No se encontró db.properties. Buscado en: " + resourcePath + " y en la raíz del classpath");
            }

            props.load(input);

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");

        } catch (IOException e) {
            throw new RuntimeException("Error cargando db.properties: " + e.getMessage(), e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // Ignorar error al cerrar
                }
            }
        }
    }

    /**
     * Registra el driver JDBC de MySQL.
     */
    private static void loadDriver() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error cargando el driver JDBC: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna una nueva conexión JDBC.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
