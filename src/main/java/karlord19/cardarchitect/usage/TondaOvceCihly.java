package karlord19.cardarchitect.usage;

import karlord19.cardarchitect.*;

public class TondaOvceCihly {
    public static void main(String[] args) {

        Fit fitStretch = new Fit();
        fitStretch.setFitType(Fit.FitType.SCALE);
        Picture picture = new Picture();
        picture.setFit(fitStretch);

        Fit fitCenter = new Fit();
        fitCenter.setFitPositionX(Fit.FitPositionX.CENTER);
        fitCenter.setFitPositionY(Fit.FitPositionY.CENTER);
        MultilineText text = new MultilineText();
        text.setFit(fitCenter);

        for (int i = 0; i < 10; i++) {
            picture.add("usage/TondaOvceCihly/ovce.png");
            text.add("ovce");
        }
        for (int i = 0; i < 10; i++) {
            picture.add("usage/TondaOvceCihly/kamen.jpg");
            text.add("kámen");
        }
        for (int i = 0; i < 10; i++) {
            picture.add("usage/TondaOvceCihly/obili.jpg");
            text.add("obilí");
        }
        for (int i = 0; i < 5; i++) {
            picture.add("usage/TondaOvceCihly/zlato.jpeg");
            text.add("5 cihel\nzlata");
        }
        for (int i = 0; i < 5; i++) {
            picture.add("usage/TondaOvceCihly/zlato.jpeg");
            text.add("1 cihla\nzlata");
        }

        Card back = new Card();
        back.add(picture);
        back.setHeightsEqual(33000);
        back.setWidthsEqual(36500);

        Card top = new Card();
        top.add(text);
        top.setHeightsEqual(33000);
        top.setWidthsEqual(36500);

        DeckDrawer deckDrawer = new DeckDrawer(5000, 5000, 5000, 5000);
        deckDrawer.setHorizontalSpace(2500);
        deckDrawer.setVerticalSpace(2500);
        deckDrawer.drawDeck(new Card[] { back, top }, "TondaOvceCihly.pdf", 40);
    }
}
