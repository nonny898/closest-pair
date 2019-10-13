import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;

public class ClosestPair {
  
  private static double[][] points;
  
  public static void main(String[] args) {
    try{
      BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
      String line;
      while ((line = bufferRead.readLine())!=null) {
        int amount = Integer.parseInt(line);
        int counter = 0;
        points = new double[amount][2];
        Map<Double, Double> tempMap = new HashMap<>();
        while (amount > 0) {
          line = bufferRead.readLine();
          String[] lst = line.split(" ");
          tempMap.put(Double.parseDouble(lst[0]), Double.parseDouble(lst[1]));
          amount--;
        }
        for (double x : tempMap.keySet()) {
          points[counter][0] = x;
          points[counter][1] = tempMap.get(x);
          counter++;
        }
        double result = closestPair(0, points.length-1);
        if (result == 10000.0) {
          System.out.println("INFINTIY");
        }
        else{
          System.out.println((double)Math.round(result * 10000d) / 10000d );
        }
      }
      bufferRead.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  
  private static double closestPair(int startPos, int stopPos) {
    if (startPos == stopPos) return 10000.0;
    else if ((stopPos - startPos) == 1) return distance(points[startPos],points[stopPos]);
    else {
      int mid = (int) ((stopPos - startPos) / 2.0);
      double d1 = closestPair(startPos, mid - 1 + startPos);
      double d2 = closestPair(startPos + mid, stopPos);
      double d = min(d1, d2);
      double xMid = points[mid][0];
      Map<Double, Double> tempMap = new HashMap<>();
      for (double[] point : points) {
        if (abs(point[0] - xMid) < d) {
          tempMap.put(point[1],point[0]);
        }
      }
      double[][] strip = new double[tempMap.size()][2];
      int counter = 0;
      for (double x : tempMap.keySet()) {
        strip[counter][0] = x;
        strip[counter][1] = tempMap.get(x);
        counter++;
      }
      return min(d, stripClosest(strip,tempMap.size(),d));
    }
  }
  
  private static double distance ( double[] pointA, double[] pointB){
    double d = sqrt(((pointA[0] - pointB[0]) * (pointA[0] - pointB[0])) + ((pointA[1] - pointB[1]) * (pointA[1] - pointB[1])));
    return Math.min(d, 10000.00);
  }
  
  private static double stripClosest(double[][] strip, int size, double d) {
    double min = d;
    for (int i = 0; i < size; i++) {
      for (int j = i+1; j < size && (strip[j][0] - strip[i][0]) < min; j++) {
        if (distance(strip[i],strip[j]) < min)
          min = distance(strip[i],strip[j]);
      }
    }
    return min;
  }
}
