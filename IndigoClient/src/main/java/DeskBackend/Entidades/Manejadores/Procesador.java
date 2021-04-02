/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Manejadores;

import DeskBackend.Analizadores.Lexer;
import DeskBackend.Analizadores.Parser_Indigo;
import DeskBackend.Entidades.EntidadError;
import DeskBackend.Entidades.Herramientas.DesktopAPI;
import DeskBackend.Entidades.Herramientas.ListaEnlazada;
import DeskBackend.Entidades.Intermediarias.Enlazador;
import java.io.StringReader;
import java.net.URI;

/**
 *
 * @author phily
 */
public class Procesador {//este se encargará de llamar al manejador que le corresponde trabajar la lista no vacía ecibida del parser...esto siempre y cuando no hayan habido errores de cualquier tipo  
    private ManejadorArchivoRespuestaEntrada manejadorRespuestas;           
    private Enlazador enlazador;
    private ListaEnlazada<EntidadError> listadoErrores = null;//puesto que esta clase se instancia en el parser, entonces no habrá problema con decir que si esta lista es null entonces no hubieron errores xD
    //encargados de realizar el aálisis a la entrada
    private Lexer lexer;
    private Parser_Indigo parser;     
    
    public Procesador(String especificacionesEntrada){        
        try {
            StringReader lectorEntrada = new StringReader(especificacionesEntrada);
            enlazador = new Enlazador();            
            
            System.out.println("A punto de entrar a la fase de análisis...\n");
            lexer = new Lexer(lectorEntrada);
            parser = new Parser_Indigo(lexer);
            parser.estableceManejadorErrores(lexer.darManejadorErrores());
            
            parser.parse();
            
            System.out.println("A punto de entrar a la conexion\n");
            
            if(parser.darListadoErrores().isEmpty()){
                //enlazador.linkWithIndigoFormsWeb();
                
                DesktopAPI.browse(new URI("http://localhost:8080/IndigoWebApp/creadorComponentes"));
                System.out.println("Acabando de salir de la conexion\n");
            }else{
                listadoErrores = parser.darListadoErrores();
            }
        } catch (Exception ex) {
            System.out.println("Error al intentar ANALIZAR la entrada");
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }    
    
    public ListaEnlazada<EntidadError> darListadoErrores(){
        return listadoErrores;
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
