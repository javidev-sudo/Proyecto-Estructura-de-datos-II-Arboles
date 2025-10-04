/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package excepciones;


public class ExcepcionDatoNoExiste extends Exception{

    
    public ExcepcionDatoNoExiste() {
        super("Dato no existe en el árbol");
    }
    
    public ExcepcionDatoNoExiste(String msg) {
        super(msg);
    }
}
