package labs.lab_2;

import java.util.Scanner;

public class Lab1{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("Input point 1: ");
        String[] input1 = input.nextLine().split(" ");
        System.out.print("Input point 2: ");
        String[] input2 = input.nextLine().split(" ");
        System.out.print("Input point 3: ");
        String[] input3 = input.nextLine().split(" ");
        input.close();


        Point3d points1 = new Point3d(Double.parseDouble(input1[0]), Double.parseDouble(input1[1]), Double.parseDouble(input1[2]));
        Point3d points2 = new Point3d(Double.parseDouble(input2[0]), Double.parseDouble(input2[1]), Double.parseDouble(input2[2]));
        Point3d points3 = new Point3d(Double.parseDouble(input3[0]), Double.parseDouble(input3[1]), Double.parseDouble(input3[2]));

        if (!(points1.equals(points2) || points1.equals(points3) || points2.equals(points3)) )
            System.out.println(computeArea(points1, points2, points3));
        else
            System.out.println("Some points are same");
    }

    public static double computeArea(Point3d points1, Point3d points2, Point3d points3){
        
        double a = Math.abs(points2.distanceTo(points1));
        double b = Math.abs(points3.distanceTo(points2));
        double c = Math.abs(points1.distanceTo(points3));

        double half_perimeter = (a + b + c) / 2;

        double square = Math.sqrt(half_perimeter * (half_perimeter - a) * (half_perimeter -b) * (half_perimeter - c));

        double scale = Math.pow(10, 2);

        return Math.ceil((square * scale) / scale);
    }
}