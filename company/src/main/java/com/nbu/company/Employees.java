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
import javax.swing.JComboBox;
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
public class Employees {
    private JPanel panel;
    private JTextField name;
    private JTextField phone;
    private JTextField email;
    public final static double L1SALARY = 4.72;
    public final static double L2SALARY = 7.28;
    
    public void showList() {
        JFrame frame = new JFrame("Служители");
        frame.setBounds(300, 300, 658, 487);
        panel = new JPanel();
        panel.setLayout(null);       

        DBStore db = new DBStore();
        JTable jt = db.listEmployees();

        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(5,5,600,400);
        
        panel.add(sp);
        frame.add(panel);
        
        frame.show();
    }
    
    public JPanel showModify() {
        panel = new JPanel();
        panel.setLayout(null);

        JLabel lblName = new JLabel("Име");
        lblName.setBounds(65, 31, 86, 14);
        panel.add(lblName);

        name = new JTextField();
        name.setBounds(128, 28, 126, 30);
        panel.add(name);
        name.setColumns(10);
        
        JLabel lblPhone = new JLabel("Телефон");
        lblPhone.setBounds(65, 68, 86, 14);
        panel.add(lblPhone);

        phone = new JTextField();
        phone.setBounds(128, 65, 126, 30);
        panel.add(phone);
        phone.setColumns(10);

        JLabel lblEmailId = new JLabel("Email");
        lblEmailId.setBounds(65, 115, 86, 14);
        panel.add(lblEmailId);

        email = new JTextField();
        email.setBounds(128, 112, 126, 30);
        panel.add(email);
        email.setColumns(10);

        JLabel lblLevel = new JLabel("Ниво");
        lblLevel.setBounds(65, 188, 67, 14);
        panel.add(lblLevel);

        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.addItem("Select");
        comboBox.addItem("1");
        comboBox.addItem("2");
        comboBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                }
        });
        comboBox.setBounds(128, 185, 91, 20);
        panel.add(comboBox);

        JButton btnClear = new JButton("Изчисти");

        btnClear.setBounds(312, 287, 89, 23);
        panel.add(btnClear);

        JButton btnSubmit = new JButton("Добави");

        btnSubmit.setBackground(Color.BLUE);
        btnSubmit.setForeground(Color.MAGENTA);
        btnSubmit.setBounds(65, 287, 89, 23);
        panel.add(btnSubmit);
        
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                
                if (name.getText().isEmpty()||(phone.getText().isEmpty())||(email.getText().isEmpty())||(comboBox.getSelectedItem().equals("Select"))) {
                    JOptionPane.showMessageDialog(null, "Имате непопълнени полета");
                } else if (!email.getText().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
                    JOptionPane.showMessageDialog(null, "E-mail not correct"); 
                } else if (!phone.getText().matches("^[0-9\\/\\-\\s]+$")) {
                    JOptionPane.showMessageDialog(null, "Телефона е невалиден");    
                } else {
                        DBStore db = new DBStore();
                        // insert rows
                        if (db.insertEmployee(name.getText(), phone.getText(), email.getText(), (String)comboBox.getSelectedItem())) {
                            JOptionPane.showMessageDialog(null, "Данните са въведени.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Възникна грешка! Обърнете се към администратор.");
                        }


                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        name.setText(null);
                        phone.setText(null);
                        email.setText(null);
                        comboBox.setSelectedItem("Select");


                }
        });
        
        return panel;
    }
}
