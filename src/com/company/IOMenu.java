package com.company;

import java.util.ArrayList;


public abstract class IOMenu {
    protected ArrayList<IOOpcionMenu> opciones;
    protected String nombre;
    protected DNRPA direccion ;

    public IOMenu() {
        opciones= new ArrayList<>();
        nombre="Menu";
    }

    public void mostrarMenu(){
        Boolean continuar=true;

        while (continuar) {
            Utils_IO.imprimirSeparador();
            Utils_IO.imprimirOpciones(nombre,opciones);
            Integer opcion = Utils_IO.pedirUnaOpcion(opciones.size()-1,true);
            continuar = ejecutarOpcionMenuYContinuar(opcion);
        }
    }

    protected abstract Boolean ejecutarOpcionMenuYContinuar(Integer opcion) ;

}