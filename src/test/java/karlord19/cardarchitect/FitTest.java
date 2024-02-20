package karlord19.cardarchitect;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FitTest {
    public Card createBoxAround() {
        Card card = new Card(3,3);
        Picture picture = new Picture();
        picture.addPicture("FitTest/box.png");
        Fit fit = new Fit();
        fit.setFitType(Fit.FitType.ORIGINAL);
        card.add(picture, "top", 0, 0, 0, 1);
        card.add(picture, "left", 1, 0, 2, 0);
        card.add(picture, "right", 0, 2, 1, 2);
        card.add(picture, "bottom", 2, 1, 2, 2);
        return card;
    }
    @ParameterizedTest
    @CsvSource({
        "LEFT, TOP, ORIGINAL, FitTest/TopLeft.pdf",
        "CENTER, TOP, ORIGINAL, FitTest/TopCenter.pdf",
        "RIGHT, TOP, ORIGINAL, FitTest/TopRight.pdf",
        "LEFT, CENTER, ORIGINAL, FitTest/CenterLeft.pdf",
        "CENTER, CENTER, ORIGINAL, FitTest/CenterCenter.pdf",
        "RIGHT, CENTER, ORIGINAL, FitTest/CenterRight.pdf",
        "LEFT, BOTTOM, ORIGINAL, FitTest/BottomLeft.pdf",
        "CENTER, BOTTOM, ORIGINAL, FitTest/BottomCenter.pdf",
        "RIGHT, BOTTOM, ORIGINAL, FitTest/BottomRight.pdf"
    })
    public void position(Fit.FitPositionX fx, Fit.FitPositionY fy, Fit.FitType ft, String filename) {
        Card card = createBoxAround();

        Picture picture = new Picture();
        picture.addPicture("FitTest/miniSquare.png");
        Fit fit = new Fit();
        fit.setFitPositionX(fx);
        fit.setFitPositionY(fy);
        fit.setFitType(ft);
        picture.setFit(fit);

        card.add(picture, "center", 1, 1);

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, filename);
    }
}
