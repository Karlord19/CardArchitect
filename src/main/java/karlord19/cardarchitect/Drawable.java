package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

/**
 * Drawable
 * 
 * An interface that represents a drawable object.
 */
public interface Drawable {
    // in case of exception, just say it to the console, dont throw it
    public void draw(int x, int y, int width, int height, int index, PDDocument document, PDPageContentStream contentStream);
}
