package com.company;


import java.time.DateTimeException;
import java.time.LocalDate;


public abstract class Utils {

    public static char letraRandom(){
        //Random random = new Random();
        //return (char)(random.nextInt(91) + 65);
        return (char) Math.floor(Math.random() * (90 - 65 + 1) + 65);
    }

    public static int numeroRandom(){
        //Random random = new Random();
        //return random.nextInt(10);
        return (int) Math.floor(Math.random() * 10);
    }

    //-----------------------------------------------------------------------
    // Metodos de Validacion

    public static Boolean numeroValido(Double numero){
        return (numero>0);
    }

    public static Boolean palabraValido(String palabra) {
        return (!(palabra.trim().equals("")));
    }

    public static boolean validarString(String texto, int maximo) {
        return texto.length()<=maximo;
    }

    public static boolean esNumerico(String palabra) {
        Boolean resultado;
        try {
            Long.parseLong(palabra);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

    public static boolean dniValido(String dni) {
        return ((dni.length()>6 && dni.length()<9) && esNumerico(dni));
    }

    public static boolean esFechaValidaHastaHoy(int anio, int mes,int dia) {
        if (esFechaValida(anio,mes,dia)) {
            return !LocalDate.of(anio,mes,dia).isAfter(LocalDate.now());
        }
        return false;
    }

    public static boolean esFechaValida(int anio, int mes,int dia){
        boolean esFechaValida = true;
        try{
            LocalDate.of(anio, mes, dia);
        }catch(DateTimeException e) {
            esFechaValida = false;
        }
        return esFechaValida;
    }
}
