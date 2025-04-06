Feature:  Testing tags

  @createVerifyTag
  Scenario: create and verify tag
    Given user hits Post with api "/api/myaccount/tags"
    Then user hits Get with api "/api/myaccount/tags" to get all tags
    Then verify that tag has been created

