package com.company;

public class IOMenuConsultasAutomotor extends IOMenu{

    public IOMenuConsultasAutomotor(DNRPA direccion) {
        opciones.add(new IOOpcionMenu("1. Consulta por Dominio"));
        opciones.add(new IOOpcionMenu("2. Consulta por Secciónal"));
        opciones.add(new IOOpcionMenu("3. Consulta por Categoría"));
        opciones.add(new IOOpcionMenu("4. Consulta Tiempo desde último Propietario"));
        opciones.add(new IOOpcionMenu("0. Volver"));
        this.nombre="Menu Consultas Automotores";

        this.direccion = direccion;
    }

    @Override
    protected Boolean ejecutarOpcionMenuYContinuar(Integer opcion) {
        boolean continuar=true;
        boolean continuarOpcion=true;

        switch (opcion){
            case 1:
                while (continuarOpcion) {
                    ejecutarMenuConsultaPorDominio();
                    continuarOpcion = Utils_IO.imprimirDeseaContinuar();
                }
                break;
            case 2:
                while (continuarOpcion) {
                    ejecutarMenuConsultaPorSeccional();
                    continuarOpcion = Utils_IO.imprimirDeseaContinuar();
                }
                break;
            case 3:
                while (continuarOpcion) {
                    ejecutarMenuConsultaPorCategoria();
                    continuarOpcion = Utils_IO.imprimirDeseaContinuar();
                }
                break;
            case 4:
                ejecutarMenuConsultaTiempoUltimoPropietario();
                Utils_IO.imprimirPresioneTecla();
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
    private void ejecutarMenuConsultaPorDominio() {
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Consulta Automotor por Dominio:");
        System.out.println("-------------------------------");
        //Pido datos
        String dominio = Utils_IO.pedirDominio("Ingrese Dominio (para TODOS dejar en blanco): ",true);
        //Consulto dato
        this.direccion.listarAutomotoresPorDominio(dominio);
    }

    private void ejecutarMenuConsultaPorSeccional() {
        //Pido datos
        Registro_Seccional seccional = direccion.seleccionarSeccional(false);

        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Consulta Automotor por Seccional:");
        System.out.println("---------------------------------");
        //Consulto dato
        this.direccion.listarAutomotoresPorSeccional(seccional);
    }

    private void ejecutarMenuConsultaPorCategoria() {
        //Pido datos
        Categoria categoria = Utils_IO.pedirCategoria();
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Consulta Automotor por Categoría:");
        System.out.println("---------------------------------");
        //Consulto dato
        this.direccion.listarAutomotoresPorCategoria(categoria);
    }

    private void ejecutarMenuConsultaTiempoUltimoPropietario() {
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Consulta Tempo desde último cambio de Propietario:");
        System.out.println("--------------------------------------------------");
        //Pido datos
        String dominio = Utils_IO.pedirDominio("Ingrese Dominio: ",false);
        //Consulto dato
        this.direccion.ConsultarTiempoDesdeCambioPropietario(dominio);
    }
}
