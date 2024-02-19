package karlord19.cardarchitect;

import java.util.logging.Logger;

public class DeckDrawer {
    private int[] margins; // top, right, bottom, left
    private static final Logger logger = Logger.getLogger(DeckDrawer.class.getName());
    private void setMargins(int top, int right, int bottom, int left) {
        margins = new int[] { top, right, bottom, left };
    }
    // use default 5000 margins
    public DeckDrawer() {
        setMargins(5000, 5000, 5000, 5000);
    }
    public DeckDrawer(int top, int right, int bottom, int left) {
        setMargins(top, right, bottom, left);
    }
    public void drawDeck(Card card, String pdfPath) {
        PDFManager pdf = new PDFManager(pdfPath, margins);
        int cardWidth = card.getWidth();
        int cardHeight = card.getHeight();
        int cardsInRow = (int)(pdf.getPrintPA().area.width / cardWidth);
        int cardsInColumn = (int)(pdf.getPrintPA().area.height / cardHeight);
        int rowSpace = (pdf.getPrintPA().area.width - (cardWidth * cardsInRow)) / (cardsInRow - 1);
        int columnSpace = (pdf.getPrintPA().area.height - (cardHeight * cardsInColumn)) / (cardsInColumn - 1);
        logger.info("Deck drawer will draw " + cardsInRow + " cards in row and " + cardsInColumn + " cards in column.");
        
        int numberOfPages = (int)Math.ceil((double)card.numberOfCards / (cardsInRow * cardsInColumn));
        int remaindingCards = card.numberOfCards;
        for (int i = 0; i < numberOfPages; i++) {
            try {
                pdf.addPage();
            } catch (Exception e) {
                logger.severe(pdfPath + " failed to add page.");
                e.printStackTrace();
                return;
            }
            for (int j = 0; j < cardsInColumn; j++) {
                for (int k = 0; k < cardsInRow & remaindingCards > 0; k++) {
                    PositionedArea pa = new PositionedArea(
                        pdf.getPrintPA().pos.x + k * (cardWidth + rowSpace),
                        pdf.getPrintPA().pos.y + j * (cardHeight + columnSpace),
                        cardWidth,
                        cardHeight);
                    card.draw(pa, i * cardsInRow * cardsInColumn + j * cardsInRow + k, pdf);
                    remaindingCards--;
                }
            }
        }

        try {
            pdf.close();
        } catch (Exception e) {
            logger.severe(pdfPath + " failed to close.");
            e.printStackTrace();
            return;
        }
    }
}
