package Core;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Neil
 * Date: 14/12/12
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class Deck {
    private ArrayList<Card> deck;

    public Deck()
    {
        this.deck = new ArrayList<Card>();
        addDeckToPile();
    }

    public void addDeckToPile()
    {
        populateDeck();
    }

    public int deckSize()
    {
        return deck.size();
    }

    public Card dealShuffledCard()
    {
        return deck.remove(new Random().nextInt(this.deckSize()));
    }

    private void populateDeck()
    {
        for (int suit = 0; suit < 4; suit++)
            for (int card = 2; card < 15; card++)
            {
                Card temp = new Card(suit, card);
                deck.add(temp);
            }
    }
}
