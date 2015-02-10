package ca.etsmtl.log430.lab2.bus;

import ca.etsmtl.log430.lab2.components.AssignResourceToProject;
import ca.etsmtl.log430.lab2.components.ListProjects;
import ca.etsmtl.log430.lab2.components.ListProjectsAssignedToResource;
import ca.etsmtl.log430.lab2.components.ListProjectsPreviouslyAssignedToResource;
import ca.etsmtl.log430.lab2.components.ListResources;
import ca.etsmtl.log430.lab2.components.ListResourcesAssignedToProject;

/**
 * Initializes the system. First each component is instantiated. This is where
 * the component name and an ID is assigned. Then, the executive component is
 * started.
 * 
 * @author A.J. Lattanze, CMU
 * @version 1.5, 2013-Oct-06
 */

/*
 * Modification Log **********************************************************
 * v1.5, R. Champagne, 2013-Oct-06 - Various refactorings for new lab.
 * 
 * v1.4, R. Champagne, 2012-Jun-19 - Various refactorings for new lab.
 * 
 * v1.3, R. Champagne, 2012-Feb-14 - Various refactorings for new lab.
 * 
 * v1.2, R. Champagne, 2011-Feb-24 - Various refactorings, conversion of
 * comments to javadoc format.
 * 
 * v1.1, R. Champagne, 2002-Jun-19 - Adapted for use at ETS.
 * 
 * v1.0, A.J. Lattanze, 12/29/99 - Original version.
 * ***************************************************************************
 */

public class SystemInitialize {

	public static void main(String argv[]) {
		/*
		 * We instantiate each system component. After these components are
		 * instantiated, they simply wait until the Executive broadcasts
		 * changes. The broadcast message includes a registration number. These
		 * IDs are used in broadcasts to components. The component name is also
		 * included and is used whenever a caller wants to reference a
		 * component. Rather than have to remember a number, they can reference
		 * a component by name. I used the declared name of the object, but you
		 * could use anything you want.
		 */
		new AssignResourceToProject(new Integer(100), "AssignResourceToProject");
		new ListResources(new Integer(101), "ListResourcesComponent");
		new ListProjects(new Integer(102), "ListProjectsComponent");
		new ListProjectsAssignedToResource(new Integer(103),
				"ListProjectsAssignedToResourceComponent");
		new ListResourcesAssignedToProject(new Integer(104),
				"ListResourcesAssignedToProjectComponent");
		new ListProjectsPreviouslyAssignedToResource(new Integer(105),
				"ListProjectsPreviouslyAssignedToResourceComponent");
		
		Executive executiveComponent = new Executive(new Integer(106),
				"ExecutiveComponent");

		// Start the executive
		executiveComponent.execute();
	}
}