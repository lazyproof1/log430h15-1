package ca.etsmtl.log430.lab3.systemA;

import java.io.PipedWriter;

/**
 * This class contains the main method for assignment 3, System A. The program
 * consists of these files:<br><br>
 * 
 * 1) SystemInitialize: instantiates all filters and pipes, starts all filters.<br>
 * 2) FileReaderFilter: reads input file and sends each line to its output pipe.<br>
 * 3) StatusFilter: separates the input stream in two project types (REG, CRI) and writes
 *    lines to the appropriate output pipe.<br>
 * 4) StateFilter: determines if an entry contains a particular state specified
 *    at instantiation. If so, sends the whole line to its output pipe.<br>
 * 5) MergeFilter: accepts inputs from 2 input pipes and writes them to its output pipe.<br>
 * 6) FileWriterFilter: sends its input stream to a text file.<br><br>
 * 
 * Modifications:
 * 7) InputSplitterFilter: read input file and split to the output pipe.<br>
 * 8) ProjecDifferentiatorFilter: read input streams and categorize to specific conditions
 * 	  to the output pipes<br>
 * 9) StateSortFilter: sorts alphabetically the input stream and send it to output pipe.<br>
 * 
 * Pseudo Code:
 * <pre>
 * instantiate all filters and pipes
 * start FileReaderFilter
 * start InputSplitterFilter
 * start StatusFilter
 * start MergeFilter
 * start ProjecDifferentiatorFilter
 * start StateSortFilter
 * start FileWriterFilter
 * </pre>
 * 
 * Running the program:
 * <pre>
 * java SystemInitialize InputFile OutputFile > DebugFile
 * 
 * SystemInitialize - Program name
 * InputFile - Text input file (see comments below)
 * OutputFile - Text output file with students
 * DebugFile - Optional file to direct debug statements
 * </pre>
 */
public class SystemInitialize {

	public static void main(String argv[]) {
		// Let's make sure that input and output files are provided on the
		// command line

		if (argv.length != 3) {

			System.out
					.println("\n\nNombre incorrect de parametres d'entree. Utilisation:");
			System.out
					.println("\njava SystemInitialize <fichier d'entree> <fichier de sortie 1> <fichier de sortie 2>");

		} else {
			
			// These are the declarations for the pipes.
			PipedWriter fileReaderOutPipe 			= new PipedWriter();
			PipedWriter splitterOutPipe1 			= new PipedWriter();
			PipedWriter splitterOutPipe2 			= new PipedWriter();
			PipedWriter statusFilterOutPipe1 		= new PipedWriter();
			PipedWriter statusFilterOutPipe2 		= new PipedWriter();
			PipedWriter stateFilter1OutPipe 		= new PipedWriter();
			PipedWriter stateFilter2OutPipe 		= new PipedWriter();
			PipedWriter mergeFilterOutPipe 			= new PipedWriter();
			PipedWriter projectDifferentiatorFilterOutPipe1 = new PipedWriter();
			PipedWriter projectDifferentiatorFilterOutPipe2 = new PipedWriter();
			PipedWriter stateSortFilterOutPipe1 	= new PipedWriter();
			PipedWriter stateSortFilterOutPipe2 	= new PipedWriter();
			
			// Instantiate Filter Threads
			Thread fileReaderFilter = new FileReaderFilter(argv[0], fileReaderOutPipe);
			
			// Split input files
			Thread inputSplitterFilter = new InputSplitterFilter(fileReaderOutPipe, splitterOutPipe1, splitterOutPipe2);
			
			// Status and state filter
			Thread statusFilter = new StatusFilter(splitterOutPipe1, statusFilterOutPipe1, statusFilterOutPipe2);
			Thread stateFilter1 = new StateFilter("RIS", statusFilterOutPipe1, stateFilter1OutPipe);
			Thread stateFilter2 = new StateFilter("DIF", statusFilterOutPipe2, stateFilter2OutPipe);
			
			// Merge
			Thread mergeFilter = new MergeFilter(stateFilter1OutPipe, stateFilter2OutPipe, mergeFilterOutPipe);
			
			// Categorize selected and unselected projects
			Thread projectDifferentiatorFilter = new ProjectDifferentiatorFilter(splitterOutPipe2, mergeFilterOutPipe, 
					projectDifferentiatorFilterOutPipe1, projectDifferentiatorFilterOutPipe2);
			
			// Sort and write both output pipes
			Thread stateSortFilter1 = new StateSortFilter(projectDifferentiatorFilterOutPipe1, stateSortFilterOutPipe1);
			Thread stateSortFilter2 = new StateSortFilter(projectDifferentiatorFilterOutPipe2, stateSortFilterOutPipe2);
			
			Thread fileWriterFilter1 = new FileWriterFilter(argv[1], stateSortFilterOutPipe1); // Unselected
			Thread fileWriterFilter2 = new FileWriterFilter(argv[2], stateSortFilterOutPipe2); // Selected

			// Start the threads
			fileReaderFilter.start();
			inputSplitterFilter.start();
			statusFilter.start();
			stateFilter1.start();
			stateFilter2.start();
			mergeFilter.start();
			projectDifferentiatorFilter.start();
			stateSortFilter1.start();
			stateSortFilter2.start();
			fileWriterFilter1.start();
			fileWriterFilter2.start();
			
		}  // if
		
	} // main
	
} // SystemInitialize