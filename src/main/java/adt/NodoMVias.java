/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author javi24
 * @param <T>
 */
public class NodoMVias <T> {
    public List<T> listaDeDatos;
    public List<NodoMVias<T>> listaDeHijos;
    
    public NodoMVias(int orden){
        this.listaDeDatos = new ArrayList<>();
        this.listaDeHijos = new ArrayList<>();
        for (int i = 0; i < orden; i++) {
            
            this.listaDeDatos.add((T)NodoMVias.datoVacio());
            this.listaDeHijos.add(NodoMVias.nodoVacio());
        }
        
        this.listaDeHijos.add(NodoMVias.nodoVacio());
    }
    
    public NodoMVias(int orden, T primerDato){
        this(orden); // llama al otro contructor;
        this.listaDeDatos.set(0, primerDato);
    }
    
    public static Object datoVacio(){
        return null;
    }
    
    public static NodoMVias nodoVacio(){
        return null;
    }
    
    public static boolean esNodoVacio(NodoMVias nodo){
        return nodo == NodoMVias.nodoVacio();
    }
    
    public void setDato(int posicion, T dato){
        this.listaDeDatos.set(posicion, dato);
    }

    public T getDato(int posicion){
        return this.listaDeDatos.get(posicion);
    }
    
    public boolean esDatoVacio(int posicion){
        return this.listaDeDatos.get(posicion) == NodoMVias.datoVacio();
    }
    
    public void setHijo(int posicion, NodoMVias<T> hijo){
        this.listaDeHijos.set(posicion, hijo);
    }
    
    public NodoMVias<T> getHijo(int posicion){
        return this.listaDeHijos.get(posicion);
    }
    
    public boolean esHijoVacio(int posicion){
        return this.listaDeHijos.get(posicion) == NodoMVias.nodoVacio();
    }
            
    public int nroDeDatosNoVacios(){
        int cantidad = 0;
        for (int i = 0; i < this.listaDeDatos.size();i++) {
            if(!this.esDatoVacio(i)){
                cantidad++;
            }
        }
        return cantidad;
    }
    
    public boolean esHoja(){
        for (int i = 0; i < listaDeHijos.size(); i++) {
            if(!this.esHijoVacio(i)){
                return false;
            }          
        }
        return true;
    }

    public boolean estanDatosLLenos() {
        return nroDeDatosNoVacios() == this.listaDeDatos.size();
    }

    
}
