package karlord19.cardarchitect.usage;

import karlord19.cardarchitect.*;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;

public class Brneni {
    public static void main(String[] args) {
        
        int statsFontSize = 12;
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

        for (int i = 0; i < 10; i++) {
            picture.add("usage/helmet_Turtle_Shell.webp");
            textName.add("helma z želvy");
            textType.add("meč");
            textNum.add("2");
        }

        Card card = new Card(3, 3);
        card.add(picture, "pic", 0, 0, 0, 2);
        card.add(textName, "name", 1, 0, 1, 2);
        card.add(textType, "type", 2, 0);
        card.add(separator, "sep", 2, 1);
        card.add(textNum, "num", 2, 2);
        card.setHeights(new int[]{27000, 6000, 6000});
        card.setWidths(new int[]{15000, 3000, 9000});

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.setHorizontalSpace(5000);
        deckDrawer.setVerticalSpace(5000);
        deckDrawer.drawDeck(card, "Brneni.pdf", 12);
    }
}
