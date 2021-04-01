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
public class UserBus {
    private String tipoAccion = null;
    private String nombreActual = null;
    private String nombreAntiguo = null;
    private String password = null;
    private String fecha = null;//si es que sí se puede usar la DB entonces pasaras esto al tipo date que maneja SQL, para trabajar con las fechas así como lo haz hecho en los proyectos pasados, más que todo en el proyecto anterior, es decir Banco_ElBilleton
    
    public void establecerTipoAccion(String tipo){
        tipoAccion = tipo;
    }
    
    public void establecerNombreActual(String elNombreActual){
        nombreActual = elNombreActual;
    }
    
    public void establecerNombreAntiguo(String elNombreAntiguo){
        nombreAntiguo = elNombreAntiguo;
    }   
    
    public void establecerPassword(String thePassword){
        password = thePassword;
    }   
    
    public void establecerFecha(String laFecha){//basta con esto, puesto que sin importar que tipo de acción sea, solo se requerirá un campo fecha xD
        fecha = laFecha;
    }
    
    public String darTipoAccion(){
        return tipoAccion;
    }
    
    public String darNombreActual(){
        return nombreActual;        
    }
    
    public String darNombreAntiguo(){
        return nombreAntiguo;
    }
    
    public String darPassword(){
        return password;
    }
    
    public String darFecha(){
        return fecha;
    }
    
}
