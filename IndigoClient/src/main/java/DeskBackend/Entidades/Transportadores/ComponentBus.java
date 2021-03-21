/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Transportadores;

import java.util.LinkedList;

/**
 *
 * @author phily
 */
public class ComponentBus {
    private String tipoAccion = null;
    private String ID = null;
    private String nombreCampo = null;
    private String formulario = null;
    private String tipoComponente = null;
    private int indice = 0;
    private String texto = null;
    private String alineacion = null;
    private boolean esRequerido = true;
    private LinkedList<String> opciones = new LinkedList<>();//se convertirá a una lista, puesto que no se sabe cuantas opciones add el user...
    private int numeroFilas = 0;
    private int numeroColumnas = 0;
    private String URL = null;
    
    public void establecerTipoAccion(String laAccion){
        tipoAccion = laAccion;
    }
    
    public void establecerIDComponente(String elID){
        ID = elID;
    }
    
    public void establecerNombreCampo(String elNombreCampo){
        nombreCampo = elNombreCampo;
    }
    
    public void establecerIDFormulario(String elIDFormulario){
        formulario = elIDFormulario;
    }
    
    public void establecerTipoComponente(String elTipoComponente){
        tipoComponente = elTipoComponente;
    }
    
    public void establecerIndice(int elIndice){
        indice = elIndice;
    }
    
    public void establecerTexto(String elTexto){
        texto = elTexto;
    }
    
    public void establecerTipoAlineacion(String elTipoAlineacion){
        alineacion = elTipoAlineacion;
    }
    
    public void establecerTipoRequerimiento(boolean tipoRequerimiento){
        esRequerido = tipoRequerimiento;
    }
    
    public void establecerOpcion(String opcion){
        opciones.add(opcion);
    }
    
    public void establecerNumeroFilas(int elNumeroFilas){
        numeroFilas = elNumeroFilas;
    }
    
    public void establecerNumeroColumnas(int elNumeroColumnas){
        numeroColumnas = elNumeroColumnas;
    }
    
    public void establecerURL(String laURL){
        URL = laURL;
    }
    
    public String darTipoAccion(){
        return tipoAccion;
    }
    
    public String darID(){
        return ID;
    }
    
    public String darNombreCampo(){
        return nombreCampo;
    }
    
    public String darFormulario(){
        return formulario;
    }
    
    public String darTipoComponente(){
        return tipoComponente;
    }
    
    public int darIndice(){
        return indice;
    }
    
    public String darTexto(){
        return texto;
    }
    
    public String darAlineacion(){
        return alineacion;
    }
    
    public boolean darTipoRequerimiento(){
        return esRequerido;
    }
    
    public LinkedList<String> darOpciones(){
        return opciones;
    }
    
    public int darNumeroFilas(){
        return numeroFilas;
    }    
    
    public int darNumeroColumnas(){
        return numeroColumnas;
    }
    
    public String darURL(){
        return URL;
    }         
}
