package br.com.lavacao.model.dao;

import br.com.lavacao.model.domain.Marca;
import br.com.lavacao.model.domain.Modelo;
import br.com.lavacao.model.domain.Marca;
import br.com.lavacao.model.domain.Modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModeloDAO{

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Modelo modelo) {
        String sql = "INSERT INTO modelo(nome, id_marca) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getNome());
            stmt.setInt(2, modelo.getMarca().getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Modelo modelo) {
        String sql = "UPDATE modelo SET nome=?, id_categoria=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getNome());
            stmt.setInt(2, modelo.getMarca().getId());
            stmt.setInt(3, modelo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Modelo modelo) {
        String sql = "DELETE FROM modelo WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Modelo> listar() {
        String sql =  "SELECT m.id as modelo_id, m.nome as modelo_nome "
                + "mr.id as marca_id, mr.descricao as marca_descricao "
                + "FROM modelo m INNER JOIN marca mr ON mr.id = m.id_marca;";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Modelo modelo = populateVO(resultado);
                retorno.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<Modelo> listarPorMarca(Marca marca) {
        String sql =  "SELECT p.id as produto_id, p.nome as produto_nome, p.descricao as produto_descricao, p.preco as produto_preco, "
                + "c.id as categoria_id, c.descricao as categoria_descricao "
                + "FROM produto p INNER JOIN categoria c ON c.id = p.id_categoria WHERE c.id = ?;";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, marca.getId());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Modelo modelo = populateVO(resultado);
                retorno.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Modelo buscar(Modelo modelo) {
        String sql =  "SELECT m.id as modelo_id, m.nome as produto_nome, p.descricao as produto_descricao, p.preco as produto_preco, "
                + "c.id as categoria_id, c.descricao as categoria_descricao "
                + "FROM produto p INNER JOIN categoria c ON c.id = p.id_categoria WHERE p.id = ?;";
        //Modelo retorno = new Modelo() ;
        Modelo retorno = new Modelo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno = populateVO(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    private Modelo populateVO(ResultSet rs) throws SQLException {
        Modelo modelo = new Modelo();
        Marca marca = new Marca();
        modelo.setMarca(marca);
        
        modelo.setId(rs.getInt("produto_id"));
        modelo.setNome(rs.getString("produto_nome"));
        //modelo.setDescricao(rs.getString("produto_descricao"));
        //modelo.setPreco(rs.getBigDecimal("produto_preco"));
        marca.setId(rs.getInt("categoria_id"));
        marca.setNome(rs.getString("categoria_descricao"));
        return modelo;
    }    
}
