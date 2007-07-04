package org.ambiance.nntpclient;

import java.util.ArrayList;
import java.util.List;

import org.ambiance.nntpclient.model.NntpServer;

public class NntpConnectionManager {

	private List<NntpConnection> availablePool;

	private List<NntpConnection> usedPool;

	private NntpServer server;

	public NntpConnectionManager(NntpServer server) throws NntpException {
		this.server = server;

		// Initializing usedPool
		this.usedPool = new ArrayList<NntpConnection>();

		// Initializing availablePool
		this.availablePool = new ArrayList<NntpConnection>();
		for (int i = 0; i < server.getMaxConnections(); i++) {
			availablePool.add(new NntpConnection(this, server));
		}

		if (this.availablePool.size() != server.getMaxConnections()) {
			throw new NntpException("Enable to connect correctly to server");
		}
	}

	public synchronized NntpConnection getConnection() throws NntpException {
		// Wait until a conection is available
		while (availablePool.size() == 0) {
			try {
				this.wait();
			} catch (InterruptedException ie) {
				throw new NntpException("Error while retrieving connection", ie);
			}
		}

		// Return the first available connection
		NntpConnection conn = availablePool.remove(0);
		if (!conn.isAvailable())
			conn = new NntpConnection(this, server);
		usedPool.add(conn);
		return conn;
	}

	/**
	 * Release a connection : move it from usedPool to activablePool
	 * 
	 * @param conn
	 *            The connection to release
	 */
	public synchronized void releaseConnection(NntpConnection conn) {
		usedPool.remove(conn);
		availablePool.add(conn);
		this.notifyAll();
	}

	public NntpServer getServer() {
		return server;
	}

	public synchronized void closeAll() throws NntpException {
		for (NntpConnection conn : usedPool) {
			conn.close();
		}

		for (NntpConnection conn : availablePool) {
			conn.close();
		}
	}

}
