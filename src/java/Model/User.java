/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Samuel
 */
public class User {
    
   /*
    
   // Columns:  //  Colunas: //
       
        id int(11) AI PK 
        nome varchar(50) 
        sexo enum('Masculino','Feminino') 
        dataNascimento date 
        telefone varchar(20) 
        email varchar(100) 
        cpf char(11) 
        senha varchar(50) 
    
    */
    
     private String image;
     private Long id;
     private String nome;
     private String sexo;
     private Date nascimento;
     private String telefone;
     private String email;
     private String CPF;
     private String senha;

    public User(Long id, String nome, String sexo, Date nascimento, String telefone, String email, String CPF, String senha, String image) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.email = email;
        this.CPF = CPF;
        this.senha = senha;
        this.image = image;
    }

    public User() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFormatedNascimento() {
        SimpleDateFormat formated = new SimpleDateFormat("dd/MM/yyyy");
        
        return formated.format(nascimento);
    }
    
     
}
