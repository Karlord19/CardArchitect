package karlord19.cardarchitect;

import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFManager {
    private PDDocument document;
    private PDPageContentStream contentStream;
    private String file;
    private PositionedArea printArea;
    private int[] margins; // top, right, bottom, left
    private PDRectangle pageSize = PDRectangle.A4;
    public PDFManager(String path, int[] margins) {
        file = path;
        document = new PDDocument();
        this.margins = margins;
        printArea = new PositionedArea(0, 0, Metrics.p2m(pageSize.getWidth()) - margins[3] - margins[1], Metrics.p2m(pageSize.getHeight()) - margins[0] - margins[2]);
    }
    public void addPage() throws Exception {
        PDPage page = new PDPage(pageSize);
        if (contentStream != null) contentStream.close();
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
    }
    public void close() throws Exception {
        contentStream.close();
        File f = new File(file);
        {
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();
        }
        document.save(file);
        document.close();
    }
    public PositionedArea getPrintPA() {
        return printArea;
    }
    public PDDocument getDocument() {
        return document;
    }
    public PDPageContentStream getContentStream() {
        return contentStream;
    }
    public static class PDFPosition {
        public float x;
        public float y;
        public PDFPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }
        public PDFPosition() {
            this(0, 0);
        }
        public String toString() {
            return "PDFPos: (" + x + ", " + y + ")";
        }
    }
    public static class PDFArea {
        public float width;
        public float height;
        public PDFArea(float width, float height) {
            this.width = width;
            this.height = height;
        }
        public PDFArea() {
            this(0, 0);
        }
        public String toString() {
            return "PDFArea: (" + width + ", " + height + ")";
        }
    }
    public static class PDFPA {
        public PDFPosition pos;
        public PDFArea area;
        public PDFPA(float x, float y, float width, float height) {
            pos = new PDFPosition(x, y);
            area = new PDFArea(width, height);
        }
        public PDFPA() {
            this(0, 0, 0, 0);
        }
        public String toString() {
            return "PDFPA: " + pos + " " + area;
        }
    }
    public PDFPA transform(PositionedArea pa) {
        float x = Metrics.m2p(pa.pos.x + margins[3]);
        float y = Metrics.m2p(printArea.area.height + margins[2] - pa.pos.y - pa.area.height);
        float width = Metrics.m2p(pa.area.width);
        float height = Metrics.m2p(pa.area.height);
        return new PDFPA(x, y, width, height);
    }
}
