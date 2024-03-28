package data;

import business.SubCarro.*;
import business.SubCircuito.Circuito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.*;



public class CarroDAO implements Map<Integer,Carro> {  // antes era: implements Map<String,Carro>

    private static CarroDAO singleton=null;

    //---------------------------------------------------------------------------------------------------------------//

    private CarroDAO(){
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // esta tabela tem de guardar todos os carros, hibridos incluidos
            String sql = "CREATE TABLE IF NOT EXISTS carros (" +
                    "Id_Carro int primary key," +
                    "Categoria varchar(10)," +
                    "Marca varchar(45) NOT NULL," +
                    "Modelo varchar(45) NOT NULL," +
                    "Cilindrada int,"+
                    "Potencia int," +
                    "Potencia_Eletrico int," +  // caso o carro seja hibrido, se nao meter 0
                    "Fiabilidade int,"+
                    "Tempo double,"+
                    "Dnf bit(1),"+
                    "Pneu int,"+
                    "Pac double )";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------------------------------//

    public static CarroDAO getInstance() {
        if (CarroDAO.singleton == null) {
            CarroDAO.singleton = new CarroDAO();
        }
        return CarroDAO.singleton;
    }

    //---------------------------------------------------------------------------------------------------------------//

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("select count(*) from carros")) {
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

    //---------------------------------------------------------------------------------------------------------------//

    public boolean isEmpty() {
        return this.size() == 0;
}

    //---------------------------------------------------------------------------------------------------------------//

    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("select Id_Carro from carros where Id_Carro='"+key+"'")) {
            r = rs.next();
        } catch (SQLException e) {

            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    //---------------------------------------------------------------------------------------------------------------//

    public boolean containsValue(Object value) {
        Carro carr = (Carro) value;
        return this.containsKey(carr.get_ID_Carro());
    }

    //---------------------------------------------------------------------------------------------------------------//

    public Carro get(Object key){
        Carro c = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                 Statement stm = conn.createStatement();
                 ResultSet rs = stm.executeQuery("SELECT * from carros WHERE Id_Carro='"+key+"'")) {

            if (rs.next()) {  // O carro existe na tabela

                // verificar qual é a categoria e criar o carro conforme

                if (Objects.equals(rs.getString("Categoria"), "C1")) {
                    c = new PC1(rs.getInt("Id_Carro"),
                            rs.getString("Marca"),
                            rs.getString("Modelo"),
                            rs.getInt("Cilindrada"),
                            rs.getInt("Potencia"),
                            "",
                            rs.getDouble("Pac"),
                            0);
                }
                else if (Objects.equals(rs.getString("Categoria"), "C1H")) {
                    c = new PC1H(rs.getInt("Id_Carro"),
                            rs.getString("Marca"),
                            rs.getString("Modelo"),
                            rs.getInt("Cilindrada"),
                            rs.getInt("Potencia"),
                            "",
                            rs.getInt("Potencia_Eletrico"),
                            rs.getDouble("Pac"),
                            0);
                }
                else if (Objects.equals(rs.getString("Categoria"), "C2")) {
                    c = new PC2(rs.getInt("Id_Carro"),
                            rs.getString("Marca"),
                            rs.getString("Modelo"),
                            rs.getInt("Cilindrada"),
                            rs.getInt("Potencia"),
                            rs.getInt("Potencia"), // O construtor de PC2 pede potencia mecanica. Que é essa merda???
                            "",
                            rs.getDouble("Pac"),
                            0);
                }
                else if (Objects.equals(rs.getString("Categoria"), "C2H")) {
                    c = new PC2H(rs.getInt("Id_Carro"),
                            rs.getString("Marca"),
                            rs.getString("Modelo"),
                            rs.getInt("Cilindrada"),
                            rs.getInt("Potencia"),
                            rs.getInt("Potencia"), // O construtor de PC2 pede potencia mecanica. Que é essa merda???
                            "",
                            rs.getInt("Potencia_Eletrico"),
                            rs.getDouble("Pac"),
                            0);
                }
                else if (Objects.equals(rs.getString("Categoria"), "SC")) {
                    c = new SC(rs.getInt("Id_Carro"),
                            rs.getString("Marca"),
                            rs.getString("Modelo"),
                            rs.getInt("Cilindrada"),
                            rs.getInt("Potencia"),
                            "",
                            rs.getDouble("Pac"),
                            0);
                }
                else if (Objects.equals(rs.getString("Categoria"), "GT")) {
                    c = new GT(rs.getInt("Id_Carro"),
                            rs.getString("Marca"),
                            rs.getString("Modelo"),
                            rs.getInt("Cilindrada"),
                            rs.getInt("Potencia"),
                            "",
                            rs.getDouble("Pac"),
                            0);
                }
                else if (Objects.equals(rs.getString("Categoria"), "GTH")) {
                    c = new GTH(rs.getInt("Id_Carro"),
                            rs.getString("Marca"),
                            rs.getString("Modelo"),
                            rs.getInt("Cilindrada"),
                            rs.getInt("Potencia"),
                            "",
                            rs.getInt("Potencia_Eletrico"),
                            rs.getDouble("Pac"),
                            0);
                }

            }
        } catch(SQLException e){
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }

    //---------------------------------------------------------------------------------------------------------------//

    public Carro put(Integer key, Carro c) {
        Carro carro = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // é assim que se faz?
            if(c instanceof PC1) {
                PC1 cast = (PC1) c;
                stm.executeUpdate(
                        "insert into carros values " +
                                "(" + cast.get_ID_Carro() + ", ' " +
                                "C1" + "', ' " +
                                cast.getMarca() + "', ' " +
                                cast.getModelo() + "', " +
                                cast.getCilindrada() + ", " +
                                cast.getPotencia() + ", " +
                                0 + ", " +
                                cast.getFiabilidade() + ", " +
                                cast.getTempo() + ", " +
                                0 + ", " +
                                0 + ", " +                 // pneu
                                cast.getPAC() + ") ");        // pac


            }




            else if(c instanceof PC1H) {
                PC1H cast = (PC1H) c;
                stm.executeUpdate(
                        "insert into carros values " +
                                "(" + cast.get_ID_Carro() + ", '" +
                                "C1H" + "', '" +
                                cast.getMarca() + "', '" +
                                cast.getModelo() + "', " +
                                cast.getCilindrada() + ", " +
                                cast.getPotencia() + ", " +
                                cast.getPotenciaMotorEletrico() + ", " +
                                cast.getFiabilidade() + ", " +
                                cast.getTempo() + ", " +
                                0 + ", " +
                                0 + ", " +                 // pneu
                                cast.getPAC() + ") ");        // pac


            }

            else if(c instanceof PC2) {
                PC2 cast = (PC2) c;
                stm.executeUpdate(
                        "insert into carros values " +
                                "(" + cast.get_ID_Carro() + ", '" +
                                "C2" + "', '" +
                                cast.getMarca() + "', '" +
                                cast.getModelo() + "', " +
                                cast.getCilindrada() + ", " +
                                cast.getPotencia() + ", " +
                                0 + ", " +
                                cast.getFiabilidade() + ", " +
                                cast.getTempo() + ", " +
                                0 + ", " +
                                0 + ", " +                 // pneu
                                cast.getPAC() + ") ");        // pac


            }

            else if(c instanceof PC2H) {
                PC2H cast = (PC2H) c;
                stm.executeUpdate(
                        "insert into carros values " +
                                "(" + cast.get_ID_Carro() + ", '" +
                                "C2H" + "', '" +
                                cast.getMarca() + "', '" +
                                cast.getModelo() + "', " +
                                cast.getCilindrada() + ", " +
                                cast.getPotencia() + ", " +
                                cast.getPotenciaMotorEletrico() + ", " +
                                cast.getFiabilidade() + ", " +
                                cast.getTempo() + ", " +
                                0 + ", " +
                                0 + ", " +                 // pneu
                                cast.getPAC() + ") ");        // pac


            }

            else if(c instanceof GT) {
                GT cast = (GT) c;
                stm.executeUpdate(
                        "insert into carros values " +
                                "(" + cast.get_ID_Carro() + ", '" +
                                "GT" + "', '" +
                                cast.getMarca() + "', '" +
                                cast.getModelo() + "', " +
                                cast.getCilindrada() + ", " +
                                cast.getPotencia() + ", " +
                                0 + ", " +
                                cast.getFiabilidade() + ", " +
                                cast.getTempo() + ", " +
                                0 + ", " +
                                0 + ", " +                 // pneu
                                cast.getPAC() + ") ");        // pac


            }

            else if(c instanceof GTH) {
                GTH cast = (GTH) c;
                stm.executeUpdate(
                        "insert into carros values " +
                                "(" + cast.get_ID_Carro() + ", '" +
                                "GTH" + "', '" +
                                cast.getMarca() + "', '" +
                                cast.getModelo() + "', " +
                                cast.getCilindrada() + ", " +
                                cast.getPotencia() + ", " +
                                cast.getPotenciaMotorEletrico() + ", " +
                                cast.getFiabilidade() + ", " +
                                cast.getTempo() + ", " +
                                0 + ", " +
                                0 + ", " +                 // pneu
                                cast.getPAC() + ") ");        // pac


            }

            else if(c instanceof SC) {
                SC cast = (SC) c;
                stm.executeUpdate(
                        "insert into carros values " +
                                "(" + cast.get_ID_Carro() + ", '" +
                                "SC" + "', '" +
                                cast.getMarca() + "', '" +
                                cast.getModelo() + "', " +
                                cast.getCilindrada() + ", " +
                                cast.getPotencia() + ", " +
                                0 + ", " +
                                cast.getFiabilidade() + ", " +
                                cast.getTempo() + ", " +
                                0 + ", " +
                                0 + ", " +                 // pneu
                                cast.getPAC() + ") ");        // pac


            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return carro;
    }

    //---------------------------------------------------------------------------------------------------------------//

    public Carro remove(Object key) {
        Carro c = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // apagar o circuito
            stm.executeUpdate("delete from carros where Id_Carro='"+key+"'");

        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }

    //---------------------------------------------------------------------------------------------------------------//

    // public void putAll(Map<? extends String, ? extends Carro> carros) {
        // Afonso: mudei para integer

    public void putAll(Map<? extends Integer, ? extends Carro> carros) {
        for(Carro c : carros.values()) {
            this.put(c.get_ID_Carro(), c);
        }
    }

    //---------------------------------------------------------------------------------------------------------------//

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("truncate carros");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------------------------------//

    public Set<Entry<Integer, Carro>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aluno>> entrySet() not implemented!");
    }

    //---------------------------------------------------------------------------------------------------------------//

    public Set<Integer> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    //---------------------------------------------------------------------------------------------------------------//

    public Collection<Carro> values() {
        Collection<Carro> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("select Id_Carro from carros")) { // ResultSet com os nomes de todos os carros
            while (rs.next()) {
                String idt = rs.getString("Id_Carro"); // Obtemos um id de carro do ResultSet
                //Carro c = this.get(idt);                    // Utilizamos o get para construir os carros um a um
                res.add(this.get(idt));                                 // Adiciona ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }



}