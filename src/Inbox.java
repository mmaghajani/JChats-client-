import java.util.ArrayList;


public class Inbox {

	private static ArrayList<ServerMessage> inbox ;
	
	public Inbox(){
		inbox = new ArrayList<ServerMessage>() ;
	}
	
	public void addMessage( ServerMessage SM ){
		inbox.add(SM) ;
	}
	
	public void delMessage( ServerMessage SM ){
		inbox.remove(SM) ;
	}
	
	public void delMessage( int x ){
		inbox.remove(x) ;
	}
	
	public ArrayList<ServerMessage> getMessages(){
		return inbox ;
	}
}
