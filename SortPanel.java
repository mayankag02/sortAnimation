/************************************************************
 *                                                          *
 *  CSCI 470/502          Assignment 9           Fall 2018  *                                             
 *                                                          *
 *  Programmer: Mayank Agarwal (Z1828333)                   *  
 *                                                          *
 *  Date Due:   12/10/2018                                  *                          
 *                                                          *
 *  Purpose:   This is a SortPanel class for                *
 *  		   Assignment 9                                 *
 *                                                          *
 *  Extra Credit: 1, 2, 4 and 6                             *
 *                                                          *
 *                                                          *
 ***********************************************************/

package assignment9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SortPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private final JPanel outerPanel;
	private final JPanel drawPanel;
	private final JPanel buttonsPanel;
	private final JPanel buttonsPanelN;
	private final JPanel buttonsPanelS;

	private final JButton populateArrayButton;
	private final JButton sortButton;
	private final JButton pauseSortButton;
	private final JButton stopSortButton;
	private final JButton clearButton;

	private final JTextField elapsedTimeTextField;
	
	private final JComboBox<String> populateArraySelectionComboBox;
	private final JComboBox<String> sortKindComboBox;
	private final JComboBox<String> sortDirectionComboBox;
	private final JComboBox<String> sortSpeedComboBox;

	private static final String[] populateArraySelection = {"Random", "Ascending", "Descending"};
	private static final String[] sortKinds = { "Bubble", "Selection", "Insertion" };
	private static final String[] sortDirection = { "Ascending", "Descending" };
	private static final String[] sortSpeed = { "Fast", "Medium", "Slow" };

	SortAnimationPanel animation = new SortAnimationPanel();

	private int populateArraySelectionIndex;
	private int sortKindSelection;
	private int sortDirectionSelectionIndex;
	private int sortSpeedSelection;
	
	Integer[] array;
	
	public SortPanel() {
		setLayout(new BorderLayout());

		outerPanel = new JPanel(new BorderLayout());
		drawPanel = new JPanel(new BorderLayout()); //very imp
		drawPanel.setBackground(Color.white);
		drawPanel.setOpaque(true); // JPanels are opaque by default

		outerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonsPanel = new JPanel(new BorderLayout()); //again very imp
		buttonsPanelN = new JPanel();
		buttonsPanelS = new JPanel();

		populateArrayButton = new JButton("Populate Array");
		populateArrayButton.setEnabled(true);
		
		sortButton = new JButton("Sort");
		sortButton.setEnabled(false);
		
		pauseSortButton = new JButton("Pause");
		pauseSortButton.setEnabled(false);
		pauseSortButton.setToolTipText("Toggle button");
		
		stopSortButton = new JButton("Stop");
		stopSortButton.setEnabled(false);
		
		clearButton = new JButton("Clear");
		clearButton.setEnabled(false);
		clearButton.setToolTipText("Refresh");
		
		elapsedTimeTextField = new JTextField(6);
		elapsedTimeTextField.setVisible(false);
		elapsedTimeTextField.setToolTipText("Sorting Time");

		populateArraySelectionComboBox = new JComboBox<String>(populateArraySelection);
		populateArraySelectionComboBox.setToolTipText("Populate Array Type");
		
		sortKindComboBox = new JComboBox<String>(sortKinds);
		sortKindComboBox.setEnabled(false);
		sortKindComboBox.setToolTipText("Sorting Type");

		sortDirectionComboBox = new JComboBox<String>(sortDirection);
		sortDirectionComboBox.setEnabled(false);
		sortDirectionComboBox.setToolTipText("Sorting Direction");

		sortSpeedComboBox = new JComboBox<String>(sortSpeed);
		sortSpeedComboBox.setEnabled(false);
		sortSpeedComboBox.setToolTipText("Sorting Speed");

		buttonsPanelN.add(populateArrayButton);
		buttonsPanelN.add(populateArraySelectionComboBox);
		buttonsPanelN.add(sortButton);
		buttonsPanelN.add(sortKindComboBox);
		buttonsPanelN.add(sortDirectionComboBox);
		buttonsPanelN.add(sortSpeedComboBox);
		
		buttonsPanelS.add(pauseSortButton);
		buttonsPanelS.add(stopSortButton);
		buttonsPanelS.add(clearButton);
		buttonsPanelS.add(elapsedTimeTextField);
		
		buttonsPanel.add(buttonsPanelN, BorderLayout.NORTH);
		buttonsPanel.add(buttonsPanelS, BorderLayout.SOUTH);

		outerPanel.add(drawPanel);
		outerPanel.add(buttonsPanel, BorderLayout.SOUTH);

		add(outerPanel);

		populateArraySelectionComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					populateArraySelectionIndex = populateArraySelectionComboBox.getSelectedIndex();
				}
			}
		});
		sortKindComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					sortKindSelection = sortKindComboBox.getSelectedIndex();
				}
			}
		});
		sortDirectionComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					sortDirectionSelectionIndex = sortDirectionComboBox.getSelectedIndex();
				}
			}
		});
		sortSpeedComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					sortSpeedSelection = sortSpeedComboBox.getSelectedIndex();
				}
			}
		});
		ButtonHandler handler = new ButtonHandler();
		populateArrayButton.addActionListener(handler);
		sortButton.addActionListener(handler);
		pauseSortButton.addActionListener(handler);
		stopSortButton.addActionListener(handler);
		clearButton.addActionListener(handler);
}
	
	/**
	 * ButtonHandler to perform the actions on button click
	 * @author Mayank
	 *
	 */
	private class ButtonHandler implements ActionListener {
		
		boolean pauseStatus = true;
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == populateArrayButton) {
				String cmd = event.getActionCommand();
				if (cmd.equalsIgnoreCase("Populate Array")) {
					ArrayGenerator arrayGenerator = new ArrayGenerator();
					array = arrayGenerator.arrayGeneration(populateArraySelectionIndex);
					revalidate();
					repaint();
					drawPanel.add(animation);
					populateArrayButton.setText("Repopulate Array");
					populateArraySelectionComboBox.setEnabled(false);
					sortButton.setEnabled(true);
					changeComboBoxEnableStatus(true);
					clearButton.setEnabled(true);
					elapsedTimeTextField.setVisible(false);
				}
				else {
					populateArrayButton.setText("Populate Array");
					populateArraySelectionComboBox.setEnabled(true);
				}
			}
			if (event.getSource() == sortButton) {
				sortButton.setEnabled(false);
				pauseSortButton.setEnabled(true);
				stopSortButton.setEnabled(true);
				clearButton.setEnabled(false);
				sortKindComboBox.setEnabled(false);
				sortDirectionComboBox.setEnabled(false);
				Thread thread = new Thread(animation);
				thread.start(); //calls run method of SortAnimationPanel class
			} 
			if (event.getSource() == pauseSortButton) {
				String command = event.getActionCommand();
				if (command.equals("Pause")) {
					pauseSortButton.setText("Resume");
					clearButton.setEnabled(true);
					animation.pause();
					pauseStatus = false;
				} else {
					pauseSortButton.setText("Pause");
					clearButton.setEnabled(false);
					animation.resume();
					pauseStatus = true;
				}
			}
			if (event.getSource() == stopSortButton) {
				changeButtonStatus();
				clearButton.setEnabled(true);
				sortSpeedComboBox.setEnabled(false);
				animation.stop();
			}
			if (event.getSource() == clearButton) {
				clear();
				clearButton.setEnabled(false);
				sortButton.setEnabled(false);
				elapsedTimeTextField.setVisible(false);
				changeComboBoxEnableStatus(false);
				changeButtonStatus();
			}
		}
		public void changeButtonStatus() {
			stopSortButton.setEnabled(false);
			pauseSortButton.setEnabled(false);
			if (!pauseStatus) {
				pauseSortButton.setText("Pause");
			}
			populateArrayButton.setText("Populate Array");
			populateArraySelectionComboBox.setEnabled(true);
		}
		public void changeComboBoxEnableStatus(boolean flag) {
			sortKindComboBox.setEnabled(flag);
			sortDirectionComboBox.setEnabled(flag);
			sortSpeedComboBox.setEnabled(flag);
		}
		public void clear() {
			drawPanel.removeAll();
			drawPanel.updateUI();
		}
	}
	
	/**
	 * SortAnimationPanel class to display the visual results of sorting
	 * @author Mayank
	 *
	 */
	public class SortAnimationPanel extends JPanel implements Runnable {
		
		private static final long serialVersionUID = 1L;
		//Integer[] array; // so that the array can be reverse sorted
		Integer arraySize = Constants.PANEL_WIDTH;
		int panelHeight = Constants.PANEL_HEIGHT;
		boolean stopFlag = false;
		boolean pause = false;
		boolean resume = false;
		private final Object pauseLock = new Object();
		
		public void stop(){
			stopFlag = true;
		}
		
		public void pause() {
			pause = true;
		}
		
		public void resume() {
			synchronized (pauseLock) {
				pauseLock.notifyAll();
				pause = false;
				resume = true;
			}
		}
		
		/**
		 * Run method from where sorting begins
		 */
		public void run() {
			System.out.println(Thread.currentThread().getName());
			int sleepTime = sortSpeed();
			sortKindSelection(sleepTime);
		}
		
		private int sortSpeed() {
			if (sortSpeedSelection == Constants.FAST_SPEED_INDEX) {
				return Constants.FAST_SPEED;
			} else if (sortSpeedSelection == Constants.MEDIUM_SPEED_INDEX) {
				return Constants.MEDIUM_SPEED;
			} else{
				return Constants.SLOW_SPEED;
			}
		}

		private void sortKindSelection(int sleepTime) {
			if (sortKindSelection == Constants.BUBBLE_SORT_INDEX) { 
				bubbleSort(sleepTime);
			} else if (sortKindSelection == Constants.SELECTION_SORT_INDEX) {
				selectionSort(sleepTime);
			} else if (sortKindSelection == Constants.INSERTION_SORT_INDEX) {
				insertionSort(sleepTime);
			}
		}
		
		/**
		 * Bubble sort function to sort in ascending or descending order
		 */
		public void bubbleSort(int sleepTime) {
			long start = 0;
			// if user selected ascending order
			if (sortDirectionSelectionIndex == 0) {
				start = System.currentTimeMillis();
				for (int i = 0; i < arraySize - 1; i++) {
					for (int j = 0; j < (arraySize - 1 - i); j++) {
						if (array[j] > array[j + 1]) {
							synchronized (pauseLock) {
								if (!stopFlag) {
									int tmp = array[j + 1];
									array[j + 1] = array[j];
									array[j] = tmp;
									if (pause) {
										try {
											pauseLock.wait();
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
									repaint(); // call repaint() any time two elements are swapped
								} else {
									stopFlag = false;
									return;
								}
							}
						}
					}
					try {
						if (resume) {
							Thread.sleep(sortSpeed());
						} else {
							Thread.sleep(sleepTime); // After each pass the thread sleeps
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			// if user selected descending order
			else {
				start = System.currentTimeMillis();
				for (int i = 0; i < arraySize - 1; i++) {
					for (int j = 0; j < (arraySize - 1 - i); j++) {
						if (array[j] < array[j + 1]) {
							synchronized (pauseLock) {
								if (!stopFlag) {
									int tmp = array[j + 1];
									array[j + 1] = array[j];
									array[j] = tmp;
									if (pause) {
										try {
											pauseLock.wait();
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
									repaint(); // call repaint() any time two elements are swapped
								} else {
									stopFlag = false;
									return;
								}
							}
						}
					}
					try {
						if (resume) {
							Thread.sleep(sortSpeed());
						} else {
							Thread.sleep(sleepTime); // After each pass the thread sleeps
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			long elapsedTime = System.currentTimeMillis() - start;
			elapsedTimeTextField.setVisible(true);
			elapsedTimeTextField.setText(Long.toString(elapsedTime) + " ms");
			elapsedTimeTextField.setEnabled(false);
			buttonStatusAfterSorting();
		}

		/**
		 * Selection sort function to sort in ascending or descending order
		 */
		public void selectionSort(int sleepTime) {
			// if user selected ascending order
			if (sortDirectionSelectionIndex == 0) {
				for (int i = 0; i < array.length - 1; i++) {
					int minIndex = i;
					for (int j = i + 1; j < array.length; j++) {
						if (array[j] < array[minIndex]) {
							minIndex = j;
						}
					}
					if (!stopFlag) {
						int tmp = array[minIndex];
						array[minIndex] = array[i];
						array[i] = tmp;
						repaint(); //call repaint() any time two elements are swapped
					}
					else {
						stopFlag = false;
						return;
					}
					try {
						Thread.sleep(sleepTime); // After each pass the thread sleeps
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			// if user selected descending order
			else{
				for (int i = 0; i < array.length - 1; i++) {
					int minIndex = i;
					for (int j = i + 1; j < array.length; j++) {
						if (array[j] > array[minIndex]) {
							minIndex = j;
						}
					}
					if (!stopFlag) {
						int tmp = array[minIndex];
						array[minIndex] = array[i];
						array[i] = tmp;
						repaint(); //call repaint() any time two elements are swapped
					}
					else {
						stopFlag = false;
						return;
					}
					try {
						Thread.sleep(sleepTime); // After each pass the thread sleeps
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			buttonStatusAfterSorting();
		}

		/**
		 * Insertion sort function to sort in ascending or descending order
		 */
		public void insertionSort(int sleepTime) {

			// if user selected ascending order
			if (sortDirectionSelectionIndex == 0) {
				for (int i = 0; i < array.length; i++) {
					int current = array[i];
					int j = i - 1;

					while (j >= 0 && array[j] > current) {
						array[j + 1] = array[j];
						j = j - 1;
					}
					if (!stopFlag) {
						array[j + 1] = current;
						repaint(); //call repaint() any time two elements are swapped
					}
					else {
						stopFlag = false;
						return;
					}
					try {
						Thread.sleep(sleepTime); // After each pass the thread sleeps
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			// if user selected ascending order
			else {
				for (int i = 0; i < array.length; i++) {
					int current = array[i];
					int j = i - 1;
					while (j >= 0 && array[j] < current) {
						array[j + 1] = array[j];
						j = j - 1;
					}
					if (!stopFlag) {
						array[j + 1] = current;
						repaint(); //call repaint() any time two elements are swapped
					}
					else {
						stopFlag = false;
						return;
					}
					try {
						Thread.sleep(sleepTime); // After each pass the thread sleeps
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			buttonStatusAfterSorting();
		}
		
		public void buttonStatusAfterSorting() {
			populateArrayButton.setText("Populate Array");
			populateArrayButton.setEnabled(true);
			populateArraySelectionComboBox.setEnabled(true);
			sortSpeedComboBox.setEnabled(false);
			pauseSortButton.setEnabled(false);
			stopSortButton.setEnabled(false);
		}
		
		@Override
		public void paintComponent(Graphics g) { // problem: it gets called twice
			super.paintComponent(g);
			this.setBackground(Color.white);//imp
			g.setColor(Color.blue);
				for (int i = 0; i < arraySize; i++) {
					g.drawLine(i, panelHeight, i, panelHeight - array[i]);
				}
			}
		}
	}