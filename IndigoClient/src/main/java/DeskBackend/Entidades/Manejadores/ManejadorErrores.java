/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Manejadores;

import DeskBackend.Entidades.EntidadError;
import DeskBackend.Entidades.Herramientas.ListaEnlazada;
import DeskBackend.Entidades.Token;

/**
 *
 * @author phily
 */
public class ManejadorErrores {//este se encargará de enviar los listado de formulario [para así crearlo, abrilo o eliminarlo según corresp xD] y el de los componentes para así addlos a la págiana correspondiente, es decir que tb se encarga de establecer la conex [por medio de los métodos de otra clase, supongo xD] hacia la webApp xD
    private ListaEnlazada<EntidadError> listadoErrores;
    
    public ManejadorErrores(){        
       listadoErrores = new ListaEnlazada<>();//a mi pensar cuando iguale a la instancia del lexer, la var que se empleará en CUP, este cntrc no se exe xD, niña, cuanto tiempo, deberías saberlo!!! :v xD
        
    }
    
    public void establecerErrorDeToken(String tipoError, Token token){
        String descripcion = "";
        
        switch(tipoError){
            case "lexico"://Aquí e nombre del tipo de error, que brindará la descripción generalizada [lo cual está super dúper xD] del problema, y así completar los que requiere lel cntrc de la clase error xD
                descripcion = "El caracter "+ token.darLexema() +" no es válido";
            break;
            case "atributoRepetido":
                descripcion = "Atributo "+ token.darNombreDelToken()+" repetido";
            break;                                   
        }              
        
        EntidadError error = new EntidadError(token, tipoError, descripcion);
    }
    
    public void establecerErrorHallado(String tipoError, String elError, String loAfectado){
        String descripcion = "";
        
        switch(tipoError){
            case "atributosAModificarFaltantes":
                descripcion = "Necesitas modificar tema, titulo\n o nombre del formulario";
            break;    
            case "obligatorioFaltante":
                descripcion = "Campo obligatorio "+ elError+" ausente";
            break;    
            case "atributosInsuficientes":
                descripcion = "No se especificó el número de parametros esperados";
            break;    
            case "atributosLoginFaltantes":
                descripcion = "Debes especificar el username y password";
            break;    
            case "atributoFaltante":
                descripcion = "Faltó especificar el " +elError+" a eliminar";//será útil para la eli: form, eli user, eli comp [solo que en este caso elError tendría a 2, el idCOmp e IDForm xD]
            break; 
            case "atribsFaltantesAddComp":
                descripcion = "No puedes especificar menos atributos de los que requiere un botón";
            break;
            case "atribsFaltantesModifComp":
                descripcion = "Al menos debes especificar el IDComp, IDForm y un atributo a modificar";//Si se mira mal, dejas el msje, no se recibió el # atributos mínimos
            break;
        }
    }
    
    public ListaEnlazada<EntidadError> darListadoErrores(){
        return listadoErrores;
    }
    
    public boolean hubieronErrores(){
        return listadoErrores.isEmpty();
    }
    
}
