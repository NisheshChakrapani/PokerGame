/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

/**
 *
 * @author chaknis18
 */
public class Card {
    private int rank;
    private int suit;
    public Card (int rank, int suit) {
        this.rank = rank/72;
        this.suit = suit/97;
    }
    
    public boolean equals(Object o) {
        if (o instanceof Card) {
            Card newCard = (Card) o;
            return (rank == newCard.rank && suit == newCard.suit);
        } else {
            return false;
        }
    }
    
    public boolean sameRank(Card c) {
        return (rank == c.rank);
    }
    
    public boolean sameSuit(Card c) {
        return (suit == c.suit);
    }
    
    @Override
    public String toString() {
        String suitString = "";
        String rankString = "";
        if (this.suit == 1) {
            suitString = "Hearts";
        } else if (this.suit == 2) {
            suitString = "Clubs";
        } else if (this.suit == 3) {
            suitString = "Diamonds";
        } else {
            suitString = "Spades";
        }
        
        if (this.rank+1 == 1) {
            rankString = "Ace";
        } else if (this.rank+1 == 2) {
            rankString = "Two";
        } else if (this.rank+1 == 3) {
            rankString = "Three";
        } else if (this.rank+1 == 4) {
            rankString = "Four";
        } else if (this.rank+1 == 5) {
            rankString = "Five";
        } else if (this.rank+1 == 6) {
            rankString = "Six";
        } else if (this.rank+1 == 7) {
            rankString = "Seven";
        } else if (this.rank+1 == 8) {
            rankString = "Eight";
        } else if (this.rank+1 == 9) {
            rankString = "Nine";
        } else if (this.rank+1 == 10) {
            rankString = "Ten";
        } else if (this.rank+1 == 11) {
            rankString = "Jack";
        } else if (this.rank+1 == 12) {
            rankString = "Queen";
        } else {
            rankString = "King";
        }
        
        return (rankString + " of " + suitString);
    }
    
    public String rankString() {
        String rankString;
        if (this.rank+1 == 1) {
            rankString = "Ace";
        } else if (this.rank+1 == 2) {
            rankString = "Two";
        } else if (this.rank+1 == 3) {
            rankString = "Three";
        } else if (this.rank+1 == 4) {
            rankString = "Four";
        } else if (this.rank+1 == 5) {
            rankString = "Five";
        } else if (this.rank+1 == 6) {
            rankString = "Six";
        } else if (this.rank+1 == 7) {
            rankString = "Seven";
        } else if (this.rank+1 == 8) {
            rankString = "Eight";
        } else if (this.rank+1 == 9) {
            rankString = "Nine";
        } else if (this.rank+1 == 10) {
            rankString = "Ten";
        } else if (this.rank+1 == 11) {
            rankString = "Jack";
        } else if (this.rank+1 == 12) {
            rankString = "Queen";
        } else {
            rankString = "King";
        }
        return rankString;
    }
    
    public int getRank() {
        return this.rank;
    }
    
    public int getSuit() {
        return this.suit;
    }
}
