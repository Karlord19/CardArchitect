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
    }
    public static class PDFPA {
        public PDFPosition pos;
        public PDFArea area;
        public PDFPA(float x, float y, float width, float height) {
            pos = new PDFPosition(x, y);
            area = new PDFArea(width, height);
        }
    }
    private PDFPA transform(PositionedArea pa) {
        float x = Metrics.m2p(pa.pos.x + margins[3]);
        float y = Metrics.m2p(printArea.area.height + margins[2] - pa.pos.y - pa.area.height);
        float width = Metrics.m2p(pa.area.width);
        float height = Metrics.m2p(pa.area.height);
        return new PDFPA(x, y, width, height);
    }
    public void drawImage(File image, PositionedArea pa, Fit fit) {
        try {
            PDImageXObject pdImage = PDImageXObject.createFromFile(image.getPath(), document);
            PDFPA pdfpa = transform(pa);
            PDFArea imageArea = new PDFArea(pdImage.getWidth(), pdImage.getHeight());
            PDFPA imagePA = fit.givePositionedArea(imageArea, pdfpa);

            contentStream.saveGraphicsState();
            contentStream.addRect(pdfpa.pos.x, pdfpa.pos.y, pdfpa.area.width, pdfpa.area.height);
            contentStream.clip();
            contentStream.drawImage(pdImage, imagePA.pos.x, imagePA.pos.y, imagePA.area.width, imagePA.area.height);
            contentStream.restoreGraphicsState();
        } catch (IOException e) {
            System.err.println("Failed to draw image " + image.getName());
            e.printStackTrace();
        }
    }
    public void drawOneLineText(String text, PositionedArea pa, PDFont font, int fontSize) {
        try {
            PDFPA pdfpa = transform(pa);
            contentStream.beginText();
            contentStream.newLineAtOffset(pdfpa.pos.x, pdfpa.pos.y + pdfpa.area.height - fontSize);
            contentStream.setFont(font, fontSize);
            contentStream.showText(text);
            contentStream.endText();
            if (font.getStringWidth(text) > pdfpa.area.width) {
                System.out.println("Warning: text is too long to fit in bounding box." + text);
            }
            if (fontSize > pdfpa.area.height) {
                System.out.println("Warning: text is too high to fit in bounding box." + text);
            }
        } catch (Exception e) {
            System.err.println("Failed to draw text " + text);
            e.printStackTrace();
        }
    }
    public void drawMultiLineText(String text, PositionedArea pa, PDFont font, int fontSize) {
        try {
            PDFPA pdfpa = transform(pa);
            contentStream.beginText();
            contentStream.newLineAtOffset(pdfpa.pos.x, pdfpa.pos.y + pdfpa.area.height - fontSize);
            contentStream.setFont(font, fontSize);
            String[] lines = text.split("\n");
            for (String line : lines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -fontSize);
                if (font.getStringWidth(line) > pdfpa.area.width) {
                    System.out.println("Warning: text is too long to fit in bounding box." + line);
                }
            }
            contentStream.endText();
            if (fontSize * lines.length > pdfpa.area.height) {
                System.out.println("Warning: text is too high to fit in bounding box." + text);
            }
        } catch (Exception e) {
            System.err.println("Failed to draw text " + text);
            e.printStackTrace();
        }
    }
    public void drawCropText(String text, PositionedArea pa, PDFont font, int fontSize) {
        try {
            PDFPA pdfpa = transform(pa);
            contentStream.beginText();
            contentStream.newLineAtOffset(pdfpa.pos.x, pdfpa.pos.y + pdfpa.area.height - fontSize);
            contentStream.setFont(font, fontSize);
            String[] words = text.split(" ");
            int lines = 0;
            String line = "";
            for (String word : words) {
                String newLine = line.length() == 0 ? word : line + " " + word;
                float newLineWidth = font.getStringWidth(newLine) / 1000 * fontSize;
                if (newLineWidth > pdfpa.area.width) {
                    contentStream.newLineAtOffset(0, -fontSize);
                    contentStream.showText(line);
                    if (font.getStringWidth(line) / 1000 * fontSize > pdfpa.area.width) {
                        System.out.println("Warning: line is too long to fit in bounding box." + line);
                    }
                    lines++;
                    line = word;
                } else {
                    line = newLine;
                }
            }
            if (line.length() > 0) {
                contentStream.newLineAtOffset(0, -fontSize);
                contentStream.showText(line);
                lines++;
            }
            contentStream.endText();
            if (fontSize * lines > pdfpa.area.height) {
                System.out.println("Warning: text is too high to fit in bounding box." + text);
            }
        } catch (Exception e) {
            System.err.println("Failed to draw text " + text);
            e.printStackTrace();
        }
    }
}
