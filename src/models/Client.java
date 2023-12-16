package models;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;




/**
 * The Client Class.
 * This class deals with the communication with the SMTP server.
 * @author E.A NDAYIRAGIJE
 */
public class Client {
	
	/** The smtp socket. */
	private Socket smtpSocket;
	
	/** The writer. */
	private PrintWriter writer;
	
	/** The reader. */
	private BufferedReader reader;
	
	/** The reader stream. */
	private InputStreamReader readerStream;
	
	/** The msg. */
	private Message msg;
	
	/**
	 * Instantiates a new client.
	 *
	 * @param socket the socket that will establish an SMTP session
	 */
	public Client(Socket socket)
	{
		this.smtpSocket = socket;
		
		try
		{
			this.writer = new PrintWriter(this.smtpSocket.getOutputStream());
			this.readerStream = new InputStreamReader(this.smtpSocket.getInputStream());
			this.reader = new BufferedReader(this.readerStream);
			
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * Close resources.
	 *
	 * @param writer the writer
	 * @param reader the reader
	 * @param readerStream the reader stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void closeResources(PrintWriter writer, BufferedReader reader, InputStreamReader readerStream) throws IOException
	{
		if(writer != null)
		{
			writer.close();
		}
		if(readerStream != null)
		{
			readerStream.close();
		}
		if(reader != null)
		{
			reader.close();
		}
		if(smtpSocket != null)
		{
			smtpSocket.close();
		}
	}
	
	/**
	 * Read response.
	 * A helper method for reading from the socket input stream
	 *
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String readResponse() throws IOException
	{
		String response = this.reader.readLine();
		return response;
	}
	
	/**
	 * Write message.
	 * A helper method for writing to the socket output stream
	 *
	 * @param message the message
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void WriteMessage(String message) throws IOException
	{
		this.writer.println(message);
		this.writer.flush();
	}
	
	
	
	
	/**
	 * Send mail.
	 */
	public void SendMail()
	{
		try {
			System.out.println(readResponse());
			WriteMessage("HELO User");
			System.out.println(readResponse());
			WriteMessage("MAIL FROM:" + "<" +this.msg.getSenderAddress()+">");
			System.out.println(readResponse());
			WriteMessage("RCPT TO:" + "<"+this.msg.getRecptAddress()+">");
			System.out.println(readResponse());
			WriteMessage("DATA");
			System.out.println(readResponse());
			WriteMessage("MIME-Version: 1.0");
            WriteMessage("Content-Type: multipart/mixed; boundary=thisIsABoundary-!-!");
            WriteMessage("Subject: " + this.msg.getMessageSubject());
			WriteMessage("FROM: " + "<" +this.msg.getSenderAddress()+">");
			WriteMessage("TO: " + "<"+ this.msg.getRecptAddress()+">");
			LocalDateTime currentDateTime = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	        String formattedDateTime = currentDateTime.format(formatter);
			WriteMessage("Date: " + formattedDateTime);
			WriteMessage("");
			WriteMessage("--thisIsABoundary-!-!");
	        WriteMessage("Content-Type: text/plain; charset=\"UTF-8\"");
	        WriteMessage("");
	        WriteMessage(this.msg.getMessageContent());
			if(this.msg.hasAttachment())
			{
				WriteMessage("--thisIsABoundary-!-!");
				attachFile(this.msg.getAttachment());
			}
			WriteMessage(".");
			System.out.println(readResponse());
			WriteMessage("QUIT");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	
	/**
	 * Attach file.
	 *
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void attachFile(File file) throws IOException
	{
		String filePath = file.getAbsolutePath();
        byte[] attachmentBytes = readAttachmentBytes(filePath);
        String base64EncodedAttachment = Base64.getEncoder().encodeToString(attachmentBytes);
        
        WriteMessage("Content-Type: application/octet-stream");
        WriteMessage("Content-Disposition: attachment; filename=" + this.msg.getAttachment().getName());
        WriteMessage("Content-Transfer-Encoding: base64");
        WriteMessage("");
        WriteMessage(base64EncodedAttachment);
	}
	
	/**
	 * Read attachment bytes.
	 *
	 * @param path the path
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] readAttachmentBytes(String path) throws IOException {
//	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//	    try (FileInputStream inputStream = new FileInputStream(path)) {
//	        int data;
//	        while ((data = inputStream.read()) != -1) {
//	            byteArrayOutputStream.write(data);
//	        }
//	    }
	    //return byteArrayOutputStream.toByteArray();
		return Files.readAllBytes(Paths.get(path));
	}

	/**
	 * Sets the msg.
	 *
	 * @param msg the new msg
	 */
	public void setMsg(Message msg) {
		this.msg = msg;
	}
	
	
}
