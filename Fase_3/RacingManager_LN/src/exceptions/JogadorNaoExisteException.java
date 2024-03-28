package exceptions;

public class JogadorNaoExisteException extends Exception {
    public JogadorNaoExisteException(){
        super();
    }
    public JogadorNaoExisteException(String msg){
        super(msg);
    }
}
