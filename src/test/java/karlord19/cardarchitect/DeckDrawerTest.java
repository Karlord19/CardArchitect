package karlord19.cardarchitect;

import org.junit.jupiter.api.Test;

public class DeckDrawerTest {
    @Test
    public void margins() {
        Card card = new Card();
        Picture picture = new Picture("box.png");
        Fit fit = new Fit();
        fit.setFitType(Fit.FitType.STRETCH);
        picture.setFit(fit);
        card.add(picture);

        DeckDrawer deckDrawer = new DeckDrawer(10005, 20005, 30005, 40005);
        deckDrawer.drawDeck(card, "Test/DeckDrawerTest/Margins.pdf", 20);
    }

    @Test
    public void space() {
        Card card = new Card();
        Picture picture = new Picture("box.png");
        Fit fit = new Fit();
        fit.setFitType(Fit.FitType.STRETCH);
        picture.setFit(fit);
        card.add(picture);

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.horizontalSpace = 10005;
        deckDrawer.verticalSpace = 20005;
        deckDrawer.drawDeck(card, "Test/DeckDrawerTest/Space.pdf", 20);
    }
}
