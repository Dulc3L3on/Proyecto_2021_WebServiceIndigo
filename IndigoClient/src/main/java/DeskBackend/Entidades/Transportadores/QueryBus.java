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
public class QueryBus {
    private LinkedList<String> nombreCampos = new LinkedList<>();//Se pasará a una lista puesto que no se sabe cuántos serán los que add el user...
    private LinkedList<String> listadoCondiciones = new LinkedList<>();    
    private LinkedList<String> listadoSimbolosLogicos = new LinkedList<>();
    
    public void establecerNombreCampo(String elNombre){
        nombreCampos.add(elNombre);
    }
    
    
    public void establecerCondicion(String identificador, String operadorRelacional, String valorComparacion){//tendrá que ser la triada completa o hacer que en un arreglo [o una lista xD] se reciba cada una de las partes que luegose concatenarán para addlas aquí como una sola colsa, lo cual es lo que en realidad sucede xD
        String comparacionIndividual = "";//aquí no está considerado el NOT qu epuede tnere la primer expresión, deplano que se deberá elimnar la producción de negación que solo la primer comparación pueda tener, eso quiere decir que tendriás que hacer la revisión de que para la primer comparaciń est osolo pueda ser NOT y no lo demás... para los demás símbolos de comparación tendríasque almacenarlos en otro listado para luego asignarlos a la pareja de comparaciones que corresponde, puesto que a habrás resultao como hacer para recibirlos en el orden en que los esperas...
            
        comparacionIndividual +=  comparacionIndividual+ identificador + operadorRelacional + valorComparacion;
        listadoCondiciones.add(comparacionIndividual);
    }
    
    public String darNOmbresCampos(){//Ahí decides si usarás a las listas creads por tí o una que proporciona JAVA, yo diría que si usar las tuyas lo uses par todo, aunque si podría reultar útil usar las de Java para una cosa y las tuyas para otra, piensalo xD
        String listadoNombres = "";
        
        for (int nombreActual = 0; nombreActual < nombreCampos.size(); nombreActual++) {
            listadoNombres += nombreCampos.get(nombreActual);
        }
        
        return listadoNombres;
    }//de tal forma que este método ahorre el tener que hacer en el lugar donde se necesitan los nombres, el recorrido de la lista para concatenar el contenido...
    
    public String darCondiciones(){//lo mismo que el método de arribita xD
        String condicionCompleta = "";
        //hago esta add puesto que sé que cuando legue aquí es porque ya se terminó de add todas las condi y todo lo que se almacenó está bien xD
        listadoSimbolosLogicos.add("");//de tal forma que no se tenga problemas con la ultima condición y solo se necesite de un for, pues hacer esto provoca que auqnue sea una sola comparación, no se tenga problemas al concat el simbolo lógico, puesto que no habrá xD y no se provocará un indexOutBounds puesto que tendrá el mismo tamño que la lista que almacena las condiciones xD    
                
        for(int condicionActual = 0; condicionActual < listadoCondiciones.size(); condicionActual++) {
            condicionCompleta += listadoCondiciones.get(condicionActual) + listadoSimbolosLogicos.get(condicionActual);//recuerda que este método get tiene lims >=0 && <size... xD
        }        
        return condicionCompleta;
    }
}
