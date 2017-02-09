/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author chaknis18
 */
public class Hand {
    private Card pairedCard;
    private Card specHighCard;
    private Card[] hand;
    public Hand(Card[] hand) {
        this.hand = hand;
    }

    public void printHandVal() {
        System.out.print("Hand value: ");
        if (royalFlush()) {
            System.out.println("Royal flush");
        } else if (straightFlush()) {
            System.out.println("Straight flush");
        } else if (fourOfAKind()) {
            System.out.println("Four of a kind");
        } else if (fullHouse()) {
            System.out.println("Full house");
        } else if (flush()) {
            System.out.println("Flush");
        } else if (straight()) {
            System.out.println("Straight");
        } else if (threeOfAKind()) {
            System.out.println("Three of a kind");
        } else if (twoPair()) {
            System.out.println("Two pair");
        } else if (pair()) {
            if (pairedCard.getRank() == 5) {
                System.out.println("Pair of " + pairedCard.rankString() + "es");
            } else {
                System.out.println("Pair of " + pairedCard.rankString() + "s");
            }
        } else {
            System.out.println("Junk (high card is " + highCard().rankString() + ")");
        }
    }
    
    private int handVal() {
        if (royalFlush()) {
            return 9;
        } else if (straightFlush()) {
            return 8;
        } else if (fourOfAKind()) {
            return 7;
        } else if (fullHouse()) {
            return 6;
        } else if (flush()) {
            return 5;
        } else if (straight()) {
            return 4;
        } else if (threeOfAKind()) {
            return 3;
        } else if (twoPair()) {
            return 2;
        } else if (pair()) {
            return 1;
        } else {
            return 0;
        }
    }
    
    private Card highCard() {
        for (Card c : hand) {
            if (c.getRank() == 0) {
                return c;
            }
        }
        
        int highest = 0;
        for (int i = 0; i < hand.length; i++) {
            if (hand[i].getRank() > hand[highest].getRank()) {
                highest = i;
            }
        }
        return hand[highest];
    }
    
    private boolean pair() {
        for (int i = 0; i < hand.length; i++) {
            for (int j = i+1; j < hand.length; j++) {
                if (hand[i].sameRank(hand[j])) {
                    pairedCard = hand[i];
                    specHighCard = hand[i];
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean threeOfAKind() {
        Card[] sorted = this.sortByRank();
        if (sorted[0].getRank() == sorted[1].getRank() && sorted[1] == sorted[2]) {
            specHighCard = sorted[0];
            return true;
        } else if (sorted[1].getRank() == sorted[2].getRank() && sorted[2].getRank() == sorted[3].getRank()) {
            specHighCard = sorted[1];
            return true;
        } else if (sorted[2].getRank() == sorted[3].getRank() && sorted[3].getRank() == sorted[4].getRank()) {
            specHighCard = sorted[2];
            return true;
        } else {
            return false;
        }
    }
    
    private boolean fourOfAKind() {
        Card[] sorted = this.sortByRank();
        if (sorted[0].getRank() == sorted[1].getRank() && sorted[1].getRank() == sorted[2].getRank() && sorted[2].getRank() == sorted[3].getRank()) {
            specHighCard = sorted[0];
            return true;
        } else if (sorted[1].getRank() == sorted[2].getRank() && sorted[2].getRank() == sorted[3].getRank() && sorted[3].getRank() == sorted[4].getRank()) {
            specHighCard = sorted[1];
            return true;
        } else {
            return false;
        }
    }
    
    private boolean contains(Card[] cards, Card c) {
        for (Card card : cards) {
            if (card == c) {
                return true;
            }
        }
        return false;
    }
    
    private Card[] sortByRank() {
        Card[] sorted = new Card[5];
        int[] ranks = new int[5];
        for (int i = 0; i < hand.length; i++) {
            ranks[i] = hand[i].getRank();
        }
        Arrays.sort(ranks);
        for (int i = 0; i < ranks.length; i++) {
            for (int j = 0; j < hand.length; j++) {
                if (ranks[i] == hand[j].getRank() && !contains(sorted, hand[j])) {
                    sorted[i] = hand[j];
                }
            }
        }
        return sorted;
    }
    
    public Card[] sortedHand() {
        Card[] sorted = this.sortByRank();
        return sorted;
    }
    
    private boolean flush() {
        int numSame = 0;
        for (int i = 0; i < hand.length-1; i++) {
            if (hand[i].sameSuit(hand[i+1])) {
                numSame++;
            }
        }
        specHighCard = this.highCard();
        if (numSame == 5) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean straight() {
        Card[] sorted = this.sortByRank();
        specHighCard = this.highCard();
        if (royal()) {
            return true;
        }
        for (int i = 0; i < sorted.length-1; i++) {
            if (!(sorted[i+1].getRank() == (sorted[i].getRank()+1))) {
                return false;
            }
        }
        return true;
    }
    
    private boolean straightFlush() {
        specHighCard = this.highCard();
        if (straight() && flush()) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean royal() {
        Card[] sorted = this.sortByRank();
        if (sorted[0].getRank() == 0 && sorted[1].getRank() == 9 && sorted[2].getRank() == 10 && sorted[3].getRank() == 11 && sorted[4].getRank() == 12) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean royalFlush() {
        specHighCard = this.highCard();
        if (royal() && flush()) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean twoPair() {
        Card[] sorted = this.sortByRank();
        if (sorted[0].sameRank(sorted[1]) && sorted[2].sameRank(sorted[3]) && !sorted[0].sameRank(sorted[2])) {
            if (sorted[2].getRank() > sorted[0].getRank()) {
                specHighCard = sorted[2];
            } else {
                specHighCard = sorted[0];
            }
            return true;
        } else if (sorted[0].sameRank(sorted[1]) && sorted[3].sameRank(sorted[4]) && !sorted[0].sameRank(sorted[3])) {
            if (sorted[3].getRank() > sorted[0].getRank()) {
                specHighCard = sorted[3];
            } else {
                specHighCard = sorted[0];
            }
            return true;
        } else if (sorted[1].sameRank(sorted[2]) && sorted[3].sameRank(sorted[4]) && !sorted[1].sameRank(sorted[3])) {
            if (sorted[3].getRank() > sorted[1].getRank()) {
                specHighCard = sorted[3];
            } else {
                specHighCard = sorted[1];
            }
            return true;
        } else {
            return false;
        }
    }
    
    private boolean fullHouse() {
        Card[] sorted = this.sortByRank();
        if (sorted[0].sameRank(sorted[1]) && sorted[1].sameRank(sorted[2]) && sorted[3].sameRank(sorted[4]) && !sorted[0].sameRank(sorted[3])) {
            specHighCard = sorted[0];
            return true;
        } else if (sorted[0].sameRank(sorted[1]) && sorted[2].sameRank(sorted[3]) && sorted[3].sameRank(sorted[4]) && !sorted[0].sameRank(sorted[2])) {
            specHighCard = sorted[2];
            return true;
        } else {
            return false;
        }
    }
    
    public boolean betterThan(Hand h) {
        if (this.handVal() > h.handVal()) {
            return true;
        } else if (this.handVal() < h.handVal()) {
            return false;
        } else {
            if (this.handVal() == 0 && h.handVal() == 0) {
                return (this.highCard().getRank() > h.highCard().getRank());
            } else {
                return (this.specHighCard.getRank() > h.specHighCard.getRank());
            }
        }
    }
    
    public int cpuBet(int balance) {
        boolean bluff = false;
        Random random = new Random();
        bluff = (random.nextInt(4) == 1);

        if (royalFlush()) {
            if (bluff) {
                return balance/100;
            }
            return balance;
        } else if (straightFlush()) {
            if (bluff) {
                return balance/100;
            }
            return balance/2;
        } else if (fourOfAKind()) {
            if (bluff) {
                return balance/100;
            }
            return balance/4;
        } else if (fullHouse()) {
            if (bluff) {
                return balance/100;
            }
            return balance/5;
        } else if (flush()) {
            if (bluff) {
                return balance/200;
            }
            return balance/10;
        } else if (straight()) {
            if (bluff) {
                return balance/200;
            }
            return balance/20;
        } else if (threeOfAKind()) {
            if (bluff) {
                return balance/50;
            }
            return balance/25;
        } else if (twoPair()) {
            if (bluff) {
                return balance/25;
            }
            return balance/50;
        } else if (pair()) {
            if (bluff) {
                return balance/50;
            }
            return balance/100;
        } else {
            boolean bluff2 = (random.nextInt(3)==1);
            if (bluff && bluff2) {
                return 0;
            }
            if (bluff) {
                return balance/50;
            }
            return balance/150;
        }
    }
}
