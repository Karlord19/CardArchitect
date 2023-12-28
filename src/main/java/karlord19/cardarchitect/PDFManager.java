package karlord19.cardarchitect;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFManager {
    private PDDocument document;
    private PDPageContentStream contentStream;
    private String file;
    private PositionedArea printArea;
    private int[] margins; // top, right, bottom, left
    public PDFManager(String path, int[] margins) {
        file = path;
        document = new PDDocument();
        this.margins = margins;
    }
    public void addPage() throws Exception {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        int width = Metrics.p2m(page.getMediaBox().getWidth()) - margins[3] - margins[1];
        int height = Metrics.p2m(page.getMediaBox().getHeight()) - margins[0] - margins[2];
        printArea = new PositionedArea(0, 0, width, height);
        contentStream = new PDPageContentStream(document, page);
    }
    public void close() throws Exception {
        contentStream.close();
        document.save(file);
        document.close();
    }
    public PositionedArea getPrintPA() {
        return printArea;
    }
    private class PDFPA {
        public float x;
        public float y;
        public float width;
        public float height;
        public PDFPA(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
    private PDFPA transform(PositionedArea pa) {
        float x = Metrics.m2p(pa.pos.x + margins[3]);
        float y = Metrics.m2p(printArea.area.height + margins[2] - pa.pos.y - pa.area.height);
        float width = Metrics.m2p(pa.area.width);
        float height = Metrics.m2p(pa.area.height);
        return new PDFPA(x, y, width, height);
    }
    public void drawImage(File image, PositionedArea pa) {
        try {
            PDImageXObject pdImage = PDImageXObject.createFromFile(image.getPath(), document);
            PDFPA pdfpa = transform(pa);
            contentStream.drawImage(pdImage, pdfpa.x, pdfpa.y, pdfpa.width, pdfpa.height);
        } catch (IOException e) {
            System.err.println("Failed to draw image " + image.getName());
            e.printStackTrace();
        }
    }
    public void drawText(String text, PositionedArea pa, PDFont font, int fontSize) {
        try {
            PDFPA pdfpa = transform(pa);
            contentStream.beginText();
            contentStream.newLineAtOffset(pdfpa.x, pdfpa.y);
            contentStream.setFont(font, fontSize);
            contentStream.showText(text);
            contentStream.endText();
        } catch (Exception e) {
            System.err.println("Failed to draw text " + text);
            e.printStackTrace();
        }
    }
}
