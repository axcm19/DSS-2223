package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import business.SubUtilizador.*;

public class JogadorDAO {
    private static JogadorDAO singleton = null;

    private JogadorDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "create table if not exists jogadores (" +
            "Username varchar(45) NOT NULL primary key,"+
            "Password varchar(45) NOT NULL,"+
            "PontosJogador int,"+
            "IsAutenticado boolean,"+
            "PontosCorrida int)";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static JogadorDAO getInstance() {
        if (JogadorDAO.singleton == null) {
            JogadorDAO.singleton = new JogadorDAO();
        }
        return JogadorDAO.singleton;
    }

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("select count(*) from jogadores")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("select Username from jogadores where Username='"+key+"'")) {
            r = rs.next();
        } catch (SQLException e) {

            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }
    public boolean containsValue(Object value) {
        Jogador jog = (Jogador) value;
        return this.containsKey(jog.getUsername());
    }

    public Jogador get(Object key) {
        try (
            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * from jogadores WHERE Username='"+key+"'")) {
                if (rs.next()){
                    return new Jogador(rs.getString("Username"),
                                       rs.getString("Password"),
                                       rs.getInt("PontosJogador"),
                                       rs.getBoolean("IsAutenticado"),
                                       rs.getInt("PontosCorrida"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return null;
    }


    public Jogador put(String key, Jogador j) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            
            stm.executeUpdate(
                    "insert into jogadores values " +
                            "('"+j.getUsername() + "', '"+
                            j.getPassword()+"', "+
                            j.getPontos()+", "+
                            j.jogAutenticado()+", "+
                            j.getPontosCorrida()+")" );

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return j;
    }

    public Jogador remove(Object key) {
        Jogador j = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // apagar o circuito
            stm.executeUpdate("delete from jogadores where Username='"+key+"'");

        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return j;
    }
    public void putAll(Map<? extends String, ? extends Jogador> jogadores) {
        for(Jogador j : jogadores.values()) {
            this.put(j.getUsername(), j);
        }
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("truncate jogadores");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }
    public Collection<Jogador> values() {
        Collection<Jogador> j = new HashSet<Jogador>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select Username from jogadores")){
            while (rs.next()){
            String n = rs.getString("Username"); 
            Jogador jog = this.get(n);                    
            j.add(jog);                
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return j;
    }
}


    