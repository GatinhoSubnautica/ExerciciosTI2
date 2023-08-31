package com.ti2cc;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        DAO dao = new DAO();
        dao.conectar();
        
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("===== Menu =====");
            System.out.println("1- Listar Produtos");
            System.out.println("2- Inserir Produto");
            System.out.println("3- Excluir Produto");
            System.out.println("4- Atualizar Produto");
            System.out.println("5- Sair");
            System.out.print("Insira uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("==== Produtos === ");
                    Produtos[] produtos = dao.getProdutos();
                    for (Produtos produto : produtos) {
                        System.out.println(produto.toString());
                    }
                    break;
                case 2:
                    System.out.print("Nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.print("Categoria (A/L/V): ");
                    String categoria = scanner.nextLine();
                    System.out.print("Preço: ");
                    double preco = scanner.nextDouble();
                    System.out.print("Quantidade: ");
                    int quantidade = scanner.nextInt();
                    scanner.nextLine();

                    Produtos novoProduto = new Produtos(nome, categoria, preco, quantidade);
                    if (dao.inserirProdutos(novoProduto)) {
                        System.out.println("Produto inserido = " + novoProduto.toString());
                    }
                    break;
                case 3:
                    System.out.print("Nome do produto a ser excluído: ");
                    String nomeExcluir = scanner.nextLine();
                    dao.excluirProdutos(nomeExcluir);
                    System.out.println("Produto excluído");
                    break;
                case 4:
                    System.out.print("Nome do produto a ser atualizado: ");
                    String nomeAtualizar = scanner.nextLine();
                    Produtos produtoAtualizar = dao.getProduto(nomeAtualizar);
                    if (produtoAtualizar != null) {
                        System.out.print("Novo preço: ");
                        double novoPreco = scanner.nextDouble();
                        produtoAtualizar.setPreco(novoPreco);
                        dao.atualizarProdutos(produtoAtualizar);
                        System.out.println("Produto atualizado com sucesso.");
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;
                case 5:
                    System.out.println("Fim.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
            
        } while (opcao != 5);

        scanner.close();
        dao.close();
    }
}
