package org.ow2.petals.webadministration.service;

import org.ow2.petals.admin.registry.RegistryView;
import org.ow2.petals.webadministration.service.exceptions.WebAdministrationException;

public interface RegistryService{
 
	 public RegistryView getRegistryContent(String endpointNameRegex, String serviceNameRegex,
	            String interfaceNameRegex) throws WebAdministrationException;

	 public RegistryView getRegistryContent() throws WebAdministrationException;

	 public int getMaxServicePerPage();
}