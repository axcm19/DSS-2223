package business.SubCarro;
/**
 * Write a description of class PC1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Random;

public class PC1 extends Carro {

    public PC1() {
        super();
    }
    
    public PC1(int id, String marca, String modelo, int cilindrada, int potencia, String modo_motor, double pac, int posicao) {
        super(id, marca,modelo,cilindrada,potencia, modo_motor,95, pac, posicao);
    }
    
    public PC1(PC1 p) {
        super(p);
    }
    
    public PC1 clone() {
        return new PC1(this);
    }
    
    public boolean DNF(int volta,int totalvoltas,int clima) {
       Random rand=new Random();
       int x=rand.nextInt(87);
       return (x > super.getFiabilidade());
       //return false;
    }
    
    public boolean equals(Object o) {
        if(this==o) {
            return true;
        }
        
        if(o==null || this.getClass()!=o.getClass()) {
            return false;
        }
        
        PC1 c = (PC1) o;
        return ( super.equals(c));
    }
}
