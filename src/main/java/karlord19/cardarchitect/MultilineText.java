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

    protected Fit fit = new Fit();
    /** 
     * The FitType is set to ORIGINAL, because the text is never scaled 
     */
    public void setFit(Fit fit) {
        this.fit = fit;
        fit.setFitType(Fit.FitType.ORIGINAL);
    }

    private float drawLine(PDFManager.PDFPA bigBox, String line, PDPageContentStream contentStream, float currentX) {
        float x = fit.givePositionedArea(new PDFManager.PDFArea(getWidth(line), 0), bigBox).pos.x;
        try {
            contentStream.newLineAtOffset(x - currentX, -fontSize);
            contentStream.showText(line);
        }
        catch (Exception e) {
            logger.warning("Failed to draw text " + line);
            e.printStackTrace();
            return x;
        }
        return x;
    }

    protected void draw(String[] lines, PDFManager.PDFPA boundingBox, PDPageContentStream contentStream) {
        float maxWidth = 0;
        for (String line : lines) {
            float lineWidth = getWidth(line);
            if (lineWidth > maxWidth) {
                maxWidth = lineWidth;
            }
            if (lineWidth > boundingBox.area.width) {
                System.out.println("Warning: line is too long to fit in bounding box." + line);
            }
        }
        float heightSum = lines.length * fontSize;
        if (heightSum > boundingBox.area.height) {
            System.out.println("Warning: text is too high to fit in bounding box." + lines);
        }
        PDFManager.PDFPA printPA = fit.givePositionedArea(new PDFManager.PDFArea(maxWidth, heightSum), boundingBox);
        try {
            // navigate to the previous line; every line will navigate to its line itself
            contentStream.newLineAtOffset(printPA.pos.x, printPA.pos.y + printPA.area.height);
        }
        catch (Exception e) {
            logger.warning("Failed to draw text " + lines);
            e.printStackTrace();
            return;
        }
        float currentX = printPA.pos.x;
        for (String line : lines) {
            currentX = drawLine(boundingBox, line, contentStream, currentX);
        }
    }

    @Override
    protected void draw(PDFManager.PDFPA boundingBox, int index, PDPageContentStream contentStream) {
        String[] lines = texts.get(index).split("\n");
        draw(lines, boundingBox, contentStream);
    }
}
