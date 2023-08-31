package com.ti2cc;

public class Produtos {
	private String nome;
	private String categoria;
	private double preco;
	private int quantidade;
	public int length;
	
	public Produtos() {
		this.nome = "";
		this.categoria = "";
		this.preco = '*';
		this.quantidade = '*';
	}
	
	public Produtos(String nome, String categoria, double preco, int quantidade) {
		this.nome = nome;
		this.categoria = categoria;
		this.preco = preco;
		this.quantidade = quantidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return "Produtos [nome=" + nome + ", categoria=" + categoria + ", preco=" + preco + ", quantidade=" + quantidade + "]";
	}	
}
