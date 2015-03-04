package ca.etsmtl.log430.lab3.systemB;

import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * This class is intended to be a filter that will key on a particular state
 * provided at instantiation. 
 * If this string does not appear in the input stream, the whole line is passed to the
 * output stream.
 * 
 * <pre>
 * Pseudo Code:
 *
 * connect to input pipe
 * connect to output pipe
 *
 * while not end of line
 *
 *		read input pipe
 *
 *		if specified severity does not appear on line of text
 *			write line of text to output pipe
 *			flush pipe
 *		end if
 *
 * end while
 * close pipes
 * </pre>
 *
 * @author ak34270
 * @version 1.0
 */
public class OppositeStateFilter extends Thread {

	// Declarations

	boolean done;

	String severity;
	PipedReader inputPipe = new PipedReader();
	PipedWriter outputPipe = new PipedWriter();

	public OppositeStateFilter(String severity, PipedWriter inputPipe,
			PipedWriter outputPipe) {

		this.severity = severity;

		try {

			// Connect inputPipe
			this.inputPipe.connect(inputPipe);
			System.out.println("OppositeStateFilter " + severity
					+ ":: connected to upstream filter.");

			// Connect outputPipe
			this.outputPipe = outputPipe;
			System.out.println("OppositeStateFilter " + severity
					+ ":: connected to downstream filter.");

		} catch (Exception Error) {

			System.err.println("OppositeStateFilter " + severity
					+ ":: Error connecting to other filters.");

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

						System.out.println("StateFilter " + severity
								+ ":: received: " + lineOfText + ".");

						if (lineOfText.indexOf(severity) == -1) {

							System.out.println("OppositeStateFilter "
									+ severity + ":: sending: "
									+ lineOfText + " to output pipe.");
							lineOfText += new String(characterValue);
							outputPipe
									.write(lineOfText, 0, lineOfText.length());
							outputPipe.flush();

						} // if

						lineOfText = "";

					} else {

						lineOfText += new String(characterValue);

					} // if //

				} // if

			} // while

		} catch (Exception error) {

			System.err.println("OppositeStateFilter::" + severity
					+ " Interrupted.");

		} // try/catch

		try {

			inputPipe.close();
			System.out.println("OppositeStateFilter " + severity
					+ ":: input pipe closed.");

			outputPipe.close();
			System.out.println("OppositeStateFilter " + severity
					+ ":: output pipe closed.");

		} catch (Exception error) {

			System.err.println("OppositeStateFilter " + severity
					+ ":: Error closing pipes.");

		} // try/catch

	} // run

} // class