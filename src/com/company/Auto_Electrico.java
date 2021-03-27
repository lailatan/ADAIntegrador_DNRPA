package com.company;

import java.time.LocalDate;

public class Auto_Electrico extends Automotor implements VehiculoElectrico{
    private String voltaje;
    private Integer horasCargaCompleta;


    public Auto_Electrico(Uso uso, Conductor propietario,Marca marca, String modelo, String voltaje, Integer horasCargaCompleta) {
        super(uso, propietario, marca, modelo);
        this.voltaje = voltaje;
        this.horasCargaCompleta = horasCargaCompleta;
    }

    public Auto_Electrico(Uso uso, Conductor propietario, LocalDate fechaAlta, String dominio, Marca marca, String modelo,String voltaje, Integer horasCargaCompleta) throws DatosDominioException {
        super(uso, propietario, fechaAlta, dominio,marca ,modelo );
        this.voltaje = voltaje;
        this.horasCargaCompleta = horasCargaCompleta;
    }

    @Override
    public String getVoltaje() {
        return voltaje;
    }

    @Override
    public Integer getAutonomia() {
        return horasCargaCompleta * 10;
    }

    public Integer getHorasCargaCompleta() {
        return horasCargaCompleta;
    }
}
