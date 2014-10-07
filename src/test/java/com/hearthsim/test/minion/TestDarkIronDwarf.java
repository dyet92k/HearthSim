package com.hearthsim.test.minion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.concrete.DarkIronDwarf;
import com.hearthsim.card.minion.concrete.BoulderfistOgre;
import com.hearthsim.card.minion.concrete.RaidLeader;
import com.hearthsim.card.minion.concrete.ScarletCrusader;
import com.hearthsim.card.spellcard.concrete.TheCoin;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class TestDarkIronDwarf {

	private HearthTreeNode board;
	private Deck deck;

	@Before
	public void setup() {
		board = new HearthTreeNode(new BoardModel());

		Minion minion0_0 = new BoulderfistOgre();
		Minion minion0_1 = new RaidLeader();
		Minion minion1_0 = new BoulderfistOgre();
		Minion minion1_1 = new RaidLeader();
		Minion minion1_2 = new ScarletCrusader();
		
		board.data_.placeCardHandCurrentPlayer(minion0_0);
		board.data_.placeCardHandCurrentPlayer(minion0_1);
				
		board.data_.placeCardHandWaitingPlayer(minion1_0);
		board.data_.placeCardHandWaitingPlayer(minion1_1);
		board.data_.placeCardHandWaitingPlayer(minion1_2);

		Card cards[] = new Card[10];
		for (int index = 0; index < 10; ++index) {
			cards[index] = new TheCoin();
		}
	
		deck = new Deck(cards);

		Card fb = new DarkIronDwarf();
		board.data_.placeCardHandCurrentPlayer(fb);

		board.data_.getCurrentPlayer().setMana((byte)8);
		board.data_.getWaitingPlayer().setMana((byte)8);
		
		board.data_.getCurrentPlayer().setMaxMana((byte)8);
		board.data_.getWaitingPlayer().setMaxMana((byte)8);
		
		HearthTreeNode tmpBoard = new HearthTreeNode(board.data_.flipPlayers());
		try {
			tmpBoard.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, tmpBoard.data_.getCurrentPlayerHero(), tmpBoard, deck, null);
			tmpBoard.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, tmpBoard.data_.getCurrentPlayerHero(), tmpBoard, deck, null);
			tmpBoard.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, tmpBoard.data_.getCurrentPlayerHero(), tmpBoard, deck, null);
		} catch (HSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board = new HearthTreeNode(tmpBoard.data_.flipPlayers());
		try {
			board.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, board.data_.getCurrentPlayerHero(), board, deck, null);
			board.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, board.data_.getCurrentPlayerHero(), board, deck, null);
		} catch (HSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.data_.resetMana();
		board.data_.resetMinions();
		
	}
	
	

	@Test
	public void test0() throws HSException {
		
		//null case
		Minion target = board.data_.getCharacter(PlayerSide.WAITING_PLAYER, 0);
		Card theCard = board.data_.getCurrentPlayerCardHand(0);
		HearthTreeNode ret = theCard.useOn(PlayerSide.WAITING_PLAYER, target, board, deck, null);
		
		assertTrue(ret == null);
		assertEquals(board.data_.getNumCards_hand(), 1);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getNumMinions(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getNumMinions(), 3);
		assertEquals(board.data_.getCurrentPlayer().getMana(), 8);
		assertEquals(board.data_.getWaitingPlayer().getMana(), 8);
		assertEquals(board.data_.getCurrentPlayerHero().getHealth(), 30);
		assertEquals(board.data_.getWaitingPlayerHero().getHealth(), 30);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(0).getHealth(), 2);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(1).getHealth(), 7);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(0).getHealth(), 1);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(1).getHealth(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(2).getHealth(), 7);

		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(0).getTotalAttack(), 2);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(1).getTotalAttack(), 7);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(0).getTotalAttack(), 4);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(1).getTotalAttack(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(2).getTotalAttack(), 7);
		
		assertTrue(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(0).getDivineShield());
	}
	
	@Test
	public void test2() throws HSException {
		
		Minion target = board.data_.getCharacter(PlayerSide.CURRENT_PLAYER, 2);
		Card theCard = board.data_.getCurrentPlayerCardHand(0);
		HearthTreeNode ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, target, board, deck, null);
		
		assertFalse(ret == null);
		assertEquals(board.data_.getNumCards_hand(), 0);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getNumMinions(), 3);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getNumMinions(), 3);
		assertEquals(board.data_.getCurrentPlayer().getMana(), 4);
		assertEquals(board.data_.getWaitingPlayer().getMana(), 8);
		assertEquals(board.data_.getCurrentPlayerHero().getHealth(), 30);
		assertEquals(board.data_.getWaitingPlayerHero().getHealth(), 30);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(0).getHealth(), 2);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(1).getHealth(), 7);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(2).getHealth(), 4);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(0).getHealth(), 1);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(1).getHealth(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(2).getHealth(), 7);

		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(0).getTotalAttack(), 2);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(1).getTotalAttack(), 7);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(2).getTotalAttack(), 5);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(0).getTotalAttack(), 4);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(1).getTotalAttack(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(2).getTotalAttack(), 7);

		//At this point, the BoardState should have 5 children: 2 buffs on friendly side
		//and 3 buffs on enemy side
		assertEquals(board.numChildren(), 5);
		
		assertEquals(board.getChildren().get(0).data_.getCurrentPlayer().getMinions().get(0).getExtraAttackUntilTurnEnd(), 2);
		assertEquals(board.getChildren().get(1).data_.getCurrentPlayer().getMinions().get(1).getExtraAttackUntilTurnEnd(), 2);
		assertEquals(board.getChildren().get(2).data_.getWaitingPlayer().getMinions().get(0).getExtraAttackUntilTurnEnd(), 2);
		assertEquals(board.getChildren().get(3).data_.getWaitingPlayer().getMinions().get(1).getExtraAttackUntilTurnEnd(), 2);
		
		//make sure that the extra attack is working!
		HearthTreeNode child1 = board.getChildren().get(0);
		target = child1.data_.getCharacter(PlayerSide.WAITING_PLAYER, 0);
		Minion minion = child1.data_.getCurrentPlayer().getMinions().get(0);
		minion.attack(PlayerSide.WAITING_PLAYER, target, child1, deck, null);
		assertEquals(child1.data_.getNumCards_hand(), 0);
		assertEquals(child1.data_.getCurrentPlayer().getNumMinions(), 3);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(child1).getNumMinions(), 3);
		assertEquals(child1.data_.getCurrentPlayer().getMana(), 4);
		assertEquals(child1.data_.getWaitingPlayer().getMana(), 8);
		assertEquals(child1.data_.getCurrentPlayerHero().getHealth(), 30);
		assertEquals(child1.data_.getWaitingPlayerHero().getHealth(), 26);
		assertEquals(child1.data_.getCurrentPlayer().getMinions().get(0).getHealth(), 2);
		assertEquals(child1.data_.getCurrentPlayer().getMinions().get(1).getHealth(), 7);
		assertEquals(child1.data_.getCurrentPlayer().getMinions().get(2).getHealth(), 4);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(child1).getMinions().get(0).getHealth(), 1);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(child1).getMinions().get(1).getHealth(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(child1).getMinions().get(2).getHealth(), 7);

		assertEquals(child1.data_.getCurrentPlayer().getMinions().get(0).getTotalAttack(), 4);
		assertEquals(child1.data_.getCurrentPlayer().getMinions().get(1).getTotalAttack(), 7);
		assertEquals(child1.data_.getCurrentPlayer().getMinions().get(2).getTotalAttack(), 5);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(child1).getMinions().get(0).getTotalAttack(), 4);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(child1).getMinions().get(1).getTotalAttack(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(child1).getMinions().get(2).getTotalAttack(), 7);
		
	}
}
