package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import business.SubCircuito.*;

public class PilotoDAO  {

    private static PilotoDAO singleton = null;

    public PilotoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS pilotos (" +
                    "Username varchar(45) NOT NULL primary key," +
                    "cts double NOT NULL," +
                    "sva double NOT NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }



    public static PilotoDAO getInstance() {
        if (PilotoDAO.singleton == null) {
            PilotoDAO.singleton = new PilotoDAO();
        }
        return PilotoDAO.singleton;
    }

    public Piloto get(Object key) {
        Piloto t = new Piloto();
        try {

            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM pilotos WHERE Username" +
                    "='"+key+"'");
            {
                if (rs.next()) {
                    t=new Piloto(rs.getString("Username"),rs.getFloat("cts"),rs.getFloat("sva"));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    public HashMap<String,Piloto> getPilotosDB() throws SQLException {

        try {
            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement s = conn.createStatement();
            try (ResultSet rs = s.executeQuery("select * from pilotos")) {
                HashMap<String,Piloto> pilotos = new HashMap<>();
                while (rs.next()) {
                    pilotos.put(rs.getString("Username"),new Piloto(rs.getString("Username"),rs.getFloat("cts"),rs.getFloat("sva")));
                }

                return pilotos;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /*
     *  Devolve o número de Pilotos na base de dados
     */
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM pilotos")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT Username FROM pilotos WHERE Username='"+key+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }
    public Piloto put(String key, Piloto t) {
        Piloto res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {


            stm.executeUpdate(
                    "insert into pilotos VALUES ( '"+t.getNome()+"', '"+t.getCts()+"', '"+t.getSva()+"') ");


        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

   


    public Piloto remove(Object key) {
        Piloto p = this.get(key);
        
        try {
            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             stm.executeUpdate("delete from pilotos WHERE Username='"+key+"'");
    
        }
        catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());

        }
        return p;
    }

    public void putAll(Map<? extends String, ? extends Piloto> pilotos) {
        for(Piloto p : pilotos.values()) {
            this.put(p.getNome(), p);
        }
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("truncate pilotos");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }
    public Collection<Piloto> values() {
        Collection<Piloto> p = new HashSet<Piloto>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select Username from pilotos")){
            while (rs.next()){
            String n = rs.getString("Username"); 
            Piloto pil = this.get(n);                    
            p.add(pil);                
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
}

