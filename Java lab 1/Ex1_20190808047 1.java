/*
* @author :  Burak Aydın 
* @number : 20190808047
* @date : 03.03.2023
*/
import java.util.ArrayList;
import java.util.Scanner;


public class Ex1_20190808047 {
  
 
    public static void main(String[] args) {

       
    Stock stock = new Stock("ORCL", "Oracle Corporation");
    stock.setPreviousClosingPrice(34.5);
    stock.setCurrentPrice(34.35);

    System.out.println("price-change percentage : " + stock.getChangePercent());

    adjustmentOfFan();




    }
    public static void adjustmentOfFan(){
        Scanner scan =new Scanner(System.in);
        System.out.print("Please enter the number of object : ");
        int numberOfObject =scan.nextInt();
    
        Fan fan =new Fan();
    
        ArrayList<Fan> fans = new ArrayList<Fan>();
    
    
        for(int i=0;i<numberOfObject;i++){
            
            if(i%2==0){ 
                Fan fanEven =new Fan();
                fans.add(fanEven);
            }
            else {
                Fan fanOdd = new Fan((fan.getRadius()+1),"yellow");
                fans.add(fanOdd);
    
                if(i%3==0 && fanOdd.isOn() ){
                    if(fanOdd.getSpeed()==fan.SLOW){
                        fanOdd.setSpeed(fan.MEDIUM);
                    }
                    else if(fanOdd.getSpeed()==fan.MEDIUM){
                        fanOdd.setSpeed(fan.FAST);
    
                    }
                    else if(fanOdd.getSpeed()==fan.FAST){
                        fanOdd.setSpeed(fan.SLOW);
    
                    }
    
                }
    
            }   
        
        }

    }
}

class Stock{
    String symbol;
    String name;
    double previousClosingPrice;
    double currentPrice;
    
    public Stock() {
    }

    public Stock(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public double getChangePercent() {
        return (this.currentPrice - this.previousClosingPrice) / this.previousClosingPrice;
    }

    public void setPreviousClosingPrice(double previousClosingPrice) {
        this.previousClosingPrice = previousClosingPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
class Fan {
    public static final int SLOW = 1;
    public static final int MEDIUM = 2;
    public static final int FAST = 3;

    private int speed = SLOW;
    private boolean on = false;
    private double radius = 5;
    private String color = "blue";

    public Fan(double radius, String color) {
        this.radius = radius;
        this.color = color;
    }

    public Fan() {
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public double  getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString() {
        if (isOn()) {
            return "Speed: " + getSpeed() + " Color: " + getColor() + " Radius: " + getRadius();
        } else {
            return "Color: " + getColor() + " Radius: " + getRadius() + "fan is off";
        }
    }

}
