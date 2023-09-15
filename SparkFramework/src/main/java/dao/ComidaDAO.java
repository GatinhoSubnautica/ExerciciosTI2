package dao;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.Comida;

public class ComidaDAO {
    private Connection conexao;

    public ComidaDAO() {
        conexao = null;
    }

    public boolean conectar() {
        String driverName = "org.postgresql.Driver";
        String serverName = "localhost";
        String mydatabase = "CRUD";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
        String username = "ti2cc";
        String password = "ti@cc";
        boolean status = false;
        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com o PostgreSQL!");
        } catch (ClassNotFoundException e) {
            System.err.println("Conexão NÃO efetuada com o PostgreSQL -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o PostgreSQL -- " + e.getMessage());
        }

        return status;
    }

    public boolean close() {
        boolean status = false;

        try {
            conexao.close();
            status = true;
            System.out.println("Conexão fechada.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }

    public boolean add(Comida comida) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement(
                    "INSERT INTO comida (id, descricao, preco, quantidade, dataFab, dataVal) VALUES (?, ?, ?, ?, ?, ?)");
            st.setInt(1, comida.getId());
            st.setString(2, comida.getDescricao());
            st.setDouble(3, comida.getPreco());
            st.setInt(4, comida.getQuant());
            st.setTimestamp(5, Timestamp.valueOf(comida.getDataFab()));
            st.setDate(6, Date.valueOf(comida.getDataVal()));
            st.executeUpdate();
            st.close();
            status = true;
            System.out.println("Registro inserido com sucesso.");
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }

    public boolean atualizarComida(Comida comida) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement(
                    "UPDATE comida SET descricao = ?, preco = ?, quantidade = ?, dataFab = ?, dataVal = ? WHERE id = ?");
            st.setString(1, comida.getDescricao());
            st.setDouble(2, comida.getPreco());
            st.setInt(3, comida.getQuant());
            st.setTimestamp(4, Timestamp.valueOf(comida.getDataFab()));
            st.setDate(5, Date.valueOf(comida.getDataVal()));
            st.setInt(6, comida.getId());
            st.executeUpdate();
            st.close();
            status = true;
            System.out.println("Registro atualizado com sucesso.");
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean remove(Comida comida) {
        boolean status = false;
        try {
            PreparedStatement st = conexao.prepareStatement("DELETE FROM comida WHERE id = ?");
            st.setInt(1, comida.getId());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                status = true;
            }
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }


    public Comida[] getComida() {
        Comida[] comida = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM comida");
            if (rs.next()) {
                rs.last();
                comida = new Comida[rs.getRow()];
                rs.beforeFirst();

                for (int i = 0; rs.next(); i++) {
                    comida[i] = new Comida(rs.getInt("id"), rs.getString("descricao"),
                            rs.getFloat("preco"), rs.getInt("quantidade"),
                            rs.getTimestamp("dataFab").toLocalDateTime(),
                            rs.getDate("dataVal").toLocalDate());
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return comida;
    }

    public Comida getComida(int idComida) {
        Comida comida = null;

        try {
            PreparedStatement st = conexao.prepareStatement("SELECT * FROM comida WHERE id = ?");
            st.setInt(1, idComida);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                comida = new Comida(rs.getInt("id"), rs.getString("descricao"),
                        rs.getFloat("preco"), rs.getInt("quantidade"),
                        rs.getTimestamp("dataFab").toLocalDateTime(),
                        rs.getDate("dataVal").toLocalDate());
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return comida;
    }
    public Comida[] getAll() {
        Comida[] comidas = null;
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM comida");
            if (rs.next()) {
                rs.last();
                comidas = new Comida[rs.getRow()];
                rs.beforeFirst();

                for (int i = 0; rs.next(); i++) {
                    comidas[i] = new Comida(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getFloat("preco"),
                        rs.getInt("quantidade"),
                        rs.getTimestamp("dataFab").toLocalDateTime(),
                        rs.getDate("dataVal").toLocalDate()
                    );
                }
            }
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comidas;
    }

    public int getMaxId() {
        int maxId = 0;
        try {
            PreparedStatement st = conexao.prepareStatement("SELECT MAX(id) FROM comida");
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return maxId;
    }

}

		
		
	