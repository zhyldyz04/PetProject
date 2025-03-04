Feature: Login to saucedemo
  @login
  Scenario: Login to saucedemo and verify you logged in
    Given user is on the log in page
    Then user provide valid username
    And user provide valid password
    Then user clicks on login button
    And verify user logged in