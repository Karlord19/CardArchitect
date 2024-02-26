package karlord19.cardarchitect;

/**
 * Drawable
 * 
 * An abstract class that represents a drawable object.
 */
abstract class AbstractDrawable {
    // in case of exception, just say it to the console, dont throw it
    void draw(PositionedArea pa, int index, PDFManager pdf) {}

    /**
     * Add a thing to the drawable.
     * @param thing
     */
    public void add(String thing) {}
}
