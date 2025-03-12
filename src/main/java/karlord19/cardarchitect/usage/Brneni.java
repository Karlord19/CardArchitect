package karlord19.cardarchitect.usage;

import karlord19.cardarchitect.*;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;

public class Brneni {
    public static void main(String[] args) {

        PDType0Font font;
        try {
            PDDocument document = new PDDocument();
            font = PDType0Font.load(document, new File("src/main/resources/karlord19/cardarchitect/usage/LiberationSans-Regular.ttf"));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        Fit fitStretch = new Fit();
        fitStretch.setFitType(Fit.FitType.SCALE);
        Picture picture = new Picture();
        picture.setFit(fitStretch);

        Fit fitBottomLeft = new Fit();
        fitBottomLeft.setFitPositionX(Fit.FitPositionX.LEFT);
        fitBottomLeft.setFitPositionY(Fit.FitPositionY.BOTTOM);
        MultilineText textBottomLeft = new MultilineText();
        textBottomLeft.setFont(font, 17);
        textBottomLeft.setFit(fitBottomLeft);

        Fit fitBottomRight = new Fit();
        fitBottomRight.setFitPositionX(Fit.FitPositionX.RIGHT);
        fitBottomRight.setFitPositionY(Fit.FitPositionY.BOTTOM);
        MultilineText textBottomRight = new MultilineText();
        textBottomRight.setFont(font, 17);
        textBottomRight.setFit(fitBottomRight);

        for (int i = 0; i < 10; i++) {
            picture.add("usage/helmet_Turtle_Shell.webp");
            textBottomLeft.add("želva");
            textBottomRight.add("4 meč");
        }

        // Card back = new Card();
        // back.add(picture);
        // back.setHeightsEqual(50000);
        // back.setWidthsEqual(50000);

        // Card top = new Card();
        // top.add(textBottomLeft);
        // top.add(textBottomRight);
        // top.setHeightsEqual(50000);
        // top.setWidthsEqual(50000);

        Card card = new Card(2, 2);
        card.add(picture, "pic", 0, 0, 0, 1);
        card.add(textBottomLeft, "textBottomLeft", 1, 0);
        card.add(textBottomRight, "textBottomRight", 1, 1);
        card.setHeights(new int[]{40000, 10000});

        DeckDrawer deckDrawer = new DeckDrawer(5000, 5000, 5000, 5000);
        deckDrawer.setHorizontalSpace(2500);
        deckDrawer.setVerticalSpace(2500);
        // deckDrawer.drawDeck(new Card[] { back, top }, "Brneni.pdf", 40);
        deckDrawer.drawDeck(card, "Brneni.pdf", 12);
    }
}
