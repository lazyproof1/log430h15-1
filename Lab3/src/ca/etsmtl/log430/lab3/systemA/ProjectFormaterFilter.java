package ca.etsmtl.log430.lab3.systemA;

import java.io.PipedReader;
import java.io.PipedWriter;

public class ProjectFormaterFilter extends Thread {

	// Declarations

	boolean done;

	PipedReader inputPipe = new PipedReader();
	PipedWriter outputPipe = new PipedWriter();
	
	public ProjectFormaterFilter(PipedWriter inputPipe,
			PipedWriter outputPipe) {


		try {

			// Connect inputPipe
			this.inputPipe.connect(inputPipe);
			System.out.println("ProjectFormaterFilter :: connected to upstream filter.");

			// Connect outputPipe
			this.outputPipe = outputPipe;
			System.out.println("ProjectFormaterFilter :: connected to downstream filter.");

		} catch (Exception Error) {

			System.out.println("ProjectFormaterFilter :: Error connecting to other filters.");

		} // try/catch

	} // Constructor

	// This is the method that is called when the thread is started
	public void run() {

		// Declarations
		char[] characterValue = new char[1];
		// char array is required to turn char into a string
		String lineOfText = "";
		String formatedProject = "";
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

						System.out.println("ProjectFormaterFilter :: received: " + lineOfText + ".");

						System.out.println("ProjectFormaterFilter :: sending: "
								+ lineOfText + " to output pipe.");
						lineOfText += new String(characterValue);
						
						formatedProject = formatProject(lineOfText);
						outputPipe
						.write(formatedProject, 0, lineOfText.length());
						
						outputPipe.flush();
						
						formatedProject = "";
						lineOfText = "";

					} else {

						lineOfText += new String(characterValue);

					} // if //

				} // if

			} // while

		} catch (Exception error) {

			System.out.println("ProjectFormaterFilter:: Interrupted.");

		} // try/catch

		try {

			
			inputPipe.close();
			System.out.println("ProjectFormaterFilter :: input pipe closed.");

			outputPipe.close();

			System.out.println("ProjectFormaterFilter :: output pipe closed.");

		} catch (Exception error) {

			System.out.println("ProjectFormaterFilter :: Error closing pipes.");

		} // try/catch

	} // run

	  private String formatProject(String project) {
	        String[] arr = project.split(" ");
	        String no = arr[0];
	        String statut = arr[1];
	        String taux = arr[4];
	        String etat = arr[5];
	        return String.format(statut+" "+etat+" "+taux+" "+no); 
	    }
	
}
