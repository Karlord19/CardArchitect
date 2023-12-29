package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class WrapText extends Text {

    public WrapText() {
        super();
    }
    public WrapText(String text) {
        super(text);
    }
    public WrapText(String[] texts) {
        super(texts);
    }

    private void drawLine(PDFManager.PDFPA pdfpa, String line, PDPageContentStream contentStream) throws Exception {
        contentStream.newLineAtOffset(0, -fontSize);
        contentStream.showText(line);
        if (font.getStringWidth(line) * fontSize / 1000 > pdfpa.area.width) {
            System.out.println("Warning: line is too long to fit in bounding box." + line);
        }
    }

    @Override
    protected void draw(PDFManager.PDFPA pdfpa, int index, PDPageContentStream contentStream) throws Exception {
        String[] words = texts.get(index).split(" ");
        String line = null;
        int lines = 0;
        contentStream.newLineAtOffset(pdfpa.pos.x, pdfpa.pos.y + pdfpa.area.height - fontSize);
        for (String word : words) {
            String newLine = (line == null) ? word : line + " " + word;
            float newLineWidth = font.getStringWidth(newLine) / 1000 * fontSize;
            if (newLineWidth > pdfpa.area.width) {
                drawLine(pdfpa, line, contentStream);
                lines++;
                line = word;
            } else {
                line = newLine;
            }
        }
        if (line.length() > 0) {
            drawLine(pdfpa, line, contentStream);
            lines++;
        }
        if (fontSize * lines > pdfpa.area.height) {
            System.out.println("Warning: text is too high to fit in bounding box." + texts.get(index));
        }
    }
}
