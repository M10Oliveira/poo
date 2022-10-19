/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.lavacao.model.domain;
import br.com.lavacao.model.domain.Marca;

/**
 *
 * @author matheus.ti
 */
public class Modelo {
    private int id;
    private Marca marca;
    private String nome;

    public Modelo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Modelo(Marca marca) {
        this.marca = marca;
    }

    public Modelo(Marca marca, String nome) {
        this.marca = marca;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Modelo{" + "id=" + id + ", marca=" + marca + ", nome=" + nome + '}';
    }

   
    
}
