/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Manejadores;

/**
 *
 * @author phily
 */
public class ManejadorAtributos {
    private final String[] creacionUsuario = {"usuario", "password", "fechaCreacion"};//para el caso de las fechas cuando halles que no está definida, te acuerdas de addla xD
    private final String[] modificacionUsuario = {"usuarioAntiguo", "usuarioNuevo", "nuevoPassword", "fechaModificacion"};//todos
    private final String[] eliminacionUsuario = {"usuario"};//oblig
    private final String[] loginUsuario = {"usuario", "password"};//oblig
    private final String[] creacionFormulario = {"id", "titulo", "nombre", "tema", "usuarioCreacion", "fechaCreacion"};//obli
    private final String[] eliminacionFormulario = {"id"};//obli
    private final String[] modificacionFormulario = {"id", "titulo", "nombre", "tema"};//solo ID obligatorio
    private final String[] agregarComponentes = {"id", "formulario", "clase", "textoVisible"};//todos obligatorios... xD
    private final String[] modificarComponentes = {"id", "formulario", "indice"};//solo índice es opcional xD
    private final String[] eliminarComponente = {"id", "formulario"};//oblig
    
    //private String consulta = {};//no se si debo incluirlo xD :v
    
    public String[] darAtributosCorrespondientes(String tipoAtributos){
        switch(tipoAtributos){
            case "creacionUsuario":
                return creacionUsuario;
            case "modificacionUsuario":
                return modificacionUsuario;
            case "eliminacionUsuario":
                return eliminacionUsuario;
            case "loginUsuario":
                return loginUsuario;    
            case "creacionFormulario":
                return creacionFormulario;
            case "eliminacionFormulario":
                return eliminacionFormulario;
            case "modificacionFormulario":
                return modificacionFormulario;
            case "agregarComponentes":
                return agregarComponentes;
            case "modificarComponentes":
                return modificarComponentes;
            case "eliminarComponente":
                return eliminarComponente;
        }
        
        return null;
    } 
    
    public String[] darDemasObligatoriosComponente(String tipoComponente){//este método s eencarga de dar el listado de atributos correspondientes al tipo de componente, estos serán tomados como optativos en el caso de la modificación y como obligatirios/opcionales dependiendo del tipo, en la agregación xD
        String[] otrosAtributos;
        
        switch(tipoComponente){
            case "campoTexto":
                otrosAtributos = new String[3];
                otrosAtributos[0] = "nombreCampo";otrosAtributos[1] = "alineacion";otrosAtributos[2] = "requerido";
                return otrosAtributos;//solo nombre de Campo es obligatorio
            
            case "areaTexto":
                otrosAtributos = new String[5];
                otrosAtributos[0] = "nombreCampo"; otrosAtributos[1] = "filas"; otrosAtributos[2] = "columnas";
                ;otrosAtributos[3] = "alineacion";otrosAtributos[4] = "requerido";
                return otrosAtributos;//solo la alineación y "requerido" son opcionales... xD
           
            case "checkbox": case "radio": case "combo":            
                otrosAtributos = new String[2];//:v xD
                otrosAtributos[0] = "nombreCampo";otrosAtributos[1] = "opciones";
                otrosAtributos[2] = "alineacion";otrosAtributos[3] = "requerido";
                
                return otrosAtributos;
            
            case "imagen":
                otrosAtributos = new String[1];
                otrosAtributos[0] = "URL";
                return otrosAtributos;//solo nombre de Campo es obligatorio            
        }//eliminé al botón puesto que solo requiere de los atrib obligatorios generales xD
        
        return null;//porque creo que el fichero solo requiere ID y el IDForm
    }
    
    public int[] darImportancia(String tipoAccion){//1 -> obligatorio, -1 -> opcional, 0 -> al menos 1...
        switch(tipoAccion){
            case "creacionUsuario":
                {
                    int[] importancia = {1, 1, 1};
                    return importancia;
                }                
            case "modificacionUsuario": case "agregarComponentes":
                {
                    int[] importancia = {1, 1, 1, 1};
                    return importancia;
                }                
            case "eliminacionUsuario": case "eliminacionFormulario":
                {
                    int[] importancia = {1};
                    return importancia;
                }                
            case "loginUsuario": case "eliminarComponente":
                {
                    int[] importancia = {1,1};
                    return importancia;
                }                
            case "creacionFormulario":
                {
                    int[] importancia = {1,1,1,1,1,1};
                    return importancia;
                }                
            case "modificacionFormulario":
                {
                    int[] importancia = {1, 0, 0, 0};
                    return importancia;
                }                                   
            case "modificarComponentes":
                {
                    int[] importancia = {1, 1, -1};
                    return importancia;
                }         
               
             //de los atributos de los componentes xD
            case "campoTexto":
                {
                    int[] importancia = {1,-1,-1};
                    return importancia;
                }                                         
            case "areaTexto":
                {
                    int[] importancia = {1, 1, 1,-1, -1};
                    return importancia;
                }    
            case "checkbox": case "radio": case "combo":
                {
                    int[] importancia = {1, 1, -1, -1};
                    return importancia;
                }              
            case "imagen":
                {
                    int[] importancia = {1};
                    return importancia;
                }   
        }
        
        return null;//para evitar llega aquí cuand o el comp sea un btn, solo no llames este método para el comp xD
    }          
}
