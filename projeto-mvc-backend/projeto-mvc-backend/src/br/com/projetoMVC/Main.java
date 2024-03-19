package br.com.projetoMVC;

import br.com.projetoMVC.controller.produtoController;
import br.com.projetoMVC.model.Produto;

public class Main {
	
	public static void main(String[] args) {
		
		produtoController controller = new produtoController();
		
		Produto p = new Produto();
		p.setDescricao("Mouse");
		controller.cadastrar(p);
		
		System.out.println("- Lista de Produtos - ");
		System.out.println("Id    Descrição");
		for (Produto produto : controller.listarTodos()) {
		
		System.out.println(produto.getId() + "     " + produto.getDescricao());
		
		}
		
	}

}
