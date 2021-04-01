/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Intermediarias;

/**
 *
 * @author phily
 */
public class FormBus {
    private String tipoAccion = null;
    private String ID = null;
    private String titulo = null;
    private String nombre = null;
    private String tema = null;
    private String creador = null;
    private String fecha = null;
    
    public void establecerTipoAccion(String laAccion){
        tipoAccion = laAccion;
    }
    
    public void establecerID(String elID){
        ID = elID;
    }   
    
    public void establecerTitulo(String elTitulo){
        titulo = elTitulo;
    }
    
    public void establecerElNombre(String elNombre){
        nombre = elNombre;
    }
    
    public void establecerTema(String elTema){
        tema = elTema;
    }
    
    public void establecerUsuarioCreador(String elCreador){
        creador = elCreador;
    }
    
    public void establecerFecha(String laFecha){
        fecha = laFecha;
    }
    
    public String darTipoAccion(){
        return tipoAccion;
    }
    
    public String darID(){
        return ID;
    }    
    
    public String darTitulo(){
        return titulo;
    }
    
    public String darNOmbre(){
        return nombre;
    }
    
    public String darTema(){
        return tema;
    }
    
    public String darCreador(){
        return creador;
    }
    
    public String darFecha(){
        return fecha;
    }   
}
