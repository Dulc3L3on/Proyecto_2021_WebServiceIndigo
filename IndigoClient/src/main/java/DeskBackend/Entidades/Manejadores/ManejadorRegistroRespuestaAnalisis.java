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
public class ManejadorRegistroRespuestaAnalisis {
    private FileWriter escritorArchivos;   
    private BufferedWriter escritor;
    private File archivo;    
    private String nombreArchTemp = "ArchivoRespuesta.txt";
    private String path = "/home/phily/Documentos/Carpeta_estudios/2021/5toSemestre/Compi_1"
            + "/Laboratorio/tareas/pra-proy/Proyecto1/Proyecto_2021_WebServiceIndigo/IndigoWebApp/src/"
            + "main/webapp/resources/Archivos/Respuestas/";//el nombre del arch cb según el nombre del user y el del componente, por lo cual para tener acceso a él, se almacenará a una var para que se pueda recuperar el dato cuando se requiera xD    
    private String nombreArchivo ="";
    
    public ManejadorRegistroRespuestaAnalisis(){
        try {                     
            archivo = new File(path+nombreArchTemp);//el path, será una combinación del nombre del usuario y el nombre del formulario xD, así será fácil hallarlo al buscarlo xD                        
                //se escribe el archivo y se envía a la web para que lo procese con sus analizadores y los muestre gráficamente xD                
            archivo.createNewFile();
            escritorArchivos = new FileWriter(archivo);
            escritor = new BufferedWriter(escritorArchivos);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar CREAR el archivo de respuesta");
            System.out.println("msje:"+ex.getMessage());
        }       
    }     
    
    public void finalizarArchivoRespuesta(ListaEnlazada<String> listadoEspecificaciones, int numeroSolicitudes, boolean hubieronErrores){
        if(numeroSolicitudes>1){
            listadoEspecificaciones.add(0, "< ! ini _ respuestas >");
            listadoEspecificaciones.add("< ! fin _ respuestas >");            
        }
        
        if(!hubieronErrores){
            try {            
            //mientras tanto lo comentaré, pues no es de mi utilidad xD
            nombreArchivo = path+nombreArchivo;//puepara este punto el nombre del archivo ya estará bien formado xD, pues si no fuera aśi es porque ubieron errores xD y al existir ellos no se llegaría hasta aquí xD
            archivo.renameTo(new File(nombreArchivo));//YO SUPONGO que pasa el arch original completo a la ubicación especificada y NO lo vuelve a crear, es decir no lo deja en blanco... claramente esto no scederá porque NO TIENE SENTIDO que se llame renameTO... xD y tampoco tendría sentido la forma en que se renombra si lo que hiciera este métooo es rehacer el arch desde 0... xD            
            escribirArchivoRespuesta(listadoEspecificaciones);
            escritor.close();
            //escritor.flush();//no importa si lo comento, puesto que el manejador de respuestas se instancia en el CUP, por lo cual cada vez que se empleee dicho parser, se credaá una instancia nueva, puest oque al terminr el trabajo el parser, ya no tiene nada más que hacer xD, ADEMÁS tb comenté este método porque era el que adndaba dando errores xD
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Surgió un error al registrar\n el archivo de respuesta :(");
            }
        }else{
            System.out.println("Se borro el archivo");
            archivo.delete();//que bueno que almacena el nombre hasta ser limpiado xD
        }
    }
    
     private void escribirArchivoRespuesta(ListaEnlazada<String> listadoEspecificaciones){
        for (int lineaActual = 0; lineaActual < listadoEspecificaciones.size(); lineaActual++) {
            try {
                escritor.write(String.valueOf(listadoEspecificaciones.get(lineaActual)));
                escritor.newLine();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nescribir el archivo de reespuesta");
            }
        }
    }
    
    public void establecerNombreArchivo(String elNombre){//Auqneu en relidad será una parte que se irá modificando para al final formar el nombre del archivo xD
        nombreArchivo = elNombre;
    }
    
    public String darNombreArchivo(){
        return nombreArchivo;
    }
    
    public String darDireccionCompletaArchivo(){//están formados por un nombre de usuario y el nombre del frmulario detal forma que se pueda hacer una revsión en or
        return path+nombreArchivo;
    }      
    
}
