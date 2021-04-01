/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Intermediarias;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URI;


public class Enlazador{

    public void linkWithIndigoFormsWeb(){

        HttpGet request = new HttpGet("http://localhost:8080/IndigoWebApp/creadorComponentes");

        // add request headers
        request.addHeader("nombreUser", "ArchivoRespuesta");//en el 2do paraám iría el nombre del arch correspondiente al user y nombre del form... xD
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");//vamos a ver para qué sirve esto xD

        try (CloseableHttpClient cliente = HttpClients.createDefault();
             CloseableHttpResponse response = cliente.execute(request)) {                
            
            
            // Get HttpResponse Status
            System.out.println(response.getProtocolVersion());              // HTTP/1.1
            System.out.println(response.getStatusLine().getStatusCode());   // 200
            System.out.println(response.getStatusLine().getReasonPhrase()); // OK
            System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

            HttpEntity entity = response.getEntity();
            entity.consumeContent();
            if (entity != null) {
                // return it as a String
                
                
            //    EntityUtils.consume(entity);
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }

        } catch (IOException ex) {
            System.out.println("Error al intentar conectar las aplicaciones");
        }
    }

}