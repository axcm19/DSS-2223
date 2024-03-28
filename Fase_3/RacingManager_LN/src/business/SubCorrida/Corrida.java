package business.SubCorrida;
/**
 * Write a description of class Corrida here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import business.SubCarro.Carro;
import business.SubCarro.Hibrido;
import business.SubCircuito.Circuito;
import business.SubCircuito.Piloto;
import business.SubCircuito.Record;
import business.SubCircuito.Tipo_Estrada;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Collections;
import java.util.Random;
import java.util.Iterator;
import java.io.Serializable;
import java.text.BreakIterator;

public class Corrida implements Serializable {

    


    //------------------------------------- VARIAVEIS DE INSTANCIA -------------------------------------//


   // private List<Carro> listaCarros;

   private Map<Piloto, Carro> listaPilotosCarros;   // achamos que faz mais sentido ser assim

   private int id_corrida;
   private Circuito circuito;
   private Set<Carro> resultados;
   //private Map<Carro,Long> bestLap;
   private List<Carro> primeiroVolta;
   private Map<Carro, Integer> dnf;
   private int clima; //1-chove 0-sol 



   //------------------------------------- CONSTRUTORES -------------------------------------//


   public Corrida() {

       //this.listaCarros = new ArrayList<Carro>();

       this.listaPilotosCarros = new HashMap<>();

       this.id_corrida = 0;
       this.circuito = new Circuito();
       this.resultados = new TreeSet<Carro>();
       //this.bestLap = new HashMap<Carro,Long>();
       this.primeiroVolta = new ArrayList<Carro>();
       this.dnf = new HashMap<Carro,Integer>();
       Random rand = new Random();
       int x = rand.nextInt(2);
       this.clima = x;
   }
   
   
   public Corrida(Map<Piloto, Carro> l, int id_corrida, Circuito c, Set<Carro> r, List<Carro> p, int clima) {
       this();

       for(Piloto pi : l.keySet()) {
           this.listaPilotosCarros.put(pi, l.get(pi));
       }
    
       this.id_corrida = id_corrida;

       this.circuito = c.clone();

       for(Carro car: r) {
           this.resultados.add(car.clone());
       }

       for(Carro x : p) {
           this.primeiroVolta.add(x.clone());
       }

       this.clima = clima;
   }
   
  
   public Corrida(Corrida c) {
       this.listaPilotosCarros = c.getPilotosCarros();
       this.id_corrida = c.getId_corrida();
       this.circuito = c.getCircuito();
       this.resultados = c.getResultados();
       this.primeiroVolta = c.getPrimeiroVolta();
       this.dnf = c.getDNF();
       this.clima = c.getClima();
   }


   //------------------------------------- GETS / SETS -------------------------------------//


   public Map<Piloto, Carro> getPilotosCarros() {
       Map<Piloto, Carro> aux = new HashMap<>();

       for(Piloto p: this.listaPilotosCarros.keySet()) {
           aux.put(p.clone(), listaPilotosCarros.get(p).clone());
       }

       return aux;
   }

   public int getId_corrida() {
       return this.id_corrida;
   }
   
   public Circuito getCircuito() {
       return this.circuito.clone();
   }
   
   
   public Set<Carro> getResultados() {
       TreeSet<Carro> aux = new TreeSet<Carro>();

       for(Carro c : this.resultados) {
           aux.add(c.clone());
       }

       return aux;
   }
   
   public Map<Carro,Integer> getDNF() {
       HashMap<Carro,Integer> aux = new HashMap<Carro,Integer>();

       for(Carro c : this.dnf.keySet()) {
           aux.put(c.clone(), this.dnf.get(c));
       }

       return aux;
   }
   
   public int getClima() {
       return this.clima;
   }
   
   
   public List<Carro> getPrimeiroVolta() {
       ArrayList<Carro> aux = new ArrayList<Carro>();

       for(Carro c : this.primeiroVolta) {
           aux.add(c.clone());
       }

       return aux;
   }
   
   
   public void setCircuito(Circuito c) {
       this.circuito = c.clone();
   }


    //------------------------------------- OUTROS METODOS -------------------------------------//


   public Corrida clone() {
       return new Corrida(this);
    }
   
   /**
     * Adicionar um carro e piloto ao map
     */
    public void adicionarPilotoCarro(Piloto p, Carro c) {

        this.listaPilotosCarros.put(p.clone(), c.clone());
    }
    
    /**
     * adicionar map de pilotos e carros
     */
    public void adicionarPilotoCarro(Map<Piloto,Carro> l) {
        for(Piloto pi : l.keySet()) {
            this.listaPilotosCarros.put(pi.clone(), l.get(pi).clone());
        }
    }
    
    /**
     * Numero total de carros
     */
    public int totalCarros() {
        return this.listaPilotosCarros.size();
    }
    
    /**
     * Remover um carro e piloto
     */
    public void removerCarro(Carro c) {
        for(Piloto pi : this.listaPilotosCarros.keySet()){
            if(listaPilotosCarros.get(pi).equals(c)){
                this.listaPilotosCarros.remove(pi);
                break;
            }
        }
    }
    
    /**
     * Limpa o map de pilotos e carros
     */
    public void limpaPilotosCarros() {
      this.listaPilotosCarros.clear();
    }
    
    /**
     * Simula a corrida
     */   
    public void simulaCorrida() {
        int voltas = this.circuito.getVoltas();
        double t_aux, t_volta;
        Map<Piloto, Carro> aux = new HashMap<>();
        HashMap<Carro,Integer> temp = new HashMap<Carro,Integer>();

        for(Piloto pi : this.listaPilotosCarros.keySet()) {
            aux.put(pi.clone(), this.listaPilotosCarros.get(pi).clone());
        }

        for(int i=0; i<voltas; i++) {

            for(Piloto pi : aux.keySet()) {

                Carro c = aux.get(pi);

                if(c.getDNF()==false) {//verifica se o carro esta acidentado

                    if(c.DNF(i,voltas,this.clima)==true) {//verifica se o carro tem acidente na volta

                        c.setDNF(true);
                        temp.put(c.clone(),i);
                    }

                    else {
                       t_aux = c.getTempo(); //tempo feito ate ao momento

                       if(c instanceof Hibrido) {
                           Hibrido h = (Hibrido)c;
                           int motor = h.getPotenciaMotorEletrico();
                           t_volta = c.tempoProximaVolta(this.circuito, 0, i, pi) - motor*10;
                       }

                       else {
                           t_volta = c.tempoProximaVolta(this.circuito, 0, i, pi);
                       }

                       c.setTempo(t_aux +t_volta); 
                       //atualizar record
                       if(this.circuito.getRecord().getTempo() > t_volta) {

                           if(i<(this.circuito.getVoltas()/2)) {
                               // nao faz nada de jeito
                               Piloto p = new Piloto();
                               Record r = new Record(t_volta, p, c.clone());
                               this.circuito.setRecord(r);
                           }
                           else {
                               // nao faz nada de jeito
                               Piloto p = new Piloto();
                               Record r = new Record(t_volta, p, c.clone());
                               this.circuito.setRecord(r);
                           }
                       }
                    }
                }
            }
            this.primeiroVolta(i,aux); //metodo auxiliar para determinar o 1º a cada volta
        }
        for(Piloto pi : aux.keySet()) {

            Carro c = aux.get(pi);
            if(c.getDNF()==false) {
                    this.resultados.add(c);
            }
        }
        this.dnf = temp;
    }
    
    /**
     * Metodo auxiliar privado para determinar o carro que vai em 1o a cada volta
     */
    private void primeiroVolta(int volta, Map<Piloto, Carro> l) {

        List<Carro> l2 = new ArrayList<>();

        for(Piloto pi : l.keySet()){
            l2.add(l.get(pi).clone());
        }

       Collections.sort(l2);
       Iterator<Carro> it = l2.iterator();
       boolean f = false;
       Carro c = null;

       while(it.hasNext() && f == false) {
           c = it.next();

           if(c.getDNF() == false) f=true;
       }

       if(c!=null) this.primeiroVolta.add(volta,c.clone());
    }
    
    /**
     * Lista o 1o classificado em cada volta
     */
    private String printPrimeiroVolta() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("\n||||| Primeiro carro a cada volta e desistentes |||||");

        for(int i=0; i<this.primeiroVolta.size();i++) {
            sb.append("\n");
            sb.append(i+1);sb.append("ª Volta: ");
            sb.append(this.primeiroVolta.get(i).getMarca());sb.append(" ");
            sb.append(this.primeiroVolta.get(i).getModelo());

            for(Carro c : this.dnf.keySet()) {

                if(this.dnf.get(c) == i) {
                    sb.append("\n\t");sb.append("Desistente: ");
                    sb.append(c.getMarca());sb.append(" ");
                    sb.append(c.getModelo());
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * Lista os resultados da corrida.
     */ 
   public String printResultados() {
       StringBuilder sb = new StringBuilder();
       int i = 1;
       sb.append("\n||||| ");sb.append(this.id_corrida);sb.append(" |||||");
       sb.append("\n||||| ");sb.append(this.circuito.getNome());sb.append(" |||||");
       sb.append("\n||||| ");sb.append("Voltas: ");sb.append(this.circuito.getVoltas());sb.append(" |||||");
       sb.append("\n||||| ");sb.append("Distancia: ");sb.append(this.circuito.getDistancia());sb.append("km | ");
       sb.append("Condição meteorológica: ");

       if(this.clima == 0) {
            sb.append("Sol");
       }

       else {
            sb.append("Chuva");;
       }

       sb.append(" |||||");
       sb.append("\n||||| ");sb.append("Record: ");sb.append(TimeConverter.toTimeFormat(this.circuito.getRecord().getTempo()));
       sb.append(" | Piloto: ");sb.append(this.circuito.getRecord().getPiloto().getNome());
       sb.append(" Carro: ");sb.append(this.circuito.getRecord().getCarro().getMarca());
       sb.append(" ");sb.append(this.circuito.getRecord().getCarro().getModelo());
       sb.append("\n\n||||| Classificacoes da corrida |||||");

       for(Carro c : this.resultados) {
            sb.append("\n");
            sb.append(i);sb.append("º: ");
            sb.append(TimeConverter.toTimeFormat(c.getTempo()));
            sb.append("\t Categoria: "); sb.append(c.getClass().getName()); sb.append(" ");
            sb.append("\t Carro: "); sb.append(c.getMarca()); sb.append(" ");
            sb.append(c.getModelo());
            i++;
       }

       for(int v=this.circuito.getVoltas();v>=0;v--) {

           for(Carro c : this.dnf.keySet()) {

                   if(v==this.dnf.get(c)) {
                   sb.append("\n");
                   sb.append(i);sb.append("º: ");
                   sb.append("DNF");
                   sb.append("\t Categoria: "); sb.append(c.getClass().getName()); sb.append(" ");
                   sb.append("\t Carro: "); sb.append(c.getMarca()); sb.append(" ");
                   sb.append(c.getModelo());
                   i++;
                }
           }
       }
       
       sb.append("\n\n||||| Classificacoes da corrida Hibridos |||||");
       i=1;

       for(Carro c : this.resultados) {
            if(c instanceof Hibrido) {
                sb.append("\n");
                sb.append(i);sb.append("º: ");
                sb.append(TimeConverter.toTimeFormat(c.getTempo()));
                sb.append("\t Categoria: "); sb.append(c.getClass().getName()); sb.append(" ");
                sb.append("\t Carro: "); sb.append(c.getMarca()); sb.append(" ");
                sb.append(c.getModelo());
                i++;
            }
       }      
       for(int v=this.circuito.getVoltas();v>=0;v--) {

           for(Carro c : this.dnf.keySet()) {

               if(c instanceof Hibrido) {

                   if(v==this.dnf.get(c)) {
                       sb.append("\n");
                       sb.append(i);sb.append("º: ");
                       sb.append("DNF");
                       sb.append("\t Categoria: "); sb.append(c.getClass().getName()); sb.append(" ");
                       sb.append("\t Carro: "); sb.append(c.getMarca()); sb.append(" ");
                       sb.append(c.getModelo());
                       i++;
                   }
               }
           }
       }
       sb.append(this.printPrimeiroVolta());
       return sb.toString();
   }
   
   /**
    * Lista de Acidentados DNF
    */
   public String printDNF() {
       StringBuilder sb = new StringBuilder();
       sb.append("Espetados!!!");

       for(Carro c : this.dnf.keySet()) {
           sb.append("\n" + c.getMarca() + " \t\tVolta: " + this.dnf.get(c));
       }

       return sb.toString();
   }
   
   /**
    * Lista de participantes da corrida
    */
   public String listaCarrosParticipantes() {
       StringBuilder sb = new StringBuilder();
       int i = 1;

       for(Piloto pi : this.listaPilotosCarros.keySet()) {
           Carro c = this.listaPilotosCarros.get(pi);
           sb.append("\n");
           sb.append(i);sb.append(" - ");sb.append(c.getMarca());sb.append(" ");sb.append(c.getModelo());
           sb.append("\t ");sb.append("\t ");sb.append(c.getClass().getName());
           i++;
       }

       return sb.toString();
   }
   public boolean consegueUlt(Carro car,Carro carF){
    double dist = car.getPosicao()-carF.getPosicao();
    if(dist<car.getDistUlt()){
        if(car.getPAC()<carF.getPAC()){
            return true;
        }
    }
    return false;
}
    public double ProbUlt(Piloto p,Tipo_Estrada te){
          double prob=1;
          String gdu=te.getGDU();
          if((p.getCts()>0.5 && this.clima==1) || (p.getCts()<0.5 && this.clima==0)|| (p.getSva()<0.5)) prob-=0.1;
          switch(gdu){
            case "Impossível":
            prob-=0.1;
            break;
            case "Difícil":
            prob-=0.05;
            break;
            case "Possível":
            prob+=0.05;
            break;
            default:
            break;
        }

          return prob;
    }


    public void checkUlt(Piloto p,Tipo_Estrada t,Carro c, Carro car){
        boolean ult=consegueUlt(c, c);
        double prob=ProbUlt(p, t);
        if(ult==true && prob>=0.5){//consegue ultrapassar
              trocaPosicoes(c, car);
        }
        else{//não consegue ultrapassar verifica se houve despiste
            double prob_2 = probAvariar(c);
            Random rand = new Random();
            double num = rand.nextDouble();
            if(num <= prob_2){ //se avariou
                c.setDNF(true);
                dnf.put(c,c.get_ID_Carro());
            }
            else{
                c.setTempo( this.getCircuito().getPenalizacao() + c.getTempo() );
                
            }
            
        }
    }

    public double probAvariar(Carro c){
        double prob=0;
        String modo_motor=c.get_modo_motor();
        switch (modo_motor){
            case "Conservador":
                prob+=0.22;
                break;
            case "Normal":
                prob+=0.33;
                break;
            case "Agressivo":
                prob+=0.45;
                break;
            default:
                break;
        }

        return prob;
    }

/* 
   public double checkAvaria(Carro c){
       double prob=probAvariar(c);
       Random rand = new Random();
       double num = rand.nextDouble();
       if(num <= prob){
           //avariou - falta implementar - verificar se falta coisas
           c.setDNF(true);
           dnf.put(c,id_Carro);
       }

       return num;
   }
*/

    //Troca as posições de 2 Carros
    public void trocaPosicoes(Carro c,Carro cf){
        int posCF=cf.getPosicao();
        System.out.println(" Carro que  ultrapassou: " +c.get_ID_Carro() + " estava na posição  " +c.getPosicao());
        System.out.println(" Carro que foi ultrapassado: " +cf.get_ID_Carro() +" estava na posicao " +cf.getPosicao() );
        cf.setPosicao(posCF);
        c.setPosicao(posCF);
        System.out.println(" Carro que  ultrapassou: " +c.get_ID_Carro() + " encontra-se na posição  " +c.getPosicao());
        System.out.println(" Carro que foi ultrapassado: " +cf.get_ID_Carro() +" encontra-se na posicao " +cf.getPosicao() );
    }
}
