/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Componentes;

/**
 *
 * @author phily
 */
public class Campo_Texto extends Componente{         
    String nombre;      
    String alineacion;       
    boolean esRequerido = true;//a menos que diga lo contrario [puesto que es opcional] yo lo tomaré como true xD   
        
    public void establecerElNombre(String elNombre){
        nombre = elNombre;
    }
    
    public void establecerAlineacion(String elTipoAlineacion){
        alineacion = elTipoAlineacion;
    }
    
    public void establecerTipoRequerimiento(boolean tipoRequerimiento){
        esRequerido = tipoRequerimiento;
    }
    
    public String darNombre(){
        return nombre;
    }
    
    public String darAlineacion(){
        return alineacion;
    }
    
    public boolean darTipoRequerimiento(){
        return esRequerido;
    }        
}
