/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Herramientas;

/**
 *
 * @author phily
 */
public class DistribuidorBloques {
    private String[] tipoParametros = {"CREDENCIALES_USUARIO", "PARAMETROS_FORMULARIO", "PARAMETROS_COMPONENTE"};    
        
    public String darTipoBloque(String tipo){
        switch(tipo){
            case "USUARIO":
                return darBloqueFrecuente(tipoParametros[0]);
                
            case "FORMULARIO":
                return darBloqueFrecuente(tipoParametros[1]);
                
            case "COMPONENTE":
                return darBloqueFrecuente(tipoParametros[2]);
             
            case "CONSULTA":
                return darBloqueConsultas();
        }
        return null;//pero esto no sucederá porque se hace por medio de btn xD
    }
    
    private String darBloqueFrecuente(String tipoParametro){
        return "< ! ini _ solicitud : \"TIPO _ SOLICITUD \" >\n\t{ \" "+tipoParametro+" \" : [ {\n\t\t"
                + "\" PARAMETRO _ 1 \" : \"valor_1\",\n\t\t\" PARAMETRO _ 2 \" : \"valor_2\""
                + "\n\t\t}\n\t\t]\n\t}\n< fin _ solicitud ! >";
    }
    
    private String darBloqueConsultas(){
        return "< ! ini _ solicitud : \" CONSULTAR _ DATOS \" >\n\t{ \" CONSULTAS \": [ {\n\t\t\" CONSULTA - # \" : "
                + "\" SELECT TO FORM -> $identificadorFormulario [ ID_campos ]\",\n\t\t\t"
                + "WHERE [ID_Campo tipoRelacion ValorComparacion]\n\t\t"
                + "\" PARAMETRO _ 2 \" : \"valor_2\"\n\t\t}\n\t\t]\n\t}\n< fin _ solicitud ! >";
    }
    
}
