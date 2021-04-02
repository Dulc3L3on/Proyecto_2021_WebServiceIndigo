/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;


import Analizadores.LexerResponse;
import Analizadores.ParserResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
@WebServlet("/creadorComponentes")
public class ServletCreadorComponentes extends HttpServlet{
    private ParserResponse parser;
    private LexerResponse lexer;
    public static String pathArchivoAAnalizar;
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
       //se abre el archivo de registros de los formularios
       //se obtiene la lista
       //se obtiene la ultima posicion
       //Se obtiene el contenido del atrib path del nodo actual
       //Se llama al método analizar
       analizarFormulario(response);
    }      
    
    private void analizarFormulario(HttpServletResponse response){
        try {
            //cuando el  servlet sea empleado porque acabn de crer un FORM, el nombre lo obtendrá a partir del archivo alamcenador...
            //en cambio cuando el servlet sea iniciado por haber seleccionado un form del listado, el nombre lo obtendrá a partir de al var requets que le nvió el JSP... al doPost de este mismo servlet xD
            File archivo = new File(pathArchivoAAnalizar);
            FileReader lector = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(lector);
            
            lexer = new LexerResponse(buffer);
            parser = new ParserResponse(lexer);
            parser.parse();
            //se analiza léxica y sintácticamente
            
            //se solicita [con un request xD] al JSP lector de los componentes
            response.sendRedirect("IndigoForms.jsp");//por el momento se está empleando el response, pero no será este el que se utilice, puseto que debe enviarse el arr que aquí se obtuevo
            //Aquí no te preocupes por los errores de los comp, porque esos ya fueron tratados y ateapados en fases anteriores... xD
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar redirigir\nal formulario creado");
        } catch (Exception ex) {
            System.out.println("Error al intentar ANALIZAR la respuesta\n");
        }
    }
    
     @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
       //se obtiene el path que se almacenó en el request
       //Se llama al método analizar
       analizarFormulario(response);
    }   
    
}
