package ca.etsmtl.log430.lab1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class defines the Resource object for the system. Besides the basic
 * attributes, there are two lists maintained. alreadyAssignedProjectList is a
 * ProjectList object that maintains a list of projects that the resource was
 * already assigned to prior to this execution of the system.
 * projectsAssignedList is also a ProjectList object that maintains a list of
 * projects assigned to the resource durint the current execution or session.
 * 
 * @author A.J. Lattanze, CMU
 * @version 1.6, 2013-Sep-13
 */

/* Modification Log
 ****************************************************************************
 * v1.6, R. Champagne, 2013-Sep-13 - Various refactorings for new lab.
 * 
 * v1.5, R. Champagne, 2012-Jun-19 - Various refactorings for new lab.
 * 
 * v1.4, R. Champagne, 2012-May-31 - Various refactorings for new lab.
 * 
 * v1.3, R. Champagne, 2012-Feb-02 - Various refactorings for new lab.
 * 
 * v1.2, 2011-Feb-02, R. Champagne - Various refactorings, javadoc comments.
 *  
 * v1.1, 2002-May-21, R. Champagne - Adapted for use at ETS. 
 * 
 * v1.0, 12/29/99, A.J. Lattanze - Original version.

 ****************************************************************************/

public class Resource {
	
	/**
	 * Project priority values
	 */
	public static final int HIGH 	= 100;
	public static final int MED 	= 50;
	public static final int LOW 	= 25;

	/**
	 * Resource's last name
	 */
	private String lastName;
	
	/**
	 * Resource's first name
	 */
	private String firstName;
	
	/**
	 * Resource's identification number
	 */
	private String id;
	
	/**
	 * Resource role 
	 */
	private String role;

	/**
	 *  List of projects the resource is already allocated to
	 */
	private ProjectList alreadyAssignedProjectList = new ProjectList();

	/**
	 *  List of projects assigned to the resource in this session
	 */
	private ProjectList projectsAssignedList = new ProjectList();

	/**
	 *  Value of resource based on project priority
	 */
	private int resourceValue;
	
	/**
	 * Assigns a project to a resource.
	 * 
	 * @param project
	 * @throws Exception 
	 */
	public void assignProject(Project project) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = formatter.parse(project.getStartDate());
		Date endDate = formatter.parse(project.getEndDate());
		
		//Complex logic with dates manipulation :
		
		//Iterate over all assigned project to this resource
		//For each project, check if the new project is inside the time lap
		   //If inside the time lap
		     //Check if the priority will go over 100%
		       //If yes : throw the exception
		       //else : assign the project
		
		//Basic logic :
		addResourceValue(project);
		if(getResourceValue() > HIGH){
			subResourceValue(project);
			throw new Exception("Resource usage will go over 100&");
		}
		else{
			getProjectsAssigned().addProject(project);
		}
		
	}
	
	/**
	 * Add resource value to project
	 * 
	 * @param projectAdded
	 * @return
	 */
	public void addResourceValue(Project projectAdded){
		setResourceValue(getResourceValue() + mapPriority(projectAdded.getPriority().charAt(0)));
	}
	
	/**
	 * Subtract resource value to project
	 * 
	 * @param projectRemoved
	 * @return
	 */
	public void subResourceValue(Project projectRemoved){
		setResourceValue(getResourceValue() - mapPriority(projectRemoved.getPriority().charAt(0)));
	}
	
	/**
	 * Returns priority value
	 * 
	 * @param letter
	 * @return
	 */
	public int mapPriority(char letter) {
		
		switch (letter){
			case 'H':
				return HIGH;
			case 'M':
				return MED;
			case 'L':
				return LOW;
			default:
				return 0;
		}
			
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setPreviouslyAssignedProjectList(ProjectList projectList) {
		this.alreadyAssignedProjectList = projectList;
	}

	public ProjectList getPreviouslyAssignedProjectList() {
		return alreadyAssignedProjectList;
	}

	public void setProjectsAssigned(ProjectList projectList) {
		this.projectsAssignedList = projectList;
	}

	public ProjectList getProjectsAssigned() {
		return projectsAssignedList;
	}

	public int getResourceValue() {
		return resourceValue;
	}

	public void setResourceValue(int resourceValue) {
		this.resourceValue = resourceValue;
	}

} // Resource class