package karlord19.cardarchitect;

import java.util.HashMap;
import java.util.logging.Logger;

public class Card implements Drawable {
    private HashMap<String, Drawable> namedElements = new HashMap<String, Drawable>();
    private int rows;
    private int columns;
    private Grid grid;
    private static final Logger logger = Logger.getLogger(Card.class.getName());

    public Card(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        grid = new Grid(rows, columns);
        setWidthsEqual(50000);
        setHeightsEqual(50000);
    }
    public Card() {
        this(1, 1);
    }
    public void setWidths(int[] widths) {
        if (widths.length != columns) {
            logger.severe("Bad number of widths. Actual: " + widths.length + " expected: " + columns);
            return;
        }
        grid.setWidths(widths);
    }
    public void setHeights(int[] heights) {
        if (heights.length != rows) {
            logger.severe("Bad number of heights. Actual: " + heights.length + " expected: " + rows);
            return;
        }
        grid.setHeights(heights);
    }
    private int[] distributeEqual(int sum, int count) {
        int[] result = new int[count];
        int value = sum / count;
        for (int i = 0; i < count; i++) {
            result[i] = value;
        }
        int remainder = sum % count;
        for (int i = 0; i < remainder; i++) {
            result[i]++;
        }
        return result;
    }
    public void setWidthsEqual(int widthSum) {
        int[] widths = distributeEqual(widthSum, columns);
        logger.config("All widths set to: " + widths[0]);
        grid.setWidths(widths);
    }
    public void setHeightsEqual(int heightSum) {
        int[] heights = distributeEqual(heightSum, rows);
        logger.config("All heights set to: " + heights[0]);
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
            logger.severe("Did not add " + name + " to cells (" + startRow + ", " + startColumn + ") - (" + endRow + ", " + endColumn + ") as it is already taken.");
        }
    }
    public void add(Drawable drawable, String name, int row, int column) {
        add(drawable, name, row, column, row, column);
    }
    public void add(Drawable drawable) {
        add(drawable, "", 0, 0);
    }
    /**
     * Just for interface
     * Do not use, it does nothing
     */
    public void add(String thing) {}

    public void addBorder(String name, LineStyle ls, Direction dir) {
        grid.setAroundBorder(ls, name, dir);
    }
    public void addBorderAround(String name, LineStyle ls) {
        grid.setAroundBorder(ls, name);
    }
    
    public void draw(PositionedArea pa, int index, PDFManager pdf) {
        grid.setPA(pa);
        logger.info("Drawing card at index " + index + " to " + pa);
        for (String name : namedElements.keySet()) {
            PositionedArea subPA;
            try {
                subPA = grid.getPA(name);
            } catch (Exception e) {
                logger.severe("Failed to get PA for " + name);
                e.printStackTrace();
                return;
            }
            logger.info("Element " + name + " drawing to " + subPA);
            Drawable drawable = namedElements.get(name);
            drawable.draw(subPA, index, pdf);
        }
        grid.drawBorders(pdf);
    }
}
