public class Line {
    private RealPoint point1;
    private RealPoint point2;

    public Line(RealPoint point1, RealPoint point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Line(double x1, double y1, double x2, double y2) {
        point1 = new RealPoint(x1, y1);
        point2 = new RealPoint(x2, y2);
    }

    public RealPoint getPoint1() {
        return point1;
    }

    public void setPoint1(RealPoint point1) {
        this.point1 = point1;
    }

    public RealPoint getPoint2() {
        return point2;
    }

    public void setPoint2(RealPoint point2) {
        this.point2 = point2;
    }
}
