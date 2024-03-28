package business.SubUtilizador;

import java.util.Map;

import business.I_SubUtilizador;
import data.AdministradorDAO;
import data.JogadorDAO;
import exceptions.JogadorJaExisteException;
import exceptions.JogadorNaoExisteException;

public class SubUtilizadorFacade implements I_SubUtilizador {
    
    private final JogadorDAO jogadorDAO;
    private final AdministradorDAO administradorDAO;

    public SubUtilizadorFacade(){
        this.jogadorDAO=JogadorDAO.getInstance();
        this.administradorDAO=AdministradorDAO.getInstance();
    }

    public boolean JogExiste(String username){
        return this.jogadorDAO.containsKey(username);
    }

    public boolean AdminExiste(String username){
    return this.administradorDAO.containsKey(username);
    }

    public boolean JogIsAutenticado(String username){
        Jogador j = this.jogadorDAO.get(username);
        if(j==null) return false ;
        return j.jogAutenticado();
    }

    public Jogador getJ(String username) throws JogadorNaoExisteException{
        Jogador j = this.jogadorDAO.get(username);
        if(j==null) throw new  JogadorNaoExisteException("Jogador Não Existe");
        return j;
    }

    public Administrador getAd(String username) throws JogadorNaoExisteException{
         Administrador adm = this.administradorDAO.get(username);
         if(adm==null) throw new  JogadorNaoExisteException("Administrador Não Existe");
         return adm;
     }

    public void registarJog(String username,String password) throws JogadorJaExisteException{
        if(this.jogadorDAO.containsKey(username)) throw new JogadorJaExisteException("Jogador já se encontra registado");
        else this.jogadorDAO.put(username,  new Jogador(username, password,0,false,0));
    }

    public void registarAd(String username,String password) throws JogadorJaExisteException{
        if(this.administradorDAO.containsKey(username)) throw new JogadorJaExisteException("Jogador já se encontra registado");
        else this.administradorDAO.put(username,  new Administrador(username, password,false));
    }

    public Jogador loginJ(String username,String password) throws JogadorNaoExisteException{
        Jogador j=this.jogadorDAO.get(username);
        if(j==null) throw new JogadorNaoExisteException("Não existe nenhum jogador com este username");
        else {
            if(j.jogAutenticado()==false){
                j = this.jogadorDAO.get(username);
                j.setAutenticado();
            }
            return j;
        }
    }

    public Administrador loginAd(String username,String password) throws JogadorNaoExisteException{
        Administrador ad=this.administradorDAO.get(username);
        if(ad==null) throw new JogadorNaoExisteException("Não existe nenhum administrador com este username");
        else {
            if(ad.AdAut()==false){
                ad = this.administradorDAO.get(username);
                ad.setAutenticado();
            }
        return ad;
        }
    }

}


