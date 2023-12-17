package karlord19.cardarchitect;

/**
 * Drawable
 * 
 * An interface that represents a drawable object.
 */
public interface Drawable {
    // in case of exception, just say it to the console, dont throw it
    public void draw(PositionedArea pa, int index, PDFManager pdf);
}
