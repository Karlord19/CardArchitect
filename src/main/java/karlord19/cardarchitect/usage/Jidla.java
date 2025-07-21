package karlord19.cardarchitect.usage;

import karlord19.cardarchitect.*;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;

public class Jidla {
    public static void main(String[] args) {
        
        int statsFontSize = 14;
        PDType0Font font;
        try {
            PDDocument document = new PDDocument();
            font = PDType0Font.load(document, new File("src/main/resources/karlord19/cardarchitect/usage/LiberationSans-Regular.ttf"));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        Fit fitLeft = new Fit();
        fitLeft.setFitPositionX(Fit.FitPositionX.LEFT);
        fitLeft.setFitPositionY(Fit.FitPositionY.CENTER);

        WrapText textName = new WrapText();
        textName.setFont(font, statsFontSize);
        textName.setFit(fitLeft);
        
        Fit fitRight = new Fit();
        fitRight.setFitPositionX(Fit.FitPositionX.RIGHT);
        fitRight.setFitPositionY(Fit.FitPositionY.CENTER);
        
        MultilineText textCost = new MultilineText();
        textCost.setFont(font, statsFontSize);
        textCost.setFit(fitRight);

        MultilineText textOrigin = new MultilineText();
        textOrigin.setFont(font, statsFontSize);
        textOrigin.setFit(fitRight);
        
        MultilineText textDifficulty = new MultilineText();
        textDifficulty.setFont(font, statsFontSize);
        textDifficulty.setFit(fitRight);

        Fit fitPicture = new Fit();
        fitPicture.setFitType(Fit.FitType.FIT_WIDTH);
        Picture picture = new Picture();
        picture.setFit(fitPicture);

        CsvLoader loader = new CsvLoader();
        loader.addColumn("nazev", textName);
        loader.addColumn("cena", textCost);
        loader.addColumn("puvod", textOrigin);
        loader.addColumn("obtiznost", textDifficulty);
        loader.addColumn("obrazek", picture);
        loader.addTimesColumn("pocet");
        loader.load("src/main/resources/karlord19/cardarchitect/usage/-karticky.csv");

        Card card = new Card(4, 2);
        card.add(picture, "obrazek", 0, 0, 2, 0);
        card.add(textName, "nazev", 3, 0, 3, 1);
        card.add(textCost, "cena", 2, 1);
        card.add(textOrigin, "puvod", 1, 1);
        card.add(textDifficulty, "obtiznost", 0, 1);

        int charWidth = 8000;
        int height = 47400;
        int width = 42400;
        card.setHeights(new int[]{height/2/3, height/2/3, height/2/3, height/2});
        card.setWidths(new int[]{width - charWidth, charWidth});

        DeckDrawer deckDrawer = new DeckDrawer(5000, 5000, 5000, 5000);
        deckDrawer.setHorizontalSpace(10000);
        deckDrawer.setVerticalSpace(10000);
        deckDrawer.drawDeck(card, "jidla.pdf", 38);
    }
}