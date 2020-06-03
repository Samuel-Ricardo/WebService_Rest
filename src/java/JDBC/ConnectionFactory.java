/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Samuel
 */
public class ConnectionFactory {

    private static final String DRIVER = "com.mysql.jdbc.Driver";  // Driver location / local do Driver / 
    private static final String URL = "jdbc:mysql://localhost:3308/agendafx";  // Database URL used in the MySQL case / URL do banco de dados usado no caso MySQL /
    private static final String USER = "root"; // usuario 
    private static final String PASS = ""; //senha

    public static Connection getConnection() {  // method to get the connection / metodo para pegar a conexao /

        try {

            Class.forName(DRIVER); //pegando o Driver / getting the driver

            return (Connection) DriverManager.getConnection(URL, USER, PASS); // performing the Connection to the database / realizando a Conexao com o banco de dados / 

        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Nao foi possivel realizar conexao com o banco: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
            throw new RuntimeException("Nao foi possivel realizar conexao com o banco: " + ex);

        }

    }

    public static void closeConnection(Connection connection) { // method to close the connection / metodo para encerrar a conexao /

        try {

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Nao foi possivel encerrar conexao com o banco: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
        }

    }

    public static void closeConnection(Connection connection, PreparedStatement statement ){ // method to close the connection / metodo para encerrar a conexao /

        closeConnection(connection);

        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Nao foi possivel encerrar conexao com o banco: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
        }

    }

    public static void closeConnection(Connection connection, PreparedStatement statement, ResultSet result) { // method to close the connection / metodo para encerrar a conexao /

        closeConnection(connection, statement);

        try {

            if (result != null) {
                result.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Nao foi possivel encerrar conexao com o banco: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
        }

    }

}
