package com.company;

import java.util.*;

public class Registro_Seccional {
    private String nombre;
    private Integer codigo;
    private Map<String, Automotor> automotores;

    public Registro_Seccional(String nombre, Integer codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.automotores = new HashMap();
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Map<String, Automotor> getAutomotores() {
        return automotores;
    }

    public Boolean agregarAutomotor(Automotor automotor) {
        return (automotores.putIfAbsent(automotor.getDominio(), automotor) != null);
    }

    private void listarEncabezadoSeccional(){
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Registro Seccional: " + nombre + "-" + codigo);
        Utils_IO.subrayar("Registro Seccional: " + nombre + "-" + codigo,true);

    }

    public Integer listarAutomotores() {
        if (automotores.size()>0) {
            listarEncabezadoSeccional();
            Automotor.mostrarDatosTablaEncabezado();
            //for (Automotor a: automotores) a.mostrarDatosTabla();
            automotores.forEach((dominio, automotor) -> automotor.mostrarDatosTabla());
        }
        return automotores.size();
    }

    public Integer listarAutomotoresPorCategoria(Categoria categoria) {
        Boolean primeraVes=true;
        Integer cantidad=0;
        for (Map.Entry<String, Automotor> entry : automotores.entrySet()) {
            Automotor automotor = entry.getValue();
            if (automotor.determinarTipoVehiculo().equals(categoria.name())) {
                if (primeraVes) {
                    listarEncabezadoSeccional();
                    Automotor.mostrarDatosTablaEncabezado();
                    primeraVes = false;
                }
                automotor.mostrarDatosTabla();
                cantidad++;
            }
        }
        return cantidad;
    }

    public Integer listarPropietariosPorCategoria(Categoria categoria) {
        Boolean primeraVes=true;
        Integer cantidad=0;
        for (Map.Entry<String, Automotor> entry : automotores.entrySet()) {
            Automotor automotor = entry.getValue();
            if (automotor.determinarTipoVehiculo().equals(categoria.name())) {
                if (primeraVes) {
                    listarEncabezadoSeccional();
                    Conductor.mostrarDatosTablaEncabezado();
                    primeraVes = false;
                }
                automotor.getPropietario().mostrarDatosTabla();
                cantidad++;
            }
        }
        return cantidad;
    }

    public Boolean existeDominio(String dominio) {
        return automotores.containsKey(dominio);
    }

    public Automotor getAutomotor(String dominio) {
        return automotores.get(dominio);
    }

}