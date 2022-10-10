public class Point {
    float x = 0.f;
    float y = 0.f;

    Point() {
    }

    Point(float iX, float iY) {
        x = iX;
        y = iY;
    }

    Point(Point other) {
        x = other.x;
        y = other.y;
    }

    public float dist(Point other) {
        return (float) Math.sqrt(
                Math.pow(x - other.x, 2) +
                        Math.pow(y - other.y, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass())
            return false;
        else return x == ((Point)o).x;
    }
}