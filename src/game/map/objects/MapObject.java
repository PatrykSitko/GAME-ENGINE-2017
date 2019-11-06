package game.map.objects;

import game.map.bounds.MapBounds;
import game.shapes.Shape;
import java.awt.Graphics2D;

public abstract class MapObject {

    protected Shape shape;
    private MapBounds mapBounds;

    public MapObject(MapBounds mapBounds) {
        shape = new Shape();
        this.mapBounds = mapBounds;
    }

    public MapBounds getMapBounds() {
        return mapBounds;
    }

    public void setMapBounds(MapBounds mapBounds) {
        this.mapBounds = mapBounds;
    }

    public abstract void update();

    public abstract void draw(Graphics2D g);
}
