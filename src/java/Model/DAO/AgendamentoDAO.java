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
import model.Agendamento;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Cliente;
import model.Servico;


public class AgendamentoDAO {
    
    private Connection conexao;
    private String sql;

    public boolean insert(Agendamento agendamento){
        
        conectar();
        PreparedStatement comando = null;
        sql = "INSERT INTO agendamento ( idServico, valor, agendado, observacao, idCliente) VALUES (?,?,?,?,?);";
        
            /*
            Columns:  // colunas:
            
            idAgen int(11) AI PK
            idCliente int(11)
            idServico int(11)
            valor float
            agendado date 
            observacao varchar(5000)
            */
          try {
              
            comando = conexao.prepareStatement(sql);
            comando.setInt(1, agendamento.getServico().getId());
            comando.setDouble(2, agendamento.getServico().getValor());
            comando.setDate(3, agendamento.getDataSQL());
            comando.setString(4, agendamento.getObservacao());
            comando.setInt(5, agendamento.getCliente().getId());
            
            comando.execute();
            
            return true;
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "Erro ao Salvar: " + ex); 
          return false;
        } finally{
              ConnectionFactory.closeConnection(conexao, comando);
          }
        
        
    }
    
    public boolean update(Agendamento agendamento){
        
    conectar();
        PreparedStatement comando = null;
        sql = "UPDATE agendamento idCliente = ?, idServico = ?, valor = ?, agenda = ?, observacao = ? WHERE idAgen = ?;";
        
            /*
            Columns:  // colunas:
            
            idAgen int(11) AI PK
            idCliente int(11)
            idServico int(11)
            valor float
            agendado date 
            observacao varchar(5000)
            */
          try {
              
            comando = conexao.prepareStatement(sql);
            comando.setInt(1, agendamento.getServico().getId());
            comando.setDouble(2, agendamento.getServico().getValor());
            comando.setDate(3, agendamento.getDataSQL());
            comando.setString(4, agendamento.getObservacao());
            comando.setInt(5, agendamento.getId());
            
            comando.execute();
            
            return true;
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "Erro ao Atualizar: " + ex); 
          return false;
        } finally{
              ConnectionFactory.closeConnection(conexao, comando);
          }
        
    }

    
    public boolean delete(Agendamento agendamento){
            
    conectar();
        PreparedStatement comando = null;
        sql = "DELETE FROM agendamento WHERE idAgen = ?;";
        
            /*
            Columns:  // colunas:
            
            idAgen int(11) AI PK
            idCliente int(11)
            idServico int(11)
            valor float
            agendado date 
            observacao varchar(5000)
            */
          try {
              
            comando = conexao.prepareStatement(sql);
            
            comando.setInt(1, agendamento.getId());
            
            comando.execute();
            
            return true;
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "Erro ao Deletar: " + ex); 
          return false;
        } finally{
              ConnectionFactory.closeConnection(conexao, comando);
          }
    }
    

    public List<Agendamento> selectAll(){
      
        conectar();
        PreparedStatement comando = null;
        ResultSet resultado = null;
        sql = "SELECT * FROM agendado_vw;";
        List<Agendamento> agendamentos = new ArrayList<>();
   
            /*
            Columns:  // colunas:
            
            idAgen int(11) AI PK
            idCliente int(11)
            idServico int(11)
            valor float
            agendado date 
            observacao varchar(5000)
            */
          try {
              
            comando = conexao.prepareStatement(sql);
            
            resultado = comando.executeQuery();
            
            while(resultado.next()){
                
                Agendamento agendamento = new Agendamento();
                
                agendamento.setId(resultado.getInt("idAgen"));
                agendamento.setDataLong(resultado.getDate("agendado").getTime());
                agendamento.setObservacao(resultado.getString("observacao"));
                agendamento.setValor(resultado.getFloat("valor"));
                
               Cliente cliente = new Cliente();
                
                cliente.setId(resultado.getInt("idClient"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setSexo(resultado.getString("sexo"));
                cliente.setDataNascimento(resultado.getDate("dataNascimento"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setCPF(resultado.getString("cpf"));
                cliente.setEndereco(resultado.getString("endereco"));
                cliente.setCEP(resultado.getString("CEP"));
                
                agendamento.setCliente(cliente);
                
                Servico servico = new Servico();
                
                servico.setDescricao(resultado.getString("descricao"));
                servico.setId(resultado.getInt("idServ"));
                servico.setTitulo(resultado.getString("titulo"));
                servico.setValor(resultado.getFloat("preco"));
                  
                agendamento.setServico(servico);
                
                System.out.println(agendamento.getData()+"");
                
                agendamentos.add(agendamento);
            }

        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "Erro ao Pesqisar: " + ex); 
        
        } finally{
              ConnectionFactory.closeConnection(conexao, comando,resultado);
          }
       return agendamentos;
    }
    
    public List<Agendamento> search(String pesquisa){
      
        conectar();
        PreparedStatement comando = null;
        ResultSet resultado = null;
        sql = "SELECT * FROM agendado_vw WHERE observacao LIKE ? or descricao LIKE ? or titulo LIKE ? or nome LIKE ?;";
        List<Agendamento> agendamentos = new ArrayList<>();
       
            /*
            Columns:  // colunas:
            
            idAgen int(11) AI PK
            idCliente int(11)
            idServico int(11)
            valor float
            agendado date 
            observacao varchar(5000)
            */
          try {
              
            comando = conexao.prepareStatement(sql);
            
            comando.setString(1, "%"+pesquisa+"%");
            comando.setString(2, "%"+pesquisa+"%");
            comando.setString(3, "%"+pesquisa+"%");
            comando.setString(4, "%"+pesquisa+"%");
            
            resultado = comando.executeQuery();
            
            while(resultado.next()){
                
                Agendamento agendamento = new Agendamento();
                
                agendamento.setId(resultado.getInt("idAgen"));
                agendamento.setDataLong(resultado.getDate("agendado").getTime());
                agendamento.setObservacao(resultado.getString("observacao"));
                agendamento.setValor(resultado.getFloat("valor"));
                
               Cliente cliente = new Cliente();
                
                cliente.setId(resultado.getInt("idClient"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setSexo(resultado.getString("sexo"));
                cliente.setDataNascimento(resultado.getDate("dataNascimento"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setCPF(resultado.getString("cpf"));
                cliente.setEndereco(resultado.getString("endereco"));
                cliente.setCEP(resultado.getString("CEP"));
                
                agendamento.setCliente(cliente);
                
                Servico servico = new Servico();
                
                servico.setDescricao(resultado.getString("descricao"));
                servico.setId(resultado.getInt("idServ"));
                servico.setTitulo(resultado.getString("titulo"));
                servico.setValor(resultado.getFloat("preco"));
                  
                agendamento.setServico(servico);
                
                agendamentos.add(agendamento);
            }

        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "Erro ao Pesqisar: " + ex); 
        
        } finally{
              ConnectionFactory.closeConnection(conexao, comando,resultado);
          }
       return agendamentos;
    }
    
    private boolean idSaoIguais(Agendamento agendamento, Agendamento agendamentoAComparar) {
        return agendamento.getId() ==  agendamentoAComparar.getId();
    }
    
    private int proximoId(){
        
        /*      int maiorId = 0;
        
        for (Agendamento agendamento : Banco.agendamento) {
        int id = agendamento.getId();
        
        if(maiorId < id){
        maiorId = id;
        }
        
        }
        */
        return 1;
    }

    private void conectar() {
     
        conexao = ConnectionFactory.getConnection();

    }
    
}
