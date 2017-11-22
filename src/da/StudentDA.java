package da;

import domain.Programme;
import domain.Student;
import java.sql.*;
import javax.swing.*;

public class StudentDA {
    private String host = "jdbc:derby://localhost:1527/collegedb2";
    private String user = "username";
    private String password = "username";
    private String tableName = "Student";
    private Connection conn;
    private PreparedStatement stmt;
    private ProgrammeDA progDA;
    
    public StudentDA() {
        createConnection();
        progDA = new ProgrammeDA();
    }
    
    public Student getRecord(String id) {
        String queryStr = "SELECT * FROM " + tableName + " WHERE ID = ?";
        Student student = null;
        try {
            stmt = conn.prepareStatement(queryStr);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Programme programme = progDA.getRecord(rs.getString("ProgrammeCode"));
                student = new Student(id, rs.getString("IC"), rs.getString("Name"), rs.getString("Level").charAt(0), programme, rs.getInt("Yr"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return student;
    }
    
    public void addRecord(Student student){
        String insertStr = "Insert Into " + tableName + " VALUES(?, ?, ?, ?, ?, ?)";
        try{
            stmt = conn.prepareStatement(insertStr);
            stmt.setString(1, student.getId());
            stmt.setString(2, student.getIc());
            stmt.setString(3, student.getName());
            stmt.setString(4, String.valueOf(student.getLevel()));
            stmt.setString(5, student.getProgramme().getCode());
            stmt.setInt(6, student.getYear());
            stmt.executeUpdate();
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void updateRecord(Student student){
        try{
            String updateStr = "UPDATE " + tableName + " SET IC = ?, Name = ?, Level = ?, ProgrammeCode = ?, Yr = ? WHERE ID = ?";
            System.out.println("\n***TRACE Line 63: sql string: " + updateStr);
            stmt = conn.prepareStatement(updateStr);
            System.out.println("\n***TRACE Line 63: sql string: " + updateStr);
            stmt.setString(1, student.getIc());
            stmt.setString(2, student.getName());
            stmt.setString(3, String.valueOf(student.getLevel()));
            stmt.setString(4, student.getProgramme().getCode());
            System.out.println("\n***TRACE Line 63: sql string: " + updateStr);
            stmt.setInt(5, student.getYear());
            System.out.println("\n***TRACE Line 63: sql string: " + updateStr);
            stmt.setString(6, student.getId());
            stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }  
    }
    
    public void deleteRecord(String id){
        try{
            String deleteStr = "DELETE FROM " + tableName + " WHERE ID = ?";
            stmt = conn.prepareStatement(deleteStr);
            stmt.setString(1, id);
            stmt.executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
        
    private void createConnection() {
        try {
            conn = DriverManager.getConnection(host, user, password);
            System.out.println("***TRACE: Connection established.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void shutDown() {
        if (conn != null)
            try {
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        StudentDA da = new StudentDA();
        Student student = da.getRecord("11WAD11111");
        System.out.println("***TRACE: Retrived record: " + student);
        student.setYear(5);
        da.updateRecord(student);
        System.out.println("***TRACE: Update record: " + student);
        System.out.println(student);
    }
}
