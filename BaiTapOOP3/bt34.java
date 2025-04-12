package BaiTapOOP3;

import BaiTapOOP3.MyTime;

public class bt34 {

	public static void main(String[] args) {
		MyTime t1 = new MyTime(1, 2, 3); 
	      System.out.println(t1.tostring());
	      t1.setHour(4); 
	      t1.setMinute(5); 
	      t1.setSecond(6); 
	      System.out.println(t1.tostring());
	      System.out.println("Giờ: " + t1.getHour()); 
	      System.out.println("Phút: " + t1.getMinute()); 
	      System.out.println("Giây: " + t1.getSecond()); 
	      t1.setTime(23, 59, 58); 
	      System.out.println(t1.tostring());   
	      System.out.println(t1.nextSecond().tostring()); 
	      System.out.println(t1.nextSecond().nextSecond().tostring());
	      System.out.println(t1.previousSecond().tostring()); 
	      System.out.println(t1.previousSecond().previousSecond().tostring()); 

	}

}
