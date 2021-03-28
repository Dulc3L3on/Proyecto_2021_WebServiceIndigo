/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Manejadores;

import DeskBackend.Entidades.Herramientas.ListaEnlazada;
import DeskBackend.Entidades.Token;
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
    private ListaEnlazada<Token> listadoAtributos;
    private FileWriter escritorArchivos;
    private BufferedWriter escritor;
    private File archivo;
    private String nombreArchivo;
    private String path = "/home/phily/Documentos/Carpeta_estudios/2021/5toSemestre/Compi_1"
            + "/Laboratorio/tareas/pra-proy/Proyecto1/Proyecto_2021_WebServiceIndigo/IndigoClient/src/"
            + "main/java/DeskBackend/Archivos/RespuestaEntrada/EntradaPrueba.txt";//el nombre del arch cb según el nombre del user y el del componente, por lo cual para tener acceso a él, se almacenará a una var para que se pueda recuperar el dato cuando se requiera xD
    private String tipoSolicitud;
    private String claseComponente = null;
    private ManejadorAtributos manejadorAtributos = new ManejadorAtributos();    
    private ManejadorErrores manejadorErrores;
    private int atributos = 0;//sigo sin saber por qué coloqué esto :v xD
    private int atributosOrdenados = 0;
    private int numeroSolicitudes = 0;
    private StringBuffer buffer;//a este se add los datos que se escribirán o no d
    
    
    public void registrarCdadDeAtributos(){//para qué rayos era eso??? xD :v xD
        atributos++;
    }        
    
    public ManejadorArchivoRespuestaEntrada(ManejadorErrores elManejadorErrores){
        try {
            manejadorErrores = elManejadorErrores;
            
            listadoAtributos = new ListaEnlazada<>();
            
            
            archivo = new File(path);//el path, será una combinación del nombre del usuario y el nombre del formulario xD, así será fácil hallarlo al buscarlo xD
            //archivo.createNewFile();//auqnue no se si debería hacerlo aquí, puesto que solo se enviará al arch lo recoupilado, si NO hubo error alguno xD
            
            
            escritorArchivos = new FileWriter(archivo);
            escritor = new BufferedWriter(escritorArchivos);
//            escritor.
            //NO se si add esto o no, puesto que si es una sola solicitud, estará mal... pero si no lo hago aquí, no se como lo agregaría al principio...
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar CREAR el archivo de respuesta");
        }       
    }    
    
    public void establecerNombreArchivo(String elNombreArchivo){//este es e que aún no se sabe donde exe, puesto que en el cntrc ya se requiere del valor que este recibe, pero este solose puede usar después de construir... :| xD
        nombreArchivo = elNombreArchivo;
    }//ahora no se como hacerle con el nombre de archivo, puesto que este lo recibo hasta haber logeado al user y esto según estaba pensando, se hace después de que se hayan emeplado ests métodos, [puesto que estos se exe en las axn del CUP] y la revisión del login se hace hasta que haya terminado el CUP su trabajo... :| xD
     //estaba pensando enviarle la var al string del path de una vez y que cuando se tviera el nombre que se colocara en la var, pero no funcionará puesto qu eun String no cb automaticamente cuando el obj al que se le igualó cb, uesto que el String [según recuerdo] es semejante a los tipos primitivos como el int...xD
    
    public void agregarAtributo(String nombreAtributo, Token token){//con atributos me refiero a "usuario" "fechaMOdificacion", "fechaCreacion"... y así xD        
        if(nombreAtributo.equals("opciones") | nombreAtributo.equals("textoVisible")){
            if(!Token.parseToken(listadoAtributos.getLastItem()).darNombreDelToken().equals("opciones") && 
               !Token.parseToken(listadoAtributos.getLastItem()).darNombreDelToken().equals("textoVisible")){
                
                token.establecerNombreDelToken(nombreAtributo);
                listadoAtributos.add(token);                
            }else{
               String lexema = Token.parseToken(listadoAtributos.getLastItem()).darLexema();
               lexema += ", "+token.darLexema();
               Token.parseToken(listadoAtributos.getLastItem()).reestablecerLexema(lexema);
            }                        
        }else{
            token.establecerNombreDelToken(nombreAtributo);
            listadoAtributos.add(token);         
        
            if(nombreAtributo.equals("clase")){
                claseComponente = nombreAtributo;
            }                       
        }        
    }
    
    public void establecerTipoSolicitud(String elTipoSolicitud){
      tipoSolicitud = elTipoSolicitud;
      escribirArchivo("iniSoli", elTipoSolicitud);  
      
      if(claseComponente!=null){
          escribirArchivo("clase", claseComponente);          
      }     
      
      ordenar();
      claseComponente = null;//para el siguiente componente si es que lo hubiera
      
      escribirArchivo("finSoli", elTipoSolicitud);  
      numeroSolicitudes++;
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
        Token tokenActual;
        
        for (atributosOrdenados = 0; atributosOrdenados < listadoAtributos.size(); atributosOrdenados++) {//de esta manera no habrá problemas cuando se vuelva a emplear esta variable, por le hecho de que cuando se requiera emplear nuevamente, se reiniciará su valor xD y cuando se quiere emplear para hacer el ajuste en la posición del istado al ordenar los componentes, aún poserá su valor, lo cual es lo que se necesita xD :3 xD
            tokenActual = Token.parseToken(listadoAtributos.get(atributosOrdenados));
            int posicionDelAtributo = buscarPosicionDelAtributo(tokenActual.darNombreDelToken(), atributosEsperados);
            
           if(!atributosEspecificados[posicionDelAtributo]){//no coloco if !=-1, porque nunca se llegará ahí puesto qu etodos los atributos que están en la lista, son los permitidos... xD
               atributosEspecificados[posicionDelAtributo] = true;
           }else{
               manejadorErrores.establecerErrorDeToken("atributoRepetido", tokenActual);
           }                        
        }
        
        revisarObligatoriedad(atributosEsperados, atributosEspecificados, tipoSolicitud);
    }
    
    private int buscarPosicionDelAtributo(String elAtributo, String[] atributosEsperados){
        for (int posicionAtributoActual = 0; posicionAtributoActual < atributosEsperados.length; posicionAtributoActual++) {
            if(atributosEsperados[posicionAtributoActual].equals(elAtributo)){
                return posicionAtributoActual;
            }            
        }
        return -1;//Aunque si mal no estoy, a esta partae no se va a llegar nunca, por el hecho de que todos los atributos que estén en la lista d eespecificados, será todos los permitirdos, lo que si puede pasar es que estén repetidos, pero NO que sean atributos que no admite la solicitud en cuestión xD
    }//preferí escoger este método y no el de buscar en la lista [ya sea la coincidencia o repitencia, por medio de un método a add en la ListaEnlazada.. xD
    
    private void revisarObligatoriedad(String[] atributosEsperados, boolean atributosEspecificados[], String tipoObligatoriedad){
        int[] arregloObligatoriedad = manejadorAtributos.darImportancia(tipoObligatoriedad);
        int numeroAtributosMinimosAdd = 0;
        
        for (int atributoActual = 0; atributoActual < atributosEspecificados.length; atributoActual++) {
            if(arregloObligatoriedad[atributoActual]==1 && atributosEspecificados[atributoActual]==false){
                //Add el error al manejador, debido a que no se especificó un campo obligatorio...
            }else if(arregloObligatoriedad[atributoActual]==0 && atributosEspecificados[atributoActual]==true){//Se hace el onteo para los atributos "minimo 1"
                numeroAtributosMinimosAdd++;
            }//soy libre de hacer eso, puesto que si hay un atributo == 0 entonces hay otros que tambien == 0... xD
        }
        
        if(tipoObligatoriedad.equals("modificacionFormulario") && numeroAtributosMinimosAdd<1){//yo me recuerdo que solo este tiene como oblig un valor 0... xD
            //Add el error por no haber especificado al menos un atributo de "estilo" a modificar del formulario xD
            manejadorErrores.establecerErrorHallado("atributosAModificarFaltantes", "", "");
        }
    }
    
    private void ordenarAtributosComponentes(){
        String[] atributosEspecificosComponente = manejadorAtributos.darDemasObligatoriosComponente(claseComponente);
        boolean[] atributosEspecificados = new boolean[atributosEspecificosComponente.length];
        Token tokenActual;
        atributosOrdenados++;//puesto que necesito la cdad evaluada y no la última posición xD
        
        for (int atributoActual = 0; atributoActual < listadoAtributos.size(); atributoActual++) {
            tokenActual = (Token) listadoAtributos.get(atributosOrdenados+atributoActual);
            int posicionDelAtributo = buscarPosicionDelAtributo(tokenActual.darNombreDelToken(), atributosEspecificosComponente);            
            
            if(!atributosEspecificados[posicionDelAtributo]){//no coloco if !=-1, porque nunca se llegará ahí puesto qu etodos los atributos que están en la lista, son los permitidos... xD
               atributosEspecificados[posicionDelAtributo] = true;//esto debe colocarse aunque sea una cloase, porque esta no ha sifdo registrada de esa manera xD
               
               if(!tokenActual.darNombreDelToken().equals("clase")){
                   //se escribe en el archivo xD
                   escribirArchivo(tokenActual.darNombreDelToken(), tokenActual.darLexema());
               }//puesto que ya se ha agergado antes xD                              
           }else{
               //Se envía el arror de REPITENCIA al manejador...
               manejadorErrores.establecerErrorDeToken("atributoRepetido", tokenActual);
           }                      
        }
          
        revisarObligatoriedad(atributosEspecificosComponente, atributosEspecificados, claseComponente);//es posible hacer esto puesto que el método de "revisarObligatoriedad" obtiene los datos del método que brinda los arregloes de enteros, también tiene declarados los caso que corresponden a los componentes xD :3 xD  
    }  
    
    private void escribirArchivo(String encabezado, String cuerpo){
        try {
            if(encabezado.equals("iniSoli")){
                escritor.append("<!ini_respuesta: \""+cuerpo+"\">");
                escritor.newLine();
      
            }else if(encabezado.equals("finSoli")){
                escritor.append("<!fin_respuesta: \""+cuerpo+"\">");
                escritor.newLine();
            
            }else{
                escritor.append(encabezado+": "+ cuerpo);
                escritor.newLine();
            }            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar escribir la respuesta a la solicitud");
        }        
    }
    
    public void finalizarArchivo(){
        if(numeroSolicitudes>1){
            try {
                escritor.append("<!ini_respuestas>", 0, 18);//para que se escriba al principio de todo el archivo xD, si no sale como quiero intenta nuevamente y si no, no lo coloques :v, de todos modos no te afecta al formar los tipo de componentes xD
                escritor.newLine();
                escritor.append("<!fin_respuestas>");
                escritor.newLine();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\n terminar de escribir el archivo");
            }
        }
        
        if(!manejadorErrores.hubieronErrores()){
            try {
                //se escribe el archivo y se envía a la web para que lo procese con sus analizadores y los muestre gráficamente xD
                archivo.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Surgió un error al registrar\n el archivo de respuesta :(");
            }
        }
    }
    
    public String darNombreArchivo(){//están formados por un nombre de usuario y el nombre del frmulario detal forma que se pueda hacer una revsión en or
        return nombreArchivo;
    }
    
    //deberás pensar como le harás cuando los bloques que se requeiren estań presenrtes pero no el orden que debería [como crear form y add componentes, que viniera antes lo de los compoentes y después del form :v, usarás lo que el inge dijo es decir descarar hasta encontrar lo que se busca o harás otra cosa, como buscarlo hasta hallarlo??? xD
}
