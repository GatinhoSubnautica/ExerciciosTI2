package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Comida implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String DESCRICAO_PADRAO = "Nova comida";
	public static final int MAX_ESTOQUE = 1000;
	private int id;
	private String descricao;
	private float preco;
	private int quantidade;
	private LocalDateTime dataFab;	
	private LocalDate dataVal;
	
	public Comida() {
		id = -1;
		descricao = DESCRICAO_PADRAO;
		preco = 0.01F;
		quantidade = 0;
		dataFab = LocalDateTime.now();
		dataVal = LocalDate.now().plusMonths(5); 
	}

	public Comida(int id, String descricao, float preco, int quantidade, LocalDateTime fabricacao, LocalDate v) {
		setId(id);
		setDescricao(descricao);
		setPreco(preco);
		setQuant(quantidade);
		setDataFab(fabricacao);
		setDataVal(v);
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
		if (descricao.length() >= 5)
			this.descricao = descricao;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		if (preco > 0)
			this.preco = preco;
	}

	public int getQuant() {
		return quantidade;
	}
	
	public void setQuant(int quantidade) {
		if (quantidade >= 0 && quantidade <= MAX_ESTOQUE)
			this.quantidade = quantidade;
	}
	
	public LocalDate getDataVal() {
		return dataVal;
	}

	public LocalDateTime getDataFab() {
		return dataFab;
	}

	public void setDataFab(LocalDateTime dataFab) {
		// Pega a Data Atual
		LocalDateTime agora = LocalDateTime.now();
		// Garante que a data de fabricação não pode ser futura
		if (agora.compareTo(dataFab) >= 0)
			this.dataFab = dataFab;
	}

	public void setDataVal(LocalDate dataVal) {
		// a data de fabricação deve ser anterior é data de validade.
		if (getDataFab().isBefore(dataVal.atStartOfDay()))
			this.dataVal = dataVal;
	}

	public boolean emValidade() {
		return LocalDateTime.now().isBefore(this.getDataVal().atTime(23, 59));
	}

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Comida: " + descricao + "   Preço: R$" + preco + "   Quant.: " + quantidade + "   Fabricação: "
				+ dataFab  + "   Data de Validade: " + dataVal;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Comida) obj).getId());
	}	
}