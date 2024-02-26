package karlord19.cardarchitect;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Card
 * 
 * A card is a collection of Drawables in a grid.
 * Grid can be modified to have different widths and heights.
 * Drawables can span in multiple cells to form rectangles.
 * Drawables should be named to be referenced later.
 * Drawables can have borders around them in different styles in chosen directions.
 */
public class Card extends AbstractDrawable {
    private HashMap<String, AbstractDrawable> namedElements = new HashMap<String, AbstractDrawable>();
    private int rows;
    private int columns;
    private Grid grid;
    private static final Logger logger = Logger.getLogger(Card.class.getName());

    /**
     * Create a card with a grid of rows and columns.
     * @param rows
     * @param columns
     */
    public Card(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        grid = new Grid(rows, columns);
        setWidthsEqual(50000);
        setHeightsEqual(50000);
    }

    /**
     * Create a card with a 1x1 grid.
     */
    public Card() {
        this(1, 1);
    }

    /**
     * Set the widths of the columns.
     * @param widths
     */
    public void setWidths(int[] widths) {
        if (widths.length != columns) {
            logger.severe("Bad number of widths. Actual: " + widths.length + " expected: " + columns);
            return;
        }
        grid.setWidths(widths);
    }

    /**
     * Set the heights of the rows.
     * @param heights
     */
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

    /**
     * Set the widths of the columns to be equal.
     * 
     * In case of a remainder, the first cells will have one more micrometer.
     * @param widthSum
     */
    public void setWidthsEqual(int widthSum) {
        int[] widths = distributeEqual(widthSum, columns);
        logger.config("All widths set to: " + widths[0]);
        grid.setWidths(widths);
    }

    /**
     * Set the heights of the rows to be equal.
     * 
     * In case of a remainder, the first cells will have one more micrometer.
     * @param heightSum
     */
    public void setHeightsEqual(int heightSum) {
        int[] heights = distributeEqual(heightSum, rows);
        logger.config("All heights set to: " + heights[0]);
        grid.setHeights(heights);
    }

    /**
     * Get the width of the card.
     * 
     * @return
     */
    public int getWidth() {
        return grid.getWidth();
    }

    /**
     * Get the height of the card.
     * 
     * @return
     */
    public int getHeight() {
        return grid.getHeight();
    }

    /**
     * Add a drawable to the card.
     * 
     * The drawable will span from startRow, startColumn to endRow, endColumn.
     * @param drawable
     * @param name
     * @param startRow
     * @param startColumn
     * @param endRow
     * @param endColumn
     */
    public void add(AbstractDrawable drawable, String name, int startRow, int startColumn, int endRow, int endColumn) {
        if (grid.add(name, startRow, startColumn, endRow, endColumn)) {
            namedElements.put(name, drawable);
        } else {
            logger.severe("Did not add " + name + " to cells (" + startRow + ", " + startColumn + ") - (" + endRow + ", " + endColumn + ") as it is already taken.");
        }
    }

    /**
     * Add a drawable to the card.
     * 
     * The drawable will not span, it will be in the cell row, column.
     * @param drawable
     * @param name
     * @param row
     * @param column
     */
    public void add(AbstractDrawable drawable, String name, int row, int column) {
        add(drawable, name, row, column, row, column);
    }

    /**
     * Add a drawable to the card.
     * 
     * The drawable will be in the cell 0,0. Good for single cell cards.
     * The name will be empty.
     * @param drawable
     */
    public void add(AbstractDrawable drawable) {
        add(drawable, "", 0, 0);
    }

    /**
     * Add a border around a drawable in a direction.
     * @param name
     * @param ls
     * @param dir
     */
    public void addBorder(String name, LineStyle ls, Direction dir) {
        grid.setAroundBorder(ls, name, dir);
    }

    /**
     * Add a border around a drawable in all directions.
     * @param name
     * @param ls
     */
    public void addBorderAround(String name, LineStyle ls) {
        grid.setAroundBorder(ls, name);
    }
    
    @Override
    void draw(PositionedArea pa, int index, PDFManager pdf) {
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
            AbstractDrawable drawable = namedElements.get(name);
            drawable.draw(subPA, index, pdf);
        }
        grid.drawBorders(pdf);
    }
}
