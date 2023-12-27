package karlord19.cardarchitect.example;

import karlord19.cardarchitect.DeckDrawer;
import karlord19.cardarchitect.Picture;
import karlord19.cardarchitect.Card;

/**
 * BasicTemplate
 */
public class BasicTemplate {

    public static void main(String[] args) {
        Picture picture = new Picture();
        picture.add_picture("example/BasicTemplate/pics/cmrk.jpg");
        Card card = new Card(2, 2);
        card.setWidths(new int[] { 20000, 50000 });
        card.setHeights(new int[] { 30000, 80000 });
        card.add(picture, 0, 0);
        card.add(picture, 0, 1);
        card.add(picture, 1, 1);
        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, "BasicTemplate.pdf");
    }
}
