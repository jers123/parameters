package org.jers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //listar(miEmpresas());
        listar(miEmpresa());
        //listar(rocio2());

    }

    private static void listar(List<Empresa> empresas) {
        double sem = 0.0;
        BigDecimal semanas = new BigDecimal(Double.toString(0.0));
        semanas = semanas.setScale(2, RoundingMode.HALF_EVEN);
        for (Empresa empresa : empresas) {
            sem = sem + empresa.setSemanas();
        }
        System.out.println("semanas totales: " + sem + " - semanas totales: " + redondearDosDecimales(sem));
        System.out.println("");
    }

    private static BigDecimal redondearDosDecimales(double valor) {
        BigDecimal bd = new BigDecimal(Double.toString(valor));
        bd = bd.setScale(2, RoundingMode.HALF_EVEN);
        return bd;
    }

    private static List<Empresa> miEmpresas() {
        List<Empresa> empresas = new ArrayList<>();
        empresas.add(new Empresa("Alphil Sistemas", LocalDate.of(2015,2,1), LocalDate.of(2015,4,30)));
        empresas.add(new Empresa("IT Consultancy Group", LocalDate.of(2018,8,22), LocalDate.of(2018,8,31)));
        empresas.add(new Empresa("IT Consultancy Group", LocalDate.of(2018,9,1), LocalDate.of(2019,12,31)));
        empresas.add(new Empresa("IT Consultancy Group", LocalDate.of(2020,1,1), LocalDate.of(2020,1,15)));
        empresas.add(new Empresa("sohos solutions", LocalDate.of(2021,6,8), LocalDate.of(2021,6,30)));
        empresas.add(new Empresa("sohos solutions", LocalDate.of(2021,7,1), LocalDate.of(2021,7,31)));
        empresas.add(new Empresa("sohos solutions", LocalDate.of(2021,8,1), LocalDate.of(2021,8,6)));
        empresas.add(new Empresa("Capgemini Colombia", LocalDate.of(2021,10,7), LocalDate.of(2021,10,31)));
        empresas.add(new Empresa("Capgemini Colombia", LocalDate.of(2021,11,1), LocalDate.of(2022,1,31)));
        empresas.add(new Empresa("Capgemini Colombia", LocalDate.of(2022,2,1), LocalDate.of(2022,2,28)));
        empresas.add(new Empresa("Capgemini Colombia", LocalDate.of(2022,3,1), LocalDate.of(2022,12,31)));
        empresas.add(new Empresa("Capgemini Colombia", LocalDate.of(2023,1,1), LocalDate.of(2023,1,31)));
        empresas.add(new Empresa("Capgemini Colombia", LocalDate.of(2023,2,1), LocalDate.of(2023,2,28)));
        empresas.add(new Empresa("Capgemini Colombia", LocalDate.of(2023,3,1), LocalDate.of(2023,4,30)));
        empresas.add(new Empresa("Capgemini Colombia", LocalDate.of(2023,5,1), LocalDate.of(2023,7,31)));
        empresas.add(new Empresa("Capgemini Colombia", LocalDate.of(2023,8,1), LocalDate.of(2023,8,1)));
        empresas.add(new Empresa("Julian Enrique Rodriguez", LocalDate.of(2023,9,1), LocalDate.of(2023,12,31)));
        empresas.add(new Empresa("Julian Enrique Rodriguez", LocalDate.of(2024,1,1), LocalDate.of(2024,3,31)));
        empresas.add(new Empresa("Talycap Global", LocalDate.of(2024,4,15), LocalDate.of(2024,4,30)));
        empresas.add(new Empresa("Talycap Global", LocalDate.of(2024,5,1), LocalDate.of(2024,5,31)));
        empresas.add(new Empresa("Talycap Global", LocalDate.of(2024,6,1), LocalDate.of(2024,6,14)));
        empresas.add(new Empresa("Julian Enrique Rodriguez", LocalDate.of(2024,7,1), LocalDate.of(2024,9,30)));
        return empresas;
    }

    private static List<Empresa> miEmpresa() {
        List<Empresa> empresas = new ArrayList<>();
        empresas.add(new Empresa("Alphil Sistemas", LocalDate.of(2015,2,1), LocalDate.of(2015,4,30)));
        empresas.add(new Empresa("IT Consultancy Group", LocalDate.of(2018,8,22), LocalDate.of(2020,1,15)));
        empresas.add(new Empresa("sohos solutions", LocalDate.of(2021,6,8), LocalDate.of(2021,8,6)));
        empresas.add(new Empresa("Capgemini Colombia", LocalDate.of(2021,10,7), LocalDate.of(2023,8,1)));
        empresas.add(new Empresa("Julian Enrique Rodriguez", LocalDate.of(2023,9,1), LocalDate.of(2024,3,31)));
        empresas.add(new Empresa("Talycap Global", LocalDate.of(2024,4,15), LocalDate.of(2024,6,14)));
        empresas.add(new Empresa("Julian Enrique Rodriguez", LocalDate.of(2024,7,1), LocalDate.of(2024,12,31)));
        return empresas;
    }

    private static List<Empresa> rocio() {
        List<Empresa> empresas = new ArrayList<>();
        empresas.add(new Empresa("Worley", LocalDate.of(2022,2,1), LocalDate.of(2022,2,2)));
        empresas.add(new Empresa("Canpack", LocalDate.of(2022,2,17), LocalDate.of(2024,12,31)));
        empresas.add(new Empresa("Worley", LocalDate.of(2022,2,1), LocalDate.of(2022,2,6)));
        empresas.add(new Empresa("Canpack", LocalDate.of(2022,2,17), LocalDate.of(2024,12,31)));
        return empresas;
    }

    private static List<Empresa> rocio2() {
        List<Empresa> empresas = new ArrayList<>();
        empresas.add(new Empresa("Worley", LocalDate.of(2022,2,1), LocalDate.of(2022,2,6)));
        empresas.add(new Empresa("Canpack", LocalDate.of(2022,2,17), LocalDate.of(2024,11,30)));
        return empresas;
    }
}

/*3204

2920
30
30
31
31
30
31 */