package com.company;

public class IOMenuConsultasPropietario extends IOMenu{

    public IOMenuConsultasPropietario(DNRPA direccion) {
        opciones.add(new IOOpcionMenu("1. Listado Propietarios"));
        opciones.add(new IOOpcionMenu("2. Consulta propietarios por Categoria"));
        opciones.add(new IOOpcionMenu("0. Volver"));
        this.nombre="Menu Consultas Propietarios";

        this.direccion = direccion;
    }

    @Override
    protected Boolean ejecutarOpcionMenuYContinuar(Integer opcion) {
        boolean continuar=true;
        boolean continuarOpcion=true;

        switch (opcion){
            case 1:
                ejecutarMenuConsultaPropietarios();
                Utils_IO.imprimirPresioneTecla();
                break;
            case 2:
                while (continuarOpcion) {
                    ejecutarMenuConsultaPropietariosPorCategoria();
                    continuarOpcion = Utils_IO.imprimirDeseaContinuar();
                }
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
    private void ejecutarMenuConsultaPropietarios() {
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Listado de Propietarios:");
        System.out.println("------------------------");
        //Consulto dato
        this.direccion.listarConductoresAlfabeticamente();
    }

    private void ejecutarMenuConsultaPropietariosPorCategoria() {
        //Pido datos
        Categoria categoria = Utils_IO.pedirCategoria();
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Consulta Propietarios por Categoría:");
        System.out.println("------------------------------------");
        //Consulto dato
        this.direccion.listarConductoresPorCategoria(categoria);
    }
}
