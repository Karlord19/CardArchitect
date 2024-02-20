package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Text class
 * Is meant to be derived from, plus override draw method
 * Basic behaviour is to draw a single line with no formatting and no endlines
 */
public class Text implements Drawable {

    protected ArrayList<String> texts = new ArrayList<String>();
    protected PDFont font = PDType1Font.HELVETICA;
    protected int fontSize = 30;
    protected Logger logger = Logger.getLogger(Text.class.getName());

    public Text() {}
    public Text(String text) {
        texts.add(text);
    }
    public Text(String[] texts) {
        for (String text : texts) {
            this.texts.add(text);
        }
    }

    public void addText(String text) {
        texts.add(text);
    }
    public void addTexts(String[] texts) {
        for (String text : texts) {
            this.texts.add(text);
        }
    }

    public void setFont(PDFont font, int size) {
        this.font = font;
        this.fontSize = size;
    }

    protected void draw(PDFManager.PDFPA pdfpa, int index, PDPageContentStream contentStream) throws Exception {
        contentStream.newLineAtOffset(pdfpa.pos.x, pdfpa.pos.y + pdfpa.area.height - fontSize);
        contentStream.showText(texts.get(index));
        float dif;
        if ((dif = font.getStringWidth(texts.get(index)) * fontSize / 1000 - pdfpa.area.width) > 0) {
            logger.warning("Text is longer by " + dif + " to fit in bounding box: " + texts.get(index));
        }
        if ((dif = fontSize - pdfpa.area.height) > 0) {
            logger.warning("Text is higher by " + dif + " to fit in bounding box: " + texts.get(index));
        }
    }

    /**
     * Wrapper for drawing text
     * Try to be final
     */
    public final void draw(PositionedArea pa, int index, PDFManager pdf) {
        if (texts.size() == 0) {
            logger.warning("No texts to draw.");
            return;
        }
        index = index % texts.size();
        logger.info("Drawing text " + texts.get(index) + " at index " + index + " to " + pa);

        PDPageContentStream contentStream = pdf.getContentStream();
        try {
            PDFManager.PDFPA boundingBox = pdf.transform(pa);
            contentStream.saveGraphicsState();
            contentStream.addRect(boundingBox.pos.x, boundingBox.pos.y, boundingBox.area.width, boundingBox.area.height);
            contentStream.clip();
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            draw(boundingBox, index, contentStream);
            contentStream.restoreGraphicsState();
        }
        catch (Exception e) {
            logger.severe("Failed to draw text: " + texts.get(index));
            e.printStackTrace();
            return;
        }
        finally {
            try {
                contentStream.endText();
            }
            catch (Exception e) {
                logger.severe("Failed to end text: " + texts.get(index));
                e.printStackTrace();
                return;
            }
        }
    }
}
