package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuFrame extends JFrame {
    
    private JButton jbtProg = new JButton("Maintain Programme");
    private JButton jbtStud = new JButton("Maintain Student");
    
    public MenuFrame(){
        setLayout(new GridLayout(2, 1, 10, 10));
        add(jbtProg);
        add(jbtStud);
        
        jbtProg.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){
                new MaintainProgrammeFrame();
            }
        });
        
        jbtStud.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){
                new MaintainStudentFrame();
            }
        });
        
        setTitle("Maintain Information");
        setSize(280,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args){
        new MenuFrame();
    }
}