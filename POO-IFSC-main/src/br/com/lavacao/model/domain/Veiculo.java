/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.lavacao.model.domain;

/**
 *
 * @author matheus.ti
 */
public class Veiculo {
    private int id;
    private Modelo modelo;
    private Cor cor;
    private String placa;
    private String observacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "Veiculo{" + "id=" + id + ", modelo=" + modelo + ", cor=" + cor + ", placa=" + placa + ", observacao=" + observacao + '}';
    }
    
    
}
