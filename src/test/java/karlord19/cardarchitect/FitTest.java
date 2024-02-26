package karlord19.cardarchitect;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FitTest {
    public static Card createBoxAround() {
        Card card = new Card(3,3);
        card.setHeights(new int[]{10000, 50000, 10000});
        card.setWidths(new int[]{10000, 50000, 10000});

        Picture picture = new Picture();
        picture.add("box.png");
        Fit fit = new Fit();
        fit.setFitType(Fit.FitType.STRETCH);
        picture.setFit(fit);

        card.add(picture, "top", 0, 0, 0, 1);
        card.add(picture, "left", 1, 0, 2, 0);
        card.add(picture, "right", 0, 2, 1, 2);
        card.add(picture, "bottom", 2, 1, 2, 2);
        return card;
    }
    @ParameterizedTest
    @CsvSource({
        "LEFT, TOP, Test/FitTest/TopLeft.pdf",
        "CENTER, TOP, Test/FitTest/TopCenter.pdf",
        "RIGHT, TOP, Test/FitTest/TopRight.pdf",
        "LEFT, CENTER, Test/FitTest/CenterLeft.pdf",
        "CENTER, CENTER, Test/FitTest/CenterCenter.pdf",
        "RIGHT, CENTER, Test/FitTest/CenterRight.pdf",
        "LEFT, BOTTOM, Test/FitTest/BottomLeft.pdf",
        "CENTER, BOTTOM, Test/FitTest/BottomCenter.pdf",
        "RIGHT, BOTTOM, Test/FitTest/BottomRight.pdf"
    })
    public void position(Fit.FitPositionX fx, Fit.FitPositionY fy, String filename) {
        Card card = createBoxAround();

        Picture picture = new Picture();
        picture.add("miniSquare.png");
        Fit fit = new Fit();
        fit.setFitPositionX(fx);
        fit.setFitPositionY(fy);
        fit.setFitType(Fit.FitType.ORIGINAL);
        picture.setFit(fit);

        card.add(picture, "center", 1, 1);

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, filename);
    }

    @ParameterizedTest
    @CsvSource({
        "STRETCH, miniRectHigh.png, Test/FitTest/StretchedHigh.pdf",
        "STRETCH, miniRectWide.png, Test/FitTest/StretchedWide.pdf",
        "STRETCH, miniSquare.png, Test/FitTest/StretchedSquare.pdf",
        "FIT_WIDTH, miniRectHigh.png, Test/FitTest/FitWidthHigh.pdf",
        "FIT_WIDTH, miniRectWide.png, Test/FitTest/FitWidthWide.pdf",
        "FIT_WIDTH, miniSquare.png, Test/FitTest/FitWidthSquare.pdf",
        "FIT_HEIGHT, miniRectHigh.png, Test/FitTest/FitHeightHigh.pdf",
        "FIT_HEIGHT, miniRectWide.png, Test/FitTest/FitHeightWide.pdf",
        "FIT_HEIGHT, miniSquare.png, Test/FitTest/FitHeightSquare.pdf",
        "SCALE, miniRectHigh.png, Test/FitTest/ScaledHigh.pdf",
        "SCALE, miniRectWide.png, Test/FitTest/ScaledWide.pdf",
        "SCALE, miniSquare.png, Test/FitTest/ScaledSquare.pdf",
        "ORIGINAL, miniRectHigh.png, Test/FitTest/OriginalHigh.pdf",
    })
    public void bySides(Fit.FitType ft, String imagePath, String filename) {
        Card card = createBoxAround();

        Picture picture = new Picture();
        picture.add(imagePath);
        Fit fit = new Fit();
        fit.setFitType(ft);
        picture.setFit(fit);

        card.add(picture, "center", 1, 1);

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, filename);
    }
}
