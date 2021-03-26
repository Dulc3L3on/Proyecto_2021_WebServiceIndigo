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
    private LinkedList<String> opciones;    
    
    public void establecerListadoOpciones(LinkedList<String> listaComponentes){
        opciones = listaComponentes;
    }
    
    public LinkedList<String> darListadoComponentes(){
        return opciones;
    }    
    
}
