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
import java.text.SimpleDateFormat;
import model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    private Connection connection;
    private  Date dataSQL;
    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    
    public boolean insert(Usuario usuario) {

        conect();
        PreparedStatement statement = null;
        String sql = "INSERT INTO usuario (nome,sexo,dataNascimento,telefone,email,cpf,senha, nivelDeAcesso) VALUES (?,?,?,?,?,?,?,?) "; // sql Command  //  comando sql

        /* Clomumns // colunas :
            
            idUser int(11) AI PK
            nome varchar(50)
            sexo enum('Masculino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(100)
            cpf char(11)
            senha varchar(50)
            nivelDeAcesso varchar(30)
         */
        try {

            statement = connection.prepareStatement(sql);

            dataSQL = new Date(usuario.getDataNascimento().getTime());
            
            statement.setString(1, usuario.getNome());    // Filling in the camp "?"  //  Preenchendo os campos "?"
            statement.setString(2, usuario.getSexo());
            statement.setDate(3, dataSQL);
            statement.setString(4, usuario.getTelefone());
            statement.setString(5, usuario.getEmail());
            statement.setString(6, usuario.getCPF());
            statement.setString(7, usuario.getSenha());
            statement.setString(8, usuario.getNivelDeAcesso());

            statement.execute(); // executing sql instruction   //  executando instruçao sql

            return true; //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
            return false; //returns false if unsuccessful // retorna falso se nao for bem sucedido
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

    }

    
    public boolean update(Usuario usuario) {

        conect();
        PreparedStatement statement = null;
        String sql = "UPDATE usuario SET nome = ? , sexo = ?, dataNascimento = ?, telefone = ?,email = ?, cpf = ?, senha = ?, nivelDeAcesso = ? WHERE idUser = ?"; // sql Command  //  comando sql

        /* Clomumns // colunas :
            
            idUser int(11) AI PK
            nome varchar(50)
            sexo enum('Masculino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(100)
            cpf char(11)
            senha varchar(50)
            nivelDeAcesso varchar(30)
         */
        try {

            statement = connection.prepareStatement(sql);

             dataSQL = new Date(usuario.getDataNascimento().getTime());
            
            statement.setString(1, usuario.getNome());    // Filling in the camp "?"  //  Preenchendo os campos "?"
            statement.setString(2, usuario.getSexo());
            statement.setDate(3, dataSQL);
            statement.setString(4, usuario.getTelefone());
            statement.setString(5, usuario.getEmail());
            statement.setString(6, usuario.getCPF());
            statement.setString(7, usuario.getSenha());
            statement.setString(8, usuario.getNivelDeAcesso());
            statement.setInt(9, usuario.getId());

            statement.execute(); // executing sql instruction   //  executando instruçao sql

            return true; //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
            return false; //returns false if unsuccessful // retorna falso se nao for bem sucedido
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

    }


    public boolean delete(Usuario usuario) {
        conect();
        PreparedStatement statement = null;
        String sql = "DELETE FROM usuario WHERE idUser = ?"; // sql Command  //  comando sql

        try {

            statement = connection.prepareStatement(sql);

            statement.setInt(1, usuario.getId());    // Filling in the camp "?"  //  Preenchendo os campos "?"

            statement.execute(); // executing sql instruction   //  executando instruçao sql

            return true; //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Deletar: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
            return false; //returns false if unsuccessful // retorna falso se nao for bem sucedido
        }finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

    }


    public List<Usuario> selectAll() {

        conect();
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT * FROM usuario;"; // sql Command  //  comando sql

        List<Usuario> usuarios = new ArrayList<>();

        try {

            statement = connection.prepareStatement(sql);

            result = statement.executeQuery(); //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            while (result.next()) {  // while there is a result do  //  enquanto ouver resultado faça

                /* Clomumns // colunas :
                
            idUser int(11) AI PK
            nome varchar(50)
            sexo enum('Masculino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(100)
            cpf char(11)
            senha varchar(50)
            nivelDeAcesso varchar(30)
                 */
                Usuario usuario = new Usuario();

                Date nascimento = new Date (result.getDate("dataNascimento").getTime());
                

                usuario.setId(result.getInt("idUser"));  // create user with database data  // criando usuario com dados do banco de dados

                usuario.setNome(result.getString("nome"));  // create user with database data  // criando usuario com dados do banco de dados
                usuario.setSexo(result.getString("sexo"));
                usuario.setDataNascimento(nascimento);
                usuario.setTelefone(result.getString("telefone"));
                usuario.setEmail(result.getString("email"));
                usuario.setCPF(result.getString("cpf"));
                usuario.setSenha(result.getString("senha"));
                usuario.setNivelDeAcesso(result.getString("nivelDeAcesso"));

                usuarios.add(usuario);  // add user created in List users  //  adiciona o usuario criado no List usuarios

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Deletar: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
        }finally {
            ConnectionFactory.closeConnection(connection, statement,result);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return usuarios;
    }
    
    public List<Usuario> search(String nome) {
         
        conect();
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT * FROM usuario WHERE nome LIKE ?;"; // sql Command  //  comando sql

        List<Usuario> usuarios = new ArrayList<>();

        try {

            statement = connection.prepareStatement(sql);

            statement.setString(1, "%"+nome+"%");   // Filling in the camp "?"  //  Preenchendo os campos "?"
            
            result = statement.executeQuery(); //  executa instruçao sql retornando resultado  //  executa instruçao sql retornando resultado

            
            while (result.next()) {  // while there is a result do  //  enquanto ouver resultado faça

                /* Clomumns // colunas :
                
            id int(11) AI PK
            nome varchar(50)
            sexo enum('Masculino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(100)
            cpf char(11)
            senha varchar(50)
            nivelDeAcesso varchar(30)
                 */
               Date nascimento = new Date (result.getDate("dataNascimento").getTime());
                
                Usuario usuarioR = new Usuario();

                usuarioR.setId(result.getInt("idUser"));  // create user with database data  // criando usuario com dados do banco de dados
                usuarioR.setNome(result.getString("nome"));  // create user with database data  // criando usuario com dados do banco de dados
                usuarioR.setSexo(result.getString("sexo"));
                usuarioR.setDataNascimento(nascimento);
                usuarioR.setTelefone(result.getString("telefone"));
                usuarioR.setEmail(result.getString("email"));
                usuarioR.setCPF(result.getString("cpf"));
                usuarioR.setSenha(result.getString("senha"));
                usuarioR.setNivelDeAcesso(result.getString("nivelDeAcesso"));

                usuarios.add(usuarioR);  // add user created in List users  //  adiciona o usuario criado no List usuarios

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Pesquisar: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
        }finally {
            ConnectionFactory.closeConnection(connection, statement,result);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return usuarios;
    }
    
     public Usuario search(Usuario usuario) {
         
        conect();
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT * FROM usuario WHERE nome = ? and senha = ?;"; // sql Command  //  comando sql

        
        Usuario usuarioR = new Usuario();

        try {

            statement = connection.prepareStatement(sql);
           
            statement.setString(1, usuario.getNome());  // Filling in the camp "?"  //  Preenchendo os campos "?"
            statement.setString(2, usuario.getSenha());
            
            result = statement.executeQuery(); //  executa instruçao sql retornando resultado  //  executa instruçao sql retornando resultado

                /* Clomumns // colunas :
                
            id int(11) AI PK
            nome varchar(50)
            sexo enum('Masculino','Feminino')
            dataNascimento date
            telefone varchar(20)
            email varchar(100)
            cpf char(11)
            senha varchar(50)
            nivelDeAcesso varchar(30)
                 */
                 if(result.next()){

                Date nascimento = new Date (result.getDate("dataNascimento").getTime());
                
            
                     

                usuarioR.setId(result.getInt("idUser"));  // create user with database data  // criando usuario com dados do banco de dados

                usuarioR.setNome(result.getString("nome"));  // create user with database data  // criando usuario com dados do banco de dados
                usuarioR.setSexo(result.getString("sexo"));
                usuarioR.setDataNascimento(nascimento);
                usuarioR.setTelefone(result.getString("telefone"));
                usuarioR.setEmail(result.getString("email"));
                usuarioR.setCPF(result.getString("cpf"));
                usuarioR.setSenha(result.getString("senha"));
                usuarioR.setNivelDeAcesso(result.getString("nivelDeAcesso"));
                 }else{
                     JOptionPane.showMessageDialog(null, "Sem resultados");
                 }
            
             
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Pesquisar: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
           
        }finally {
            ConnectionFactory.closeConnection(connection, statement,result);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return usuarioR;
    }

    private boolean nomeESenhaSaoIguais(Usuario usuario, Usuario usuarioAPesquisar) {
        return usuario.getNome().equals(usuarioAPesquisar.getNome()) && usuario.getSenha().equals(usuarioAPesquisar.getSenha());
    }


    private boolean idSaoIguais(Usuario usuario, Usuario usuarioAComparar) {
        return usuario.getId() == usuarioAComparar.getId();
    }

    private void conect() {

        connection = ConnectionFactory.getConnection(); // Start Connection  // Iniciando conexao 

    }

}
