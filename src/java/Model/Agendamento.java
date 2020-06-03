/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class Agendamento {
    
    private int id ;
    private Cliente cliente;
    private Servico servico;
    private float valor;
    private Date data;
    private String observacao;

    public Agendamento(int id, Cliente cliente, Servico servico, float valor, String data) {
        this.id = id;
        this.cliente = cliente;
        this.servico = servico;
        this.valor = valor;
        
        
        try{
        this.data = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(data);
        }catch(ParseException e){
            Logger.getLogger(Agendamento.class.getName()).log(Level.SEVERE, null, e);
        }    
    
     }

    public Agendamento(int id, Cliente cliente, Servico servico, float valor, String data, String observacao) throws ParseException {
       
        this(id, cliente, servico, valor, data);
        this.observacao = observacao;
    }

    public Agendamento() {
    }

    public Agendamento(Cliente cliente, Servico servico, float valor, String dataHora, String observacao) {
        
        this.cliente = cliente;
        this.servico = servico;
        this.valor = valor;
        this.observacao = observacao;
        
        try{
        this.data = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dataHora);
        }catch(ParseException e){
            Logger.getLogger(Agendamento.class.getName()).log(Level.SEVERE, null, e);
        }    
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }
    
    public String getDataFormatada(){
    
    return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }
    
    public java.sql.Date getDataSQL(){

        java.sql.Date dataSQL = new java.sql.Date(this.data.getTime());
    return dataSQL;
    }
    
    public String getHoraFormatada(){
        
        return new SimpleDateFormat("HH:mm").format(data);
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setDataLong(long SQLdate) {
        
        this.data = new Date(SQLdate);
        
    }

   
    
    
    
    
}
