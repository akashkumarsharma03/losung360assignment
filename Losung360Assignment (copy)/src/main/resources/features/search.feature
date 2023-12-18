Feature: Google Search Validation

  Scenario: Validate Amazon and Flipkart links on the search results page
    Given I open Google search page
    When I search for "Best Washing Machine"
    Then I validate the number of Amazon and Flipkart links on the first page
    And I navigate to each Amazon link and confirm the title and results count
    And I navigate to each Flipkart link and confirm the title and results count
    And I close the browser

