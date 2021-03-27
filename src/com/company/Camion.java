package com.company;


import java.time.LocalDate;

public class Camion extends Automotor implements VehiculoACombustion{
    private Combustible tipoCombustible;
    private Integer litrosCapacidadTanque;
    private Integer capacidadCarga;
    private Integer cantidadEjes;


    public Camion(Uso uso, Conductor propietario,Marca marca, String modelo, Combustible tipoCombustible, Integer litrosCapacidadTanque, Integer capacidadCarga, Integer cantidadEjes) {
        super(uso, propietario, marca,modelo );
        this.tipoCombustible = tipoCombustible;
        this.litrosCapacidadTanque = litrosCapacidadTanque;
        this.capacidadCarga = capacidadCarga;
        this.cantidadEjes = cantidadEjes;
    }

    public Camion(Uso uso, Conductor propietario, LocalDate fechaAlta, String dominio, Marca marca, String modelo,Combustible tipoCombustible, Integer litrosCapacidadTanque, Integer capacidadCarga, Integer cantidadEjes) throws DatosDominioException {
        super(uso, propietario, fechaAlta, dominio,marca ,modelo );
        this.tipoCombustible = tipoCombustible;
        this.litrosCapacidadTanque = litrosCapacidadTanque;
        this.capacidadCarga = capacidadCarga;
        this.cantidadEjes = cantidadEjes;
    }

    public Integer getCantidadEjes() {
        return cantidadEjes;
    }

    public Integer getCapacidadCarga() {
        return capacidadCarga;
    }

    @Override
    public Combustible gettipoCombustible() {
        return tipoCombustible;
    }

    @Override
    public Integer getCapacidadTanque() {
        return litrosCapacidadTanque;
    }

    @Override
    public Integer getAutonomia() {
        return litrosCapacidadTanque * 8;
    }
}
