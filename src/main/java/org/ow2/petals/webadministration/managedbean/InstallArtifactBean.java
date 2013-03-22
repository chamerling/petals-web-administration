package org.ow2.petals.webadministration.managedbean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Named;

import org.ow2.petals.webadministration.service.ArtifactService;
import org.ow2.petals.webadministration.service.exceptions.WebAdministrationException;
import org.ow2.petals.webadministration.utils.jsf.JSFUtils;
import org.ow2.petals.webadministration.utils.jsf.Messages;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

@Named
@Scope("session")
public class InstallArtifactBean {

	@Inject
	ArtifactService artifactService;


	private String url;

	public void handleFileURL() {

		URL currentURL = null;

		try {

			currentURL = new URL(this.url);
			this.artifactService.installArtifact(currentURL);

			Messages.addInfoMessage( Messages.buildMessage("installArtifact.message.install.ok",currentURL.getFile().substring(currentURL.getFile().lastIndexOf("/")+1)));
			ArtifactsBean a = JSFUtils.getBackingBean("artifactsBean",ArtifactsBean.class);
			if(a!=null) a.refreshPage();

		} catch (MalformedURLException e) {
			Messages.addErrorMessage( Messages.buildMessage("installArtifact.message.install.error.badurl"));
		} catch (WebAdministrationException e) {
			Messages.addErrorMessage( Messages.buildMessage("installArtifact.message.install.error",currentURL.getFile().substring(currentURL.getFile().lastIndexOf("/")+1)));
		}

		JSFUtils.redirectPage("/artifacts.xhmtl");
	}

	public void handleFileUpload() {

		if(this.file!=null){

		File fout =null;

			try {
				fout = copyFile(this.file.getFileName(), this.file.getInputstream());
				this.artifactService.installArtifact(fout.toURI().toURL());

				Messages.addInfoMessage( Messages.buildMessage("installArtifact.message.install.ok",this.file.getFileName()));
				ArtifactsBean a = JSFUtils.getBackingBean("artifactsBean",ArtifactsBean.class);
				if(a!=null) a.refreshPage();

			} catch (MalformedURLException e) {
				Messages.addErrorMessage( Messages.buildMessage("installArtifact.message.install.error",this.file.getFileName()));
			} catch (WebAdministrationException e) {
				Messages.addErrorMessage( Messages.buildMessage("installArtifact.message.install.error",this.file.getFileName()));
			} catch (IOException e) {
				Messages.addErrorMessage( Messages.buildMessage("installArtifact.message.install.error",this.file.getFileName()));
			} finally {
				if (!fout.delete()) fout.deleteOnExit();
			}
		}

		JSFUtils.redirectPage("/artifacts.xhmtl");
	}

	private File copyFile(String fileName, InputStream in) throws IOException {

		File temp = null;
		OutputStream out = null;

		try {
			temp = File.createTempFile(fileName, null);

			// write the inputStream to a FileOutputStream
			out = new FileOutputStream(temp);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

		} catch (IOException e) {
			throw e;
		} finally{
			try {
				in.close();
				if( out !=null ){
				out.flush();
				out.close();}
			} catch (IOException e) {
			}
		}
		return temp;
	}

    private UploadedFile file;

    public UploadedFile getFile() {
        return this.file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}