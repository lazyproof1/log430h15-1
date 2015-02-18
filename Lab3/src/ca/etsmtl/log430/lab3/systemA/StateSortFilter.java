package ca.etsmtl.log430.lab3.systemA;

import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class StateSortFilter extends Thread{

		// Declarations

		boolean done;

		PipedReader inputPipe = new PipedReader();
		PipedWriter outputPipe = new PipedWriter();

		public StateSortFilter(PipedWriter inputPipe,
				PipedWriter outputPipe) {


			try {

				// Connect inputPipe
				this.inputPipe.connect(inputPipe);
				System.out.println("StateSortFilter :: connected to upstream filter.");

				// Connect outputPipe
				this.outputPipe = outputPipe;
				System.out.println("StateSortFilter :: connected to downstream filter.");

			} catch (Exception Error) {

				System.err.println("StateSortFilter :: Error connecting to other filters.");

			} // try/catch

		} // Constructor

		// This is the method that is called when the thread is started
		public void run() {

			// Declarations
			ArrayList<String> projects = new ArrayList<String>();
			//ArrayList to sort the projects
			char[] characterValue = new char[1];
			// char array is required to turn char into a string
			String lineOfText = "";
			// string is required to look for the keyword
			int integerCharacter; // the integer value read from the pipe

			try {

				done = false;

				while (!done) {

					integerCharacter = inputPipe.read();
					characterValue[0] = (char) integerCharacter;

					if (integerCharacter == -1) { // pipe is closed

						done = true;

					} else {

						if (integerCharacter == '\n') { // end of line

							System.out.println("StateSortFilter :: received: " + lineOfText + ".");
							
							lineOfText += new String(characterValue);
							
							//Add the project to the list
							projects.add(lineOfText);
							
							lineOfText = "";

						} else {

							lineOfText += new String(characterValue);

						} // if //

					} // if

				} // while

			} catch (Exception error) {

				System.err.println("StateSortFilter:: Interrupted.");

			} // try/catch

			try {

				//Sort the List
				Collections.sort(projects, new Comparator<String>() {
		            @Override
		            public int compare(String project1, String project2) {
		            	//state is the 5th element in the project line
		                return project1.split(" ")[5].compareTo(project2.split(" ")[5]);
		            }
		        });
				
				for(String project : projects){
					System.out.println("StateSortFilter :: sending: "
							+ project + " to output pipe.");
					outputPipe.write(project);
					outputPipe.flush();
				}
				
				inputPipe.close();
				System.out.println("StateSortFilter :: input pipe closed.");

				outputPipe.close();
				System.out.println("StateSortFilter :: output pipe closed.");

			} catch (Exception error) {

				System.err.println("StateSortFilter :: Error closing pipes.");

			} // try/catch

		} // run

	 
	
}
