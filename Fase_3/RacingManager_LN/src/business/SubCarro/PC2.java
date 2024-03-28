package business.SubCarro;
/**
 * Write a description of class PC2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Random;

public class PC2 extends Carro {

    private int preparacao_mecanica;
    
    public PC2() {
        super();
    }
    
    public PC2(int id, String marca, String modelo, int cilindrada, int potencia,int p_mecanica,String modo_motor, double pac, int posicao) {
        super(id, marca,modelo,cilindrada,potencia,modo_motor,80, pac,posicao);
        this.preparacao_mecanica = p_mecanica;
    }
    
    public PC2(PC2 p) {
        super(p);
        this.preparacao_mecanica = p.getPreparacaoMecaninca();
    }
    
    public int getPreparacaoMecaninca() {
        return this.preparacao_mecanica;
    }
    
    public PC2 clone() {
        return new PC2(this);
    }
    
    public boolean DNF(int volta,int totalvoltas,int clima) {
       Random rand=new Random();
       int x=rand.nextInt(85);
       //no maximo fiabilidade de 85%
       int fiabilidade = super.getFiabilidade() + (super.getCilindrada()/1200) + (this.preparacao_mecanica/10);
       return (x > fiabilidade);
    }
    
    public boolean equals(Object o) {
        if(this==o) {
            return true;
        }
        
        if(o==null || this.getClass()!=o.getClass()) {
            return false;
        }
        
        PC2 c = (PC2) o;
        return ( super.equals(c) && this.preparacao_mecanica == c.getPreparacaoMecaninca());
    }
}
