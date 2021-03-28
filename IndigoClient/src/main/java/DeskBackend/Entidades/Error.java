/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades;

/**
 *
 * @author phily
 */
public class Error {
    private Token token;
    private String tipoError;
    private String descripcion;
    
    public Error(Token elToken, String elTipoError, String laDescripcion){
        token = elToken;
        tipoError = elTipoError;
        descripcion = laDescripcion;
    }
    
    public String darTipoError(){
        return tipoError;
    }
    
    public String darDescripcion(){
        return descripcion;
    }
    
    public String darNombreTokenErrado(){
        return token.darNombreDelToken();
    }
    
    public String darLexemaErrado(){
        return token.darLexema();
    }
    
    public String darAnterior(){
        return token.darLexemaAnterior();
    }
    
    public String darSiguiente(){
        return token.darLexemaSiguiente();
    }
    
    public int darFila(){
        return token.darFila();
    }
    
    public int darColumna(){
        return token.darColumna();
    }
}
