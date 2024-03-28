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

public class AdministradorDAO {
    private static AdministradorDAO singleton = null;

    public AdministradorDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS administradores (" +
                    "Username varchar(45) NOT NULL," +
                    "Password varchar(45) NOT NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static AdministradorDAO getInstance() {
        if (AdministradorDAO.singleton == null) {
            AdministradorDAO.singleton = new AdministradorDAO();
        }
        return AdministradorDAO.singleton;
    }

    public Administrador get(Object key) {
       Administrador t = new Administrador();
        try {

            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Username,Password FROM administradores WHERE Username" +
                    "='"+key+"'");
            {
                if (rs.next()) {
                    t=new Administrador(rs.getString("Username"),rs.getString("Password"), true);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM administradores")) {
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
                     stm.executeQuery("SELECT Username FROM administradores WHERE Username='"+key+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }
    public boolean isEmpty() {
        return this.size() == 0;
    }


    public Administrador put(String key, Administrador t) {
        Administrador res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {


            stm.executeUpdate(
                    "insert into administradores VALUES ( '"+t.getUsername()+"', '"+t.getPassword()+"') ");


        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }
    public Administrador remove(Object key) {
       Administrador p = this.get(key);
        
        try {
            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             stm.executeUpdate("delete from administradores WHERE Username='"+key+"'");
    
        }
        catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());

        }
        return p;
    }

    public void putAll(Map<? extends String, ? extends Administrador> administradores) {
        for(Administrador p : administradores.values()) {
            this.put(p.getUsername(), p);
        }
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("truncate administradores");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }
    public Collection<Administrador> values() {
        Collection<Administrador> p = new HashSet<Administrador>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select Username from administradores")){
            while (rs.next()){
            String n = rs.getString("Username"); 
            Administrador ad = this.get(n);                    
            p.add(ad);                
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

}