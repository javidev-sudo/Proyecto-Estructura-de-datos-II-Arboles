/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package excepciones;


public class ExcepcionDatoYaExiste extends Exception{

    
    public ExcepcionDatoYaExiste() {
        
        super("Dato ya exite en el arbol");
    }

    public ExcepcionDatoYaExiste(String msg) {
        super(msg);
    }
}
