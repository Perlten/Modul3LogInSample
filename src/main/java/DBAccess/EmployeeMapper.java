/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import FunctionLayer.FOGException;
import FunctionLayer.LogicFacade;
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

/**
 *
 * @author Perlt
 */
public class EmployeeMapper {

    private Connection con;

    /**
     * Set connection to the live database
     *
     * @throws FOGException
     */
    public EmployeeMapper() throws FOGException {
        try {
            con = new LiveConnection().connection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new FOGException("Could not find connection", Level.WARNING);
        }
    }

    /**
     * Give the connection to which the mapper should connect to.
     *
     * @param con
     */
    public EmployeeMapper(Connection con) {
        this.con = con;
    }

    /**
     * If user exits in database that corresponse to the username and password
     * given the method wil return said employee if not the method will throw a
     * exception.
     *
     * @param username Username of employee
     * @param password Password is encryptet
     * @return Employee
     * @throws FOGException
     */
    public Employee verfyLogin(String username, String password) throws FOGException {
        String sql = "SELECT idemployee, username, roleid, firstname, lastname, email, employed, date_created, reset_password FROM employee WHERE BINARY username = ? and BINARY password = ? AND employed = true";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            List<Employee> list = convert(rs);
            return list.get(0);
        } catch (SQLException ex) {
            throw new FOGException("Could not verify login", Level.INFO);
        }
    }

    /**
     * Creates Employee
     *
     * @param emp
     * @param password
     * @param salt
     * @throws FOGException
     */
    public void createEmployee(Employee emp, String password, String salt) throws FOGException {
        String sql = "INSERT INTO employee(username, roleid, firstname, lastname, password, email, salt)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, emp.getUsername());
            ps.setInt(2, emp.getAuthenticationLevel());
            ps.setString(3, emp.getFirstname());
            ps.setString(4, emp.getLastname());
            ps.setString(5, password);
            ps.setString(6, emp.getEmail());
            ps.setString(7, salt);
            ps.execute();
        } catch (SQLException ex) {
            throw new FOGException("Could not create employee", Level.INFO);
        }
    }

    /**
     * Gets Employee on email
     *
     * @param email
     * @return
     * @throws FOGException
     */
    public Employee getEmployeeByEmail(String email) throws FOGException {
        String sql = "SELECT idemployee, username, roleid, firstname, lastname, email, employed, date_created, reset_password FROM employee WHERE email = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            List<Employee> list = convert(rs);
            return list.get(0);
        } catch (SQLException e) {
            throw new FOGException("Could not find employee", Level.INFO);
        }
    }

    /**
     * Gets all Employees
     *
     * @param noFired Include fired employees
     * @return List with Employee
     * @throws FOGException
     */
    public List<Employee> getAllEmployees(boolean noFired) throws FOGException {
        String sql = "SELECT * FROM employee";
        if (noFired) {
            sql += " where employed = true";
        }
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            return convert(rs);
        } catch (SQLException e) {
            throw new FOGException("Could not find employees", Level.WARNING);
        }
    }

    /**
     * Gets Employee by id
     *
     * @param id
     * @return Employee
     * @throws FOGException
     */
    public Employee getEmployeeById(int id) throws FOGException {
        String sql = "SELECT * FROM employee WHERE idemployee = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            List<Employee> list = convert(rs);
            return list.get(0);
        } catch (SQLException e) {
            throw new FOGException("Could not find employee", Level.INFO);
        }
    }

    /**
     * Converts a Prepared Statements into a List of Employee
     *
     * @param rs ResultSet
     * @return List with Employee
     * @throws SQLException
     * @throws FOGException
     */
    private List<Employee> convert(ResultSet rs) throws SQLException, FOGException {
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
            boolean resetPassword = rs.getBoolean("reset_password");
            list.add(new Employee(employeeId, authenticationLevel, username, firstName, lastName, email, employed, date, resetPassword));
        }
        if (list.isEmpty()) {
            throw new FOGException("Could not find employee(s)", Level.WARNING);
        }
        return list;
    }

    /**
     * Update Employee
     *
     * @param employee
     * @throws FOGException
     */
    public void updateEmployee(Employee employee) throws FOGException {
        String sql = "UPDATE employee SET username = ?, roleid = ?, firstname = ?, lastname = ?, email = ? where idemployee = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, employee.getUsername());
            ps.setInt(2, employee.getAuthenticationLevel());
            ps.setString(3, employee.getFirstname());
            ps.setString(4, employee.getLastname());
            ps.setString(5, employee.getEmail());
            ps.setInt(6, employee.getEmployeeId());
            ps.execute();
        } catch (SQLException e) {
            throw new FOGException("Could not update employee", Level.WARNING);
        }
    }

    /**
     * Fire Employee
     *
     * @param employeeId
     * @throws FOGException
     */
    public void fireEmployee(int employeeId) throws FOGException {
        String sql = "UPDATE employee SET employed = false WHERE idemployee = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, employeeId);
            ps.execute();
        } catch (SQLException e) {
            throw new FOGException("Could not fire Employee", Level.WARNING);
        }
    }

    /**
     * Reset password
     *
     * @param employeeId
     * @throws FOGException
     */
    public void resetPassword(int employeeId) throws FOGException {
        String sql = "UPDATE employee SET reset_password = true WHERE idemployee = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, employeeId);
            ps.execute();
        } catch (SQLException e) {
            throw new FOGException("Could not reset password", Level.WARNING);
        }
    }

    /**
     * Changes password for Employee
     *
     * @param employeeId
     * @param newPassword
     * @param salt
     * @param resetPassword
     * @throws FOGException
     */
    public void changePassword(int employeeId, String newPassword, String salt, boolean resetPassword) throws FOGException {
        String sql = "UPDATE employee SET reset_password = ?, password = ?, salt = ? WHERE idemployee = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, resetPassword);
            ps.setString(2, newPassword);
            ps.setString(3, salt);
            ps.setInt(4, employeeId);
            ps.execute();
        } catch (SQLException e) {
            throw new FOGException("Could not change password", Level.WARNING);
        }
    }

    /**
     * Gets salt
     *
     * @param username
     * @return String
     * @throws FOGException
     */
    public String getSalt(String username) throws FOGException {
        String sql = "SELECT salt FROM employee where username = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            throw new FOGException("Could not get salt", Level.WARNING);
        }
    }

    public Connection getCon() {
        return con;
    }

    /**
     * Exam purpose.
     *
     * Delete later
     *
     * @throws FunctionLayer.FOGException
     */
    public static void resetAdmin() throws FOGException {
        try {
            String sql = "DELETE FROM employee WHERE email='FogCarportAdm@gmail.com' OR username='admin'";
            Connection staticCon = new LiveConnection().connection();
            PreparedStatement ps = staticCon.prepareStatement(sql);
            ps.execute();
            Employee emp = new Employee("admin", "admin", "admin", "FogCarportAdm@gmail.com", 3);
            LogicFacade.createEmployee(emp);
            emp = LogicFacade.getEmployeeByEmail(emp.getEmail());
            DataFacade.changePassword(emp.getEmployeeId(), "FB96595462E15B1E814EA9C784428B0424136067", "aovppjgmtr");
        } catch (SQLException | ClassNotFoundException e) {
            throw new FOGException("Could not reset admin user!", Level.SEVERE);
        }

    }
}
