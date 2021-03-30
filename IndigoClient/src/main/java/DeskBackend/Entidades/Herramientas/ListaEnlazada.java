/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Herramientas;

import DeskBackend.Entidades.Token;
import java.util.ArrayList;

/**
 *
 * @author phily
 */
public class ListaEnlazada <T> extends ArrayList{
    
    public int[] existToken(ListaEnlazada<Token> listadoTokens, String datoABuscar){//nos vamos a tardar más en buscar, pero así deberá hacerse... :( xD
        int datosImportantes[] = {-1, 0};//[0] -> la posición, [1]-> el # apariciones
        
        for (int datoActual = 0; datoActual < this.size(); datoActual++) {
            Token token = (Token)listadoTokens.get(datoActual);
            
            if(token.darNombreDelToken().equals(datoABuscar)){
                datosImportantes[1]++;
                
                if(datosImportantes[1]==1){//esto quiere decir que solo se tomará en uenta el valor de la prier opción... aunque creo que era más fácil ir asignando la posición ás actual... creo xD
                    datosImportantes[0] = datoActual;
                }
            }           
        }
        return datosImportantes;
    }   
        
    public Object getLastItem(){
        return this.get(this.size()-1);
    }
    
}
