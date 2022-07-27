public class Point {
    float mX;
    float mY;

    Point (float iX, float iY) {
        this.mX = iX;
        this.mY = iY;
    }

    Point (Point other) {
        this.mX = other.mX;
        this.mY = other.mY;
    }
}