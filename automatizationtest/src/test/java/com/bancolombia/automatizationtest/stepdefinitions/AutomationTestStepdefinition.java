package com.bancolombia.automatizationtest.stepdefinitions;

import com.bancolombia.automatizationtest.questions.Congrtulation;
import com.bancolombia.automatizationtest.tasks.LoginTask;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnlineCast;

import org.hamcrest.core.IsEqual;

import static com.bancolombia.automatizationtest.util.Constants.ACTOR;
import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class AutomationTestStepdefinition {

    @Before
    public void setup() {
        setTheStage(new OnlineCast());
        //theActorCalled(ACTOR).wasAbleTo(Open.url(URL_RIGO));
    }

    @Given("I want to go to the page {string}")
    public void iWantToGoToThePage(String url) {
        theActorCalled(ACTOR).wasAbleTo(Open.url(url));
    }

    @When("Login with the username {string} and password {string}")
    public void loginWithTheUsernameAndPassword(String user, String password) {
        theActorCalled(ACTOR).wasAbleTo(LoginTask.withCredential(user, password));
    }

    @Then("validate that the test completed successfully")
    public void validateThatTheTestCompletedSuccessfully() {
        theActorInTheSpotlight().should(seeThat(Congrtulation.finished(), IsEqual.equalTo(true)));
    }
}