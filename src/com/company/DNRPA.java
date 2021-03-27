package com.company;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DNRPA {
    private static List<Registro_Seccional> registros;

    public DNRPA() {
        registros = new ArrayList<>();
    }

    public List<Registro_Seccional> getRegistros() {
        return registros;
    }

    public void agregarRegistro(Registro_Seccional registro){
        registros.add(registro);
    }

    public void listarAutomotoresPorSeccional(Registro_Seccional seccional){
        Integer cantidad=0;
        //Utils_IO.imprimirSeparador();
        //System.out.println("CONSULTA DE AUTOMOTORES POR REGISTRO SECCIONAL");
        if (registros.size()>0) {
            if (seccional == null) for (Registro_Seccional r : registros) cantidad+=r.listarAutomotores();
            else cantidad+=seccional.listarAutomotores();
        }
        Utils_IO.mostrarResultadoTotalDatos(cantidad);
    }

    public void listarAutomotoresPorDominio(String dominio){
        SortedMap<String,Automotor> automotores = new TreeMap<>();
        //Utils_IO.imprimirSeparador();
        //System.out.println("CONSULTA DE AUTOMOTORES POR DOMINIO");
        for (Registro_Seccional r : registros){
            if (dominio=="") automotores.putAll(r.getAutomotores());
            else {
                if (r.existeDominio(dominio)) {
                    automotores.put(dominio,r.getAutomotor(dominio));
                    break;
                }
            }
        }
        if (automotores.size()>0) {
            if (automotores.size()==1)
                automotores.get(dominio).mostrarDatosConAutorizados();
            else{
                Automotor.mostrarDatosTablaEncabezado();
                automotores.forEach((patente, automotor) -> automotor.mostrarDatosTabla());
                Utils_IO.mostrarCantidadEncontrada(automotores.size());
            }
        } else Utils_IO.mostrarNoSeEncontraronDatos();
    }

    public void listarAutomotoresPorCategoria(Categoria categoria) {
        Integer cantidad=0;
        //Utils_IO.imprimirSeparador();
        //System.out.println("CONSULTA DE AUTOMOTORES POR CATEGORIA");
        for (Registro_Seccional r : registros){
            cantidad+=r.listarAutomotoresPorCategoria(categoria);
        }
        Utils_IO.mostrarResultadoTotalDatos(cantidad);
    }

    public void listarConductoresAlfabeticamente(){
        //Utils_IO.imprimirSeparadorSecundario();
        //System.out.println("LSTADO DE CONDUCTORES ORDEN ALFABETICO");

        List<Conductor> conductores = crearListaConductoresOrdenada();

        if (conductores.size()>0){
            Conductor.mostrarDatosTablaEncabezado();
            for (Conductor c : conductores) c.mostrarDatosTabla();
            Utils_IO.mostrarCantidadEncontrada(conductores.size());
        } else Utils_IO.mostrarNoSeEncontraronDatos();
    }

    public void listarConductoresPorCategoria(Categoria categoria) {
        Integer cantidad=0;
        //Utils_IO.imprimirSeparador();
        //System.out.println("CONSULTA DE PROPIETARIOS POR CATEGORIA");
        for (Registro_Seccional r : registros){
            cantidad+=r.listarPropietariosPorCategoria(categoria);
        }
        Utils_IO.mostrarResultadoTotalDatos(cantidad);
    }

    private void agregarListaConductorSoloUnaVez(List<Conductor> conductores, Conductor conductor) {
        if (!(conductores.contains(conductor)))
            conductores.add(conductor);
    }

    private List<Conductor> crearListaConductoresOrdenada(){
        List<Conductor> conductores = new ArrayList<>();
        for (Registro_Seccional r : registros) {
            r.getAutomotores().forEach((dominio, automotor) -> {
                agregarListaConductorSoloUnaVez(conductores, automotor.getPropietario());
                for (Conductor conductor : automotor.getAutorizados()) {
                    agregarListaConductorSoloUnaVez(conductores, conductor);
                }
            });
        }
        Comparator<Conductor> comparator = (c1, c2) -> c1.getNombre().compareTo(c2.getNombre());
        conductores.sort(comparator);

        return conductores;
    }

    public static String crearNuevoDominio(){
        String dominio_nuevo ;
        do {
            dominio_nuevo = "";
            for (int i = 0; i < 2; i++)
                dominio_nuevo += Utils.letraRandom();
            for (int i = 0; i < 3; i++)
                dominio_nuevo += Utils.numeroRandom();
            for (int i = 0; i < 2; i++)
                dominio_nuevo += Utils.letraRandom();

        } while (existeDominio(dominio_nuevo));
        return dominio_nuevo;
    }

    public static  Boolean validarDominio(String dominio){
        Pattern p = Pattern.compile("[A-Z]{2}[0-9]{3}[A-Z]{2}"); //Formato de la patente, 3 letras, 3 números y 2 letras
        Matcher m = p.matcher(dominio);
        return  (m.matches());
    }

    public static Boolean existeDominio(String dominio){
        if (dominio.length()>0){
            for (Registro_Seccional registro:registros){
                if (registro.existeDominio(dominio)) return true;
            }
        }
        return false;
    }

    public static Automotor buscarDominio(String dominio){
        Automotor automotor= null;
        for (Registro_Seccional r : registros){
            if (r.existeDominio(dominio)) {
                automotor = r.getAutomotor(dominio);
                break;
            }
        }
        return automotor;
    }

    public void cambioTitularDominio(String dominio) {
        Automotor automotor = buscarDominio(dominio);
        Conductor conductorNuevo;
        if (automotor == null) System.out.println("Ese dominio no se encuentra registrado.");
        else {
            if (automotor.pasoMasde1AnioUltimoTitular()){
                conductorNuevo=pedirConductor("NUEVO Propietario");
                Uso usoNuevo = Utils_IO.pedirUso();
                automotor.cambiarPropietario(conductorNuevo, usoNuevo);
                Utils_IO.imprimirSeparadorSecundario();
                automotor.mostrarDatos();
                System.out.println("*** El Dominio " + dominio + " se transfirió con Éxito ***");
            }else  System.out.println("Ese dominio no puede transferirse porque pasó menos de 1 año desde el último cambio de Titularidad.");
        }
    }

    private Conductor pedirConductor(String tipoConductor) {
        Conductor conductor=null;
        if (Utils_IO.imprimirPreguntaSiNo("El nuevo " + tipoConductor + " ya está registrado en el sistema?"))
            conductor= seleccionarConductor();
        if (conductor==null) {
            conductor = Utils_IO.pedirNuevoConductor();
            Utils_IO.imprimirSeparadorSecundario();
            System.out.println("*** Conductor creado con Éxito ***");
        }
        return conductor;
    }

    public void ConsultarTiempoDesdeCambioPropietario(String dominio) {
        Automotor automotor= buscarDominio(dominio);
        if (automotor==null) {
            Utils_IO.imprimirSeparadorSecundario();
            System.out.println("Ese dominio no se encuentra registrado.");
        }
        else {
            automotor.mostrarDatos();
            Utils_IO.imprimirSeparadorSecundario();
            System.out.print( automotor.pasoMasde1AnioUltimoTitular()?"SI":"NO");
            System.out.println(" pasó mas de un año para el dominio " + dominio );
        }
    }

    public Conductor seleccionarConductor(){
        List<Conductor> conductores =  crearListaConductoresOrdenada();
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Seleccione un conductor:");
        System.out.println("-----------------------");
        for (int i=0;i<conductores.size();i++){
            System.out.printf("%2d. %-30s - %-15s", (i+1),conductores.get(i).getNombre(),conductores.get(i).getDni() );
            System.out.println();
        }
        System.out.printf("%2d. %-30s ", 0, "Nuevo" );
        System.out.println();
        System.out.print("Seleccione una opción: ");
        Integer opcion=  Utils_IO.pedirUnaOpcion(conductores.size(),true);

        return (opcion>0?conductores.get(opcion-1):null);
    }

    public Registro_Seccional seleccionarSeccional(Boolean admiteAlta){
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("Seleccione una Seccional:");
        System.out.println("------------------------");
        for (int i=0;i<registros.size();i++){
            System.out.printf("%2d. %-30s - %-15s", (i+1),registros.get(i).getNombre(),registros.get(i).getCodigo() );
            System.out.println();
        }
        if (admiteAlta) System.out.printf("%2d. %-30s ", 0, "Nueva" );
        else System.out.printf("%2d. %-30s ", 0, "Todas" );

        System.out.println();
        System.out.print("Seleccione una opción: ");
        Integer opcion=  Utils_IO.pedirUnaOpcion(registros.size(),true);

        return (opcion>0?registros.get(opcion-1):null);
    }

    public void IngresoNuevoAutomotor(String dominio) {
        if (existeDominio(dominio)) {
            Utils_IO.imprimirSeparadorSecundario();
            System.out.println("Ese Dominio ya está ingresado en el Sistema.");
        } else {
            Registro_Seccional seccional =seleccionarSeccional(true);
            if (seccional==null) seccional=darAltaSeccional();
            Automotor automotorNuevo = pedirAutomotor(dominio);
            if (automotorNuevo==null) System.out.println("Error al intentar esta operación");
            else {
                seccional.agregarAutomotor(automotorNuevo);
                Utils_IO.imprimirSeparadorSecundario();
                if (dominio.length() > 0)
                    System.out.println("*** Dominio " + dominio + " ingresado con Éxito ***");
                else
                    System.out.println("*** Dominio  " + automotorNuevo.getDominio() + " dado de Alta con Ëxito ***");
                if (Utils_IO.imprimirPreguntaSiNo("Desea agregar autorizados al Automotor?")) agregarAutorizadosAlta(automotorNuevo);
            }
        }
    }

    private Registro_Seccional darAltaSeccional() {
        Registro_Seccional seccionalNueva= Utils_IO.pedirNuevaSeccional();
        agregarRegistro(seccionalNueva);
        Utils_IO.imprimirSeparadorSecundario();
        System.out.println("*** Registro Seccional " + seccionalNueva.getNombre() + " - " +seccionalNueva.getCodigo() + " creado con Éxito ***");
        return seccionalNueva;
    }

    public Automotor pedirAutomotor(String dominio) {
        Automotor automotor=null;
        LocalDate fechaAlta = null;
        if (dominio.length()>0) fechaAlta = Utils_IO.pedirFecha("Fecha de Alta");
        Categoria categoria = Utils_IO.pedirCategoria();
        Uso uso = Utils_IO.pedirUso();
        Marca marca= Utils_IO.pedirMarca();
        String modelo = Utils_IO.pedirString("el modelo",false);
        Conductor conductor = pedirConductor("propietario");
        switch (categoria){
            case AUTO: automotor = altaAuto(uso,conductor,fechaAlta,dominio,marca,modelo); break;
            case AUTO_ELECTRICO: automotor = altaAutoElectrico(uso,conductor,fechaAlta,dominio,marca,modelo); break;
            case CAMION: automotor = altaCamion(uso,conductor,fechaAlta,dominio,marca,modelo); break;
            case COLECTIVO: automotor = altaColectivo(uso,conductor,fechaAlta,dominio,marca,modelo); break;
            case MOTO: automotor = altaMoto(uso,conductor,fechaAlta,dominio,marca,modelo); break;
            case MOTO_ELECTRICA: automotor = altaMotoElectrica(uso,conductor,fechaAlta,dominio,marca,modelo); break;
            case UTILITARIO: automotor = altaUtilitario(uso,conductor,fechaAlta,dominio,marca,modelo); break;
        }
        return automotor;
    }

    private Automotor altaAuto(Uso uso,Conductor conductor, LocalDate fechaAlta,String dominio,Marca marca,String modelo) {
        Automotor automotor;
        Combustible tipoCombustible=null;
        Integer litrosCapacidadTanque=0;
        Utils_IO.pedirDatosVehiculoCombustion(tipoCombustible,litrosCapacidadTanque);
        Integer cantidadPuertas= Utils_IO.pedirInteger("Cantidad de puertas");
        if (dominio.length()>0) {
            try {
                automotor = (Automotor) new Auto(uso,conductor,fechaAlta,dominio,marca,modelo,tipoCombustible,litrosCapacidadTanque,cantidadPuertas);
            } catch (DatosDominioException e) { automotor=null; }
        }
        else
            automotor = (Automotor) new Auto(uso,conductor,marca,modelo,tipoCombustible,litrosCapacidadTanque,cantidadPuertas);
        return automotor;
    }

    private Automotor altaAutoElectrico(Uso uso,Conductor conductor, LocalDate fechaAlta,String dominio,Marca marca,String modelo) {
        Automotor automotor;
        String voltaje="";
        Integer horasCargaCompleta=0;
        Utils_IO.pedirDatosVehiculoElectrico(voltaje,horasCargaCompleta);
        if (dominio.length()>0) {
            try {
                automotor = (Automotor) new Auto_Electrico(uso,conductor,fechaAlta,dominio,marca,modelo,voltaje,horasCargaCompleta);
            } catch (DatosDominioException e) { automotor=null; }
        }
        else
            automotor = (Automotor) new Auto_Electrico(uso,conductor,marca,modelo,voltaje,horasCargaCompleta);
        return automotor;
    }

    private Automotor altaCamion(Uso uso,Conductor conductor, LocalDate fechaAlta,String dominio,Marca marca,String modelo) {
        Automotor automotor;
        Combustible tipoCombustible=null;
        Integer litrosCapacidadTanque=0;
        Utils_IO.pedirDatosVehiculoCombustion(tipoCombustible,litrosCapacidadTanque);
        Integer capacidadCarga= Utils_IO.pedirInteger("Capacidad de Carga");
        Integer cantidadEjes= Utils_IO.pedirInteger("Cantidad de Ejes");
        if (dominio.length()>0) {
            try {
                automotor = (Automotor) new Camion(uso,conductor,fechaAlta,dominio,marca,modelo,tipoCombustible,litrosCapacidadTanque,capacidadCarga,cantidadEjes);
            } catch (DatosDominioException e) { automotor=null; }
        }
        else
            automotor = (Automotor) new Camion(uso,conductor,marca,modelo,tipoCombustible,litrosCapacidadTanque,capacidadCarga,cantidadEjes);
        return automotor;
    }

    private Automotor altaColectivo(Uso uso,Conductor conductor, LocalDate fechaAlta,String dominio,Marca marca,String modelo) {
        Automotor automotor;
        Combustible tipoCombustible=null;
        Integer litrosCapacidadTanque=0;
        Utils_IO.pedirDatosVehiculoCombustion(tipoCombustible,litrosCapacidadTanque);
        Integer capacidadPasajeros= Utils_IO.pedirInteger("Cantidad de Pasajeros");
        if (dominio.length()>0) {
            try {
                automotor = (Automotor) new Colectivo(uso,conductor,fechaAlta,dominio,marca,modelo,tipoCombustible,litrosCapacidadTanque,capacidadPasajeros);
            } catch (DatosDominioException e) { automotor=null; }
        }
        else
            automotor = (Automotor) new Colectivo(uso,conductor,marca,modelo,tipoCombustible,litrosCapacidadTanque,capacidadPasajeros);
        return automotor;
    }

    private Automotor altaMoto(Uso uso,Conductor conductor, LocalDate fechaAlta,String dominio,Marca marca,String modelo) {
        Automotor automotor;
        Combustible tipoCombustible=null;
        Integer litrosCapacidadTanque=0;
        Utils_IO.pedirDatosVehiculoCombustion(tipoCombustible,litrosCapacidadTanque);
        Integer cilindrada= Utils_IO.pedirInteger("Cilindrada");
        if (dominio.length()>0) {
            try {
                automotor = (Automotor) new Moto(uso,conductor,fechaAlta,dominio,marca,modelo,tipoCombustible,litrosCapacidadTanque,cilindrada);
            } catch (DatosDominioException e) { automotor=null; }
        }
        else
            automotor = (Automotor) new Moto(uso,conductor,marca,modelo,tipoCombustible,litrosCapacidadTanque,cilindrada);
        return automotor;
    }

    private Automotor altaMotoElectrica(Uso uso,Conductor conductor, LocalDate fechaAlta,String dominio,Marca marca,String modelo) {
        Automotor automotor;
        String voltaje="";
        Integer horasCargaCompleta=0;
        Utils_IO.pedirDatosVehiculoElectrico(voltaje,horasCargaCompleta);
        if (dominio.length()>0) {
            try {
                automotor = (Automotor) new Moto_Electrica(uso,conductor,fechaAlta,dominio,marca,modelo,voltaje,horasCargaCompleta);
            } catch (DatosDominioException e) { automotor=null; }
        }
        else
            automotor = (Automotor) new Moto_Electrica(uso,conductor,marca,modelo,voltaje,horasCargaCompleta);
        return automotor;
    }

    private Automotor altaUtilitario(Uso uso,Conductor conductor, LocalDate fechaAlta,String dominio,Marca marca,String modelo) {
        Automotor automotor;
        Combustible tipoCombustible=null;
        Integer litrosCapacidadTanque=0;
        Utils_IO.pedirDatosVehiculoCombustion(tipoCombustible,litrosCapacidadTanque);
        Integer capacidadCarga= Utils_IO.pedirInteger("Capacidad de Carga");
        if (dominio.length()>0) {
            try {
                automotor = (Automotor) new Utilitario(uso,conductor,fechaAlta,dominio,marca,modelo,tipoCombustible,litrosCapacidadTanque,capacidadCarga);
            } catch (DatosDominioException e) { automotor=null; }
        }
        else
            automotor = (Automotor) new Utilitario(uso,conductor,marca,modelo,tipoCombustible,litrosCapacidadTanque,capacidadCarga);
        return automotor;
    }

    private void agregarAutorizadosAlta(Automotor automotorNuevo) {
        Boolean continuar = true;
        do {
            Conductor conductor = pedirConductor("autorizado");
            if (conductor != null) {
                automotorNuevo.agregarAutorizado(conductor);
                Utils_IO.imprimirSeparadorSecundario();
                System.out.println("*** Autorizado del dominio " + automotorNuevo.getDominio() + " agregado con Éxito ***");
            }
            continuar= Utils_IO.imprimirPreguntaSiNo("Desea agregar otro autorizados?");
        } while (continuar);
    }

    public void cargarDatosInicialesPrueba(){

        //Conductores
        Conductor conductor1 = new Conductor("Pedro Gomez", "121212");
        Conductor conductor2 = new Conductor("Juan Miro", "343342");
        Conductor conductor3 = new Conductor("Maria Lopez", "12645612");
        Conductor conductor4 = new Conductor("Alberto Marin", "345232");
        conductor1.setDomicilio("Maipu 1111 - Vicente Lopez");
        conductor2.setDomicilio("Gaona 3223 - CABA");
        conductor3.setDomicilio("Mitre 13223 - San Martin");
        conductor4.setDomicilio("Fleming 434 - Martinez");


        // automotores viejos
        Auto auto1 = new Auto(Uso.PARTICULAR,conductor1,Marca.FORD,"Focus",Combustible.NAFTA,55,2);
        auto1.agregarAutorizado(conductor2);
        auto1.agregarAutorizado(conductor3);

        Moto moto1 = new Moto(Uso.PARTICULAR,conductor1,Marca.HONDA,"CB125R",Combustible.NAFTA,15,5);
        moto1.agregarAutorizado(conductor2);

        Utilitario utilitario1 = new Utilitario(Uso.PROFESIONAL,conductor4,Marca.PEUGEOT,"Patagónica",Combustible.DIESEL,70,200);
        utilitario1.agregarAutorizado(conductor3);

        Auto_Electrico auto2 = new Auto_Electrico(Uso.PARTICULAR,conductor3,Marca.FIAT,"Cronos","12 volt",8);

        Camion camion1 = new Camion(Uso.PROFESIONAL,conductor4,Marca.SCANIA,"R 470",Combustible.DIESEL,150,400,3);

        Colectivo colectivo1 = new Colectivo(Uso.PROFESIONAL,conductor4,Marca.MERCEDES_BENZ,"AZZ",Combustible.DIESEL,150,40);

        //Registros
        Registro_Seccional registro1 = new Registro_Seccional("CABA", 1);
        Registro_Seccional registro2 = new Registro_Seccional("Cordoba", 23);
        registro1.agregarAutomotor(auto1);
        registro1.agregarAutomotor(moto1);
        registro1.agregarAutomotor(utilitario1);
        registro2.agregarAutomotor(auto2);
        registro2.agregarAutomotor(camion1);
        registro2.agregarAutomotor(colectivo1);

        // automotores viejos
        Auto auto11 = null;
        try {
            auto11 = new Auto(Uso.PARTICULAR,conductor2, LocalDate.of(2019,2,3),"AA111AA",Marca.HONDA,"Fit", Combustible.NAFTA,55,2);
            auto11.agregarAutorizado(conductor1);
            auto11.agregarAutorizado(conductor4);
            registro2.agregarAutomotor(auto11);
        } catch (DatosDominioException e) {
            System.out.println(e.getMessage());
        }

        Moto moto11 = null;
        try {
            moto11 = new Moto(Uso.PARTICULAR,conductor4, LocalDate.of(2020,12,6),"AA111AB",Marca.HONDA,"CG 150 Titan",Combustible.NAFTA,15,5);
            moto11.agregarAutorizado(conductor3);
            registro2.agregarAutomotor(moto11);
        } catch (DatosDominioException e) {
            System.out.println(e.getMessage());
        }

        Utilitario utilitario11 = null;
        try {
            utilitario11 = new Utilitario(Uso.PROFESIONAL,conductor3, LocalDate.of(2010,8,13),"AA111AC",Marca.RENAULT,"Kangoo", Combustible.DIESEL,70,200);
            utilitario11.agregarAutorizado(conductor1);
            registro1.agregarAutomotor(utilitario11);
        } catch (DatosDominioException e) {
            System.out.println(e.getMessage());
        }

        Auto_Electrico auto21 = null;
        try {
            auto21 = new Auto_Electrico(Uso.PARTICULAR,conductor1, LocalDate.of(2000,5,23),"AA111AD",Marca.NISSAN,"X-TRAIL","12 volt",8);
            registro2.agregarAutomotor(auto21);
        } catch (DatosDominioException e) {
            System.out.println(e.getMessage());
        }

        Camion camion11 = null;
        try {
            camion11 = new Camion(Uso.PROFESIONAL,conductor1, LocalDate.of(2012,1,26),"AA111AE",Marca.MERCEDES_BENZ,"FPN", Combustible.DIESEL,150,400,3);
            registro1.agregarAutomotor(camion11);
        } catch (DatosDominioException e) {
            System.out.println(e.getMessage());
        }


        agregarRegistro(registro1);
        agregarRegistro(registro2);

    }

}
