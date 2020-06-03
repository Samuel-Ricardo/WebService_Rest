/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import JDBC.ConnectionFactory;
import Model.User;
import com.mysql.jdbc.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Samuel
 */
public class UserDAO {

    private Connection connection;
    private static User user;
    private Date sqlDate;
    java.util.Date userDate;

    public UserDAO() {

    }

    public boolean insert(User user) {
        connect();
        PreparedStatement statement = null;
        String sql = "INSERT INTO usuario (nome , sexo , dataNascimento , telefone , email , cpf , senha, imagePerfil) VALUES (?,?, ?, ?, ?, ?, ?, ?);";

        /*
            
            // Columns:  //  Colunas: //
            
            id int(11) AI PK
            nome varchar(50)
            sexo enum('Masculino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(1000)
            cpf char(11)
            senha varchar(50)
            imagePerfil varchar(10000)
        
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado

            sqlDate = new Date(user.getNascimento().getTime());  // converting Date from java to Date from SQL  //  convertendo Date do java para o Date do SQL

            statement.setString(1, user.getNome());     // Filling in the camp "?"  //  Preenchendo os campos "?"
            statement.setString(2, user.getSexo());
            statement.setDate(3, sqlDate);
            statement.setString(4, user.getTelefone());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getCPF());
            statement.setString(7, user.getSenha());
            statement.setString(8, user.getImage());

            statement.execute();    // executing sql instruction   //  executando instruçao sql

            return true;    //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
            return false;   //returns false if unsuccessful // retorna falso se nao for bem sucedido
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }
    }

    public boolean update(User user) {

        connect();
        PreparedStatement statement = null;
        String sql = "UPDATE usuario set nome = ? , sexo = ?, dataNascimento = ?, telefone = ?, email = ?, cpf = ?, senha = ?, imagePerfil = ? WHERE id = ?;";

        /*
            
            // Columns:  //  Colunas: //
            
            id int(11) AI PK
            nome varchar(50)
            sexo enum('Masculino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(1000)
            cpf char(11)
            senha varchar(50)
            imagePerfil varchar(10000)
            
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado

            sqlDate = new Date(user.getNascimento().getTime());  // converting Date from java to Date from SQL  //  convertendo Date do java para o Date do SQL

            statement.setString(1, user.getNome());     // Filling in the camp "?"  //  Preenchendo os campos "?"
            statement.setString(2, user.getSexo());
            statement.setDate(3, sqlDate);
            statement.setString(4, user.getTelefone());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getCPF());
            statement.setString(7, user.getSenha());
            statement.setLong(9, user.getId());
            statement.setString(8, user.getImage());

            statement.execute();    // executing sql instruction   //  executando instruçao sql

            UserDAO.user = user;
            return true;    //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
            return false;   //returns false if unsuccessful // retorna falso se nao for bem sucedido
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

    }

    public boolean delete(User user) {

        connect();
        PreparedStatement statement = null;
        String sql = "DELETE FROM usuario WHERE id = ?;";

        /*
            
            // Columns:  //  Colunas: //
            
            id int(11) AI PK
            nome varchar(50)
            sexo enum('Masculino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(1000)
            cpf char(11)
            senha varchar(50)
            imagePerfil varchar(10000)
            
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado

            statement.setLong(1, user.getId());       // Filling in the camp "?"  //  Preenchendo os campos "?"

            statement.execute();    // executing sql instruction   //  executando instruçao sql

            return true;    //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
            return false;   //returns false if unsuccessful // retorna falso se nao for bem sucedido
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

    }

    public List<User> selectAll() {

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM usuario;";

        /*
            
            // Columns:  //  Colunas: //
            
            id int(11) AI PK
            nome varchar(50)
            sexo enum('Masculino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(1000)
            cpf char(11)
            senha varchar(50)
            imagePerfil varchar(10000)
            
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado

            result = statement.executeQuery();    //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            while (result.next()) {

                User user = new User();     // create user with database data  // criando usuario com dados do banco de dados

                if (result.getDate("dataNascimento") != null) {
                    userDate = new java.util.Date(result.getDate("dataNascimento").getTime());
                }

                user.setId(result.getLong("id"));
                user.setNome(result.getString("nome"));
                user.setSexo(result.getString("sexo"));
                user.setNascimento(userDate);
                user.setTelefone(result.getString("telefone"));
                user.setEmail(result.getString("email"));
                user.setCPF(result.getString("cpf"));
                user.setSenha(result.getString("senha"));
                user.setImage(result.getString("imagePerfil"));

                users.add(user);    // add user created in List users  //  adiciona o usuario criado no List usuarios
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return users;
    }

    public List<User> searchByName(String search) {

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE nome LIKE ?;";

        /*
            
            // Columns:  //  Colunas: //
            
            id int(11) AI PK
            nome varchar(50)
            sexo enum('Masculino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(1000)
            cpf char(11)
            senha varchar(50)
            imagePerfil varchar(10000)
            
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado

            statement.setString(1, "%" + search + "%");   // Filling in the camp "?"  //  Preenchendo os campos "?"

            result = statement.executeQuery();          //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            while (result.next()) {

                User user = new User();     // create user with database data  // criando usuario com dados do banco de dados

                if (result.getDate("dataNascimento") != null) {
                    userDate = new java.util.Date(result.getDate("dataNascimento").getTime());
                }

                user.setId(result.getLong("id"));
                user.setNome(result.getString("nome"));
                user.setSexo(result.getString("sexo"));
                user.setNascimento(userDate);
                user.setTelefone(result.getString("telefone"));
                user.setEmail(result.getString("email"));
                user.setCPF(result.getString("cpf"));
                user.setSenha(result.getString("senha"));
                user.setImage(result.getString("imagePerfil"));

                users.add(user);    // add user created in List users  //  adiciona o usuario criado no List usuarios
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return users;
    }

    public User search(User logUser) {

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        User findUser = new User();     // create user with database data  // criando usuario com dados do banco de dados
        String sql = "SELECT * FROM usuario WHERE nome = ? and senha = ?;";

        /*
            
            // Columns:  //  Colunas: //
            
            id int(11) AI PK
            nome varchar(50)
            sexo enum('Masculino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(1000)
            cpf char(11)
            senha varchar(50)
            imagePerfil varchar(10000)
            
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado

            statement.setString(1, logUser.getNome());   // Filling in the camp "?"  //  Preenchendo os campos "?"
            statement.setString(2, logUser.getSenha());

            result = statement.executeQuery();          //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            if (result.next()) {

                if (result.getDate("dataNascimento") != null) {
                    userDate = new java.util.Date(result.getDate("dataNascimento").getTime());
                }

                findUser.setId(result.getLong("id"));
                findUser.setNome(result.getString("nome"));
                findUser.setSexo(result.getString("sexo"));
                findUser.setNascimento(userDate);
                findUser.setTelefone(result.getString("telefone"));
                findUser.setEmail(result.getString("email"));
                findUser.setCPF(result.getString("cpf"));
                findUser.setSenha(result.getString("senha"));
                findUser.setImage(result.getString("imagePerfil"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return findUser;
    }

    private void connect() {

        connection = ConnectionFactory.getConnection();

    }

    public boolean exist(User user) {     // look for the user in the bank and return true if it exists  // procura o usuario no banco e retorna verdadeiro se exisstir

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT * FROM usuario WHERE nome = ? and senha = ?;";

        try {

            statement = connection.prepareStatement(sql);      // prepares the command to be executed  // prepara o comando para ser executado

            statement.setString(1, user.getNome());      // Filling in the camp "?"  //  Preenchendo os campos "?"
            statement.setString(2, user.getSenha());

            result = statement.executeQuery();            //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            if (result.next()) {  // if there is a result it returns true if it does not return false  //se houver resultado ele retorna verdadeiro se nao retorna false
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
            return false;
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserDAO.user = user;
    }

}
