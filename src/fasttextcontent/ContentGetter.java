
package fasttextcontent;

import java.util.Observable;
import java.util.Observer;

public class ContentGetter extends Observable implements Runnable {
    
    private String resultado;
    private String palabra;

    public ContentGetter(String palabraBuscada, Observer observer){
        this.resultado = "<html> Sin contenido. </html>";
        this.palabra = palabraBuscada;
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
         
        HTTPManager managerHTML = new HTTPManager();
        
        try {
            resultado = managerHTML.getSearchResult(palabra);
            
        } catch (Exception ex) {            
            resultado = "<html> Ocurrió un error en la carga del contenido. </html>";
        }
        setChanged();
        notifyObservers(resultado);
    }
    
}
