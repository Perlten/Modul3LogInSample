/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Perlt
 */
public class TestConnection implements Connector {

    private static final String URL = "jdbc:mysql://159.89.19.132:3306/fog_test?autoReconnect=true&serverTimezone=UTC&useSSL=false&allowMultiQueries=true";
    private static final String USERNAME = "perltadmin";
    private static final String PASSWORD = "#15PepsiMax";

    private static Connection singleton;

    @Override
    public void setConnection(Connection con) {
        singleton = con;
    }

    @Override
    public Connection connection() throws ClassNotFoundException, SQLException {
        if (singleton == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            singleton = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return singleton;
    }
}
