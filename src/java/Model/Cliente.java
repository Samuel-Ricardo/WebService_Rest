/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Samuel
 */
public class Cliente extends Pessoa{
    
    private String endereco;
    private String CEP;
    
    
    
 public Cliente(String endereco,String CEP,int id, String nome, String sexo, String dataNascimento, String telefone, String email, String RG) throws ParseException {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimento);
        this.telefone = telefone;
        this.email = email;
        this.CPF = CPF;
        this.endereco = endereco;
        this.CEP = CEP;
    }
    
 public Cliente(String CEP, int id, String nome, String sexo, String telefone) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.telefone = telefone;
        this.CEP = CEP;
    }
 
 public Cliente(int id,String nome,String sexo, String dataNascimento, String telefone,String email,String RG,String endereco,String CEP) throws ParseException {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimento);
        this.telefone = telefone;
        this.email = email;
        this.CPF = CPF;
        this.endereco = endereco;
        this.CEP = CEP;
    }

    public Cliente() {
   
    }

   
 
 
 
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }
    
    
    @Override
    public String toString(){
    return getNome();
    }
    
    
}
