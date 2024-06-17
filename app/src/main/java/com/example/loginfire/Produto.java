package com.example.loginfire;

public class Produto {
    private String nome;
    private String descricao;
    private double preco;
    private String imagemUrl;

    public Produto(String nome, String descricao, double preco, String imagemUrl) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }
}
