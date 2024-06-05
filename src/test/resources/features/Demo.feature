Feature: WIX demo
  These Tests verify WIX android UI.

  @Smoke
  Scenario: Main screen for logged out user is shown
    Given a logged out user
    Then app main page is shown

#  @Smoke @Regression
#  Scenario: User skip and continue to login page
#    Given a logged out user
#    When user clicks on 'Tap to Reload'
#    Then user clicks on continue to skip
#    Then Login page is shown

