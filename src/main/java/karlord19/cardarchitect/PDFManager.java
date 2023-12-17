package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFManager {
    private PDDocument document;
    private PDPageContentStream contentStream;
    private String file;
    public PDFManager(String path) {
        this.file = path;
        this.document = new PDDocument();
    }
    public void addPage() throws Exception {
        PDPage page = new PDPage();
        this.document.addPage(page);
        this.contentStream = new PDPageContentStream(this.document, page);
    }
    public void close() throws Exception {
        this.contentStream.close();
        this.document.save(this.file);
        this.document.close();
    }
    public void draw(Drawable drawable, int x, int y, int width, int height, int index) {
        drawable.draw(x, y, width, height, index, this.document, this.contentStream);
    }
}
