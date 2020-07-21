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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.util.HashMap;

/**
 *
 * @author jivkowork
 */
public class Products {
    private JPanel panel;
    private JTextField name;
    private JTextField code;
    private JTextField price;
    private double matPrice;
    private JCheckBox leather, glue, thread, foam, clip;
    
    public void showList() {
        JFrame frame = new JFrame("Детайли");
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
    
    public JPanel showModify() {
        panel = new JPanel();
        panel.setLayout(null);

        matPrice = 0;
        HashMap<String, Double> matPrices = new HashMap<String, Double>();
        matPrices.put("leather", 25.50);
        matPrices.put("glue", 7.25);
        matPrices.put("thread", 2.50);
        matPrices.put("foam", 3.85);
        matPrices.put("clip", 2.25);
        
        
        JLabel lblName = new JLabel("Наименование");
        lblName.setBounds(65, 31, 106, 14);
        panel.add(lblName);
        
        name = new JTextField();
        name.setBounds(228, 28, 126, 20);
        panel.add(name);
        name.setColumns(10);

        JLabel lblCode = new JLabel("Код");
        lblCode.setBounds(65, 68, 86, 14);
        panel.add(lblCode);

        code = new JTextField();
        code.setBounds(228, 65, 126, 30);
        panel.add(code);
        code.setColumns(10);

        JLabel lblPrice = new JLabel("Цена");
        lblPrice.setBounds(65, 104, 86, 14);
        panel.add(lblPrice);

        price = new JTextField();
        price.setBounds(228, 102, 126, 30);
        panel.add(price);
        price.setColumns(10);
        
        JLabel lblMaterial = new JLabel("Материали");
        lblMaterial.setBounds(65, 141, 86, 14);
        panel.add(lblMaterial);
        
        leather = new JCheckBox("Кожа");
        glue = new JCheckBox("Лепило");
        thread = new JCheckBox("Конци");
        foam = new JCheckBox("Пяна");
        clip = new JCheckBox("Скоби");
        
        leather.setBounds(228, 141, 100, 100); 
        glue.setBounds(228, 206, 100, 100); 
        thread.setBounds(328, 141, 100, 100); 
        foam.setBounds(328, 206, 100, 100);
        clip.setBounds(428, 141, 100, 100); 

        
        panel.add(leather);
        panel.add(glue);
        panel.add(thread);
        panel.add(foam);
        panel.add(clip);
        
        JButton btnClear = new JButton("Изчисти");

        btnClear.setBounds(228, 357, 89, 23);
        panel.add(btnClear);

        JButton btnSubmit = new JButton("Добави");

        btnSubmit.setBackground(Color.BLUE);
        btnSubmit.setForeground(Color.MAGENTA);
        btnSubmit.setBounds(65, 357, 89, 23);
        panel.add(btnSubmit);
        
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (leather.isSelected()) {
                    matPrice += matPrices.get("leather");
                }

                if (glue.isSelected()) {
                    matPrice += matPrices.get("glue");
                }

                if (thread.isSelected()) {
                    matPrice += matPrices.get("thread");
                }

                if (foam.isSelected()) {
                    matPrice += matPrices.get("foam");
                }

                if (clip.isSelected()) {
                    matPrice += matPrices.get("clip");
                }                
                
                if (name.getText().isEmpty() || code.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Имате непопълнени полета");
                } else if (!price.getText().matches("^[0-9]+(\\.){0,1}[0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "Цената е невалидна");
                } else if (matPrice == 0) {
                    JOptionPane.showMessageDialog(null, "Не са въведени материали");
                } else {
                    DBStore db = new DBStore();
                    // insert rows
                    if (db.insertProduct(name.getText(), code.getText(), Double.parseDouble(price.getText()), matPrice)) {
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
                code.setText(null);
                price.setText(null);
                leather.setSelected(false);
                glue.setSelected(false);
                thread.setSelected(false);
                foam.setSelected(false);
                clip.setSelected(false);
            }
        });
        
        return panel;
    }
}