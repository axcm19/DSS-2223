package business;

import business.SubUtilizador.Administrador;
import business.SubUtilizador.Jogador;
import exceptions.JogadorJaExisteException;
import exceptions.JogadorNaoExisteException;

public interface I_SubUtilizador {
    public boolean JogExiste(String username);
    public boolean AdminExiste(String username);
    public boolean JogIsAutenticado(String username);
    public Jogador getJ(String username) throws JogadorNaoExisteException;
    public Administrador getAd(String username) throws JogadorNaoExisteException;
    public void registarJog(String username,String password) throws JogadorJaExisteException;
    public void registarAd(String username,String password) throws JogadorJaExisteException;
    public Jogador loginJ(String username,String password) throws JogadorNaoExisteException;
    public Administrador loginAd(String username,String password) throws JogadorNaoExisteException;
}
