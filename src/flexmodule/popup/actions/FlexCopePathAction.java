package flexmodule.popup.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class FlexCopePathAction implements IObjectActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection strselction = (IStructuredSelection) selection;
			IResource selectFile = (IResource) strselction.getFirstElement();
			if (null != selectFile&&selectFile instanceof IFile) {
				IFile file = (IFile)selectFile;
				String sourcePath = file.getProjectRelativePath().toOSString();
				sourcePath =sourcePath.substring(sourcePath.indexOf("\\")+1,sourcePath.indexOf("."));
				sourcePath=	sourcePath.replaceAll("\\\\", ".");
				 Clipboard clipBoard = new Clipboard(null);
				 TextTransfer textTransfer = TextTransfer.getInstance(); 
				 clipBoard.setContents(new String[]{sourcePath}, new Transfer[]{textTransfer});
				 clipBoard.dispose();
			}
		}

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {

	}

}
