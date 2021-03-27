package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class IOMenuPrincipal extends IOMenu{

    public IOMenuPrincipal() {
        super();
        opciones.add(new IOOpcionMenu("1. Alta/Ingreso Automotor"));
        opciones.add(new IOOpcionMenu("2. Cambio Titularidad"));
        opciones.add(new IOOpcionMenu("3. Consultas Automotores"));
        opciones.add(new IOOpcionMenu("4. Consultas Propietarios"));
        opciones.add(new IOOpcionMenu("0. Salir"));
        this.nombre="Menu Principal";
        direccion = new DNRPA();
        direccion.cargarDatosInicialesPrueba();
    }


    @Override
    protected Boolean ejecutarOpcionMenuYContinuar(Integer opcion) {
        boolean continuar=true;
        boolean continuarOpcion=true;

        switch (opcion){
            case 1:
                while (continuarOpcion) {
                    ejecutarMenuAltaingresoAutomotor();
                    continuarOpcion = Utils_IO.imprimirDeseaContinuar();
                }
                break;
            case 2:
                ejecutarMenuCambioTitularidad();
                Utils_IO.imprimirPresioneTecla();
                break;
            case 3:
                ejecutarMenuConsultaAutomotor();
                break;
            case 4:
                ejecutarMenuConsultarConductor();
                break;
            case 0:
                continuar = false;
                break;
            default:
                System.out.println("Opción inválida. Vuelva a elegir.");
                Utils_IO.imprimirPresioneTecla();
        }
        return continuar;

    }

    //-----------------------------------------------------------------------
    // Metodos de Ejecución Menú

    private void ejecutarMenuAltaingresoAutomotor() {
        Utils_IO.imprimirSeparador();
        System.out.println("Alta/Ingreso de Automotor:");
        System.out.println("-------------------------");
        //Pido datos
        String dominio = Utils_IO.pedirDominio("Ingrese Dominio del Auotomotor o dejar en blanco para ALTA: ",true);
        //Consulto dato
        this.direccion.IngresoNuevoAutomotor(dominio);
    }

    private void ejecutarMenuCambioTitularidad() {
        Utils_IO.imprimirSeparador();
        System.out.println("Cambio Titularidad:");
        System.out.println("-------------------");
        //Pido datos
        String dominio = Utils_IO.pedirDominio("Ingrese Dominio: ",false);
        //Consulto dato
        this.direccion.cambioTitularDominio(dominio);
    }

    private void ejecutarMenuConsultaAutomotor() {
        IOMenuConsultasAutomotor menu = new IOMenuConsultasAutomotor(direccion);
        menu.mostrarMenu();
    }

    private void ejecutarMenuConsultarConductor() {
        IOMenuConsultasPropietario menu = new IOMenuConsultasPropietario(direccion);
        menu.mostrarMenu();
    }
}

