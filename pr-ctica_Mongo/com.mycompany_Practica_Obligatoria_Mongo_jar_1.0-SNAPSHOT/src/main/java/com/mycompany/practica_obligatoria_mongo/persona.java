/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica_obligatoria_mongo;

/**
 *
 * @author danie
 */
public class persona {
    private String dni;
    private String nombre;
    private String equipo;
    private String tipo;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public persona(String dni, String nombre, String equipo, String tipo) {
        this.dni = dni;
        this.nombre = nombre;
        this.equipo = equipo;
        this.tipo = tipo;
    }
    
    public persona() {
        
    }
}

