package ca.etsmtl.log430.lab3.systemB;

import java.io.*;

/**
 * This class is intended to be a filter that will collect the streams from 
 * an input pipe and duplicate them separately on the output pipes.<br><br>
 * 
 * Pseudo Code:
 * <pre>
 * connect to input pipe 
 * connect to output pipe 1
 * connect to output pipe 2
 *
 * while not done
 *		read char1 from input pipe
 *		write string to output pipe 1
 *		write string to output pipe 2
 * end while
 *
 * close pipes
 * close file
 * </pre>
 * 
 * @author ak34270
 * @version 1.0
 */
public class DuplicateFilter extends Thread {

	// Declarations

	boolean done;

	PipedReader inputPipe = new PipedReader();
	PipedWriter outputPipe1 = new PipedWriter();
	PipedWriter outputPipe2 = new PipedWriter();

	public DuplicateFilter(PipedWriter inputPipe, PipedWriter outputPipe1, PipedWriter outputPipe2) {

		try {

			// Connect inputPipe
			this.inputPipe.connect(inputPipe);
			System.out.println("DuplicateFilter:: connected to upstream filter.");

			// Connect OutputPipes
			this.outputPipe1 = outputPipe1;
			this.outputPipe2 = outputPipe2;
			System.out.println("DuplicateFilter:: connected to downstream filters.");

		} catch (Exception Error) {

			System.out.println("DuplicateFilter:: Error connecting to other filters.");

		} // try/catch

	} // Constructor

	// This is the method that is called when the thread is started

	public void run() {

		// Declarations

		char[] characterValue = new char[1];
		// char array is required to turn char into a string
		String lineOfText = "";
		// string is required to look for the status code
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

						// Copy to first output pipe
						System.out.println("DuplicateFilter:: received: " + lineOfText + ".");
						System.out.println("DuplicateFilter:: sending: "
								+ lineOfText + " to output pipe 1.");
						lineOfText += new String(characterValue);
						outputPipe1
								.write(lineOfText, 0, lineOfText.length());
						outputPipe1.flush();

						// Copy to second output pipe
						System.out.println("DuplicateFilter:: sending: "
								+ lineOfText + " to output pipe 2.");
						lineOfText += new String(characterValue);
						outputPipe2
								.write(lineOfText, 0, lineOfText.length());
						outputPipe2.flush();

						lineOfText = "";

					} else {

						lineOfText += new String(characterValue);

					} // if //

				} // if

			} // while

		} catch (Exception error) {

			System.out.println("DuplicateFilter:: Interrupted.");

		} // try/catch

		try {

			inputPipe.close();
			System.out.println("DuplicateFilter:: input pipe closed.");

			outputPipe1.close();
			outputPipe2.close();
			System.out.println("DuplicateFilter:: output pipes closed.");

		} catch (Exception Error) {

			System.out.println("DuplicateFilter:: Error closing pipes.");

		} // try/catch

	} // run

} // class