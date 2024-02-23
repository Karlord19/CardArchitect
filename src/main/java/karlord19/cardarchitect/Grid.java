package karlord19.cardarchitect;

import java.util.HashMap;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import karlord19.cardarchitect.PDFManager.LineStyle;

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
        bordersHor = new LineStyle[rows+1][columns];
        bordersVer = new LineStyle[columns+1][rows];
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
    public void setHorBorder(LineStyle lineStyle, int column, int startRow, int endRow) {
        if (column < 0 || column >= bordersVer.length) {
            logger.severe("Bad column for vertical border.");
            return;
        }
        if (startRow < 0 || startRow > bordersHor.length || endRow < 0 || endRow > bordersHor.length) {
            logger.severe("Bad row interval for vertical border.");
            return;
        }
        for (int i = startRow; i <= endRow; i++) {
            bordersVer[column][i] = lineStyle;
        }
    }
    public void setVerBorder(LineStyle lineStyle, int row, int startColumn, int endColumn) {
        if (row < 0 || row >= bordersHor.length) {
            logger.severe("Bad row for horizontal border.");
            return;
        }
        if (startColumn < 0 || startColumn >= bordersVer.length || endColumn < 0 || endColumn >= bordersVer.length) {
            logger.severe("Bad column interval for horizontal border.");
            return;
        }
        for (int i = startColumn; i <= endColumn; i++) {
            bordersHor[row][i] = lineStyle;
        }
    }
    public enum BorderPos {
        TOP, BOTTOM, LEFT, RIGHT
    }
    public void setAroundBorder(LineStyle lineStyle, String name, BorderPos borderPos) {
        Rectangle rectangle = namedRectangles.get(name);
        if (rectangle == null) {
            logger.severe("No such named area " + name + ".");
            return;
        }
        switch (borderPos) {
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
        setAroundBorder(lineStyle, name, BorderPos.TOP);
        setAroundBorder(lineStyle, name, BorderPos.BOTTOM);
        setAroundBorder(lineStyle, name, BorderPos.LEFT);
        setAroundBorder(lineStyle, name, BorderPos.RIGHT);
    }
    public void drawBorders(PDFManager pdf) {
        int x = gridPA.pos.x;
        int y = gridPA.pos.y;
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX borders");
        PDPageContentStream contentStream = pdf.getContentStream();
        for (int row = 0; row < bordersHor.length; row++) {
            for (int column = 0; column < bordersHor[row].length; column++) {
                if (bordersHor[row][column] != null) {
                    try {
                        float fromX = Metrics.m2p(x);
                        float fromY = Metrics.m2p(y);
                        float toX = Metrics.m2p(x + getWidth());
                        float toY = Metrics.m2p(y);
                        contentStream.setLineWidth(Metrics.m2p(bordersHor[row][column].width));
                        contentStream.moveTo(fromX, fromY);
                        contentStream.lineTo(toX, toY);
                        contentStream.setStrokingColor(bordersHor[row][column].color);
                        contentStream.stroke();
                    } catch (Exception e) {
                        logger.severe("Failed to draw vertical border.");
                    }
                }
            }
        }
    }
}
