package ca.etsmtl.log430.lab1.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.etsmtl.log430.lab1.donnees.Project;
import ca.etsmtl.log430.lab1.donnees.ProjectList;
import ca.etsmtl.log430.lab1.donnees.Resource;
import ca.etsmtl.log430.lab1.gestion.ProjectReader;
import ca.etsmtl.log430.lab1.gestion.ResourceReader;

/***************************************************************************
 * Main testing class for assignment 1, LOG430 
 * 
 * All test units will need the following files:
 * +	projects.txt
 * + 	resources.txt
 * 
 * ===========================================================================
 * Test 1
 * Description: 	Test the modification #1 -- The list of projects assigned 
 * 					to a resource before the execution of the system.
 * Procedure: 		Get previously assigned project list and compare them with
 * 					currently known resources.
 * ===========================================================================
 * Test 2
 * Description: 	Test the  modification #2 -- The list of roles assigned to
 * 					a project before and after the execution of the system.
 * Procedure: 		
 * ===========================================================================
 * Test 3
 * Description: 	Test the  modification #3 -- Verify resource overload
 * 					depending on the project's resource value and the dates.
 * Procedure: 		
 ***************************************************************************/
public class testMain {
	
	ProjectReader projectListTest;
	ResourceReader resourceListTest;

	Resource resourceTest;
	ProjectList prevResource;
	
	/***************************************************************************
	 * Load the following files before each test
	 **************************************************************************/
	@Before
	public void loadData(){
		projectListTest = new ProjectReader("projects.txt");
		resourceListTest = new ResourceReader("resources.txt");
	}

	/***************************************************************************
	 * Test modification #1 R001 - Previously assigned project list
	 **************************************************************************/
	@Test
	public void mod1R001Test() {
		
		// Select resource R001
		resourceTest = resourceListTest.getListOfResources().findResourceByID("R001");
		
		prevResource = resourceTest.getPreviouslyAssignedProjectList();
		prevResource.goToFrontOfList();

		assertTrue(prevResource.getNextProject().getID().equals("P001"));
		assertTrue(prevResource.getNextProject().getID().equals("P003"));
		assertTrue(prevResource.getNextProject().getID().equals(""));
		
	}

	/***************************************************************************
	 * Test modification #1 R003 - Previously assigned project list
	 **************************************************************************/
	@Test
	public void mod1R003Test() {

		// Select resource R003
		resourceTest = resourceListTest.getListOfResources().findResourceByID("R003");
		
		prevResource = resourceTest.getPreviouslyAssignedProjectList();
		prevResource.goToFrontOfList();

		assertTrue(prevResource.getNextProject().getID().equals("P004"));
		//assertTrue(prevResource.getNextProject().getID().equals(""));
		
	}

	/***************************************************************************
	 * Test modification #1 Unknown value - Previously assigned project list
	 **************************************************************************/
	@Test
	public void mod1R00XTest() {

			resourceTest = resourceListTest.getListOfResources().findResourceByID("R912");
			assertNull(resourceTest);
		
	}

	/***************************************************************************
	 * Test modification #2 R002 - Previously assigned project list
	 **************************************************************************/
	@Test
	public void testMod2(){
		
	}

}
