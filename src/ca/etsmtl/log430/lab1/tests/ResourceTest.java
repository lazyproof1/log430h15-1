package ca.etsmtl.log430.lab1.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.etsmtl.log430.lab1.donnees.Project;
import ca.etsmtl.log430.lab1.donnees.ProjectList;
import ca.etsmtl.log430.lab1.donnees.Resource;
import ca.etsmtl.log430.lab1.donnees.ResourceList;
import ca.etsmtl.log430.lab1.gestion.ProjectReader;
import ca.etsmtl.log430.lab1.gestion.ResourceReader;

import java.util.Random;

/****************************************************************************
 * This class groups all unit test on Resource.java. Since it is part of
 * the data layer, a significative amount of time were spent on creating
 * test cases.
 * 
 * Each test case will use the following format:
 * =========================================================================
 * 	Test case #		Number of the current test case
 * 	Description: 	Describe the test case without going too far in the details.
 * 	Procedure:		Step by step explanation on how to achieve the results.
 * 	Data in:		List of files that will be used on the test case
 * 	Data out:		Modification brought on the used files.
 *	Pass/fail:		Determine if the test case is a PASS or FAIL.
 * =========================================================================
 * 
 ****************************************************************************/
public class ResourceTest {

	ProjectReader 	projectReadTest;
	ProjectList 	projectListTest;
	Project 		testProject;
	
	ResourceReader 	resourceReadTest;
	ResourceList 	resourceListTest;
	Resource 		testResource;
	
	/***************************************************************************
	 * Load the following files before each test
	 **************************************************************************/
	@Before
	public void loadData(){
		
		// Read files in build path
		projectReadTest = new ProjectReader("projects.txt");
		resourceReadTest = new ResourceReader("resources.txt");

		// Assign data to respective list
		projectListTest = projectReadTest.getListOfProjects();
		resourceListTest = resourceReadTest.getListOfResources();
		
		// Start with the first item of the list
		projectListTest.goToFrontOfList();
		resourceListTest.goToFrontOfList();
		
	} // loadData
	
	/****************************************************************************
	 * Test the AssignProject() method within the data layer.
	 * =========================================================================
	 * Test case 1
	 * Description: 	Assign a high priority project to all known resources
	 * 					and verify that adding another project returns an error
	 * 					due to resource's project overloading.
	 * Procedure:		1) 	Assign project P001 to all resources
	 * 					2) 	Try adding a project that overlaps the first project
	 * Data in:			+ 	projects.txt
	 * 					+ 	resources.txt
	 * Data out:		All resources are assigned to P001.
	 * Pass/fail:		PASS
	 * =========================================================================
	 * @throws Exception
	 ****************************************************************************/
	@Test
	public void test1AssignProject() throws Exception {

		do{

			// Get the first resource in the list
			testResource = resourceListTest.getNextResource();
			
			if(testResource != null){
				
				// Add project P001 to every resources
				testResource.assignProject(projectListTest.findProjectByID("P001"));

				// Adding project P002 will return an exception
				try{
					testResource.assignProject(projectListTest.findProjectByID("P002"));
				} catch (Exception e) {
					System.out.println("Test case 1 - " + testResource.getID() + ": " + e.toString());
				}
				
				// Verify that the project P002 has not been added to the resource's project list
				assertNull(testResource.getProjectsAssigned().findProjectByID("P002"));
				
			} // if

		} while (testResource != null);
		
	} // test1AssignProject

	/****************************************************************************
	 * Test the AssignProject() method within the data layer.
	 * =========================================================================
	 * Test case 2
	 * Description: 	
	 * Procedure:		1) 	
	 * Data in:			+ 	projects.txt
	 * 					+ 	resources.txt
	 * Data out:		All resources are assigned to P001 and P003.
	 * Pass/fail:		PASS
	 * =========================================================================
	 * @throws Exception
	 ****************************************************************************/
	@Test
	public void test2AssignProject() throws Exception {

		do{

			// Get the first resource in the list
			testResource = resourceListTest.getNextResource();
			
			if(testResource != null){
				
				// Add project P001 to every resources
				testResource.assignProject(projectListTest.findProjectByID("P001"));
				
				for (int i = 0; i < 4; i ++){
					testResource.assignProject(projectListTest.findProjectByID("P003"));
				}
			
				// Verify that the project P003 has been added to the resource's project list
				assertNotNull(testResource.getProjectsAssigned().findProjectByID("P003"));
				
				// Adding a fifth project P003 will return an exception
				try{
					testResource.assignProject(projectListTest.findProjectByID("P003"));
				} catch (Exception e) {
					System.out.println("Test case 2 - " + testResource.getID() + ": " + e.toString());
				}
				
			} // if

		} while (testResource != null);
		
	} // test2AssignProject

	/****************************************************************************
	 * Test the AssignProject() method within the data layer.
	 * =========================================================================
	 * Test case 3
	 * Description: 	
	 * Procedure:		1) 	
	 * Data in:			+ 	projects.txt
	 * 					+ 	resources.txt
	 * Data out:		All resources are assigned to P001 and P003.
	 * Pass/fail:		PASS
	 * =========================================================================
	 * @throws Exception
	 ****************************************************************************/
	@Test
	public void test3AssignProject() throws Exception {

		do{

			// Get the first resource in the list
			testResource = resourceListTest.getNextResource();
			
			if(testResource != null){
				
				// Add project P001 to every resources
				testResource.assignProject(projectListTest.findProjectByID("P002"));
				testResource.assignProject(projectListTest.findProjectByID("P002"));
				
				// Adding a project P002 will return an exception
				try{
					testResource.assignProject(projectListTest.findProjectByID("P001"));
				} catch (Exception e) {
					System.out.println("Test case 3 - " + testResource.getID() + ": " + e.toString());
				}
			}

		} while (testResource != null);
		
	} // test3AssignProject
	
	/****************************************************************************
	 * Test the MapPriority() method within the data layer.
	 * =========================================================================
	 * Test case 1
	 * Description: 	Verify all characters that are being sent to the method.
	 * Procedure:		1) 	Check if known characters returns corresponding value
	 * 					2) 	Check if unknown values always returns 0 
	 * Data in:			+ 	resources.txt
	 * Data out:		None
	 * Pass/fail:		PASS
	 ****************************************************************************/
	@Test
	public void testMapPriority() {
		
		do{
			
			// Get the first resource in the list
			testResource = resourceListTest.getNextResource();
			
			if(testResource != null){
				
				// Verify for all resources if method return the corresponding value
				assertEquals(Resource.HIGH, testResource.mapPriority('H'));
				assertEquals(Resource.MED, 	testResource.mapPriority('M'));
				assertEquals(Resource.LOW,	testResource.mapPriority('L'));
				
				assertNotEquals(Resource.LOW,	testResource.mapPriority('M'));
				assertNotEquals(Resource.LOW,	testResource.mapPriority('H'));
				assertNotEquals(Resource.MED,	testResource.mapPriority('L'));
				assertNotEquals(Resource.MED,	testResource.mapPriority('H'));
				assertNotEquals(Resource.HIGH,	testResource.mapPriority('L'));
				assertNotEquals(Resource.HIGH,	testResource.mapPriority('M'));
				
				Random r = new Random();
				char c;
				
				// Verify unidentified letters
				for(int j = 0; j < 100; j++){
					
					// Generate random char
					c = (char)(r.nextInt(26) + 'A');
					
					// Exclude H, M and L since they've been verified
					if (c != 'H' && c != 'M' && c != 'L'){
						assertEquals(0, testResource.mapPriority(c));
					} // if
					
				} // for
			}
			
		} while(testResource != null);
		
	} // testMapPriority

}
