/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import JDBC.ConnectionFactory;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Servico;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ServicoDAO {
    
    private Connection conexao;
    private String sql = "";

    
    public boolean insert(Servico servico){
      
        conectar();
        PreparedStatement comando = null;

        sql = "INSERT INTO servico (titulo , descricao, preco) VALUES (?,?,?) " ;

        
        /*
        
              Columns:  // Colunas
              

                idServ int(11) AI PK 
                titulo varchar(30) 
                descricao varchar(500) 
                preco double

        */
        
        
        try {
            
            comando = conexao.prepareStatement(sql);
            
            comando.setString(1, servico.getTitulo());
            comando.setString(2, servico.getDescricao());
            comando.setDouble(3, servico.getValor());
            
            comando.execute();      // executing sql instruction   //  executando instruçao sql

            
        return true; //returns true if successful // retorna verdadeiro se for bem sucedido
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar: " + ex); // error message if it occurs / mensagem de erro se ocorrer /
            return false; //returns false if unsuccessful // retorna falso se nao for bem sucedido
        } finally {
            ConnectionFactory.closeConnection(conexao, comando);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }
        
    }

    public boolean update(Servico servico){
        
        conectar();
        PreparedStatement comando = null;

        sql = "UPDATE servico titulo = ?, descricao = ?, preco = ? WHERE idServ = ?;";

        
        try {
            
            comando = conexao.prepareStatement(sql);
            
            comando.setString(1, servico.getTitulo());
            comando.setString(2, servico.getDescricao());
            comando.setDouble(3, servico.getValor());
            comando.setInt(4, servico.getId());
            
            comando.execute();
            
         return true;
         
        } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "Erro ao Atualizar: " + ex);
          return false;    
        } finally {
            ConnectionFactory.closeConnection(conexao, comando);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }

    }
    
    public boolean delete(Servico servico){

        conectar();
        PreparedStatement comando = null;

        sql = "DELET FROM servico WHERE idServ = ?";

        
        try {
            
            comando = conexao.prepareStatement(sql);
            
            comando.setInt(1, servico.getId());
            
            comando.execute();
                    
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Deletar: " + ex);
          return false;
        } finally {
            ConnectionFactory.closeConnection(conexao, comando);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }
        
        
       
    }
    

    public List<Servico> selectAll(){
    
        conectar();
        PreparedStatement comando = null;
        ResultSet resultado = null;
        sql = "SELECT * FROM servico;";
        List<Servico> servicos = new ArrayList<>();
        
        try {
            
              comando = conexao.prepareStatement(sql);
            
              resultado = comando.executeQuery();
              
              while(resultado.next()){
                  
                  Servico servico = new Servico();
                  
                  servico.setDescricao(resultado.getString("descricao"));

                  servico.setId(resultado.getInt("idServ"));
                  servico.setTitulo(resultado.getString("titulo"));
                  servico.setValor(resultado.getFloat("preco"));

                  
                  servicos.add(servico);
              }
              
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao Pesquisar: " + ex);
        }finally {
            ConnectionFactory.closeConnection(conexao, comando,resultado);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }
        
       return servicos;
    }
    
     public List<Servico> search(String pesquisa){
    
        conectar();
        PreparedStatement comando = null;
        ResultSet resultado = null;
        sql = "SELECT * FROM servico WHERE descricao LIKE ? or titulo LIKE ?;";
        List<Servico> servicos = new ArrayList<>();
        
        try {
            
              comando = conexao.prepareStatement(sql);
              
              comando.setString(1, pesquisa);
              comando.setString(2, pesquisa);
            
              resultado = comando.executeQuery();
              
              while(resultado.next()){
                  
                  Servico servico = new Servico();
                  
                  servico.setDescricao(resultado.getString("descricao"));

                  servico.setId(resultado.getInt("idServ"));
                  servico.setTitulo(resultado.getString("titulo"));
                  servico.setValor(resultado.getDouble("preco"));

                  servicos.add(servico);
              }
              
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao Pesquisar: " + ex);
        }finally {
            ConnectionFactory.closeConnection(conexao, comando,resultado);  // closes all connections regardless of success  // fecha todas as conexoes independente de sucesso
        }
        
       return servicos;
    }

    private boolean idSaoIguais(Servico servico, Servico servicoAComparar) {
        return servico.getId() ==  servicoAComparar.getId();
    }

    private void conectar() {
    
        conexao = ConnectionFactory.getConnection();
    
    }
    
}
