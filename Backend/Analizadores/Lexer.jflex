/*primer sección: imports*/
package Backend.Analizadores;
import Backend.Entidades.Token;

%%
/*segunda sección: settings*/
%class Lexer
%standalone/*por el momento xD*/
%unicode
%line
%column

comilla = \"
letra = [a-zA-Z]
numero = [0-9]

/*NOTA: yo me acuerdo que para el graficador, al declarr una clase que tuviera más de un tipo de palabra reservada, me dada problema, de volverte a pasar lo mismo, colocarás a cada palabra por separado en el YYINITIAL... sino entonces en la misma linea del YYINITIAL colocas el yytext como nombre del tkn xD*/
BLOQUES = "CREDENCIALES" | "PARAMETROS" 
USER = "CREAR" | "USUARIO" |  "MODIFICAR" | "ELIMINAR" | "LOGIN"
COMPONENTES = "AGREGAR" | "COMPONENTE"
ALINEACION = "CENTRO" | "IZQUIERDA" | "DERECHA" | "JUSTIFICAR"
FORMULARIO = "NUEVO" | "FORMULARIO" 
CLASE_COMPONENTE = "CAMPO" | "TEXTO" | "AREA" | "CHECKBOX" | "RADIO" | "FICHERO" | "IMAGEN" | "COMBO" | "BOTON"
REQUERIDO = "SI" | "NO"

identificador =  (\$|_|-)({letra}|{numero}|_|-|\$)*

%{   
    //en este caso el Token anterior te será útil aquí para saber si debes enviar o no al otro estado, dicho lexema lo obtendrás del contenido del token
    //si el token es null, entonces no envíes aunque en realidad el que sucediera esto, indica que hay un error de sintazis xD
    boolean estaEnYYINITIAL = true;//para qué era esto??? :v :| xD aaah, es para saber si al estar en alfa#, se debe dar el # que le representa ó el # del token con el que coincide... xD
    boolean sonConsultas = false;//esto es para saber si se debe entrar a valores, cuando se encuentre una " y antes de ella : xD
    Token tokenAnterior;

/*    private Symbol simbolo(int tipo) {
        return simbolo(tipo, "");//puesto que no veo que tenga razón de ser xD
    }

    private Symbol simbolo(int tipo, String agrupacionToken){//empleado por tema, alineación, requerido y clase, por el momento xD
        Token tokenActual = new Token(nombreToken, yytext(), tokenAnterior, yyline+1, yycolumn+1);

        if(tokenAnterior!=null){
            tokenAnterior.establecerSiguiente(tokenActual);
        }

        tokenAnterior = tokenActual;//por esto, ya no es necesario tener el método añadirAnterior... pues se puede revisar directamente esta var de tipo Token... xD
        return new Symbol(tipo, tokenActual);
    }*///Por el hecho de que aún no tenemos la clase symb xD

    private int darTipoAlfaNum(){
        if(estaEnYYINITIAL){
            if(yytext().equalsIgnoreCase("ini")){
                return 0;//el # de ini
            }
            else if(yytext().equalsIgnoreCase("solicitud")){
                return 0;//el # de solicitud
            }else if(yytext().equalsIgnoreCase("solicitudes")){
                return 0;//el # de solicitudes
            }else if(yytext().equalsIgnoreCase("fin")){//puesto que pueden haber más formas entonces por eso sí especifico xD
                return 0;//el # de fin
            }
        }
        return 0;//aquí el # que corresponde al alfa#
    }
    
%}

%state VALORES

%%
/*tercer sección: lexer rules*/
//Recuerda que lo que se enviará como nombre del Symb, para todas las palabras reservadas será, el lexema mismo xD, solo para el caso de las insensitive, se tendrá que enviar el lexema en mayus xD
<YYINITIAL> {BLOQUES}                                     {sonConsultas = false; /*return simbolo((yytext().equals("CREDENCIALES")?CREDENCIALES:PARAMETROS));*/}
<YYINITIAL> {USER}                                        {/*return simbolo((yytext().equals("CREAR")?CREAR:(yytext().equals("USUARIO")?USUARIO:(yytext().equals("MODIFICAR")?MODIFICAR:(yytext().equals("ELIMINAR")?ELIMINAR:LOGIN)))));*/}
<YYINITIAL> {COMPONENTES}                                 {/*return simbolo(yytext().equals("AGREGAR")?AGREGAR:COMPONENTE);*/}
<YYINITIAL> "CONSULTAR" | "DATOS" | "SELECT" | "TO" | "FORM" | "WHERE" | "CONSULTA"  {/*return simbolo((yytext().equals("CONSULTAR")?CONSULTAR:(yytext().equals("DATOS")?DATOS:(yytext().equals("SELECT")?SELECT:yytext().equals("TO")?TO:(yytext().equals("FORM")?FORM:(yytext().equals("WHERE")?WHERE:CONSULTA))))));*/}
<YYINITIAL> "CONSULTAS"                                   {sonConsultas = true; /*return simbolo(CONSULTAS);*/}
<YYINITIAL, VALORES> {ALINEACION}                         {/*return simbolo(TIPOALINEACION, "Tipo Alineacion");*/}
                                                            /*simbolo(yytext().equals("CENTRO")?CENTRO:(yytext().equals("IZQUIERDA")?IZQUIERDA:(yytext().equals("DERECHA")?DERECHA:JUSTIFICAR)))*/
<YYINITIAL> {FORMULARIO}                                  {/*return simbolo(yytext().equals("NUEVO")?NUEVO:(yytext().equals("FORMULARIO")?FORMULARIO:TEMA));*/}
<YYINITIAL, VALORES> {CLASE_COMPONENTE}                   {/*return simbolo(TIPOCLASE, "Tipo Clase";*/}
                                                           /*yytext().equals("CAMPO")?CAMPO:(yytext().equals("TEXTO")?TEXTO:(yytext().equals("AREA")?AREA:(yytext().equals("CHECKBOX")?CHECKBOX:(yytext().equals("RADIO")?RADIO:(yytext().equals("FICHERO")?FICHERO:(yytext().equals("IMAGEN")?IMAGEN:(yytext().equals("COMBO")?COMBO:BOTON)))))))*/
<YYINITIAL, VALORES> {REQUERIDO}                          {/*return simbolo(TIPOREQUERIDO, "Requerimiento");*/}
                                                           /*(yytext().equals("SI")?SI:NO)*/
<YYINITIAL> "AND" | "OR" | "NOT"                          {/*return simbolo(yytext().equals("AND")?AND:(yytext().equals("OR")?OR:NOT));*/}//AQUÍ SE deberá add Consultas al <>
<YYINITIAL> "ID" | "CLASE" | "INDICE" | "VISIBLE" | "ALINEACION" | "REQUERIDO" |"OPCIONES" | "FILAS" | "COLUMNAS" | "URL"    {/*return simbolo(yytext().equals("ID")?ID:(yytext().equals("CLASE")?CLASE:(yytext().equals("INDICE")?INDICE:(yytext().equals("VISIBLE")?VISIBLE:(yytext().equals("ALINEACION")?ALINEACION:(yytext().equals("REQUERDIDO")?REQUERIDO:(yytext().equals("OPCIONES")?OPCIONES:(yytext().equals("FILAS")?FILAS:(yytext().equals("COLUMNAS")?COLUMNAS:URL)))))))));*/}
<YYINITIAL> "PASSWORD" | "FECHA" | "CREACION" | "ANTIGUO" | "MODIFICACION" | "TITULO" | "NOMBRE" | "TEMA" {/*return simbolo(yytext().equals("PASSWORD")?PASSWORD:(yytext().equals("FECHA")?FECHA:(yytext().equals("CREACION")?CREACION:(yytext().equals("ANTIGUO")?ANTIGUO:(yytext().equals("MODIFICACION")?MODIFICACION:(yytext().equals("TITULO")?TITULO:(yytext().equals("NOMBRE")?NOMBRE:TEMA)))))));*/}
<YYINITIAL, VALORES> "DARK" | "STANDAR" | "PINK"           {/*return simbolo(TIPOTEMA, "Tipo Tema");*/}
                                                            /*(yytext().equals("DARK")?DARK:(yytext().equals("STANDAR")?STANDAR:PINK))*/
//insensitive, quizá ni siquiera hubiera sido necesario colocar este estado, puesto que por estar esta expre englobada por YYINITIAL entrará a la axn si es que ha > coin con los caracteres recopilados, de alfa# xD...
<YYINITIAL> "ini" | "solicitud" | "solicitudes" | "fin"    {/*return simbolo(yytext().equals("ini")?INI:(yytext().equals("solicitud")?SOLICITUD:(yytext().equals("solicitudes")?SOLICITUDES:FIN)));*/}
//simbolos; no sé si vaya a dar problema el hecho de que en algunas expresiones regulares, se encuentren símbolos que corresponde a palabras reservadas, es decir, no se si el Lex, va a tomar cada uno de esos simbolos como palraba reservada aunque se encuentre en medio,o en cualquier parte de la palrba que tb contiene los demás caracteres permitidos por la expre... si si, entonce te tocará pensar, por eso debes apresurarte!!!        
<YYINITIAL>  "_"  | ":" | "->" | "," | "-"                 {/*return simbolo(yytext().equals("_")?GUIONBAJO:(yytext().equals(":")?DOSPUNTOS:(yytext().equals("->")?ASIGNAR:(yytext().equals(",")?COMA:MENOS))));*/}
<YYINITIAL> "[" | "]" | "{" | "}" | "(" | ")"              {/*return simbolo(yytext().equals("[")?APERTURAANGULAR:(yytext().equals("]")?CIERREANGULAR:(yytext().equals("{")?APERTURALLAVE:(yytext().equals("}")?CIERRELLAVE:(yytext().equals("(")?APERTURAPARENTESIS:CIERREPARENTESIS)))));*/}

<YYINITIAL>{
    \"                                                     {estaEnYYINITIAL = false;/*(tokenAnterior.darLexema().equals(":") && !sonConsultas)?*/yybegin(VALORES)/*:return simbolo(COMILLA)*/;}//no se si vaya a funcionar por la falta del ; en la llamada al método, sino entonces si crealo a parte y luego de definir la gramática ingresas en el parámetro de tipo, el # que le corresponde xD        
    "!" | ">" | "<" | "=" | "<>" | "<=" | ">="             {(sonConsultas = true)?yybegin(VALORES):/*return simbolo(yytext().equals("!")?ADMIRACION:(yytext().equals(">")?MAYOR:(yytext().equals("<")?MENOR:(yytext().equals("=")?IGUAL:(yytext().equals("<>")?DIFERENTE:(yytext().equals("<=")?MENORIGUAL:MAYORIGUAL))))))*/;} 
    //  {comilla}(~{comilla})         {}/*SERÁ EIMINADA XD, porque sé que la solución SÍ funciona xD*/

   <VALORES>{//NO necestio concatenar, porque sé que cuando en la gramática reciba más de un superalfa# es porque terminó su clasificación debido a un caracter no definido en su regla, lo más probable, un tipo de espacio en blanco xD
        \"                                                                                      {estaEnYYINITIAL = true; yybegin(YYINITIAL); }
        \|                                                                                      {/*return simbolo(SEPARADOR);*/}//yo asumo que solo se puede salir de otros estados al indicarle explícitamente esto con un método y no al emplear un return... [mira el ejemplo de flex, hay uno más o menos así xD]

        {numero}+                                                                               {/*return simbolo(NUMERO);*/}
        ({numero})({numero})({numero})({numero})-({numero})({numero})?-({numero})({numero})?    {/*return simbolo(FORMATOFECHA);*/}
        {identificador}                                                                         {/*return simbolo(IDENTIFICADOR);*/}
        ({letra}|{numero}|_|\$|%|#|\!|¡|&|\/|\(|\)|\+|-|\*|\?|¿|'|\{|\}|\.|,|=|\]|\[)+	        {/*return simbolo(darTipoAlfaNum());*/}//si no te da problemas incluyes el slaxh para que le user pueda ingresar los salos de linea corresp, que se entenderán como esto al leer el string concatenado en la axn de la gram correspondiente xD
        
    }//introduje a este estado, por la existencia de los errores, que de entre ellos puede ser que aparezca en un lugar donde no corresponde un token aceptado por el alfabeto xD
    //este estado permite que se reduzcan la cantidad de palabras que el user no puede escribir igual a la de las reservadas xD, además me permitió ver que si hay espacios, entonces habrá maś de un tipo de expre reg definida, p ej. superalfa# xD
}

//ahora en alfa# será donde revises si alguna de sus entradas coinciden con palarbas reservadas, de tal forma que se envíe el # que representa a dicho lexema xD
[^]      {/*caracter no aceptado*/}
