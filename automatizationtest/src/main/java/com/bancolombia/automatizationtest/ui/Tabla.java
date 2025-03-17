package com.bancolombia.automatizationtest.ui;

import net.serenitybdd.screenplay.targets.Target;

public class Tabla {

    public static final Target TXTE = Target.the("error").locatedBy("/html[1]/body[1]/div[2]/div[2]/div[1]/div[1]/p[1]");
    public static final Target TXT = Target.the("posiciones").locatedBy("/html[1]/body[1]/div[2]/div[1]/p[2]");
    public static final Target BTNS = Target.the("botones numericos").locatedBy("/html[1]/body[1]/div[3]/form[1]/div[1]/button");
    public static final Target SUM = Target.the("suma").locatedBy("/html[1]/body[1]/div[4]/div[1]/div[1]/div[2]/div[1]/div[1]/form[1]/input[1]");
    public static final Target BTNE = Target.the("botone enviar").locatedBy("/html[1]/body[1]/div[4]/div[1]/div[1]/div[2]/div[1]/div[1]/form[1]/button[1]");
    public static final Target LB_CANT = Target.the("texto cantidad").locatedBy("/html[1]/body[1]/div[2]/div[2]/div[1]/div[1]/p[2]");
}