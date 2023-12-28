package karlord19.cardarchitect;

import java.util.HashMap;

public class Card implements Drawable {
    private HashMap<String, Drawable> namedElements;
    private int rows;
    private int columns;
    private Grid grid;
    public Card(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        namedElements = new HashMap<String, Drawable>();
        grid = new Grid(rows, columns);
        setWidthsEqual(50000);
        setHeightsEqual(50000);
    }
    public Card() {
        this(1, 1);
    }
    public void setWidths(int[] widths) {
        if (widths.length != columns) {
            System.out.println("Bad number of widths.");
            return;
        }
        grid.setWidths(widths);
    }
    public void setHeights(int[] heights) {
        if (heights.length != rows) {
            System.out.println("Bad number of heights.");
            return;
        }
        grid.setHeights(heights);
    }
    public void setWidthsEqual(int widthSum) {
        int width = widthSum / columns;
        int[] widths = new int[columns];
        for (int i = 0; i < columns; i++) {
            widths[i] = width;
        }
        int remainder = widthSum % columns;
        for (int i = 0; i < remainder; i++) {
            widths[i]++;
        }
        grid.setWidths(widths);
    }
    public void setHeightsEqual(int heightSum) {
        int height = heightSum / rows;
        int[] heights = new int[rows];
        for (int i = 0; i < rows; i++) {
            heights[i] = height;
        }
        int remainder = heightSum % rows;
        for (int i = 0; i < remainder; i++) {
            heights[i]++;
        }
        grid.setHeights(heights);
    }
    public int getWidth() {
        return grid.getWidth();
    }
    public int getHeight() {
        return grid.getHeight();
    }
    public void add(Drawable drawable, String name, int startRow, int startColumn, int endRow, int endColumn) {
        if (grid.add(name, startRow, startColumn, endRow, endColumn)) {
            namedElements.put(name, drawable);
        } else {
            System.out.println("Did not add " + name + " to cells " + startRow + ", " + startColumn + ", " + endRow + ", " + endColumn);
        }
    }
    public void add(Drawable drawable, String name, int row, int column) {
        add(drawable, name, row, column, row, column);
    }
    public void add(Drawable drawable) {
        add(drawable, "", 0, 0);
    }
    public void draw(PositionedArea pa, int index, PDFManager pdf) {
        grid.setPA(pa);
        System.out.println("Card drawing to " + pa);
        for (String name : namedElements.keySet()) {
            System.out.println("Card drawing " + name);
            Drawable drawable = namedElements.get(name);
            PositionedArea subPA = grid.getPA(name);
            drawable.draw(subPA, index, pdf);
        }
    }
}
