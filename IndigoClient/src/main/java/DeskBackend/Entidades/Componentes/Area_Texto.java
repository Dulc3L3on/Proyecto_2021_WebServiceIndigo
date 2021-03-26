/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Componentes;

/**
 *
 * @author phily
 */
public class Area_Texto extends Campo_Texto{
    private int filas;
    private int columnas;        
    //al parecer SÍ se heredan los miembros static :v yo sabí que no xD
    
    public void establecerNumeroFilas(int numeroFilas){
        filas = numeroFilas;
    }
    
    public void establecerNumeroColumnas(int numeroColumnas){
        columnas = numeroColumnas;
    }
    
    public int darNumeroFilas(){
        return filas;
    }
    
    public int darNumeroColumnas(){
        return columnas;
    }
}
