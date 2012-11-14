package flexmodule.popup.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import flexmodule.xml.ActionScriptPropertiesDeal;
import flexmodule.xml.ActonScriptPropertiesConst;
import flexmodule.xml.IActionScriptPropertiesDeal;

public class FlexMoudleAction implements IObjectActionDelegate {

	private Shell shell;
	private IActionScriptPropertiesDeal asdeal;
	private ISelection selection;
	private int doAction = ActonScriptPropertiesConst.NOTHING;

	public FlexMoudleAction() {
		super();
		// System.out.println("FlexMoudleAction");
		asdeal = ActionScriptPropertiesDeal.getInstance();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	public void run(IAction action) {
		if (doAction == ActonScriptPropertiesConst.NOTHING)
			return;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection strselction = (IStructuredSelection) selection;
			IResource selectFile = (IResource) strselction.getFirstElement();
			if ((selectFile instanceof IFile)
					|| (selectFile instanceof IFolder)) {
				IProject selectProject = selectFile.getProject();
				asdeal.doActin(selectProject, selectFile, doAction);
				// 刷新资源
				try {
					selectProject.refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
//		System.out.println("run");
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection strselction = (IStructuredSelection) selection;
			IResource selectFile = (IResource) strselction.getFirstElement();
			if(null==selectFile){
				doAction = ActonScriptPropertiesConst.NOTHING;
				action.setText(ActonScriptPropertiesConst.NOTHING_DESC);
				return;
			}
			IProject selectProject = selectFile.getProject();
			if (selectFile instanceof IFile) {
				String filename = selectFile.getName();
				if (filename.endsWith(ActonScriptPropertiesConst.EXTENTION_SWC)) {
					Boolean haslib = asdeal.hasLib(selectProject, selectFile);
					if (haslib) {
						doAction = ActonScriptPropertiesConst.REMOVE_LIB;
						action.setText(ActonScriptPropertiesConst.REMOVE_LIB_DESC);
					} else {
						doAction = ActonScriptPropertiesConst.ADD_LIB;
						action.setText(ActonScriptPropertiesConst.ADD_LIB_DESC);
					}
				} else if (filename
						.endsWith(ActonScriptPropertiesConst.EXTENTION_AS)) {
					//是主程序不能设置为模块
					if(asdeal.isApplication(selectProject, selectFile)){
						doAction = ActonScriptPropertiesConst.NOTHING;
						action.setText(ActonScriptPropertiesConst.IS_APPLICATION_DESC);
						return;
					}
					Boolean hasmodule = asdeal.hasModule(selectProject,
							selectFile);
					if (hasmodule) {
						doAction = ActonScriptPropertiesConst.REMOVE_MODULE;
						action.setText(ActonScriptPropertiesConst.REMOVE_MODULE_DESC);
					} else {
						doAction = ActonScriptPropertiesConst.ADD_MODULE;
						action.setText(ActonScriptPropertiesConst.ADD_MODULE_DESC);
					}

				}
			} else if (selectFile instanceof IFolder) {
				Boolean haslib = asdeal.hasLib(selectProject, selectFile);
				if (haslib) {
					doAction = ActonScriptPropertiesConst.REMOVE_LIB;
					action.setText(ActonScriptPropertiesConst.REMOVE_LIB_DESC);
				} else {
					doAction = ActonScriptPropertiesConst.ADD_LIB;
					action.setText(ActonScriptPropertiesConst.ADD_LIB_DESC);
				}
			} else {
				doAction = ActonScriptPropertiesConst.NOTHING;
				action.setText(ActonScriptPropertiesConst.NOTHING_DESC);
			}

		} else {
			doAction = ActonScriptPropertiesConst.NOTHING;
			action.setText(ActonScriptPropertiesConst.NOTHING_DESC);
		}
		// System.out.println("change");
	}

}
