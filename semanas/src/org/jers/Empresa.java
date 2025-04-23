package org.jers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class Empresa {
	private String nombre;
	private LocalDate inicio;
	private LocalDate fin;
	private boolean pagado;
	private double diaSemana = 1.0 / 7.0;
	private double mesSemana = Math.round((30.0 / 7.0) * 100.0) / 100.0;
	private BigDecimal semanas;

	public Empresa(String nombre, LocalDate inicio, LocalDate fin) {
		this.nombre = nombre;
		this.inicio = inicio;
		this.fin = fin;
	}

	public BigDecimal getSemanas() {
		return semanas;
	}

	public double setSemanas() {
		double sem = 0.0;
		int dias = calcularDiasTrabajados();
		sem = dias / 7.0;
		semanas = redondearDosDecimales(sem);
		System.out.println("dias: " + dias);
		System.out.println(nombre + " -- " + inicio + " - " + fin + " - total: " + semanas);
		return sem;
	}

	private BigDecimal redondearDosDecimales(double valor) {
		BigDecimal bd = new BigDecimal(Double.toString(valor));
		bd = bd.setScale(2, RoundingMode.HALF_EVEN);
		return bd;
	}

	private int calcularDiasTrabajados() {
		if (inicio.getMonth() == fin.getMonth() && inicio.getYear() == fin.getYear()) {
			return calcularDiasMismoMes();
		} else {
			int diasPrimerMes = calcularDiasDesdeInicio();
			int diasUltimoMes = calcularDiasHastaFin();
			int mesesIntermedios = (int) ChronoUnit.MONTHS.between(inicio.withDayOfMonth(1), fin.withDayOfMonth(1)) - 1;
			return diasPrimerMes + diasUltimoMes + (mesesIntermedios * 30);
		}
	}

	// Cálculo de días trabajados si las fechas están en el mismo mes
	private int calcularDiasMismoMes() {
		int diaInicio = inicio.getDayOfMonth();
		int diaFin = fin.getDayOfMonth();

		// Casos especiales para febrero y meses de 31 días
		if (inicio.getMonth() == Month.FEBRUARY && esUltimoDiaFebrero(fin)) {
			return 30;
		} else if (tiene31Dias(inicio.getMonth()) && diaFin == 31) {
			return 30 - diaInicio + 1;
		} else {
			return diaFin - diaInicio + 1;
		}
	}

	// Cálculo de días desde el inicio del trabajo hasta el fin del mes de inicio
	private int calcularDiasDesdeInicio() {
		int diaInicio = inicio.getDayOfMonth();
		Month mes = inicio.getMonth();
		int diasDelMes = mes.length(inicio.isLeapYear());

		if (mes == Month.FEBRUARY || diasDelMes == 31) {
			return 30 - (diaInicio - 1);
		} else {
			return diasDelMes - (diaInicio - 1);
		}
	}

	// Cálculo de días trabajados en el mes de fin
	private int calcularDiasHastaFin() {
		int diaFin = fin.getDayOfMonth();
		if (fin.getMonth() == Month.FEBRUARY && esUltimoDiaFebrero(fin)) {
			return 30;
		} else if (tiene31Dias(fin.getMonth()) && diaFin == 31) {
			return 30;
		} else {
			return diaFin;
		}
	}

	// Verifica si una fecha es el último día de febrero
	private boolean esUltimoDiaFebrero(LocalDate fecha) {
		return fecha.getMonth() == Month.FEBRUARY && (fecha.getDayOfMonth() == 28 || fecha.getDayOfMonth() == 29);
	}

	// Verifica si un mes tiene 31 días
	private boolean tiene31Dias(Month mes) {
		return mes == Month.JANUARY || mes == Month.MARCH || mes == Month.MAY || mes == Month.JULY
				|| mes == Month.AUGUST || mes == Month.OCTOBER || mes == Month.DECEMBER;
	}
}