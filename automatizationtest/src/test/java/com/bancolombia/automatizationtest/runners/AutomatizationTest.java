package com.bancolombia.automatizationtest.runners;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
		glue= "com.bancolombia.automatizationtest.stepdefinitions",
		features= "src/test/resources/com/bancolombia/automationtest/features/evalart_login.feature",
		snippets= SnippetType.CAMELCASE
)
class AutomatizationTest {

}