/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import FunctionLayer.FOGException;
import FunctionLayer.entities.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Perlt
 */
public class EmployeeMapper {

    public static Employee verfyLogin(String username, String password) throws FOGException {
//        TODO: change roleid name;
        String sql = "SELECT idemployee, username, roleid, firstname, lastname, email, employed, date_created FROM fog.employee WHERE BINARY username = ? and BINARY password = ?";
        try {
            Connection con = Connector.connection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            List<Employee> list = getEmployees(rs);
            return list.get(0);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new FOGException(ex.getMessage());
        }
    }

    public static Employee getEmployeeByEmail(String email) throws FOGException {
        String sql = "SELECT idemployee, username, roleid, firstname, lastname, employed, date_created FROM fog.employee WHERE email = ?";
        try {
            Connection con = Connector.connection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            List<Employee> list = getEmployees(rs);
            return list.get(0);
        } catch (ClassNotFoundException | SQLException e) {
            throw new FOGException("Could not find employee");
        }
    }

    public static void changePasswordForEmployee(int id, String password) throws FOGException {
        String sql = "UPDATE fog.employee SET password = ? WHERE idemployee = ?";
        try {
            Connection con = Connector.connection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, password);
            ps.setInt(2, id);
            ps.execute();
        } catch (ClassNotFoundException | SQLException e) {
            throw new FOGException("Could not change password");
        }
    }

    public static List<Employee> getAllEmployees() throws FOGException {
        String sql = "SELECT * FROM fog.employee";
        try{
            Connection con = Connector.connection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            return getEmployees(rs);
        }catch(ClassNotFoundException | SQLException e){
            throw new FOGException("Could not find employees");
        }
    }

    private static List<Employee> getEmployees(ResultSet rs) throws SQLException {
        List<Employee> list = new ArrayList<>();
        while (rs.next()) {
            int employeeId = rs.getInt("idemployee");
            int authenticationLevel = rs.getInt("roleid");
            String username = rs.getString("username");
            String firstName = rs.getString("firstname");
            String email = rs.getString("email");
            String lastName = rs.getString("lastname");
            boolean employed = rs.getBoolean("employed");
            Calendar date = Calendar.getInstance();
            Timestamp ts = rs.getTimestamp("date_created");
            date.setTime((Date) ts);
            list.add(new Employee(employeeId, authenticationLevel, username, firstName, lastName, email, employed, date));
        }
        return list;
    }

}
