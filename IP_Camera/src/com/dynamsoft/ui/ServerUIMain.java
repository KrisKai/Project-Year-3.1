package com.dynamsoft.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JPanel;

import data.DataListener;
import com.dynamsoft.io.SocketServer;
 
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
 
    public ServerUIMain(String username, String password) {
    	SocketServer server = new SocketServer(username,password);
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
 
    @Override
    public Dimension getPreferredSize() {
        if (mImage == null) {
             return new Dimension(960,720); // init window size
        } else {
           return new Dimension(mImage.getWidth(null), mImage.getHeight(null));
       }
    }
 
//    public static void main(String[] args) {
// 
//        JFrame f = new JFrame("Monitor");
//             
//        f.addWindowListener(new WindowAdapter(){
//                @Override
//                public void windowClosing(WindowEvent e) {
//                    System.exit(0);
//                }
//            });
// 
//        f.add(new ServerUIMain());
//        f.pack();
//        f.setVisible(true);
//    }

	@Override
	public void onDirty(BufferedImage bufferedImage) {
		// TODO Auto-generated method stub
		updateUI(bufferedImage);
	}
}
