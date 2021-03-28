package DeskBackend.Entidades;

import java.io.Serializable;

public class Token implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String nombreToken;
    private String lexema;
    private final Token tokenAnterior;//no es necesario add null como default porque de todos modos si es el primero lo que termianrá conteniendo es nada "" xD
    private final int fila;
    private final int columna;
    private Token tokenSiguiente;

    public Token(String elNombreToken, String elLexema, Token elAnterior, int laFila, int laColumna){
        nombreToken = elNombreToken;
        lexema = elLexema;
        tokenAnterior = elAnterior;//dejalo así, pero de todos modos hay que encargarse de eso en el .jflex xD
        fila = laFila;
        columna = laColumna;
    }

    public static Token parseToken(Object objeto){
        Token token = (Token) objeto;
        return token;
    }

    public void establecerSiguiente(Token elSiguiente){
        tokenSiguiente = elSiguiente;
    }
    
    public void establecerNombreDelToken(String elNombre){
        nombreToken = elNombre;
    }

    public void reestablecerLexema(String nuevoLexema){
        lexema = nuevoLexema;
    }
    
    public String darNombreDelToken(){
        return nombreToken;
    }    

    public String darLexema(){
        return lexema;
    }

    public String darLexemaAnterior(){
        if(tokenAnterior!=null){
            return tokenAnterior.lexema;
        }
        return "";
    }

    public int darFila(){
        return fila;
    }

    public int darColumna(){
        return columna;
    }

    public String darLexemaSiguiente(){//por el var defaul me ahorro colocar si es null entonces que muestre "", y pues como después no sería posible que se volviera nulo, entonces no es nec colocar el if... xD
        if(tokenSiguiente!=null){
            return tokenSiguiente.darLexema();
        }
        return "";
    }

    public String darNombreAnterior(){
        if(tokenAnterior!=null){
            return tokenAnterior.nombreToken;
        }
        return "";
    }
}