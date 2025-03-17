package com.bancolombia.automatizationtest.interactions;

import com.bancolombia.automatizationtest.tasks.*;
import com.bancolombia.automatizationtest.ui.Tabla;
import com.bancolombia.automatizationtest.ui.Test;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Text;

import static com.bancolombia.automatizationtest.util.Constants.TIME;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Cicle implements Interaction {
    @Override
    public <T extends Actor> void performAs(T actor) {
        String serarch = Text.of(Tabla.LB_CANT).asString().answeredBy(actor).trim();
        int cant = ciclos(serarch);
        for (int i = 0; i < cant; i++) {
            System.out.println("");
            System.out.println("Accion = " + (i+1));
            actor.attemptsTo(//SearhIconsTask.search(),
                    //MultiplesTask.select(),
                    //DateTask.countDays(),
                    //CalculateTask.operationsMath(i),
                    //Click.on(Test.BTN_SEND),
                    ButtonTask.search(i),
                    Wait.thePause(TIME)
            );
            //serarch = Text.of(Test.LABEL_ERROR).asString().answeredBy(actor).trim();
            //System.out.println("-" + serarch + "-");
            //if (i == 9) {
            //    actor.attemptsTo(Wait.thePause(1));
            //}

            //if (!serarch.equals("Ciclos :")) {
            //    i = -1;
            //}
        }
    }

    private int ciclos(String txt) {
        System.out.println("cant = " + txt);
        txt = txt.replace("Se encuentra en el ciclo 1 de ", "");
        System.out.println("cant = " + txt);
        return Integer.parseInt(txt);
    }

    public static Cicle run() {
        return instrumented(Cicle.class);
    }
}