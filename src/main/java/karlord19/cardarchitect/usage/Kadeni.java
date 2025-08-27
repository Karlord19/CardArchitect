package karlord19.cardarchitect.usage;

import karlord19.cardarchitect.*;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;

public class Kadeni {
    public static void main(String[] args) {
        
        int statsFontSize = 12;
        int nahoreFontSize = 16;
        PDType0Font font;
        try {
            PDDocument document = new PDDocument();
            font = PDType0Font.load(document, new File("src/main/resources/karlord19/cardarchitect/usage/NotoSans-VariableFont_wdth,wght.ttf"));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        Fit fitCenter = new Fit();
        fitCenter.setFitPositionX(Fit.FitPositionX.CENTER);
        fitCenter.setFitPositionY(Fit.FitPositionY.CENTER);

        MultilineText textForce = new MultilineText();
        textForce.setFont(font, nahoreFontSize);
        textForce.setFit(fitCenter);

        WrapText textName = new WrapText();
        textName.setFont(font, nahoreFontSize);
        textName.setFit(fitCenter);        
        
        Fit fitPicture = new Fit();
        fitPicture.setFitType(Fit.FitType.SCALE);
        Picture picture = new Picture();
        picture.setFit(fitPicture);

        WrapText textDesc = new WrapText();
        textDesc.setFont(font, statsFontSize);
        textDesc.setFit(fitCenter);

        CsvLoader loader = new CsvLoader();
        loader.addColumn("sila", textForce);
        loader.addColumn("nazev", textName);
        loader.addColumn("obrazek", picture);
        loader.addColumn("popis", textDesc);
        loader.addTimesColumn("pocet");
        loader.load("src/main/resources/karlord19/cardarchitect/usage/kadeni.csv");

        Card card = new Card(3, 2);
        card.add(textForce, "sila", 0, 0);
        card.add(textName, "nazev", 0, 1);
        card.add(picture, "obrazek", 1, 0, 1, 1);
        card.add(textDesc, "popis", 2, 0, 2, 1);

        int charWidth = 8000;
        // int height = 63000;
        int width = 42400;
        card.setHeights(new int[]{13000, 30000, 20000});
        card.setWidths(new int[]{charWidth, width - charWidth});

        DeckDrawer deckDrawer = new DeckDrawer(5000, 5000, 5000, 5000);
        deckDrawer.setHorizontalSpace(10000);
        deckDrawer.setVerticalSpace(10000);
        deckDrawer.drawDeck(card, "kadeni.pdf", 7*21+60);
    }
}