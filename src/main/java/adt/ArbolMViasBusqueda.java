/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import excepciones.ExcepcionDatoNoExiste;
import excepciones.ExcepcionDatoYaExiste;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



public class ArbolMViasBusqueda <T extends Comparable<T>> implements IArbolBusqueda<T>{
    protected static final int ORDEN_MINIMO = 3;
    protected static final int POSICION_INVALIDA = -1;
    
    protected NodoMVias<T> raiz;
    protected int orden;

    public ArbolMViasBusqueda() {
        this.orden = ORDEN_MINIMO;
    }
    
    public ArbolMViasBusqueda(int orden){
        if(orden < ORDEN_MINIMO){
            this.orden = ORDEN_MINIMO;
        }       
        this.orden = orden;            
    }
    
    
    @Override
    public void vaciar() {
       this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
       return this.raiz == NodoMVias.nodoVacio();
    }
    
    @Override
    public void insertar(T datoAInsertar) throws ExcepcionDatoYaExiste {
        if(datoAInsertar == null){
            throw new IllegalArgumentException("No se puede insertar datos nulos!!");
        }
        if(this.esArbolVacio()){
            this.raiz = new NodoMVias<>(orden,datoAInsertar);
            return;
        }
        
        NodoMVias<T> nodoEnTurno = this.raiz;//guardamos la raiz en un nodoEnTurno
        
        do {
            int posionDeDato = this.buscarPosicionDeDatoEnNodo(nodoEnTurno, datoAInsertar); // puede dar -1 , o un numero mayor
            if(posionDeDato != POSICION_INVALIDA){
                throw new ExcepcionDatoYaExiste();
            }
            if(nodoEnTurno.esHoja()){ // si el nodo es hoja
                
                if(nodoEnTurno.estanDatosLLenos()){
                    // no hay espacio
                     int posicionPorDondeBajar = this.buscarPosicionPorDondeBajar(nodoEnTurno, datoAInsertar); // falta implementar
                     NodoMVias<T> nuevoNodo = new NodoMVias<>(this.orden,datoAInsertar);
                     nodoEnTurno.setHijo(posicionPorDondeBajar, nuevoNodo);
                }else {
                    //si hay espacio
                    this.insertarDatoOrdenadoEnNodo(nodoEnTurno, datoAInsertar);
                    
                }
                nodoEnTurno = NodoMVias.nodoVacio();
            } else { // si no es hoja
                int posicionPorDondeBajar = this.buscarPosicionPorDondeBajar(nodoEnTurno, datoAInsertar);
                 if(nodoEnTurno.esHijoVacio(posicionPorDondeBajar)){
                     NodoMVias<T> nodoNuevo = new NodoMVias<>(this.orden,datoAInsertar);
                     nodoEnTurno.setHijo(posicionPorDondeBajar, nodoNuevo);
                     nodoEnTurno =  NodoMVias.nodoVacio();
                 }else {
                     nodoEnTurno = nodoEnTurno.getHijo(posicionPorDondeBajar);
                 }
            }
            
        } while (!NodoMVias.esNodoVacio(nodoEnTurno));
         
    }
    
    protected int buscarPosicionDeDatoEnNodo(NodoMVias<T> nodoEnTurno, T datoAInsertar) {
        for (int i = 0; i < nodoEnTurno.nroDeDatosNoVacios(); i++) {
            T dataEnTurno = nodoEnTurno.getDato(i);
            if(datoAInsertar.compareTo(dataEnTurno) == 0){
                return i;
            }
        }      
        return POSICION_INVALIDA;
    }

    @Override
    public void eliminar(T datoAEliminar) throws ExcepcionDatoNoExiste {
        if(datoAEliminar == null){
            throw new IllegalArgumentException();
        }
        this.raiz = eliminar(raiz , datoAEliminar);
    }
    
    private NodoMVias<T> eliminar(NodoMVias<T> nodoEnTurno, T datoAEliminar) throws ExcepcionDatoNoExiste{
        if(NodoMVias.esNodoVacio(nodoEnTurno)){
            throw new ExcepcionDatoNoExiste();
        }
        
        int posicionDeDatoAEliminar = buscarPosicionDeDatoEnNodo(nodoEnTurno, datoAEliminar);
        
        if(posicionDeDatoAEliminar == POSICION_INVALIDA){
            int posicionPorDondeBajar = buscarPosicionPorDondeBajar(nodoEnTurno, datoAEliminar);
            NodoMVias<T> supuestoNuevoHijo = eliminar(nodoEnTurno.getHijo(posicionPorDondeBajar)
                                            , datoAEliminar);
            nodoEnTurno.setHijo(posicionPorDondeBajar, supuestoNuevoHijo);
            return nodoEnTurno;
        }
        
        if(nodoEnTurno.esHoja()){ //caso 1
            this.eliminarDatoDePosicion(nodoEnTurno,posicionDeDatoAEliminar);
            if(nodoEnTurno.nroDeDatosNoVacios() == 0){ // por si al eliminar en una hoja se borra, quede vacio
                return NodoMVias.nodoVacio();
            }
            return nodoEnTurno;
        }
        
        T datoReemplazo = null;
        
        if(existenHijosDespuesDePosicion(nodoEnTurno,posicionDeDatoAEliminar)){
            datoReemplazo = buscarSucesorInOrden(nodoEnTurno,posicionDeDatoAEliminar);
        }else if(existenHijosAntesDePosicion(nodoEnTurno,posicionDeDatoAEliminar)){
            datoReemplazo = buscarPredecesorInOrden(nodoEnTurno,posicionDeDatoAEliminar);
        }
        if(datoReemplazo != null){           
            nodoEnTurno = eliminar(nodoEnTurno, datoReemplazo);      
            nodoEnTurno.setDato(posicionDeDatoAEliminar, datoReemplazo);
            return nodoEnTurno;
            
        }else{      
            this.eliminarDatoManteniendoOrdenLosHijos(nodoEnTurno ,posicionDeDatoAEliminar); //metodopor mi
            return nodoEnTurno;
        }
                
    }

    @Override
    public T buscar(T dato) {
        if(dato == null){
            throw new IllegalArgumentException("dato no puede ser nulo");
        }
        if(!this.esArbolVacio()){
            NodoMVias<T> nodoEnturno = raiz;
            while (!NodoMVias.esNodoVacio(nodoEnturno)) {
                for (int i = 0; i < nodoEnturno.nroDeDatosNoVacios(); i++) {
                    if (nodoEnturno.getDato(i).compareTo(dato) == 0) {
                        return nodoEnturno.getDato(i);
                    }
                }
                
                int posicionPordondeBajar = this.buscarPosicionPorDondeBajar(nodoEnturno, dato);
                nodoEnturno = nodoEnturno.getHijo(posicionPordondeBajar);
            }
        }
        
        return null;
    }

    @Override
    public boolean contiene(T dato) {
        return this.buscar(dato) != null;
    }

    @Override
    public int size() {
        return size(this.raiz);
    }
    
    private int size(NodoMVias<T> nodoEnTurno){
        if(NodoMVias.esNodoVacio(nodoEnTurno)){
            return 0;
        }
        int contadorDeDatos = 0;
        for (int i = 0; i < nodoEnTurno.nroDeDatosNoVacios(); i++) {
            contadorDeDatos += size(nodoEnTurno.getHijo(i)) + 1;
        }
        
        contadorDeDatos += size(nodoEnTurno.getHijo(nodoEnTurno.nroDeDatosNoVacios()));
        return contadorDeDatos;
    }

    @Override
    public int altura() {
        return altura(raiz);
    }
    
    private int altura(NodoMVias<T> nodoEnTurno){
        if(NodoMVias.esNodoVacio(nodoEnTurno)){
            return 0;
        }
        
        int altura = 0;
        for (int i = 0; i <= nodoEnTurno.nroDeDatosNoVacios(); i++) {
            int alturaHijo = altura(nodoEnTurno.getHijo(i));
            if(alturaHijo > altura){
                altura = alturaHijo;
            }
        }
        /*int alturaUltimoHijo = altura(nodoEnTurno.getHijo(nodoEnTurno.nroDeDatosNoVacios()));
        if(alturaUltimoHijo > altura){
            
            return alturaUltimoHijo+1;
        }*/
        return altura+1;
        
    }


    @Override
    public int nivel() {
        return nivel(raiz);
    }
    
    private int nivel(NodoMVias<T> nodoEnTurno){
        if(NodoMVias.esNodoVacio(nodoEnTurno)){
            return -1;
        }
        if(nodoEnTurno.esHoja()){
            return 0;
        }
        int nivelActual = 0;
        for (int i = 0; i <= nodoEnTurno.nroDeDatosNoVacios(); i++) {
            int nivelHijoNodoEnTurno = nivel(nodoEnTurno.getHijo(i));
            if(nivelHijoNodoEnTurno > nivelActual){
                nivelActual = nivelHijoNodoEnTurno;
            }
        }
        
        return nivelActual + 1;
    }

    @Override
    public List<T> recorridoEnInOrden() {
        List<T> listaDeRecorrido = new ArrayList<>();
        recorridoEnInOrden(this.raiz,listaDeRecorrido);
        return listaDeRecorrido;
    } 
    
    private void recorridoEnInOrden(NodoMVias<T> nodoEnTurno, List<T> listaDeRecorrido){
        if(!NodoMVias.esNodoVacio(nodoEnTurno)){
            for (int i = 0; i < nodoEnTurno.nroDeDatosNoVacios(); i++) {
                recorridoEnInOrden(nodoEnTurno.getHijo(i), listaDeRecorrido);
                listaDeRecorrido.add(nodoEnTurno.getDato(i));
            }
            recorridoEnInOrden(nodoEnTurno.getHijo(nodoEnTurno.nroDeDatosNoVacios()), listaDeRecorrido);
        }
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> listaDeRecorrido = new ArrayList<>();
        recorridoEnPreOrden(this.raiz,listaDeRecorrido);
        return listaDeRecorrido;
    }
    
    private void recorridoEnPreOrden(NodoMVias<T> nodoEnTurno, List<T> listaDeRecorrido) {
        if(!NodoMVias.esNodoVacio(nodoEnTurno)){
            for (int i = 0; i < nodoEnTurno.nroDeDatosNoVacios(); i++) {
                listaDeRecorrido.add(nodoEnTurno.getDato(i));
                recorridoEnPreOrden(nodoEnTurno.getHijo(i),listaDeRecorrido);
            }
            recorridoEnPreOrden(nodoEnTurno.getHijo(nodoEnTurno.nroDeDatosNoVacios()), listaDeRecorrido);
        }
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> listaDeRecorrido = new LinkedList<>();
        recorridoEnPostOrden(this.raiz,listaDeRecorrido);
        return listaDeRecorrido;
    }
    
    private void recorridoEnPostOrden(NodoMVias<T> nodoEnTurno, List<T> listaDerecorrido){
        
        if(!NodoMVias.esNodoVacio(nodoEnTurno)){
            recorridoEnPostOrden(nodoEnTurno.getHijo(0), listaDerecorrido);
            
            for (int i = 1; i <= nodoEnTurno.nroDeDatosNoVacios(); i++) {
                recorridoEnPostOrden(nodoEnTurno.getHijo(i), listaDerecorrido);
                listaDerecorrido.add(nodoEnTurno.getDato(i-1));
            }
        }
    }

    @Override
    public List<T> recorridoPorNiveles() {
        List<T> recorridoNiveles = new LinkedList<>();
        if(!this.esArbolVacio()){
            Queue<NodoMVias<T>> colaDeNodosMvias = new LinkedList<>();
            colaDeNodosMvias.offer(raiz);
            
            while(!colaDeNodosMvias.isEmpty()){
                NodoMVias<T> nodoEnTurno = colaDeNodosMvias.poll();
                
                for (int i = 0; i < nodoEnTurno.nroDeDatosNoVacios(); i++) {
                   recorridoNiveles.add(nodoEnTurno.getDato(i));
  
                   if(!nodoEnTurno.esHijoVacio(i)){
                       colaDeNodosMvias.offer(nodoEnTurno.getHijo(i));
                   }
                }  
                if(!nodoEnTurno.esHijoVacio(nodoEnTurno.nroDeDatosNoVacios())){
                    colaDeNodosMvias.offer(nodoEnTurno.getHijo(nodoEnTurno.nroDeDatosNoVacios()));
                }            
            }           
        }       
        return recorridoNiveles;
    }

    private int buscarPosicionPorDondeBajar(NodoMVias<T> nodoEnTurno, T datoAInsertar) {
        for (int i = 0; i < nodoEnTurno.nroDeDatosNoVacios(); i++) {
           if(datoAInsertar.compareTo(nodoEnTurno.getDato(i)) < 0){
              return i;
           }else if(datoAInsertar.compareTo(nodoEnTurno.getDato(i)) == 0){
               return POSICION_INVALIDA; // -1
           }
        }   
        return orden; //el orden de la que es los valores
    }

    public void insertarDatoOrdenadoEnNodo(NodoMVias<T> nodoEnTurno, T datoAInsertar) {
        int n = nodoEnTurno.nroDeDatosNoVacios();
        
        for (int i = 0; i < n; i++) {
            if(datoAInsertar.compareTo(nodoEnTurno.getDato(i)) < 0){ 
                
                for(int j = n - 1;j >= i; j--){
                    T datoCopia = nodoEnTurno.getDato(j);
                    nodoEnTurno.setDato(j+1, datoCopia);
                }
                nodoEnTurno.setDato(i, datoAInsertar);
                break;             
            }else{
                if(i == n-1){
                    nodoEnTurno.setDato(i+1, datoAInsertar);
                }
            }
        } 
    }
    
    @Override
    public String toString() {
        if (NodoMVias.esNodoVacio(raiz)) {
            return "Árbol vacío";
        }

        StringBuilder sb = new StringBuilder();
        Queue<NodoMVias<T>> cola = new LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            int size = cola.size();
            for (int i = 0; i < size; i++) {
                NodoMVias<T> nodo = cola.poll();

                sb.append("[");
                for (int j = 0; j < nodo.nroDeDatosNoVacios(); j++) {
                    sb.append(nodo.getDato(j));
                    if (j < nodo.nroDeDatosNoVacios() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("] ");

                // Encolar hijos
                for (int j = 0; j < nodo.listaDeHijos.size(); j++) {
                    if (!nodo.esHijoVacio(j)) {
                        cola.add(nodo.getHijo(j));
                    }
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    
   
   //PRACTICAS
    
    //1. Para un árbol mvias de búsqueda implementar un método que reciba un dato, la busque en el árbol,
    //en caso de encontrarlo que retorne en que nivel está. Que retorne -1 en caso de no estar el dato en el
    //árbol. La implementación debe ser recursiva.
    
    public int nivelDato(T dato){
        return nivelDato(this.raiz, dato);
    }

    private int nivelDato(NodoMVias<T> nodoEnTurno, T dato) {
        
        if(NodoMVias.esNodoVacio(nodoEnTurno)){
            return -1;
        }
        
        for (int i = 0; i < nodoEnTurno.nroDeDatosNoVacios(); i++) {
            if(nodoEnTurno.getDato(i).compareTo(dato) == 0){
                return 0;
            }
        }
        int bajar = buscarPosicionPorDondeBajar(nodoEnTurno, dato);
        int nivelDeldato = nivelDato(nodoEnTurno.getHijo(bajar),dato);
        
        return nivelDeldato >= 0 ? nivelDeldato + 1 : -1;

    }
    
    public boolean nodoCompletoNivel(int nivel){
        if(nivel < 0){
            return false;
        }
        return nodoCompletoNivel(raiz, nivel);
    }

    private boolean nodoCompletoNivel(NodoMVias<T> nodoEnTurno, int nivel) {
        if(NodoMVias.esNodoVacio(nodoEnTurno)){
            return false;
        }
        
        if(nivel == 0){
            for (int i = 0; i <= nodoEnTurno.nroDeDatosNoVacios(); i++) {
                if(nodoEnTurno.esHijoVacio(i)){
                    return false;
                }
            }
            return true;
        }
        
        for (int i = 0; i <= nodoEnTurno.nroDeDatosNoVacios(); i++) {
            if(!nodoCompletoNivel(nodoEnTurno.getHijo(i), nivel-1)){
                return false;
            }           
        }
        
        return true;
    }
    
    
    public int nroDedatosHojas(int nivel){
        
        return nroDedatosHojas(raiz, nivel);
    }

    private int nroDedatosHojas(NodoMVias<T> nodoEnTurno, int nivel) {
        if(NodoMVias.esNodoVacio(nodoEnTurno)){
            return 0;
        }
        
        if (nivel < 0) {
            int cantHojas = 0;
            if(nodoEnTurno.esHoja()){
                return 1;
            }else{
                
                for(int i = 0; i <= nodoEnTurno.nroDeDatosNoVacios(); i++) {
                    cantHojas += nroDedatosHojas(nodoEnTurno.getHijo(i), nivel);
                }
            }
            
            return cantHojas;
        }
        int cantTotalDeHojasCadaHijo = 0;
        for (int i = 0; i <= nodoEnTurno.nroDeDatosNoVacios(); i++) {
             cantTotalDeHojasCadaHijo += nroDedatosHojas(nodoEnTurno.getHijo(i), nivel-1);
        }
        
       return cantTotalDeHojasCadaHijo;
    }

    private void eliminarDatoDePosicion(NodoMVias<T> nodoEnTurno, int posicionDeDatoAEliminar) {
        int posicionDelUltimoDato = nodoEnTurno.nroDeDatosNoVacios()-1;
        for (int i = posicionDeDatoAEliminar; i < posicionDelUltimoDato; i++) {
            nodoEnTurno.setDato(i, nodoEnTurno.getDato(i+1));
        }   
        nodoEnTurno.setDato(posicionDelUltimoDato, (T)NodoMVias.datoVacio());
    }

    private boolean existenHijosDespuesDePosicion(NodoMVias<T> nodoEnTurno, int posicionDeDatoAEliminar) { 
        return !nodoEnTurno.esHijoVacio(posicionDeDatoAEliminar+1);
    }

    private boolean existenHijosAntesDePosicion(NodoMVias<T> nodoEnTurno, int posicionDeDatoAEliminar) {
        return !nodoEnTurno.esHijoVacio(posicionDeDatoAEliminar);
    }

    private T buscarSucesorInOrden(NodoMVias<T> nodoEnTurno, int posicionDeDatoAEliminar) {
        NodoMVias<T> nodoHijoDerecho = nodoEnTurno.getHijo(posicionDeDatoAEliminar+1);
        while(!NodoMVias.esNodoVacio(nodoHijoDerecho.getHijo(0))){
            nodoHijoDerecho = nodoHijoDerecho.getHijo(0);
        }
        return nodoHijoDerecho.getDato(0);
    }

    private T buscarPredecesorInOrden(NodoMVias<T> nodoEnTurno, int posicionDeDatoAEliminar) {
        NodoMVias<T> nodoHijoIzquierdo = nodoEnTurno.getHijo(posicionDeDatoAEliminar);
        while(!NodoMVias.esNodoVacio(nodoHijoIzquierdo.getHijo(nodoHijoIzquierdo.nroDeDatosNoVacios()))){
            nodoHijoIzquierdo = nodoHijoIzquierdo.getHijo(nodoHijoIzquierdo.nroDeDatosNoVacios());
        }
        return nodoHijoIzquierdo.getDato(nodoHijoIzquierdo.nroDeDatosNoVacios()-1);
    }

    private void eliminarDatoManteniendoOrdenLosHijos(NodoMVias<T> nodoEnTurno, int posicionDeDatoAEliminar) {
        
        int posicionDelDatoFinal = nodoEnTurno.nroDeDatosNoVacios()-1;
        for (int i = posicionDeDatoAEliminar; i < posicionDelDatoFinal; i++) {
            nodoEnTurno.setDato(i, nodoEnTurno.getDato(i+1));
        }
        
        for (int i = posicionDeDatoAEliminar+2; i <= posicionDelDatoFinal; i++) {               
                if(!nodoEnTurno.esHijoVacio(i)){
                    nodoEnTurno.setHijo(i-1, nodoEnTurno.getHijo(i));
                    nodoEnTurno.setHijo(i, NodoMVias.nodoVacio());
                }   
        }
        
        nodoEnTurno.setDato(posicionDelDatoFinal, (T)NodoMVias.datoVacio());
        
    }

    
    //recursivo
    public T buscarPractica(T datoABuscar){
        return buscarPractica(raiz, datoABuscar);
    }

    private T buscarPractica(NodoMVias<T> nodoEnTurno, T datoABuscar ) {
        if(NodoMVias.esNodoVacio(nodoEnTurno)){
            return null;
        }
        T datoEncontrado = null;
        for (int i = 0; i < nodoEnTurno.nroDeDatosNoVacios(); i++) {
            if(datoABuscar.compareTo(nodoEnTurno.getDato(i)) == 0){
                return nodoEnTurno.getDato(i);
            }
           datoEncontrado = buscarPractica(nodoEnTurno.getHijo(i), datoABuscar);       
        }
        
        if(datoEncontrado == null){
            return buscarPractica(nodoEnTurno.getHijo(nodoEnTurno.nroDeDatosNoVacios()),datoABuscar);
        }else{
            return datoEncontrado;
        }
            
    }
    
    //iterativo
    
    public T buscarPracticaItera(T datoABuscar ) {
       
        NodoMVias<T> nodoEnTurno = raiz;
        
        while(!NodoMVias.esNodoVacio(nodoEnTurno)){
            for (int i = 0; i < nodoEnTurno.nroDeDatosNoVacios(); i++) {
                if(datoABuscar.compareTo(nodoEnTurno.getDato(i)) == 0){
                    return nodoEnTurno.getDato(i);
                }
            }
            
            int posicionPorDondeBajar = this.buscarPosicionPorDondeBajar(nodoEnTurno, datoABuscar);
            nodoEnTurno = nodoEnTurno.getHijo(posicionPorDondeBajar);
        }
        return null;  
    }

    
   public T mayor(){
       return mayor(raiz);
   }
   
   private T mayor(NodoMVias<T> nodoEnTurno){
       if(NodoMVias.esNodoVacio(nodoEnTurno)){
           return (T)NodoMVias.datoVacio();
       }
       
       T datoMayor = nodoEnTurno.getDato(nodoEnTurno.nroDeDatosNoVacios()-1);
       int posicionPorDondeBajar = nodoEnTurno.nroDeDatosNoVacios();
       
       T datomayorAbajo = mayor(nodoEnTurno.getHijo(posicionPorDondeBajar));
       
       if(datomayorAbajo != null){
           return datomayorAbajo;
       }
       
       return datoMayor;
   }
}
