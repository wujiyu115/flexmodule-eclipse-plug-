package flexmodule.xml;

import java.io.File;

public class ActonScriptPropertiesInfo {
	private ActonScriptPropertiesVo asproperties;
	private long lastModifed=0;
	private File file;

	public ActonScriptPropertiesInfo(File file) {
		super();
		this.file = file;
	}

	public ActonScriptPropertiesVo getAsproperties() {
		return asproperties;
	}

	private void updateLastModifed() {
		this.lastModifed = file.exists() ? file.lastModified()
				: this.lastModifed;
	}

	private Boolean getModified() {
		if (!file.exists())
			return false;
		return file.lastModified() != lastModifed;
	}

	public void update() {
		Boolean isModified = getModified();
//		System.out.println("是否修改文件:"+isModified);
		if (isModified) {
			asproperties = ActionScriptPropertiesReadWrite.createVo(file);
			updateLastModifed();
		}
	}

	public void write() {
//		System.out.println(asproperties.getDoc().asXML());
		ActionScriptPropertiesReadWrite.writeFile(file.getAbsolutePath(),
				asproperties.getDoc());
		updateLastModifed();
	}
	
	

}
