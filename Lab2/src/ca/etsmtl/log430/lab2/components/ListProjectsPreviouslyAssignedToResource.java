package ca.etsmtl.log430.lab2.components;

import ca.etsmtl.log430.common.Displays;
import ca.etsmtl.log430.common.Menus;
import ca.etsmtl.log430.common.Resource;
import ca.etsmtl.log430.lab2.sharedData.CommonData;

import java.util.Observable;


public class ListProjectsPreviouslyAssignedToResource extends Communication {

    
    public ListProjectsPreviouslyAssignedToResource(Integer registrationNumber, String componentName) {
        super(registrationNumber, componentName);
    }


    /**
     * The update() method is an abstract method that is called whenever the
     * notifyObservers() method is called by the Observable class. First we
     * check to see if the NotificationNumber is equal to this thread's
     * RegistrationNumber. If it is, then we execute.
     *
     * @see ca.etsmtl.log430.lab2.components.Communication#update(java.util.Observable,
     *      java.lang.Object)
     */
    public void update(Observable thing, Object notificationNumber) {

        if (registrationNumber.compareTo((Integer) notificationNumber) == 0) {
			
        	Menus menu = new Menus();
            Displays display = new Displays();
            Resource myResource;
            
        	/*
			 * First we use a Displays object to list all of the resources. Then
			 * we ask the user to pick a resource using a Menus object.
			 */
            addToReceiverList("ListResourcesComponent");
            signalReceivers("ListResourcesComponent");
            myResource = menu.pickResource(CommonData.theListOfResources
                    .getListOfResources());

			/*
			 * If the user selected an invalid resource, then a message is
			 * printed to the terminal.
			 */
            if (myResource != null) {
                display.displayProjectsPreviouslyAssignedToResource(myResource, 
                		CommonData.theListOfProjects.getListOfProjects());
            } else {
                System.out.println("\n\n *** Resource not found ***");
            }
        }
        removeFromReceiverList("ListResourcesComponent");
    }



}

