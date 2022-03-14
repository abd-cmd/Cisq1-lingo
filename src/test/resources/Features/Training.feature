Feature: Lingo Trainer
  As a player
  I want to guss 5,6 and 7 letter words
  In order to prepare for my TV appearance

  Scenario Outline: Start a new round
    Given : I am playing a game
    And the round was won
    And the last word had "<previous length>" letters
    When : I start a new round
    Then : The word to guss has "<next length>" letters
    And : I should see the first letter

    Examples:
    | previous length | next length |
    | 5               | 6           |
    | 6               | 7           |
    | 7               | 5           |

  Scenario Outline: Guess a word
    Given : I am playing a game
    And the word to guess is "<word to guess>"
    When : I guess "<attempt>"
    Then : I get feedback "<feedback>"

    Examples:
      | word to guess       | attempt     | feedback                                        |
      | GROEP               | GEGROET     | INVALID,INVALID,INVALID,INVALID,INVALID         |
      | GROEP               | GENEN       | CORRECT,ABSENT,ABSENT,CORRECT,ABSENT            |
      | GROEP               | GERST       | CORRECT,PRESENT,PRESENT,ABSENT,ABSENT           |
      | GROEP               | GEDOE       | CORRECT,PRESENT,ABSENT,PRESENT,ABSENT           |
      | GROEP               | GROEP       | CORRECT,CORRECT,CORRECT,CORRECT,CORRECT         |






