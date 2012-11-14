package flexmodule.xml;

import java.io.File;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * actionscript �����ļ�������
 * 
 * @author far
 * @email wujiyu115@gmail.com
 * */
public class ActionScriptPropertiesDeal implements IActionScriptPropertiesDeal {

	private ActonScriptPropertiesInfo currentpropertiesInfo;
	private HashMap<IProject, ActonScriptPropertiesInfo> projects;
	private IPath actionscriptPro;

	private static ActionScriptPropertiesDeal instance;

	private ActionScriptPropertiesDeal() {
		init();
	}

	public static ActionScriptPropertiesDeal getInstance() {
		if (null == instance)
			instance = new ActionScriptPropertiesDeal();
		return instance;
	}

	private void init() {
		projects = new HashMap<IProject, ActonScriptPropertiesInfo>();
		actionscriptPro = new Path(".actionScriptProperties");
	}

	private Boolean setProject(IProject selectProject) {
		Boolean asreal = selectProject.exists(actionscriptPro);
		if (asreal) {
			ActonScriptPropertiesInfo selectpropertiesinfo;
			if (projects.containsKey(selectProject)) {
				selectpropertiesinfo = projects.get(selectProject);
			} else {
				IFile asfile = (IFile) selectProject.getFile(actionscriptPro);
				File convertfile = asfile.getLocation().toFile();
				selectpropertiesinfo = new ActonScriptPropertiesInfo(
						convertfile);
				projects.put(selectProject, selectpropertiesinfo);
			}
			selectpropertiesinfo.update();
			currentpropertiesInfo = selectpropertiesinfo;
		}
		return asreal;
	}

	/**
	 * ���ģ��
	 * */
	public void addModule(IProject project, IResource selectFile) {
		if (!(selectFile instanceof IFile))
			return;
		IFile file = (IFile) selectFile;
		Boolean asreal = setProject(project);
		if (!asreal)
			return;
		ActonScriptPropertiesVo currentInfo = currentpropertiesInfo
				.getAsproperties();
		String sourcePath = file.getProjectRelativePath().toOSString()
				.replaceAll("\\\\", "/");
		currentInfo.addModule(sourcePath);
		currentpropertiesInfo.write();
	}

	/**
	 * �Ƴ�ģ��
	 * */
	public void removeModule(IProject project, IResource selectFile) {
		if (!(selectFile instanceof IFile))
			return;
		IFile file = (IFile) selectFile;
		Boolean asreal = setProject(project);
		if (!asreal)
			return;
		ActonScriptPropertiesVo currentInfo = currentpropertiesInfo
				.getAsproperties();
		String sourcePath = file.getProjectRelativePath().toOSString()
				.replaceAll("\\\\", "/");
		currentInfo.removeModule(sourcePath);
		currentpropertiesInfo.write();
	}

	/** ��ӵ��� */
	public void addLib(IProject project, IResource selectFile) {
		Boolean asreal = setProject(project);
		if (!asreal)
			return;
		ActonScriptPropertiesVo currentInfo = currentpropertiesInfo
				.getAsproperties();
		String libPath = null;
		int kind = 0;
		if (selectFile instanceof IFile) {
			kind = ActonScriptPropertiesConst.libraryPathEntry_SWC;
		} else if (selectFile instanceof IFolder) {
			kind = ActonScriptPropertiesConst.libraryPathEntry_LIB;
		}
		libPath = selectFile.getProjectRelativePath().toOSString()
				.replaceAll("\\\\", "/");
		if (null != libPath) {
			currentInfo.addLib(libPath, kind);
			currentpropertiesInfo.write();
		}

	}

	/** �ӿ��Ƴ� */
	public void removeLib(IProject project, IResource selectFile) {
		Boolean asreal = setProject(project);
		if (!asreal)
			return;
		ActonScriptPropertiesVo currentInfo = currentpropertiesInfo
				.getAsproperties();
		String libPath = null;
		libPath = selectFile.getProjectRelativePath().toOSString()
				.replaceAll("\\\\", "/");
		if (null != libPath) {
			currentInfo.removeLib(libPath);
			currentpropertiesInfo.write();
		}
	}

	/**
	 * �Ƿ���� ��ģ��
	 * */
	public Boolean hasModule(IProject project, IResource selectFile) {
		if (!(selectFile instanceof IFile))
			return false;
		IFile file = (IFile) selectFile;
		Boolean asreal = setProject(project);
		if (!asreal)
			return false;
		ActonScriptPropertiesVo currentInfo = currentpropertiesInfo
				.getAsproperties();
		String sourcePath = file.getProjectRelativePath().toOSString()
				.replaceAll("\\\\", "/");
		if (null != sourcePath) {
			return currentInfo.hasModule(sourcePath);
		}
		return false;
	}

	/**
	 * �Ƿ���ڴ˿�
	 * */
	public Boolean hasLib(IProject project, IResource selectFile) {
		Boolean asreal = setProject(project);
		if (!asreal)
			return false;
		ActonScriptPropertiesVo currentInfo = currentpropertiesInfo
				.getAsproperties();
		String libPath = null;
		libPath = selectFile.getProjectRelativePath().toOSString()
				.replaceAll("\\\\", "/");
		if (null != libPath) {
			return currentInfo.hasLib(libPath);
		}
		return false;
	}

	@Override
	public void doActin(IProject project, IResource selectFile, int doAction) {
		switch (doAction) {
		case ActonScriptPropertiesConst.ADD_MODULE:
			this.addModule(project, selectFile);
			break;
		case ActonScriptPropertiesConst.ADD_LIB:
			this.addLib(project, selectFile);
			break;
		case ActonScriptPropertiesConst.REMOVE_MODULE:
			this.removeModule(project, selectFile);
			break;
		case ActonScriptPropertiesConst.REMOVE_LIB:
			this.removeLib(project, selectFile);
			break;
		default:
			break;
		}
	}

	@Override
	public Boolean isApplication(IProject project, IResource selectFile) {
		if (!(selectFile instanceof IFile))
			return false;
		IFile file = (IFile) selectFile;
		Boolean asreal = setProject(project);
		if (!asreal)
			return false;
		ActonScriptPropertiesVo currentInfo = currentpropertiesInfo
				.getAsproperties();
		String sourcePath = file.getProjectRelativePath().toOSString()
				.replaceAll("\\\\", "/");
		if (null != sourcePath) {
			return currentInfo.isApplication(sourcePath);
		}
		return false;
	}

}
