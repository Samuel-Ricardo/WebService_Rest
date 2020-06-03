/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import JDBC.ConnectionFactory;
import Model.PostIt;
import Model.Type;
import Model.User;
import com.mysql.jdbc.Connection;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Samuel
 */
public class PostItDAO {
    
    private Connection connection;
    private static PostIt postIt;
    private SimpleDateFormat complet = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    public boolean Insert(PostIt postIt){
    
      if(connection == null){
         connect();
        }
        PreparedStatement statement = null;
        String sql = "INSERT INTO postit (idPostIt, postItUser, title, body, scheduled, horary, warned, type_postit, sound) VALUES (?,?,?,?,?,?,?,?,?);";
        
        /*
        
        Columns:
                idPostIt int(11) AI PK 
                title varchar(45) 
                body tinytext 
                scheduled varchar(25) 
                horary varchar(10) 
                warned tinyint(1) 
                type_postit varchar(50)  
                postItUser int(11) 
                sound text

        */
        try {
            
            statement = connection.prepareStatement(sql);
            
            statement.setInt(1, postIt.getId());
            statement.setInt(2, postIt.getUser().getId().intValue());
            statement.setString(3, postIt.getTitle());
            statement.setString(4, postIt.getDescription());
            statement.setString(5, postIt.getScheduledDate());
            statement.setString(6, postIt.getScheduledHour());
            statement.setBoolean(7, postIt.isWarned());
            statement.setInt(8, postIt.getType().getId().intValue());
            statement.setString(9, postIt.getMusic().getAbsolutePath());
            
            statement.execute();
            
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Inserir: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
            return false;
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
  }
    
    public boolean update(PostIt postIt){
        
          if(connection == null){
         connect();
        }
        PreparedStatement statement = null;
        String sql = "UPDATE postit SET postItUser = ?, title = ?, body = ?, scheduled = ?, horary = ?, warned = ?, type_postit = ?,  = ?, sound = ? WHERE idPostIt = ?;";
        
        try {
            
            statement = connection.prepareStatement(sql);
            
            statement.setInt(1, postIt.getUser().getId().intValue());
            statement.setString(2, postIt.getTitle());
            statement.setString(3, postIt.getDescription());
            statement.setString(4, postIt.getScheduledDate());
            statement.setString(5, postIt.getScheduledHour());
            statement.setBoolean(6, postIt.isWarned());
            statement.setInt(7, postIt.getType().getId().intValue());
            statement.setString(8, postIt.getMusic().getAbsolutePath());
            
            statement.execute();
            
            return true;
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro ao Atualizar: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
            return false;  
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public boolean delet(PostIt postIt){
        
        if(connection == null){
         connect();
        }
        PreparedStatement statement = null;
        String sql = "DELETE FROM postit WHERE idPostIt = ?;";
        
        try {
            
            statement = connection.prepareStatement(sql);
            
            statement.setInt(1, postIt.getId());
            
            statement.execute();
           
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PostItDAO.class.getName()).log(Level.SEVERE, null, ex);
           JOptionPane.showMessageDialog(null, "Erro ao Deletar: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
            return false;  
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    
     public List<PostIt> selectAll() {

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        List<PostIt> postits = new ArrayList<>();
        String sql = "SELECT * FROM postit_from_user ORDER BY scheduled;";

        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado
            
            result = statement.executeQuery();    //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            while (result.next()) {

                PostIt postIt = new PostIt();     // create PostIt with database data  // criando notificacao com dados do banco de dados

                postIt.setId(result.getInt("idNotific"));
                postIt.setTitle(result.getString("title"));
                postIt.setDescription(result.getString("body"));
                postIt.setMusic(new File(result.getString("sound")));
                try {
                    postIt.setScheduledDay(complet.parse(result.getString("scheduled")+" "+result.getString("horary")));
                } catch (ParseException ex) {
                    Logger.getLogger(PostItDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                postIt.setWarned(result.getBoolean("avisado"));

                User user = new User();

                java.util.Date userDate = null;

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

                postIt.setUser(user);

                Type type = new Type();
                
                type.setId(result.getInt("id_tipo"));
                type.setName(result.getString("tipo"));
                type.setSecondaryColor(result.getString("cor"));
                type.setPrimaryColor(result.getString("detalhes_de_cores"));
                type.setImportance(result.getInt("importancia"));
                type.setUser(user);

                postIt.setType(type);
                
                postits.add(postIt);    // add PostIt created in List PostIts  //  adiciona o notificacao criado no List notificacaos
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return postits;
    }
    
    public List<PostIt> selectAllFromUser(int id) {

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        List<PostIt> postits = new ArrayList<>();
        String sql = "SELECT * FROM postit_from_user WHERE id = ? ORDER BY horario;";
        
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado
            
            result = statement.executeQuery();    //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            while (result.next()) {

                PostIt postIt = new PostIt();     // create PostIt with database data  // criando notificacao com dados do banco de dados

                postIt.setId(result.getInt("idNotific"));
                postIt.setTitle(result.getString("title"));
                postIt.setDescription(result.getString("body"));
                postIt.setMusic(new File(result.getString("sound")));
                try {
                    postIt.setScheduledDay(complet.parse(result.getString("scheduled")+" "+result.getString("horary")));
                } catch (ParseException ex) {
                    Logger.getLogger(PostItDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                postIt.setWarned(result.getBoolean("avisado"));

                User user = new User();

                java.util.Date userDate = null;

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

                postIt.setUser(user);

                Type type = new Type();
                
                type.setId(result.getInt("id_tipo"));
                type.setName(result.getString("tipo"));
                type.setSecondaryColor(result.getString("cor"));
                type.setPrimaryColor(result.getString("detalhes_de_cores"));
                type.setImportance(result.getInt("importancia"));
                type.setImportance(result.getInt("importancia"));
                type.setUser(user);

                postIt.setType(type);

                postits.add(postIt);    // add PostIt created in List PostIts  //  adiciona o notificacao criado no List notificacaos
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return postits;
    }

    public List<PostIt> search(String search) {

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        List<PostIt> postits = new ArrayList<>();
        String sql = "SELECT * FROM postit_from_user WHERE titutlo LIKE ? OR descricao LIKE ?;";
        
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado
            
            result = statement.executeQuery();    //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            while (result.next()) {

                PostIt postIt = new PostIt();     // create PostIt with database data  // criando notificacao com dados do banco de dados

                postIt.setId(result.getInt("idNotific"));
                postIt.setTitle(result.getString("title"));
                postIt.setDescription(result.getString("body"));
                postIt.setMusic(new File(result.getString("sound")));
                try {
                    postIt.setScheduledDay(complet.parse(result.getString("scheduled")+" "+result.getString("horary")));
                } catch (ParseException ex) {
                    Logger.getLogger(PostItDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                postIt.setWarned(result.getBoolean("avisado"));

                User user = new User();

                java.util.Date userDate = null;

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

                postIt.setUser(user);

                Type type = new Type();
                
                type.setId(result.getInt("id_tipo"));
                type.setName(result.getString("tipo"));
                type.setSecondaryColor(result.getString("cor"));
                type.setPrimaryColor(result.getString("detalhes_de_cores"));
                type.setImportance(result.getInt("importancia"));
                type.setUser(user);

                postIt.setType(type);

                postits.add(postIt);    // add PostIt created in List PostIts  //  adiciona o notificacao criado no List notificacaos
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return postits;
    }


    public PostIt search(PostIt postIt) {

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        PostIt findPostIt = new PostIt();     // create PostIt with database data  // criando notificacao com dados do banco de dados
        String sql = "SELECT * FROM postit_from_user WHERE idNotific = ?;";
        
        try {

                statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado
            
            result = statement.executeQuery();    //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            while (result.next()) {
                
                findPostIt.setId(result.getInt("idNotific"));
                findPostIt.setTitle(result.getString("title"));
                findPostIt.setDescription(result.getString("body"));
                findPostIt.setMusic(new File(result.getString("sound")));
                try {
                    findPostIt.setScheduledDay(complet.parse(result.getString("scheduled")+" "+result.getString("horary")));
                } catch (ParseException ex) {
                    Logger.getLogger(PostItDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                findPostIt.setWarned(result.getBoolean("avisado"));

                User user = new User();

                java.util.Date userDate = null;

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

                findPostIt.setUser(user);

                Type type = new Type();
                
                type.setId(result.getInt("id_tipo"));
                type.setName(result.getString("tipo"));
                type.setSecondaryColor(result.getString("cor"));
                type.setPrimaryColor(result.getString("detalhes_de_cores"));
                type.setImportance(result.getInt("importancia"));
                type.setImportance(result.getInt("importancia"));
                type.setUser(user);
                
                findPostIt.setType(type);
                
                findPostIt = findPostIt;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return findPostIt;
    }

    public boolean exist(PostIt postIt) {     // look for the PostIt in the bank and return true if it exists  // procura o notificacao no banco e retorna verdadeiro se exisstir

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT * FROM postit_from_user WHERE idPostIt = ?;";

        try {

            statement = connection.prepareStatement(sql);      // prepares the command to be executed  // prepara o comando para ser executado

            statement.setInt(1, postIt.getId());      // Filling in the camp "?"  //  Preenchendo os campos "?"

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

    private void connect() {
     connection = ConnectionFactory.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static PostIt getPostIt() {
        return postIt;
    }

    public static void setPostIt(PostIt postIt) {
        PostItDAO.postIt = postIt;
    }

    public SimpleDateFormat getComplet() {
        return complet;
    }

    public void setComplet(SimpleDateFormat complet) {
        this.complet = complet;
    }
    
    
    
}
