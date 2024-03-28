package exceptions;

public class CircuitoNaoExisteException extends Exception{
    public CircuitoNaoExisteException(){
        super();
    }
    
    public CircuitoNaoExisteException(String msg){
        super(msg);
    }
}