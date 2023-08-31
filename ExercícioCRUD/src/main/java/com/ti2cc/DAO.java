package com.ti2cc;

import java.sql.*;

public class DAO {
	private Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "CRUD";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean inserirProdutos(Produtos produtos) {
	    boolean status = false;
	    
	    try {  
	        PreparedStatement st = conexao.prepareStatement("INSERT INTO produtos (nome, categoria, preco, quantidade) VALUES (?, ?, ?, ?)");
	        st.setString(1, produtos.getNome());
	        st.setString(2, produtos.getCategoria());
	        st.setDouble(3, produtos.getPreco());
	        st.setInt(4, produtos.getQuantidade());
	        st.executeUpdate();
	        st.close();
	        status = true;
	    } catch (SQLException u) {  
	        throw new RuntimeException(u);
	    }
	    
	    return status;
	}

	
	public boolean atualizarProdutos(Produtos produtos) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE produtos SET nome = '" + produtos.getNome() + "', categoria = '"  
				       + produtos.getCategoria() + "', preco = '" + produtos.getPreco() + "'"
					   + " WHERE quantidade = " + produtos.getQuantidade();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirProdutos(String nome) {
	    boolean status = false;
	    
	    try {  
	        PreparedStatement st = conexao.prepareStatement("DELETE FROM produtos WHERE nome = ?");
	        st.setString(1, nome);
	        st.executeUpdate();
	        st.close();
	        status = true;
	    } catch (SQLException u) {  
	        throw new RuntimeException(u);
	    }
	    
	    return status;
	}
	
	
	public Produtos[] getProdutos() {
		Produtos[] produtos = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM produtos");		
	         if(rs.next()){
	             rs.last();
	             produtos = new Produtos[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                produtos[i] = new Produtos(rs.getString("nome"), rs.getString("categoria"), 
	                		                  rs.getDouble("preco"), rs.getInt("quantidade"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produtos;
	}

	
	public Produtos getProduto(String nomeProduto) {
	    Produtos produto = null;
	    
	    try {
	        PreparedStatement st = conexao.prepareStatement("SELECT * FROM produtos WHERE nome = ?");
	        st.setString(1, nomeProduto);
	        ResultSet rs = st.executeQuery();
	        
	        if (rs.next()) {
	            produto = new Produtos(rs.getString("nome"), rs.getString("categoria"), rs.getDouble("preco"), rs.getInt("quantidade"));
	        }
	        
	        st.close();
	    } catch (SQLException e) {
	        System.err.println(e.getMessage());
	    }
	    
	    return produto;
	}

}