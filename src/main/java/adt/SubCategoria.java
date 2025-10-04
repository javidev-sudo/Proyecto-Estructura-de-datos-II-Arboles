/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author javi24
 */
public class SubCategoria implements Comparable<SubCategoria>{
    
    String nombre;
    IArbolBusqueda<Producto> productos;
    
    public SubCategoria(){
        productos = new ArbolBinarioBusqueda<>();
    }
    public SubCategoria(String nombre, String tipoArbol){
        this.nombre = nombre;
        if (tipoArbol.contains("ARBOL BINARIO")) {
            productos = new ArbolBinarioBusqueda<>();
        } else if (tipoArbol.contains("ARBOL AVL")) {
            productos = new AVL<>();
        } else if (tipoArbol.contains("ARBOL MVIAS")) {

        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public IArbolBusqueda<Producto> getProductos() {
        return productos;
    }

    public void setProductos(IArbolBusqueda<Producto> productos) {
        this.productos = productos;
    }
    
    @Override
    public int compareTo(SubCategoria o) {
        return o.nombre.compareTo(this.nombre);
    }

    @Override
    public String toString() {
        return "SubCategoria{" + "nombre=" + nombre + ", productos=" + productos.toString() + '}';
    }
    
}
