package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JPanel;

import data.DataListener;
import io.SocketServer;
 
public class ServerUIMain extends JPanel implements DataListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<BufferedImage> mQueue = new LinkedList<BufferedImage>();
	private static final int MAX_BUFFER = 15;
           
    BufferedImage mImage, mLastFrame;
 
    @Override
    public void paint(Graphics g) {
        synchronized (mQueue) {
        	if (mQueue.size() > 0) {
        		mLastFrame = mQueue.poll();
        	}	
        }
        if (mLastFrame != null) {
        	g.drawImage(mLastFrame, 0, 0, null);
        }
        else if (mImage != null) {
            g.drawImage(mImage, 0, 0, null);
        }
    }
 
    public ServerUIMain(String username, String password, String ip, int port) {
    	SocketServer server = new SocketServer(username,password,ip,port);
        server.setOnDataListener(this);
        server.start();
    }
    
    private void updateUI(BufferedImage bufferedImage) {

        synchronized (mQueue) {
        	if (mQueue.size() ==  MAX_BUFFER) {
        		mLastFrame = mQueue.poll();
        	}	
        	mQueue.add(bufferedImage);
        }
   
        repaint();
    }
    
    public void closeUI() {
    	
    }
 
    @Override
    public Dimension getPreferredSize() {
        if (mImage == null) {
             return new Dimension(640,480); // init window size
        } else {
           return new Dimension(mImage.getWidth(null), mImage.getHeight(null));
       }
    }

	@Override
	public void onDirty(BufferedImage bufferedImage) {
		// TODO Auto-generated method stub
		updateUI(bufferedImage);
	}
}
