package game.shapes;

import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.Objects;

public final class Shape {

    protected Geometry.Shapes shape;
    protected boolean contains;

    protected int width = 0;
    protected int height = 0;
    protected int radius = 0;
    protected int perpendicular = 0;

    protected int[] xPolygons;
    protected int[] yPolygons;

    private int x = 0;
    private int y = 0;

    public Shape() {
        this.shape = Geometry.Shapes.SHAPELESS;
    }

    protected Shape(Shape s) {
        this.shape = s.shape;
        set(Geometry.X, s.get(Geometry.X))
                .set(Geometry.Y, s.get(Geometry.Y))
                .set(Geometry.WIDTH, s.get(Geometry.WIDTH))
                .set(Geometry.RADIUS, s.get(Geometry.RADIUS))
                .set(Geometry.PERPENDICULAR, s.get(Geometry.PERPENDICULAR))
                .set(Geometry.PERIMETER, s.get(Geometry.PERIMETER))
                .set(Geometry.AREA, s.get(Geometry.AREA));
        this.xPolygons = s.xPolygons;
        this.yPolygons = s.yPolygons;
    }

    public final Shape set(Geometry g, int value) {
        switch (g) {
            default:
                return this;
            case X:
                this.x = value;
                return this;
            case Y:
                this.y = value;
                return this;
            case WIDTH:
                if (shape == Geometry.Shapes.CIRCULAR) {
                    this.radius = value;
                    this.height = value;
                }
                this.width = value;
                return this;
            case HEIGHT:
                if (shape == Geometry.Shapes.CIRCULAR) {
                    this.width = value;
                    this.radius = value;
                }
                this.height = value;
                return this;
            case RADIUS:
                if (shape == Geometry.Shapes.CIRCULAR) {
                    this.width = value;
                    this.height = value;
                }
                this.radius = value;
                return this;
            case PERPENDICULAR:
                this.perpendicular = value;
                return this;
        }
    }

    public Shape setShape(Geometry.Shapes shape) {
        this.shape = shape;
        return this;
    }

    public int get(Geometry g) {
        switch (g) {
            default:
                return 0;
            case X:
                return x;
            case Y:
                return y;
            case WIDTH:
                return width;
            case HEIGHT:
                return height;
            case RADIUS:
                return radius;
            case PERPENDICULAR:
                return perpendicular;
            case AREA:
                switch (shape) {
                    default:
                    case SHAPELESS:
                        return 0;
                    case TRIANGULAR:
                        return width * height / 2;
                    case RECTANGULAR:
                        return width * height;
                    case CIRCULAR:
                        return (int) (radius * radius * Math.PI);
                }
            case PERIMETER:
                switch (shape) {
                    default:
                    case SHAPELESS:
                        return 0;
                    case TRIANGULAR:
                        return (width + height + perpendicular);
                    case RECTANGULAR:
                        return (width * 2) + (height * 2);
                    case CIRCULAR:
                        return (int) (2 * Math.PI * radius);
                }
            case ANGLES:
                switch (shape) {
                    default:
                    case SHAPELESS:
                        return 0;
                    case TRIANGULAR:
                        return 3;
                    case RECTANGULAR:
                        return 4;
                    case CIRCULAR:
                        return 360;
                }
        }
    }

    public Geometry.Shapes getShape() {
        return shape;
    }

    public boolean compare(Geometry g, Shape s) {
        switch (g) {
            default:
                return false;
            case X:
                return x == s.x;
            case Y:
                return y == s.y;
            case WIDTH:
                return width == s.width;
            case HEIGHT:
                return height == s.height;
            case RADIUS:
                return radius == s.radius;
            case PERPENDICULAR:
                return perpendicular == s.perpendicular;
            case AREA:
                return get(Geometry.AREA) == s.get(Geometry.AREA);
            case PERIMETER:
                return get(Geometry.PERIMETER) == s.get(Geometry.PERIMETER);
        }
    }

    public Shape grow(int diameter) {
        if (null != shape) {
            switch (shape) {
                default:
                case SHAPELESS:
                    break;
                case TRIANGULAR:
                    set(Geometry.RADIUS, get(Geometry.RADIUS) + diameter).
                            set(Geometry.PERPENDICULAR, get(Geometry.PERPENDICULAR) + diameter);
                    break;
                case RECTANGULAR:
                    set(Geometry.WIDTH, get(Geometry.WIDTH) + diameter).
                            set(Geometry.HEIGHT, get(Geometry.HEIGHT) + diameter);
                    break;
                case CIRCULAR:
                    set(Geometry.RADIUS, get(Geometry.RADIUS) + diameter);
                    break;
            }
        }
        return this;
    }

    public boolean hasContained() {
        return contains;
    }

    public boolean hasContained(boolean b) {
        return contains = b;
    }

    public boolean contains(int x, int y) {
        switch (shape) {
            default:
                return contains = false;

            case SHAPELESS:
                updateShapeBounds(Geometry.Shapes.SHAPELESS);
                return contains = this.x == x && this.y == y;

            case TRIANGULAR:
                updateShapeBounds(Geometry.Shapes.TRIANGULAR);
                if (x < get(Geometry.X) || x > get(Geometry.X) + get(Geometry.WIDTH) || y < get(Geometry.Y) || y > get(Geometry.Y) + get(Geometry.HEIGHT)) {
                    return false;
                } else if (x <= get(Geometry.X) + get(Geometry.PERPENDICULAR)) {
                    return contains = !Line2D.linesIntersect(get(Geometry.X), get(Geometry.Y) + get(Geometry.HEIGHT), get(Geometry.X) + get(Geometry.PERPENDICULAR),
                            get(Geometry.Y), x, y, get(Geometry.X) + get(Geometry.PERPENDICULAR), get(Geometry.Y) + get(Geometry.HEIGHT));
                } else {
                    return contains = !Line2D.linesIntersect(get(Geometry.X) + get(Geometry.PERPENDICULAR), get(Geometry.Y), get(Geometry.X) + get(Geometry.WIDTH),
                            get(Geometry.Y) + get(Geometry.HEIGHT), get(Geometry.X) + get(Geometry.PERPENDICULAR), get(Geometry.Y) + get(Geometry.HEIGHT), x, y);
                }
            case RECTANGULAR:
                updateShapeBounds(Geometry.Shapes.RECTANGULAR);
                return contains = x >= get(Geometry.X) && x <= get(Geometry.X) + get(Geometry.WIDTH) && y >= get(Geometry.Y) && y <= get(Geometry.Y) + get(Geometry.HEIGHT);

            case CIRCULAR:
                updateShapeBounds(Geometry.Shapes.CIRCULAR);
                boolean containsMousePointer = false,
                 containsCoordinateX = (x >= get(Geometry.X) - get(Geometry.RADIUS) && x <= get(Geometry.X) + get(Geometry.RADIUS)),
                 eastContainsCoordinateY = (get(Geometry.X) <= x),
                 southContainsCoordinateY = (get(Geometry.Y) <= y);
                final int southEastSector = 1,
                 southWestSector = 2,
                 northWestSector = 3,
                 northEastSector = 4;
                int startingDegree = 0,
                 finnishingDegree = 0,
                 sector = 0;
                if (!containsCoordinateX) {
                    return contains = false;
                } else {
                    if (eastContainsCoordinateY && southContainsCoordinateY) {
                        sector = southEastSector;
                    } else if (!eastContainsCoordinateY && southContainsCoordinateY) {
                        sector = southWestSector;
                    } else if (!eastContainsCoordinateY && !southContainsCoordinateY) {
                        sector = northWestSector;
                    } else if (eastContainsCoordinateY && !southContainsCoordinateY) {
                        sector = northEastSector;
                    }
                    switch (sector) {
                        case southEastSector:
                            startingDegree = 0;
                            finnishingDegree = 90;
                            break;
                        case southWestSector:
                            startingDegree = 90;
                            finnishingDegree = 180;
                            break;
                        case northWestSector:
                            startingDegree = 180;
                            finnishingDegree = 270;
                            break;
                        case northEastSector:
                            startingDegree = 270;
                            finnishingDegree = 360;
                            break;
                    }
                    for (; startingDegree < finnishingDegree; startingDegree++) {
                        switch (sector) {
                            case southEastSector:
                                containsMousePointer = xPolygons[startingDegree] >= x && yPolygons[startingDegree] >= y;
                                break;
                            case southWestSector:
                                containsMousePointer = xPolygons[startingDegree] <= x && yPolygons[startingDegree] >= y;
                                break;
                            case northWestSector:
                                containsMousePointer = xPolygons[startingDegree] <= x && yPolygons[startingDegree] <= y;
                                break;
                            case northEastSector:
                                containsMousePointer = xPolygons[startingDegree] >= x && yPolygons[startingDegree] <= y;
                                break;
                        }
                        if (containsMousePointer) {
                            return contains = true;
                        }
                    }
                }
                return contains = containsMousePointer;
        }
    }

    protected void updateShapeBounds(Geometry.Shapes s) {
        Geometry.Shapes previousShape = shape;
        switch (s) {
            default:
            case SHAPELESS:
                shape = Geometry.Shapes.SHAPELESS;
                xPolygons = new int[get(Geometry.ANGLES)];
                yPolygons = new int[get(Geometry.ANGLES)];
                shape = previousShape;
                break;
            case TRIANGULAR:
                shape = Geometry.Shapes.TRIANGULAR;
                xPolygons = new int[]{get(Geometry.X), get(Geometry.X) + get(Geometry.PERPENDICULAR), get(Geometry.X) + get(Geometry.WIDTH)};
                yPolygons = new int[]{get(Geometry.Y) + get(Geometry.HEIGHT), get(Geometry.Y), get(Geometry.Y) + get(Geometry.HEIGHT)};
                shape = previousShape;
                break;
            case RECTANGULAR:
                shape = Geometry.Shapes.RECTANGULAR;
                xPolygons = new int[]{get(Geometry.X), get(Geometry.X) + get(Geometry.WIDTH), get(Geometry.X) + get(Geometry.WIDTH), get(Geometry.X)};
                yPolygons = new int[]{get(Geometry.Y), get(Geometry.Y), get(Geometry.Y) + get(Geometry.HEIGHT), get(Geometry.Y) + get(Geometry.HEIGHT)};
                shape = previousShape;
                break;
            case CIRCULAR:
                shape = Geometry.Shapes.CIRCULAR;
                xPolygons = new int[get(Geometry.ANGLES)];
                yPolygons = new int[get(Geometry.ANGLES)];
                for (int degree = 0; degree < get(Geometry.ANGLES); degree++) {
                    xPolygons[degree] = get(Geometry.X) + (int) (radius * Math.cos(Math.toRadians(degree)));
                    yPolygons[degree] = get(Geometry.Y) + (int) (radius * Math.sin(Math.toRadians(degree)));
                }
                shape = previousShape;
                break;
        }
    }

    public Shape copy() {
        return new Shape(this);
    }

    public static enum Geometry {
        X, Y, WIDTH, HEIGHT, RADIUS, PERPENDICULAR, AREA, PERIMETER, ANGLES;

        public static enum Shapes {
            SHAPELESS, TRIANGULAR, RECTANGULAR, CIRCULAR;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.shape);
        hash = 41 * hash + (this.contains ? 1 : 0);
        hash = 41 * hash + this.width;
        hash = 41 * hash + this.height;
        hash = 41 * hash + this.radius;
        hash = 41 * hash + this.perpendicular;
        hash = 41 * hash + Arrays.hashCode(this.xPolygons);
        hash = 41 * hash + Arrays.hashCode(this.yPolygons);
        hash = 41 * hash + this.x;
        hash = 41 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Shape other = (Shape) obj;
        if (this.contains != other.contains) {
            return false;
        }
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if (this.radius != other.radius) {
            return false;
        }
        if (this.perpendicular != other.perpendicular) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.shape != other.shape) {
            return false;
        }
        if (!Arrays.equals(this.xPolygons, other.xPolygons)) {
            return false;
        }
        return Arrays.equals(this.yPolygons, other.yPolygons);
    }
}
