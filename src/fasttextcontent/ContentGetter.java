
package fasttextcontent;

import java.util.Observable;
import java.util.Observer;

public class ContentGetter extends Observable implements Runnable {
    
    private String resultadoHTML;
    private String palabra;
    private String tiempoBusqueda;
    
    public ContentGetter(String palabraBuscada, Observer observer){
        this.resultadoHTML = "<html> Sin contenido. </html>";
        this.palabra = palabraBuscada;
        addObserver(observer);
    }
    
    public ContentGetter(String palabra, String html, String tiempo){
        this.palabra = palabra; this.resultadoHTML = html; this.tiempoBusqueda = tiempo;
    }
    
    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg); 
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o); 
    }
    
    @Override
    public void run() {
         
        HTTPManager managerHTML = new HTTPManager();
        
        try {
            long startTime = System.nanoTime();
            resultadoHTML = managerHTML.getSearchResult(palabra);
            long stopTime = System.nanoTime();
            
            tiempoBusqueda = String.valueOf((stopTime - startTime)/1000000) + " milisegundos.";
            
        } catch (Exception ex) {            
            resultadoHTML = "<html> Ocurrió un error en la carga del contenido. </html>";
        }
        setChanged();
        notifyObservers(this);
    }

    public String getResultadoHTML() {
        return resultadoHTML;
    }

    public String getPalabra() {
        return palabra;
    }

    public String getTiempoBusqueda() {
        return tiempoBusqueda;
    }
    
    
}
