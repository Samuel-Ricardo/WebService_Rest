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
public class Usuario extends Pessoa{
    
    private String senha;
    private String nivelDeAcesso;

   
     
    public Usuario (String senha, String nivelDeAcesso,int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.nivelDeAcesso = nivelDeAcesso;
    }
    
     public Usuario (int id, String nome ,String sexo, String dataNascimento, String telefone, String email, String CPF, String senha, String nivelDeAcesso) throws ParseException {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimento);
        this.telefone = telefone;
        this.email = email;
        this.CPF = CPF;
        this.senha = senha;
        this.nivelDeAcesso = nivelDeAcesso;
    
     }

    public Usuario() {
    
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNivelDeAcesso() {
        return nivelDeAcesso;
    }

    public void setNivelDeAcesso(String nivelDeAcesso) {
        this.nivelDeAcesso = nivelDeAcesso;
    }
    
    
    
    
    
    
    
}
