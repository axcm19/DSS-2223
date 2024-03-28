package business.SubCircuito;
/**
 * Write a description of class Record here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import business.SubCarro.Carro;
import business.SubCarro.PC1;
import business.SubCorrida.TimeConverter;

import java.io.Serializable;

public class Record implements Serializable {


    //------------------------------------- VARIAVEIS DE INSTANCIA -------------------------------------//


   private double tempo;
   private Piloto piloto;
   private Carro carro;


   //------------------------------------- CONSTRUTORES -------------------------------------//


   public Record() {
       this.tempo = 0;
       this.piloto = new Piloto();
       this.carro = new PC1();
   }
   
   public Record(double t, Piloto p, Carro c) {
       this.tempo = t;
       this.piloto = p.clone();
       this.carro = c.clone();
   }
   
   public Record(Record c) {
       this.tempo = c.getTempo();
       this.piloto = c.getPiloto();
       this.carro = c.getCarro();
   }


   //------------------------------------- GETS / SETS -------------------------------------//


   public double getTempo()
   {
       return this.tempo;
   }
   
   public Piloto getPiloto()
   {
       return this.piloto.clone();
   }
   
   public Carro getCarro()
   {
       return this.carro.clone();
   }
   
   public void setTempo(double t)
   {
       this.tempo = t;
   }
   
   public void setPiloto(Piloto p)
   {
       this.piloto = p.clone();
   }
   
   public void setCarro(Carro c)
   {
       this.carro = c.clone();
   }


   //------------------------------------- METODOS USUAIS -------------------------------------//


   public Record clone()
   {
       return new Record(this);
   }
   
   public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append("\nTempo Record: ");sb.append(TimeConverter.toTimeFormat(this.tempo));
       sb.append(this.piloto.toString());
       sb.append(this.carro.toString());
       return sb.toString();
   }
   
   public boolean equals(Object o) {
       if(this == o) {
           return true;
       }
       
       if(o == null || this.getClass() != o.getClass()) {
           return false;
       }
       
       Record r = (Record) o;
       return ( this.tempo == r.getTempo() &&
                this.piloto.equals(r.getPiloto()) &&
                this.carro.equals(r.getCarro()));
   }
}
