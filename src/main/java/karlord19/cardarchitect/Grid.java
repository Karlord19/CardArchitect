package karlord19.cardarchitect;

import java.util.HashMap;

public class Grid {
    public class NamedArea {
        public String name;
        int startRow;
        int startColumn;
        int endRow;
        int endColumn;
        public NamedArea(String name, int startRow, int startColumn, int endRow, int endColumn) {
            this.name = name;
            this.startRow = startRow;
            this.startColumn = startColumn;
            this.endRow = endRow;
            this.endColumn = endColumn;
        }
    }
    private boolean[][] taken;
    private int[] widths;
    private int[] heights;
    private HashMap<String, NamedArea> namedAreas;
    private PositionedArea gridPA;
    public Grid(int rows, int columns) {
        taken = new boolean[rows][columns];
        namedAreas = new HashMap<String, NamedArea>();
    }
    public Grid() {
        this(1, 1);
    }
    public void setWidths(int[] widths) {
        this.widths = widths;
    }
    public void setHeights(int[] heights) {
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
        if (startRow < 0 || startRow >= taken.length || startColumn < 0 || startColumn >= taken[0].length) {
            System.out.println("Bad start row or column.");
            return false;
        }
        if (endRow < 0 || endRow >= taken.length || endColumn < 0 || endColumn >= taken[0].length) {
            System.out.println("Bad end row or column.");
            return false;
        }
        if (startRow > endRow || startColumn > endColumn) {
            System.out.println("Bad interval.");
            return false;
        }
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startColumn; j <= endColumn; j++) {
                if (taken[i][j]) {
                    System.out.println("Area already taken.");
                    return false;
                }
            }
        }
        NamedArea na = new NamedArea(name, startRow, startColumn, endRow, endColumn);
        namedAreas.put(name, na);
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
    public PositionedArea getPA(String name) {
        NamedArea na = namedAreas.get(name);
        if (na == null) {
            System.out.println("No such name: " + name);
            return null;
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
