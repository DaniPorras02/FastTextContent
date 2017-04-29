package fasttextcontent;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


public class CacheSingleton implements Observer {
    
    private static Map<String, String> datos = new HashMap<>();
    
    private static final CacheSingleton instance = new CacheSingleton();
    
    // constructor privado para evitar instanciacion por defecto
    private CacheSingleton(){}

    public static CacheSingleton getInstance(){
        return instance;
    }
    
    public void guardar(String llave, String valor){ datos.put(llave, valor); }
    
    public String obtenerValor(String llave) { return datos.get(llave); }
    
    public boolean contiene(String llave) { return datos.containsKey(llave); }

    @Override
    public void update(Observable o, Object arg) {
        
        ContentGetter resultado = (ContentGetter) arg;
        if (! this.contiene(resultado.getPalabra()) ){
            guardar(resultado.getPalabra(), resultado.getResultadoHTML());
        }
    
    }
    
    public String prueba(String key){
        if (contiene(key)) { return "true"; }
        else { return "false"; }
    }
}
