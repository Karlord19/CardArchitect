package karlord19.cardarchitect;

import java.util.HashMap;
import java.util.logging.Logger;

public class Grid {
    public static class Rectangle {
        public int startRow;
        public int startColumn;
        public int endRow;
        public int endColumn;
        public Rectangle(int startRow, int startColumn, int endRow, int endColumn) {
            this.startRow = startRow;
            this.startColumn = startColumn;
            this.endRow = endRow;
            this.endColumn = endColumn;
        }
    }
    private Logger logger = Logger.getLogger(Grid.class.getName());
    private boolean[][] taken;
    private int[] widths;
    private int[] heights;
    private HashMap<String, Rectangle> namedRectangles;
    private PositionedArea gridPA;
    public Grid(int rows, int columns) {
        taken = new boolean[rows][columns];
        widths = new int[columns];
        heights = new int[rows];
        namedRectangles = new HashMap<String, Rectangle>();
    }
    public Grid() {
        this(1, 1);
    }
    public void setWidths(int[] widths) {
        if (widths.length != this.widths.length) {
            logger.severe("Bad number of widths. Actual: " + widths.length + " expected: " + this.widths.length);
            return;
        }
        this.widths = widths;
    }
    public void setHeights(int[] heights) {
        if (heights.length != this.heights.length) {
            logger.severe("Bad number of heights. Actual: " + heights.length + " expected: " + this.heights.length);
            return;
        }
        this.heights = heights;
    }
    public int getWidth() {
        int width = 0;
        for (int i = 0; i < widths.length; i++) {
            width += widths[i];
        }
        return width;
    }
    public int getHeight() {
        int height = 0;
        for (int i = 0; i < heights.length; i++) {
            height += heights[i];
        }
        return height;
    }
    public boolean add(String name, int startRow, int startColumn, int endRow, int endColumn) {
        if (startRow < 0 || startRow >= heights.length || startColumn < 0 || startColumn >= widths.length) {
            logger.severe(name + " has bad start row or column.");
            return false;
        }
        if (endRow < 0 || endRow >= heights.length || endColumn < 0 || endColumn >= heights.length) {
            logger.severe(name + " has bad end row or column.");
            return false;
        }
        if (startRow > endRow || startColumn > endColumn) {
            logger.severe("Bad interval for " + name + ".");
            return false;
        }
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startColumn; j <= endColumn; j++) {
                if (taken[i][j]) {
                    logger.severe("Area already taken. on (" + i + ", " + j + ") for " + name + ".");
                    return false;
                }
            }
        }
        Rectangle na = new Rectangle(startRow, startColumn, endRow, endColumn);
        namedRectangles.put(name, na);
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startColumn; j <= endColumn; j++) {
                taken[i][j] = true;
            }
        }
        return true;
    }
    public void setPA(PositionedArea pa) {
        gridPA = pa;
    }
    public PositionedArea getPA(String name) throws Exception {
        Rectangle na = namedRectangles.get(name);
        if (na == null) {
            throw new Exception("No such named area " + name + ".");
        }
        int x = gridPA.pos.x;
        int y = gridPA.pos.y;
        for (int i = 0; i < na.startRow; i++) {
            y += heights[i];
        }
        for (int i = 0; i < na.startColumn; i++) {
            x += widths[i];
        }
        int width = 0;
        for (int i = na.startColumn; i <= na.endColumn; i++) {
            width += widths[i];
        }
        int height = 0;
        for (int i = na.startRow; i <= na.endRow; i++) {
            height += heights[i];
        }
        return new PositionedArea(x, y, width, height);
    }
}
