package org.ow2.petals.webadministration.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.ow2.petals.admin.api.ContainerAdministration;
import org.ow2.petals.admin.api.PetalsAdministrationFactory;
import org.ow2.petals.admin.api.RegistryAdministration;
import org.ow2.petals.admin.api.exception.ContainerAdministrationException;
import org.ow2.petals.admin.api.exception.DuplicatedServiceException;
import org.ow2.petals.admin.api.exception.MissingServiceException;
import org.ow2.petals.admin.api.exception.RegistryAdministrationException;
import org.ow2.petals.admin.api.exception.RegistryRegexpPatternException;
import org.ow2.petals.admin.registry.RegistryView;
import org.ow2.petals.webadministration.service.PreferenceService;
import org.ow2.petals.webadministration.service.RegistryService;
import org.ow2.petals.webadministration.service.exceptions.WebAdministrationException;

@Named
public class RegistryServiceImpl implements RegistryService {

	@Inject
	private PreferenceService preferenceService;

	@Override
	public RegistryView getRegistryContent(String endpointNameRegex,
			String serviceNameRegex, String interfaceNameRegex)
			throws WebAdministrationException {
		
		RegistryView r;
		
		try {
			PetalsAdministrationFactory jmxfactory = PetalsAdministrationFactory.newInstance();
			ContainerAdministration containerAdministration = jmxfactory.newContainerAdministration();
			containerAdministration.connect(preferenceService.get().getHost(), preferenceService.get().getPort(), preferenceService.get().getUser(), preferenceService.get().getPassword());

			RegistryAdministration registryAdministration = jmxfactory.newRegistryAdministration();
			r = registryAdministration.getRegistryContent(endpointNameRegex,serviceNameRegex, interfaceNameRegex);
			containerAdministration.disconnect();
		} catch (RegistryRegexpPatternException e) {
			throw new WebAdministrationException(e.getMessage(),e);
		} catch (DuplicatedServiceException e) {
			throw new WebAdministrationException(e.getMessage(),e);
		} catch (MissingServiceException e) {
			throw new WebAdministrationException(e.getMessage(),e);
		} catch (ContainerAdministrationException e) {
			throw new WebAdministrationException(e.getMessage(),e);
		} catch (RegistryAdministrationException e) {
			throw new WebAdministrationException(e.getMessage(),e);
		}
        return r;
	}
	
	
	@Override
	public RegistryView getRegistryContent() throws WebAdministrationException {
		return getRegistryContent(".*", ".*", ".*");
	}
	
	@Override
	public int getMaxServicePerPage(){
		return preferenceService.get().getMaxPerPageServiceList();
	}
	
}