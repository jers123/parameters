package com.bancolombia.automatizationtest.tasks;

import com.bancolombia.automatizationtest.interactions.Wait;
import com.bancolombia.automatizationtest.ui.Tabla;
import com.bancolombia.automatizationtest.ui.Test;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Text;

import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ButtonTask implements Task {

    private int index;

    public ButtonTask(int i) {
        this.index = i;
    }


    @Override
    public <T extends Actor> void performAs(T actor) {

        List<String> sep = new ArrayList<String>();
        sep.add("_");
        sep.add(".");
        sep.add("*");
        sep.add(";");
        sep.add("-");
        sep.add(",");

        /*List<WebElementFacade> botones;
        String search;
        //int i = -1;
        int i = botones.size();
        List<Integer> pos = new ArrayList<Integer>();
        do {
            i--;
            botones = Tabla.BTNS.resolveAllFor(actor);
            System.out.println(i + " - " + botones.get(i).getText());
            actor.attemptsTo(Click.on(botones.get(i))
                , Wait.thePause(1)
            );
            search = Text.of(Tabla.TXTE).asString().answeredBy(actor).trim();
            System.out.println(search);
        } while (search.equals("Ha cometido un error, intente de nuevo"));
        System.out.println("valor" + i);
        pos.add(i);*/


        List<WebElementFacade> botones = Tabla.BTNS.resolveAllFor(actor);
        int r = (int)Math.sqrt(botones.size());
        System.out.println(botones.size() + " - " + r);
        WebElementFacade[][] btns = new WebElementFacade[r][r];
        int pos = 0;
        String s = "";
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                btns[i][j] = botones.get(pos);
                pos++;
                if (btns[i][j].getText().length() == 1) {
                    s = s + btns[i][j].getText() + "   ";
                } else if (btns[i][j].getText().length() == 2) {
                    s = s + btns[i][j].getText() + "  ";
                } else {
                    s = s + btns[i][j].getText() + " ";
                }
            }
            s = s + "\n";
        }
        System.out.println(s);
        String search = Text.of(Tabla.TXT).asString().answeredBy(actor).trim();
        System.out.println(search);

        String res ="";
        for(int i = 1; i < search.length()-1; i++) {
            if(search.charAt(i) == ')') {
                res = res + "_";
                i = i+2;
            } else {
                res = res + search.charAt(i);
            }
        }
        System.out.println("Corregido " + res);

        String sp[] = res.split("_");
        System.out.println(sp.length);
        int x[] = new int[sp.length];
        int y[] = new int[sp.length];
        int px = 1;
        int py = 1;
        for (int i = 0; i < sp.length; i++) {
            String sp1[] = sp[i].split(",");
            x[i] = Integer.parseInt(sp1[0]);
            y[i] = Integer.parseInt(sp1[1]);
            px = px + x[i];
            py = py + y[i];
            if(px <= 0) {
                px = 1;
            }
            if(py <= 0) {
                py = 1;
            }
            if(px > r) {
                px = r;
            }
            if(py > r) {
                py = r;
            }
            //px = Math.abs(px);
            //py = Math.abs(py);
            System.out.println("POS " + x[i] + " - " + y[i]);
            System.out.println("" + px + " - " + py);
            System.out.println(btns[py-1][px-1].getText().trim());
        }

        int sum = Integer.parseInt(btns[py-1][px-1].getText().trim());
        if((px-1) > 0) {
            sum += Integer.parseInt(btns[py-1][px-2].getText().trim());
            if((py-1) > 0) {
                sum += Integer.parseInt(btns[py-2][px-2].getText().trim());
            }
            if((py+1) <= r) {
                sum += Integer.parseInt(btns[py][px-2].getText().trim());
            }
        }
        if((py-1) > 0) {
            sum += Integer.parseInt(btns[py-2][px-1].getText().trim());
        }
        if((py+1) <= r) {
            sum += Integer.parseInt(btns[py][px-1].getText().trim());
        }
        if((px+1) <= r) {
            sum += Integer.parseInt(btns[py-1][px].getText().trim());
            if((py-1) > 0) {
                sum += Integer.parseInt(btns[py-2][px].getText().trim());
            }
            if((py+1) <= r) {
                sum += Integer.parseInt(btns[py][px].getText().trim());
            }
        }
        System.out.println("suma " + sum);

        actor.attemptsTo(Click.on(btns[py-1][px-1]),
                Wait.thePause(1),
                Enter.theValue(sum + "").into(Tabla.SUM),
                Click.on(Tabla.BTNE)
        );
        System.out.println(Text.of(Tabla.TXTE).asString().answeredBy(actor).trim());
    }

    public static ButtonTask search(int index) {
        return instrumented(ButtonTask.class, index);
    }
}