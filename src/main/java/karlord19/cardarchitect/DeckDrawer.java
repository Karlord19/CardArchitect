package karlord19.cardarchitect;

public class DeckDrawer {
    private float[] margins; // top, right, bottom, left
    private void setMargins(int top, int right, int bottom, int left) {
        margins = Metrics.m2p(new int[] { top, right, bottom, left });
    }
    // use default 5000 margins
    public DeckDrawer() {
        setMargins(5000, 5000, 5000, 5000);
    }
    public DeckDrawer(int top, int right, int bottom, int left) {
        setMargins(top, right, bottom, left);
    }
    private PDFManager drawDeckBefore(String pdfPath) {
        PDFManager pdf = new PDFManager(pdfPath, margins);
        try {
            pdf.addPage();
        } catch (Exception e) {
            System.err.println("Failed to add page.");
            e.printStackTrace();
            return null;
        }
        return pdf;
    }
    private void drawDeckAfter(PDFManager pdf) {
        try {
            pdf.close();
        } catch (Exception e) {
            System.err.println("Failed to close pdf.");
            e.printStackTrace();
            return;
        }
    }
    public void drawDeck(Card card, String pdfPath) {
        PDFManager pdf = drawDeckBefore(pdfPath);
        if (pdf == null) {
            return;
        }
        float cardWidth = card.getWidth();
        float cardHeight = card.getHeight();
        int cardsInRow = (int)(pdf.getPrintPA().area.width / cardWidth);
        int cardsInColumn = (int)(pdf.getPrintPA().area.height / cardHeight);
        float rowSpace = (pdf.getPrintPA().area.width - (cardWidth * cardsInRow)) / (cardsInRow - 1);
        float columnSpace = (pdf.getPrintPA().area.height - (cardHeight * cardsInColumn)) / (cardsInColumn - 1);
        for (int i = 0; i < cardsInColumn; i++) {
            for (int j = 0; j < cardsInRow; j++) {
                PositionedArea pa = new PositionedArea(
                    pdf.getPrintPA().pos.x + j * (cardWidth + rowSpace),
                    pdf.getPrintPA().pos.y + i * (cardHeight + columnSpace),
                    cardWidth,
                    cardHeight);
                card.draw(pa, i * cardsInColumn + j, pdf);
            }
        }
        drawDeckAfter(pdf);
    }
}
