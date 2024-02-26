package karlord19.cardarchitect;

import java.util.logging.Logger;

public class DeckDrawer {
    private int[] margins = new int[] {5000, 5000, 5000, 5000}; // top, right, bottom, left
    private static final Logger logger = Logger.getLogger(DeckDrawer.class.getName());
    
    /**
     * Set horizontal space between cards in micrometers.
     * @param horizontalSpace
     */
    public void setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
    }

    private int horizontalSpace = 5000;

    /**
     * Set vertical space between cards in micrometers.
     * @param verticalSpace
     */
    public void setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }

    private int verticalSpace = 5000;

    /**
     * Create a DeckDrawer with default margins: 5000 micrometers in all directions.
     */
    public DeckDrawer() {}

    /**
     * Create a DeckDrawer with custom margins.
     * @param top
     * @param right
     * @param bottom
     * @param left
     */
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
    private void drawPage(int cardWidth, int cardHeight, int cardsInRow, int cardsInColumn, int rowSpace, int columnSpace, int pageNumber, Card[] cardLayers, PDFManager pdf, int remaindingCards) {
        for (int c = 0; c < cardsInColumn; c++) {
            for (int r = 0; r < cardsInRow & remaindingCards > 0; r++) {
                PositionedArea pa = new PositionedArea(
                    pdf.getPrintPA().pos.x + r * (cardWidth + rowSpace),
                    pdf.getPrintPA().pos.y + c * (cardHeight + columnSpace),
                    cardWidth,
                    cardHeight);
                for (Card card : cardLayers) {
                    card.draw(pa, pageNumber * cardsInRow * cardsInColumn + c * cardsInRow + r, pdf);
                }
                remaindingCards--;
            }
        }
    }

    /**
     * Draw a deck of cards in a PDF file.
     * @param card
     * @param pdfPath
     * @param numberOfCards
     */
    public void drawDeck(Card card, String pdfPath, int numberOfCards) {
        drawDeck(new Card[] {card}, pdfPath, numberOfCards);
    }

    /**
     * Draw one card in a PDF file.
     * @param card
     * @param pdfPath
     */
    public void drawDeck(Card card, String pdfPath) {
        drawDeck(new Card[] {card}, pdfPath, 1);
    }
    
    /**
     * Draw a deck of cards in a PDF file.
     * @param cardLayers Array of cards to draw. The first card will be the bottom layer and the last card will be the top layer.
     * @param pdfPath
     */
    public void drawDeck(Card[] cardLayers, String pdfPath, int numberOfCards) {
        PDFManager pdf = new PDFManager(pdfPath, margins);
        int cardWidth = cardLayers[0].getWidth();
        int cardHeight = cardLayers[0].getHeight();
        int cardsInRow = cardsInLine(horizontalSpace, cardWidth, pdf.getPrintPA().area.width);
        int cardsInColumn = cardsInLine(verticalSpace, cardHeight, pdf.getPrintPA().area.height);
        int rowSpace = spaceInLine(horizontalSpace, cardWidth, pdf.getPrintPA().area.width, cardsInRow);
        int columnSpace = spaceInLine(verticalSpace, cardHeight, pdf.getPrintPA().area.height, cardsInColumn);
        logger.info("Deck drawer will draw " + cardsInRow + " cards in row and " + cardsInColumn + " cards in column.");
        logger.info("Deck drawer will will leave " + rowSpace + " mim horizontaly and " + columnSpace + " mim verticaly between cards.");
        
        int numberOfPages = (int)Math.ceil((double) numberOfCards / (cardsInRow * cardsInColumn));
        for (int i = 0; i < numberOfPages; i++) {
            try {
                pdf.addPage();
            } catch (Exception e) {
                logger.severe(pdfPath + " failed to add page.");
                e.printStackTrace();
                return;
            }
            drawPage(cardWidth, cardHeight, cardsInRow, cardsInColumn, rowSpace, columnSpace, i, cardLayers, pdf, numberOfCards);
            numberOfCards -= cardsInRow * cardsInColumn;
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
