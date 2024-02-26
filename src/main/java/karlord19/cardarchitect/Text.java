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
public class Text extends AbstractDrawable {

    protected ArrayList<String> texts = new ArrayList<String>();
    protected PDFont font = PDType1Font.HELVETICA;
    protected int fontSize = 30;
    protected Logger logger = Logger.getLogger(Text.class.getName());

    /**
     * Create a Text.
     */
    public Text() {}

    /**
     * Create a Text with a text.
     * @param text
     */
    public Text(String text) {
        texts.add(text);
    }

    /**
     * Create a Text with texts.
     * @param texts
     */
    public Text(String[] texts) {
        for (String text : texts) {
            this.texts.add(text);
        }
    }

    /**
     * Add a text to the collection.
     * @param text
     */
    public void add(String text) {
        texts.add(text);
    }

    /**
     * Add texts to the collection.
     * @param texts
     */
    public void addTexts(String[] texts) {
        for (String text : texts) {
            this.texts.add(text);
        }
    }

    /**
     * Set the font and size of the text.
     * @param font
     * @param size
     */
    public void setFont(PDFont font, int size) {
        this.font = font;
        this.fontSize = size;
    }

    protected void draw(PDFManager.PDFPA pdfpa, int index, PDPageContentStream contentStream) {
        try {
            contentStream.newLineAtOffset(pdfpa.pos.x, pdfpa.pos.y + pdfpa.area.height - fontSize);
            contentStream.showText(texts.get(index));
            float dif;
            if ((dif = getWidth(texts.get(index)) - pdfpa.area.width) > 0) {
                logger.warning("Text is longer by " + dif + " to fit in bounding box: " + texts.get(index));
            }
            if ((dif = fontSize - pdfpa.area.height) > 0) {
                logger.warning("Text is higher by " + dif + " to fit in bounding box: " + texts.get(index));
            }
        }
        catch (Exception e) {
            logger.warning("Failed to draw text " + texts.get(index));
            e.printStackTrace();
        }
    }

    /**
     * Wrapper for drawing text.
     */
    @Override
    final void draw(PositionedArea pa, int index, PDFManager pdf) {
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
            try {
                contentStream.addRect(boundingBox.pos.x, boundingBox.pos.y, boundingBox.area.width, boundingBox.area.height);
                contentStream.clip();
                try {
                    contentStream.beginText();
                    try {
                        contentStream.setFont(font, fontSize);
                        draw(boundingBox, index, contentStream);
                    }
                    catch (Exception e) {
                        logger.warning("Failed to set font while drawing text: " + texts.get(index));
                        e.printStackTrace();
                        return;
                    }
                    contentStream.endText();
                }
                catch (Exception e) {
                    logger.warning("Failed to handle text block while drawing text: " + texts.get(index));
                }
            }
            catch (Exception e) {
                logger.warning("Failed to clip text: " + texts.get(index));
                e.printStackTrace();
                return;
            }
            contentStream.restoreGraphicsState();
        }
        catch (Exception e) {
            logger.warning("Failed to handle graphics state while drawing text: " + texts.get(index));
            e.printStackTrace();
            return;
        }
    }

    protected float getWidth(String text) {
        try {
            return font.getStringWidth(text) / 1000 * fontSize;
        }
        catch (Exception e) {
            logger.warning("Failed to get width of text: " + text);
            e.printStackTrace();
            return 0;
        }
    }
}
