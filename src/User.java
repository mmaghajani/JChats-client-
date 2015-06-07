import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable{

	 private String firstName;
	 private String lastName ;
	 private String userName ;
	 private String pass ;
	 private String birthDay ;
	 private String degree ;
	 private ArrayList<String> freinds ;
	 private int numOfFreinds ;
	 private String status ;
	 private int numOfPendingFreinds ;
	
	public User(){
		freinds = new ArrayList<String>() ;
		firstName = "" ;
		userName = "" ;
		lastName = "" ;
		pass = "" ;
		birthDay = " " ;
		degree = " " ;
		status = " " ;
	}
	
	public User( String s ){
		userName = s ;
	}
	
	public void setName( String s ){
		firstName = s ;
	}
	
	public void setLastName( String s ){
		lastName = s ;
	}
	
	public void setUserName( String s ){
		userName = s ;
	}
	
	public void setPass( String s ){
		pass = s ;
	}
	
	public void setBirthDay( String s ){
		birthDay = s ;
	}
	
	public void setDegree( String s ){
		degree = s ;
	}
	
	public String getName(){
		return firstName ;
	}
	
	public String getLastName(){
		return lastName ;
	}
	
	public String getUserName(){
		return userName ;
	}
	
	public String getPass(){
		return pass ;
	}
	
	public String getBirthDay(){
		return birthDay ;
	}
	
	public String getDegree(){
		return degree ;
	}
	
	public int getNumOfFreinds(){
		return numOfFreinds ;
	}
	
	public void setNumOfFreinds( int x ){
		numOfFreinds = x ;
	}
	
	public ArrayList<String> getFreinds(){
		return freinds ;
	}
	
	public void addFreind( String userName ){
		freinds.add(userName) ;
	}
	
	public void setStatus( String s ){
		status = s ;
	}

	public String getStatus(){
		return status ;
	}
	
	@Override
	public boolean equals( Object s ){
		if( userName.equals( ((User) s).getUserName() ) )
			return true ;
		else
			return false ;
	}
}
