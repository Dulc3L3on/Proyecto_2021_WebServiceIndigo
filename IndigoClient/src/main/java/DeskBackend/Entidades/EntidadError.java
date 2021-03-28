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
public class EntidadError {
    private Token token;
    private String tipoError;
    private String descripcion;
    
    public EntidadError(String elTipoError, String laDescripcion){//este será para cuando se halle un errro que no correpsonda a un token especificador por el cliente, sino a otra coasa, p.ej que no lo haya especificado xD
        tipoError = elTipoError;
        descripcion = laDescripcion;
    }
    
    public EntidadError(Token elToken, String elTipoError, String laDescripcion){//cuando el error sea en un token, es decir en el que se está trabajndo actualemnte xD
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
