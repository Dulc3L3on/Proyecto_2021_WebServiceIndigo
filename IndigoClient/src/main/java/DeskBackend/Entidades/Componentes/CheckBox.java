/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Componentes;

import java.util.LinkedList;

/**
 *
 * @author phily
 */
public class CheckBox extends Campo_Texto{//De esta clase heredará los componentes que poseen tipos de opciones xD
    private LinkedList<String> opciones;//puesto que en lugar de hacer la concatenciación en la producción cadena [así como se hizo para el arc de entrada] se add de una vez aquí cada atributo xD    
    
    public CheckBox(){
        opciones = new LinkedList<>();
    }
    
    public void agregarOpciones(String opcion){
        opciones.add(opcion);
    }
    
    public LinkedList<String> darListadoComponentes(){
        return opciones;
    }    
    
}
