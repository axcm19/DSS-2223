package exceptions;

public class CarroNaoExisteException extends Exception{
    public CarroNaoExisteException(){
        super();
    }
    
    public CarroNaoExisteException(String msg){
        super(msg);
    }
}
