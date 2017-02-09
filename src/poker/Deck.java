/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author chaknis18
 */
public class Deck {
    private Card returnedCard;
    private Card[] cards = new Card[0];
    private BufferedImage cardSheet() throws IOException {
        BufferedImage sheet = ImageIO.read(new File("H:\\CardDeck2.png"));
        return sheet;
    }
    
    public BufferedImage top() throws IOException {
        BufferedImage topOfDeck = ImageIO.read(new File("H:\\BackOfCard.png"));
        return topOfDeck;
    }
    
    public BufferedImage side() throws IOException {
        BufferedImage sideCard = ImageIO.read(new File("H:\\SidewaysCard.png"));
        return sideCard;
    }
    
    public BufferedImage drawCard() throws IOException {
        Random random = new Random();
        int rank = random.nextInt(13) * 72;
        int suit = random.nextInt(4) * 97;
        Card newCard = new Card(rank, suit);
        if (cards.length > 0) {
            if (this.isDrawn(newCard)) {
                while (this.isDrawn(newCard)) {
                    rank = random.nextInt(13) * 72;
                    suit = random.nextInt(4) * 97;
                    newCard = new Card(rank, suit);
                }
            }
        }
        returnedCard = new Card(rank, suit);
        Rectangle rect = new Rectangle(rank, suit, 72, 97);
        BufferedImage card = this.crop(this.cardSheet(), rect);
        Card[] newCards = new Card[cards.length+1];
        for (int i = 0; i < cards.length; i++) {
            newCards[i] = cards[i];
        }
        newCards[cards.length] = newCard;
        cards = newCards;
        return card;
    }
    
    public BufferedImage showCard(int rank, int suit) throws IOException {
        Rectangle rect = new Rectangle(rank*72, suit*97, 72, 97);
        BufferedImage card = this.crop(this.cardSheet(), rect);
        return card;
    }
    
    private BufferedImage crop(BufferedImage src, Rectangle rect) {
        BufferedImage dest = new BufferedImage((int)rect.getWidth(), (int)rect.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics g = dest.getGraphics();
        g.drawImage(src, 0, 0, (int)rect.getWidth(), (int)rect.getHeight(), (int)rect.getX(), (int)rect.getY(), (int)rect.getX() + (int)rect.getWidth(), (int)rect.getY() + (int)rect.getHeight(), null);
        g.dispose();
        return dest;
    }
    
    private boolean isDrawn(Card c) {
        for (Card card : cards) {
            if (card.equals(c)) {
                return true;
            }
        }
        return false;
    }
    
    public Card getCard() {
        return returnedCard;
    }
    
    public void reset() {
        cards = new Card[0];
    }
}
