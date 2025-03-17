package com.bancolombia.automatizationtest.tasks;

import com.bancolombia.automatizationtest.interactions.Wait;
import com.bancolombia.automatizationtest.ui.Test;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Text;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import static com.bancolombia.automatizationtest.util.Constants.TIME;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DateTask implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        String search = Text.of(Test.LABEL_DATE).asString().answeredBy(actor).trim();
        search = search.replace("Indique la fecha que corresponde a ","");
        String[] sp = search.split(" ");
        int d = 0;
        //System.out.println(sp[0] + " - " + sp[4]);
        if (sp[4].equals("el")) {
            d = Integer.parseInt(sp[0]);
        } else {
            d = Integer.parseInt(sp[0]) * -1;
        }
        //search = search.replace("dias contados desde el : ","");
        //System.out.println(search);
        sp = sp[sp.length-1].split("/");
        LocalDate f = LocalDate.of(Integer.parseInt(sp[2]), Integer.parseInt(sp[1]), Integer.parseInt(sp[0]));
        Period periodo = Period.ofDays(1).ofDays(d);
        f = f.plus(periodo);
        search = "" + f.getDayOfMonth();
        if(f.getDayOfMonth() < 10) {
            search = "0" + f.getDayOfMonth();
        }
        if(f.getMonthValue() < 10) {
            search = search + "0" + f.getMonthValue();
        } else {
            search = search + f.getMonthValue();
        }
        search = search + f.getYear();
        System.out.println(f.getDayOfMonth() + "/" + f.getMonthValue() + "/" + f.getYear());
        actor.attemptsTo(Enter.theValue(search).into(Test.TXT_DATE)//,
                //Wait.thePause(TIME)
        );
    }

    public static DateTask countDays() {
        return instrumented(DateTask.class);
    }
}