
package fasttextcontent;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainUI implements Observer {

    @Override
    public void update(Observable o, Object arg) {        
        
        ContentGetter resultado = (ContentGetter) arg;
        resultadosBusqueda.add(resultado);
        
        CacheSingleton cache = CacheSingleton.getInstance();
        cache.toString();      
        
        if(listaPalabrasBusqueda.size() == resultadosBusqueda.size()){
            desplegarResultados();
        }
    }
    
    
    private ArrayList<ContentGetter> resultadosBusqueda;
    private Set<String> listaPalabrasBusqueda;
    
    
    public void ejecutarBusqueda(FTC_GUI gui){
        
        // Se reestablecen los valores de los atributos de la clase
        
        limpiarAlmacenamientoLocal();
        
        // El codigo siguiente convierte la entrada del usuario a una lista de las palabras ingresadas
        
        String stringBusqueda = gui.getStringBusqueda().toLowerCase();

        if (!stringBusqueda.equalsIgnoreCase("")){ 

            listaPalabrasBusqueda = new HashSet<>(       Arrays.asList(stringBusqueda.split(" "))        );
        }
        else {
            listaPalabrasBusqueda = new HashSet<>();
        }
        
        // Termina la conversion
        
        // Se ejecuta el proceso de obtener el html        
        listaPalabrasBusqueda.forEach( (palabra) -> iniciarProcesoBusqueda(palabra) );
        
    }

    private void iniciarProcesoBusqueda(String palabra) {
        
        CacheSingleton cache = CacheSingleton.getInstance();
        
        if (cache.contiene(palabra)){
            
            double startTime = System.nanoTime();
            String resultadoCache = cache.obtenerValor(palabra);                    
            double stopTime = System.nanoTime();
            
            String tiempo = String.valueOf((stopTime - startTime)/1000000) + " milisegundos (desde caché).";
            update(null, new ContentGetter( palabra, resultadoCache, tiempo));
            
        } else {
            ContentGetter getterThread = new ContentGetter(palabra, this);
            getterThread.addObserver(cache);
            Thread hiloBusqueda = new Thread(getterThread);
            hiloBusqueda.start();
        }
    }

    private void limpiarAlmacenamientoLocal() {
        
        this.listaPalabrasBusqueda = new HashSet<>();
        this.resultadosBusqueda = new ArrayList<>();
        
    }
    
    private void desplegarResultados(){
        
        // Configuraciones de la ventana de resultados
        JFrame frameResultados = new JFrame();
        frameResultados.setLayout(new BorderLayout());
        frameResultados.setSize(700, 700);    
        frameResultados.setLocationRelativeTo(null);
        frameResultados.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        
        JPanel contenedorInterno = new JPanel();
        contenedorInterno.setLayout(new BoxLayout(contenedorInterno, BoxLayout.Y_AXIS));
        
        JPanel contenedorExterno = new JPanel();
        contenedorExterno.setLayout(new BorderLayout());
        contenedorExterno.add(contenedorInterno, BorderLayout.PAGE_START);
        
        JScrollPane scroll = new JScrollPane(contenedorInterno);
        frameResultados.add(scroll, BorderLayout.CENTER);
        
        // Ciclo de insercion de resultados
        for (ContentGetter resultado : resultadosBusqueda){
            // Agregar el label de la palabra
            JPanel panelPalabra = new JPanel(new BorderLayout());
            panelPalabra.add(new JLabel("Palabra buscada: " + resultado.getPalabra()), BorderLayout.NORTH);
            // Agregar el label con el tiempo
            JPanel panelTiempo = new JPanel(new BorderLayout());
            panelTiempo.add(new JLabel("Tiempo de obtención: " + resultado.getTiempoBusqueda()), BorderLayout.NORTH);

            // Cargar el resultado html
            JPanel panelHTML = new JPanel(new BorderLayout());
            Browser browser = new Browser();
            BrowserView htmlViewer = new BrowserView(browser);
            panelHTML.add(htmlViewer, BorderLayout.CENTER);  
            browser.loadHTML(resultado.getResultadoHTML());            
            panelHTML.setPreferredSize(new Dimension(300,300));
            
            // Agregar todos los componentes a la vista
            contenedorInterno.add(panelPalabra);
            contenedorInterno.add(panelTiempo);
            contenedorInterno.add(panelHTML);
        }
        frameResultados.pack();
        frameResultados.setVisible(true);

    }
    

}
