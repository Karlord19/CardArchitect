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

    protected ArrayList<String> texts;
    protected PDFont font = PDType1Font.HELVETICA;
    protected int fontSize = 30;
    protected Logger logger;

    public Text() {
        texts = new ArrayList<String>();
        logger = Logger.getLogger(Text.class.getName());
        logger.info("Text obj created with no text.");
    }
    public Text(String text) {
        texts = new ArrayList<String>();
        texts.add(text);
        logger = Logger.getLogger(Text.class.getName());
        logger.info("Text obj created with text " + text);
    }
    public Text(String[] texts) {
        this.texts = new ArrayList<String>();
        for (String text : texts) {
            this.texts.add(text);
        }
        logger = Logger.getLogger(Text.class.getName());
        logger.info("Text obj created with texts " + texts);
    }

    public void addText(String text) {
        texts.add(text);
        logger.info("Added text " + text);
    }
    public void addTexts(String[] texts) {
        for (String text : texts) {
            this.texts.add(text);
        }
        logger.info("Added texts " + texts);
    }

    public void setFont(PDFont font, int size) {
        this.font = font;
        this.fontSize = size;
        logger.info("Set font to " + font + " with size " + size);
    }

    protected void draw(PDFManager.PDFPA pdfpa, int index, PDPageContentStream contentStream) throws Exception {
        contentStream.newLineAtOffset(pdfpa.pos.x, pdfpa.pos.y + pdfpa.area.height - fontSize);
        contentStream.showText(texts.get(index));
        if (font.getStringWidth(texts.get(index)) > pdfpa.area.width) {
            logger.warning("Text is too long to fit in bounding box. " + texts.get(index));
        }
        if (fontSize > pdfpa.area.height) {
            logger.warning("Text is too high to fit in bounding box. " + texts.get(index));
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
        logger.info("Rendering text " + texts.get(index) + " at index " + index + " to " + pa);
        PDFManager.PDFPA pdfpa = pdf.transform(pa);
        PDPageContentStream contentStream = pdf.getContentStream();
        try {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            draw(pdfpa, index, contentStream);
            contentStream.endText();
        } catch (Exception e) {
            logger.severe("Failed to draw text " + texts.get(index));
            e.printStackTrace();
        }
    }
}
