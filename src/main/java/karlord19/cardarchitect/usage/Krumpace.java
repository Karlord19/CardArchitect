package karlord19.cardarchitect.usage;

import karlord19.cardarchitect.*;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;

public class Krumpace {

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

        Fit fitStretch = new Fit();
        fitStretch.setFitType(Fit.FitType.STRETCH);
        Picture picture = new Picture();
        picture.setFit(fitStretch);

        Fit fitCenter = new Fit();
        fitCenter.setFitPositionX(Fit.FitPositionX.CENTER);
        fitCenter.setFitPositionY(Fit.FitPositionY.CENTER);

        MultilineText textName = new MultilineText();
        textName.setFont(font, statsFontSize);
        textName.setFit(fitCenter);

        Fit fitRight = new Fit();
        fitRight.setFitPositionX(Fit.FitPositionX.RIGHT);
        fitRight.setFitPositionY(Fit.FitPositionY.CENTER);

        MultilineText textUhli = new MultilineText("uhlí...");
        textUhli.setFont(font, statsFontSize);
        textUhli.setFit(fitRight);

        MultilineText textZelezo = new MultilineText("železo...");
        textZelezo.setFont(font, statsFontSize);
        textZelezo.setFit(fitRight);

        MultilineText textZlato = new MultilineText("zlato...");
        textZlato.setFont(font, statsFontSize);
        textZlato.setFit(fitRight);

        MultilineText textRedstone = new MultilineText("redstone...");
        textRedstone.setFont(font, statsFontSize);
        textRedstone.setFit(fitRight);

        MultilineText textDiamant = new MultilineText("diamant...");
        textDiamant.setFont(font, statsFontSize);
        textDiamant.setFit(fitRight);

        MultilineText textSmaragd = new MultilineText("smaragd...");
        textSmaragd.setFont(font, statsFontSize);
        textSmaragd.setFit(fitRight);

        Fit fitLeft = new Fit();
        fitLeft.setFitPositionX(Fit.FitPositionX.LEFT);
        fitLeft.setFitPositionY(Fit.FitPositionY.CENTER);

        MultilineText textNumUhli = new MultilineText();
        textNumUhli.setFont(font, statsFontSize);
        textNumUhli.setFit(fitLeft);

        MultilineText textNumZelezo = new MultilineText();
        textNumZelezo.setFont(font, statsFontSize);
        textNumZelezo.setFit(fitLeft);

        MultilineText textNumZlato = new MultilineText();
        textNumZlato.setFont(font, statsFontSize);
        textNumZlato.setFit(fitLeft);

        MultilineText textNumRedstone = new MultilineText();
        textNumRedstone.setFont(font, statsFontSize);
        textNumRedstone.setFit(fitLeft);

        MultilineText textNumDiamant = new MultilineText();
        textNumDiamant.setFont(font, statsFontSize);
        textNumDiamant.setFit(fitLeft);

        MultilineText textNumSmaragd = new MultilineText();
        textNumSmaragd.setFont(font, statsFontSize);
        textNumSmaragd.setFit(fitLeft);
 
        CsvLoader loader = new CsvLoader();
        loader.addColumn("picture", picture);
        loader.addColumn("uhli", textNumUhli);
        loader.addColumn("zelezo", textNumZelezo);
        loader.addColumn("zlato", textNumZlato);
        loader.addColumn("redstone", textNumRedstone);
        loader.addColumn("diamant", textNumDiamant);
        loader.addColumn("smaragd", textNumSmaragd);
        loader.load("src/main/resources/karlord19/cardarchitect/usage/krumpace.csv");

        Card card = new Card(4, 4);

        card.add(picture, "picture", 0, 0, 0, 3);
        card.add(textUhli, "textUhli", 1, 0);
        card.add(textNumUhli, "pocetUhli", 1, 1);
        card.add(textZelezo, "textZelezo", 2, 0);
        card.add(textNumZelezo, "pocetZelezo", 2, 1);
        card.add(textZlato, "textZlato", 3, 0);
        card.add(textNumZlato, "pocetZlato", 3, 1);
        card.add(textRedstone, "textRedstone", 1, 2);
        card.add(textNumRedstone, "pocetRedstone", 1, 3);
        card.add(textDiamant, "textDiamant", 2, 2);
        card.add(textNumDiamant, "pocetDiamant", 2, 3);
        card.add(textSmaragd, "textSmaragd", 3, 2);
        card.add(textNumSmaragd, "pocetSmaragd", 3, 3);

        int textHight = 6000;
        card.setHeights(new int[]{60000, textHight, textHight, textHight});
        card.setWidths(new int[]{25000, 5000, 25000, 5000});

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.setHorizontalSpace(5000);
        deckDrawer.setVerticalSpace(5000);
        deckDrawer.drawDeck(card, "Krumpace.pdf", 12);
    }
}
