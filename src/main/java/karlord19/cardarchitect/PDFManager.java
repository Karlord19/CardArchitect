package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFManager {
    private PDDocument document;
    private PDPageContentStream contentStream;
    private String file;
    private PositionedArea printArea;
    private float[] margins;
    public PDFManager(String path, float[] margins) {
        file = path;
        document = new PDDocument();
        this.margins = margins;
    }
    public void addPage() throws Exception {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        printArea = new PositionedArea(margins[3], margins[0], page.getMediaBox().getWidth() - margins[3] - margins[1], page.getMediaBox().getHeight() - margins[0] - margins[2]);
        contentStream = new PDPageContentStream(document, page);
        contentStream.transform(new Matrix(1, 0, 0, -1, 0, page.getMediaBox().getHeight()));
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
}
