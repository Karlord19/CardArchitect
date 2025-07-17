package karlord19.cardarchitect.usage;

import karlord19.cardarchitect.*;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;

public class Jidla {
    public static void main(String[] args) {
        
        int statsFontSize = 8;
        PDType0Font font;
        try {
            PDDocument document = new PDDocument();
            font = PDType0Font.load(document, new File("src/main/resources/karlord19/cardarchitect/usage/LiberationSans-Regular.ttf"));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        Fit fitCenter = new Fit();
        fitCenter.setFitPositionX(Fit.FitPositionX.CENTER);
        fitCenter.setFitPositionY(Fit.FitPositionY.CENTER);

        WrapText textName = new WrapText();
        textName.setFont(font, statsFontSize);
        textName.setFit(fitCenter);
        
        Fit fitRight = new Fit();
        fitRight.setFitPositionX(Fit.FitPositionX.RIGHT);
        fitRight.setFitPositionY(Fit.FitPositionY.CENTER);
        
        MultilineText textCost = new MultilineText();
        textCost.setFont(font, statsFontSize);
        textCost.setFit(fitRight);
        
        MultilineText textDifficulty = new MultilineText();
        textDifficulty.setFont(font, statsFontSize);
        textDifficulty.setFit(fitRight);

        Fit fitStretch = new Fit();
        fitStretch.setFitType(Fit.FitType.SCALE);
        Picture picture = new Picture();
        picture.setFit(fitStretch);

        CsvLoader loader = new CsvLoader();
        loader.addColumn("nazev", textName);
        loader.addColumn("cena", textCost);
        loader.addColumn("obtiznost", textDifficulty);
        loader.addColumn("obrazek", picture);
        loader.addTimesColumn("pocet");
        loader.load("src/main/resources/karlord19/cardarchitect/usage/-karticky.csv");

        Card card = new Card(3, 2);
        card.add(picture, "obrazek", 0, 0, 0, 1);
        card.add(textName, "nazev", 1, 0, 2, 0);
        card.add(textCost, "cena", 1, 1);
        card.add(textDifficulty, "obtiznost", 2, 1);

        card.setHeights(new int[]{25000, 4000, 4000});
        card.setWidths(new int[]{25000, 8000});

        DeckDrawer deckDrawer = new DeckDrawer(5000, 5000, 5000, 5000);
        deckDrawer.setHorizontalSpace(2000);
        deckDrawer.setVerticalSpace(2000);
        deckDrawer.drawDeck(card, "jidla.pdf", 10);
    }
}