public class Point {
    public static final float DEFAULT_COORDINATE = 0.f;
    float x;
    float y;

    Point (float iX, float iY) {
        this.x = iX;
        this.y = iY;
    }

    Point (Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    @Override
    public boolean equals(Object o){
       if (o.getClass() != this.getClass())
           return false;
       else return Float.compare(x, ((Point)o).x) == 0 &&
               Float.compare(y, ((Point)o).y) == 0;
    }
}