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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JSeparator;

/**
 *
 * @author jivkowork
 */
public class Company {
    private JPanel panel;
    private JTextField firm;
    private JTextField phone;
    private JTextField email;
    
    public void showList() {
        JFrame frame = new JFrame("Фирма");
        DBStore db = new DBStore();
        frame.setBounds(300, 300, 658, 487);
        panel = new JPanel();
        panel.setLayout(null);
                
        String[] companyInfo;
        companyInfo = db.listCompany();
        double income = db.getIncome();
        double outcome = db.getOutcome();
        double profit = (income - outcome);
        double netProfit = profit*0.8;

        JLabel lblName = new JLabel("Име:\t\t" + companyInfo[0]);
        lblName.setBounds(50, 50, 186, 14);
        panel.add(lblName);

        JLabel lblMail = new JLabel("Е-mail:\t\t" + companyInfo[1]);
        lblMail.setBounds(50, 75, 186, 14);
        panel.add(lblMail);

        JLabel lblPhone = new JLabel("Телефон:\t\t" + companyInfo[2]);
        lblPhone.setBounds(50, 100, 186, 14);
        panel.add(lblPhone);

        JLabel lblAddress = new JLabel("Адрес:\t\t" + companyInfo[3]);
        lblAddress.setBounds(50, 125, 186, 14);
        panel.add(lblAddress);
        
        JLabel lblIncome = new JLabel("Приход:\t\t" + String.format("%.2f", income));
        lblIncome.setBounds(50, 225, 186, 14);
        panel.add(lblIncome);
        JLabel lblOutcome = new JLabel("Разход:\t\t" + String.format("%.2f", outcome));
        lblOutcome.setBounds(50, 250, 186, 14);
        panel.add(lblOutcome);
        JLabel lblProfit = new JLabel("Печалба:\t\t" + String.format("%.2f", profit));
        lblProfit.setBounds(50, 300, 186, 14);
        panel.add(lblProfit);
        JLabel lblNetProfit = new JLabel("Чиста печалба:\t\t" + String.format("%.2f", netProfit));
        lblNetProfit.setBounds(50, 325, 186, 14);
        panel.add(lblNetProfit);
        
        JButton btnNew = new JButton("Промени");
        btnNew.setBounds(50, 387, 89, 23);
        panel.add(btnNew);


        btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFrame newFrame = new JFrame("Фирма");
                newFrame.setVisible(true);
                newFrame.setBounds(150, 100, 658, 487);
                
                newFrame.add(showModify());
            }
        });
        
        frame.add(panel);
        
        frame.show();
    }
    
    public JPanel showModify() {
        panel = new JPanel();
        panel.setLayout(null);

        DBStore db = new DBStore();
        String[] companyInfo;
        companyInfo = db.listCompany();
        
        JLabel lblName = new JLabel("Фирма");
        lblName.setBounds(65, 31, 86, 14);
        panel.add(lblName);

        firm = new JTextField();
        firm.setBounds(128, 28, 126, 30);
        panel.add(firm);
        firm.setColumns(10);
        firm.setText(companyInfo[0]);
        
        JLabel lblPhone = new JLabel("Телефон");
        lblPhone.setBounds(65, 68, 86, 14);
        panel.add(lblPhone);

        phone = new JTextField();
        phone.setBounds(128, 65, 126, 30);
        panel.add(phone);
        phone.setColumns(10);
        phone.setText(companyInfo[2]);

        JLabel lblEmailId = new JLabel("Email");
        lblEmailId.setBounds(65, 115, 86, 14);
        panel.add(lblEmailId);

        email = new JTextField();
        email.setBounds(128, 112, 126, 30);
        panel.add(email);
        email.setColumns(10);
        email.setText(companyInfo[1]);

        JLabel lblAddress = new JLabel("Адрес");
        lblAddress.setBounds(65, 162, 86, 14);
        panel.add(lblAddress);
        
        JTextArea address = new JTextArea();
        address.setBounds(126, 157, 212, 40);
        panel.add(address);
        address.setText(companyInfo[3]);

        JButton btnClear = new JButton("Изчисти");

        btnClear.setBounds(312, 287, 89, 23);
        panel.add(btnClear);

        JButton btnSubmit = new JButton("Запази");

        btnSubmit.setBackground(Color.BLUE);
        btnSubmit.setForeground(Color.MAGENTA);
        btnSubmit.setBounds(65, 287, 89, 23);
        panel.add(btnSubmit);
        
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (firm.getText().isEmpty() || (phone.getText().isEmpty()) || (email.getText().isEmpty()) || (address.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Имате непопълнени полета");
                } else if (!phone.getText().matches("^[0-9\\/\\-\\s]+$")) {
                    JOptionPane.showMessageDialog(null, "Телефона е невалиден");    
                } else if (!email.getText().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
                    JOptionPane.showMessageDialog(null, "Пощата е невалидна");    
                } else {
                        // insert rows
                        if (db.updateCompany(firm.getText(), address.getText(), email.getText(), phone.getText())) {
                            JOptionPane.showMessageDialog(null, "Данните са въведени.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Възникна грешка! Обърнете се към администратор.");
                        }


                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firm.setText(null);
                phone.setText(null);
                email.setText(null);
                address.setText(null);
            }
        });
        
        return panel;
    }
}
