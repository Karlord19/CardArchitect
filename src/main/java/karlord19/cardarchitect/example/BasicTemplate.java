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
        Card card = new Card();
        card.add(picture);
        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, "BasicTemplate.pdf");
    }
}
