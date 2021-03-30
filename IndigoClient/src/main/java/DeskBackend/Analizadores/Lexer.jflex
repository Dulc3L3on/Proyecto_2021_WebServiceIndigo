/*primer sección: imports*/
package DeskBackend.Analizadores;
import java_cup.runtime.*;
import static DeskBackend.Analizadores.Parser_IndigoSym.*;
import DeskBackend.Entidades.EntidadError;
import DeskBackend.Entidades.Token;
import DeskBackend.Entidades.Herramientas.ListaEnlazada;
import DeskBackend.Entidades.Manejadores.ManejadorErrores;


%%
/*segunda sección: settings*/
%class Lexer
%cup
%unicode
%line
%column
%public

//comilla = \"
letra = [a-zA-Z]
numero = [0-9]
finDeLinea = \r|\n|\r\n
tabulacion = [ \t\f]/*no olvides que ese espacio en blanco siempre debe ir, de lo contrario no funcionará para todas las poribles formas de un blankSpace*/
espacioEnBlanco = {finDeLinea} | {tabulacion}

/*NOTA: yo me acuerdo que para el graficador, al declarr una clase que tuviera más de un tipo de palabra reservada, me dada problema, de volverte a pasar lo mismo, colocarás a cada palabra por separado en el YYINITIAL... sino entonces en la misma linea del YYINITIAL colocas el yytext como nombre del tkn xD*/
BLOQUES = "CREDENCIALES" | "PARAMETROS" 
USER = "CREAR" | "USUARIO" |  "MODIFICAR" | "ELIMINAR" | "LOGIN"
COMPONENTES = "AGREGAR" | "COMPONENTE"
ALINEACION = "CENTRO" | "IZQUIERDA" | "DERECHA" | "JUSTIFICAR"
FORMULARIO = "NUEVO" | "FORMULARIO" 
CLASE_COMPONENTE = "CHECKBOX" | "RADIO" | "FICHERO" | "IMAGEN" | "COMBO" | "BOTON"
REQUERIDO = "SI" | "NO"

identificador =  (\$|_|-)({letra}|{numero}|_|-|\$)*

%{   
    //en este caso el Token anterior te será útil aquí para saber si debes enviar o no al otro estado, dicho lexema lo obtendrás del contenido del token
    //si el token es null, entonces no envíes aunque en realidad el que sucediera esto, indica que hay un error de sintazis xD
    boolean estaEnYYINITIAL = true;//para qué era esto??? :v :| xD aaah, es para saber si al estar en alfa#, se debe dar el # que le representa ó el # del token con el que coincide... xD
    boolean sonConsultas = false;//esto es para saber si se debe entrar a valores, cuando se encuentre una " y antes de ella : xD
    Token tokenAnterior;
    ManejadorErrores manejadorErrores = new ManejadorErrores();

    private Symbol simbolo(int tipo){//empleado por tema, alineación, requerido y clase, por el momento xD
        Token tokenActual = new Token("", yytext(), tokenAnterior, yyline+1, yycolumn+1);

        if(tokenAnterior!=null){
            tokenAnterior.establecerSiguiente(tokenActual);
        }

        tokenAnterior = tokenActual;//por esto, ya no es necesario tener el método añadirAnterior... pues se puede revisar directamente esta var de tipo Token... xD
        return new Symbol(tipo, tokenActual);
    }//Por el hecho de que aún no tenemos la clase symb xD

    private int darTipoAlfaNum(){
        if(estaEnYYINITIAL){
            if(yytext().equalsIgnoreCase("ini")){
                return 2;//el # de ini
            }
            else if(yytext().equalsIgnoreCase("solicitud")){
                return 3;//el # de solicitud
            }else if(yytext().equalsIgnoreCase("solicitudes")){
                return 4;//el # de solicitudes
            }else if(yytext().equalsIgnoreCase("fin")){//puesto que pueden haber más formas entonces por eso sí especifico xD
                return 5;//el # de fin
            }
        }
        return 60;//aquí el # que corresponde al alfa#
    }
    
    public ManejadorErrores darManejadorErrores(){
        return manejadorErrores;
    }
%}

%state VALORES

%%
/*tercer sección: lexer rules*/
//Recuerda que lo que se enviará como nombre del Symb, para todas las palabras reservadas será, el lexema mismo xD, solo para el caso de las insensitive, se tendrá que enviar el lexema en mayus xD
<YYINITIAL> {BLOQUES}                                                                {sonConsultas = false; return simbolo((yytext().equals("CREDENCIALES")?CREDENCIALES:PARAMETROS));}
<YYINITIAL> {USER}                                                                   {return simbolo((yytext().equals("CREAR")?CREAR:(yytext().equals("USUARIO")?USUARIO:(yytext().equals("MODIFICAR")?MODIFICAR:(yytext().equals("ELIMINAR")?ELIMINAR:LOGIN)))));}
<YYINITIAL> {COMPONENTES}                                                            {return simbolo(yytext().equals("AGREGAR")?AGREGAR:COMPONENTE);}
<YYINITIAL> "CONSULTAR" | "DATOS" | "SELECT" | "TO" | "FORM" | "WHERE" | "CONSULTA"  {return simbolo((yytext().equals("CONSULTAR")?CONSULTAR:(yytext().equals("DATOS")?DATOS:(yytext().equals("SELECT")?SELECT:yytext().equals("TO")?TO:(yytext().equals("FORM")?FORM:(yytext().equals("WHERE")?WHERE:CONSULTA))))));}
<YYINITIAL> "CONSULTAS"                                                              {sonConsultas = true; return simbolo(CONSULTAS);}
<YYINITIAL, VALORES> {ALINEACION}                                                    {System.out.println(yytext());
                                                                                      return simbolo(TIPOALINEACION);}/*Ahora que lo pienso y que tengo el manejador de respuestas, no veo necesario que se establezca un grupo, aquí en el lexer, lo meor es hacerlo en el manejador del arch de respuetas... xD*/
                                                                                          /*simbolo(yytext().equals("CENTRO")?CENTRO:(yytext().equals("IZQUIERDA")?IZQUIERDA:(yytext().equals("DERECHA")?DERECHA:JUSTIFICAR)));*/
<YYINITIAL> {FORMULARIO}                                                             {return simbolo(yytext().equals("NUEVO")?NUEVO:(yytext().equals("FORMULARIO")?FORMULARIO:TEMA));}
<YYINITIAL, VALORES> {CLASE_COMPONENTE}                                              {return simbolo(TIPOCLASE);}
                                                                                         /*yytext().equals("CAMPO")?CAMPO:(yytext().equals("TEXTO")?TEXTO:(yytext().equals("AREA")?AREA:(yytext().equals("CHECKBOX")?CHECKBOX:(yytext().equals("RADIO")?RADIO:(yytext().equals("FICHERO")?FICHERO:(yytext().equals("IMAGEN")?IMAGEN:(yytext().equals("COMBO")?COMBO:BOTON)))))))*/
<YYINITIAL, VALORES> {REQUERIDO}                                                     {System.out.println(yytext());
                                                                                      return simbolo(TIPOREQUERIDO);}
                                                                                         /*(yytext().equals("SI")?SI:NO)*/
<YYINITIAL> "AND" | "OR" | "NOT"                                                     {return simbolo(yytext().equals("AND")?AND:(yytext().equals("OR")?OR:NOT));}//AQUÍ SE deberá add Consultas al <>
<YYINITIAL, VALORES> "CAMPO" | "TEXTO" | "AREA"                                      {return simbolo(yytext().equals("CAMPO")?CAMPO:(yytext().equals("TEXTO")?TEXTO:AREA));}
<YYINITIAL> "ID" |"CLASE" | "INDICE" | "VISIBLE" | "ALINEACION" | "REQUERIDO" |"OPCIONES" | "FILAS" | "COLUMNAS" | "URL"    {return simbolo(yytext().equals("ID")?ID:(yytext().equals("CLASE")?CLASE:(yytext().equals("INDICE")?INDICE:(yytext().equals("VISIBLE")?VISIBLE:(yytext().equals("ALINEACION")?ALINEACION:(yytext().equals("REQUERIDO")?REQUERIDO:(yytext().equals("OPCIONES")?OPCIONES:(yytext().equals("FILAS")?FILAS:(yytext().equals("COLUMNAS")?COLUMNAS:URL)))))))));}
<YYINITIAL> "PASSWORD" | "FECHA" | "CREACION" | "ANTIGUO" | "MODIFICACION" | "TITULO" | "NOMBRE" | "TEMA"                   {return simbolo(yytext().equals("PASSWORD")?PASSWORD:(yytext().equals("FECHA")?FECHA:(yytext().equals("CREACION")?CREACION:(yytext().equals("ANTIGUO")?ANTIGUO:(yytext().equals("MODIFICACION")?MODIFICACION:(yytext().equals("TITULO")?TITULO:(yytext().equals("NOMBRE")?NOMBRE:TEMA)))))));}
<YYINITIAL, VALORES> "DARK" | "STANDAR" | "PINK"                                     {return simbolo(TIPOTEMA);}
                                                                                        /*(yytext().equals("DARK")?DARK:(yytext().equals("STANDAR")?STANDAR:PINK))*/
//insensitive, quizá ni siquiera hubiera sido necesario colocar este estado, puesto que por estar esta expre englobada por YYINITIAL entrará a la axn si es que ha > coin con los caracteres recopilados, de alfa# xD...
<YYINITIAL> "ini" | "solicitud" | "solicitudes" | "fin"                              {System.out.println(yytext());
                                                                                      return simbolo(yytext().equals("ini")?INI:(yytext().equals("solicitud")?SOLICITUD:(yytext().equals("solicitudes")?SOLICITUDES:FIN)));}
//simbolos; no sé si vaya a dar problema el hecho de que en algunas expresiones regulares, se encuentren símbolos que corresponde a palabras reservadas, es decir, no se si el Lex, va a tomar cada uno de esos simbolos como palraba reservada aunque se encuentre en medio,o en cualquier parte de la palrba que tb contiene los demás caracteres permitidos por la expre... si si, entonce te tocará pensar, por eso debes apresurarte!!!        
<YYINITIAL, VALORES>  "_"                                                            {return simbolo(GUIONBAJO);}              
<YYINITIAL> ":" | "->" | "," | "-"                                                   {System.out.println(yytext());
                                                                                      return simbolo(yytext().equals(":")?DOSPUNTOS:(yytext().equals("->")?ASIGNAR:(yytext().equals(",")?COMA:MENOS)));}
<YYINITIAL> "[" | "]" | "{" | "}"                                                    {System.out.println(yytext());
                                                                                      return simbolo(yytext().equals("[")?APERTURAANGULAR:(yytext().equals("]")?CIERREANGULAR:(yytext().equals("{")?APERTURALLAVE:CIERRELLAVE)));}//agergo los () solo porque al final de cuentas están permitidos, pero en este conjunto Lexer-Parser que se encarga de leer la entrada del user, no se requieren como una palabra reservada pues no se usa explicitamente en gramática alguna, solo en el e alfa#...

<YYINITIAL>{
    \"                                                     {estaEnYYINITIAL = false; 
                                                            if(tokenAnterior.darLexema().equals(":") && !sonConsultas)/*coloqué esto así, porque los únicos que están encerrados etrne " que tienen : como token anterior, son los parámetros o valores de especificación xD*/
                                                                yybegin(VALORES);
                                                            else 
                                                                return simbolo(COMILLA);}//no se si vaya a funcionar por la falta del ; en la llamada al método, sino entonces si crealo a parte y luego de definir la gramática ingresas en el parámetro de tipo, el # que le corresponde xD        
    "!" | ">" | "<" | "=" | "<>" | "<=" | ">="             {System.out.println(yytext());
                                                            if(sonConsultas == true)
                                                            yybegin(VALORES);
                                                            else
                                                            return simbolo(yytext().equals("!")?ADMIRACION:(yytext().equals(">")?MAYOR:(yytext().equals("<")?MENOR:(yytext().equals("=")?IGUAL:(yytext().equals("<>")?DIFERENTE:(yytext().equals("<=")?MENORIGUAL:MAYORIGUAL))))));} 
    //  {comilla}(~{comilla})         {}/*SERÁ EIMINADA XD, porque sé que la solución SÍ funciona xD*/

   <VALORES>{//NO necestio concatenar, porque sé que cuando en la gramática reciba más de un superalfa# es porque terminó su clasificación debido a un caracter no definido en su regla, lo más probable, un tipo de espacio en blanco xD
        \"                                                                                      {estaEnYYINITIAL = true; yybegin(YYINITIAL); }
        \|                                                                                      {return simbolo(SEPARADOR);}//yo asumo que solo se puede salir de otros estados al indicarle explícitamente esto con un método y no al emplear un return... [mira el ejemplo de flex, hay uno más o menos así xD]

        {numero}+("."{numero}+)?                                                                {return simbolo(NUMERO);}
        ({numero})({numero})({numero})({numero})-({numero})({numero})?-({numero})({numero})?    {return simbolo(FORMATOFECHA);}
        {identificador}                                                                         {System.out.println(yytext());
                                                                                                 return simbolo(IDENTIFICADOR);}
        ({letra}|{numero}|_|:|\$|%|#|\!|¡|&|\/|\(|\)|\+|-|\*|\?|¿|'|\{|\}|\.|,|=|\]|\[)+	        {System.out.println(yytext());/*se agregó a :, por el eje del aux, espero no de problemas... no debería xD pero por lo que observé, los estados internos tb pueden emplear las palrbas iniciales del estado que los engloba... o esos : que aparecieron fueron porque estaban "errados"?... fmmm xD*/
                                                                                                 return simbolo(darTipoAlfaNum());}//si no te da problemas incluyes el slaxh para que le user pueda ingresar los salos de linea corresp, que se entenderán como esto al leer el string concatenado en la axn de la gram correspondiente xD
          
        {espacioEnBlanco}                                                                       {/*se ignora, puesto que no afecta el desarrollo del análisis que YA FUNCIONA xD*/}

    }//introduje a este estado, por la existencia de los errores, que de entre ellos puede ser que aparezca en un lugar donde no corresponde un token aceptado por el alfabeto xD
    //este estado permite que se reduzcan la cantidad de palabras que el user no puede escribir igual a la de las reservadas xD, además me permitió ver que si hay espacios, entonces habrá maś de un tipo de expre reg definida, p ej. superalfa# xD
}

//ahora en alfa# será donde revises si alguna de sus entradas coinciden con palarbas reservadas, de tal forma que se envíe el # que representa a dicho lexema xD
[^]      {manejadorErrores.establecerErrorDeToken("lexico", (Token)simbolo(1).value);/*caracter no aceptado*/}
//vamos a dejarlo así por el momento, porque hay que probar como funciona, cuando se vea que no falla, add la prod de alfa# aquí para que "cache" a todos aquellos conjuntos de caracteres [palabras] que no forman parte de las palabras reservadas... xD
