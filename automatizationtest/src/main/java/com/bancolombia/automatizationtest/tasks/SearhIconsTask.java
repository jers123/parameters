package com.bancolombia.automatizationtest.tasks;

import com.bancolombia.automatizationtest.interactions.Wait;
import com.bancolombia.automatizationtest.ui.Test;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Text;

import static com.bancolombia.automatizationtest.util.Constants.TIME;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SearhIconsTask implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        String search = Text.of(Test.LABEL_ICON).asString().answeredBy(actor).trim();
        search = search.replace("Indique cuantos ","");
        search = search.replace(" hay en la siguiente secuencia:","");
        String mns = Text.of(Test.LABEL_ICONS).asString().answeredBy(actor).trim();
        String[] ms = mns.split("");
        int c = 0;
        for(int i = 0; i < mns.length(); i=i+2) {
            if((ms[i]+ms[i+1]).equals(search)) {
                c++;
            }
        }
        System.out.println(search + " - " + c);
        actor.attemptsTo(Enter.theValue(c + "").into(Test.TXT_NUMBER)//,
                //Wait.thePause(TIME)
        );
    }

    public static SearhIconsTask search() {
        return instrumented(SearhIconsTask.class);
    }
}