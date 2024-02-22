package karlord19.cardarchitect;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TextTest {
    @ParameterizedTest
    @CsvSource({
        "Short, Test/TextTest/Short.pdf",
        "This is a long text, Test/TextTest/Long.pdf"
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
        "CENTER, CENTER, Test/TextTest/MultilineCenterCenter.pdf",
        "LEFT, BOTTOM, Test/TextTest/MultilineLeftBottom.pdf",
        "RIGHT, TOP, Test/TextTest/MultilineRightTop.pdf"
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

    @ParameterizedTest
    @CsvSource({
        "This text should be split into multiple lines, Test/TextTest/WrapEasy.pdf",
        "This text contains a verylongwordthatshouldNOTbesplitbutcliped, Test/TextTest/WrapWide.pdf",
        "This text is very long so that it should overflow the bounding box and be cliped but I cant be sure so I will write a bit more words, Test/TextTest/WrapHigh.pdf"
    })
    public void wrapText(String string, String filename) {
        Card card = FitTest.createBoxAround();

        WrapText text = new WrapText(string);
        text.fontSize = 25;
        card.add(text, "center", 1, 1);

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, filename);
    }
}
