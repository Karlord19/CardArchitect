package karlord19.cardarchitect;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFManager {
    private PDDocument document;
    private PDPageContentStream contentStream;
    private String file;
    private PositionedArea printArea;
    private float[] margins; // top, right, bottom, left
    public PDFManager(String path, float[] margins) {
        file = path;
        document = new PDDocument();
        this.margins = margins;
    }
    public void addPage() throws Exception {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        printArea = new PositionedArea(0, 0, page.getMediaBox().getWidth() - margins[3] - margins[1], page.getMediaBox().getHeight() - margins[0] - margins[2]);
        contentStream = new PDPageContentStream(document, page);
    }
    public void close() throws Exception {
        contentStream.close();
        document.save(file);
        document.close();
    }
    public PDDocument getDocument() {
        return document;
    }
    public PDPageContentStream getContentStream() {
        return contentStream;
    }
    public PositionedArea getPrintPA() {
        return printArea;
    }
    private PositionedArea transform(PositionedArea pa) {
        float x = pa.pos.x + margins[3];
        float y = printArea.area.height + margins[2] - pa.pos.y - pa.area.height;
        return new PositionedArea(x, y, pa.area.width, pa.area.height);
    }
    public void drawImage(File image, PositionedArea pa) {
        try {
            PDImageXObject pdImage = PDImageXObject.createFromFile(image.getPath(), document);
            pa = transform(pa);
            contentStream.drawImage(pdImage, pa.pos.x, pa.pos.y, pa.area.width, pa.area.height);
        } catch (IOException e) {
            System.err.println("Failed to draw image " + image.getName());
            e.printStackTrace();
        }
    }
}
