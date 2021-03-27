package com.company;

import java.time.LocalDate;

public class Moto extends Automotor implements VehiculoACombustion {
    private Combustible tipoCombustible;
    private Integer litrosCapacidadTanque;
    private Integer cilindrada;

    public Moto(Uso uso, Conductor propietario, Marca marca, String modelo,Combustible tipoCombustible, Integer litrosCapacidadTanque, Integer cilindrada) {
        super(uso, propietario,marca ,modelo );
        this.tipoCombustible = tipoCombustible;
        this.litrosCapacidadTanque = litrosCapacidadTanque;
        this.cilindrada = cilindrada;
    }

    public Moto(Uso uso, Conductor propietario, LocalDate fechaAlta, String dominio,Marca marca, String modelo, Combustible tipoCombustible, Integer litrosCapacidadTanque, Integer cilindrada) throws DatosDominioException {
        super(uso, propietario, fechaAlta, dominio,marca ,modelo );
        this.tipoCombustible = tipoCombustible;
        this.litrosCapacidadTanque = litrosCapacidadTanque;
        this.cilindrada = cilindrada;
    }

    public Integer getCilindrada() {
        return cilindrada;
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
        return litrosCapacidadTanque * 20;
    }
}
