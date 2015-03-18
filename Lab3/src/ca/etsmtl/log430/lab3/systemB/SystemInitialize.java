package ca.etsmtl.log430.lab3.systemB;

import java.io.PipedWriter;

/**
 * This class contains the main method for assignment 3, System B. The program
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
 * 7) DuplicateFilter: read input file and duplicate it to the output pipe.<br>
 * 8) OppositeStateFilter: same purpose as StateFilter however, does the opposite based 
 *    entry.<br>
 * 9) ProgressFilter: check the progress status of the input stream and send to output pipe
 * 	  if entry condition is true.<br>
 * 10) StateSortFilter: sorts alphabetically the input stream and send it to output pipe.<br>
 * 
 * Pseudo Code:
 * <pre>
 * instantiate all filters and pipes
 * start FileReaderFilter
 * start StatusFilter
 * start DuplicateFilter
 * start ProgressFilter for REG
 * start OppositeStateFilter for PRO
 * start ProgressFilter for CRI
 * start StateFilter for RIS
 * start OppositeStateFilter for RIS
 * start MergeFilter twice
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
			PipedWriter pipeRead 			= new PipedWriter();
			PipedWriter pipeReg 			= new PipedWriter();
			PipedWriter pipeRegNotPro 		= new PipedWriter();
			PipedWriter pipeRegNotProEnd 	= new PipedWriter();
			PipedWriter pipeCri 			= new PipedWriter();
			PipedWriter pipeCriCopy1		= new PipedWriter();
			PipedWriter pipeCriCopy2		= new PipedWriter();
			PipedWriter pipeCriRis 			= new PipedWriter();
			PipedWriter pipeCriNotRis 		= new PipedWriter();
			PipedWriter pipeCriRisEnd		= new PipedWriter();
			PipedWriter pipeCriNotRisEnd 	= new PipedWriter();
			PipedWriter pipeCriMerged 		= new PipedWriter();
			PipedWriter pipeRegSort 		= new PipedWriter();
			PipedWriter pipeCriSort 		= new PipedWriter();
			
			// Instantiate Filter Threads
			Thread fileReaderFilter 		= new FileReaderFilter(argv[0], pipeRead);
			Thread statusFilter 			= new StatusFilter(pipeRead, pipeReg, pipeCri);
			
			// Since CRI needs to be verified twice on two different conditions
			Thread duplicateFilter 			= new DuplicateFilter(pipeCri, pipeCriCopy1, pipeCriCopy2);
			
			// REG - notPRO
			Thread progressFilter1			= new ProgressFilter("<", 50, pipeReg, pipeRegNotPro);
			Thread stateFilter1 			= new OppositeStateFilter("PRO", pipeRegNotPro, pipeRegNotProEnd);

			// CRI - RIS
			Thread progressFilter2			= new ProgressFilter("==", 25, pipeCriCopy1, pipeCriRis);
			Thread stateFilter2 			= new StateFilter("RIS", pipeCriRis, pipeCriRisEnd);

			// CRI - notRIS
			Thread progressFilter3 			= new ProgressFilter(">", 75, pipeCriCopy2, pipeCriNotRis);
			Thread stateFilter3 			= new OppositeStateFilter("RIS", pipeCriNotRis, pipeCriNotRisEnd);

			// Merge CRI
			Thread mergeFilter 				= new MergeFilter(pipeCriNotRisEnd, pipeCriRisEnd, pipeCriMerged);
			
			// Sort and write
			Thread stateSortFilter1 		= new StateSortFilter(pipeRegNotProEnd, pipeRegSort);
			Thread stateSortFilter2 		= new StateSortFilter(pipeCriMerged, pipeCriSort);
			
			Thread fileWriterFilter1 		= new FileWriterFilter(argv[1], pipeRegSort);
			Thread fileWriterFilter2 		= new FileWriterFilter(argv[2], pipeCriSort);
			
			// Start the threads
			fileReaderFilter.start();
			statusFilter.start();
			duplicateFilter.start();
			progressFilter1.start();
			stateFilter1.start();
			progressFilter2.start();
			stateFilter2.start();
			progressFilter3.start();
			stateFilter3.start();
			mergeFilter.start();
			stateSortFilter1.start();
			stateSortFilter2.start();
			fileWriterFilter1.start();
			fileWriterFilter2.start();
			
		}  // if
		
	} // main
	
} // SystemInitialize