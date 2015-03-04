package ca.etsmtl.log430.lab3.systemA;

import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.ArrayList;

/**
* This class is intended to be a filter that will collect the streams from 
* the input pipes and categorize selected and non selected projects to the 
* output pipes .<br><br>
* 
* Pseudo Code:
* <pre>
* connect to input pipe 1
* connect to input pipe 2
* connect to output pipe 1
* connect to output pipe 2
*
* while not done
*		read char1 from input pipe
*		read char2 from input pipe
*		write string to output pipe 1
*		write string to output pipe 2
* end while
*
* close pipes
* close file
* </pre>
* 
* @version 1.0
*/
public class ProjectDifferentiatorFilter extends Thread {
	
	// Declarations
	boolean done;

	PipedReader allProjectsInputPipe = new PipedReader();
	PipedReader selectedProjectsInputPipe = new PipedReader();
	
	PipedWriter selectedProjectsOutputPipe = new PipedWriter();
	PipedWriter nonSelectedProjectsOutputPipe = new PipedWriter();
	
	public ProjectDifferentiatorFilter(PipedWriter allProjectsInputPipe ,PipedWriter selectedProjectsInputPipe,
			PipedWriter selectedProjectsOutputPipe, PipedWriter nonSelectedProjectsOutputPipe) {

		try {

			// Connect inputPipe
			this.allProjectsInputPipe.connect(allProjectsInputPipe);
			this.selectedProjectsInputPipe.connect(selectedProjectsInputPipe);
			System.out.println("ProjectDifferentiatorFilter :: connected to upstream filter.");

			// Connect outputPipes
			this.selectedProjectsOutputPipe = selectedProjectsOutputPipe;
			this.nonSelectedProjectsOutputPipe = nonSelectedProjectsOutputPipe;
			System.out.println("ProjectDifferentiatorFilter :: connected to downstream filter.");

		} catch (Exception Error) {

			System.err.println("ProjectDifferentiatorFilter :: Error connecting to other filters.");

		} // try/catch

	} // Constructor

	// This is the method that is called when the thread is started
	public void run() {

		// Declarations
		char[] characterValue1 = new char[1];
		char[] characterValue2 = new char[1];
		// char array is required to turn char into a string
		
		ArrayList<String> allProjectList = new ArrayList<String>();
		ArrayList<String> selectedProjectList = new ArrayList<String>();
		ArrayList<String> nonSelectedProjectList = new ArrayList<String>();
		
		String lineOfText1 = "";
		String lineOfText2 = "";
		// string is required to look for the keyword
		int integerCharacter1; // the integer value read from the pipe1
		int integerCharacter2; // the integer value read from the pipe2

		try {

			done = false;

			while (!done) {

				integerCharacter1 = allProjectsInputPipe.read();
				integerCharacter2 = selectedProjectsInputPipe.read();
				
				characterValue1[0] = (char) integerCharacter1;
				characterValue2[0] = (char) integerCharacter2;

				if (integerCharacter1 == -1 && integerCharacter2 == -1) { // pipes are closed

					done = true;

				} else {

					if (integerCharacter1 == '\n') { // end of line

						System.out.println("ProjectDifferentiatorFilter :: received: " + lineOfText1 + ".");
						System.out.println("ProjectDifferentiatorFilter :: sending: "
								+ lineOfText1 + " to output pipe.");
						lineOfText1 += new String(characterValue1);
						allProjectList.add(lineOfText1);
						
						lineOfText1 = "";

					} 
					else {

						lineOfText1 += new String(characterValue1);

					}
					if (integerCharacter2 == '\n') { // end of line

						System.out.println("ProjectDifferentiatorFilter :: received: " + lineOfText2 + ".");
						System.out.println("ProjectDifferentiatorFilter :: sending: "
								+ lineOfText2 + " to output pipe.");
						lineOfText2 += new String(characterValue2);
						selectedProjectList.add(lineOfText2);
						
						lineOfText2 = "";

					} 
					else {

						lineOfText2 += new String(characterValue2);

					}// if //

				} // if

			} // while

		} catch (Exception error) {

			System.err.println("ProjectDifferentiatorFilter:: Interrupted.");

		} // try/catch

		try {

			//Create the list of non selected projects
			for(String project : allProjectList){
				if(selectedProjectList.contains(project) == false){
					nonSelectedProjectList.add(project);
				}
			}
			
			//Write to output pipes
			for(String project : selectedProjectList){
				System.out.println("ProjectFormaterFilter :: sending: "
						+ project + " to selected projects output pipe.");
				selectedProjectsOutputPipe.write(project);
				selectedProjectsOutputPipe.flush();
			}
			for(String project : nonSelectedProjectList){
				System.out.println("ProjectFormaterFilter :: sending: "
						+ project + " to non selected projects output pipe.");
				nonSelectedProjectsOutputPipe.write(project);
				nonSelectedProjectsOutputPipe.flush();
			}
			
			allProjectsInputPipe.close();
			selectedProjectsInputPipe.close();
			System.out.println("ProjectDifferentiatorFilter :: input pipes closed.");

			selectedProjectsOutputPipe.close();
			nonSelectedProjectsOutputPipe.close();
			System.out.println("ProjectDifferentiatorFilter :: output pipes closed.");

		} catch (Exception error) {

			System.err.println("ProjectDifferentiatorFilter :: Error closing pipes.");

		} // try/catch

	} // run
}
