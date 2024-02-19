package karlord19.cardarchitect.example;

import karlord19.cardarchitect.*;
import org.apache.pdfbox.pdmodel.font.*;

/**
 * BasicTemplate
 */
public class BasicTemplate {

    public static void main(String[] args) {
        Card card = new Card(3, 3);
        card.setWidthsEqual(55000);
        card.setHeights(new int[] { 10000, 20000, 30000 });

        Text textTL = new Text("Áop Left");
        card.add(textTL, "TL", 0, 0, 0, 1);
        
        // Picture pictureTR = new Picture();
        // pictureTR.addPictures("example/BasicTemplate/pics/", "red[0-9]{2}[.]png");
        // card.add(pictureTR, "TR", 0, 2, 1, 2);

        WrapText textTR = new WrapText("moc moc moc textu");
        textTR.setFont(PDType1Font.COURIER, 20);
        card.add(textTR, "TR", 0, 2, 1, 2);
        
        // Fit fitTW = new Fit();
        // fitTW.setFitType(Fit.FitType.FIT_WIDTH);
        // fitTW.setFitPositionY(Fit.FitPositionY.TOP);

        // Picture pictureBL = new Picture();
        // pictureBL.addPictures("example/BasicTemplate/pics/", "blue[0-9]{2}[.]png");
        // pictureBL.setFit(fitTW);
        // card.add(pictureBL, "MM", 1, 0, 2, 1);

        MultilineText textBL = new MultilineText("a a srdtsa\nasd a dsa ocmocdlouheslovozesetamurcitenevejde a a ads a\ndts a a\na");
        textBL.setFont(PDType1Font.COURIER, 10);
        Fit fitCC = new Fit();
        fitCC.setFitPositionX(Fit.FitPositionX.CENTER);
        fitCC.setFitPositionY(Fit.FitPositionY.CENTER);
        textBL.setFit(fitCC);
        card.add(textBL, "BL", 1, 0, 2, 1);

        Fit fitMH = new Fit();
        fitMH.setFitType(Fit.FitType.FIT_HEIGHT);
        
        Picture pictureR = new Picture();
        pictureR.addPictures("example/BasicTemplate/pics/", "green[0-9]{2}[.]png");
        pictureR.setFit(fitMH);
        card.add(pictureR, "R", 2, 2);

        card.numberOfCards = 20;

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, "BasicTemplate.pdf");
    }
}
