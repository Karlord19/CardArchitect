package karlord19.cardarchitect.example;

import karlord19.cardarchitect.*;

/**
 * BasicTemplate
 */
public class BasicTemplate {

    public static void main(String[] args) {

        Card card = new Card(2, 2);
        card.setWidthsEqual(60000);
        card.setHeights(new int[] { 15000, 50000});

        Text text = new Text(new String[] { "Odd", "Even" });
        card.add(text, "text", 0, 0);

        Picture picTL = new Picture("example/BasicTemplate/pics/yellow01.png");
        picTL.add("example/BasicTemplate/pics/yellow02.png");
        Fit fitStretch = new Fit();
        fitStretch.setFitType(Fit.FitType.STRETCH);
        picTL.setFit(fitStretch);
        card.add(picTL, "TL", 0, 1);
        
        Picture picB = new Picture();
        picB.addDir("example/BasicTemplate/pics/", "blue[0-9]{2}[.]png");
        Fit fitB = new Fit();
        fitB.setFitType(Fit.FitType.SCALE);
        fitB.setFitPositionY(Fit.FitPositionY.BOTTOM);
        picB.setFit(fitB);
        card.add(picB, "B", 1, 0, 1, 1);

        card.numberOfCards = 20;

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(card, "BasicTemplate.pdf");
    }
}
