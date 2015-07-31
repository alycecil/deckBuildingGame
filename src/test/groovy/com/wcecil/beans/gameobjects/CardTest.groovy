package com.wcecil.beans.gameobjects;

import static org.junit.Assert.*

import org.junit.Test

import com.wcecil.actions.initial.LoadCards
import com.wcecil.beans.GameState


class CardTest {

	@Test
	void testCardFile() {
		def cost = 1
		def money = 2
		def draw = 3
		def value = 4
		def startingCount = 5
		def deckCount = 6
		def staticCount = 7

		def name =  "TestCard"
		def description = 'dëscríptïon'

		def newCost = 99

		def scriptRaw = """
card.cost = $newCost
assert source != null
assert target != null
assert game != null
println 'Hello Cards From File'
"""
		def parent = new File(this.getClass().getClassLoader().getResource('').toURI())
		def scriptFile = "scripts/test_${System.currentTimeMillis()}.script"
		def scriptFile_File = new File(parent,scriptFile)
		assertTrue scriptFile_File.createNewFile()
		scriptFile_File.write(scriptRaw)
		scriptFile_File.deleteOnExit()

		def testCardJSON = """
[  
   {  
      "cost":$cost,
      "money":$money,
      "draw":$draw,
      "value":$value,
      "name":"$name",
      "description":"$description",
      "startingCount":$startingCount,
      "deckCount":$deckCount,
      "staticCount":$staticCount,
      "specialActionScript":"file:${scriptFile}"
   }
]
"""
		List<Card> cards = []
		new LoadCards().parseCardJSON(testCardJSON, cards, null)

		assertFalse cards.isEmpty()
		assertEquals cards.size(), 1
		
		def game = new GameState()
		def source = new Player()
		def target = new Player()
		game.players<<source
		game.players<<target
		game.currentPlayer = source
		
		cards.each { Card c ->
			verifyCard(c, money, cost, draw, value, name, description, staticCount, deckCount, startingCount, scriptRaw)

			def real = new ActualCard(c)
			verifyCard(real, money, cost, draw, value, name, description, staticCount, deckCount, startingCount, scriptRaw)

			def result = real.specialAction(game, source, target)

			assertEquals c.cost, newCost
		}
	}


	@Test
	void testCard() {

		def cost = 1
		def money = 2
		def draw = 3
		def value = 4
		def startingCount = 5
		def deckCount = 6
		def staticCount = 7

		def name =  "TestCard"
		def description = 'dëscríptïon'

		def newCost = 99

		def script = """
card.cost = $newCost
println 'Hello Cards'
"""

		def testCardJSON = """
[  
   {  
      "cost":$cost,
      "money":$money,
      "draw":$draw,
      "value":$value,
      "name":"$name",
      "description":"$description",
      "startingCount":$startingCount,
      "deckCount":$deckCount,
      "staticCount":$staticCount,
      "specialActionScript":"${script.replaceAll("\\r?\\n", '\\\\n')}"
   }
]
"""

		List<Card> cards = []
		new LoadCards().parseCardJSON(testCardJSON, cards, null)

		assertFalse cards.isEmpty()
		assertEquals cards.size(), 1

		def game = new GameState()
		def source = new Player()
		def target = new Player()
		game.players<<source
		game.players<<target
		game.currentPlayer = source

		cards.each { Card c ->
			verifyCard(c, money, cost, draw, value, name, description, staticCount, deckCount, startingCount, script)

			def real = new ActualCard(c)
			verifyCard(real, money, cost, draw, value, name, description, staticCount, deckCount, startingCount, script)

			def result = real.specialAction(game, source, target)

			assertEquals c.cost, newCost
		}
	}

	private verifyCard(Card c, int money, int cost, int draw, int value, String name, String description, int staticCount, int deckCount, int startingCount, String script) {
		assertEquals c.money, money
		assertEquals c.cost, cost
		assertEquals c.draw, draw
		assertEquals c.value, value
		assertEquals c.name, name
		assertEquals c.description, description
		assertEquals c.staticCount, staticCount
		assertEquals c.deckCount, deckCount
		assertEquals c.startingCount, startingCount
		assertEquals c.specialActionScript, script
	}
}