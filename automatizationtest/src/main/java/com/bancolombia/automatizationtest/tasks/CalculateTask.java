package com.bancolombia.automatizationtest.tasks;

import com.bancolombia.automatizationtest.interactions.Wait;
import com.bancolombia.automatizationtest.ui.Test;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.questions.Text;

import java.util.ArrayList;
import java.util.List;

import static com.bancolombia.automatizationtest.util.Constants.TIME;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CalculateTask implements Task {

    private int i;

    public CalculateTask(int i) {
        this.i = i;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        List<Integer> r = new ArrayList<Integer>();
        r.add(48900);
        r.add(6960081);
        r.add(-190);
        r.add(58084);
        r.add(-136);
        r.add(510389);
        r.add(38959);
        r.add(58971);
        r.add(-78234);
        r.add(2375405);

        String op = Text.of(Test.LABEL_CALCULATE).asString().answeredBy(actor).trim();
        System.out.println(op);
        actor.attemptsTo(//Wait.thePause(30),
                SelectFromOptions.byVisibleText(r.get(i) + "").from(Test.SEL_CALCULATE)//,
                //Wait.thePause(TIME)
        );
        System.out.println(r.get(i));

        /*String op = Text.of(Test.LABEL_CALCULATE).asString().answeredBy(actor).trim();
        System.out.println(op);
        List<Integer> n = new ArrayList<>();
        List<String> o = new ArrayList<String>();
        String s[]= op.split("");
        String nn = "";
        for(int i = 0; i < s.length; i++) {
            if(s[i].equals("+") || s[i].equals("-") || s[i].equals("*") || s[i].equals("/")) {
                o.add(s[i]);
                n.add(Integer.parseInt(nn));
                nn = "";
            } else {
                nn = nn + s[i];
            }
            if(i == s.length-1) {
                n.add(Integer.parseInt(nn));
            }
            //System.out.println("L -" + s[i] + "- N -" + nn +"-");

        }
        //System.out.println("OP -" + o.size() + "- N -" + n.size() +"-");
        double r = 0;
        List<Double> rr = new ArrayList<Double>();
        for(int i = 0; i < o.size(); i++) {
            if(o.equals("*") || o.equals("/")) {
                if(i == 0) {
                    r = preop(o.get(i), n.get(i), n.get(i+1));
                    rr.add(r);
                } else {
                    r = preop(o.get(i), r, n.get(i+1));
                }

                n.remove(i);
                o.remove(i);
                n.remove(i+1);
            } else {

            }
        }
        for(int i = 0; i < o.size(); i++) {
            //System.out.println(i);
            if(i == 0) {
                r = preop(o.get(i), n.get(i), n.get(i+1));
            } else {
                r = preop(o.get(i), r, n.get(i+1));
            }

            //System.out.println(n.get(i) + " " + o.get(i) + " " + n.get(i+1) + " = " + r);
        }
        System.out.println(r);

        double asd = 71*199*250+121+221*94*165;
        System.out.println(asd);
        actor.attemptsTo(SelectFromOptions.byVisibleText((int)r + "").from(Test.SEL_CALCULATE),
                Wait.thePause(5)
        );*/
    }

    /*private double preop(String o, double n1, int n2) {
        double r = 0;
        if(o.equals("+")) {
            r = n1 + n2;
        } else if(o.equals("-")) {
            r = n1 - n2;
        } else if(o.equals("*")) {
            r = n1 * n2;
        } else {
            r = n1 / n2;
        }
        System.out.println(n1 + " " + o + " " + n2 + " = " + r);
        return r;
    }*/

    public static CalculateTask operationsMath(int i) {
        return instrumented(CalculateTask.class, i);
    }
}