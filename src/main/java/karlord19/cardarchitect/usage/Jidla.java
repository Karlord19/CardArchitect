package karlord19.cardarchitect.usage;

import karlord19.cardarchitect.*;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;

public class Jidla {
    public static void main(String[] args) {
        
        int statsFontSize = 10;
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
        fitPicture.setFitType(Fit.FitType.SCALE);
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
        card.add(picture, "obrazek", 0, 0, 0, 1);
        card.add(textName, "nazev", 1, 0, 3, 0);
        card.add(textCost, "cena", 1, 1);
        card.add(textOrigin, "puvod", 2, 1);
        card.add(textDifficulty, "obtiznost", 3, 1);

        LineStyle blue = new LineStyle(500, 0, 0, 1);
        card.addBorderAround("nazev", blue);

        int rowHeight = 6000;
        card.setHeights(new int[]{39400 - 3*rowHeight, rowHeight, rowHeight, rowHeight});
        card.setWidths(new int[]{42400 - 2*rowHeight, 2*rowHeight});

        DeckDrawer deckDrawer = new DeckDrawer(5000, 5000, 5000, 5000);
        deckDrawer.setHorizontalSpace(10000);
        deckDrawer.setVerticalSpace(10000);
        deckDrawer.drawDeck(card, "jidla.pdf", 38);
    }
}