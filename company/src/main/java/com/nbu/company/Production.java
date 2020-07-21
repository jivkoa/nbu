/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbu.company;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author jivkowork
 */
public class Production {
    private JPanel panel;
    private JTextField count;
    
    public void showList() {
        JFrame frame = new JFrame("Изработени детайли");
        frame.setBounds(300, 300, 658, 487);
        panel = new JPanel();
        panel.setLayout(null);        

        DBStore db = new DBStore();
        JTable jt = db.listDetails();

        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(5,5,600,400);
        
        panel.add(sp);
        frame.add(panel);
        
        frame.show();
    }

    public void showEmployeeList() {
        JFrame frame = new JFrame("Служители производство");
        frame.setBounds(300, 300, 658, 487);
        panel = new JPanel();
        panel.setLayout(null);        

        DBStore db = new DBStore();
        JTable jt = db.listEmployeeDetails();

        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(5,5,600,400);
        
        panel.add(sp);
        frame.add(panel);
        
        frame.show();
    }
        
    public JPanel showModify() {
        panel = new JPanel();
        panel.setLayout(null);
        
        JLabel lblCount = new JLabel("Брой детайли");
        lblCount.setBounds(65, 31, 106, 14);
        panel.add(lblCount);
        
        count = new JTextField();
        count.setBounds(228, 28, 126, 20);
        panel.add(count);
        count.setColumns(10);
        
        JButton btnClear = new JButton("Изчисти");

        btnClear.setBounds(228, 157, 89, 23);
        panel.add(btnClear);

        JButton btnSubmit = new JButton("Стартирай");

        btnSubmit.setBackground(Color.BLUE);
        btnSubmit.setForeground(Color.MAGENTA);
        btnSubmit.setBounds(65, 157, 109, 23);
        panel.add(btnSubmit);
        
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {           
                if (count.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Имате непопълнени полета");
                } else if (!count.getText().matches("^[\\d]+$")) {
                    JOptionPane.showMessageDialog(null, "Бройката трябва да е число");
                } else {
                    DBStore db = new DBStore();
                    if (db.startProduction(Integer.parseInt(count.getText()))) {
                        JOptionPane.showMessageDialog(null, "Продукцията е пусната за производство");
                    } else {
                        JOptionPane.showMessageDialog(null, "Възникна грешка! Обърнете се към администратор.");
                    }
                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                count.setText(null);
            }
        });
        
        return panel;
    }
}