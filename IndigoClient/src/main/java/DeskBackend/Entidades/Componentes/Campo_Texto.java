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
public class Campo_Texto{     
    String ID;    
    String nombre;
    String ID_formulario;
    int indice;//Para el caso de las agregaciones no debería aparecer... aunque si te recuerdas en la gramática consideras eso, así que no te preocupes por eso, solo haz que pueda ser opcional en el caso de la agregación
    String textoVisible;
    String alineacion;       
    boolean esRequerido = true;//a menos que diga lo contrario [puesto que es opcional] yo lo tomaré como true xD   
    
    public void establecerID(String elID){
        ID = elID;
    }
    
    public void establecerElNombre(String elNombre){
        nombre = elNombre;
    }
    
    public void establecerElIDFormulario(String elIDFormulario){
       ID_formulario = elIDFormulario;
    }
    
    public void establecerIndice(int elIndice){
        indice = elIndice;
    }
    
    public void establecerElTextoVisible(String elTextoVisible){
        textoVisible = elTextoVisible;
    }
    
    public void establecerAlineacion(String elTipoAlineacion){
        alineacion = elTipoAlineacion;
    }
    
    public void establecerTipoRequerimiento(boolean tipoRequerimiento){
        esRequerido = tipoRequerimiento;
    }
    
    public String darID(){
        return ID;
    }
    
    public String darNombre(){
        return nombre;
    }
    
    public String darIDFormulario(){
        return ID_formulario;
    }
    
    public int darIndice(){
        return indice;
    }
    
    public String darTextoVisible(){
        return textoVisible;
    }
    
    public String darAlineacion(){
        return alineacion;
    }
    
    public boolean darTipoRequerimiento(){
        return esRequerido;
    }        
}
