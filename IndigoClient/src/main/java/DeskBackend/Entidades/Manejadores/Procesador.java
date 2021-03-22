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
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class Procesador {//este se encargará de llamar al manejador que le corresponde trabajar la lista no vacía ecibida del parser...esto siempre y cuando no hayan habido errores de cualquier tipo
    private LinkedList<UserBus> listadoAccionesUsuario;
    private LinkedList<FormBus> listadoAccionesFormulario;
    private LinkedList<ComponentBus> listadoAccionesComponente;
    private LinkedList<QueryBus> listadoConsultas;
    private ManejadorSolicitudesUsuario manejadorAxnUser;
    private ManejadorSolicitudesFormulario manejadorAxnForm;    
    private ManejadorSolicitudesConsultas manejadorAxnConsultas;    
    //encargados de realizar el aálisis a la entrada
    private Lexer analizadorLexico;
    private ParserEntrada parser;
    
    
    public Procesador(String especificacionesEntrada){
        lexer.setEntrada(especificacionesEntrada);
        parser.set(lexer);

        Procesador(parser.getListadoAxnUser(), parser.getListadoAxnFormulario(), parser.getListadoAxnComponentes(), parser.getListadoConsultas());//aún no se han add esto smétodos al parser...
    }
    
    
    private Procesador(LinkedList<UserBus> elListadoAccionesUsuario, LinkedList<FormBus> elListadoAccionesFormulario,
            LinkedList<ComponentBus> elListadoAccionesComponente, LinkedList<QueryBus> elListadoConsultas){
        
        listadoAccionesUsuario = elListadoAccionesUsuario;
        listadoAccionesFormulario = elListadoAccionesFormulario;
        listadoAccionesComponente = elListadoAccionesComponente;
        listadoConsultas = elListadoConsultas;
        
        manejadorAxnUser = new ManejadorSolicitudesUsuario();
        manejadorAxnForm = new ManejadorSolicitudesFormulario();
        manejadorAxnConsultas = new ManejadorSolicitudesConsultas();    
    }
    
    public void ejecutarAxn(){
        if(!listadoAccionesUsuario.isEmpty()){
        
        }
        if(/*Y EL USAURIO YA INICIÓ SESIÓN*/){
            if(!listadoAccionesFormulario.isEmpty()){
        
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
