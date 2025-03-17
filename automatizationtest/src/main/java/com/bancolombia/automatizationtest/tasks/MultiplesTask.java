package com.bancolombia.automatizationtest.tasks;

import com.bancolombia.automatizationtest.interactions.Wait;
import com.bancolombia.automatizationtest.ui.Test;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Text;

import java.util.List;

import static com.bancolombia.automatizationtest.util.Constants.TIME;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class MultiplesTask implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        List<WebElementFacade> labels = Test.LABELS_MULT.resolveAllFor(actor);
        List<WebElementFacade> checks = Test.CBS_MULT.resolveAllFor(actor);
        String search = Text.of(Test.LABEL_MULT).asString().answeredBy(actor).trim();
        search = search.replace("Selecciona todos los multiplos de ","");
        int n = Integer.parseInt(search);
        int number = 0;
        search = "";
        for (int i = 0; i < labels.size(); i++) {
            number = Integer.parseInt(labels.get(i).getText().trim());
            if ((number % n) == 0) {
                actor.attemptsTo(Click.on(checks.get(i)));
                search = search + " - " + number;
            }
        }
        System.out.println(search);
        //actor.attemptsTo(Wait.thePause(TIME));
    }

    public static MultiplesTask select() {
        return instrumented(MultiplesTask.class);
    }
}