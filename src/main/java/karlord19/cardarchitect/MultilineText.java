package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class MultilineText extends Text {
    
    public MultilineText() {
        super();
    }
    public MultilineText(String text) {
        super(text);
    }
    public MultilineText(String[] texts) {
        super(texts);
    }

    private Fit fit = new Fit();
    public void setFit(Fit fit) {
        this.fit = fit;
    }

    private float drawLine(PDFManager.PDFPA pdfpa, String line, PDPageContentStream contentStream, float currentX) throws Exception {
        float x = fit.givePositionedArea(new PDFManager.PDFArea(font.getStringWidth(line) / 1000 * fontSize, fontSize), pdfpa).pos.x;
        contentStream.newLineAtOffset(x - currentX, -fontSize);
        logger.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX " + (x - currentX));
        contentStream.showText(line);
        return x;
    }

    @Override
    protected void draw(PDFManager.PDFPA pdfpa, int index, PDPageContentStream contentStream) throws Exception {
        String[] lines = texts.get(index).split("\n");
        float maxWidth = 0;
        for (String line : lines) {
            float lineWidth = font.getStringWidth(line) / 1000 * fontSize;
            if (lineWidth > maxWidth) {
                maxWidth = lineWidth;
            }
            if (lineWidth > pdfpa.area.width) {
                System.out.println("Warning: line is too long to fit in bounding box." + line);
            }
        }
        float heightSum = lines.length * fontSize;
        if (heightSum > pdfpa.area.height) {
            System.out.println("Warning: text is too high to fit in bounding box." + texts.get(index));
        }
        PDFManager.PDFPA printPA = fit.givePositionedArea(new PDFManager.PDFArea(maxWidth, heightSum), pdfpa);
        contentStream.newLineAtOffset(printPA.pos.x, printPA.pos.y + printPA.area.height - fontSize);
        float currentX = printPA.pos.x;
        for (String line : lines) {
            currentX = drawLine(pdfpa, line, contentStream, currentX);
        }
    }
}
