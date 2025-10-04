/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author javi24
 */

public class Categoria implements Comparable<Categoria>{
    String nombre;
    IArbolBusqueda<SubCategoria> subCategoria; // esta subcategoria ya trae los productos
    
    public Categoria(String nombre, String tipoArbol){
        this.nombre = nombre;

        if (tipoArbol.contains("ARBOL BINARIO")) {
            subCategoria = new ArbolBinarioBusqueda<>();
        } else if (tipoArbol.contains("ARBOL AVL")) {
            subCategoria = new AVL<>();
        } else if (tipoArbol.contains("ARBOL MVIAS")) {

        }
    }
   
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public IArbolBusqueda<SubCategoria> getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(IArbolBusqueda<SubCategoria> subCategoria) {
        this.subCategoria = subCategoria;
    }
    
   
    
    @Override
    public int compareTo(Categoria o) {
        return o.nombre.compareTo(nombre);
    }
    
}
