package ca.etsmtl.log430.lab3.systemB;

import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * This class is intended to be a filter that will key on a particular state
 * provided at instantiation.  Note that the stream has to be buffered so that
 * it can be checked to see if the specified severity appears on the stream.
 * If this string appears in the input stream, teh whole line is passed to the
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
 *		if specified severity appears on line of text
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

public class ProgressREGFilter extends Thread {

	// Declarations

	boolean done;

	PipedReader inputPipe = new PipedReader();
	PipedWriter outputPipe = new PipedWriter();

	public ProgressREGFilter( PipedWriter inputPipe,
			PipedWriter outputPipe) {

		try {

			// Connect inputPipe
			this.inputPipe.connect(inputPipe);
			System.out.println("ProgressREGFilter :: connected to upstream filter.");

			// Connect outputPipe
			this.outputPipe = outputPipe;
			System.out.println("ProgressREGFilter :: connected to downstream filter.");

		} catch (Exception Error) {

			System.out.println("ProgressREGFilter :: Error connecting to other filters.");

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
		int progressValue = 0;
		boolean toFilter = false;

		try {

			done = false;

			while (!done) {

				integerCharacter = inputPipe.read();
				characterValue[0] = (char) integerCharacter;

				if (integerCharacter == -1) { // pipe is closed

					done = true;

				} else {

					if (integerCharacter == '\n') { // end of line

						System.out.println("ProgressREGFilter:: received: " 
								+ lineOfText + ".");

						// Set variable to initial value
						progressValue = 0;
						toFilter = true;

						// Check the progress value
						try
						{
							progressValue = Integer.parseInt(lineOfText.substring(22, 24));
						} 
						catch (Exception e) 
						{
							System.out.println("Error, value not found");
						}

						// Check if severity is RIS and progress value < 50
						if ( lineOfText.indexOf(" PRO ") == -1  && progressValue < 50 ) 
						{
							toFilter = false;
						} 
						
						// If the form is correct, don't filter
						if(!toFilter)
						{
							System.out.println("ProgressREGFilter:: sending: "
									+ lineOfText + " to output pipe.");
							lineOfText += new String(characterValue);
							outputPipe
									.write(lineOfText, 0, lineOfText.length());
							outputPipe.flush();
						}

						lineOfText = "";

					} else {

						lineOfText += new String(characterValue);

					} // if //

				} // if

			} // while

		} catch (Exception error) {

			System.out.println("ProgressREGFilter:: Interrupted.");

		} // try/catch

		try {

			inputPipe.close();
			System.out.println("ProgressREGFilter:: input pipe closed.");

			outputPipe.close();
			System.out.println("ProgressREGFilter:: output pipe closed.");

		} catch (Exception error) {

			System.out.println("ProgressREGFilter:: Error closing pipes.");

		} // try/catch

	} // run

} // class