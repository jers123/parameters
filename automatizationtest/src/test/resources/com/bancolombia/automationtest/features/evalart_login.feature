#Author: Julian Enrique Rodriguez Saavedra

Feature: automation test for Bancolombia
  I want to prove that I can log into ParaBank

  Scenario: Login in ParaNank
    Given I want to go to the page "https://tasks.evalartapp.com/automatization/"
    When Login with the username "731761" and password "10df2f32286b7120My0zLTE2NzEzNw==30e0c83e6c29f1c3"
    Then validate that the test completed successfully
