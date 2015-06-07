
public class ServerMessage {

	private String key ;
	private String message ;
	private String value ;
	private String sender ;
	public ServerMessage(){
		key = " " ;
		message = " " ;
		value = " " ;
		sender = " " ;
	}
	
	public void setKey(	String key ){
		this.key = key ;
	}
	
	public void setMessage( String mess ){
		message = mess ;
	}
	
	public String getKey(){
		return key ;
	}
	
	public String getMessage(){
		return message ;
	}
	
	public void setValue( String value ){
		this.value = value ;
	}
	
	public String getValue(){
		return value ;
	}
	
	public String getSender(){
		return sender ;
	}
	
	public void setSender( String s ){
		sender = s ;
	}
	
	
	@Override
	public boolean equals( Object SM ){
		if( key.equals( ( (ServerMessage)SM).getKey() ) && message.equals( ( (ServerMessage)SM).getMessage() ) ){
			return true ;
		}
		else
			return false ;
	}
	
}
