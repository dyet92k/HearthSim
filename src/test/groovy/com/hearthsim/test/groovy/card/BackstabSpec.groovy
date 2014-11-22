package com.hearthsim.test.groovy.card

import static com.hearthsim.model.PlayerSide.CURRENT_PLAYER
import static com.hearthsim.model.PlayerSide.WAITING_PLAYER
import static org.junit.Assert.*

import com.hearthsim.card.minion.concrete.WarGolem
import com.hearthsim.card.spellcard.concrete.Backstab
import com.hearthsim.model.BoardModel
import com.hearthsim.test.helpers.BoardModelBuilder
import com.hearthsim.util.tree.HearthTreeNode

class BackstabSpec extends CardSpec {

	HearthTreeNode root
	BoardModel startingBoard

	def setup() {

		startingBoard = new BoardModelBuilder().make {
			currentPlayer {
				hand([Backstab])
				mana(0)
			}
			waitingPlayer {
				field([[minion: WarGolem], [minion: WarGolem, health: 3]])
			}
		}

		root = new HearthTreeNode(startingBoard)
	}

	def "can target undamaged minion"() {
		def copiedBoard = startingBoard.deepCopy()
		def target = root.data_.getCharacter(WAITING_PLAYER, 1)
		def theCard = root.data_.getCurrentPlayerCardHand(0)
		def ret = theCard.useOn(WAITING_PLAYER, target, root, null, null)

		expect:
		assertEquals(root, ret);

		assertBoardDelta(copiedBoard, ret.data_) {
			currentPlayer {
				removeCardFromHand(Backstab)
			}
			waitingPlayer {
				updateMinion(0, [deltaHealth: -2])
			}
		}
	}

	def "cannot target damaged minion"() {
		def copiedBoard = startingBoard.deepCopy()
		def target = root.data_.getCharacter(WAITING_PLAYER, 2)
		def theCard = root.data_.getCurrentPlayerCardHand(0)
		def ret = theCard.useOn(WAITING_PLAYER, target, root, null, null)

		expect:
		assertNull(ret);

		assertBoardEquals(copiedBoard, root.data_)
	}
}
