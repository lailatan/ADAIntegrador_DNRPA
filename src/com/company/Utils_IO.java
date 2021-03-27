package com.company;

import java.time.LocalDate;

import java.util.List;
import java.util.Scanner;

public abstract class Utils_IO {


    //-----------------------------------------------------------------------
    // Metodos de Saludos
    public static void mostrarBienvenida(){
        imprimirSeparador();
        System.out.println();
        System.out.println("    -------------------------------------");
        System.out.println("    | Bienvenido al Sistema de la DNRPA |");
        System.out.println("    -------------------------------------");
        System.out.println();
        imprimirPresioneTecla();
    }

    public static void mostrarDespedida(){
        imprimirSeparador();
        System.out.println("    ----------------------------------------");
        System.out.println("    | Gracias por utilizar nuestro Sistema |");
        System.out.println("    ----------------------------------------");
        imprimirSeparador();
    }

    //-----------------------------------------------------------------------
    // Metodos de Pantalla

    public static void imprimirSeparador() {
        for (int i=0; i<175; i++) System.out.print("+");
        System.out.println();
    }

    public static void imprimirSeparadorSecundario() {
        for (int i=0; i<175; i++) System.out.print("-");
        System.out.println();
    }

    public static void imprimirOpciones(String menu, List<IOOpcionMenu> opciones){
        System.out.println();
        System.out.println(menu + ":");
        Utils_IO.subrayar(menu,true);
        opciones.forEach((opcion)-> {
            System.out.println(opcion.getTexto());
        });
        System.out.println();
        System.out.print("Seleccione una opción: ");
    }

    public static void subrayar(String palabra,Boolean imprimeEnter) {
        for (int i=0;i<palabra.length();i++) System.out.print("-");
        if (imprimeEnter) System.out.println();
        else System.out.print(" ");
    }

    public static  Boolean imprimirDeseaContinuar(){
        return imprimirPreguntaSiNo("¿ Desea continuar en esta opción de menú ?");
    }

    public static  Boolean imprimirPreguntaSiNo(String mensaje){
        imprimirSeparadorSecundario();
        System.out.println(mensaje);
        System.out.print("Presione S para Si ... ");
        Scanner scanner = new Scanner((System.in));
        String tecla = scanner.nextLine();
        return (tecla.startsWith("s") || tecla.startsWith("S"));
    }

    public static  void imprimirPresioneTecla(){
        imprimirSeparadorSecundario();
        System.out.print("Presione una tecla para Continuar... ");
        String tecla=pedirUnaTecla();
    }
    //-----------------------------------------------------------------------
    // Metodos de Interaccion con Usuario
    public static  String pedirUnaTecla() {
        Scanner scanner = new Scanner((System.in));
        return scanner.nextLine();
    }

    public static  Integer pedirUnaOpcion(Integer maximo, Boolean conCero) {
        return getNumeroPositivoMenorA(maximo,conCero);
    }

    public static String pedirDominio(String mensaje,Boolean aceptaVacio) {
        boolean continuar = true;
        String palabra = "";
        while (continuar) {
            System.out.print(mensaje);
            Scanner scanner = new Scanner((System.in));
            palabra = scanner.nextLine();
            palabra=palabra.trim().toUpperCase();
            if (!palabra.equals("")) {
                if (!DNRPA.validarDominio(palabra))
                    System.out.println("Dominio inválido. Debe tener la forma 'AB123CD' ");
                else
                    continuar = false;
            } else if (aceptaVacio) continuar = false;
            else System.out.println("Debe indicar un Dominio.");
        }
        return palabra;
    }

    public static Conductor pedirNuevoConductor() {
        Conductor conductor;
        String nombre=pedirString("el nombre del nuevo Titular ",false);
        String dni=pedirDNI();
        String domicilio=pedirString("el domicilio ",true);
        conductor=new Conductor(nombre,dni);
        conductor.setDomicilio(domicilio);
        return conductor;
    }

    public static Registro_Seccional pedirNuevaSeccional() {
        Registro_Seccional seccional;
        String nombre=pedirString("el nombre del nuevo Registro Seccional",false);
        Integer codigo =pedirInteger("el código");
        seccional=new Registro_Seccional(nombre,codigo);
        return seccional;
    }

    private static String pedirDNI() {
        boolean continuar = true;
        String dni = "";
        while (continuar) {
            dni=pedirString("el dni ",false);
            if (!Utils.dniValido(dni))
                System.out.println("DNI inválido.");
            else
                continuar = false;
        }
        return dni;
    }

    public static Uso pedirUso() {
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Seleccione el Uso del Automotor:");
        System.out.println("-------------------------------");
        for (int i=0;i<Uso.values().length;i++){
            System.out.println("   " + (i+1) + ". " + Uso.values()[i]);
        }
        System.out.print("Seleccione una opción: ");
        Integer opcion= pedirUnaOpcion(Uso.values().length,false);
        return Uso.values()[opcion-1];
    }

    public static Categoria pedirCategoria() {
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Seleccione la Categoria:");
        System.out.println("-----------------------");
        for (int i=0;i<Categoria.values().length;i++){
            System.out.println("   " + (i+1) + ". " + Categoria.values()[i]);
        }
        System.out.print("Seleccione una opción: ");
        Integer opcion= pedirUnaOpcion(Categoria.values().length,false);
        return Categoria.values()[opcion-1];
    }

    public static Marca pedirMarca() {
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Seleccione la Marca:");
        System.out.println("-------------------");
        for (int i=0;i<Marca.values().length;i++){
            System.out.println("   " + (i+1) + ". " + Marca.values()[i]);
        }
        System.out.print("Seleccione una opción: ");
        Integer opcion= pedirUnaOpcion(Marca.values().length,false);
        return Marca.values()[opcion-1];
    }

    private static Combustible pedirCombustible() {
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Seleccione el Combustible:");
        System.out.println("-------------------------");
        for (int i=0;i<Combustible.values().length;i++){
            System.out.println("   " + (i+1) + ". " + Combustible.values()[i]);
        }
        System.out.print("Seleccione una opción: ");
        Integer opcion= pedirUnaOpcion(Combustible.values().length,false);
        return Combustible.values()[opcion-1];
    }

    public static  String pedirString(String mensaje,Boolean aceptaVacio) {
        boolean continuar = true;
        String palabra = "";
        Utils_IO.imprimirSeparadorSecundario();
        while (continuar) {
            System.out.print("Ingrese " + mensaje + ": ");
            Scanner scanner = new Scanner((System.in));
            palabra = scanner.nextLine();
            if (!Utils.palabraValido(palabra) && !aceptaVacio)
                System.out.println("Valor inválido.");
            else
                continuar = false;
        }
        return palabra;
    }

    public static  Double pedirDouble(String mensaje) {
        Utils_IO.imprimirSeparadorSecundario();
        System.out.print("Ingrese " + mensaje + ": ");
        return getDouble();
    }

    public static  Integer pedirInteger(String mensaje) {
        Utils_IO.imprimirSeparadorSecundario();
        System.out.print("Ingrese " + mensaje + ": ");
        return getInt();
    }

    public static int getInt() {
        Scanner sc = new Scanner(System.in);
        String texto = "";
        int numero = 0;
        boolean correcto = false;
        do{
            texto = sc.nextLine();
            try{
                numero = Integer.parseInt(texto);
                correcto = true;
            }catch (NumberFormatException e){
                System.out.print("Ingrese un número correcto: ");
            }
        }while(!correcto);
        return numero;
    }

    public static  double getDouble() {
        Scanner sc = new Scanner(System.in);
        String texto = "";
        double numero = 0.00;
        boolean correcto = false;
        do{
            texto = sc.nextLine();
            try{
                numero = Double.parseDouble(texto);
                correcto = true;
            }catch (NumberFormatException e){
                System.out.print("Ingrese un número correcto: ");
            }
        }while(!correcto);
        return numero;
    }

    public static int getNumeroPositivoMayorA(int minimo){
        int numero = 0;
        while (numero < minimo){
            System.out.println("El numero debe ser mayor a "+minimo);
            numero = getInt();
        }
        return numero;
    }

    public static int getNumeroPositivoMenorA(int maximo,Boolean conCero){
        int numero = -1;
        numero = getInt();
        while (numero > maximo || (conCero? numero<0:numero<1)){
            System.out.print("El numero debe ser positivo menor a "+ maximo + ": ");
            numero = getInt();
        }
        return numero;
    }

    public static String getString(String mensaje) {
        String texto = "";
        Scanner sc = new Scanner(System.in);
        boolean correcto = false;
        do{
            System.out.print(mensaje+": ");
            texto = sc.nextLine();
            if(texto.length()>0) correcto=true;
        }while(!correcto);
        return texto;
    }

    public static void mostrarNoSeEncontraronDatos() {
        imprimirSeparadorSecundario();
        System.out.println("No se encontraron datos con ese Criterio.");
    }

    public static void mostrarCantidadEncontrada(Integer cantidad) {
        imprimirSeparadorSecundario();
        System.out.println((cantidad==1?"Se encontró ":"Se encontraron ")
                + cantidad
                + (cantidad==1?" dato ":" datos ")
                + "con ese Criterio.");
    }

    public static void mostrarResultadoTotalDatos(Integer cantidad) {
        if (cantidad ==0) Utils_IO.mostrarNoSeEncontraronDatos();
        else Utils_IO.mostrarCantidadEncontrada(cantidad);
    }

    public static void pedirDatosVehiculoCombustion(Combustible tipoCombustible,Integer litrosCapacidadTanque){
        tipoCombustible = pedirCombustible();
        litrosCapacidadTanque = Utils_IO.pedirInteger("Capacidad del tanque en litros");
    }

    public static void pedirDatosVehiculoElectrico(String voltaje,Integer horasCargaCompleta) {
        voltaje = pedirString("Voltaje", false);
        horasCargaCompleta = Utils_IO.pedirInteger("Tiempo Carga completa en horas");
    }

    public static LocalDate pedirFecha(String mensaje) {
        boolean continuar = true;
        LocalDate fecha=null;
        Utils_IO.imprimirSeparadorSecundario();
        while (continuar) {
            System.out.println("Ingrese " + mensaje + ": ");
            System.out.print("- Día: ");
            int dia = getNumeroPositivoMenorA(31,false);
            System.out.print("- Mes: ");
            int mes = getNumeroPositivoMenorA(12,false);
            System.out.print("- Año: ");
            int anio = getNumeroPositivoMenorA(LocalDate.now().getYear(),false);
            if (!Utils.esFechaValidaHastaHoy(anio,mes,dia))
                System.out.println("Fecha inválida. " + dia +"/"+ mes +"/"+ anio +" no es una fecha de Alta correcta.");
            else {
                fecha=LocalDate.of(anio,mes,dia);
                continuar = false;
            }
        }
        return fecha;
    }
}