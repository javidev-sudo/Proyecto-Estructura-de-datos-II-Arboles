/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import excepciones.ExcepcionDatoNoExiste;
import excepciones.ExcepcionDatoYaExiste;
import java.util.List;

/**
 *
 * @author javi24
 * @param <T>
 */
public class AVL<T extends Comparable<T>> extends ArbolBinarioBusqueda<T>{
    
    private static final byte LIMTE_MAXIMO = 1;

    
    @Override
    public void eliminar(T datoAEliminar) throws ExcepcionDatoNoExiste {
        if(datoAEliminar == null){
            throw new ExcepcionDatoNoExiste("Dato a eliminar no puede ser nulo");
        }
        super.raiz = eliminar(super.raiz,datoAEliminar);
    }
    
    private NodoBinario<T> eliminar(NodoBinario<T> nodoEnTurno, T datoAEliminar)
        throws ExcepcionDatoNoExiste {
        
       if(NodoBinario.esNodoVacio(nodoEnTurno)){
           throw new ExcepcionDatoNoExiste();
       }
       
       T datoDelNodoEnTurno = nodoEnTurno.getDato(); // saco el dato del nodo, esto puede ser cualquier dato generico
       
       if(datoAEliminar.compareTo(datoDelNodoEnTurno) < 0){ //el dato a eliminar es menor osea que puede dar valores negativos
           NodoBinario<T> supuestoNuevoHijoIzq = this.eliminar(nodoEnTurno.getHijoIzq(), datoAEliminar);
           
           nodoEnTurno.setHijoIzq(supuestoNuevoHijoIzq);
           return balancear(nodoEnTurno);
       }
       
       if(datoAEliminar.compareTo(datoDelNodoEnTurno) > 0){ //el dato a eliminar es mayor y que va entrar si hay valores positivos 
           NodoBinario<T> supuestoNuevoHijoDer = this.eliminar(nodoEnTurno.getHijoDer(), datoAEliminar);
           
           nodoEnTurno.setHijoDer(supuestoNuevoHijoDer);
           return balancear(nodoEnTurno);
       }
       // SI LLEGAMOS HASTA ACA SABEMOS QUE EL NODO EN TURNO TIENE EL DATO QUE TENEMOS QUE ELIMINAR
       //CASO 1 que el nodo sea una hoja
       
       if(nodoEnTurno.esHoja()){
           
           return NodoBinario.nodoVacio(); // 
           
       }
       
       // CASO 2a que el nodo tenga un solo hijo en este caso que solo haya el izquierdo
       
       if(!nodoEnTurno.esVacioHijoIzq() && nodoEnTurno.esVacioHijoDer()){ 
           NodoBinario<T> nodoARetornar = nodoEnTurno.getHijoIzq();
           nodoEnTurno.setHijoIzq(NodoBinario.nodoVacio());
           return balancear(nodoARetornar);
       }
       
       // CASO 2b que el nodo tenga un solo hijo en este caso que solo haya el derecho
       
       if(nodoEnTurno.esVacioHijoIzq() && !nodoEnTurno.esVacioHijoDer()){
           NodoBinario<T> nodoARetornar = nodoEnTurno.getHijoDer();
           nodoEnTurno.setHijoDer(NodoBinario.nodoVacio());
           return balancear(nodoARetornar);
       }
       
       // CASO 3 que el nodo que se quiere eliminar tenga dos hijos, pero no lo eliminamos si no hacemos un reemplazo de valores  
       
       T reemplazo = this.buscarSucesorInOrden(nodoEnTurno.getHijoDer());
       NodoBinario<T> supuestoNuevoHijoDerecho = this.eliminar(nodoEnTurno.getHijoDer(), reemplazo);
       nodoEnTurno.setHijoDer(supuestoNuevoHijoDerecho);
       nodoEnTurno.setDato(reemplazo);
       return balancear(nodoEnTurno);
    }
    
    
    @Override
    public void insertar(T datoAInsertar) throws ExcepcionDatoYaExiste{
        if(datoAInsertar == null){
           throw new IllegalArgumentException("No se puede insertar datos nulos!!");
        }
        
        super.raiz = this.insertar(super.raiz, datoAInsertar);
    }
    
    private NodoBinario<T> insertar(NodoBinario<T> nodoEnTurno, T datoAInsertar) throws ExcepcionDatoYaExiste{
        if(NodoBinario.esNodoVacio(nodoEnTurno)){ // caso base
            return new NodoBinario<>(datoAInsertar);
        }
        
        T datoDelNodoEnTurno = nodoEnTurno.getDato();
        if(datoAInsertar.compareTo(datoDelNodoEnTurno) < 0){ // se va por isquierda
            NodoBinario<T> supuestoNuevohijoIzquierdo = this.insertar(nodoEnTurno.getHijoIzq(), datoAInsertar);  
            nodoEnTurno.setHijoIzq(supuestoNuevohijoIzquierdo);
            return balancear(nodoEnTurno);
        }
        if(datoAInsertar.compareTo(datoDelNodoEnTurno) > 0){
            NodoBinario<T> supuestoNuevohijoDerecho = this.insertar(nodoEnTurno.getHijoDer(), datoAInsertar);
            nodoEnTurno.setHijoDer(supuestoNuevohijoDerecho);
            return balancear(nodoEnTurno);
        }
        throw new ExcepcionDatoYaExiste();
    }
       
    private NodoBinario<T> balancear(NodoBinario<T> nodoEnTurno){
        int alturaHijoDer = super.altura(nodoEnTurno.getHijoDer());
        int alturaHijoIzq = super.altura(nodoEnTurno.getHijoIzq());
        int diferencia = alturaHijoIzq - alturaHijoDer; //
        
        if(diferencia > AVL.LIMTE_MAXIMO){ // lado mas largo es la IZQUIERDA, == rotar a la derecha
            NodoBinario<T> hijoIzquierdoNodoEnTurno = nodoEnTurno.getHijoIzq();
            
            alturaHijoDer = super.altura(hijoIzquierdoNodoEnTurno.getHijoDer());
            alturaHijoIzq = super.altura(hijoIzquierdoNodoEnTurno.getHijoIzq());
            
            
            if(alturaHijoDer > alturaHijoIzq){
                return rotacionDobleADerecha(nodoEnTurno);
            }
            
            return rotacionSimpleADerecha(nodoEnTurno); // falta adicionar
            
        } else if (diferencia < -AVL.LIMTE_MAXIMO){// lado mas largo es la DERECHA, == rotar a la IZQUIERDA
            
            NodoBinario<T> hijoDerechoNodoEnTurno = nodoEnTurno.getHijoDer();
            
            alturaHijoDer = super.altura(hijoDerechoNodoEnTurno.getHijoDer());
            alturaHijoIzq = super.altura(hijoDerechoNodoEnTurno.getHijoIzq());
            
            if(alturaHijoIzq > alturaHijoDer){
                
                return rotacionDobleAIzquierda(nodoEnTurno);
            }
            
            return rotacionSimpleAIzquierda(nodoEnTurno);
        }
        
        return nodoEnTurno;
    }

    private NodoBinario<T> rotacionSimpleADerecha(NodoBinario<T> nodoEnTurno) {
        NodoBinario<T> nodoARetornar = nodoEnTurno.getHijoIzq();
        nodoEnTurno.setHijoIzq(nodoARetornar.getHijoDer());
        nodoARetornar.setHijoDer(nodoEnTurno);
        return nodoARetornar;
    }

    private NodoBinario<T> rotacionSimpleAIzquierda(NodoBinario<T> nodoEnTurno) {
       NodoBinario<T> nodoARetornar = nodoEnTurno.getHijoDer();
       nodoEnTurno.setHijoDer(nodoARetornar.getHijoIzq());
       nodoARetornar.setHijoIzq(nodoEnTurno);
       return nodoARetornar;
    }

    private NodoBinario<T> rotacionDobleADerecha(NodoBinario<T> nodoEnTurno) {
        NodoBinario<T> nodoARetornar = rotacionSimpleAIzquierda(nodoEnTurno.getHijoIzq());
        nodoEnTurno.setHijoIzq(nodoARetornar);
        return rotacionSimpleADerecha(nodoEnTurno);
    }

    private NodoBinario<T> rotacionDobleAIzquierda(NodoBinario<T> nodoEnTurno) {
       NodoBinario<T> nodoARetornar = rotacionSimpleADerecha(nodoEnTurno.getHijoDer());
        nodoEnTurno.setHijoDer(nodoARetornar);
        return rotacionSimpleAIzquierda(nodoEnTurno); 
    }

    
}
