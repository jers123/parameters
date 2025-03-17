package com.bancolombia.automatizationtest.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Wait implements Interaction {

    private int seconds;

    public Wait(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        try {
            Thread.sleep((long)(seconds * 1000));
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    public static Wait thePause(int seconds) {
        return instrumented(Wait.class, seconds);
    }
}