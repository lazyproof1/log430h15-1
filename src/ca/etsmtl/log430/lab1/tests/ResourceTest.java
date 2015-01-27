package ca.etsmtl.log430.lab1.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.etsmtl.log430.lab1.donnees.Project;
import ca.etsmtl.log430.lab1.donnees.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ResourceTest {

	Resource testRes = new Resource();
	Project testProject = new Project();
	
	@Test
	public void testAssignProject() throws Exception {

		// Create a new project
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate1 = formatter.parse(testProject.getStartDate());
		Date endDate1 = formatter.parse(testProject.getEndDate());
		
		
		
	}

	@Test
	public void testMapPriority() {
		
		// Verify if method return the corresponding value
		assertEquals(Resource.HIGH, testRes.mapPriority('H'));
		assertEquals(Resource.MED, 	testRes.mapPriority('M'));
		assertEquals(Resource.LOW,	testRes.mapPriority('L'));
		
		assertNotEquals(Resource.LOW,	testRes.mapPriority('M'));
		assertNotEquals(Resource.LOW,	testRes.mapPriority('H'));
		assertNotEquals(Resource.MED,	testRes.mapPriority('L'));
		assertNotEquals(Resource.MED,	testRes.mapPriority('H'));
		assertNotEquals(Resource.HIGH,	testRes.mapPriority('L'));
		assertNotEquals(Resource.HIGH,	testRes.mapPriority('M'));
		
		Random r = new Random();
		char c;
		
		// Verify unidentified letters
		for(int i = 0; i < 100; i++){
			c = (char)(r.nextInt(26) + 'A');
			if (c != 'H' && c != 'M' && c != 'L'){
				assertEquals(0, testRes.mapPriority(c));
			} // if
		} // for
		
	}

}
