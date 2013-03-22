package org.ow2.petals.webadministration.service;

import java.net.URL;

import org.ow2.petals.webadministration.pojo.ArtifactList;
import org.ow2.petals.webadministration.service.exceptions.WebAdministrationException;

public interface ArtifactService{
 
	public ArtifactList listArtifacts () throws WebAdministrationException;
	public void startArtifact (String type, String name) throws WebAdministrationException;
	public void stopArtifact (String type, String name) throws WebAdministrationException;
	public void shutdownArtifact (String type, String name) throws WebAdministrationException;
	public int getMaxArtifactPerPage();
	public void installArtifact(URL artifactUrl) throws WebAdministrationException;
}