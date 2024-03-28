package data;

import business.SubCampeonato.*;
import business.SubCarro.Carro;
import business.SubCircuito.Circuito;
import business.SubCircuito.Piloto;
import business.SubCorrida.Corrida;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.*;
import java.lang.String;



public class CampeonatoDAO implements Map<String,Campeonato> {  // antes era: implements Map<String,Campeonato>

    private static CampeonatoDAO singleton=null;

    //---------------------------------------------------------------------------------------------------------------//

    private CampeonatoDAO(){
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // esta tabela tem de guardar todos os campeonato
            String sql = "CREATE TABLE IF NOT EXISTS campeonatos (" +
                    "Nome_Campeonato varchar(45) NOT NULL primary key) ";

            stm.executeUpdate(sql);

            sql = "create table if not exists circuitos_dum_campeonato (" +
                    "Nome_Campeonato varchar(45) not null," +
                    "Nome_Circuito varchar(45) not null," +
                    "primary key (Nome_Campeonato, Nome_Circuito) )";

            stm.executeUpdate(sql);
        
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------------------------------//

    public static CampeonatoDAO getInstance() {
        if (CampeonatoDAO.singleton == null) {
            CampeonatoDAO.singleton = new CampeonatoDAO();
        }
        return CampeonatoDAO.singleton;
    }

    //---------------------------------------------------------------------------------------------------------------//

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("select count(*) from campeonatos")) {
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

    /**
     * Método que certifica se um nome de campeonato existe na base de dados
     *
     * @param key nome do campeonato
     * @return true se o campeonato existe
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("select Nome_Campeonato from campeonatos where Nome_Campeonato='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    //---------------------------------------------------------------------------------------------------------------//

    /**
     * Verifica se um campeonato existe na base de dados
     *
     * Esta implementação é provisória. Devia testar o objecto e não apenas a chave.
     *
     * @param value ...
     * @return ...
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public boolean containsValue(Object value) {
        Campeonato t =  (Campeonato) value;
        return this.containsKey(t.getNome());
    }

    //---------------------------------------------------------------------------------------------------------------//

    public Campeonato get(Object key) {
        Campeonato camp;
        Map<Piloto, Carro> listaPilotosCarros = new HashMap<>();
        List corridas = new ArrayList<Corrida>();
        Map classificacao = new HashMap<String,Integer>();
        Map classificacaoH = new HashMap<String,Integer>();
        String nome_camp = null;
        Corrida corr;
        Circuito circ;
        Set<Carro> carroSet = new TreeSet<>();
        List<Carro> carroList = new ArrayList<>();

        try {

            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM campeonatos WHERE Nome_Campeonato" + "='"+key+"'");{

                if (rs.next()) {
                    nome_camp = rs.getString("Nome_Campeonato");
                }

            }

            try(ResultSet rs_lista_circuitos = stm.executeQuery("SELECT * FROM circuitos_dum_campeonato WHERE Nome_Campeonato" + "='"+key+"'")){
                int i = 1;
                while(rs_lista_circuitos.next()){
                    String nome_circ = rs_lista_circuitos.getString("Nome_Circuito");
                    circ = new Circuito(CircuitoDAO.getInstance().get(nome_circ));
                    corr = new Corrida(listaPilotosCarros,i,circ,carroSet,carroList,0);

                    corridas.add(corr);

                    i++;
                }
            }

            camp = new Campeonato(nome_camp,corridas,classificacao,classificacaoH,0);
        }

        catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return camp;
    }
    //---------------------------------------------------------------------------------------------------------------//

    public Campeonato put(String key, Campeonato t) {
        Campeonato res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {


            stm.executeUpdate(
                    "insert into campeonatos VALUES ( '"+t.getNome()+"') ");

            for(Corrida corr : t.getCorridas()) {
                stm.executeUpdate(
                        "insert into circuitos_dum_campeonato VALUES ( '" + t.getNome() + "', '" + corr.getCircuito().getNome()+"') ");

            }


        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    //---------------------------------------------------------------------------------------------------------------//

    /**
     * Remover um campeonato, dado o seu nome
     *
     * NOTA: Não estamos a apagar o recorde (acho eu ???)...
     *
     * @param key nome do campeonato a remover
     * @return o campeonato removido
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Campeonato remove(Object key) {
        Campeonato c = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // apagar o campeonato
            stm.executeUpdate("delete from campeonatos where Nome_Campeonato='"+key+"'");

        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }

    //---------------------------------------------------------------------------------------------------------------//

    /**
     * Adicionar um conjunto de campeonatos à base de dados
     *
     * @param campeonatos --> os campeonatos a adicionar
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */

    public void putAll(Map<? extends String, ? extends Campeonato> campeonatos) {
        for (Campeonato c : campeonatos.values()) {
            this.put(c.getNome(), c);
        }
    }

    //---------------------------------------------------------------------------------------------------------------//

    /**
     * Apagar todos os campeonatos
     *
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("truncate campeonatos");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------------------------------//

    /**
     * NÃO IMPLEMENTADO!
     * @return ainda nada!
     */
    @Override
    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    //---------------------------------------------------------------------------------------------------------------//

    /**
     * @return Todos os campeonatos da base de dados
     */
    @Override
    public Collection <Campeonato> values() {
        Collection <Campeonato> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("select Nome_Campeonato from campeonatos")) { // ResultSet com os nomes de todos os campeonatos
            while (rs.next()) {
                String idt = rs.getString("Nome"); // Obtemos um nome de campeonato do ResultSet
                Campeonato c = this.get(idt);                    // Utilizamos o get para construir os campeonatos um a um
                res.add(c);                                 // Adiciona ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    //---------------------------------------------------------------------------------------------------------------//

    @Override
    public Set<Entry<String, Campeonato>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Campeonato>> entrySet() not implemented!");
    }
}
