package exceptions;

public class CarroJaExisteException extends Exception {
    public CarroJaExisteException(){
        super();
    }
    
    public CarroJaExisteException(String msg){
        super(msg);
    }
}