package com.company;

import java.time.LocalDate;

public class Moto_Electrica extends Automotor implements VehiculoElectrico{
    private String voltaje;
    private Integer horasCargaCompleta;


    public Moto_Electrica(Uso uso, Conductor propietario, Marca marca, String modelo,String voltaje, Integer horasCargaCompleta) {
        super(uso, propietario, marca,modelo );
        this.voltaje = voltaje;
        this.horasCargaCompleta = horasCargaCompleta;
    }

    public Moto_Electrica(Uso uso, Conductor propietario, LocalDate fechaAlta, String dominio, Marca marca, String modelo,String voltaje, Integer horasCargaCompleta) throws DatosDominioException {
        super(uso, propietario, fechaAlta, dominio, marca, modelo);
        this.voltaje = voltaje;
        this.horasCargaCompleta = horasCargaCompleta;
    }

    @Override
    public String getVoltaje() {
        return voltaje;
    }

    @Override
    public Integer getHorasCargaCompleta() {
        return horasCargaCompleta;
    }

    @Override
    public Integer getAutonomia() {
        return horasCargaCompleta * 15;

    }
}
