package karlord19.cardarchitect.example;

import karlord19.cardarchitect.DeckDrawer;
import karlord19.cardarchitect.Picture;
import karlord19.cardarchitect.Text;
import karlord19.cardarchitect.Card;

/**
 * BasicTemplate
 */
public class BasicTemplate {

    public static void main(String[] args) {
        Card card = new Card(3, 3);
        card.setWidthsEqual(55000);
        card.setHeights(new int[] { 10000, 20000, 30000 });

        Text textTL = new Text("Top Left");
        card.add(textTL, "TL", 0, 0, 0, 1);

        Picture pictureTR = new Picture();
        pictureTR.addPictures("example/BasicTemplate/pics/", "red[0-9]{2}[.]png");
        card.add(pictureTR, "TR", 0, 2, 1, 2);

        Picture pictureBL = new Picture();
        pictureBL.addPictures("example/BasicTemplate/pics/", "blue[0-9]{2}[.]png");
        card.add(pictureBL, "MM", 1, 0, 2, 1);

        Picture pictureR = new Picture();
        pictureR.addPictures("example/BasicTemplate/pics/", "green[0-9]{2}[.]png");
        card.add(pictureR, "R", 2, 2);

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, "BasicTemplate.pdf");
    }
}
