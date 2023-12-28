package karlord19.cardarchitect;

public class Fit {
    public enum FitType {
        ORIGINAL,
        FIT_WIDTH,
        FIT_HEIGHT,
        FIT_BOTH
    };
    private FitType fitType = FitType.ORIGINAL;
    public void setFitType(FitType fitType) {
        this.fitType = fitType;
    }

    public enum FitPositionX {
        LEFT,
        CENTER,
        RIGHT
    };
    private FitPositionX fitPositionX = FitPositionX.LEFT;
    public void setFitPositionX(FitPositionX fitPositionX) {
        this.fitPositionX = fitPositionX;
    }

    public enum FitPositionY {
        TOP,
        CENTER,
        BOTTOM
    };
    private FitPositionY fitPositionY = FitPositionY.TOP;
    public void setFitPositionY(FitPositionY fitPositionY) {
        this.fitPositionY = fitPositionY;
    }

    public Area giveArea(Area area, Area boundingBox) {
        Area newArea = new Area();
        switch (fitType) {
            case ORIGINAL:
                newArea = area;
                break;
            case FIT_WIDTH:
                newArea.width = boundingBox.width;
                newArea.height = area.height * boundingBox.width / area.width;
                break;
            case FIT_HEIGHT:
                newArea.height = boundingBox.height;
                newArea.width = area.width * boundingBox.height / area.height;
                break;
            case FIT_BOTH:
                if (area.width / area.height > boundingBox.width / boundingBox.height) {
                    newArea.width = boundingBox.width;
                    newArea.height = area.height * boundingBox.width / area.width;
                } else {
                    newArea.height = boundingBox.height;
                    newArea.width = area.width * boundingBox.height / area.height;
                }
                break;
            default:
                break;
        }
        return newArea;
    }

    public PositionedArea givePositionedArea(Area area, PositionedArea boundingBox) {
        int x = 0;
        int y = 0;
        Area givenArea = giveArea(area, boundingBox.area);
        switch (fitPositionX) {
            case LEFT:
                x = boundingBox.pos.x;
                break;
            case CENTER:
                x = boundingBox.pos.x + (boundingBox.area.width - givenArea.width) / 2;
                break;
            case RIGHT:
                x = boundingBox.pos.x + boundingBox.area.width - givenArea.width;
                break;
            default:
                break;
        }
        switch (fitPositionY) {
            case TOP:
                y = boundingBox.pos.y;
                break;
            case CENTER:
                y = boundingBox.pos.y + (boundingBox.area.height - givenArea.height) / 2;
                break;
            case BOTTOM:
                y = boundingBox.pos.y + boundingBox.area.height - givenArea.height;
                break;
            default:
                break;
        }
        return new PositionedArea(x, y, givenArea.width, givenArea.height);
    }
}
