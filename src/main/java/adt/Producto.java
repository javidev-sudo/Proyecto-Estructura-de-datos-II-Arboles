/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author javi24
 */
    public class Producto implements Comparable<Producto>{
        String codigo;
        String nombre;
        float precio;
        String fechaElaboracion;
        String fechaVencimiento;
        boolean estaDisponible;

        public Producto(String codigo, String nombre, float precio, String fechaElaboracion, String fechaVencimineto){

            this.codigo = codigo;
            this.nombre = nombre;
            this.precio = precio;
            this.fechaElaboracion = fechaElaboracion;
            this.fechaVencimiento = fechaVencimineto;
            this.estaDisponible = true;
        }

    public boolean isEstaDisponible() {
        return estaDisponible;
    }

    public void setEstaDisponible(boolean estaDisponible) {
        this.estaDisponible = estaDisponible;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(String fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return "" + codigo + " | " + nombre + " | " + precio + " | " + fechaElaboracion + " | " + fechaVencimiento + "\n";
    }
    
    @Override
    public int compareTo(Producto o) {
        return this.codigo.compareTo(o.codigo);
    }   
}
