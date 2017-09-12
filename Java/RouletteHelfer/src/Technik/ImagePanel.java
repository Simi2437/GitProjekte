package Technik;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;

public class ImagePanel extends Panel {
	   private Image image;
	   private boolean changed = false ; 
	   
	   
	   public ImagePanel(Image image , Rectangle Size) {
	      this.image = image;
	      this.setSize(Size.getSize());
	   }
	   public ImagePanel(Image image)
	   {
		   this.image = image; 
	   }
	 
	   public void setImage(Image image) {
		   
	      this.image = image;
//			System.out.println("test 12");
//	      this.changed = true ; 
//	      this.setSize(getPreferredSize());
	      repaint();
	   }
	   public boolean hasChanged()
	   {
		   return this.changed ; 
	   }
	   public Dimension getPreferredSize() {
	       if(image != null )
	       {
	    	   return new Dimension(image.getWidth(this), image.getHeight(this));
	       }
	       else return this.getSize() ; 
	   }
	   public void paint(Graphics g) {
	      super.paint(g);
	      if(image != null) {
	    	  if(!hasChanged())
	    	  {
	    	  this.setSize(this.getPreferredSize()) ; 
	    	  this.changed = true ; 
	    	  }
	    	  g.drawImage(image, 0, 0, this);
	      }
//	      g.setColor(Color.GREEN);
//    	  System.out.println("test ImageZeichen");
	   }
	}