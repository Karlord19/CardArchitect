package karlord19.cardarchitect;

import org.junit.jupiter.api.Test;

public class LoaderTest {
    @Test
    public void loaderTest() {
        CsvLoader loader = new CsvLoader();
        Picture picture = new Picture();
        Text text = new Text();
        loader.addColumn("picture", picture);
        loader.addColumn("text", text);
        loader.load("src/test/resources/karlord19/cardarchitect/textpictureloader.csv");

        Card card = new Card(2, 1);
        card.add(text, "text", 0, 0);
        card.add(picture, "picture", 1, 0);
        card.setHeights(new int[]{20000, 35000});
        card.setWidths(new int[]{44444});

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, "Test/Loader.pdf", 6);
    }
}
