package models;

import java.io.File;


/**
 * The Class Message.
 * 
 * @author E.A NDAYIRAGIJE
 */
public class Message {
	
	/** The sender address. */
	private String senderAddress;
	
	/** The recpt address. */
	private String recptAddress;
	
	/** The message subject. */
	private String messageSubject;
	
	/** The message content. */
	private String messageContent;
	
	/** The attachment. */
	private File attachment;
	
	/** The has attachment. */
	private boolean hasAttachment = false;
	
	/**
	 * Instantiates a new message.
	 */
	// This constructor is only for testing purposes
	public Message()
	{
	}
	
	/**
	 * Instantiates a new message.
	 *
	 * @param senderAddress the sender address
	 * @param recptAddress the recpt address
	 * @param messageContent the message content
	 * @param messageSubject the message subject
	 * @param attachment the attachment
	 */
	public Message(String senderAddress, String recptAddress, String messageContent, String messageSubject, File attachment)
	{
		this.senderAddress = senderAddress;
		this.recptAddress = recptAddress;
		this.messageSubject = messageSubject;
		this.messageContent = messageContent;
		if(attachment != null)
		{
			this.attachment = attachment;
			this.hasAttachment = true;
		}
		
	}

	/**
	 * Gets the sender address.
	 *
	 * @return the sender address
	 */
	public String getSenderAddress() {
		return senderAddress;
	}

	/**
	 * Gets the recpt address.
	 *
	 * @return the recpt address
	 */
	public String getRecptAddress() {
		return recptAddress;
	}

	/**
	 * Gets the message subject.
	 *
	 * @return the message subject
	 */
	public String getMessageSubject() {
		return messageSubject;
	}
	
	/**
	 * Gets the message content.
	 *
	 * @return the message content
	 */
	public String getMessageContent() {
		return messageContent;
	}
	
	/**
	 * Checks for attachment.
	 *
	 * @return true, if successful
	 */
	public boolean hasAttachment()
	{
		return hasAttachment;
	}

	/**
	 * Gets the attachment.
	 *
	 * @return the attachment
	 */
	public File getAttachment() {
		return attachment;
	}
	
	/**
	 * Checks for attachment.
	 *
	 * @return true, if successful
	 */
	public boolean HasAttachment() {
		return hasAttachment;
	}
	
	/**
	 * Sets the checks for attachment.
	 *
	 * @param condition the new checks for attachment
	 */
	public void setHasAttachment(boolean condition) {
		this.hasAttachment = condition;
	}

	/**
	 * Sets the sender address.
	 *
	 * @param senderAddress the new sender address
	 */
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	/**
	 * Sets the recpt address.
	 *
	 * @param recptAddress the new recpt address
	 */
	public void setRecptAddress(String recptAddress) {
		this.recptAddress = recptAddress;
	}

	/**
	 * Sets the message subject.
	 *
	 * @param messageSubject the new message subject
	 */
	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}

	/**
	 * Sets the message content.
	 *
	 * @param messageContent the new message content
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	/**
	 * Sets the attachment.
	 *
	 * @param attachment the new attachment
	 */
	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
	
	
}
