package business.SubUtilizador;
/**
 * Write a description of class ClassificacaoJogadores here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.io.Serializable;
import business.SubUtilizador.Jogador;

public class ClassificacaoJogadores implements Serializable
{
    private Map<String,Jogador> jogadores; //key = nome jogador
    private Set<Jogador> classificacao;
    
    public ClassificacaoJogadores()
    {
        this.jogadores = new HashMap<String,Jogador>();
        this.classificacao = new TreeSet<Jogador>( new JogadorComparatorNome() );
    }
    
    public ClassificacaoJogadores(Map<String,Jogador> j, Set<Jogador> c)
    {
        this();
        for(String n : j.keySet())
        {
            this.jogadores.put(n, j.get(n).clone());
        }
        for(Jogador jog : c)
        {
            this.classificacao.add(jog.clone());
        }
    }
    
    public ClassificacaoJogadores(ClassificacaoJogadores c)
    {
        this.jogadores = c.getJogadores();
    }
    
    public Map<String,Jogador> getJogadores()
    {
        HashMap<String,Jogador> aux = new HashMap<String,Jogador>();
        for(String n : this.jogadores.keySet())
        {
            aux.put(n, this.jogadores.get(n).clone());
        }
        return aux;
    }
    
    //Metodos
    /**
     * adicionar um jogador
     */
    public void adicionarJogador(Jogador j)
    {
        this.jogadores.put(j.getUsername(), j.clone());
    }
    
    /**
     * adicionar map de jogadores
     */
    public void adicionarJogador(Map<String,Jogador> j)
    {
        for(String n : j.keySet())
        {
            this.jogadores.put(n, j.get(n).clone());
        }
    }
    
    /**
     * Jogador por nome
     */
    public Jogador getJogador(String nome)
    {
        return this.jogadores.get(nome).clone();
    }
    
 
    /**
    * Verificar se jogador existe
    */
    public boolean existeJogador(String nome)
    {
      return this.jogadores.containsKey(nome);
    }
    

    // print pode ser usado para apresentar as classificação dos jogadores   ??????
    
    /**
     * Lista os vencedores e o total ganho da aposta na corrida
     */
    public String printVencedores(Map<String, Integer> res)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n||||| Vencedores das apostas |||||");
        sb.append("\nNome \t\tTotal Ganho");
        for(String s : res.keySet())
        {
            sb.append("\n");
            sb.append(s);sb.append("\t\t");sb.append(res.get(s));
        }
        return sb.toString();
    }
}
