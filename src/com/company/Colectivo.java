package com.company;

import java.time.LocalDate;

public class Colectivo extends Automotor implements VehiculoACombustion{
    private Combustible tipoCombustible;
    private Integer litrosCapacidadTanque;
    private Integer capacidadPasajeros;


    public Colectivo(Uso uso, Conductor propietario, Marca marca, String modelo,Combustible tipoCombustible, Integer litrosCapacidadTanque, Integer capacidadPasajeros) {
        super(uso, propietario,marca ,modelo );
        this.tipoCombustible = tipoCombustible;
        this.litrosCapacidadTanque = litrosCapacidadTanque;
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public Colectivo(Uso uso, Conductor propietario, LocalDate fechaAlta, String dominio, Marca marca, String modelo,Combustible tipoCombustible, Integer litrosCapacidadTanque, Integer capacidadPasajeros) throws DatosDominioException {
        super(uso, propietario, fechaAlta, dominio,marca ,modelo );
        this.tipoCombustible = tipoCombustible;
        this.litrosCapacidadTanque = litrosCapacidadTanque;
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public Integer getCapacidadPasajeros() {
        return capacidadPasajeros;
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
        return litrosCapacidadTanque * 10;
    }
}
