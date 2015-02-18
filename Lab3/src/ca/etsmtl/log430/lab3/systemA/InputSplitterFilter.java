package ca.etsmtl.log430.lab3.systemA;

import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class InputSplitterFilter extends Thread {

	// Declarations

	boolean done;

	PipedReader inputPipe = new PipedReader();
	PipedWriter outputPipe1 = new PipedWriter();
	PipedWriter outputPipe2 = new PipedWriter();
	
	public InputSplitterFilter(PipedWriter inputPipe,
			PipedWriter outputPipe1, PipedWriter outputPipe2) {


		try {

			// Connect inputPipe
			this.inputPipe.connect(inputPipe);
			System.out.println("InputSplitterFilter :: connected to upstream filter.");

			// Connect outputPipes
			this.outputPipe1 = outputPipe1;
			this.outputPipe2 = outputPipe2;
			System.out.println("InputSplitterFilter :: connected to downstream filter.");

		} catch (Exception Error) {

			System.err.println("InputSplitterFilter :: Error connecting to other filters.");

		} // try/catch

	} // Constructor

	// This is the method that is called when the thread is started
	public void run() {

		// Declarations
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

						System.out.println("InputSplitterFilter :: received: " + lineOfText + ".");

						System.out.println("InputSplitterFilter :: sending: "
								+ lineOfText + " to output pipe.");
						lineOfText += new String(characterValue);
						
						outputPipe1
						.write(lineOfText, 0, lineOfText.length());
						outputPipe2
						.write(lineOfText, 0, lineOfText.length());
						
						outputPipe1.flush();
						outputPipe2.flush();
						
						lineOfText = "";

					} else {

						lineOfText += new String(characterValue);

					} // if //

				} // if

			} // while

		} catch (Exception error) {

			System.err.println("InputSplitterFilter:: Interrupted.");

		} // try/catch

		try {

			
			inputPipe.close();
			System.out.println("InputSplitterFilter :: input pipe closed.");

			outputPipe1.close();
			outputPipe2.close();
			System.out.println("InputSplitterFilter :: output pipes closed.");

		} catch (Exception error) {

			System.err.println("InputSplitterFilter :: Error closing pipes.");

		} // try/catch

	} // run

	
}
