package org.ow2.petals.webadministration.pojo;

import java.util.List;

import org.ow2.petals.admin.api.artifact.Component;
import org.ow2.petals.admin.api.artifact.ServiceAssembly;
import org.ow2.petals.admin.api.artifact.ServiceUnit;
import org.ow2.petals.admin.api.artifact.SharedLibrary;

public class ArtifactList {

	private List<SharedLibrary> sharedLibraryList;
	private List<Component> bcList;
	private List<Component> seList;
	private List<ServiceAssembly> saList;
	private List<ServiceUnit> suList;
	public List<SharedLibrary> getSharedLibraryList() {
		return sharedLibraryList;
	}
	public void setSharedLibraryList(List<SharedLibrary> sharedLibraryList) {
		this.sharedLibraryList = sharedLibraryList;
	}
	public List<Component> getBcList() {
		return bcList;
	}
	public void setBcList(List<Component> bcList) {
		this.bcList = bcList;
	}
	public List<Component> getSeList() {
		return seList;
	}
	public void setSeList(List<Component> seList) {
		this.seList = seList;
	}
	public List<ServiceAssembly> getSaList() {
		return saList;
	}
	public void setSaList(List<ServiceAssembly> saList) {
		this.saList = saList;
	}
	public List<ServiceUnit> getSuList() {
		return suList;
	}
	public void setSuList(List<ServiceUnit> suList) {
		this.suList = suList;
	}
	
	
}
