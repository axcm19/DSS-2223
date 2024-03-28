package exceptions;

public class CircuitoJaExisteException extends Exception {
    public CircuitoJaExisteException(){
        super();
    }
    
    public CircuitoJaExisteException(String msg){
        super(msg);
    }
}