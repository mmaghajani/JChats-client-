import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Listener {

	private BufferedReader br;
	private Inbox inbox ;
	
	public Listener(BufferedReader br) {
		
		inbox = new Inbox() ;
		this.br = br;
	}

	public void run() {
		try {
			String message;
			do {
				message = br.readLine();
				ServerMessage sm = new ServerMessage() ;
				sm.setMessage(message);
				sm.setKey(br.readLine());
				
				if( message.equals("Request for add") ) 
					sm.setValue(br.readLine());
				
				if( message.equals("You are accepted") ) 
					sm.setValue(br.readLine());
				
				if( message.equals("Receive message") ){
					sm.setValue(br.readLine());
					sm.setSender( br.readLine());
				}
				
				if( !message.equals("terminate") )
					inbox.addMessage(sm);
				
				
			} while (!message.equals("terminate"));
			
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
