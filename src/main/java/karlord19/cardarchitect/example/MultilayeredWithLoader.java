package karlord19.cardarchitect.example;

import karlord19.cardarchitect.*;

public class MultilayeredWithLoader {

    public static void main(String[] args) {

        Fit fitStretch = new Fit();
        fitStretch.setFitType(Fit.FitType.STRETCH);
        Picture picture = new Picture();
        picture.setFit(fitStretch);

        Fit fitCenter = new Fit();
        fitCenter.setFitPositionX(Fit.FitPositionX.CENTER);
        fitCenter.setFitPositionY(Fit.FitPositionY.CENTER);
        WrapText text = new WrapText();
        text.setFit(fitCenter);

        CsvLoader loader = new CsvLoader();
        loader.addColumn("picture", picture);
        loader.addColumn("text", text);
        loader.load("src/main/resources/karlord19/cardarchitect/example/multilayered.csv");

        Picture cross = new Picture("example/pics/redCross.png");
        cross.setFit(fitStretch);

        Card back = new Card();
        back.add(picture);

        Card top = new Card();
        top.add(text);

        Card over = new Card();
        over.add(cross);

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.setHorizontalSpace(15000);
        deckDrawer.drawDeck(new Card[] { back, top, over }, "MultilayeredWithLoader.pdf", 10);
    }
}
