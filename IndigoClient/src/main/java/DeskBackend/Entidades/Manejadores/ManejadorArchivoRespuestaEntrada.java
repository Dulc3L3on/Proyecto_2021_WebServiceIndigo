/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Manejadores;

import DeskBackend.Entidades.Herramientas.ListaEnlazada;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class ManejadorArchivoRespuestaEntrada {
    private ListaEnlazada<String[]> listadoAtributos;
    private FileWriter escritorArchivos;
    private BufferedWriter escritor;
    private File archivo;
    private String path = "/home/phily/Documentos/Carpeta_estudios/2021/5toSemestre/Compi_1"
            + "/Laboratorio/tareas/pra-proy/Proyecto1/Proyecto_2021_WebServiceIndigo/IndigoClient/src/"
            + "main/java/DeskBackend/Archivos/RespuestaEntrada/RespuestaEntrada.txt";
    private String tipoSolicitud;
    private String claseComponente;
    private ManejadorAtributos manejadorAtributos = new ManejadorAtributos();    
    
    public ManejadorArchivoRespuestaEntrada(){
        try {
            listadoAtributos = new ListaEnlazada<>();
            
            archivo = new File(path);
            archivo.createNewFile();//auqnue no se si debería hacerlo aquí, puesto que solo se enviará al arch lo recoupilado, si NO hubo error alguno xD
            
            escritorArchivos = new FileWriter(archivo);
            escritor = new BufferedWriter(escritorArchivos);
//            escritor.
            //NO se si add esto o no, puesto que si es una sola solicitud, estará mal... pero si no lo hago aquí, no se como lo agregaría al principio...
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar CREAR el archivo de respuesta");
        }       
    }       
    
    public void agregarAtributo(String nombreAtributo, String atributo){//con atributos me refiero a "usuario" "fechaMOdificacion", "fechaCreacion"... y así xD
        String datosAtributo[] = {nombreAtributo, atributo};
        listadoAtributos.add(datosAtributo);
        
        if(nombreAtributo.equals("tipoComponente")){
          claseComponente = nombreAtributo;
        }
    }
    
    public void establecerTipoSolicitud(String elTipoSolicitud){
      tipoSolicitud = elTipoSolicitud;
        
      ordenar();
    }     
    
    private void ordenar(){//pero habrpa un problema si nunca se llega a este método por el hecho de que no hayan especificado el tipo de solicutd... ah no xD, no sucederá puesto que eso se revisa antes entonces ni siquiera llegará a la prod para establecer el 1er atributo xD, sino que seguirá cojn el bloque de solicitud siguiente xD
        ordenarAtributosGenerales();
        
        //esto es por la existencia de los tipos de componentes... xD
        if(tipoSolicitud.equals("agregarComponentes") || tipoSolicitud.equals("modificarComponentes")){
            ordenarAtributosComponentes();
        }        
        listadoAtributos.clear();        
    }    
    
    private void ordenarAtributosGenerales(){
        String[] atributosEsperados = manejadorAtributos.darAtributosCorrespondientes(tipoSolicitud);
        boolean[] atributosEspecificados = new boolean[atributosEsperados.length];        
        
        for (int atributoActual = 0; atributoActual < listadoAtributos.size(); atributoActual++) {
            int posicionDelAtributo = buscarPosicionDelAtributo((String)listadoAtributos.get(atributoActual), atributosEsperados);
            
           if(!atributosEspecificados[posicionDelAtributo]){//no coloco if !=-1, porque nunca se llegará ahí puesto qu etodos los atributos que están en la lista, son los permitidos... xD
               atributosEspecificados[posicionDelAtributo] = true;
           }else{
               //Se envía el arror de REPITENCIA al manejador...
           }                      
        }
        
        revisarObligatoriedad(atributosEspecificados);
    }
    
    private int buscarPosicionDelAtributo(String elAtributo, String[] atributosEsperados){
        for (int posicionAtributoActual = 0; posicionAtributoActual < atributosEsperados.length; posicionAtributoActual++) {
            if(atributosEsperados[posicionAtributoActual].equals(elAtributo)){
                return posicionAtributoActual;
            }            
        }
        return -1;//Aunque si mal no estoy, a esta partae no se va a llegar nunca, por el hecho de que todos los atributos que estén en la lista d eespecificados, será todos los permitirdos, lo que si puede pasar es que estén repetidos, pero NO que sean atributos que no admite la solicitud en cuestión xD
    }//preferí escoger este método y no el de buscar en la lista [ya sea la coincidencia o repitencia, por medio de un método a add en la ListaEnlazada.. xD
    
    private void revisarObligatoriedad(boolean atributosEspecificados[]){
        int[] arregloObligatoriedad = manejadorAtributos.darImportancia(tipoSolicitud);
        int numeroAtributosMinimosAdd = 0;
        
        for (int atributoActual = 0; atributoActual < atributosEspecificados.length; atributoActual++) {
            if(arregloObligatoriedad[atributoActual]==1 && atributosEspecificados[atributoActual]==false){
                //Add el error al manejador, debido a que no se especificó un campo obligatorio...
            }else if(arregloObligatoriedad[atributoActual]==0 && atributosEspecificados[atributoActual]==true){//Se hace el onteo para los atributos "minimo 1"
                numeroAtributosMinimosAdd++;
            }//soy libre de hacer eso, puesto que si hay un atributo == 0 entonces hay otros que tambien == 0... xD
        }
        
        if(tipoSolicitud.equals("modificacionFormulario") && numeroAtributosMinimosAdd<1){
            //Add el error por no haber especificado al menos un atributo de "estilo" a modificar del formulario xD
        }
    }
    
    private void ordenarAtributosComponentes(){
        String[] atributosEspecificosComponente = manejadorAtributos.darDemasObligatoriosComponente(tipoSolicitud);
        boolean[] atributosEspecificados = new boolean[atributosEspecificosComponente.length];
        
          for (int atributoActual = 0; atributoActual < listadoAtributos.size(); atributoActual++) {
            int posicionDelAtributo = buscarPosicionDelAtributo((String)listadoAtributos.get(atributoActual), atributosEspecificosComponente);
            
           if(!atributosEspecificados[posicionDelAtributo]){//no coloco if !=-1, porque nunca se llegará ahí puesto qu etodos los atributos que están en la lista, son los permitidos... xD
               atributosEspecificados[posicionDelAtributo] = true;
           }else{
               //Se envía el arror de REPITENCIA al manejador...
           }                      
        }
          
        revisarObligatoriedad(atributosEspecificados);//es posible hacer esto puesto que el método de "revisarObligatoriedad" obtiene los datos del método que brinda los arregloes de enteros, también tiene declarados los caso que corresponden a los componentes xD :3 xD  
    }          
    
    //deberás pensar como le harás cuando los bloques que se requeiren estań presenrtes pero no el orden que debería [como crear form y add componentes, que viniera antes lo de los compoentes y después del form :v, usarás lo que el inge dijo es decir descarar hasta encontrar lo que se busca o harás otra cosa, como buscarlo hasta hallarlo??? xD
}
