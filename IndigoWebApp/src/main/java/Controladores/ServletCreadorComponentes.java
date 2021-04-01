/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import javax.servlet.annotation.WebServlet;

import java.io.IOException;
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
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            //se lee el archivo a partir del encabezado que se envió desde el enlazador
            
            
            //se analiza léxica y sintácticamente
            
            //se solicita [con un request xD] al JSP lector de los componentes
            response.sendRedirect("IndigoForms.jsp");//por el momento se está empleando el response, pero no será este el que se utilice, puseto que debe enviarse el arr que aquí se obtuevo
            //Aquí no te preocupes por los errores de los comp, porque esos ya fueron tratados y ateapados en fases anteriores... xD
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar redirigir\nal formulario creado");
        }
    }
    
    
}
