package business.SubCarro;
/**
 * Write a description of class SC here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import business.SubCircuito.Piloto;

import java.util.Random;

public class SC extends Carro {

    public SC() {
        super();
    }
    
    public SC(int id, String marca, String modelo, int cilindrada, int potencia, String modo_motor,double pac, int posicao) {
        super(id, marca,modelo,cilindrada,potencia,modo_motor,75, pac,posicao);
    }
    
    public SC(SC p) {
        super(p);
    }
    
    public SC clone() {
        return new SC(this);
    }

    // !!! não é suposto ter Equipa !!!
    public boolean DNF(int volta,int totalvoltas, int chuva) {
       Random rand=new Random();
       int x=rand.nextInt(73);
       Piloto p = null;

        /*
       if(volta<totalvoltas/2) {
           p = super.getEquipa().getPiloto1();
       }
       else {
           p = super.getEquipa().getPiloto2();
       }
        */

       double qualidade;
       if(chuva == 1)
            qualidade = p.getCts();
       else
            qualidade = p.getSva();
       //no maximo fiabilidade de 70%
       int fiabilidade = (int)(qualidade*0.75) + (int)((super.getCilindrada()/10)*0.25);
       //System.out.println("Fiabilidade: "+fiabilidade);
       //System.out.println("Random: "+x);
       return (x > fiabilidade);
    }    
     
    public boolean equals(Object o) {
        if(this==o) {
            return true;
        }
        
        if(o==null || this.getClass()!=o.getClass()) {
            return false;
        }
        
        SC c = (SC) o;
        return ( super.equals(c));
    }
}
