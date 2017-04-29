
package fasttextcontent;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;


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
    private ArrayList<String> listaPalabrasBusqueda;
    
    
    public void ejecutarBusqueda(FTC_GUI gui){
        
        // Se reestablecen los valores de los atributos de la clase
        
        limpiarAlmacenamientoLocal();
        
        // El codigo siguiente convierte la entrada del usuario a una lista de las palabras ingresadas
        
        String stringBusqueda = gui.getStringBusqueda();

        if (!stringBusqueda.equalsIgnoreCase("")){ 
            listaPalabrasBusqueda = new ArrayList<>(Arrays.asList(stringBusqueda.split(" ")));
        }
        else {
            listaPalabrasBusqueda = new ArrayList<>();
        }
        
        // Termina la conversion
        
        // Se ejecuta el proceso de obtener el html        
        listaPalabrasBusqueda.forEach( (palabra) -> iniciarProcesoBusqueda(palabra) );
        
    }

    private void iniciarProcesoBusqueda(String palabra) {
        
        CacheSingleton cache = CacheSingleton.getInstance();
        
        if (cache.contiene(palabra)){
            
            update(null, new ContentGetter( palabra + "2", cache.obtenerValor(palabra) ));
            
        } else {
            ContentGetter getterThread = new ContentGetter(palabra, this);
            getterThread.addObserver(cache);
            Thread hiloBusqueda = new Thread(getterThread);
            hiloBusqueda.start();
        }
    }

    private void limpiarAlmacenamientoLocal() {
        
        this.listaPalabrasBusqueda = new ArrayList<>();
        this.resultadosBusqueda = new ArrayList<>();
        
    }
    
    private void desplegarResultados(){
        
        // Configuraciones de la ventana de resultados
        JFrame frameResultados = new JFrame();
        frameResultados.setLayout(new BorderLayout());
        frameResultados.setSize(700, 700);    
        frameResultados.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameResultados.setLocationRelativeTo(null);
        
        JPanel contenedorExterno = new JPanel();
        contenedorExterno.setLayout(new BoxLayout(contenedorExterno, BoxLayout.Y_AXIS));
        
        JScrollPane scroll = new JScrollPane(contenedorExterno);
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
            
            // Agregar todos los componentes a la vista
            contenedorExterno.add(panelPalabra);
            contenedorExterno.add(panelTiempo);
            contenedorExterno.add(panelHTML);           
        }
        
        frameResultados.setVisible(true);
    }

}
