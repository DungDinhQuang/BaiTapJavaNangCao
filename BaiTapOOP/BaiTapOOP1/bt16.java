package BaiTapOOP1;

public class bt16 {

	public static void main(String[] args) {
		  Account a1 = new Account("A101", "Tan Ah Teck", 88); 
	      System.out.println(a1.tostring());  
	      Account a2 = new Account("A102", "Kumar"); 
	      System.out.println(a2.tostring()); 
	      System.out.println("ID: " + a1.getId()); 
	      System.out.println("Name: " + a1.getName()); 
	      System.out.println("Số dư: " + a1.getBalance());
	      a1.credit(100); 
	      System.out.println(a1.tostring()); 
	      a1.debit(50); 
	      System.out.println(a1.tostring()); 
	      a1.debit(500);   
	      System.out.println(a1.tostring()); 
	      a1.transferTo(a2, 100);  
	      System.out.println(a1.tostring()); 
	      System.out.println(a2.tostring()); 
	}

}
