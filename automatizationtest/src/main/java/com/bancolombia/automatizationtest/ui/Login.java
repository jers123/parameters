package com.bancolombia.automatizationtest.ui;

import net.serenitybdd.screenplay.targets.Target;

public class Login {

    public static final Target TXT_USER = Target.the("Campo de usuario").locatedBy("/html[1]/body[1]/div[1]/div[2]/form[1]/input[1]");
    public static final Target TXT_PASSWORD = Target.the("Campo de contrasena").locatedBy("/html[1]/body[1]/div[1]/div[2]/form[1]/input[2]");
    public static final Target BTN_LOGIN = Target.the("Boton de inicio de sesion ").locatedBy("/html[1]/body[1]/div[1]/div[2]/form[1]/button[1]");
}