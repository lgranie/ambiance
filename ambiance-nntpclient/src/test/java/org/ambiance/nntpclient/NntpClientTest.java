/**
 * Copyright (C) 2007 Laurent GRANIE.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
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
