package flexmodule.startup;

import org.eclipse.ui.IStartup;

import flexmodule.FlexModuleActivator;

public class FlexModuleStartUp implements IStartup {

	public void earlyStartup() {
		Class activator=FlexModuleActivator.class;
	}

}
