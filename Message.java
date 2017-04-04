package csp.ela.develop.smarthealthcare;

/**
 * Message is a Custom Object to encapsulate message information/fields
 * 
 *
 */
public class Message {
	/**
	 * The content of the message
	 */
	
	String message;
	
	String msg_time;
	/**
	 * boolean to determine, who is sender of this message
	 */
	boolean isMine;
	/**
	 * boolean to determine, whether the message is a status message or not.
	 * it reflects the changes/updates about the sender is writing, have entered text etc
	 */
	boolean isStatusMessage;
	
	/**
	 * Constructor to make a Message object
	 */
	public Message(String message, String msg_time, boolean isMine) {
		super();
		this.message = message;
		this.msg_time = msg_time;
		this.isMine = isMine;
		this.isStatusMessage = false;
	}
	public String getMsg_time()
	{
		return msg_time;
	}
	public void setMsg_time(String msg_time)
	{
		this.msg_time = msg_time;
	}
	/**
	 * Constructor to make a status Message object
	 * consider the parameters are swaped from default Message constructor,
	 *  not a good approach but have to go with it.
	 */
	public Message(boolean status, String message) {
		super();
		this.message = message;
		this.isMine = false;
		this.isStatusMessage = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isMine() {
		return isMine;
	}
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
	public boolean isStatusMessage() {
		return isStatusMessage;
	}
	public void setStatusMessage(boolean isStatusMessage) {
		this.isStatusMessage = isStatusMessage;
	}
	
	
}
