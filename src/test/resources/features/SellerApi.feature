Feature: Test seller api

  @getSellerVerifyEmailNotEmpty @regression
  Scenario: get a single seller and verify seller's email is not empty
    Given user hits get single seller api with "/api/myaccount/sellers/"
    Then verify seller email is not empty

  @getAllSellers @regression
  Scenario: get all sellers and verify seller id not 0
    Given user hits get all seller api with "/api/myaccount/sellers"
    Then verify sellers ids are not equal 0

  @updateSeller @regression
  Scenario: get single seller, update the same seller, verify seller was updated
    Given user hits get single seller api with "/api/myaccount/sellers/"
    Then verify seller email is not empty
    Then user hits PUT api with "/api/myaccount/sellers/"
    Then verify user email was updated
    And verify user first name was updated

  @archiveSeller @regression
  Scenario: get a single seller and archive the seller and verify seller was archived
    Given user hits get single seller api with "/api/myaccount/sellers/"
    Then user hits archive api with "/api/myaccount/sellers/archive/unarchive"
    Then user hit get all seller api with "/api/myaccount/sellers"

  @createDelete @regression
  Scenario: Create a seller, verify that sellers was created, delete a seller, verify seller was deleted
    Given user hits POST api with "/api/myaccount/sellers"
    Then verify seller ID was generated
    Then verify sellerName is not empty
    Then verify seller email is not empty
    Then DELETE the seller with "/api/myaccount/sellers/"
    Then user hits get all seller api with "/api/myaccount/sellers"
    Then verify deleted seller is not on the list




