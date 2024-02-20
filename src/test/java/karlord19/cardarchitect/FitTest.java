package karlord19.cardarchitect;

// import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FitTest {
    public Card createBoxAround() {
        Card card = new Card(3,3);

        Picture picture = new Picture();
        picture.addPicture("FitTest/box.png");
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
        "LEFT, TOP, FitTest/TopLeft.pdf",
        "CENTER, TOP, FitTest/TopCenter.pdf",
        "RIGHT, TOP, FitTest/TopRight.pdf",
        "LEFT, CENTER, FitTest/CenterLeft.pdf",
        "CENTER, CENTER, FitTest/CenterCenter.pdf",
        "RIGHT, CENTER, FitTest/CenterRight.pdf",
        "LEFT, BOTTOM, FitTest/BottomLeft.pdf",
        "CENTER, BOTTOM, FitTest/BottomCenter.pdf",
        "RIGHT, BOTTOM, FitTest/BottomRight.pdf"
    })
    public void position(Fit.FitPositionX fx, Fit.FitPositionY fy, String filename) {
        Card card = createBoxAround();

        Picture picture = new Picture();
        picture.addPicture("FitTest/miniSquare.png");
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
        "STRETCH, FitTest/miniRectHigh.png, FitTest/StretchedHigh.pdf",
        "STRETCH, FitTest/miniRectWide.png, FitTest/StretchedWide.pdf",
        "STRETCH, FitTest/miniSquare.png, FitTest/StretchedSquare.pdf",
        "FIT_WIDTH, FitTest/miniRectHigh.png, FitTest/FitWidthHigh.pdf",
        "FIT_WIDTH, FitTest/miniRectWide.png, FitTest/FitWidthWide.pdf",
        "FIT_WIDTH, FitTest/miniSquare.png, FitTest/FitWidthSquare.pdf",
        "FIT_HEIGHT, FitTest/miniRectHigh.png, FitTest/FitHeightHigh.pdf",
        "FIT_HEIGHT, FitTest/miniRectWide.png, FitTest/FitHeightWide.pdf",
        "FIT_HEIGHT, FitTest/miniSquare.png, FitTest/FitHeightSquare.pdf",
        "SCALE, FitTest/miniRectHigh.png, FitTest/ScaledHigh.pdf",
        "SCALE, FitTest/miniRectWide.png, FitTest/ScaledWide.pdf",
        "SCALE, FitTest/miniSquare.png, FitTest/ScaledSquare.pdf",
        "ORIGINAL, FitTest/miniRectHigh.png, FitTest/OriginalHigh.pdf",
    })
    public void bySides(Fit.FitType ft, String imagePath, String filename) {
        Card card = createBoxAround();

        Picture picture = new Picture();
        picture.addPicture(imagePath);
        Fit fit = new Fit();
        fit.setFitType(ft);
        picture.setFit(fit);

        card.add(picture, "center", 1, 1);

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, filename);
    }
}
