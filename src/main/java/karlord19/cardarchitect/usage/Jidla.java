package karlord19.cardarchitect.usage;

import karlord19.cardarchitect.*;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;

public class Jidla {
    public static void main(String[] args) {
        
        int statsFontSize = 22;
        PDType0Font font;
        try {
            PDDocument document = new PDDocument();
            font = PDType0Font.load(document, new File("src/main/resources/karlord19/cardarchitect/usage/Dynalight-Regular.ttf"));
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
        
        MultilineText textCost = new MultilineText();
        textCost.setFont(font, statsFontSize);
        textCost.setFit(fitCenter);

        MultilineText textOrigin = new MultilineText();
        textOrigin.setFont(font, statsFontSize);
        textOrigin.setFit(fitCenter);

        MultilineText textDifficulty = new MultilineText();
        textDifficulty.setFont(font, statsFontSize);
        textDifficulty.setFit(fitCenter);

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

        Card background = new Card();
        Picture picBack = new Picture("usage/pozadi.png");
        Fit fitStretch = new Fit();
        fitStretch.setFitType(Fit.FitType.STRETCH);
        picBack.setFit(fitStretch);
        background.add(picBack);
        background.setWidthsEqual(width);
        background.setHeightsEqual(height);

        DeckDrawer deckDrawer = new DeckDrawer(5000, 5000, 5000, 5000);
        deckDrawer.setHorizontalSpace(10000);
        deckDrawer.setVerticalSpace(11000);
        deckDrawer.drawDeck(new Card[]{background, card}, "jidla.pdf", 36*8);
    }
}
