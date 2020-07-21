/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbu.company;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author jivkowork
 */
public class MyForm extends JFrame {	
    // Launch the application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MyForm frame = new MyForm();
                frame.setVisible(true);
            }
        });
    }

    // Create the frame
    public MyForm() {
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 300);
        setTitle("Каталог");

        JPanel mainPanel = new JPanel();
        getContentPane().add(mainPanel);

        JButton b1 = new JButton("Фирма"); 
        JButton b2 = new JButton("Служители"); 
        JButton b3 = new JButton("Добави служител");
        JButton b4 = new JButton("Добави Детайл");
        JButton b5 = new JButton("Производство служители");
        JButton b6 = new JButton("Изработени детайли"); 
        JButton b7 = new JButton("Стартирай производство"); 
  
        // add buttons and textfield to panel 
        mainPanel.add(b1); 
        mainPanel.add(b2); 
        mainPanel.add(b3);
        mainPanel.add(b4);
        mainPanel.add(b5);
        mainPanel.add(b6);
        mainPanel.add(b7);

        Company company = new Company();
        Employees employees = new Employees();
        Products products = new Products();
        Production production = new Production();
        
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                company.showList();
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                employees.showList();
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFrame newFrame = new JFrame("Нов служител");
                newFrame.setVisible(true);
                newFrame.setBounds(150, 100, 658, 487);
                
                newFrame.add(employees.showModify());
            }
        });
        
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFrame newFrame = new JFrame("Нов детайл");
                newFrame.setVisible(true);
                newFrame.setBounds(150, 100, 658, 487);
                
                newFrame.add(products.showModify());
            }
        });
        
        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                production.showEmployeeList();
            }
        });
        
        b6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                production.showList();
            }
        });
        b7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFrame newFrame = new JFrame("Производство");
                newFrame.setVisible(true);
                newFrame.setBounds(150, 100, 658, 487);
                
                newFrame.add(production.showModify());
            }
        });
    }
}

