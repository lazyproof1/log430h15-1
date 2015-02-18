package ca.etsmtl.log430.lab3.systemA;

import java.io.PipedWriter;

/**
 * System A
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
			PipedWriter fileReaderOutPipe = new PipedWriter();
			
			PipedWriter splitterOutPipe1 = new PipedWriter();
			PipedWriter splitterOutPipe2 = new PipedWriter();
			
			PipedWriter statusFilterOutPipe1 = new PipedWriter();
			PipedWriter statusFilterOutPipe2 = new PipedWriter();
			
			PipedWriter stateFilter1OutPipe = new PipedWriter();
			PipedWriter stateFilter2OutPipe = new PipedWriter();
			
			PipedWriter mergeFilterOutPipe = new PipedWriter();
			
			PipedWriter projectDifferentiatorFilterOutPipe1 = new PipedWriter();
			PipedWriter projectDifferentiatorFilterOutPipe2 = new PipedWriter();
			
			PipedWriter stateSortFilterOutPipe1 = new PipedWriter();
			PipedWriter stateSortFilterOutPipe2 = new PipedWriter();
			
			
			// Instantiate Filter Threads
			Thread fileReaderFilter = new FileReaderFilter(argv[0], fileReaderOutPipe);
			
			Thread inputSplitterFilter = new InputSplitterFilter(fileReaderOutPipe, splitterOutPipe1, splitterOutPipe2);
			
			Thread statusFilter = new StatusFilter(splitterOutPipe1, statusFilterOutPipe1, statusFilterOutPipe2);
			
			Thread stateFilter1 = new StateFilter("RIS", statusFilterOutPipe1, stateFilter1OutPipe);
			Thread stateFilter2 = new StateFilter("DIF", statusFilterOutPipe2, stateFilter2OutPipe);
			
			Thread mergeFilter = new MergeFilter(stateFilter1OutPipe, stateFilter2OutPipe, mergeFilterOutPipe);
			
			Thread projectDifferentiatorFilter = new ProjectDifferentiatorFilter(splitterOutPipe2, mergeFilterOutPipe, 
					projectDifferentiatorFilterOutPipe1, projectDifferentiatorFilterOutPipe2);
			
			Thread stateSortFilter1 = new StateSortFilter(projectDifferentiatorFilterOutPipe1, stateSortFilterOutPipe1);
			Thread stateSortFilter2 = new StateSortFilter(projectDifferentiatorFilterOutPipe2, stateSortFilterOutPipe2);
			
			Thread fileWriterFilter1 = new FileWriterFilter(argv[1], stateSortFilterOutPipe1);
			Thread fileWriterFilter2 = new FileWriterFilter(argv[2], stateSortFilterOutPipe2);

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