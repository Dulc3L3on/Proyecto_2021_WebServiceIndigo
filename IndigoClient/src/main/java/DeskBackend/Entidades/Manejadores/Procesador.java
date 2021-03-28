/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Manejadores;

import DeskBackend.Entidades.Transportadores.ComponentBus;
import DeskBackend.Entidades.Transportadores.FormBus;
import DeskBackend.Entidades.Transportadores.QueryBus;
import DeskBackend.Entidades.Transportadores.UserBus;
import DeskBackend.Analizadores.Lexer;
import DeskBackend.Analizadores.Parser_Indigo;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author phily
 */
public class Procesador {//este se encargará de llamar al manejador que le corresponde trabajar la lista no vacía ecibida del parser...esto siempre y cuando no hayan habido errores de cualquier tipo  
    private ManejadorArchivoRespuestaEntrada manejadorAxnUser;     
    private ManejadorAtributos manejadorAxnConsultas;    
    //encargados de realizar el aálisis a la entrada
    private Lexer lexer;
    private Parser_Indigo parser;    
    
    public Procesador(String especificacionesEntrada){
        StringReader lectorEntrada = new StringReader(especificacionesEntrada);
        
        lexer = new Lexer(lectorEntrada);
        parser = new Parser_Indigo(lexer);
        parser.estableceManejadorErrores(lexer.darManejadorErrores());
        
        if(!parser.darListadoErrores().isEmpty()){
            //Se envía el nombre del archivo por medio del método de manipulador de arch de entrada, al JSO o servlet, por medo de las herramientas proporcionadas por el inge xD
        }        
    }    
    
   
    public void ejecutarAxn(){
        if(!listadoAccionesUsuario.isEmpty()){
            //Se realiza la axn de usuario [teniendo en cuenta que si inicia sesión nuevamente [con el mismo usuario] se lanzará un warning...
        }
        if(true/*Y EL USAURIO YA INICIÓ SESIÓN*/){
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://openjdk.java.net/")).build();//Esta URI la tendría que almacenar en una var string para que pueda existir un botón para copiar el enlace...          
            
            if(!listadoAccionesFormulario.isEmpty()){
                try {
                    //mira si esta variable requets la debes crear aquí o de manera global xD, quiza la var si de manera global pero la instancia en cada condición xD
                    //HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://openjdk.java.net/")).timeout(Duration.ofMinutes(1)).header("Content-Type", "application/json").POST(BodyPublishers.ofFile(Paths.get("file.json"))).build();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(!listadoAccionesComponente.isEmpty()){
                
            }
            if(!listadoConsultas.isEmpty()){
                
            }   
        }else if(!listadoAccionesFormulario.isEmpty() || !listadoAccionesComponente.isEmpty() || !listadoConsultas.isEmpty()){
            JOptionPane.showMessageDialog(null, "Debes iniciar sesión para\nsolicitar cualquier tipo de acción");
        }       
        
    }
    
    public void ejecutarAccionesUsuario(){
        //pdte, puesto que aún no se ha produndizado [y tampco resuleto la prod que se encarga de consultar, y de add los cb olocados por el aux hace poco] y tb porque no se sabe bien bien s ise va a poder MySQL o no,como para idear el proceso de recuperación de daos a partir de un arch y no de la DB en tablas... xD
    }
    
    public void ejecutarAccionesFormulario(){
        //aquí es donde se usan los métodos de .NET...
    }
    
    public void ejecutarAccionesComponente(){
        //aquí tb usan los métodos de .NET...
    }
    
    public void ejeutarConsultas(){
        //puesto que involucra a la DB, se está en una condición similar que la axn de los usuarios,puesto que no se ha desarrollado la soluciḱn respectiva a la DB... xD
    }
    
}
