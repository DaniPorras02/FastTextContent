
package fasttextcontent;

import java.util.Observable;
import java.util.Observer;

public class ContentGetter extends Observable implements Runnable {
    
    private String resultadoHTML;
    private String palabra;
    private String tiempoBusqueda = "tiempo random";
    
    public ContentGetter(String palabraBuscada, Observer observer){
        this.resultadoHTML = "<html> Sin contenido. </html>";
        this.palabra = palabraBuscada;
        addObserver(observer);
    }
    
    public ContentGetter(String palabra, String html){
        this.palabra = palabra; this.resultadoHTML = html;
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
            resultadoHTML = managerHTML.getSearchResult(palabra);
            
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
