package br.ufc.dc.es.meumedico.controller.domain;

public class Cuidador {

    private String nome;
    private int id;

    public Cuidador(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
