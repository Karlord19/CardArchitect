package karlord19.cardarchitect.example;

import karlord19.cardarchitect.DeckDrawer;
import karlord19.cardarchitect.Picture;
import karlord19.cardarchitect.Card;

/**
 * BasicTemplate
 */
public class BasicTemplate {

    public static void main(String[] args) {
        Picture pictureTL = new Picture();
        pictureTL.addPictures("example/BasicTemplate/pics/", "red[0-9]{2}[.]png");
        Picture pictureTR = new Picture();
        pictureTR.addPictures("example/BasicTemplate/pics/", "blue[0-9]{2}[.]png");
        Picture pictureBL = new Picture();
        pictureBL.addPictures("example/BasicTemplate/pics/", "green[0-9]{2}[.]png");
        Picture pictureBR = new Picture();
        pictureBR.addPictures("example/BasicTemplate/pics/", "yellow[0-9]{2}[.]png");
        Card card = new Card(2, 2);
        card.setWidths(new int[] { 20000, 50000 });
        card.setHeights(new int[] { 30000, 80000 });
        card.add(pictureTL, 0, 0);
        card.add(pictureTR, 0, 1);
        card.add(pictureBL, 1, 0);
        card.add(pictureBR, 1, 1);
        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, "BasicTemplate.pdf");
    }
}
