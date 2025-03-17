package com.bancolombia.automatizationtest.tasks;

import com.bancolombia.automatizationtest.interactions.Cicle;
import com.bancolombia.automatizationtest.interactions.Wait;
import com.bancolombia.automatizationtest.ui.Login;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static com.bancolombia.automatizationtest.util.Constants.TIME;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class LoginTask implements Task {

    private String strUser;
    private String strPassword;

    public LoginTask(String strUser, String strPassword) {
        this.strUser = strUser;
        this.strPassword = strPassword;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Enter.theValue(strUser).into(Login.TXT_USER),
                Enter.theValue(strPassword).into(Login.TXT_PASSWORD),
                Click.on(Login.BTN_LOGIN),
                Wait.thePause(TIME),
                Cicle.run()
                //ButtonTask.search()
        );
    }

    public static LoginTask withCredential(String strUser, String strPassword) {
        return instrumented(LoginTask.class, strUser, strPassword);
    }
}