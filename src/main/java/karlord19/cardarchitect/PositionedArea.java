package karlord19.cardarchitect;

public class PositionedArea {
    public Area area;
    public Position pos;
    public PositionedArea() {
        area = new Area(0, 0);
        pos = new Position(0, 0);
    }
    public String toString() {
        return "posarea: " + pos.toString() + " " + area.toString();
    }
    public PositionedArea(Position pos, Area area) {
        this.area = area;
        this.pos = pos;
    }
    public PositionedArea(float x, float y, float width, float height) {
        this.area = new Area(width, height);
        this.pos = new Position(x, y);
    }
}
