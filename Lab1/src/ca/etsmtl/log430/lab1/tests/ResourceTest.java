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
 * the data layer, a significative amount of test cases were made.
 * 
 * Each test case will use the following format:
 * =========================================================================
 * 	Test case #		Number of the current test case
 * 	Description: 	Describe the test case without going too far in the details.
 * 	Procedure:		Step by step explanation on how to achieve the results.
 * 	Data in:		List of files that will be used on the test case
 * 	Data out:		Modification brought on the used files.
 *	Pass/fail:		Determine if the test case is a PASS or FAIL.
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
	 * Description: 	Test wherever there is a resource's project overloading
	 * 					by assigning P001 with P002 (overlap / overload) and
	 * 					with P003 (no overlap / no overload).
	 * Procedure:		1) 	Assign project P001 to all resources
	 * 					2) 	Try adding a project that overlaps the first project
	 * 					3) 	Assign project P003 to all resources
	 * Data in:			+ 	projects.txt
	 * 					+ 	resources.txt
	 * Data out:		All resources are assigned to P001 and 4 x P003
	 * Pass/fail:		PASS
	 * =========================================================================
	 * @throws Exception
	 ****************************************************************************/
	@Test
	public void assignProjectOverload() {

		do{

			// Get the first resource in the list
			testResource = resourceListTest.getNextResource();
			
			if(testResource != null){
				
				// Add project P001 to every resources
				try {
					testResource.assignProject(projectListTest.findProjectByID("P001"));
				} catch (Exception e1) {
					fail();
				}
				
				// Adding project P002 will return an exception
				testProject = projectListTest.findProjectByID("P002");
				
			
				try{
					testResource.assignProject(testProject);
				} catch (Exception e) {
					// Verified
				} // try/catch
				
				// Verify that the project P002 has not been added to the 
				// resource's project list
				assertNull(testResource.getProjectsAssigned().findProjectByID(testProject.getID()));
				
				// Add four project ID P003
				testProject = projectListTest.findProjectByID("P003");
				for (int i = 0; i < 4; i ++){
					try {
						testResource.assignProject(testProject);
					} catch (Exception e) {
						fail();
					}
				}
			
				// Verify that the project P003 has been added to the resource's project list
				assertNotNull(testResource.getProjectsAssigned().findProjectByID(testProject.getID()));
				
				// Adding a fifth project ID P003 will return an exception
				try{
					testResource.assignProject(testProject);
				} catch (Exception e) {
					// Verified			
				} // try/catch
				
			} // if

		} while (testResource != null);
		
	} // assignProjectOverload

	/****************************************************************************
	 * Test the AssignProject() method within the data layer.
	 * =========================================================================
	 * Test case 2
	 * Description: 	Assign project P002 with P003 (no overlap / no overflow)
	 * 					and then, with P001 (overlap / overflow).
	 * Procedure:		1) 	Assign P002 to every resources (x2)
	 * 					2) 	Assign P003 to every resources (x4)
	 * 					3) 	Try assigning P001
	 * Data in:			+ 	projects.txt
	 * 					+ 	resources.txt
	 * Data out:		All resources are assigned to P002 and P003.
	 * Pass/fail:		PASS
	 * =========================================================================
	 * @throws Exception
	 ****************************************************************************/
	@Test
	public void assignProjectOverloadIndividual() {

		do{

			// Get the first resource in the list
			testResource = resourceListTest.getNextResource();
			
			if(testResource != null){
				
				// Add two project ID P002
				testProject = projectListTest.findProjectByID("P002");
				for (int i = 0; i < 2; i ++){
					try {
						testResource.assignProject(testProject);
					} catch (Exception e) {
						fail();
					}
				}
			
				// Verify that the project P002 has been added to the resource's project list
				assertNotNull(testResource.getProjectsAssigned().findProjectByID(testProject.getID()));
				
				// Adding a third project ID P002 will return an exception
				try{
					testResource.assignProject(testProject);
				} catch (Exception e) {
					// Verified				
				}
				
				// Add four project ID P003
				testProject = projectListTest.findProjectByID("P003");
				for (int i = 0; i < 4; i ++){
					try {
						testResource.assignProject(testProject);
					} catch (Exception e) {
						fail();
					}
				}
			
				// Verify that the project P003 has been added to the resource's project list
				assertNotNull(testResource.getProjectsAssigned().findProjectByID(testProject.getID()));
				
				// Adding a fifth project ID P003 will return an exception
				try{
					testResource.assignProject(testProject);
				} catch (Exception e) {
					// Verified			
				} // try/catch
				
				// Adding one project ID P001 will return an exception
				testProject = projectListTest.findProjectByID("P001");
				try{
					testResource.assignProject(testProject);
				} catch (Exception e) {
					// Verified			
				} // try/catch
				
				// Verify that the project P001 has not been added to the 
				// resource's project list
				assertNull(testResource.getProjectsAssigned().findProjectByID(testProject.getID()));
				
			} // if

		} while (testResource != null);
		
	} // assignProjectOverloadIndividual
	
	/****************************************************************************
	 * Test the AssignProject() method within the data layer.
	 * =========================================================================
	 * Test case 3
	 * Description: 	Assign one project to a resource followed by two new 
	 * 					projects, both overlapping the old project but not
	 * 					themselves.
	 * Procedure:		1) 	Assign Project P002
	 * 					2) 	Assign Project P034 (Overlap / no overflow)
	 * 					3) 	Assign Project P035 (Overlap / no overflow)
	 * Data in:			+ 	projects.txt
	 * 					+ 	resources.txt
	 * Data out:		All resources are assigned to P002, P034 and P035.
	 * Pass/fail:		PASS
	 * =========================================================================
	 * @throws Exception
	 ****************************************************************************/
	@Test
	public void assignOverloadOverlapOnly() {

		do{

			// Get the first resource in the list
			testResource = resourceListTest.getNextResource();
			
			if(testResource != null){
				
				try {

					// Add project P002 to every resources
					testResource.assignProject(projectListTest.findProjectByID("P002"));

					// Project P034 overlaps with P002
					testResource.assignProject(projectListTest.findProjectByID("P034"));
			
					// Project P035 overlaps with both P002 and P034
					testResource.assignProject(projectListTest.findProjectByID("P035"));
					
				} catch (Exception e) {
					fail();
				}

				// Verify that the all projects mentionned in TC has been added 
				// to the resource's project list
				assertNotNull(testResource.getProjectsAssigned().findProjectByID("P002"));
				assertNotNull(testResource.getProjectsAssigned().findProjectByID("P034"));
				assertNotNull(testResource.getProjectsAssigned().findProjectByID("P035"));
				
			} // if

		} while (testResource != null);
		
	} // assignOverloadOverlapOnly
	
	/****************************************************************************
	 * Test the AssignProject() method within the data layer.
	 * =========================================================================
	 * Test case 4
	 * Description: 	Assign projects on the following order:
	 * 					- 	One low priority project.
	 * 					- 	One high priority project, no overlaps.
	 * 					- 	One low priority project that overlaps all previous 
	 * 						projects.
	 * 					System should detect resource's project overload.
	 * Procedure:		1) 	Assign project 
	 * Data in:			+ 	projects.txt
	 * 					+ 	resources.txt
	 * Data out:		All resources are assigned to P047 and P048.
	 * Pass/fail:		PASS
	 * =========================================================================
	 * @throws Exception
	 ****************************************************************************/
	@Test
	public void assignProjectOverlapOthers() {

		do{

			// Get the first resource in the list
			testResource = resourceListTest.getNextResource();
			
			if(testResource != null){
				
				try {

					// Add project P047 to every resources
					testResource.assignProject(projectListTest.findProjectByID("P047"));

					// Project P048 does not overlap with P047
					testResource.assignProject(projectListTest.findProjectByID("P048"));
					
				} catch (Exception e1) {
					fail();
				}

				// Project P049 overlaps with both P047 and P048
				testProject = projectListTest.findProjectByID("P049");
				try{
					testResource.assignProject(testProject);
				} catch (Exception e) {
					// Verified
				} // try/catch

				// Verify that the project P049 has not been added
				assertNull(testResource.getProjectsAssigned().findProjectByID("P0049"));

				// Verify that the rest has been correctly added
				assertNotNull(testResource.getProjectsAssigned().findProjectByID("P047"));
				assertNotNull(testResource.getProjectsAssigned().findProjectByID("P048"));
				
			} // if

		} while (testResource != null);
		
	} // assignProjectOverlapOthers
	
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
				
				// Make sure that they return nothing else but their corresponding values
				assertNotSame(Resource.LOW,	testResource.mapPriority('M'));
				assertNotSame(Resource.LOW,	testResource.mapPriority('H'));
				assertNotSame(Resource.MED,	testResource.mapPriority('L'));
				assertNotSame(Resource.MED,	testResource.mapPriority('H'));
				assertNotSame(Resource.HIGH,testResource.mapPriority('L'));
				assertNotSame(Resource.HIGH,testResource.mapPriority('M'));
				
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
				
			} // if
			
		} while(testResource != null);
		
	} // testMapPriority

}
