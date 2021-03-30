/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Manejadores;

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
import javax.swing.JOptionPane;


/**
 *
 * @author phily
 */
public class Procesador {//este se encargará de llamar al manejador que le corresponde trabajar la lista no vacía ecibida del parser...esto siempre y cuando no hayan habido errores de cualquier tipo  
    private ManejadorArchivoRespuestaEntrada manejadorRespuestas;     
    private ManejadorAtributos manejadorAxnConsultas;    
    //encargados de realizar el aálisis a la entrada
    private Lexer lexer;
    private Parser_Indigo parser;    
    
    public Procesador(String especificacionesEntrada){
        try {
            StringReader lectorEntrada = new StringReader(especificacionesEntrada);
            
            System.out.println("A punto de entrar a la fase de análisis...\n");
            lexer = new Lexer(lectorEntrada);
            parser = new Parser_Indigo(lexer);
            parser.estableceManejadorErrores(lexer.darManejadorErrores());
            
            parser.parse();
            
            System.out.println("A punto de entrar a la conexion\n");
            
            if(parser.darListadoErrores().isEmpty()){
                try {
                    //Se envía el nombre del archivo por medio del método de manipulador de arch de entrada, al JSO o servlet, por medo de las herramientas proporcionadas por el inge xD
                    HttpClient cliente = HttpClient.newHttpClient();
                    //yo asumo que ese set ContentType es para especificar que va a ser lo que se abrirá xD, en este caso un JSP, por eso coloqué este tipo xD... aunque quizá sea lo del archivo.... :v xD
                    HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080//IndigoWebApp//IndigoForms.jsp")).timeout(Duration.ofMinutes(15)).header("Content-Type", "text/html; charset=UTF-8").POST(BodyPublishers.ofFile(Paths.get(manejadorRespuestas.darDireccionCompletaArchivo()))).build();//la otra diagonal servirá para establecer el header que será el quedistinga a cada archivo de los demás... xD
                } catch (FileNotFoundException ex) {                
                    JOptionPane.showMessageDialog(null, "El archivo: "+ manejadorRespuestas.darNombreArchivo() +" no existe");
                }
                System.out.println("Acabando de salir de la conexion\n");
            }
        } catch (Exception ex) {
            System.out.println("Error al intentar ANALIZAR la entrada");
            ex.printStackTrace();
            System.out.println(ex.getMessage());
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
