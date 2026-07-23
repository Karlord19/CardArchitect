package karlord19.cardarchitect.usage;

import karlord19.cardarchitect.*;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;

public class Odrazky {
    public static void main(String[] args) {
        
        int statsFontSize = 48;
        int nahoreFontSize = 64;
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

        MultilineText textStanoviste = new MultilineText();
        textStanoviste.setFont(font, nahoreFontSize);
        textStanoviste.setFit(fitCenter);

        MultilineText text1 = new MultilineText();
        text1.setFont(font, statsFontSize);
        text1.setFit(fitCenter);

        MultilineText text2 = new MultilineText();
        text2.setFont(font, statsFontSize);
        text2.setFit(fitCenter);

        MultilineText text3 = new MultilineText();
        text3.setFont(font, statsFontSize);
        text3.setFit(fitCenter);

        MultilineText text4 = new MultilineText();
        text4.setFont(font, statsFontSize);
        text4.setFit(fitCenter);

        CsvLoader loader = new CsvLoader();
        loader.addColumn("stanoviste", textStanoviste);
        loader.addColumn("pismeno", text1);
        loader.addColumn("cislo", text2);
        loader.addColumn("zvire", text3);
        loader.addColumn("stat", text4);
        loader.load("src/main/resources/karlord19/cardarchitect/usage/odrazky.csv");

        Card card = new Card(5, 1);
        card.add(textStanoviste, "stanoviste", 0, 0);
        card.add(text1, "pismeno", 1, 0);
        card.add(text2, "cislo", 2, 0);
        card.add(text3, "zvire", 3, 0);
        card.add(text4, "stat", 4, 0);

        // height = 280 000
        // width = 190 000
        card.setHeights(new int[]{100000, 30000, 30000, 30000, 30000});
        card.setWidths(new int[]{190000});

        DeckDrawer deckDrawer = new DeckDrawer(5000, 5000, 5000, 5000);
        deckDrawer.setHorizontalSpace(10000);
        deckDrawer.setVerticalSpace(10000);
        deckDrawer.drawDeck(card, "odrazky.pdf", 40);
    }
}