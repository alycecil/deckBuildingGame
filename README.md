# deckBuildingGame
REST Driven Deck Building Game Written in groovy

Game Uses simple common actions with optional scriptable enhancements to cards to extend the game.

Game uses two resources Victory Points and Money. Money allows you to buy more cards, Victory Points well win you the game. Money is scored when a card is played that adds money to your current turns pool, Victory Points are scored at the end of the game.

The game is over when the main deck is empty.

## Install instructions

### Local Enviroment
Have mondgodb installed (using 3.0.6 locally)
Have gradle installed (using gradle-2.6 locally)
Open mongo terminal
Run *.mongo in \deploy directory
run [gradle bootRun] to start up as spring boot

### Openshift
Create a DIY Gear
Add gear as remote
Clone gear master, replace gear master with desired checkin from deckBuildinGame
Push

#### Sample Gear
Openshift Small Gear : http://deckbuilder-alysforever.rhcloud.com/