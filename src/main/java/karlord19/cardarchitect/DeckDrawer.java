package karlord19.cardarchitect;

import java.util.logging.Logger;

public class DeckDrawer {
    private int[] margins = new int[] {5000, 5000, 5000, 5000}; // top, right, bottom, left
    /**
     * Horizontal space between cards in micrometers.
     * If null, it will be calculated to fit the cards in the page.
     */
    public Integer horizontalSpace = null;
    /**
     * Vertical space between cards in micrometers.
     * If null, it will be calculated to fit the cards in the page.
     */
    public Integer verticalSpace = null;
    private static final Logger logger = Logger.getLogger(DeckDrawer.class.getName());

    public DeckDrawer() {}
    public DeckDrawer(int top, int right, int bottom, int left) {
        setMargins(top, right, bottom, left);
    }

    private void setMargins(int top, int right, int bottom, int left) {
        margins = new int[] { top, right, bottom, left };
    }

    private static int cardsInLine(Integer space, int cardSize, int pageSize) {
        if (space == null) {
            return (int)((double) pageSize / cardSize);
        }
        return (int)((double) pageSize / (cardSize + space));
    }
    private static int spaceInLine(Integer space, int cardSize, int pageSize, int cardsInLine) {
        if (space == null) {
            return (pageSize - cardSize * cardsInLine) / (cardsInLine - 1);
        }
        return space;
    }
    private void drawPage(int cardWidth, int cardHeight, int cardsInRow, int cardsInColumn, int rowSpace, int columnSpace, int pageNumber, Card card, PDFManager pdf, int remaindingCards) {
        for (int c = 0; c < cardsInColumn; c++) {
            for (int r = 0; r < cardsInRow & remaindingCards > 0; r++) {
                PositionedArea pa = new PositionedArea(
                    pdf.getPrintPA().pos.x + r * (cardWidth + rowSpace),
                    pdf.getPrintPA().pos.y + c * (cardHeight + columnSpace),
                    cardWidth,
                    cardHeight);
                card.draw(pa, pageNumber * cardsInRow * cardsInColumn + c * cardsInRow + r, pdf);
                remaindingCards--;
            }
        }
    }
    public void drawDeck(Card card, String pdfPath) {
        PDFManager pdf = new PDFManager(pdfPath, margins);
        int cardWidth = card.getWidth();
        int cardHeight = card.getHeight();
        int cardsInRow = cardsInLine(horizontalSpace, cardWidth, pdf.getPrintPA().area.width);
        int cardsInColumn = cardsInLine(verticalSpace, cardHeight, pdf.getPrintPA().area.height);
        int rowSpace = spaceInLine(horizontalSpace, cardWidth, pdf.getPrintPA().area.width, cardsInRow);
        int columnSpace = spaceInLine(verticalSpace, cardHeight, pdf.getPrintPA().area.height, cardsInColumn);
        logger.info("Deck drawer will draw " + cardsInRow + " cards in row and " + cardsInColumn + " cards in column.");
        logger.info("Deck drawer will will leave " + rowSpace + " mim horizontaly and " + columnSpace + " mim verticaly between cards.");
        
        int numberOfPages = (int)Math.ceil((double) card.numberOfCards / (cardsInRow * cardsInColumn));
        int remaindingCards = card.numberOfCards;
        for (int i = 0; i < numberOfPages; i++) {
            try {
                pdf.addPage();
            } catch (Exception e) {
                logger.severe(pdfPath + " failed to add page.");
                e.printStackTrace();
                return;
            }
            drawPage(cardWidth, cardHeight, cardsInRow, cardsInColumn, rowSpace, columnSpace, i, card, pdf, remaindingCards);
            remaindingCards -= cardsInRow * cardsInColumn;
        }

        try {
            pdf.close();
            logger.info("Deck drawer saved document to " + pdfPath);
        } catch (Exception e) {
            logger.severe(pdfPath + " failed to save document to " + pdfPath);
            e.printStackTrace();
            return;
        }
    }
}
