package exceptions;

public class JogadorJaExisteException extends Exception {
    public JogadorJaExisteException(){
        super();
    }
    
    public JogadorJaExisteException(String msg){
        super(msg);
    }
}
