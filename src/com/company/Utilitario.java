package com.company;

import java.time.LocalDate;

public class Utilitario extends Automotor implements VehiculoACombustion{
    private Combustible tipoCombustible;
    private Integer litrosCapacidadTanque;
    private Integer capacidadCarga;

    public Utilitario(Uso uso, Conductor propietario, Marca marca, String modelo,Combustible tipoCombustible, Integer litrosCapacidadTanque, Integer capacidadCarga) {
        super(uso, propietario,marca , modelo);
        this.tipoCombustible = tipoCombustible;
        this.litrosCapacidadTanque = litrosCapacidadTanque;
        this.capacidadCarga = capacidadCarga;
    }

    public Utilitario(Uso uso, Conductor propietario, LocalDate fechaAlta, String dominio, Marca marca, String modelo,Combustible tipoCombustible, Integer litrosCapacidadTanque, Integer capacidadCarga) throws DatosDominioException {
        super(uso, propietario, fechaAlta, dominio,marca ,modelo );
        this.tipoCombustible = tipoCombustible;
        this.litrosCapacidadTanque = litrosCapacidadTanque;
        this.capacidadCarga = capacidadCarga;
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
        return litrosCapacidadTanque * 10;

    }
}
