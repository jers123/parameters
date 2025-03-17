package com.bancolombia.automatizationtest.ui;

import net.serenitybdd.screenplay.targets.Target;

public class Test {

    public static final Target LABEL_ERROR = Target.the("Texto de error").locatedBy("/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/p[1]");

    // ICONS
    public static final Target LABEL_ICON = Target.the("Imagen a comparar").locatedBy("/html[1]/body[1]/div[2]/form[1]/div[1]/div[1]/div[1]/p[1]");
    public static final Target LABEL_ICONS = Target.the("Imagenes").locatedBy("/html[1]/body[1]/div[2]/form[1]/div[1]/div[1]/div[1]/p[2]");
    public static final Target TXT_NUMBER = Target.the("campo de numero encontrados").locatedBy("/html[1]/body[1]/div[2]/form[1]/div[1]/div[1]/input[1]");

    //MULT 7
    public static final Target LABEL_MULT = Target.the("Numero al que sacar multiplos").locatedBy("/html[1]/body[1]/div[2]/form[1]/div[1]/div[3]/p[1]");
    public static final Target LABELS_MULT = Target.the("Textos multiplos de 7").locatedBy("/html[1]/body[1]/div[2]/form[1]/div[1]/div[3]/div[1]/label");
    public static final Target CBS_MULT = Target.the("checkbox multiplos de 7").locatedBy("/html[1]/body[1]/div[2]/form[1]/div[1]/div[3]/div[1]/label/input[1]");

    // DATE
    public static final Target LABEL_DATE = Target.the("Texto a calcular la fecha").locatedBy("/html[1]/body[1]/div[2]/form[1]/div[1]/div[2]/p[1]");
    public static final Target TXT_DATE = Target.the("campo de fecha").locatedBy("/html[1]/body[1]/div[2]/form[1]/div[1]/div[2]/input[1]");

    // OPERATION
    public static final Target LABEL_CALCULATE = Target.the("Texto de operacion matematica").locatedBy("/html[1]/body[1]/div[2]/form[1]/div[1]/div[4]/p[2]");
    public static final Target SEL_CALCULATE = Target.the("Lista de resultados").locatedBy("/html[1]/body[1]/div[2]/form[1]/div[1]/div[4]/select[1]");

    // SEND
    public static final Target BTN_SEND = Target.the("Boton enviar").locatedBy("/html[1]/body[1]/div[2]/form[1]/div[2]/button[1]");

    // FINISH
    public static final Target LABEL_FINISH = Target.the("Texto de felicitaciones").locatedBy("/html/body/div/div/h1");
    public static final Target LABEL_CODE = Target.the("Texto de codigo").locatedBy("/html/body/div/div/div/p");
}