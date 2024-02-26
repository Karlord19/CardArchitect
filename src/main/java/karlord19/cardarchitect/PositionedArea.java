package karlord19.cardarchitect;

/**
 * PositionedArea
 * A class that represents an area with a position.
 * Its elements are public and final.
 */
class PositionedArea {
    final Area area;
    final Position pos;
    PositionedArea() {
        area = new Area(0, 0);
        pos = new Position(0, 0);
    }
    public String toString() {
        return "posarea: " + pos.toString() + " " + area.toString();
    }
    PositionedArea(Position pos, Area area) {
        this.area = area;
        this.pos = pos;
    }
    PositionedArea(int x, int y, int width, int height) {
        this.area = new Area(width, height);
        this.pos = new Position(x, y);
    }
}
