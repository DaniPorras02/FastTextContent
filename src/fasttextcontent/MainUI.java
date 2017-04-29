
package fasttextcontent;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;


public class MainUI implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        
        System.out.println("He sido notificado!: " + arg.getClass().getName());
    
    }
    
    private ArrayList<String> listaPalabrasBusqueda;
    // private ArrayList<Thread> hilosBusqueda;
            
    public void ejecutarBusqueda(FTC_GUI gui){
        
        String stringBusqueda = gui.getStringBusqueda();
        
        if (!stringBusqueda.equalsIgnoreCase("")){ 
            listaPalabrasBusqueda = new ArrayList<>(Arrays.asList(stringBusqueda.split(" ")));
        }
        else {
            listaPalabrasBusqueda = new ArrayList<>();
        }
        
        listaPalabrasBusqueda.forEach( (palabra) -> iniciarBusqueda(palabra) );
        
        

        /*
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JScrollPane sp = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        container.add(sp);
        
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(view, BorderLayout.CENTER);
        container.add(panel1);
        browser.loadHTML(htm);
        
        Browser browser2 = new Browser();
        BrowserView view2 = new BrowserView(browser2);
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(view2, BorderLayout.CENTER);
        container.add(panel2);
        browser2.loadURL("https://google.com");
        
        Browser browser3 = new Browser();
        BrowserView view3 = new BrowserView(browser3);
        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.add(view3, BorderLayout.CENTER);
        container.add(panel3);
        browser3.loadURL("https://google.com");
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(container, BoxLayout.X_AXIS);
        frame.setSize(700, 500);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        */
    }

    private void iniciarBusqueda(String palabra) {
        
        ContentGetter getterThread = new ContentGetter(palabra, this);
        Thread hiloBusqueda = new Thread(getterThread);
        hiloBusqueda.start();
        // hilosBusqueda.add(hiloBusqueda);
    }
}
