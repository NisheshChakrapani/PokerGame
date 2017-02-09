/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author chaknis18
 */
public class Game {
    private DrawingPanel panel = new DrawingPanel(800, 600);
    private Graphics g = panel.getGraphics();
    private Deck deck = new Deck();
    private Scanner scan = new Scanner(System.in);
    private final int ANTE = 10;
    
    private Card[] player1Hand = new Card[5];
    private Card[] player2Hand = new Card[5];
    private Card[] player3Hand = new Card[5];
    private Card[] player4Hand = new Card[5];
    
    private int p1Balance = 1000;
    private int p2Balance = 1000;
    private int p3Balance = 1000;
    private int p4Balance = 1000;
    
    public void playGame() throws IOException { 
        while (true) {
            boolean fold = false;
            boolean p2Fold = false;
            boolean p3Fold = false;
            boolean p4Fold = false;
            int pot = 0;
            int p1Bet = 0;
            int p2Bet = 0;
            int p3Bet = 0;
            int p4Bet = 0;
            drawHand(1);
            drawHand(2);
            drawHand(3);
            drawHand(4);
            System.out.println("The ante is 10");
            p1Balance-=ANTE;
            p2Balance-=ANTE;
            p3Balance-=ANTE;
            p4Balance-=ANTE;
            if (p1Balance<=0 || p2Balance<=0 || p3Balance<=0 || p4Balance<=0) {
                System.out.println("Someone is bankrupt, the game is over");
                System.out.println("Final Balances:");
                System.out.println("Player 1: " + p1Balance);
                System.out.println("Player 2: " + p2Balance);
                System.out.println("Player 3: " + p3Balance);
                System.out.println("Player 4: " + p4Balance);
                panel.sleep(2000);
                System.exit(0);
            }
            pot+=40;
            System.out.print("Stay in the game? Type y for yes, n for no\n> ");
            String answer = scan.next();
            while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
                System.out.print("Stay in the game again? (Y for yes, N for no)\n> ");
                answer = scan.next();
            }
            if (answer.equalsIgnoreCase("n")) {
                System.out.println("You fold");
                fold = true;
            }
            
            System.out.println("Your balance: " + p1Balance);
            System.out.println("Player 2 balance: " + p2Balance);
            System.out.println("Player 3 balance: " + p3Balance);
            System.out.println("Player 4 balance: " + p4Balance);

            //System.out.println("YOUR HAND: ");
            //for (Card c : player1Hand) {
            //    System.out.println(c);
            //}

            Hand p1Hand = new Hand(player1Hand);
            Hand p2Hand = new Hand(player2Hand);
            Hand p3Hand = new Hand(player3Hand);
            Hand p4Hand = new Hand(player4Hand);
            //System.out.print("Your ");
            //p1Hand.printHandVal();
            /*System.out.print("Player 2 "); 
            p2Hand.printHandVal();
            System.out.print("Player 3 ");
            p3Hand.printHandVal();
            System.out.print("Player 4 ");
            p4Hand.printHandVal();*/

            boolean p1Best = (p1Hand.betterThan(p2Hand) && p1Hand.betterThan(p3Hand) && p1Hand.betterThan(p4Hand));
            boolean p2Best = (p2Hand.betterThan(p1Hand) && p2Hand.betterThan(p3Hand) && p2Hand.betterThan(p4Hand));
            boolean p3Best = (p3Hand.betterThan(p1Hand) && p3Hand.betterThan(p2Hand) && p3Hand.betterThan(p4Hand));
            System.out.println();
            
            if (!fold) {
                System.out.print("How much will you bet?\n> ");
                boolean betted = false;
                while (!betted) {
                    try {
                        p1Bet = scan.nextInt();
                        if (p1Bet == 0) {
                            System.out.println("You fold");
                            fold = true;
                        }
                        while (p1Bet > p1Balance) {
                            System.out.print("Not a valid bet. Enter bet amount\n> ");
                            p1Bet = scan.nextInt();
                            if (p1Bet == 0) {
                                System.out.println("You fold");
                                fold = true;
                            }
                        }
                        p1Balance -= p1Bet;
                        betted = true;
                    } catch (InputMismatchException e) {
                        System.out.print("Not a valid bet. Enter bet amount\n> ");
                    }
                }
            }
            p2Bet = p2Hand.cpuBet(p2Balance);
            if (p2Bet == 0) {
                System.out.println("Player 2 folds");
                p2Fold = true;
            } else {
                System.out.println("Player 2 bets " + p2Bet);
            }
            p2Balance -= p2Bet;
            p3Bet = p3Hand.cpuBet(p3Balance);
            if (p3Bet == 0) {
                System.out.println("Player 3 folds");
                p3Fold = true;
            } else {
                System.out.println("Player 3 bets " + p3Bet);
            }
            p3Balance -= p3Bet;
            p4Bet = p4Hand.cpuBet(p4Balance);
            if (p4Bet == 0) {
                System.out.println("Player 4 folds");
            } else {
                System.out.println("Player 4 bets " + p4Bet);
            }
            p4Balance -= p4Bet;
            pot += (p1Bet + p2Bet + p3Bet + p4Bet);
            System.out.println();
            
            boolean p1Win = p1Best && !fold;
            boolean p2Win = p2Best && !p2Fold;
            boolean p3Win = p3Best && !p3Fold;
            boolean p4Win = !p4Fold;
            
            if (p1Win) {
                System.out.println("You win the round.");
                p1Balance += pot;
            } else if (p2Win) {
                System.out.println("Player 2 wins the round.");
                p2Balance += pot; 
            } else if (p3Win) {
                System.out.println("Player 3 wins the round.");
                p3Balance += pot;
            } else if (p4Win) {
                System.out.println("Player 4 wins the round.");
                p4Balance += pot;
            }
            
            g.setFont(new Font("Plain", Font.BOLD, 50));
            g.setColor(Color.red);
            g.drawString("Winning Hand:", 220, 200);
            
            if (p1Win) {
                int x = 214;
                for (Card c : p1Hand.sortedHand()) {
                    BufferedImage card = deck.showCard(c.getRank(), c.getSuit());
                    g.drawImage(card, x, 250, null);
                    x+=75;
                    panel.sleep(100);
                }
            } else if (p2Win) {
                int x = 214;
                for (Card c : p2Hand.sortedHand()) {
                    BufferedImage card = deck.showCard(c.getRank(), c.getSuit());
                    g.drawImage(card, x, 250, null);
                    x+=75;
                    panel.sleep(100);
                }
            } else if (p3Win) {
                int x = 214;
                for (Card c : p3Hand.sortedHand()) {
                    BufferedImage card = deck.showCard(c.getRank(), c.getSuit());
                    g.drawImage(card, x, 250, null);
                    x+=75;
                    panel.sleep(100);
                }
            } else if (p4Win) {
                int x = 214;
                for (Card c : p4Hand.sortedHand()) {
                    BufferedImage card = deck.showCard(c.getRank(), c.getSuit());
                    g.drawImage(card, x, 250, null);
                    x+=75;
                    panel.sleep(100);
                }
            }
            
            System.out.println();
            System.out.println("Your balance: " + p1Balance);
            System.out.println("Player 2 balance: " + p2Balance);
            System.out.println("Player 3 balance: " + p3Balance);
            System.out.println("Player 4 balance: " + p4Balance);
            
            if (p1Balance == 0 || p2Balance == 0 || p3Balance == 0 || p4Balance == 0) {
                System.out.println("Someone is bankrupt, game over");
                System.out.println("Final Balances:");
                System.out.println("Player 1: " + p1Balance);
                System.out.println("Player 2: " + p2Balance);
                System.out.println("Player 3: " + p3Balance);
                System.out.println("Player 4: " + p4Balance);
                panel.sleep(2000);
                System.exit(0);
            }
            
            System.out.println();
            
            System.out.print("Play again? (Y for yes, N for no)\n> ");
            answer = scan.next();
            while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
                System.out.print("Play again? (Y for yes, N for no)\n> ");
                answer = scan.next();
            }
            if (answer.equalsIgnoreCase("n")) {
                System.exit(0);
            }
            System.out.println("------------------------------------");
            panel.clear();
            deck.reset();
            panel.sleep(250);
        }
    }
    
    private void drawHand(int tablePos) throws IOException {
        if (tablePos == 1) {
            panel.sleep(100);
            g.drawImage(deck.drawCard(), 214, 450, null);
            player1Hand[0] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.drawCard(), 289, 450, null);
            player1Hand[1] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.drawCard(), 364, 450, null);
            player1Hand[2] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.drawCard(), 441, 450, null);
            player1Hand[3] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.drawCard(), 516, 450, null);
            player1Hand[4] = deck.getCard();          
        } else if (tablePos == 2) {
            panel.sleep(100);
            g.drawImage(deck.side(), 50, 413, null);
            player2Hand[0] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.side(), 50, 338, null);
            deck.drawCard();
            player2Hand[1] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.side(), 50, 263, null);
            deck.drawCard();
            player2Hand[2] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.side(), 50, 188, null);
            deck.drawCard();
            player2Hand[3] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.side(), 50, 113, null);
            deck.drawCard();
            player2Hand[4] = deck.getCard();
        } else if (tablePos == 3) {
            panel.sleep(100);
            g.drawImage(deck.top(), 214, 50, null);
            deck.drawCard();
            player3Hand[0] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.top(), 289, 50, null);
            deck.drawCard();
            player3Hand[1] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.top(), 364, 50, null);
            deck.drawCard();
            player3Hand[2] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.top(), 441, 50, null);
            deck.drawCard();
            player3Hand[3] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.top(), 516, 50, null);
            deck.drawCard();
            player3Hand[4] = deck.getCard();
        } else {
            panel.sleep(100);
            g.drawImage(deck.side(), 650, 113, null);
            deck.drawCard();
            player4Hand[0] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.side(), 650, 188, null);
            deck.drawCard();
            player4Hand[1] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.side(), 650, 263, null);
            deck.drawCard();
            player4Hand[2] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.side(), 650, 338, null);
            deck.drawCard();
            player4Hand[3] = deck.getCard();
            panel.sleep(100);
            g.drawImage(deck.side(), 650, 413, null);
            deck.drawCard();
            player4Hand[4] = deck.getCard();
        }
    }
    
    private void printCPUHands() {
        System.out.println("\nLEFT PLAYER'S HAND: ");
        for (Card c : player2Hand) {
            System.out.println(c);
        }
        
        System.out.println("\nTOP PLAYER'S HAND: ");
        for (Card c : player3Hand) {
            System.out.println(c);
        }
        
        System.out.println("\nRIGHT PLAYER'S HAND: ");
        for (Card c : player4Hand) {
            System.out.println(c);
        }
    }
}
