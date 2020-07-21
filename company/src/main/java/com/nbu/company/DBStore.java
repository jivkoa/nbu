/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbu.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

class DBStore {
    private Connection connect() {
        // SQLite connection string
	String url = "jdbc:sqlite:/Users/jivkowork/NetBeansProjects/company/main.db";
	Connection conn = null;
	try {
            conn = DriverManager.getConnection(url);
	} catch (SQLException e) {
            System.out.println(e.getMessage());
	}
	return conn;
    }

    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        
        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        
        return new DefaultTableModel(data, columnNames);

    }

    public String[] listCompany() {
        String[] result = new String[4];
        String sql = "SELECT name, email, phone, address FROM company";
        
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                result[0] = rs.getString(1);
                result[1] = rs.getString(2);
                result[2] = rs.getString(3);
                result[3] = rs.getString(4);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return result;
    }

    public JTable listEmployees() {
        JTable table = new JTable();
        String sql = "SELECT name, phone, email, level FROM employees";
        
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {
            
            table = new JTable(buildTableModel(rs));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return table;
    }

    public JTable listDetails() {
        JTable table = new JTable();
        String sql = "SELECT pr.code, pr.name, COUNT(p.id) AS amount FROM production p, products pr WHERE p.product_id = pr.id GROUP BY p.product_id";
        
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {
            
            table = new JTable(buildTableModel(rs));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return table;
    }
    
    public JTable listEmployeeDetails() {
        JTable table = new JTable();
        String sql = "SELECT e.name AS employee_name, pr.name AS product_name, COUNT(p.id) AS amount FROM production p, products pr, employees e WHERE p.employee_id = e.id AND p.product_id = pr.id GROUP BY p.employee_id, p.product_id";
        
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {
            
            table = new JTable(buildTableModel(rs));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return table;
    }    
    public boolean updateCompany(String name, String address, String email, String phone) {
        String sql = "UPDATE company SET name = ?, address = ?, email = ?, phone = ? WHERE id = 1";
        
	try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
		pstmt.setString(1, name);
		pstmt.setString(2, address);
		pstmt.setString(3, email);
		pstmt.setString(4, phone);
		pstmt.executeUpdate();
	} catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
	}
        
        return true;
    }
    
    public boolean insertEmployee(String name, String phone, String email, String level) {
	String sql = "INSERT INTO employees(name, phone, email, level) VALUES(?, ?, ?, ?)";
        
	try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
		pstmt.setString(1, name);
		pstmt.setString(2, phone);
		pstmt.setString(3, email);
		pstmt.setString(4, level);
		pstmt.executeUpdate();
	} catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
	}
        
        return true;
    }

    public boolean insertProduct(String name, String code, double price, double matPrice) {
	String sql = "INSERT INTO products(name, code, price, mat_price) VALUES(?, ?, ?, ?)";
        
	try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
		pstmt.setString(1, name);
		pstmt.setString(2, code);
		pstmt.setDouble(3, price);
		pstmt.setDouble(4, matPrice);
		pstmt.executeUpdate();
	} catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
	}
        
        return true;
    }
    
    private int getRandom(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }
    
    public boolean startProduction(int amount) {
        String employees = "SELECT id, level FROM employees ORDER BY RANDOM() LIMIT 1";
        String products = "SELECT id FROM products";
        String production = "INSERT INTO production(product_id, employee_id, prd_price) VALUES(?, ?, ?)";
        double prdPrice;
        
        for (int i=1; i<=amount; i++) {
            int employee_id;
            int level;
            
            try (Connection conn = this.connect();
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(products)) {

                int product_id;
                int price;
                while(rs.next()) {
                    product_id = rs.getInt(1);
                    
                    try (Statement stmt2  = conn.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(employees)) {
                        if(rs2.next()) {
                            employee_id = rs2.getInt(1);
                            level = rs2.getInt(2);
                            
                            switch(level) {
                                case 1:
                                    prdPrice = getRandom(4, 6) * Employees.L1SALARY;
                                    break;
                                case 2:
                                    prdPrice = getRandom(1, 3) * Employees.L2SALARY;
                                    break;
                                default:
                                    prdPrice = 0;
                            }
                            
                            try (PreparedStatement pstmt = conn.prepareStatement(production)) {
                                    pstmt.setInt(1, product_id);
                                    pstmt.setInt(2, employee_id);
                                    pstmt.setDouble(3, prdPrice);
                                    pstmt.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                                return false;
                            }
                        }

                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        
        }

        return true;
    }
    
    public double getIncome() {
        double income = 0;
        String sql = "SELECT SUM(pr.price) FROM production p, products pr WHERE p.product_id = pr.id";
        
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {
            
            income = (rs.next()) ? rs.getDouble(1) : 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return income;
    }

    public double getOutcome() {
        double outcome = 0;
        String sql = "SELECT SUM(p.prd_price)+SUM(pr.mat_price) FROM production p, products pr WHERE p.product_id = pr.id;";
        
        try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {
            
            outcome = (rs.next()) ? rs.getDouble(1) : 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return outcome;
    }
}
