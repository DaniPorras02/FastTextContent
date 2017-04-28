
package fasttextcontent;

import java.util.Observable;
import java.util.Observer;


public class ContentGetter extends Observable implements Runnable {
    
    // Atributos
    private String resultado;
    private String palabra;
       
    public ContentGetter(String palabraBuscada, Observer observer){
        palabra = palabraBuscada;
        addObserver(observer);
    }
    
    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg); 
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o); 
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o); 
    }
    
    @Override
    public void run() {
         
    }
    
}
