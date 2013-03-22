package org.ow2.petals.webadministration.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Named;

import org.ow2.petals.admin.api.ArtifactAdministration;
import org.ow2.petals.admin.api.ContainerAdministration;
import org.ow2.petals.admin.api.PetalsAdministrationFactory;
import org.ow2.petals.admin.api.artifact.Artifact;
import org.ow2.petals.admin.api.artifact.Component;
import org.ow2.petals.admin.api.artifact.Component.ComponentType;
import org.ow2.petals.admin.api.artifact.ServiceAssembly;
import org.ow2.petals.admin.api.artifact.ServiceUnit;
import org.ow2.petals.admin.api.artifact.SharedLibrary;
import org.ow2.petals.admin.api.exception.ArtifactAdministrationException;
import org.ow2.petals.admin.api.exception.ArtifactNotDeployedException;
import org.ow2.petals.admin.api.exception.ArtifactNotFoundException;
import org.ow2.petals.admin.api.exception.ArtifactStartedException;
import org.ow2.petals.admin.api.exception.ArtifactTypeIsNeededException;
import org.ow2.petals.admin.api.exception.ContainerAdministrationException;
import org.ow2.petals.admin.api.exception.DuplicatedServiceException;
import org.ow2.petals.admin.api.exception.MissingServiceException;
import org.ow2.petals.admin.api.exception.UnsupportedArtifactTypeException;
import org.ow2.petals.webadministration.pojo.ArtifactList;
import org.ow2.petals.webadministration.service.ArtifactService;
import org.ow2.petals.webadministration.service.PreferenceService;
import org.ow2.petals.webadministration.service.exceptions.WebAdministrationException;


@Named
public class ArtifactServiceImpl implements ArtifactService{
 
	
	@Inject
	private PreferenceService preferenceService;
	

	public ArtifactList listArtifacts () throws WebAdministrationException{
	
		ArtifactList artifacts = null;
		
    	try {
			PetalsAdministrationFactory jmxfactory = PetalsAdministrationFactory.newInstance();
			//System.setProperty(PetalsAdministrationFactory.PROPERTY_NAME, "org.ow2.petals.admin.jmx.JMXAdminFactory");
			ContainerAdministration containerAdministration = jmxfactory.newContainerAdministration();
			containerAdministration.connect(preferenceService.get().getHost(), preferenceService.get().getPort(), preferenceService.get().getUser(), preferenceService.get().getPassword());

			ArtifactAdministration artifactAdministration = jmxfactory.newArtifactAdministration();
			
			artifacts = sortArtifacts(artifactAdministration.listArtifacts());
			containerAdministration.disconnect();
		} catch (DuplicatedServiceException e) {
			throw new WebAdministrationException(e.getMessage(),e);
		} catch (MissingServiceException e) {
			throw new WebAdministrationException(e.getMessage(),e);
		} catch (ContainerAdministrationException e) {
			throw new WebAdministrationException(e.getMessage(),e);
		} catch (ArtifactAdministrationException e) {
			throw new WebAdministrationException(e.getMessage(),e);
		}
			
	    return artifacts;
	}
	
    private final static Comparator<Artifact> ARTIFACTNAMECOMPARATOR = new Comparator<Artifact>() {
        @Override
        public int compare(Artifact o1, Artifact o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
	
	
    private ArtifactList sortArtifacts(List<Artifact> artifacts) {
    	TreeSet<SharedLibrary> sls = new TreeSet<SharedLibrary>(ARTIFACTNAMECOMPARATOR);
    	TreeSet<Component> bcs = new TreeSet<Component>(ARTIFACTNAMECOMPARATOR);
    	TreeSet<Component> ses = new TreeSet<Component>(ARTIFACTNAMECOMPARATOR);
    	TreeSet<ServiceAssembly> sas = new TreeSet<ServiceAssembly>(ARTIFACTNAMECOMPARATOR);
    	TreeSet<ServiceUnit> sus = new TreeSet<ServiceUnit>(ARTIFACTNAMECOMPARATOR);

        for (Artifact artifact : artifacts) {
            if (artifact instanceof SharedLibrary) {
                sls.add((SharedLibrary) artifact);
            } else if (ComponentType.BC.toString().equals(artifact.getType())) {
                bcs.add((Component) artifact);
            } else if (ComponentType.SE.toString().equals(artifact.getType())) {
                ses.add((Component) artifact);
            } else if (artifact instanceof ServiceAssembly) {
                sas.add((ServiceAssembly) artifact);
                sus.addAll(((ServiceAssembly) artifact).getServiceUnits());
            }
        }

        ArtifactList al = new ArtifactList();
        al.setBcList(new ArrayList<Component>(bcs));
        al.setSeList(new ArrayList<Component>(ses));
        al.setSaList(new ArrayList<ServiceAssembly>(sas));
        al.setSuList(new ArrayList<ServiceUnit>(sus));
        al.setSharedLibraryList(new ArrayList<SharedLibrary>(sls));
        return al;
    }
 
    
    
	public void startArtifact (String type, String name) throws WebAdministrationException{
		
				try {
					PetalsAdministrationFactory jmxfactory = PetalsAdministrationFactory.newInstance();
					//System.setProperty(PetalsAdministrationFactory.PROPERTY_NAME, "org.ow2.petals.admin.jmx.JMXAdminFactory");
					ContainerAdministration containerAdministration = jmxfactory.newContainerAdministration();
					containerAdministration.connect(preferenceService.get().getHost(), preferenceService.get().getPort(), preferenceService.get().getUser(), preferenceService.get().getPassword());

					ArtifactAdministration artifactAdministration = jmxfactory.newArtifactAdministration();
					artifactAdministration.startArtifact(type, name);
					containerAdministration.disconnect();
				} catch (UnsupportedArtifactTypeException e) {
					throw new WebAdministrationException(e.getMessage(),e);
				} catch (ArtifactNotFoundException e) {
					throw new WebAdministrationException(e.getMessage(),e);
				} catch (ArtifactTypeIsNeededException e) {
					throw new WebAdministrationException(e.getMessage(),e);
				} catch (ArtifactStartedException e) {
					throw new WebAdministrationException(e.getMessage(),e);
				} catch (ArtifactNotDeployedException e) {
					throw new WebAdministrationException(e.getMessage(),e);
				} catch (DuplicatedServiceException e) {
					throw new WebAdministrationException(e.getMessage(),e);
				} catch (MissingServiceException e) {
					throw new WebAdministrationException(e.getMessage(),e);
				} catch (ContainerAdministrationException e) {
					throw new WebAdministrationException(e.getMessage(),e);
				} catch (ArtifactAdministrationException e) {
					throw new WebAdministrationException(e.getMessage(),e);
				}

			
			
	}
	
	public void stopArtifact (String type, String name) throws WebAdministrationException{
		
        	try {
				PetalsAdministrationFactory jmxfactory = PetalsAdministrationFactory.newInstance();
				//System.setProperty(PetalsAdministrationFactory.PROPERTY_NAME, "org.ow2.petals.admin.jmx.JMXAdminFactory");
				ContainerAdministration containerAdministration = jmxfactory.newContainerAdministration();
				containerAdministration.connect(preferenceService.get().getHost(), preferenceService.get().getPort(), preferenceService.get().getUser(), preferenceService.get().getPassword());

				ArtifactAdministration artifactAdministration = jmxfactory.newArtifactAdministration();
				artifactAdministration.stopArtifact(type, name);
				containerAdministration.disconnect();
			} catch (UnsupportedArtifactTypeException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (ArtifactNotFoundException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (ArtifactTypeIsNeededException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (DuplicatedServiceException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (MissingServiceException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (ContainerAdministrationException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (ArtifactAdministrationException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			}
			
	}
	
	public void shutdownArtifact (String type, String name) throws WebAdministrationException{
		
        	try {
				PetalsAdministrationFactory jmxfactory = PetalsAdministrationFactory.newInstance();
				//System.setProperty(PetalsAdministrationFactory.PROPERTY_NAME, "org.ow2.petals.admin.jmx.JMXAdminFactory");
				ContainerAdministration containerAdministration = jmxfactory.newContainerAdministration();
				containerAdministration.connect(preferenceService.get().getHost(), preferenceService.get().getPort(), preferenceService.get().getUser(), preferenceService.get().getPassword());

				ArtifactAdministration artifactAdministration = jmxfactory.newArtifactAdministration();
				artifactAdministration.stopAndUndeployArtifact(type, name);
				containerAdministration.disconnect();
			} catch (UnsupportedArtifactTypeException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (ArtifactNotFoundException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (ArtifactTypeIsNeededException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (DuplicatedServiceException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (MissingServiceException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (ContainerAdministrationException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (ArtifactAdministrationException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			}
	}

	
	public int getMaxArtifactPerPage(){
		return preferenceService.get().getMaxPerPageArtifactList();
	}



	@Override
	public void installArtifact(URL artifactUrl) throws WebAdministrationException {
			try {
				PetalsAdministrationFactory jmxfactory = PetalsAdministrationFactory.newInstance();
				//System.setProperty(PetalsAdministrationFactory.PROPERTY_NAME, "org.ow2.petals.admin.jmx.JMXAdminFactory");
				ContainerAdministration containerAdministration = jmxfactory.newContainerAdministration();
				containerAdministration.connect(preferenceService.get().getHost(), preferenceService.get().getPort(), preferenceService.get().getUser(), preferenceService.get().getPassword());

				ArtifactAdministration artifactAdministration = jmxfactory.newArtifactAdministration();
				artifactAdministration.deployAndStartArtifact(artifactUrl);
				containerAdministration.disconnect();
			} catch (DuplicatedServiceException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (MissingServiceException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (ContainerAdministrationException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			} catch (ArtifactAdministrationException e) {
				throw new WebAdministrationException(e.getMessage(),e);
			}
			
	}
}