package fasttextcontent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HTTPManager {
    
    private final String agente = "Mozilla/5.0";
    private StringBuffer respuesta;
    
    public HTTPManager(){
        
    }
    
    public String getSearchResult(String palabra) {

        String defaultUrl = "http://www.google.com/search?q=";
        
        try {
            URL urlBusqueda = new URL(defaultUrl + palabra);
            HttpURLConnection conexionAbierta = (HttpURLConnection) urlBusqueda.openConnection();

            conexionAbierta.setRequestProperty("User-Agent", agente);

            int codigoRespuesta = conexionAbierta.getResponseCode();

            BufferedReader reader = new BufferedReader( new InputStreamReader(conexionAbierta.getInputStream()));

            respuesta = new StringBuffer();

            String lineaObtenida;
            while ((lineaObtenida = reader.readLine()) != null) {
                respuesta.append(lineaObtenida);
            }
            reader.close();
        } catch (Exception e) {
            return "<html> Ocurrió un error en la carga del contenido. </html>";
        }
        
        return respuesta.toString();
        
    }

}
