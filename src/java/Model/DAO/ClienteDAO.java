/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import JDBC.ConnectionFactory;
import com.mysql.jdbc.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Cliente;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author tiago
 */
public class ClienteDAO {

    private Connection connection;

   
    public boolean insert(Cliente cliente) {

        connect();
        PreparedStatement statement = null;
        String sql = "INSERT INTO cliente (nome, sexo, dataNascimento, telefone, email, cpf, endereco, CEP) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";  // sql Command  //  comando sql

        /*
            
            Colunas:   //    Columns:
            idClient int(11) AI PK
            nome varchar(50)
            sexo enum('Mascolino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(100)
            cpf char(11)
            endereco varchar(100)
            CEP varchar(30)
            
         */
        try {

            statement = connection.prepareStatement(sql);
            
            Date sqlDate = new Date(cliente.getDataNascimento().getTime());
            
            statement.setString(1, cliente.getNome());     // Filling in the camp "?"  //  Preenchendo os campos "?"
            statement.setString(2, cliente.getSexo());
            statement.setDate(3, sqlDate);
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getEmail());
            statement.setString(6, cliente.getCPF());
            statement.setString(7, cliente.getEndereco());
            statement.setString(8, cliente.getCEP());
            
            statement.execute();    // executing sql instruction   //  executando instruçao sql
            
        return true; //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
            return false; //returns false if unsuccessful // retorna falso se nao for bem sucedido
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

    }

    public boolean update(Cliente cliente) {

        connect();
        PreparedStatement statement = null;
        String sql = "UPDATE cliente set nome = ?, sexo = ?, dataNascimento = ?, telefone = ?, email = ?, cpf = ?, endereco = ?, CEP = ? WHERE idClient = ?";  // sql Command  //  comando sql

        /*
            
            Colunas:   //    Columns:
            idClient int(11) AI PK
            nome varchar(50)
            sexo enum('Mascolino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(100)
            cpf char(11)
            endereco varchar(100)
            CEP varchar(30)
            
         */
        try {

            statement = connection.prepareStatement(sql);
            
            statement.setString(1, cliente.getNome());     // Filling in the camp "?"  //  Preenchendo os campos "?"
            statement.setString(2, cliente.getSexo());
            statement.setDate(3, (Date) cliente.getDataNascimento());
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getEmail());
            statement.setString(6, cliente.getCPF());
            statement.setString(7, cliente.getEndereco());
            statement.setString(8, cliente.getCEP());
            statement.setInt(9, cliente.getId());
            
            statement.execute();    // executing sql instruction   //  executando instruçao sql
            
        return true; //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
            return false; //returns false if unsuccessful // retorna falso se nao for bem sucedido
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

    }

   

    public boolean delete(Cliente cliente) {
              connect();
        PreparedStatement statement = null;
        String sql = "DELETE FROM cliente WHERE idClient = ?";  // sql Command  //  comando sql

        try {

            statement = connection.prepareStatement(sql);
            
            statement.setInt(1, cliente.getId());  // Filling in the camp "?"  //  Preenchendo os campos "?"
            
            statement.execute();    // executing sql instruction   //  executando instruçao sql
            
        return true; //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
            return false; //returns false if unsuccessful // retorna falso se nao for bem sucedido
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }
    }

   
    public ArrayList<Cliente> selectAll() {
        
        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT * FROM cliente";  // sql Command  //  comando sql
        
        ArrayList<Cliente> clientes = new ArrayList<>();

        try {

            statement = connection.prepareStatement(sql);
            
            result = statement.executeQuery(); //  executa instruçao sql retornando resultado  //  executa instruçao sql retornando resultado

            while(result.next()){
                
                Cliente cliente = new Cliente();
                
                cliente.setId(result.getInt("idClient"));
                cliente.setNome(result.getString("nome"));
                cliente.setSexo(result.getString("sexo"));
                cliente.setDataNascimento(result.getDate("dataNascimento"));
                cliente.setTelefone(result.getString("telefone"));
                cliente.setEmail(result.getString("email"));
                cliente.setCPF(result.getString("cpf"));
                cliente.setEndereco(result.getString("endereco"));
                cliente.setCEP(result.getString("CEP"));
                
                clientes.add(cliente);   // add user created in List users  //  adiciona o usuario criado no List usuarios
                
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Checar o banco: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement,result);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }
       return clientes; 
    }

    
    private ArrayList<Cliente> search(String busca) {
        
        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT * FROM cliente WHERE nome LIKE ?";  // sql Command  //  comando sql
        
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try {

            statement = connection.prepareStatement(sql);
            
            statement.setString(1, "%"+busca+"%");
            
            result = statement.executeQuery(); //  executa instruçao sql retornando resultado  //  executa instruçao sql retornando resultado

            while(result.next()){
                
                Cliente cliente = new Cliente();
                
                cliente.setId(result.getInt("idClient"));
                cliente.setNome(result.getString("nome"));
                cliente.setSexo(result.getString("sexo"));
                cliente.setDataNascimento(result.getDate("dataNascimento"));
                cliente.setTelefone(result.getString("telefone"));
                cliente.setEmail(result.getString("email"));
                cliente.setCPF(result.getString("cpf"));
                cliente.setEndereco(result.getString("endereco"));
                cliente.setCEP(result.getString("CEP"));
                
                clientes.add(cliente);   // add user created in List users  //  adiciona o usuario criado no List usuarios
                
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Checar o banco: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement,result);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }
      return clientes;  
    }

    private void connect() {
        connection = ConnectionFactory.getConnection();
    }

}
