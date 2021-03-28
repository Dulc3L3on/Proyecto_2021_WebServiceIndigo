/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Herramientas;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phily
 */
public class ListaEnlazada <T> extends ArrayList{
    
    public Object find(String datoABuscar){
        for (int datoActual = 0; datoActual < this.size(); datoActual++) {
            if(this.get(datoActual).equals(datoActual)){
                return this.get(datoActual);
            }
        }
        return null;
    }   
        
    public Object getLastItem(){
        return this.get(this.size()-1);
    }
    
}
