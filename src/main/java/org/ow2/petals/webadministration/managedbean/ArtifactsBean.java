package org.ow2.petals.webadministration.managedbean;

import javax.inject.Inject;
import javax.inject.Named;

import org.ow2.petals.webadministration.pojo.ArtifactList;
import org.ow2.petals.webadministration.service.ArtifactService;
import org.ow2.petals.webadministration.service.exceptions.WebAdministrationException;
import org.ow2.petals.webadministration.utils.jsf.Messages;
import org.springframework.context.annotation.Scope;

@Named
@Scope("session")
public class ArtifactsBean {

	@Inject
	ArtifactService artifactService;

	private ArtifactList artifactList;

    public void preRenderView() {
    	if(this.artifactList==null){
    		try {
				this.artifactList = this.artifactService.listArtifacts();
			}  catch (WebAdministrationException e) {
				Messages.addErrorMessage( Messages.buildMessage("artifacts.message.list.error"));
			}
    	}
    }
    private void refresh() {
    	try {
			this.artifactList = this.artifactService.listArtifacts();
		} catch (WebAdministrationException e) {
			Messages.addErrorMessage( Messages.buildMessage("artifacts.message.list.error"));
		}
    }


	public ArtifactList getArtifactList() {
		return this.artifactList;
	}

	public void refreshPage(){
		refresh();
	}

	public void start(String type,String name) {
		try {
			this.artifactService.startArtifact(type, name);
			refresh();
		} catch (WebAdministrationException e) {
			Messages.addErrorMessage( Messages.buildMessage("artifacts.message.start.error",name));
		}
	}

	public void stop(String type,String name) {
		try {
			this.artifactService.stopArtifact(type, name);
			refresh();
		} catch (WebAdministrationException e) {
			Messages.addErrorMessage( Messages.buildMessage("artifacts.message.stop.error",name));
		}
	}
	public void shutdown(String type,String name) {
		try {
			this.artifactService.shutdownArtifact(type, name);
			refresh();
		} catch (WebAdministrationException e) {
			Messages.addErrorMessage( Messages.buildMessage("artifacts.message.stop.shutdown",name));
		}

	}
	public int getMaxArtifactPerPage(){
		return this.artifactService.getMaxArtifactPerPage();
	}

}