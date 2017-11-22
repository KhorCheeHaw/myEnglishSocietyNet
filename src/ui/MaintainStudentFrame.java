package ui;

import control.MaintainProgrammeControl;
import control.MaintainStudentControl;
import domain.Programme;
import domain.Student;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MaintainStudentFrame extends JFrame {

    private MaintainStudentControl studControl;
    private MaintainProgrammeControl progControl;
    private JTextField jtfId = new JTextField();
    private JTextField jtfIc = new JTextField();
    private JTextField jtfName = new JTextField();
    private JTextField jtfLevel = new JTextField();
    private JTextField jtfProgCode = new JTextField();
    private JTextField jtfYear = new JTextField();
    private JButton jbtAdd = new JButton("Create");
    private JButton jbtRetrieve = new JButton("Retrieve");
    private JButton jbtUpdate = new JButton("Update");
    private JButton jbtDelete = new JButton("Delete");

    public MaintainStudentFrame() {
        progControl = new MaintainProgrammeControl();
        studControl = new MaintainStudentControl();
        
        JPanel jpCenter = new JPanel(new GridLayout(6, 2));
        jpCenter.add(new JLabel("Student ID"));
        jpCenter.add(jtfId);
        jpCenter.add(new JLabel("Student IC"));
        jpCenter.add(jtfIc);
        jpCenter.add(new JLabel("Student Name"));
        jpCenter.add(jtfName);
        jpCenter.add(new JLabel("Level"));
        jpCenter.add(jtfLevel);
        jpCenter.add(new JLabel("Programme Code"));
        jpCenter.add(jtfProgCode);
        jpCenter.add(new JLabel("Year"));
        jpCenter.add(jtfYear);
        add(jpCenter);

        JPanel jpSouth = new JPanel();
        jpSouth.add(jbtAdd);
        jpSouth.add(jbtRetrieve);
        jpSouth.add(jbtUpdate);
        jpSouth.add(jbtDelete);
        add(jpSouth, BorderLayout.SOUTH);

        jbtRetrieve.addActionListener(new RetrieveListener());
        jbtAdd.addActionListener(new AddListener());
        jbtUpdate.addActionListener(new UpdateListener());
        jbtDelete.addActionListener(new DeleteListener());
        
        setTitle("Student CRUD");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void clearText(){
        jtfId.setText("");
        jtfIc.setText("");
        jtfLevel.setText("");
        jtfProgCode.setText("");
        jtfName.setText("");
        jtfYear.setText("");
        jtfId.requestFocusInWindow();
    }

    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = jtfId.getText();
            Student student = studControl.selectRecord(id);
            if(student != null){
                jtfIc.setText(student.getIc());
                jtfName.setText(student.getName());
                jtfLevel.setText(student.getLevel() + "");
                jtfProgCode.setText(student.getProgramme().getCode());
                jtfYear.setText(student.getYear() + "");                
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want delete this student?");
                if(option == JOptionPane.YES_OPTION){
                    studControl.deleteRecord(id);
                    JOptionPane.showMessageDialog(null, "Student deleted", "Record deleted", JOptionPane.INFORMATION_MESSAGE);
                    clearText();
                }
            }else{
                JOptionPane.showMessageDialog(null, "No such Student ID.", "Record Not Found", JOptionPane.ERROR_MESSAGE);
                clearText();
            }            
        }
    }

    private class UpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = jtfId.getText();
            Student student = studControl.selectRecord(id);
            if(student != null){
                student.setIc(jtfIc.getText());
                student.setName(jtfName.getText());
                student.setLevel(jtfLevel.getText().charAt(0));
                Programme programme = progControl.selectRecord(jtfProgCode.getText());
                student.setProgramme(programme);
                student.setYear(Integer.parseInt(jtfYear.getText()));

                studControl.updateRecord(student);
                JOptionPane.showMessageDialog(null, "Record updated", "Record updated", JOptionPane.INFORMATION_MESSAGE);
                clearText();
            }else{
                JOptionPane.showMessageDialog(null, "No such student id.", "Record Not Found", JOptionPane.ERROR_MESSAGE);
                clearText();
            }
        }
    }
    
    private class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String id = jtfId.getText();
            Student student = studControl.selectRecord(id);
            
            if (student != null) {
                JOptionPane.showMessageDialog(null, "This student id already exist", "Duplicate Record", JOptionPane.ERROR_MESSAGE);
            } else {
                Programme programme = progControl.selectRecord(jtfProgCode.getText());
                student = new Student(id, jtfIc.getText(), jtfName.getText(), jtfLevel.getText().charAt(0), programme, Integer.parseInt(jtfYear.getText()));
                studControl.addRecord(student);
                JOptionPane.showMessageDialog(null, "New student added", "Record added", JOptionPane.INFORMATION_MESSAGE);
                clearText();
            }
        }
    }
    
    private class RetrieveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String id = jtfId.getText();
            Student student = studControl.selectRecord(id);
            if (student != null) {
                jtfIc.setText(student.getIc());
                jtfName.setText(student.getName());
                jtfLevel.setText(student.getLevel() + "");
                jtfProgCode.setText(student.getProgramme().getCode());
                jtfYear.setText(student.getYear() + "");
            } else {
                JOptionPane.showMessageDialog(null, "No such student id.", "Record Not Found", JOptionPane.ERROR_MESSAGE);
                clearText();
            }
        }
    }

    public static void main(String[] args) {
          new MaintainStudentFrame();   
    }
}
