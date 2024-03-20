package br.com.projetoMVC.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.projetoMVC.model.Produto;
import br.com.projetoMVC.util.ConnectionFactory;

public class ProdutoDAOImpl implements GenericDAO {

	private Connection conn;

	// Construtor vazio da classe ProdutoDAOImpl, iniciando a conexão com o banco
	// de dados através da classe ConnectionFactory
	public ProdutoDAOImpl() throws Exception {
		try {
			this.conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<Object> listarTodos() {

		List<Object> lista = new ArrayList<Object>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM produto";

		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setDescricao(rs.getString("descricao"));
				lista.add(produto);
			}
		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao listar Produto! " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				ConnectionFactory.closeConnection(conn, stmt, rs);
			} catch (Exception e) {
				System.out.println("Problemas na DAO ao fechar conexão! " + e.getMessage());
			}
		}

		return lista;
	}

	@Override
	public Object listarPorId(int id) {

		Produto produto = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM produto WHERE id = ?";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setDescricao(rs.getString("descricao"));
			}

		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao listar Produto por id! " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				ConnectionFactory.closeConnection(conn, stmt, rs);
			} catch (Exception e) {
				System.out.println("Problemas na DAO ao fechar conexão! " + e.getMessage());
				e.printStackTrace();
			}
		}

		return produto;
	}

	@Override
	public boolean cadastrar(Object object) {
		
		Produto produto = (Produto) object;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO produto (descricao) VALUES (?)";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, produto.getDescricao());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao cadastrar Produto " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			try {
				ConnectionFactory.closeConnection(conn, stmt, null);
			} catch (Exception e) {
				System.out.println("Problemas na DAO ao fechar conexão! " + e.getMessage());
				e.printStackTrace();
			}		
		}
		
	}

	@Override
	public boolean alterar(Object object) {
		Produto produto = (Produto) object;
		PreparedStatement stmt = null;
		String sql = "UPDATE produto SET descricao = ? WHERE id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, produto.getDescricao());
			stmt.setInt(2, produto.getId());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			System.out.println("Erros na DAO ao alterar Produto! " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			try {
				ConnectionFactory.closeConnection(conn, stmt, null);
			} catch (Exception e) {
				System.out.println("Problemas na DAO ao fechar conexão! " + e.getMessage());
				e.printStackTrace();
			}	
		}
	}

	@Override
	public void excluir(int id) {
		PreparedStatement stmt = null;
		String sql = "DELETE FROM produto WHERE id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao excluir Produto! " + ex.getMessage());
		} finally {
			try {
				ConnectionFactory.closeConnection(conn, stmt, null);
			} catch (Exception e) {
				System.out.println("Problemas na DAO ao fechar conexão! " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
