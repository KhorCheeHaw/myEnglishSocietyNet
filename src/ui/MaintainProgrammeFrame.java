package ui;

import control.MaintainProgrammeControl;
import domain.Programme;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MaintainProgrammeFrame extends JFrame {

    private MaintainProgrammeControl progControl;
    private JTextField jtfCode = new JTextField();
    private JTextField jtfName = new JTextField();
    private JTextField jtfFaculty = new JTextField();
    private JButton jbtAdd = new JButton("Create");
    private JButton jbtRetrieve = new JButton("Retrieve");
    private JButton jbtUpdate = new JButton("Update");
    private JButton jbtDelete = new JButton("Delete");

    public MaintainProgrammeFrame() {
        progControl = new MaintainProgrammeControl();
        
        JPanel jpCenter = new JPanel(new GridLayout(3, 2));
        jpCenter.add(new JLabel("Programme Code"));
        jpCenter.add(jtfCode);
        jpCenter.add(new JLabel("Programme Name"));
        jpCenter.add(jtfName);
        jpCenter.add(new JLabel("Faculty"));
        jpCenter.add(jtfFaculty);
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
        
        setTitle("Programme CRUD");
        setSize(600, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        
    }

    private void clearText(){
        jtfCode.setText("");
        jtfName.setText("");
        jtfFaculty.setText("");
    }

    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String code = jtfCode.getText();
            Programme programme = progControl.selectRecord(code);
            if(programme != null){
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want delete this programme?");
                if(option == JOptionPane.YES_OPTION){
                    progControl.deleteRecord(code);
                    JOptionPane.showMessageDialog(null, "Programme deleted", "Record deleted", JOptionPane.INFORMATION_MESSAGE);
                    clearText();
                }
            }else{
                JOptionPane.showMessageDialog(null, "No such programme code.", "Record Not Found", JOptionPane.ERROR_MESSAGE);
                clearText();
            }            
        }
    }

    private class UpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String code = jtfCode.getText();
            Programme programme = progControl.selectRecord(code);
            if(programme != null){
                programme.setName(jtfName.getText());
                programme.setFaculty(jtfFaculty.getText());
                
                progControl.updateRecord(programme);
                JOptionPane.showMessageDialog(null, "Programme updated", "Record updated", JOptionPane.INFORMATION_MESSAGE);
                clearText();
            }else{
                JOptionPane.showMessageDialog(null, "No such programme code.", "Record Not Found", JOptionPane.ERROR_MESSAGE);
                clearText();
            }
        }
    }
    
    private class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String code = jtfCode.getText();
            Programme programme = progControl.selectRecord(code);
            if (programme != null) {
                JOptionPane.showMessageDialog(null, "This programme code already exist", "Record Not Found", JOptionPane.ERROR_MESSAGE);
            } else {
                programme = new Programme(code, jtfName.getText(), jtfFaculty.getText());
                progControl.addRecord(programme);
                JOptionPane.showMessageDialog(null, "New programme added", "Record added", JOptionPane.INFORMATION_MESSAGE);
                clearText();
            }
        }
    }
    
    private class RetrieveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String code = jtfCode.getText();
            Programme programme = progControl.selectRecord(code);
            if (programme != null) {
                jtfName.setText(programme.getName());
                jtfFaculty.setText(programme.getFaculty());
            } else {
                JOptionPane.showMessageDialog(null, "No such programme code.", "Record Not Found", JOptionPane.ERROR_MESSAGE);
                clearText();
            }
        }
    }

    public static void main(String[] args) {
          new MaintainProgrammeFrame();   
    }
}
