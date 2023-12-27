package karlord19.cardarchitect;

public class Card implements Drawable {
    private Drawable[][] elements;
    private int rows;
    private float[] rowHeights;
    private int columns;
    private float[] columnWidths;
    public Card(int rows, int columns) {
        this.rows = rows;
        this.rowHeights = new float[rows];
        this.columns = columns;
        this.columnWidths = new float[columns];
        elements = new Drawable[rows][columns];
    }
    public Card() {
        this(1, 1);
    }
    public void setWidths(int[] widths) {
        if (widths.length != columns) {
            System.out.println("Bad number of widths.");
            return;
        }
        this.columnWidths = Metrics.m2p(widths);
    }
    public void setHeights(int[] heights) {
        if (heights.length != rows) {
            System.out.println("Bad number of heights.");
            return;
        }
        this.rowHeights = Metrics.m2p(heights);
    }
    public int getWidth() {
        int width = 0;
        for (int i = 0; i < columns; i++) {
            width += columnWidths[i];
        }
        return width;
    }
    public int getHeight() {
        int height = 0;
        for (int i = 0; i < rows; i++) {
            height += rowHeights[i];
        }
        return height;
    }
    public void add(Drawable drawable, int row, int column) {
        elements[row][column] = drawable;
    }
    public void add(Drawable drawable) {
        add(drawable, 0, 0);
    }
    public void draw(PositionedArea pa, int index, PDFManager pdf) {
        float widthSum = 0;
        float heightSum = 0;
        for (int i = 0; i < rows; i++) {
            float height = rowHeights[i];
            widthSum = 0;
            for (int j = 0; j < columns; j++) {
                float width = columnWidths[j];
                PositionedArea subPa = new PositionedArea(pa.pos.x + widthSum, pa.pos.y + heightSum, width, height);
                if (elements[i][j] != null) {
                    elements[i][j].draw(subPa, index, pdf);
                }
                widthSum += width;
            }
            heightSum += height;
        }
    }
}
