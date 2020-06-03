/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import JDBC.ConnectionFactory;
import Model.Notification;
import Model.Type;
import Model.User;

import com.mysql.jdbc.Connection;
import java.io.File;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
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
public class NotificationDAO {



    private Connection connection;
    private Date sqlDate;
    private Time sqlTime;
    private java.util.Date notificationDate;
    private SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat horary = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat complet = new SimpleDateFormat("dd/MM/yy HH:mm");
    private static Notification notification;

    public NotificationDAO() {

    }

    public boolean insert(Notification notification) {
        connect();
        PreparedStatement statement = null;
        String sql = "INSERT INTO notificacao (titulo , descricao , image , horario , avisado, tipo_notificacao , anexo , musica, , userNotification , marcado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        /*
            
            // Columns:  //  Colunas: //
            
                idNotific int(11) AI PK 
                titulo varchar(25) 
                descricao varchar(1500) 
                image varchar(5000) 
                horario time 
                avisado tinyint(1) 
                tipo_notificacao int(11) 
                anexo varchar(5000) 
                musica varchar(5000)
                varchar(20)
                userNotification int(11)
                marcado date
        
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado

            sqlDate = notification.getSQLScheduledDay();  // converting Date from java to Date from SQL  //  convertendo Date do java para o Date do SQL

            java.util.Date scheduled = null;

            try {
                scheduled = horary.parse(notification.getScheduledHour());
            } catch (ParseException ex) {
                Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            sqlTime = new Time(scheduled.getTime());

            statement.setString(1, notification.getTitle());     // Filling in the camp "?"  //  Preenchendo os campos "?"
            statement.setString(2, notification.getDescription());
            if (notification.getImage() != null) {
                statement.setString(3, notification.getImage());
            } else {
                statement.setString(3, "");
            }
            statement.setTime(4, sqlTime);
            statement.setBoolean(5, notification.isWarned());
            statement.setInt(6, notification.getType().getId().intValue());
            if (notification.getAttachment() != null) {
                statement.setString(7, notification.getAttachment().getAbsolutePath());
            } else {
                statement.setString(7, "");
            }
            if (notification.getMusic() != null) {
                statement.setString(8, notification.getMusic().getAbsolutePath());
            }else{
                statement.setString(8, "");
            }
            statement.setInt(9, notification.getUser().getId().intValue());
            statement.setDate(10, sqlDate);

            statement.execute();    // executing sql instruction   //  executando instruçao sql

            return true;    //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
            return false;   //returns false if unsuccessful // retorna falso se nao for bem sucedido
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }
    }

    public boolean update(Notification notification) {

        connect();
        PreparedStatement statement = null;
        String sql = "UPDATE notificacao SET titulo = ? , descricao = ?, image = ?, horario = ?, avisado = ?, tipo_notificacao = ?, anexo = ?, musica = ?, userNotification = ?, marcado = ? WHERE idNotific = ?;";

        /*
            
            // Columns:  //  Colunas: //
            
                idNotific int(11) AI PK 
                titulo varchar(25) 
                descricao varchar(1500) 
                image varchar(5000) 
                horario time 
                avisado tinyint(1) 
                tipo_notificacao int(11) 
                anexo varchar(5000) 
                musica varchar(5000)
                varchar(20)
                userNotification int(11)
                marcado date
            
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado

            sqlDate = notification.getSQLScheduledDay();  // converting Date from java to Date from SQL  //  convertendo Date do java para o Date do SQL

            String timeString = horary.format(notification.getSQLScheduledDay());
            java.util.Date scheduled = null;

            try {
                scheduled = horary.parse(timeString);
            } catch (ParseException ex) {
                Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            sqlTime = new Time(scheduled.getTime());

            statement.setString(1, notification.getTitle());     // Filling in the camp "?"  //  Preenchendo os campos "?"
            statement.setString(2, notification.getDescription());
            statement.setString(3, notification.getImage());
            statement.setTime(4, sqlTime);
            statement.setBoolean(5, notification.isWarned());
            statement.setInt(6, notification.getType().getId().intValue());
            
            if(notification.getAttachment() != null){
                statement.setString(7, notification.getAttachment().getAbsolutePath());
            }else{
                statement.setString(7, "");
            }
                
            statement.setString(8, notification.getMusic().getAbsolutePath());
            statement.setInt(9, notification.getUser().getId().intValue());
            statement.setDate(10, sqlDate);
            statement.setInt(11, notification.getId());

            System.out.println(notification.getId()+"   id dao");
            
            statement.execute();    // executing sql instruction   //  executando instruçao sql

            return true;    //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
            return false;   //returns false if unsuccessful // retorna falso se nao for bem sucedido
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

    }

    public boolean delete(Notification notification) {

        connect();
        PreparedStatement statement = null;
        String sql = "DELETE FROM notificacao WHERE idNotific = ?;";

        /*
            
            // Columns:  //  Colunas: //
            
                idNotific int(11) AI PK 
                titulo varchar(25) 
                descricao varchar(1500) 
                image varchar(5000) 
                horario time 
                avisado tinyint(1) 
                tipo_notificacao int(11) 
                anexo varchar(5000) 
                musica varchar(5000)
                varchar(20)
                userNotification int(11)
                marcado date
            
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado

            statement.setLong(1, notification.getId());       // Filling in the camp "?"  //  Preenchendo os campos "?"

            statement.execute();    // executing sql instruction   //  executando instruçao sql

            return true;    //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
            return false;   //returns false if unsuccessful // retorna falso se nao for bem sucedido
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

    }

    public List<Notification> selectAll() {

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notification_from_user ORDER BY marcado DESC;";

        /*
            
            // Columns:  //  Colunas: //
            
                idNotific int(11) AI PK 
                titulo varchar(25) 
                descricao varchar(1500) 
                image varchar(5000) 
                horario time 
                avisado tinyint(1) 
                tipo_notificacao int(11) 
                anexo varchar(5000) 
                musica varchar(5000).
                varchar(20)
                userNotification int(11)
                marcado date
            
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado
            
            result = statement.executeQuery();    //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            while (result.next()) {

                Notification notification = new Notification();     // create Notification with database data  // criando notificacao com dados do banco de dados

                if (result.getDate("horario") != null && result.getDate("marcado") != null) {

                    Date time = new Date (result.getTime("horario").getTime());
                    Date date = new Date (result.getDate("marcado").getTime());
                    String timeS = horary.format(time);
                    String dateS = day.format(date);
                    String scheduled = dateS + " " + timeS;
                    

                    try {
                        notificationDate = complet.parse(scheduled);
                    } catch (ParseException ex) {
                        Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                notification.setId(result.getInt("idNotific"));
                notification.setTitle(result.getString("titulo"));
                notification.setDescription(result.getString("descricao"));
                notification.setAttachment(new File(result.getString("anexo")));
                notification.setImage(result.getString("image"));
                notification.setMusic(new File(result.getString("musica")));
                notification.setScheduledDay(notificationDate);
                notification.setWarned(result.getBoolean("avisado"));

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

                notification.setUser(user);
                
                Type type = new Type();
                
                type.setId(result.getInt("id_tipo"));
                type.setName(result.getString("tipo"));
                type.setSecondaryColor(result.getString("cor"));
                type.setPrimaryColor(result.getString("detalhes_de_cores"));
                type.setImportance(result.getInt("importancia"));
                type.setUser(user);

                notification.setType(type);
                
                notifications.add(notification);    // add Notification created in List Notifications  //  adiciona o notificacao criado no List notificacaos
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return notifications;
    }
    
    public List<Notification> selectAllFromUser(int id) {

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notification_from_user WHERE id = ? ORDER BY horario;";

        /*
            
            // Columns:  //  Colunas: //
            
                idNotific int(11) AI PK 
                titulo varchar(25) 
                descricao varchar(1500) 
                image varchar(5000) 
                horario time 
                avisado tinyint(1) 
                tipo_notificacao int(11) 
                anexo varchar(5000) 
                musica varchar(5000).
                varchar(20)
                userNotification int(11)
                marcado date
            
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado
            
            statement.setInt(1, id);
            
            result = statement.executeQuery();    //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            while (result.next()) {

                Notification notification = new Notification();     // create Notification with database data  // criando notificacao com dados do banco de dados

               if (result.getDate("horario") != null && result.getDate("marcado") != null) {

                    Date time = new Date (result.getTime("horario").getTime());
                    Date date = new Date (result.getDate("marcado").getTime());
                    String timeS = horary.format(time);
                    String dateS = day.format(date);
                    String scheduled = dateS + " " + timeS;
                    

                    try {
                        notificationDate = complet.parse(scheduled);
                    } catch (ParseException ex) {
                        Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                notification.setId(result.getInt("idNotific"));
                notification.setTitle(result.getString("titulo"));
                notification.setDescription(result.getString("descricao"));
                notification.setAttachment(new File(result.getString("anexo")));
                notification.setImage(result.getString("image"));
                notification.setMusic(new File(result.getString("musica")));
                notification.setScheduledDay(notificationDate);
                notification.setWarned(result.getBoolean("avisado"));

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

                notification.setUser(user);
                
                Type type = new Type();
                
                type.setId(result.getInt("id_tipo"));
                type.setName(result.getString("tipo"));
                type.setSecondaryColor(result.getString("cor"));
                type.setPrimaryColor(result.getString("detalhes_de_cores"));
                type.setImportance(result.getInt("importancia"));
                type.setUser(user);

                notification.setType(type);
                
                notifications.add(notification);    // add Notification created in List Notifications  //  adiciona o notificacao criado no List notificacaos
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return notifications;
    }

    public List<Notification> search(String search) {

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notification_from_user WHERE titutlo LIKE ? OR descricao LIKE ?;";

        /*
            
            // Columns:  //  Colunas: //
            
                idNotific int(11) AI PK 
                titulo varchar(25) 
                descricao varchar(1500) 
                image varchar(5000) 
                horario time 
                avisado tinyint(1) 
                tipo_notificacao int(11) 
                anexo varchar(5000) 
                musica varchar(5000)
                varchar(20)
                userNotification int(11)
                marcado date
        
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado

            statement.setString(1, "%" + search + "%");   // Filling in the camp "?"  //  Preenchendo os campos "?"
            statement.setString(2, "%" + search + "%");   // Filling in the camp "?"  //  Preenchendo os campos "?"

            result = statement.executeQuery();          //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            while (result.next()) {

                Notification notification = new Notification();     // create Notification with database data  // criando notificacao com dados do banco de dados

               if (result.getDate("horario") != null && result.getDate("marcado") != null) {

                    Date time = new Date (result.getTime("horario").getTime());
                    Date date = new Date (result.getDate("marcado").getTime());
                    String timeS = horary.format(time);
                    String dateS = day.format(date);
                    String scheduled = dateS + " " + timeS;
                    

                    try {
                        notificationDate = complet.parse(scheduled);
                    } catch (ParseException ex) {
                        Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                notification.setId(result.getInt("idNotific"));
                notification.setTitle(result.getString("titulo"));
                notification.setDescription(result.getString("descricao"));
                notification.setAttachment(new File(result.getString("anexo")));
                notification.setImage(result.getString("image"));
                notification.setMusic(new File(result.getString("musica")));
                notification.setScheduledDay(notificationDate);
                notification.setWarned(result.getBoolean("avisado"));

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

                notification.setUser(user);
                
                Type type = new Type();
                
                type.setId(result.getInt("id_tipo"));
                type.setName(result.getString("tipo"));
                type.setSecondaryColor(result.getString("cor"));
                type.setPrimaryColor(result.getString("detalhes_de_cores"));
                type.setImportance(result.getInt("importancia"));
                type.setUser(user);

                notification.setType(type);
                
                notifications.add(notification);    // add Notification created in List Notifications  //  adiciona o notificacao criado no List notificacaos
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return notifications;
    }

    public Notification search(Notification notification) {

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        Notification findNotification = new Notification();     // create Notification with database data  // criando notificacao com dados do banco de dados
        String sql = "SELECT * FROM notification_from_user WHERE idNotific = ?;";

        /*
            
            // Columns:  //  Colunas: //
            
                idNotific int(11) AI PK 
                titulo varchar(25) 
                descricao varchar(1500) 
                image varchar(5000) 
                horario date 
                avisado tinyint(1) 
                tipo_notificacao int(11) 
                anexo varchar(5000) 
                musica varchar(5000)
                varchar(20)
                userNotification int(11)
            
         */
        try {

            statement = connection.prepareStatement(sql);   // prepares the command to be executed  // prepara o comando para ser executado

            statement.setInt(1, notification.getId());   // Filling in the camp "?"  //  Preenchendo os campos "?"

            result = statement.executeQuery();          //  execute sql statement returning result  //  executa instruçao sql retornando resultado

            if (result.next()) {

                if (result.getDate("horario") != null && result.getDate("marcado") != null) {

                    Date time = new Date (result.getTime("horario").getTime());
                    Date date = new Date (result.getDate("marcado").getTime());
                    String timeS = horary.format(time);
                    String dateS = day.format(date);
                    String scheduled = dateS + " " + timeS;
                    

                    try {
                        notificationDate = complet.parse(scheduled);
                    } catch (ParseException ex) {
                        Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                notification.setId(result.getInt("idNotific"));
                notification.setTitle(result.getString("titulo"));
                notification.setDescription(result.getString("descricao"));
                notification.setAttachment(new File(result.getString("anexo")));
                notification.setImage(result.getString("image"));
                notification.setMusic(new File(result.getString("musica")));
                notification.setScheduledDay(notificationDate);
                notification.setWarned(result.getBoolean("avisado"));

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

                notification.setUser(user);
                
                Type type = new Type();
                
                type.setId(result.getInt("id_tipo"));
                type.setName(result.getString("tipo"));
                type.setSecondaryColor(result.getString("cor"));
                type.setPrimaryColor(result.getString("detalhes_de_cores"));
                type.setImportance(result.getInt("importancia"));
                type.setUser(user);

                notification.setType(type);
                
                findNotification = notification;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco: " + ex);  // error message if it occurs // mensagem de erro se ocorrer /
        } finally {
            ConnectionFactory.closeConnection(connection, statement);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

        return findNotification;
    }

    private void connect() {

        connection = ConnectionFactory.getConnection();

    }

    public boolean exist(Notification Notification) {     // look for the Notification in the bank and return true if it exists  // procura o notificacao no banco e retorna verdadeiro se exisstir

        connect();
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT * FROM notification_from_user WHERE idNotific = ?;";

        try {

            statement = connection.prepareStatement(sql);      // prepares the command to be executed  // prepara o comando para ser executado

            statement.setInt(1, Notification.getId());      // Filling in the camp "?"  //  Preenchendo os campos "?"

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

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate(Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    public Time getSqlTime() {
        return sqlTime;
    }

    public void setSqlTime(Time sqlTime) {
        this.sqlTime = sqlTime;
    }

    public java.util.Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(java.util.Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public SimpleDateFormat getDay() {
        return day;
    }

    public void setDay(SimpleDateFormat day) {
        this.day = day;
    }

    public SimpleDateFormat getHorary() {
        return horary;
    }

    public void setHorary(SimpleDateFormat horary) {
        this.horary = horary;
    }

    public SimpleDateFormat getComplet() {
        return complet;
    }

    public void setComplet(SimpleDateFormat complet) {
        this.complet = complet;
    }

    public static Notification getNotification() {
        return notification;
    }

    public static void setNotification(Notification notification) {
        NotificationDAO.notification = notification;
    }
    


}
