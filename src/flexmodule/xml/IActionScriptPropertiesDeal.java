package flexmodule.xml;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;

public interface IActionScriptPropertiesDeal {
	public void addModule(IProject project, IResource selectFile);

	public void removeModule(IProject project, IResource selectFile);

	public void addLib(IProject project, IResource selectFile);

	public void removeLib(IProject project, IResource selectFile);
	
	public void doActin(IProject project, IResource selectFile,int doAction);

	public Boolean hasModule(IProject project, IResource selectFile);

	public Boolean hasLib(IProject project, IResource selectFile);

}
