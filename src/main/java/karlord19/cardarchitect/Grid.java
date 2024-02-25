package karlord19.cardarchitect;

import java.util.HashMap;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

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
    private Integer width = null;
    private final int columns;
    private int[] heights;
    private Integer height = null;
    private final int rows;
    private HashMap<String, Rectangle> namedRectangles;
    private PositionedArea gridPA;
    public Grid(int rows, int columns) {
        taken = new boolean[rows][columns];
        widths = new int[columns];
        heights = new int[rows];
        namedRectangles = new HashMap<String, Rectangle>();
        bordersHor = new LineStyle[rows+1][columns];
        bordersVer = new LineStyle[columns+1][rows];
        this.rows = rows;
        this.columns = columns;
    }
    public Grid() {
        this(1, 1);
    }
    public void setWidths(int[] widths) {
        if (widths.length != columns) {
            logger.severe("Bad number of widths. Actual: " + widths.length + " expected: " + columns);
            return;
        }
        this.widths = widths;
        width = null;
    }
    public void setHeights(int[] heights) {
        if (heights.length != rows) {
            logger.severe("Bad number of heights. Actual: " + heights.length + " expected: " + rows);
            return;
        }
        this.heights = heights;
        height = null;
    }
    public int getWidth() {
        if (width != null) {
            return width;
        }
        width = 0;
        for (int i = 0; i < columns; i++) {
            width += widths[i];
        }
        return width;
    }
    public int getHeight() {
        if (height != null) {
            return height;
        }
        height = 0;
        for (int i = 0; i < rows; i++) {
            height += heights[i];
        }
        return height;
    }
    public boolean add(String name, int startRow, int startColumn, int endRow, int endColumn) {
        if (startRow < 0 || startRow >= rows) {
            logger.severe(name + " has bad start row.");
            return false;
        }
        if (startColumn < 0 || startColumn >= columns) {
            logger.severe(name + " has bad start column.");
            return false;
        }
        if (endRow < 0 || endRow >= rows) {
            logger.severe(name + " has bad end row.");
            return false;
        }
        if (endColumn < 0 || endColumn >= columns) {
            logger.severe(name + " has bad end column.");
            return false;
        }
        if (startRow > endRow || startColumn > endColumn) {
            logger.severe(name + " has bad interval.");
            return false;
        }
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startColumn; j <= endColumn; j++) {
                if (taken[i][j]) {
                    logger.severe("Area already taken on (" + i + ", " + j + "). Cant add " + name + ".");
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

    // borders related stuff

    /**
     * Horizontal lines
     * [0] is the top border of the first row
     * [1][2] is the top border of the second row in the third column
     */
    private LineStyle[][] bordersHor;
    /**
     * Vertical lines
     * [0] is the left border of the first column
     * [1][2] is the left border of the second column in the third row
     */
    private LineStyle[][] bordersVer;

    public void setHorBorder(LineStyle ls, int row, int startColumn, int endColumn) {
        if (row < 0 || row >= (rows + 1)) {
            logger.severe("Bad row for horizontal border.");
            return;
        }
        if (startColumn < 0 || endColumn >= columns || startColumn > endColumn) {
            logger.severe("Bad column interval for horizontal border.");
            return;
        }
        for (int i = startColumn; i <= endColumn; i++) {
            bordersHor[row][i] = ls;
        }
    }
    public void setVerBorder(LineStyle ls, int column, int startRow, int endRow) {
        if (column < 0 || column >= (columns + 1)) {
            logger.severe("Bad column for vertical border.");
            return;
        }
        if (startRow < 0 || endRow >= rows || startRow > endRow) {
            logger.severe("Bad row interval for vertical border.");
            return;
        }
        for (int i = startRow; i <= endRow; i++) {
            bordersVer[column][i] = ls;
        }
    }

    public void setAroundBorder(LineStyle lineStyle, String name, Direction dir) {
        Rectangle rectangle = namedRectangles.get(name);
        if (rectangle == null) {
            logger.severe("No such named area " + name + ".");
            return;
        }
        switch (dir) {
            case TOP:
                setHorBorder(lineStyle, rectangle.startRow, rectangle.startColumn, rectangle.endColumn);
                break;
            case BOTTOM:
                setHorBorder(lineStyle, rectangle.endRow + 1, rectangle.startColumn, rectangle.endColumn);
                break;
            case LEFT:
                setVerBorder(lineStyle, rectangle.startColumn, rectangle.startRow, rectangle.endRow);
                break;
            case RIGHT:
                setVerBorder(lineStyle, rectangle.endColumn + 1, rectangle.startRow, rectangle.endRow);
                break;
        }
    }
    public void setAroundBorder(LineStyle lineStyle, String name) {
        setAroundBorder(lineStyle, name, Direction.TOP);
        setAroundBorder(lineStyle, name, Direction.BOTTOM);
        setAroundBorder(lineStyle, name, Direction.LEFT);
        setAroundBorder(lineStyle, name, Direction.RIGHT);
    }
    private void stroke(PDFManager pdf, int fromX, int fromY, int difX, int difY, LineStyle ls) throws Exception {
        PDPageContentStream contentStream = pdf.getContentStream();
        pdf.setLineStyle(ls);
        PositionedArea pa = new PositionedArea(fromX, fromY, difX, difY);
        PDFManager.PDFPA pdfpa = pdf.transform(pa);
        contentStream.moveTo(pdfpa.pos.x, pdfpa.pos.y);
        contentStream.lineTo(pdfpa.pos.x + pdfpa.area.width, pdfpa.pos.y + pdfpa.area.height);
        contentStream.stroke();
    }
    public void drawBorders(PDFManager pdf) {
        int y = gridPA.pos.y;
        for (int row = 0; row < bordersHor.length; row++) {
            int x = gridPA.pos.x;
            for (int column = 0; column < bordersHor[row].length; column++) {
                if (bordersHor[row][column] != null) {
                    try {
                        stroke(pdf, x, y, widths[column], 0, bordersHor[row][column]);
                    } catch (Exception e) {
                        logger.severe("Failed to draw horizontal border on (" + row + ", " + column + ")");
                        e.printStackTrace();
                        return;
                    }
                }
                x += widths[column];
            }
            if (row < heights.length) {
                y += heights[row];
            }
        }
        int x = gridPA.pos.x;
        for (int column = 0; column < bordersVer.length; column++) {
            y = gridPA.pos.y;
            for (int row = 0; row < bordersVer[column].length; row++) {
                if (bordersVer[column][row] != null) {
                    try {
                        stroke(pdf, x, y, 0, heights[row], bordersVer[column][row]);
                    } catch (Exception e) {
                        logger.severe("Failed to draw vertical border.");
                        e.printStackTrace();
                        return;
                    }
                }
                y += heights[row];
            }
            if (column < widths.length) {
                x += widths[column];
            }
        }
    }
}
