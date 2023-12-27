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
        PositionedArea positionedArea = new PositionedArea();
        positionedArea.area = giveArea(area, boundingBox.area);
        switch (fitPositionX) {
            case LEFT:
                positionedArea.pos.x = boundingBox.pos.x;
                break;
            case CENTER:
                positionedArea.pos.x = boundingBox.pos.x + (boundingBox.area.width - positionedArea.area.width) / 2;
                break;
            case RIGHT:
                positionedArea.pos.x = boundingBox.pos.x + boundingBox.area.width - positionedArea.area.width;
                break;
            default:
                break;
        }
        switch (fitPositionY) {
            case TOP:
                positionedArea.pos.y = boundingBox.pos.y;
                break;
            case CENTER:
                positionedArea.pos.y = boundingBox.pos.y + (boundingBox.area.height - positionedArea.area.height) / 2;
                break;
            case BOTTOM:
                positionedArea.pos.y = boundingBox.pos.y + boundingBox.area.height - positionedArea.area.height;
                break;
            default:
                break;
        }
        return positionedArea;
    }
}
