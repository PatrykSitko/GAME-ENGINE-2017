/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map.bounds;

import game.map.coordinates.Coordinate;
import game.shapes.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Patryk Sitko
 */
public class MapBounds {

    private ArrayList<Coordinate> mapBounds;
    private BufferedImage mapBoundsImage;

    public MapBounds(BufferedImage mapBoundsImage) {
        this.mapBounds = new ArrayList<>();
        this.mapBoundsImage = mapBoundsImage;
        pinCollisionLocations();
    }

    private void pinCollisionLocations() {
        for (int x = 0; x < mapBoundsImage.getWidth(); x++) {
            for (int y = 0; y < mapBoundsImage.getHeight(); y++) {
                if (!isTransparent(mapBoundsImage.getRGB(x, y))) {
                    mapBounds.add(new Coordinate(x, y));
                }
            }
        }
    }

    public Boolean collides(Shape s) {
        for (Coordinate c : mapBounds) {
            if (s.contains((int) c.x, (int) c.y)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTransparent(int pixel) {
        return (pixel & 0xff000000) >>> 24 == 0x00;
    }

    public static enum Axis {
        X, Y;
    }
}
