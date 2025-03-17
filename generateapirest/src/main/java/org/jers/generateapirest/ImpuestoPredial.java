package org.jers.generateapirest;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ImpuestoPredial {
    public static void main(String[] args) {
        List<Impuesto> impuestos = new ArrayList<>();
        impuestos.add(new Impuesto(2009, 0.00, 0.00, null, null, 0.00, 0.00));
        impuestos.add(new Impuesto(2010, 515000.00, 19526000.00, null, "01-00-0137-0037-909", 107900.00, 126900.00));
        impuestos.add(new Impuesto(2011, 535600.00, 20112000.00, null, "01-00-0137-0037-909", 111100.00, 130700.00));
        impuestos.add(new Impuesto(2012, 566700.00, 20715000.00, null, "01-00-0137-0037-909", 114500.00, 134600.00));
        impuestos.add(new Impuesto(2013, 598500.00, 21336000.00, null, "01-00-0137-0037-909", 117900.00, 138700.00));
        impuestos.add(new Impuesto(2014, 616000.00, 21976000.00, "25-899-01-00-00-00-0137-0037-9-09-00-0000", "01-00-0137-0037-909", 122800.00, 142844.00));
        impuestos.add(new Impuesto(2015, 644350.00, 22635000.00, "25-899-01-00-00-00-0137-0037-9-09-00-0000", "01-00-0137-0037-909", 126600.00, 148600.00));
        
        impuestos.add(new Impuesto(2016, 689455.00, 23314000.00, "25-899-01-00-00-00-0137-0037-9-09-00-0000", "01-00-0137-0037-909", 130300.00, 153000.00));
        
        impuestos.add(new Impuesto(2017, 737717.00, 24013000.00, "25-899-01-00-00-00-0137-0037-9-09-00-0000", "01-00-0137-0037-909", 144600.00, 169800.00));
        impuestos.add(new Impuesto(2018, 781242.00, 24733000.00, "25-899-01-00-00-00-0137-0037-9-09-00-0000", "01-00-0137-0037-909", 148900.00, 174800.00));
        impuestos.add(new Impuesto(2019, 828116.00, 25475000.00, "25-899-01-00-00-00-0137-0037-9-09-00-0000", "01-00-0137-0037-909", 153400.00, 180100.00));
        impuestos.add(new Impuesto(2020, 877803.00, 26239000.00, "25-899-01-00-00-00-0137-0037-9-09-00-0000", "01-00-0137-0037-909", 157900.00, 185500.00));
        impuestos.add(new Impuesto(2021, 908526.00, 27026000.00, "25-899-01-00-00-00-0137-0037-9-09-00-0000", "01-00-0137-0037-909", 162697.00, 191074.00));
        
        impuestos.add(new Impuesto(2022, 1160000.00, 27836780.00, "25-899-01-00-00-00-0137-0909-9-00-00-0037", "01-00-0137-0037-909", 167577.00, 196806.00));
        impuestos.add(new Impuesto(2023, 1300000.00, 29037000.00, "25-899-01-00-00-00-0137-0909-9-00-00-0037", "01-00-0137-0037-909", 174803.00, 205292.00));
        impuestos.add(new Impuesto(2024, 1423500.00, 30347000.00, "25-899-01-00-00-00-0137-0909-9-00-00-0037", "01-00-0137-0037-909", 182689.00, 214553.00));
        impuestos.add(new Impuesto(2025, 1500000.00, 93611000.00, "25-899-01-00-00-00-0137-0909-9-00-00-0037", "01-00-0137-0037-909", 237496.00, 278920.00));
        
        Impuesto impuesto = new Impuesto(2025, 0.00, 0.00, "25-899-01-00-00-00-0137-0909-9-00-00-0037", "01-00-0137-0037-909", 0.00, 0.00);
        
        for(int i = 1; i < impuestos.size(); i++) {
            impuesto.SetImpuesto(impuestos.get(i).getSalario(), impuestos.get(i).getAbaluo(), impuestos.get(i).getValor(), impuestos.get(i).getValorMax());
            System.out.println(impuestos.get(i).calculos(impuestos.get(i-1)));
        }
        System.out.println(impuestos.get(impuestos.size() - 1).calculos(impuestos.get(1)));
        impuesto.promedio(impuestos.get(impuestos.size() - 1));
        System.out.println(impuesto.calculos(impuestos.get(impuestos.size() - 1)));
    }
}

@Getter
class Impuesto {
    private int anio;
    private Double salario;
    private Double abaluo;
    private String catastro30;
    private String catastro;
    private Double valor;
    private Double valorMax;
    
    private Double salarioResta;
    private Double abaluoResta;
    private Double valorResta;
    private Double valorMaxResta;
    
    private Double salarioPorcentaje;
    private Double abaluoPorcentaje;
    private Double valorPorcentaje;
    private Double valorMaxPorcentaje;
    
    private Double restaValores;
    private Double porcentajeAvaluo;
    
    
    public Impuesto(int anio, Double salario, Double abaluo, String catastro30, String catastro, Double valor, Double valorMax) {
        this.anio = anio;
        this.salario = salario;
        this.abaluo = abaluo;
        this.catastro30 = catastro30;
        this.catastro = catastro;
        this.valor = valor;
        this.valorMax = valorMax;
    }

    public void SetImpuesto(Double salario, Double abaluo, Double valor, Double valorMax) {
        this.salario = this.salario + salario;
        this.abaluo = this.abaluo + abaluo;
        this.valor = this.valor + valor;
        this.valorMax = this.valorMax + valorMax;
    }
    
    public void promedio(Impuesto  impuesto) {
        this.salario = impuesto.getSalario();
        this.abaluo = impuesto.getAbaluo();
        this.valor = impuesto.getValor();
        this.valorMax = impuesto.getValorMax();
    }
    
    private void calcular(Impuesto impuesto) {
        salarioResta = salario - impuesto.getSalario();
        abaluoResta = abaluo - impuesto.getAbaluo();
        valorResta = valor - impuesto.getValor();
        valorMaxResta = valorMax - impuesto.getValorMax();
        salarioPorcentaje = porcentaje(impuesto.getSalario(), salario);
        //abaluoPorcentaje;
        valorPorcentaje = porcentaje(impuesto.getValor(), valor);
        valorMaxPorcentaje = porcentaje(impuesto.getValorMax(), valorMax);
        
        restaValores = valorMax - valor;
        porcentajeAvaluo = porcentajeBase(abaluo, valorMax);
    }
    
    private String formato(String tipo, Double valor) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(new Locale("es", "CO"));
        simbolos.setGroupingSeparator('.');
        simbolos.setDecimalSeparator(','); // Separador decimal
        DecimalFormat formato = new DecimalFormat("#,###.0", simbolos);
        return tipo + formato.format(valor);
    }
    
    private double porcentaje(Double menor, Double mayor) {
        return ((mayor * 100) / menor) - 100;
    }
    
    private Double porcentajeBase(Double mayor, Double menor) {
        return (menor * 100) / mayor;
    }
    
    public String calculos(Impuesto impuesto) {
        
        String text = "{\n";
        text = text + "\t" + anio + " - " + impuesto.getAnio() + "\n";
        text = text + "\t" + formato("SALARIO $", salario) + " - " + formato("$", salarioResta) + " - " + formato("%", salarioPorcentaje) + "\n";
        text = text + "\t" + formato("ABALUO $", abaluo) + " - " + formato("$", abaluoResta) + " - " + formato("%", porcentaje(impuesto.getAbaluo(), abaluo)) + "\n";
        text = text + "\t" + formato("VALOR MIN $", valor) + " - " + formato("$", valorResta) + " - " + formato("%", porcentaje(impuesto.getValor(), valor))
                + " - " + formato("%", porcentajeBase(abaluo, valor)) + "\n";
        text = text + "\t" + formato("VALOR MAX $", valorMax) + " - " + formato("$", valorMax - impuesto.getValorMax()) + " - " + formato("%", porcentaje(impuesto.getValorMax(), valorMax))
                + " - " + formato("$", valorMax - valor) + " - " + formato("%", porcentajeBase(abaluo, valorMax)) + "\n";
        text = text + "}";
        return text;
    }
}