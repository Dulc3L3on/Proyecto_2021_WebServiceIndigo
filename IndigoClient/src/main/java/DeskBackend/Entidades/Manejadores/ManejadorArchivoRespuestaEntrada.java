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
    private ListaEnlazada<String> listadoEspecificaciones;
    private FileWriter escritorArchivos;
    private BufferedWriter escritor;
    private File archivo;    
    private String nombreArchTemp = "ArchivoRespuesta.txt";
    private String path = "/home/phily/Documentos/Carpeta_estudios/2021/5toSemestre/Compi_1"
            + "/Laboratorio/tareas/pra-proy/Proyecto1/Proyecto_2021_WebServiceIndigo/IndigoWebApp/src/"
            + "main/webapp/resources/Archivos/";//el nombre del arch cb según el nombre del user y el del componente, por lo cual para tener acceso a él, se almacenará a una var para que se pueda recuperar el dato cuando se requiera xD    
    private String tipoSolicitud;
    private String claseComponente = null;
    private String nombreArchivo ="";
    private ManejadorAtributos manejadorAtributos = new ManejadorAtributos();    
    private ManejadorErrores manejadorErrores;    
    private int atributosOrdenados = 0;
    private int numeroSolicitudes = 0;   
    private Token tokenAuxiliar = null;
    
    public ManejadorArchivoRespuestaEntrada(ManejadorErrores elManejadorErrores){
        try {
            manejadorErrores = elManejadorErrores;            
            listadoAtributos = new ListaEnlazada<>();            
            listadoEspecificaciones = new ListaEnlazada<>();
            
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
    
    public void agregarAtributo(String nombreAtributo, Token token){//con atributos me refiero a "usuario" "fechaMOdificacion", "fechaCreacion"... y así xD        
        if(nombreAtributo.isEmpty() | nombreAtributo.equals("opciones") | nombreAtributo.equals("textoVisible") | nombreAtributo.equalsIgnoreCase("titulo")){
            if(nombreAtributo.equalsIgnoreCase("textoVisible") | nombreAtributo.equalsIgnoreCase("opciones") | nombreAtributo.equalsIgnoreCase("titulo")){
                tokenAuxiliar.establecerNombreDelToken(nombreAtributo);
                listadoAtributos.add(tokenAuxiliar);
                tokenAuxiliar = null;//puesto que se sabe que se entrará aquí al llegar a la última palrba que se debe concatenar... xD
            }else{
                if(tokenAuxiliar==null){
                    tokenAuxiliar = token;
                }else{                    
                    tokenAuxiliar.reestablecerLexema(tokenAuxiliar.darLexema() +" "+ token.darLexema());                    
                }
            }                    
        }else{
            token.establecerNombreDelToken(nombreAtributo);
            listadoAtributos.add(token);         
        
            if(nombreAtributo.equals("clase")){
                claseComponente = token.darLexema();
            }                
        }        
    }
    
    public void establecerTipoSolicitud(String elTipoSolicitud){
      tipoSolicitud = elTipoSolicitud;
      //escribirArchivo("iniSoli", elTipoSolicitud); 
      listadoEspecificaciones.add(" < ! ini _ respuesta : \" "+ elTipoSolicitud+" \">");
      
      if(claseComponente!=null){
          //escribirArchivo("clase", claseComponente);          
          listadoEspecificaciones.add("clase: "+ claseComponente);
      }     
      
      ordenar();
      claseComponente = null;//para el siguiente componente si es que lo hubiera
      
      //escribirArchivo("finSoli", "");  
      listadoEspecificaciones.add(" < fin _ respuesta ! >");
      numeroSolicitudes++;
    }     
    
    private void ordenar(){//pero habrpa un problema si nunca se llega a este método por el hecho de que no hayan especificado el tipo de solicutd... ah no xD, no sucederá puesto que eso se revisa antes entonces ni siquiera llegará a la prod para establecer el 1er atributo xD, sino que seguirá cojn el bloque de solicitud siguiente xD
        ordenarAtributosGenerales();
        
        //esto es por la existencia de los tipos de componentes... xD
        if(claseComponente!=null && (tipoSolicitud.equals("agregarComponentes") || tipoSolicitud.equals("modificarComponentes"))){
            ordenarAtributosComponentes();
        }        
        listadoAtributos.clear();        
    }    
    
   private void ordenarAtributosGenerales(){
        String[] atributosEsperados = manejadorAtributos.darAtributosCorrespondientes(tipoSolicitud);
        boolean[] atributosEspecificados = new boolean[atributosEsperados.length];               
        int[] datosImportantes;               
        //coloco como lím el tamaño del arreglo, pues los atrib generales, nunca sobrepasarán o serán menores a la cdad esperada, por e hecho de que en las producciones se colocan los diferentes # de parámetros requeridos de a cuerdo al caso, y tb acer esto no afecta al tipo de solicitud [Add comp [creo que hay otro xD]], es más arregla el inconveniente de que el listado tenga más elementos que el arreglo, lo cual vendría a provocar un indexOutBounds... xD
        for (atributosOrdenados = 0; atributosOrdenados < atributosEsperados.length; atributosOrdenados++) {//de esta manera no habrá problemas cuando se vuelva a emplear esta variable, por le hecho de que cuando se requiera emplear nuevamente, se reiniciará su valor xD y cuando se quiere emplear para hacer el ajuste en la posición del istado al ordenar los componentes, aún poserá su valor, lo cual es lo que se necesita xD :3 xD        
            datosImportantes = listadoAtributos.existToken(listadoAtributos, atributosEsperados[atributosOrdenados]);            
            
            if(datosImportantes[1]>=1){
                if(datosImportantes[1]>1){
                    manejadorErrores.establecerErrorHallado("atributoRepetido", atributosEsperados[atributosOrdenados],"");//Se establece el error por repitencia
                }
                
                atributosEspecificados[atributosOrdenados]= true;
                
                if(!atributosEsperados[atributosOrdenados].equals("clase")){                                          
                   listadoEspecificaciones.add(atributosEsperados[atributosOrdenados]+":"+Token.parseToken(listadoAtributos.get(datosImportantes[0])).darLexema());                   
                }//puesto que ya se ha agergado antes xD                             
                
                if(atributosEsperados[atributosOrdenados].equals("nombre") && (tipoSolicitud.equals("creacionFormulario") || tipoSolicitud.equals("modificacionFormulario"))){//supongo que se podrá trabajar igual que en el caso de la creación la modificación... xD
                    nombreArchivo += Token.parseToken(listadoAtributos.get(datosImportantes[0])).darLexema();
                }
                else if(atributosEsperados[atributosOrdenados].equals("usuarioCreacion")){//supongo que se podrá trabajar igual que en el caso de la creación la modificación... xD
                    nombreArchivo = Token.parseToken(listadoAtributos.get(datosImportantes[0])).darLexema() + nombreArchivo;
                }//puesto que si no se halló ese atributo en el listado, se generaría un error de tipo IndexOutOfBounds...               
            }else if(atributosEsperados[atributosOrdenados].contains("fecha") || atributosEsperados[atributosOrdenados].equals("usuarioCreacion")){//Se puede hacer esto puesto que el error se establece en el método que se encarga de hallar si se cumplió con la obligatoriedad... xD
                if(atributosEsperados[atributosOrdenados].contains("fecha")){//quiere decir que la primer ubic es -1, puesto que almacena las posi xD                    
                    //Se envía null, puesto que en realidad no estaba en el arch de entrada... auqnue podría addle el anterior último del listado, el cual se obtendría al pedir el Ultimo, puesto que en ese momento el listado no ha cambiado por el hecho de que está del lado izq y eso se exe antes que el der...
                    listadoAtributos.add(new Token(atributosEsperados[atributosOrdenados], java.time.LocalDate.now().toString(), Token.parseToken(listadoAtributos.get(listadoAtributos.size()-1)), 0, 0));//yo me acuerdo que este separa con guiones... sino pues habrá una excepción conrespecto a este símbolo cuando se add automáticamente que cuando se add manualmente, por lo tnato habrá que add este variacion en ele otro parser al revisar el arch de respuesta... xD                    
                }else if(atributosEsperados[atributosOrdenados].equals("usuarioCreacion")){//quiere decir que la primer ubic es -1, puesto que almacena las posi xD
                    //se busca en el arch aquel user que diga logueado y se add
                    //SINO, entonces error, porque tendría que haberse logueado antes... xD
                }
                
                 atributosEspecificados[atributosOrdenados]= true;                 
                 listadoEspecificaciones.add(atributosEsperados[atributosOrdenados]+": "+Token.parseToken(listadoAtributos.get(listadoAtributos.size()-1)).darLexema());
            }//Si no es así entonces no se hace nada, pues eso le corresponde  la revisión de obligatoriedad xD...                                          
        }//NOTA: Habrá que revisarse [cuando ya se tenga el archivo en el que se alamcenarán los usuarios, si el usuario creador corresponde al logueado... sino ERROR xD
        //ese if nenvolverá el if que add al ini el nombre del user? naa xD,, pues con un error, este archivo no se crea y por lo tanto no se envía a la sig fase que corresp a la webApp xD        
        
        revisarObligatoriedad(atributosEsperados, atributosEspecificados, tipoSolicitud);
    }      
    
    private void revisarObligatoriedad(String[] atributosEsperados, boolean atributosEspecificados[], String tipoObligatoriedad){
        int[] arregloObligatoriedad = manejadorAtributos.darImportancia(tipoObligatoriedad);
        int numeroAtributosMinimosAdd = 0;
        
        for (int atributoActual = 0; atributoActual < atributosEspecificados.length; atributoActual++) {
            if(arregloObligatoriedad[atributoActual]==1 && atributosEspecificados[atributoActual]==false){
                //Add el error al manejador, debido a que no se especificó un campo obligatorio...
                manejadorErrores.establecerErrorHallado("obligatorioFaltante",atributosEsperados[atributoActual], "");
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
        String[] atributosEseradosComponente = manejadorAtributos.darDemasObligatoriosComponente(claseComponente);        
        
        if(atributosEseradosComponente!=null){
            boolean[] atributosEspecificados = new boolean[atributosEseradosComponente.length];    
            int[] datosImportantes;
            
            try{
                for (int atributoActual = 0; atributoActual < atributosEseradosComponente.length; atributoActual++) {
                    datosImportantes = listadoAtributos.existToken(listadoAtributos, atributosEseradosComponente[atributoActual]);
                        if(datosImportantes[1]>=1){
                            if(datosImportantes[1]>1){
                                manejadorErrores.establecerErrorHallado("atributoRepetido", atributosEseradosComponente[atributoActual],"");//Se establece el error por repitencia
                            }
                            atributosEspecificados[atributoActual]= true;
                            listadoEspecificaciones.add(atributosEseradosComponente[atributoActual]+": "+Token.parseToken(listadoAtributos.get(datosImportantes[0])).darLexema());
                        }                                                                       
                }
            }catch(IndexOutOfBoundsException e){
                 manejadorErrores.establecerErrorHallado("atributosComponenteIsuficienes", claseComponente, "");
            }   
         
            if((listadoAtributos.size()-atributosOrdenados+1)>atributosEseradosComponente.length){
                manejadorErrores.establecerErrorHallado("demasiadosAtribComp", claseComponente, "");
            }
            
            if(!tipoSolicitud.equals("modificarComponentes")){
                revisarObligatoriedad(atributosEseradosComponente, atributosEspecificados, claseComponente);//es posible hacer esto puesto que el método de "revisarObligatoriedad" obtiene los datos del método que brinda los arregloes de enteros, también tiene declarados los caso que corresponden a los componentes xD :3 xD  
            }//De tal forma que se pueda hallar la repitencia pero no se muestre error por no venir algun atributo xD                        
        }                              
    }  
    
    private void escribirArchivo(){
        for (int lineaActual = 0; lineaActual < listadoEspecificaciones.size(); lineaActual++) {
            try {
                escritor.write(String.valueOf(listadoEspecificaciones.get(lineaActual)));
                escritor.newLine();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ha surgido un error al intentar\nescribir el archivo de reespuesta");
            }
        }
    }
    
    public void finalizarArchivo(){
        if(numeroSolicitudes>1){
            listadoEspecificaciones.add(0, "< ! ini _ respuestas >");
            listadoEspecificaciones.add("< ! fin _ respuestas >");            
        }
        
        if(!manejadorErrores.hubieronErrores()){
            try {            
            //mientras tanto lo comentaré, pues no es de mi utilidad xD
            //archivo.renameTo(new File(path+nombreArchTemp));//YO SUPONGO que pasa el arch original completo a la ubicación especificada y NO lo vuelve a crear, es decir no lo deja en blanco... claramente esto no scederá porque NO TIENE SENTIDO que se llame renameTO... xD y tampoco tendría sentido la forma en que se renombra si lo que hiciera este métooo es rehacer el arch desde 0... xD            
            escribirArchivo();
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
    
    public String darNombreArchivo(){
        return nombreArchivo;
    }
    
    public String darDireccionCompletaArchivo(){//están formados por un nombre de usuario y el nombre del frmulario detal forma que se pueda hacer una revsión en or
        return path+nombreArchivo;
    }      
    
    //deberás pensar como le harás cuando los bloques que se requeiren estań presenrtes pero no el orden que debería [como crear form y add componentes, que viniera antes lo de los compoentes y después del form :v, usarás lo que el inge dijo es decir descarar hasta encontrar lo que se busca o harás otra cosa, como buscarlo hasta hallarlo??? xD
}