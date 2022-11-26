package labs.lab_2;

public class Point3d extends Point2d {
    
    private double zCoord;

    public Point3d (double x, double y, double z){
        super(x, y);
        zCoord = z;
    }

    public Point3d (){
        this(0, 0, 0);
    }

    public double getZ(){
        return zCoord;
    }

    public void setZ(double val){
        zCoord = val;
    }

    public double distanceTo(Point3d points1){
        return Math.sqrt(Math.pow(this.getX() - points1.getX(), 2) + Math.pow(this.getY() - points1.getY(), 2) + Math.pow(this.getZ() - points1.getZ(), 2));
    }

    public boolean isEqual(Point3d points1){
        if (points1.getX() == this.getX() & points1.getY() == this.getY() & points1.getZ() == this.getZ()){
            return true;
        }
        return false;
    }
}