package linkedlist;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class War{
	
	private final ImageIcon poker = new ImageIcon("poker.png");
	private final ImageIcon win = new ImageIcon("win.jpg");
	private final ImageIcon tie = new ImageIcon("tie.png");
	 
	private final ImageIcon smallPoker = new ImageIcon(
			poker.getImage().getScaledInstance(120, 100,  java.awt.Image.SCALE_SMOOTH)); 
	private final ImageIcon smallWin = new ImageIcon(
			win.getImage().getScaledInstance(120, 100,  java.awt.Image.SCALE_SMOOTH));
	private final ImageIcon smallTie = new ImageIcon(
			tie.getImage().getScaledInstance(120, 100,  java.awt.Image.SCALE_SMOOTH)); 
	
	private static class Card{
		
		private int num; 
		private String suit;
		
		
		public Card(int num, String suit) {
			this.num = num;
			this.suit = suit;
		}
		
		public String toString() {
			
			return num + " " + suit;
		}
		
	}
	
	
	public void shuffle(LinkedLists<Card> myDeck) {
		
		for(int i=1; i<=104; i++) {
			
			int index = (int)(Math.random()*52);
			Card temp = myDeck.remove(index);
			myDeck.add(temp);
		}
	}
	
//	public void enterCont() {
//		
//		Scanner s = new Scanner(System.in);
//		System.out.println("Press enter to deal cards.");
//		s.nextLine();
//	}
	
	
	public LinkedLists<Card> buildDeck() {
		LinkedLists<Card> deck = new LinkedLists<Card>();
		String[] suit = {"Heart", "Spade", "Club", "Diamond"};
		
		for(int i=1; i<=13; i++) {
			for(int j=0; j<=3; j++) {
				deck.add(new Card(i, suit[j]));	
			}
		}
		return deck;
		
	}
	
	
	public void playGame() {
		
		LinkedLists<Card> myDeck_1 = buildDeck();
		shuffle(myDeck_1);
//		for(int i=0; i<52; i++) {
//			System.out.println(myDeck_1.get(i).toString());
//		}
		
		LinkedLists<Card> myDeck_2 = new LinkedLists<Card>();
		
		for(int i=1; i<=26; i++) {
			Card myCard = myDeck_1.remove(0);
			myDeck_2.add(myCard);
		}
		
		int reply = JOptionPane.showConfirmDialog(null, "Game is about to start...(click NO to quit the game)", "War", JOptionPane.YES_NO_OPTION,
				JOptionPane.YES_NO_OPTION, smallPoker);
		
		if (reply == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "See you next time!!",
					"War", JOptionPane.INFORMATION_MESSAGE, smallPoker);
		} else {
			
			while(myDeck_1.size()!= 0 && myDeck_2.size()!= 0) {
				
				int size_1 = myDeck_1.size();
				int size_2 = myDeck_2.size();
				String cards = "Player 1 has " + size_1 + " cards and Player 2 has " + size_2 + " cards.";
				JOptionPane.showMessageDialog(null, cards,
						"War", JOptionPane.INFORMATION_MESSAGE, smallPoker);
			
				//enterCont();
				
				Card card_1 = myDeck_1.remove(0);
				Card card_2 = myDeck_2.remove(0);
				int num_1 = card_1.num;
				int num_2 = card_2.num;
				String suit_1 = card_1.suit;
				String suit_2 = card_2.suit;
				
				String player_1 = "Player 1 plays the " + num_1 + " of " + suit_1;
				String player_2 = "Player 2 plays the " + num_2 + " of " + suit_2;
				JOptionPane.showMessageDialog(null, player_1 + " and " + player_2,
						"War", JOptionPane.INFORMATION_MESSAGE, smallPoker);
				
				if(num_1 > num_2) {
					
					String win_1 = "Player 1 wins the round!!";
					JOptionPane.showMessageDialog(null, win_1,
							"War", JOptionPane.INFORMATION_MESSAGE, smallWin);
					myDeck_1.add(card_1);
					myDeck_1.add(card_2);
					
				} else if(num_1 < num_2){
					
					String win_2 = "Player 2 wins the round!!";
					JOptionPane.showMessageDialog(null, win_2,
							"War", JOptionPane.INFORMATION_MESSAGE, smallWin);
					myDeck_2.add(card_1);
					myDeck_2.add(card_2);
				} else {
					
					JOptionPane.showMessageDialog(null, "Tie!!",
							"War", JOptionPane.INFORMATION_MESSAGE, smallTie);
				}
				
				
			}
			if(myDeck_1.size() == 0) {
				JOptionPane.showMessageDialog(null, "PLAYER 2 WINS THE GAME!!!",
						"War", JOptionPane.INFORMATION_MESSAGE, smallWin);
			} else {
				JOptionPane.showMessageDialog(null, "PLAYER 1 WINS THE GAME!!!",
						"War", JOptionPane.INFORMATION_MESSAGE, smallWin);
			}
			
			JOptionPane.showMessageDialog(null, "----GAME OVER----Thanks for playing!!",
					"War", JOptionPane.INFORMATION_MESSAGE, smallPoker);
		}
		
		
		
		
	}
	
	public static void main(String[] args) {
		
		War game = new War();
		game.playGame();
		
	}

}
