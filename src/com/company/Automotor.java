package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public abstract class Automotor {
    private String dominio;
    private Marca marca;
    private String modelo;
    private LocalDate fechaAlta;
    private LocalDate fechaCambioPropietario;
    private Uso uso;
    private Conductor propietario;
    private List<Conductor> autorizados;


    public Automotor(Uso uso, Conductor propietario, Marca marca, String modelo) {
        this.dominio = DNRPA.crearNuevoDominio();
        this.marca=marca;
        this.modelo=modelo;
        this.uso = uso;
        this.propietario = propietario;
        autorizados = new ArrayList<>();
        fechaAlta=LocalDate.now();
        fechaCambioPropietario=null;
    }

    public Automotor(Uso uso, Conductor propietario, LocalDate fechaAlta, String dominio, Marca marca, String modelo) throws DatosDominioException {
        dominio=dominio.toUpperCase();
        if (DNRPA.validarDominio(dominio)){
            if (!(DNRPA.existeDominio(dominio))){
                this.dominio = dominio;
                this.marca=marca;
                this.modelo=modelo;
                this.uso = uso;
                this.propietario = propietario;
                this.autorizados = new ArrayList<>();
                this.fechaAlta=fechaAlta;
                this.fechaCambioPropietario=null;
            } else
                throw new DatosDominioException("Dominio ya existente");
        } else
            throw new DatosDominioException("Formato de Dominio incorrecto");
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public String getDominio() {
        return dominio;
    }

    public Uso getUso() {
        return uso;
    }

    public Conductor getPropietario() {
        return propietario;
    }

    public List<Conductor> getAutorizados() {
        return autorizados;
    }

    public void setUso(Uso uso) {
        this.uso = uso;
    }

    public Boolean cambiarPropietario(Conductor propietario, Uso uso) {
        if (pasoMasde1AnioUltimoTitular()) {
            this.propietario = propietario;
            this.uso = uso;
            autorizados = new ArrayList<>();
            fechaCambioPropietario = LocalDate.now();
            return true;
            //fechaCambioPropietario=LocalDate.of(2020,2,3);
        } else return false;

    }

    public Boolean pasoMasde1AnioUltimoTitular(){
        LocalDate fecha = (fechaCambioPropietario==null) ? fechaAlta: fechaCambioPropietario;
        LocalDate  hoy = LocalDate.now();
        //long diferencia = ChronoUnit.YEARS.between(fecha,hoy);
        return (ChronoUnit.YEARS.between(fecha,hoy)>0);
    }

    public void agregarAutorizado(Conductor conductor){
        autorizados.add(conductor);
    }

    public void eliminarAutorizado(Conductor conductor){
        autorizados.remove(conductor);
    }

    public void mostrarDatosTabla() {
        String fecha= fechaAlta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String fechaTranf= (fechaCambioPropietario==null)?"":fechaCambioPropietario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Utils_IO.imprimirSeparadorSecundario();
        System.out.format("%-12s%-15s%-15s%-12s%-20s%-30s%-12s%-15s%-12s%-12d%-20s", dominio, marca,modelo,fecha,fechaTranf, propietario.getNombre(),
                uso , determinarTipoVehiculo(),determinarEsElectrico(),autorizados.size(),
                (pasoMasde1AnioUltimoTitular() ? "Si" : "No"));
        System.out.println();
    }

    public static void mostrarDatosTablaEncabezado() {
        Utils_IO.imprimirSeparadorSecundario();
        System.out.format("%-12s%-15s%-15s%-12s%-20s%-30s%-12s%-15s%-12s%-12s%-20s", "Dominio", "Marca","Modelo", "Fecha Alta", "Fecha Transferencia",
                "Titular", "Uso", "Tipo","Electrico","Autorizados","CedulaVerdeVencida");
        System.out.println();
    }

    public void mostrarDatos() {
        String fecha= fechaAlta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String fechaTranf= (fechaCambioPropietario==null)?"":fechaCambioPropietario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Utils_IO.imprimirSeparadorSecundario();
        System.out.printf("%30s %-50s \n","Automotor: " , dominio);
        System.out.printf("%30s %-50s \n","Marca: " , marca);
        System.out.printf("%30s %-50s \n","Modelo: " , modelo);
        System.out.printf("%30s %-50s \n","Fecha Alta: " , fecha);
        System.out.printf("%30s %-50s \n","Fecha Ultima Transferencia: " , fechaTranf);
        System.out.printf("%30s %-50s \n","Cedula Verde Vencida: " , (pasoMasde1AnioUltimoTitular() ? "Si" : "No"));
        System.out.printf("%30s %-50s \n","Titular: " , propietario.getNombre());
        System.out.printf("%30s %-50s \n","Uso: " , uso);
        System.out.printf("%30s %-50s \n","Tipo: " , determinarTipoVehiculo());
        System.out.printf("%30s %-50s \n","Electrico: " , determinarEsElectrico());
        System.out.printf("%30s %-50s \n",(autorizados.size()==1?"Autorizado: ":"Autorizados: ")
                , autorizados.size() + (autorizados.size()==1?" conductor":" conductores"));
    }

    public void mostrarDatosConAutorizados(){
        mostrarDatos();
        if (autorizados.size()>0){
            System.out.printf("%30s %-1s"," ", "- ");
            for (Conductor c : autorizados) {
                System.out.print(c.getNombre() + " (" + c.getDni() + ") - ");
            }
        }
        System.out.println();
    }

    public String determinarTipoVehiculo(){
        return this.getClass().getSimpleName().toUpperCase();
    }

    private String determinarEsElectrico(){
        return ((this instanceof VehiculoElectrico) ? "Si" : "No");
    }
}
