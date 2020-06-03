/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Samuel
 */
public class Servico {
    
    private int id;
    private String titulo;
    private String descricao;
    private double valor;

    public Servico(int id, String descricao, float valor, String titulo) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.titulo = titulo;
    }

    public Servico() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    @Override
    public String toString(){
    return getDescricao();
    }
    
    
}
