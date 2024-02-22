package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import java.util.logging.Logger;

/**
 * The Border class represents the borders of a grid in a card layout.
 * It allows setting horizontal and vertical borders, as well as borders around a specific rectangle.
 */
public class Border {
    public static class LineStyle{
        public int width;
        public PDColor color;
        public LineStyle(int width, PDColor color){
            this.width = width;
            this.color = color;
        }
    }

    private Logger logger = Logger.getLogger(Border.class.getName());

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

    public Border(int rows, int columns){
        bordersHor = new LineStyle[rows+1][columns];
        bordersVer = new LineStyle[columns+1][rows];
    }

    
    /**
     * Sets the horizontal border style for a specified column and row interval.
     *
     * @param lineStyle The line style to be set for the border.
     * @param column The column index of the border.
     * @param startRow The starting row index of the border interval.
     * @param endRow The ending row index of the border interval.
     */
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
    /**
     * Sets the vertical border style for a specified row and column interval.
     *
     * @param lineStyle   the line style to set for the border
     * @param row         the row index of the border
     * @param startColumn the starting column index of the border
     * @param endColumn   the ending column index of the border
     */
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
    public void setAroundBorder(LineStyle lineStyle, Grid.Rectangle rectangle, BorderPos borderPos) {
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
    public void setAroundBorder(LineStyle lineStyle, Grid.Rectangle rectangle) {
        setAroundBorder(lineStyle, rectangle, BorderPos.TOP);
        setAroundBorder(lineStyle, rectangle, BorderPos.BOTTOM);
        setAroundBorder(lineStyle, rectangle, BorderPos.LEFT);
        setAroundBorder(lineStyle, rectangle, BorderPos.RIGHT);
    }
}
