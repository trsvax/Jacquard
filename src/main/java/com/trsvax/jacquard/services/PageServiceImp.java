package com.trsvax.jacquard.services;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import com.trsvax.jacquard.Page;

@EagerLoad
public class PageServiceImp implements PageService {
	
	   private final AtomicInteger pageSequence = new AtomicInteger(1);
	   private static final int MAX_ENTRIES = 100;
	   private final Integer port = 4712;   
	   private final PatternLayout layout;	   
	   private final String pattern = "%d{dd MMM yyyy HH:mm:ss,SSS} [%p] %X{page} %c{2} %m%n";
	   private final Request request;
	   
	   ServerSocket serverSocket;
	   
	   
	   @SuppressWarnings("serial")
	private final Map<String,Page> pages = Collections.synchronizedMap(new LinkedHashMap<String,Page>(MAX_ENTRIES) {

		     @SuppressWarnings("rawtypes")
			protected boolean removeEldestEntry(Map.Entry eldest) {
		    	 System.out.println("size " + size());
		        return size() >= MAX_ENTRIES;
		     }
	   });
	   
	   
	   public PageServiceImp(Request request) throws IOException {
		   layout = new PatternLayout(pattern);
		   this.request = request;
		   new Thread( new LogServer() ).start();
	   }
	   
	   public Page newPage(Request request) {
		   Integer sequence = pageSequence.getAndIncrement();
		   Page page =  new Page(sequence,request.getPath());
		   pages.put(sequence.toString(), page);
		   return page;
	   }
	   
	   public Collection<Page> getPages() {
		   return pages.values();
	   }

	   
	   public class LogServer implements Runnable {
		   
			@Override
			public void run() {
				try ( ServerSocket serverSocket = new ServerSocket(port) ) {
					while (true) {
						try ( Socket socket = serverSocket.accept(); 
								ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))
							) {
							
							while (true) {
								LoggingEvent event = (LoggingEvent) ois.readObject();
								String page = (String) event.getMDC("page");
								if ( pages.containsKey(page)) {
									pages.get(page).addLog(event);
								}
							}						
						} catch (IOException | ClassNotFoundException e1) {
							// don't care
						} 	
					}
				} catch (IOException e) {
					// don't care
				}
			}		   
	   }


	@Override
	public Page getPage(String pageSequence) {
		return pages.get(pageSequence);
	}
	
	Page currentPage() {
		return (Page) request.getAttribute("Page");
	}

	@Override
	public StreamResponse runJob(Runnable job) {
		return new StreamResponse() {
			
			byte[] bytes;
			
			@Override
			public void prepareResponse(Response response) {
				StringBuffer buffer = new StringBuffer();
				try {
					job.run();
					response.setStatus(HttpServletResponse.SC_OK);
					buffer.append("OK\n");
				} catch (Exception e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					buffer.append(e.getMessage());
					buffer.append("\n");
				}
				try {
					Thread.sleep(1000); // wait for logs
				} catch (InterruptedException e) {
				}
				
				Page page = currentPage();
				for ( LoggingEvent event : page.getLogs()) {
					buffer.append( layout.format(event));
				}
				bytes = buffer.toString().getBytes();
				response.setContentLength(bytes.length);
			}
			
			@Override
			public InputStream getStream() throws IOException {
				return new ByteArrayInputStream(bytes);
			}
			
			@Override
			public String getContentType() {
				return "text/plain";
			}
		};
	}



	
}
