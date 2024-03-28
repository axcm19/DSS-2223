package data;

import business.SubCarro.Carro;
import business.SubCarro.PC1;
import business.SubCircuito.*;
import business.SubCircuito.Record;
import business.SubCircuito.Piloto;

import java.sql.*;
import java.util.*;


/*
!!! ESTÁ PRATICAMENTE FEITO
 */

public class CircuitoDAO implements Map<String, Circuito> {

    private static CircuitoDAO singleton = null;

    //---------------------------------------------------------------------------------------------------------------//

    private CircuitoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "create table if not exists lista_tipos_estrada (" +
                    "Nome_Circuito varchar(45) not null," +     // Nome do circuito ao qual está associada
                    "ID_Estrada varchar(45) not null," +        // exemplo: NomeCircuito_Curva_1
                    "Tipo_Estrada varchar(45) not null," +  // Tipo_Estrada = (Curva, Chicane, Recta)
                    "GDU varchar(45)," +
                    "primary key (Nome_Circuito, ID_Estrada) )"; // chave composta para tabela com todos os tipos de estrada

            stm.executeUpdate(sql);
            sql = "create table if not exists tempos_medios_por_circuito (" +
                    "Nome_Circuito varchar(45) not null," +
                    "Categoria_Carro varchar(45) not null," +
                    "Tempo_Medio int," +
                    "primary key (Nome_Circuito, Categoria_Carro) )"; // chave composta para tabela de tempos medios


            stm.executeUpdate(sql);

            sql = "create table if not exists recordes (" +
                    "Tempo double not null primary key," +
                    "Piloto varchar(45)," +
                    "Carro int )";

            stm.executeUpdate(sql);

            sql = "create table if not exists circuitos (" +
                    "Nome varchar(45) not null primary key," +
                    "Distancia int," +
                    "Num_Voltas int," +
                    "Penalizacao double," +
                    "Recorde double )";

            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------------------------------//

    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */

    public static CircuitoDAO getInstance() {
        if (CircuitoDAO.singleton == null) {
            CircuitoDAO.singleton = new CircuitoDAO();
        }
        return CircuitoDAO.singleton;
    }

    //---------------------------------------------------------------------------------------------------------------//

    /**
     * @return número de circuitos na base de dados
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("select count(*) from circuitos")) {
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

    /**
     * Método que verifica se existem circuitos
     *
     * @return true se existirem 0 circuitos
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    //---------------------------------------------------------------------------------------------------------------//

    /**
     * Método que certifica se um nome de circuito existe na base de dados
     *
     * @param key nome do circuito
     * @return true se o circuito existe
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("select Nome from circuitos where Nome='"+key.toString()+"'")) {
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
     * Verifica se um circuito existe na base de dados
     *
     * Esta implementação é provisória. Devia testar o objecto e não apenas a chave.
     *
     * @param value ...
     * @return ...
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public boolean containsValue(Object value) {
        Circuito t = (Circuito) value;
        return this.containsKey(t.getNome());
    }

    //---------------------------------------------------------------------------------------------------------------//

    @Override
    public Circuito get(Object key) {
        Circuito c = new Circuito();
        Record r = new Record();
        Piloto p = new Piloto();
        Carro carr = new PC1();
        String nome_circ = null;
        int distancia = 0;
        int voltas = 0;
        double penalizacao = 0;
        double record_time = 0;
        String nome_pil = null;
        int id_carro = 0;


        try {
            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM circuitos WHERE Nome" + "='"+key+"'");

            if (rs.next()) {
                nome_circ = rs.getString("Nome");
                voltas = rs.getInt("Num_Voltas");
                distancia = rs.getInt("Distancia");
                penalizacao = rs.getDouble("Penalizacao");
                record_time = rs.getDouble("Recorde");
            }

            {
                List<Tipo_Estrada> road_list = new ArrayList<>();
                try (ResultSet rs_lista_estrada = stm.executeQuery("select * from lista_tipos_estrada where Nome_Circuito" + "='"+key+"'")) {

                    while(rs_lista_estrada.next()) {
                        if (Objects.equals(rs_lista_estrada.getString("Tipo_Estrada"), "Recta")) {
                            Recta re = new Recta(rs_lista_estrada.getString("ID_Estrada"), rs_lista_estrada.getString("GDU"));
                            road_list.add(re);
                        } else if (Objects.equals(rs_lista_estrada.getString("Tipo_Estrada"), "Curva")) {
                            Curva cu = new Curva(rs_lista_estrada.getString("ID_Estrada"), rs_lista_estrada.getString("GDU"));
                            road_list.add(cu);
                        } else if (Objects.equals(rs_lista_estrada.getString("Tipo_Estrada"), "Chicane")) {
                            Chicane ch = new Chicane(rs_lista_estrada.getString("ID_Estrada"));
                            road_list.add(ch);
                        }
                    }

                }

                Map<String, Double> temp_med = new HashMap<>();
                try (ResultSet rs_temp_med = stm.executeQuery("select * from tempos_medios_por_circuito where Nome_Circuito" + "='"+key+"'")) {

                    while(rs_temp_med.next()) {
                        temp_med.put(rs_temp_med.getString("Categoria_Carro"), rs_temp_med.getDouble("Tempo_Medio"));
                    }

                }


                try(ResultSet rs_record = stm.executeQuery( "select * from recordes where Tempo"+ "='"+record_time+"'")) {

                    if (rs_record.next()) {  // Encontrou o record
                        nome_pil = rs_record.getString("Piloto");
                        id_carro = rs_record.getInt("Carro");

                    }
                }

                //--------- buscar piloto ---------

                try(ResultSet rs_piloto = stm.executeQuery("select * from pilotos where Username"+ "='"+nome_pil+"'");) {

                    if (rs_piloto.next()) {  // Encontrou o piloto

                        p = new Piloto(nome_pil,
                                rs_piloto.getDouble("SVA"),
                                rs_piloto.getDouble("CTS"));
                    }
                }
                //--------- buscar carro ---------

                try(ResultSet rs_carro = stm.executeQuery("select * from carros where ID_Carro"+ "='"+id_carro+"'");) {

                    if (rs_carro.next()) {  // Encontrou o carro

                        // ainda não sei como fazer se for de outra categoria
                        // por enquanto fica PC1
                        carr = new PC1(rs_carro.getInt("ID_Carro"),
                                rs_carro.getString("Marca"),
                                rs_carro.getString("Modelo"),
                                rs_carro.getInt("Cilindrada"),
                                rs_carro.getInt("Potencia"),
                                "",
                                rs_carro.getDouble("Pac"),
                                0);

                    }
                }

                r = new Record(record_time, p, carr);

                c = new Circuito(nome_circ,distancia,voltas,temp_med,penalizacao,road_list,r);
            }
        }

        catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }



    //---------------------------------------------------------------------------------------------------------------//

    /**
     * Insere um circuito na base de dados
     *
     * ATENÇÂO: Esta implementação é provisória.
     * Falta devolver o valor existente (caso exista um)
     * Deveria utilizar transacções...
     *
     * @param key o nome do circuito
     * @param c o circuito
     * @return para já retorna sempre null (deverá devolver o valor existente, caso exista um)
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Circuito put(String key, Circuito c) {
        Circuito res = null;
        Record r = c.getRecord();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // Actualizar a tabela de 'recordes'
            stm.executeUpdate(
                    "insert into recordes values " +
                            "("+ r.getTempo()+ ", '"+
                            r.getPiloto().getNome()+"', "+
                            r.getCarro().get_ID_Carro()+") " +
                            "on duplicate key update Piloto = Values(Piloto), " +
                            "Carro=Values(Carro)");

            // Actualizar a tabela de 'circuitos'
            stm.executeUpdate(
                    "insert into circuitos values " +
                            "('"+c.getNome()+"', "+
                            c.getDistancia()+", " +
                            c.getVoltas()+", " +
                            c.getPenalizacao()+", " +
                            c.getRecord().getTempo()+") " +
                            "on duplicate key update Recorde = values(Recorde)");

            // Actualizar a tabela 'lista_tipos_estrada'
            for(Tipo_Estrada te : c.getListaEstrada()){
                stm.executeUpdate(
                        "insert into lista_tipos_estrada values " +
                                "('"+c.getNome()+"', '"+
                                te.getIdTipoEstrada()+"', ' " +
                                te.imprime_tipo_estrada()+"', '" +
                                te.getGDU()+"') ");
            }

            // Actualizar a tabela 'tempos_medios_por_circuito'
            for(String categoria : c.getTemposMedios().keySet()){
                stm.executeUpdate(
                        "insert into tempos_medios_por_circuito values " +
                                "('"+c.getNome()+"', '"+
                                categoria+"', " +
                                c.getTemposMedios().get(categoria)+") ");
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
     * Remover um circuito, dado o seu nome
     *
     * NOTA: Não estamos a apagar o recorde (acho eu ???)...
     *
     * @param key nome do circuito a remover
     * @return o circuito removido
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Circuito remove(Object key) {
        Circuito c = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // apagar o circuito
            stm.executeUpdate("delete from circuitos where Nome='"+key+"'");

        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }

    //---------------------------------------------------------------------------------------------------------------//

    /**
     * Adicionar um conjunto de circuitos à base de dados
     *
     * @param circuitos --> os circuitos a adicionar
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public void putAll(Map<? extends String, ? extends Circuito> circuitos) {
        for(Circuito c : circuitos.values()) {
            this.put(c.getNome(), c);
        }
    }

    //---------------------------------------------------------------------------------------------------------------//

    /**
     * Apagar todos os circuitos
     *
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("truncate circuitos");
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
     * @return Todos os circuitos da base de dados
     */
    @Override
    public Collection<Circuito> values() {
        Collection<Circuito> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("select Nome from circuitos")) { // ResultSet com os nomes de todos os circuitos
            while (rs.next()) {
                String idt = rs.getString("Nome"); // Obtemos um nome de circuito do ResultSet
                Circuito c = this.get(idt);                    // Utilizamos o get para construir os circuitos um a um
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

    /**
     * NÃO IMPLEMENTADO!
     * @return ainda nada!
     */
    @Override
    public Set<Entry<String, Circuito>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aluno>> entrySet() not implemented!");
    }

    //---------------------------------------------------------------------------------------------------------------//

}
