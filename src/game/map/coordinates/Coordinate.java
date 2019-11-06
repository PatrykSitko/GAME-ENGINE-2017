/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.map.coordinates;

/**
 *
 * @author Patryk Sitko
 */
public class Coordinate {

    public double x = 0d;
    public double y = 0d;

    public Coordinate() {
        this(0d, 0d);
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

    public Boolean equals(double x, double y) {
        return this.x == x && this.y == y;
    }

    public Boolean equals(int x, int y) {
        return ((int) this.x) == x && ((int) this.y) == y;
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
        final Coordinate other = (Coordinate) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        return Double.doubleToLongBits(this.y) == Double.doubleToLongBits(other.y);
    }

    public Coordinate copy() {
        return new Coordinate(this.x, this.y);
    }

}
