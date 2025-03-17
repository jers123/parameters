package com.bancolombia.automatizationtest.questions;

import com.bancolombia.automatizationtest.ui.Test;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class Congrtulation implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        String res1 = "<General>c2fe0e21ce445033Mi00LTE2NzEzNw==df63afbf4da3d4b1";
        String res2 ="<General>c2fe0e21ce445033My0zLTk1MjMzNw==df63afbf4da3d4b1";
        String res3 ="<General>c2fe0e21ce445033My0zLTE2NzEzNw==df63afbf4da3d4b1";

        String fel = Text.of(Test.LABEL_FINISH).asString().answeredBy(actor).trim();
        System.out.println(fel);
        boolean r = fel.equals("Felicidades, has terminado la prueba exitosamente");
        fel = Text.of(Test.LABEL_CODE).asString().answeredBy(actor).trim();
        System.out.println(fel);
        r = r && fel.equals(res3);
        return r;
    }

    public static Congrtulation finished() {
        return new Congrtulation();
    }
}