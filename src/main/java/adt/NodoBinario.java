/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Nicolas Jr
 */
public class NodoBinario<T extends Comparable <T>> {
//Atributos    
    private T dato;
    private NodoBinario<T> hijoIzq;
    private NodoBinario<T> hijoDer;
//Constructor
    public NodoBinario() {
    }
    
    public NodoBinario(T dato) {
        this.dato = dato;
    }    
//Setters
    public void setDato(T dato) {
        this.dato = dato;
    }

    public void setHijoIzq(NodoBinario<T> hijoIzq) {
        this.hijoIzq = hijoIzq;
    }

    public void setHijoDer(NodoBinario<T> hijoDer) {
        this.hijoDer = hijoDer;
    }
    
//Getters
    public T getDato() {
        return dato;
    }

    public NodoBinario<T> getHijoIzq() {
        return hijoIzq;
    }

    public NodoBinario<T> getHijoDer() {
        return hijoDer;
    }
    
    // metodos staticos
    public static NodoBinario nodoVacio(){  // me devuelve un nodo nulo
        return null; 
    }
    
    public static boolean esNodoVacio(NodoBinario elNodo){ // este me verifica que el nodo es nulo o no
        return elNodo==nodoVacio();
    }
    
    // metodos
    
    public boolean esVacioHijoIzq(){
        return NodoBinario.esNodoVacio(this.hijoIzq);
    }
    
    public boolean esVacioHijoDer(){
        return NodoBinario.esNodoVacio(this.hijoDer);
    }
    
    public boolean esHoja(){
        return esVacioHijoIzq() && esVacioHijoDer();
    }
}