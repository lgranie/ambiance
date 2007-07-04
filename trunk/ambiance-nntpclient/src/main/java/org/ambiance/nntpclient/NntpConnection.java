package org.ambiance.nntpclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.ambiance.nntpclient.model.NntpServer;
import org.ambiance.nntpclient.model.StatusResponse;

/**
 * An abstraction of an NNTP {@link InputStream} and {@link OutputStream} pair,
 * together with the relevant attributes.
 */
public class NntpConnection {

	private static final int SERVER_READY_POSTING_ALLOWED = 200;

	private static final int SERVER_READY_POSTING_NOT_ALLOWED = 201;

	private static final int CLOSING_CONNECTION = 205;

	/** <tt>"\r\n"</tt>, as bytes. */
	private static final byte[] CRLF = new byte[] { (byte) 13, (byte) 10 };

	private BufferedOutputStream output;

	private BufferedInputStream input;

	private Socket socket;

	private String currentGroup;

	private NntpConnectionManager ncm;

	public NntpConnection(NntpConnectionManager ncm, NntpServer server)
			throws NntpException {
		this.ncm = ncm;
		this.currentGroup = "none";

		try {
			// Initializing socket
			socket = new Socket(InetAddress.getByName(server.getHostname()),
					server.getPort());

			// Initializing socket input and output
			input = new BufferedInputStream(socket.getInputStream());
			output = new BufferedOutputStream(socket.getOutputStream());

			// Waiting server response
			StatusResponse srsp = new StatusResponse(readLine());

			switch (srsp.getStatus()) {
			case NntpConnection.SERVER_READY_POSTING_ALLOWED:
			case NntpConnection.SERVER_READY_POSTING_NOT_ALLOWED:
				break;
			default:
				throw new NntpException("Error while connectiong server : "
						+ srsp.getMessage());
			}
		} catch (UnknownHostException uhe) {
			throw new NntpException("Server unkown", uhe);
		} catch (IOException ioe) {
			throw new NntpException("Server unreachable", ioe);
		}

	}

	/**
	 * Closes everything out.
	 */
	public void close() throws NntpException {
		if (isAvailable()) {
			if (writeCommand("QUIT").getStatus() != NntpConnection.CLOSING_CONNECTION)
				throw new NntpException("Unable to close connection");
		}

		if (null != output) {
			OutputStream temp = output;
			output = null;
			try {
				temp.close();
			} catch (Exception ex) {
				// ignored
			}
		}

		if (null != input) {
			InputStream temp = input;
			input = null;
			try {
				temp.close();
			} catch (Exception ex) {
				// ignored
			}
		}

		if (null != socket) {
			Socket temp = socket;
			socket = null;
			try {
				temp.close();
			} catch (Exception ex) {
				// ignored
			}
		}
	}

	public StatusResponse writeCommand(String data) throws NntpException {
		if (isAvailable()) {
			try {
				output.write(data.getBytes());
				output.write(CRLF);
				output.flush();

				return new StatusResponse(readLine());
			} catch (IOException ioe) {
				throw new NntpException("Unable to write command to output",
						ioe);
			}
		}
		return null;
	}

	public String getCurrentGroup() {
		return currentGroup;
	}

	public void setCurrentGroup(String groupname) {
		currentGroup = groupname;
	}

	public BufferedInputStream getInput() {
		return input;
	}

	public boolean isAvailable() {
		return socket.isConnected();
	}

	/**
	 * Read up to <tt>"\n"</tt> from an (unchunked) input stream. If the
	 * stream ends before the line terminator is found, the last part of the
	 * string will still be returned. If no input data available,
	 * <code>null</code> is returned.
	 * 
	 * @param inputStream
	 *            the stream to read from
	 * 
	 * @throws IOException
	 *             if an I/O problem occurs
	 * @return a line from the stream
	 */
	public String readLine() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		if (isAvailable()) {
			int ch;
			while ((ch = input.read()) >= 0) {
				baos.write(ch);
				if (ch == '\n') {
					break;
				}
			}
		}
		if (baos.size() == 0) {
			return null;
		}

		// strip CR and LF from the end
		byte[] buf = baos.toByteArray();
		int len = buf.length;
		int offset = 0;
		if (len > 0) {
			if (buf[len - 1] == '\n') {
				offset++;
				if (len > 1) {
					if (buf[len - 2] == '\r') {
						offset++;
					}
				}
			}
		}
		return new String(buf, 0, len - offset);
	}

	public void release() {
		ncm.releaseConnection(this);
	}

}
