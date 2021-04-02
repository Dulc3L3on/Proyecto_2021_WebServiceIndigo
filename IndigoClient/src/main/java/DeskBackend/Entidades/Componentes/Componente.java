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
public class Componente {
    String ID;        
    String ID_formulario;
    int indice;//Para el caso de las agregaciones no debería aparecer... aunque si te recuerdas en la gramática consideras eso, así que no te preocupes por eso, solo haz que pueda ser opcional en el caso de la agregación
    String textoVisible;
    
    public void establecerID(String elID){
        ID = elID;
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
    
    public String darID(){
        return ID;
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
}
