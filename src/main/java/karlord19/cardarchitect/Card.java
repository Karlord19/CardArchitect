package karlord19.cardarchitect;

public class Card implements Drawable {
    private Drawable[] elements;
    public void add(Drawable drawable) {
        if (elements == null) {
            elements = new Drawable[1];
            elements[0] = drawable;
        } else {
            Drawable[] newElements = new Drawable[elements.length + 1];
            System.arraycopy(newElements, 0, drawable, 0, elements.length);
            newElements[elements.length] = drawable;
            elements = newElements;
        }
    }
    public void draw(PositionedArea pa, int index, PDFManager pdf) {
        elements[0].draw(pa, 0, pdf);
    }
}
