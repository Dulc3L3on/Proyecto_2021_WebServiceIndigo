/*primer sección: imports*/
package DeskBackend.Analizadores;
import java_cup.runtime.*;
import static DeskBackend.Analizadores.ParserResponseSym.*;
import DeskBackend.Entidades.Token;

%%
/*segunda sección: settings*/
%class LexerResponse
%cup
%unicode
%line
%column
%public

letra = [a-zA-Z]
numero = [0-9]
finDeLinea = \r|\n|\r\n

ACCIONES = "creacionUsuario"| "modificacionUsuario" | "eliminacionUsuario" | "loginUsuario" | "creacionFormulario" | "eliminacionFormulario" | "modificacionFormulario" | "agregarComponentes" | "eliminarComponente" | "modificarComponentes"

%{   
    //en este caso el Token anterior te será útil aquí para saber si debes enviar o no al otro estado, dicho lexema lo obtendrás del contenido del token
    //si el token es null, entonces no envíes aunque en realidad el que sucediera esto, indica que hay un error de sintazis xD
    boolean estaEnYYINITIAL = true;//para qué era esto??? :v :| xD aaah, es para saber si al estar en alfa#, se debe dar el # que le representa ó el # del token con el que coincide... xD
    boolean sonConsultas = false;//esto es para saber si se debe entrar a valores, cuando se encuentre una " y antes de ella : xD
    Token tokenAnterior; 

    private Symbol simbolo(int tipo){//empleado por tema, alineación, requerido y clase, por el momento xD
        Token tokenActual = new Token("", yytext(), tokenAnterior, yyline+1, yycolumn+1);

        if(tokenAnterior!=null){
            tokenAnterior.establecerSiguiente(tokenActual);
        }

        tokenAnterior = tokenActual;//por esto, ya no es necesario tener el método añadirAnterior... pues se puede revisar directamente esta var de tipo Token... xD
        return new Symbol(tipo, tokenActual);
    }//Por el hecho de que aún no tenemos la clase symb xD  
%}

%state VALORES

%%
/*tercer sección: lexer rules*/
//Recuerda que lo que se enviará como nombre del Symb, para todas las palabras reservadas será, el lexema mismo xD, solo para el caso de las insensitive, se tendrá que enviar el lexema en mayus xD
<YYINITIAL> {ACCIONES}                                                               {return simbolo(yytext().equals("creacionUsuario")?CREACIONUSER:(yytext().equals("modificacionUsuario")?MODIFICACIONUSER:(yytext().equals("eliminacionUsuario")?ELIMINACIONUSER:(yytext().equals("loginUsuario")?LOGINUSER:(yytext().equals("creacionFormulario")?CREACIONFORM:(yytext().equals("eliminacionFormulario")?ELIMINACIONFORM:(yytext().equals("modificacionFormulario")?MODIFICACIONFORM:(yytext().equals("agregarComponentes")?ADDCOMPS:(yytext().equals("eliminarComponente")?ELICOMP:MODIFCOMP)))))))));}
/*<YYINITIAL> "CONSULTAR" | "DATOS" | "SELECT" | "TO" | "FORM" | "WHERE" | "CONSULTA"  {return simbolo((yytext().equals("CONSULTAR")?CONSULTAR:(yytext().equals("DATOS")?DATOS:(yytext().equals("SELECT")?SELECT:yytext().equals("TO")?TO:(yytext().equals("FORM")?FORM:(yytext().equals("WHERE")?WHERE:CONSULTA))))));}*/
/*<YYINITIAL> "CONSULTAS"                                                              {sonConsultas = true; return simbolo(CONSULTAS);}*/
/*<YYINITIAL> "AND" | "OR" | "NOT"                                                     {return simbolo(yytext().equals("AND")?AND:(yytext().equals("OR")?OR:NOT));}//AQUÍ SE deberá add Consultas al <>*/
<YYINITIAL> "CAMPO" | "TEXTO"                                                        {return simbolo(yytext().equals("CAMPO")?CAMPO:TEXTO);}
<YYINITIAL> "ID" |"CLASE" | "INDICE" | "VISIBLE" | "ALINEACION" | "REQUERIDO" |"OPCIONES" | "FILAS" | "COLUMNAS" | "URL"    {return simbolo(yytext().equals("ID")?ID:(yytext().equals("CLASE")?CLASE:(yytext().equals("INDICE")?INDICE:(yytext().equals("VISIBLE")?VISIBLE:(yytext().equals("ALINEACION")?ALINEACION:(yytext().equals("REQUERIDO")?REQUERIDO:(yytext().equals("OPCIONES")?OPCIONES:(yytext().equals("FILAS")?FILAS:(yytext().equals("COLUMNAS")?COLUMNAS:URL)))))))));}
<YYINITIAL> "PASSWORD" | "FECHA" | "CREACION" | "ANTIGUO" | "MODIFICACION" | "TITULO" | "NOMBRE" | "TEMA"                   {return simbolo(yytext().equals("PASSWORD")?PASSWORD:(yytext().equals("FECHA")?FECHA:(yytext().equals("CREACION")?CREACION:(yytext().equals("ANTIGUO")?ANTIGUO:(yytext().equals("MODIFICACION")?MODIFICACION:(yytext().equals("TITULO")?TITULO:(yytext().equals("NOMBRE")?NOMBRE:TEMA)))))));}
<YYINITIAL> "ini" | "respuesta" | "respuetas" | "fin"                                {System.out.println(yytext());
                                                                                      return simbolo(yytext().equals("ini")?INI:(yytext().equals("respuesta")?RESPUESTA:(yytext().equals("RESPUESTAS")?RESPUESTAS:FIN)));}
//simbolos; no sé si vaya a dar problema el hecho de que en algunas expresiones regulares, se encuentren símbolos que corresponde a palabras reservadas, es decir, no se si el Lex, va a tomar cada uno de esos simbolos como palraba reservada aunque se encuentre en medio,o en cualquier parte de la palrba que tb contiene los demás caracteres permitidos por la expre... si si, entonce te tocará pensar, por eso debes apresurarte!!!        
<YYINITIAL>  "_"                                                                     {return simbolo(GUIONBAJO);}              
/*<YYINITIAL> "->" | "," | "-"                                                   {System.out.println(yytext());
                                                                                     return simbolo(yytext().equals("->")?ASIGNAR:(yytext().equals(",")?COMA:MENOS));}*/
/*<YYINITIAL> "[" | "]" | "{" | "}" | \"                                                               {System.out.println(yytext());
                                                                                     return simbolo(yytext().equals("[")?APERTURAANGULAR:(yytext().equals("]")?CIERREANGULAR:(yytext().equals("{")?APERTURALLAVE:(yytext().equals("}")?CIERRELLAVE:COMILLA))));}*/
//Creo que tb estso angulares se eli, a menos que lo que corresponde a lo de las consultas lo emplee aún xd

<YYINITIAL>{
    :                                                     {estaEnYYINITIAL = false; 
                                                            if(tokenAnterior.darLexema().equals(":") && !sonConsultas)/*coloqué esto así, porque los únicos que están encerrados etrne " que tienen : como token anterior, son los parámetros o valores de especificación xD*/
                                                                yybegin(VALORES);
                                                            else 
                                                                return simbolo(DOSPUNTOS);}//no se si vaya a funcionar por la falta del ; en la llamada al método, sino entonces si crealo a parte y luego de definir la gramática ingresas en el parámetro de tipo, el # que le corresponde xD        
    /*NO se si deba colocar estos signos de comp, puesto que cuando aparezcan estarán del LadoDer y todos estos no deben evaluarse, porque ya se hizo en el archivo de entrada anterior, sino solamente deben catalogarse como alfa# xD*/                                                                
    "!" | ">" | "<"/* | "=" | "<>" | "<=" | ">=" */            {System.out.println(yytext());
                                                            if(sonConsultas == true)
                                                            yybegin(VALORES);
                                                            else
                                                            return simbolo(yytext().equals("!")?ADMIRACION:(yytext().equals(">")?MAYOR:MENOR));
                                                           /* return simbolo(yytext().equals("!")?ADMIRACION:(yytext().equals(">")?MAYOR:(yytext().equals("<")?MENOR:(yytext().equals("=")?IGUAL:(yytext().equals("<>")?DIFERENTE:(yytext().equals("<=")?MENORIGUAL:MAYORIGUAL))))));*/} 
    //  {comilla}(~{comilla})         {}/*SERÁ EIMINADA XD, porque sé que la solución SÍ funciona xD*/

   <VALORES>{//NO necestio concatenar, porque sé que cuando en la gramática reciba más de un superalfa# es porque terminó su clasificación debido a un caracter no definido en su regla, lo más probable, un tipo de espacio en blanco xD
        {finDeLinea}                                                                                      {estaEnYYINITIAL = true; yybegin(YYINITIAL); }       
  
        ({letra}|{numero}|\||_|:|\$|%|#|\!|¡|&|\/|\(|\)|\+|-|\*|\?|¿|'|\{|\}|\.|,|=|\]|\[)+	             {System.out.println(yytext());
                                                                                                         return simbolo(ALFANUMERICO);}//si no te da problemas incluyes el slaxh para que le user pueda ingresar los salos de linea corresp, que se entenderán como esto al leer el string concatenado en la axn de la gram correspondiente xD      
        /*no incluyo las " en alfa#, pues no podrán aparecer aquí ya que en el analizador de la entrada, estas eran los indicadores de que debía netrar o salir del etado valore, por lo cual estas no son incluidas al momneto de envir loa atributos que son los que se escribireon en el archivo... xD*/                                                                                                         
    }
}

//ahora en alfa# será donde revises si alguna de sus entradas coinciden con palarbas reservadas, de tal forma que se envíe el # que representa a dicho lexema xD
[^]      {/*se ignora porque aquí en este archivo NO HAY ERRORES xD, porque yo lo mandé a hacer por lo cual todo lo que ahí aparece, cumple con las reglas xD*/}