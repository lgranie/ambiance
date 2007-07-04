package org.ambiance.nntpclient;
//package org.ambiance.binnntpclient;
//
//import java.util.List;
//
//import junit.framework.TestCase;
//
//import org.ambiance.binnntpclient.command.NntpGroupCommandBody;
//import org.ambiance.binnntpclient.command.NntpGroupCommandHead;
//import org.ambiance.binnntpclient.command.NntpServerCommandList;
//import org.ambiance.binnntpclient.model.Newsgroup;
//import org.ambiance.binnntpclient.model.NntpServer;
//
//public class NntpClientTest extends TestCase {
//
//	NntpConnectionManager ncm = null;
//
//	public NntpClientTest() {
//		super();
//		try {
//			this.ncm = new NntpConnectionManager(new NntpServer("news.free.fr"));
//		} catch (NntpException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void testOK() {
//		Exception e = null;
//
//		try {
//			final NntpClient client = new NntpClient(this.ncm);
//			NntpServerCommandList cmd = new NntpServerCommandList();
//			client.executeCommand(cmd);
//			final List<Newsgroup> groups = (List<Newsgroup>) cmd
//					.getResponseBodyAsObject();
//			cmd.releaseConnection();
//
//			Thread t0 = new Thread() {
//				public void run() {
//					try {
//						NntpServerCommandList cmd = new NntpServerCommandList();
//						client.executeCommand(cmd);
//						final List<Newsgroup> groups = (List<Newsgroup>) cmd
//								.getResponseBodyAsObject();
//						cmd.releaseConnection();
//					} catch (NntpException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			};
//
//			Thread t1 = new Thread() {
//				public void run() {
//
//					NntpGroupCommandBody cmd3;
//					try {
//						NntpServerCommandList cmd = new NntpServerCommandList();
//						client.executeCommand(cmd);
//						final List<Newsgroup> groups = (List<Newsgroup>) cmd
//								.getResponseBodyAsObject();
//						cmd.releaseConnection();
//					} catch (NntpException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				}
//			};
//
//			Thread t2 = new Thread() {
//				public void run() {
//
//					try {
//						NntpServerCommandList cmd = new NntpServerCommandList();
//						client.executeCommand(cmd);
//						final List<Newsgroup> groups = (List<Newsgroup>) cmd
//								.getResponseBodyAsObject();
//						cmd.releaseConnection();
//					} catch (NntpException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			};
//
//			Thread t4 = new Thread() {
//				public void run() {
//					try {
//						NntpServerCommandList cmd = new NntpServerCommandList();
//						client.executeCommand(cmd);
//						final List<Newsgroup> groups = (List<Newsgroup>) cmd
//								.getResponseBodyAsObject();
//						cmd.releaseConnection();
//					} catch (NntpException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			};
//			Thread t3 = new Thread() {
//				public void run() {
//
//					try {
//						NntpServerCommandList cmd = new NntpServerCommandList();
//						client.executeCommand(cmd);
//						final List<Newsgroup> groups = (List<Newsgroup>) cmd
//								.getResponseBodyAsObject();
//						cmd.releaseConnection();
//					} catch (NntpException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				}
//			};
//			t0.start();
//			t1.start();
//			t2.start();
//			t3.start();
//			t4.start();
//
//			t0.join();
//			t1.join();
//			t2.join();
//			t3.join();
//			t4.join();
//		} catch (NntpException e1) {
//			e1.printStackTrace();
//			e = e1;
//		} catch (InterruptedException e2) {
//			e2.printStackTrace();
//			e = e2;
//		} finally {
//			try {
//				ncm.closeAll();
//			} catch (NntpException e1) {
//				e1.printStackTrace();
//			}
//		}
//		assertNull(e);
//	}
//}
