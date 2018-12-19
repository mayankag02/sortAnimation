/************************************************************
 *                                                          *
 *  CSCI 470/502          Assignment 9           Fall 2018  *                                             
 *                                                          *
 *  Programmer: Mayank Agarwal (Z1828333)                   *  
 *                                                          *
 *  Date Due:   12/10/2018                                  *                          
 *                                                          *
 *  Purpose:   This is a SortAnimationApp class for         *
 *  		   Assignment 9                                 *
 *                                                          *
 *  Extra Credit: 1, 2, 4 and 6                             *
 *                                                          *
 *                                                          *
 ***********************************************************/

package assignment9;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class SortAnimationApp extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public SortAnimationApp(){
		
		super("Sorting Animation"); // custom constructor
		setLayout(new GridLayout());
		
		// pair of sortPanel objects as data members
		SortPanel sortPanel1 = new SortPanel();
		SortPanel sortPanel2 = new SortPanel();
		
		// adds two sortPanel objects to the applications layout
		add(sortPanel1);
		add(sortPanel2);
	}
		public static void main(String[] args) {
			
		SortAnimationApp app = new SortAnimationApp();	
		
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		//app.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		app.setVisible(true);
		//app.setResizable(false);
	}
}