package karlord19.cardarchitect;

public class DeckDrawer {
    public void drawDeck(Card card, String pdfPath) {
        PDFManager pdf = new PDFManager(pdfPath);
        try {
            pdf.addPage();
        } catch (Exception e) {
            System.err.println("Failed to add page.");
            e.printStackTrace();
            return;
        }
        card.draw(new PositionedArea(0, 100, 400, 200), 0, pdf);
        try {
            pdf.close();
        } catch (Exception e) {
            System.err.println("Failed to close pdf.");
            e.printStackTrace();
            return;
        }
    }
}
