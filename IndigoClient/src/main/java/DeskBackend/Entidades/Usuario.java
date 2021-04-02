/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades;

/**
 *
 * @author phily
 */
public class Usuario {//nombre, pass, fechaCre, fechaModif, esadoLogueo
    private String nombre = null;    
    private String password = null;
    private String fechaCreacion = null;
    private String fechaModificacion = null;
    private boolean estaActivo = false;       
    
    public void establecerNombre(String elNombre){
        nombre = elNombre;
    }

    public void establecerPassword(String thePassword){
        password = thePassword;
    }   
    
    public void establecerFechaCreacion(String laFecha){//basta con esto, puesto que sin importar que tipo de acción sea, solo se requerirá un campo fecha xD
        fechaCreacion = laFecha;
    }
    
    public void establecerFechaModificacion(String laFecha){//basta con esto, puesto que sin importar que tipo de acción sea, solo se requerirá un campo fecha xD
        fechaModificacion = laFecha;
    }
    
    public void establecerEstadoActividad(boolean estado){
        estaActivo = estado;
    }
    
    public String darNombre(){
        return nombre;        
    }
    
    public String darPassword(){
        return password;
    }
    
    public String darFechaCreacion(){
        return fechaCreacion;
    }
    
    public String darFechaModificacion(){
        return fechaModificacion;
    }
    
    public boolean estaActivo(){
        return estaActivo;
    }        
}
