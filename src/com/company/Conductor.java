package com.company;

public class Conductor {
    private String nombre;
    private String dni;
    private String domicilio;

    public Conductor(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
        domicilio="";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void mostrarDatosTabla(){
        Utils_IO.imprimirSeparadorSecundario();
        System.out.format("%-30s%-12s%-100s", nombre, dni, domicilio);
        System.out.println();
    }

    public static void mostrarDatosTablaEncabezado(){
        Utils_IO.imprimirSeparadorSecundario();
        System.out.format("%-30s%-12s%-100s", "Nombre", "DNI", "Domicilio");
        System.out.println();
    }

    public void mostrarDatos(){
        Utils_IO.imprimirSeparador();
        System.out.println("Nombre: " + nombre);
        System.out.println("DNI: " + dni);
        System.out.println("Domicilio: " + domicilio);
    }

}
