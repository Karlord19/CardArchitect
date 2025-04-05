package karlord19.cardarchitect.usage;

import karlord19.cardarchitect.*;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;

public class Brneni {
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

        Fit fitStretch = new Fit();
        fitStretch.setFitType(Fit.FitType.SCALE);
        Picture picture = new Picture();
        picture.setFit(fitStretch);

        Fit fitCenter = new Fit();
        fitCenter.setFitPositionX(Fit.FitPositionX.CENTER);
        fitCenter.setFitPositionY(Fit.FitPositionY.CENTER);

        MultilineText textName = new MultilineText();
        textName.setFont(font, statsFontSize);
        textName.setFit(fitCenter);

        MultilineText separator = new MultilineText("...");
        separator.setFont(font, statsFontSize);
        separator.setFit(fitCenter);

        Fit fitRight = new Fit();
        fitRight.setFitPositionX(Fit.FitPositionX.RIGHT);
        fitRight.setFitPositionY(Fit.FitPositionY.CENTER);

        MultilineText textType = new MultilineText();
        textType.setFont(font, statsFontSize);
        textType.setFit(fitRight);

        Fit fitLeft = new Fit();
        fitLeft.setFitPositionX(Fit.FitPositionX.LEFT);
        fitLeft.setFitPositionY(Fit.FitPositionY.CENTER);

        MultilineText textNum = new MultilineText();
        textNum.setFont(font, statsFontSize);
        textNum.setFit(fitLeft);

        CsvLoader loader = new CsvLoader();
        loader.addColumn("nazev", textName);
        loader.addColumn("picture", picture);
        loader.addColumn("typ", textType);
        loader.addColumn("cislo", textNum);
        loader.addTimesColumn("pocet");
        loader.load("src/main/resources/karlord19/cardarchitect/usage/brneni.csv");

        Card card = new Card(3, 3);
        card.add(picture, "pic", 0, 0, 0, 2);
        card.add(textName, "name", 1, 0, 1, 2);
        card.add(textType, "type", 2, 0);
        card.add(separator, "sep", 2, 1);
        card.add(textNum, "num", 2, 2);

        int textHight = 4000;
        card.setHeights(new int[]{30000 - 2*textHight, textHight, textHight});
        card.setWidths(new int[]{30000 - 3000 - 11000, 3000, 11000});

        DeckDrawer deckDrawer = new DeckDrawer(3000, 5000, 5000, 5000);
        deckDrawer.setHorizontalSpace(2000);
        deckDrawer.setVerticalSpace(2000);
        deckDrawer.drawDeck(card, "Brneni.pdf", 151);
    }
}
