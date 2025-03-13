package karlord19.cardarchitect.usage;

import karlord19.cardarchitect.*;

public class Krumpace {

    public static void main(String[] args) {

        Fit fitStretch = new Fit();
        fitStretch.setFitType(Fit.FitType.STRETCH);
        Picture picture = new Picture();
        picture.setFit(fitStretch);

        int statsFontSize = 12;

        Fit fitLeft = new Fit();
        fitLeft.setFitPositionX(Fit.FitPositionX.LEFT);
        fitLeft.setFitPositionY(Fit.FitPositionY.CENTER);
        MultilineText iron = new MultilineText();
        iron.setFit(fitLeft);
        iron.setFont(null, statsFontSize);
        MultilineText diamond = new MultilineText();
        diamond.setFit(fitLeft);
        diamond.setFont(null, statsFontSize);

        Fit fitRight = new Fit();
        fitRight.setFitPositionX(Fit.FitPositionX.RIGHT);
        fitRight.setFitPositionY(Fit.FitPositionY.CENTER);
        MultilineText iron2 = new MultilineText();
        iron2.setFit(fitRight);
        iron2.setFont(null, 12);
        iron2.add("Železo: ");
        MultilineText diamond2 = new MultilineText();
        diamond2.setFit(fitRight);
        diamond2.setFont(null, 12);
        diamond2.add("Diamant: ");
 
        CsvLoader loader = new CsvLoader();
        loader.addColumn("picture", picture);
        loader.addColumn("iron", iron);
        loader.addColumn("diamond", diamond);
        loader.load("src/main/resources/karlord19/cardarchitect/usage/krumpace.csv");

        Card card = new Card(2, 4);

        card.add(picture, "picture", 0, 0, 0, 3);
        card.add(iron2, "iron2", 1, 0);
        card.add(iron, "iron", 1, 1);
        card.add(diamond2, "diamond2", 1, 2);
        card.add(diamond, "diamond", 1, 3);

        card.setHeights(new int[]{40000, 10000});
        card.setWidths(new int[]{15000, 5000, 15000, 5000});

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.setHorizontalSpace(5000);
        deckDrawer.setVerticalSpace(5000);
        deckDrawer.drawDeck(card, "Krumpace.pdf", 10);
    }
}
