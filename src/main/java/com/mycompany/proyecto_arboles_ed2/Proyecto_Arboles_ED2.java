/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyecto_arboles_ed2;
import adt.IArbolBusqueda;
import adt.ArbolBinarioBusqueda;
import adt.Categoria;
import adt.AVL;
import adt.Producto;
import adt.SubCategoria;
import excepciones.ExcepcionDatoYaExiste;

/**
 *
 * @author javi24
 */
public class Proyecto_Arboles_ED2 {

    public static void main(String[] args) throws ExcepcionDatoYaExiste {
        IArbolBusqueda<Categoria> tienda = new AVL<>();
        String tipoDeArbol = "ARBOL AVL";
        Categoria comida = new Categoria("Comida",tipoDeArbol);
        Categoria bebida = new Categoria("Bebida",tipoDeArbol);
        Categoria limpiezaHogar = new Categoria("Limpieza y hogar",tipoDeArbol);
        Categoria cuidadoPersonalFarmacia = new Categoria("Cuidado personal y farmacia",tipoDeArbol);


        tienda.insertar(comida);
        tienda.insertar(bebida);
        tienda.insertar(limpiezaHogar);
        tienda.insertar(cuidadoPersonalFarmacia);
        
        
        Categoria supuestoEncontrado = tienda.buscar(new Categoria("Cuidado personal y farmacia", tipoDeArbol));
        
        System.out.println(supuestoEncontrado.getNombre());
    }
}
