/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeskBackend.Entidades.Herramientas;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;


/**
 *
 * @author phily
 */
public class EnchancerForEditor {//creo que dejará de existir, puesto que no tiene sentido estar llamando a c/u de los métodos de cada uno de los obj transportadores, por lo cual las instanciaciones se harán en un méodo creado en el CUP y la add a la lista que corresponde, que tb se encuentra en el arch CUP, se hará en la producción de la serie del tipo de operación que corresponde xD, no te preocupes del cómo se va a almacenar la info cuando hayan errores, puesto que no se ece nada si estos existen, con que no se provoquen excepciones está nice xD
    private int numeroLineasOcupadas=0;//esta var será la que se empleará para saber si el número de líneas existentes varío, ya sea umentando o disminuyendo...
    private JTextArea areaTexto;
    private JTextArea areaNumeracion;
    private JTextField barraPosicion;
    private int filaActual, columnaActual, posicionActualCursor;
    private DistribuidorBloques distribuidor;
    
    public EnchancerForEditor(JTextArea elAreaTexto, JTextArea elAreaNumeracion, JTextField laBarraPosicion){
        areaTexto = elAreaTexto;
        areaNumeracion = elAreaNumeracion;
        barraPosicion = laBarraPosicion;
        distribuidor = new DistribuidorBloques();
    }
    
    public void actualizarLinea(){
        if(areaTexto.getLineCount() != numeroLineasOcupadas){
            numeroLineasOcupadas = areaTexto.getLineCount();
            
            String texto="";
                        
            for (int lineaActual = 1; lineaActual <= numeroLineasOcupadas; lineaActual++) {
                texto+=String.valueOf(lineaActual)+"\n";                
            }            
            areaNumeracion.setText(texto);
        }               
    }//se encarga de agregaar y eliminar... xD            
    
    public void actualizarPosicion(){
        try {        
        posicionActualCursor = areaTexto.getCaretPosition();
        
        filaActual = areaTexto.getLineOfOffset(posicionActualCursor);        
        columnaActual = posicionActualCursor - areaTexto.getLineStartOffset(filaActual);
        
        barraPosicion.setText("Fila: "+ filaActual + "  Columna: " + columnaActual);        
        } catch (BadLocationException ex) {
            JOptionPane.showMessageDialog(areaTexto, "posición del cursor fuera del área");
        }
    }
    
    public void agregarBloque(String tipo){
        areaTexto.insert(distribuidor.darTipoBloque(tipo), areaTexto.getCaretPosition());                
    }
    
    
    
}
