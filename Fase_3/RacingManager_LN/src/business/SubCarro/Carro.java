package business.SubCarro;
/**
 * Write a description of class Carro here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import business.SubCircuito.Circuito;
import business.SubCircuito.Piloto;

import java.util.Map;
import java.io.Serializable;

public abstract class Carro implements Comparable<Carro>,Serializable {


    //------------------------------------- VARIAVEIS DE INSTANCIA -------------------------------------//


    private int id_Carro; // facilita a gestão da base de dados
    private String marca;
    private String modelo;
    private int cilindrada;
    private int potencia;
    private String modo_motor; // conservador,normal,agressivo
    private int fiabilidade;
    private double tempo;
    private boolean dnf;
    private int pneu; //0- chuva 1-macio 2- duro  // falta implementar adicionado ????????????????????
    private double pac;   
    private int posicao;                        

    //------------------------------------- CONSTRUTORES -------------------------------------//


    public Carro() {
        this.id_Carro = 0;
        this.marca = "";
        this.modelo = "";
        this.cilindrada = 0;
        this.potencia = 0;
        this.modo_motor = ""; //para avarias
        this.fiabilidade = 0;
        this.tempo = 0;
        this.dnf = false;
        this.pac = 0;
        this.posicao=0;
    }
    
    public Carro(int id, String marca, String modelo, int cilindrada, int potencia, String modo_motor, int fiabilidade, double pac,int posicao) {
        this.id_Carro = id;
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.potencia = potencia;
        this.modo_motor = modo_motor; //para avarias
        this.fiabilidade = fiabilidade;
        this.tempo = 0;
        this.dnf = false;
        this.pac = pac;
        this.posicao=posicao;
    }
    
    public Carro(Carro c) {
       this.id_Carro = c.get_ID_Carro();
       this.marca = c.getMarca();
       this.modelo = c.getModelo();
       this.cilindrada = c.getCilindrada();
       this.potencia = c.getPotencia();
       this.modo_motor = c.get_modo_motor(); //para avarias
       this.fiabilidade = c.getFiabilidade();
       this.tempo = c.getTempo();
       this.dnf = c.getDNF();
       this.pac = c.getPAC();
       this.posicao=c.getPosicao();
    }


    //------------------------------------- GETS / SETS -------------------------------------//


    public int get_ID_Carro()
    {
        return this.id_Carro;
    }

    public double getTempo()
    {
        return this.tempo;
    }
    
    public String getMarca()
    {
        return this.marca;
    }
    
    public String getModelo()
    {
        return this.modelo;
    }
    
    public int getCilindrada()
    {
        return this.cilindrada;
    }
    
    public int getPotencia()
    {
        return this.potencia;
    }

    public String get_modo_motor(){ return this.modo_motor; } //para avarias
    
    public int getFiabilidade()
    {
        return this.fiabilidade;
    }
    
    public boolean getDNF()
    {
        return this.dnf;
    }

    public double getPAC(){ return this.pac;}

    public void set_ID_Carro(int id)
    {
        this.id_Carro = id;
    }
    public int getPosicao(){
        return this.posicao;
    }
    public void setPosicao(int posicao){
        this.posicao=posicao;
    }
    public void setMarca(String marca)
    {
        this.marca = marca;
    }
    
    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }
    
    public void setCilindrada(int cilindrada)
    {
        this.cilindrada = cilindrada;
    }
    
    public void setPotencia(int potencia)
    {
        this.potencia = potencia;
    }

    public void set_modo_motor(String modo_motor) { this.modo_motor = modo_motor; } //para avarias
    
    public void setTempo(double t)
    {
        this.tempo = t;
    }
    
    public void setDNF(boolean b)
    {
        this.dnf = b;
    }

    public void setPAC(double b)
    {
        this.pac = b;
    }


    //------------------------------------- METODOS USUAIS -------------------------------------//


    public abstract Carro clone();
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nID: ");sb.append(this.id_Carro);
        sb.append("\nMarca: ");sb.append(this.marca);
        sb.append("\nModelo: ");sb.append(this.modelo);
        sb.append("\nCilindrada: ");sb.append(this.cilindrada);
        sb.append("\nPotencia: ");sb.append(this.potencia);
        sb.append("\nModo de funcionamento do motor: ");sb.append(this.modo_motor); //para avarias
        sb.append("\nFiabiliade: ");sb.append(this.fiabilidade);
        sb.append("\nTempo: ");sb.append(this.tempo);
        sb.append("\nDNF: ");sb.append(this.dnf);
        sb.append("\nPAC: ");sb.append(this.pac);
        return sb.toString();
    }
    
    public boolean equals(Object o) {
        if(this==o) {
            return true;
        }
        
        if(o==null || this.getClass()!=o.getClass()) {
            return false;
        }
        
        Carro c = (Carro) o;
        return( this.id_Carro == c.get_ID_Carro() &&
                this.marca.equals(c.getMarca()) &&
                this.modelo.equals(c.getModelo()) &&
                this.cilindrada == c.getCilindrada() &&
                this.potencia == c.getPotencia() &&
                this.modo_motor.equals(c.get_modo_motor()) &&
                this.fiabilidade == c.getFiabilidade() &&
                this.tempo == c.getTempo() &&
                this.dnf == c.getDNF() &&
                this.pac == c.getPAC()); //para avarias
    }
    
    public int compareTo(Carro c) {
        if(this.tempo < c.getTempo()) {
            return -1;
        }
        if(this.tempo > c.getTempo()) {
            return 1;
        }
        else {
            return 0;
        }
    }


    //------------------------------------- OUTROS METODOS -------------------------------------//


    /**
     * Tempo em milisegundos de uma volta
     */
    public double tempoProximaVolta(Circuito c, int clima, int volta, Piloto p1) {

        //Piloto p1 = this.getEquipa().getPiloto1();

        Map<String,Double> aux = c.getTemposMedios();
        double t_medio = aux.get(this.getClass().getName());
        double t_chuva = c.getPenalizacao();
        long minimum = 0;
        long maximum = 5000;
        long fator_sorte = minimum + Double.valueOf(Math.random()*(maximum-minimum)).intValue();
        long maximum_chuva = 2000;
        long fator_sorte_chuva= minimum + Double.valueOf(Math.random()*(maximum_chuva-minimum)).intValue();

        /*
        if(volta<(c.getVoltas()/2)) {
            // usa piloto 1
            return (t_medio + ((this.getCilindrada()/this.getPotencia())-p1.getSva())*1000) - fator_sorte
                    + (clima*(t_chuva - p1.getCts()*1000)) - fator_sorte_chuva;
        }
        else {
            //usa piloto 2
            if(volta == (c.getVoltas()/2)) {
                return (t_medio + ((this.getCilindrada()/this.getPotencia())-p2.getQualidade())*1000) - fator_sorte 
                    + (clima*(t_chuva - p2.getQualidadeChuva()*1000)) - fator_sorte_chuva + c.getTempoBox();
            }

            else {
                return (t_medio + ((this.getCilindrada() / this.getPotencia()) - p2.getQualidade()) * 1000) - fator_sorte
                        + (clima * (t_chuva - p2.getQualidadeChuva() * 1000)) - fator_sorte_chuva;
            }
        }
        */

        return (t_medio + ((this.getCilindrada() / this.getPotencia()) - p1.getSva())*1000) - fator_sorte + (clima*(t_chuva - p1.getCts()*1000)) - fator_sorte_chuva;
    }
    
    /**
     * define se o carro desiste (true desiste, false continua em prova)
     */
    public abstract boolean DNF(int volta,int totalvoltas,int clima);
    
   public double getDistUlt(){
       return this.getTempo()*this.getPotencia();
    }
   
}
