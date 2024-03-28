import business.SubUtilizador.Administrador;
import business.SubUtilizador.Jogador;
import business.SubUtilizador.SubUtilizadorFacade;
import exceptions.JogadorJaExisteException;
import exceptions.JogadorNaoExisteException;
import ui.Menu;
import ui.TextUI;

import java.util.Scanner;

public class Main {

    static boolean iniciarSessaoJogador() throws JogadorNaoExisteException {
        boolean res = false;
        Jogador j = new Jogador();
        SubUtilizadorFacade user = new SubUtilizadorFacade();
        Scanner input = new Scanner(System.in);

        System.out.println("---> Indique o seu Username");
        String username = input.next();


        if(!user.JogExiste(username)){
            System.out.println("Erro, conta de jogador não existe");
            return false;
        }

        System.out.println("---> Indique a sua Password");
        String password = input.next();

        j = user.loginJ(username,password);
        if(j != null) res = true;

        return res;
    }

    static boolean iniciarSessaoAdministrador() throws JogadorNaoExisteException {
        boolean res = false;
        Administrador a = new Administrador();
        SubUtilizadorFacade user = new SubUtilizadorFacade();
        Scanner input = new Scanner(System.in);

        System.out.println("---> Indique o seu Username");
        String username = input.next();


        if(!user.AdminExiste(username)){
            System.out.println("Erro, conta de administrador não existe");
            return false;
        }

        System.out.println("---> Indique a sua Password");
        String password = input.next();

        a = user.loginAd(username,password);
        if(a != null) res = true;

        return res;
    }

    static void criarJogador() throws JogadorJaExisteException {
        SubUtilizadorFacade user = new SubUtilizadorFacade();
        Scanner input = new Scanner(System.in);

        System.out.println("---> Insira o novo Username");
        String username = input.next();


        if(user.JogExiste(username)){
            System.out.println("Erro, esse nome já está a ser utilizado");
        }

        System.out.println("---> Insira a nova Password");
        String password = input.next();

        user.registarJog(username,password);

    }

    static void criarAdministrador() throws JogadorJaExisteException {
        SubUtilizadorFacade user = new SubUtilizadorFacade();
        Scanner input = new Scanner(System.in);

        System.out.println("---> Insira o novo Username");
        String username = input.next();


        if(user.JogExiste(username)){
            System.out.println("Erro, esse nome já está a ser utilizado");
        }

        System.out.println("---> Insira a nova Password");
        String password = input.next();

        user.registarAd(username,password);
    }

    public static void main(String[] args) throws JogadorNaoExisteException, JogadorJaExisteException {

        boolean is_jogador = false;
        boolean is_administrador = false;

        Scanner input = new Scanner(System.in);

        System.out.println("*** Racing Manager v.1 ***\n" +
                "1) Iniciar sessão como jogador \n" +
                "2) Iniciar sessão como administrador \n" +
                "3) Criar conta de jogador \n" +
                "4) Criar conta de administrador\n" +
                "\n" +
                "Opção: ");

        String opcao = input.next();

        if(opcao.equals("1")){is_jogador = iniciarSessaoJogador();}
        else if(opcao.equals("2")){is_administrador = iniciarSessaoAdministrador();}
        else if(opcao.equals("3")){criarJogador();}
        else if(opcao.equals("4")){criarAdministrador();}

        try {

            if (is_administrador && !is_jogador) {
                new TextUI().run_admin();
            }
            else if (is_jogador && !is_administrador) {
                new TextUI().run_joga();
            }

        }
        catch (Exception e) {
            System.out.println("Não foi possível arrancar: "+e.getMessage());
        }
    }

}
