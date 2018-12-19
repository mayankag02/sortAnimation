package assignment9;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;

public class ArrayGenerator {
	
	Integer arraySize = Constants.PANEL_WIDTH; //drawPanel.getWidth(); //so that the array can be reverse sorted
	Integer[] array = new Integer[arraySize];
	
		public Integer[] arrayGeneration (int populateArraySelectionIndex) {
			
			SecureRandom generator = new SecureRandom(); // to get new set of randomNumbers whenever PopulateArray button is clicked
			
			for (int i = 0; i < arraySize; i++) {
				int randomNumber = generator.nextInt(Constants.PANEL_HEIGHT); // drawPanel.panelHeight
				array[i] = randomNumber;
			}
			if (populateArraySelectionIndex == 1) { // ascending order
				Arrays.sort(array);
			} else if (populateArraySelectionIndex == 2) { // descending order
				Arrays.sort(array, Collections.reverseOrder());
			}
			return array;
		}
}