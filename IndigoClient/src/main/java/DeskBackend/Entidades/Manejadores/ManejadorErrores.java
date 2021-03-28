/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Manejadores;

import DeskBackend.Entidades.Herramientas.ListaEnlazada;
import DeskBackend.Entidades.Token;

/**
 *
 * @author phily
 */
public class ManejadorErrores {//este se encargará de enviar los listado de formulario [para así crearlo, abrilo o eliminarlo según corresp xD] y el de los componentes para así addlos a la págiana correspondiente, es decir que tb se encarga de establecer la conex [por medio de los métodos de otra clase, supongo xD] hacia la webApp xD
    private ListaEnlazada<Error> listadoErrores;
    
    public ManejadorErrores(){
        listadoErrores = new ListaEnlazada<>();                
    }
    
    public void establecerError(String tipoError, Token token){
        switch(tipoError){
            case ""://Aquí e nombre del tipo de error, que brindará la descripción generalizada [lo cual está super dúper xD] del problema, y así completar los que requiere lel cntrc de la clase error xD
                
        
        }
    }
    
    public ListaEnlazada<Error> darListadoErrores(){
        return listadoErrores;
    }
    
    public boolean hubieronErrores(){
        return listadoErrores.isEmpty();
    }
    
}
