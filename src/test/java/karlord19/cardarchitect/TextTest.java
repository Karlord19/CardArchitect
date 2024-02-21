package karlord19.cardarchitect;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TextTest {
    @ParameterizedTest
    @CsvSource({
        "Short, TextTest/Short.pdf",
        "This is a long text, TextTest/Long.pdf"
    })
    public void basicText(String string, String filename) {
        Card card = FitTest.createBoxAround();

        Text text = new Text(string);
        text.fontSize = 33;
        card.add(text, "center", 1, 1);

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, filename);
    }

    @ParameterizedTest
    @CsvSource({
        "CENTER, CENTER, TextTest/MultilineCenterCenter.pdf",
        "LEFT, BOTTOM, TextTest/MultilineLeftBottom.pdf",
        "RIGHT, TOP, TextTest/MultilineRightTop.pdf"
    })
    public void multilineText(Fit.FitPositionX x, Fit.FitPositionY y, String filename) {
        Card card = FitTest.createBoxAround();

        MultilineText text = new MultilineText("First\nSecond line\nThird");
        text.fontSize = 25;
        Fit fit = new Fit();
        fit.setFitPositionX(x);
        fit.setFitPositionY(y);
        text.setFit(fit);
        card.add(text, "center", 1, 1);

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, filename);
    }
}
