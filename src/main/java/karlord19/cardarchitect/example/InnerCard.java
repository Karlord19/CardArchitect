package karlord19.cardarchitect.example;

import karlord19.cardarchitect.*;

public class InnerCard {

    public static void main(String[] args) {

        Card inner = new Card(3, 3);
        inner.setHeightsEqual(30000);
        inner.setWidthsEqual(30000);

        Blank blank = new Blank();
        inner.add(blank, "eye", 1, 1);
        LineStyle blue = new LineStyle(1000, 0, 0, 1);
        inner.addBorderAround("eye", blue);

        Card outer = new Card(2, 2);
        outer.setHeightsEqual(60000);
        outer.setWidthsEqual(60000);

        outer.add(inner, "left eye", 0, 0);
        outer.add(inner, "right eye", 0, 1);
        outer.add(blank, "left mouth", 1, 0);
        outer.add(blank, "right mouth", 1, 1);

        LineStyle red = new LineStyle(1000, 1, 0, 0);
        outer.addBorder("left mouth", red, Direction.LEFT);
        outer.addBorder("left mouth", red, Direction.BOTTOM);
        outer.addBorder("right mouth", red, Direction.RIGHT);
        outer.addBorder("right mouth", red, Direction.BOTTOM);

        DeckDrawer deckDrawer = new DeckDrawer();
        deckDrawer.drawDeck(outer, "InnerCard.pdf", 6);
    }
}
