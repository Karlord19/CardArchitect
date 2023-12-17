package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFManager {
    private PDDocument document;
    private PDPageContentStream contentStream;
    private String file;
    private Area pageArea;
    public PDFManager(String path) {
        this.file = path;
        this.document = new PDDocument();
    }
    public void addPage() throws Exception {
        PDPage page = new PDPage(PDRectangle.A4);
        this.document.addPage(page);
        pageArea = new Area((int)page.getMediaBox().getWidth(), (int)page.getMediaBox().getHeight());
        this.contentStream = new PDPageContentStream(this.document, page);
    }
    public void close() throws Exception {
        this.contentStream.close();
        this.document.save(this.file);
        this.document.close();
    }
    public PDDocument getDocument() {
        return this.document;
    }
    public PDPageContentStream getContentStream() {
        return this.contentStream;
    }
    public Area getPageArea() {
        return this.pageArea;
    }
}
