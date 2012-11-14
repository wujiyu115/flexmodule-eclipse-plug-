package flexmodule.xml;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

public class ActonScriptPropertiesVo {
	private Document doc;
	private Element root;
	private Element modules;
	private Element compiler;
	private Attribute sourceFolderPath;
	private Attribute mainApplicationPath;
	private Element apps;

	public ActonScriptPropertiesVo() {
		super();
	}

	public ActonScriptPropertiesVo(Document doc, Element root, Element modules,
			Element compiler, Attribute sourceFolderPath,
			Attribute mainApplicationPath) {
		super();
		this.doc = doc;
		this.root = root;
		this.modules = modules;
		this.compiler = compiler;
		this.sourceFolderPath = sourceFolderPath;
		this.mainApplicationPath = mainApplicationPath;
	}
	
	public Element getApps() {
		return apps;
	}

	public void setApps(Element apps) {
		this.apps = apps;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public Element getRoot() {
		return root;
	}

	public void setRoot(Element root) {
		this.root = root;
	}

	public Element getModules() {
		return modules;
	}

	public void setModules(Element modules) {
		this.modules = modules;
	}

	public Element getCompiler() {
		return compiler;
	}

	public void setCompiler(Element compiler) {
		this.compiler = compiler;
	}

	public Attribute getSourceFolderPath() {
		return sourceFolderPath;
	}

	public void setSourceFolderPath(Attribute sourceFolderPath) {
		this.sourceFolderPath = sourceFolderPath;
	}

	public Attribute getMainApplicationPath() {
		return mainApplicationPath;
	}

	public void setMainApplicationPath(Attribute mainApplicationPath) {
		this.mainApplicationPath = mainApplicationPath;
	}

	public Boolean hasModule(String path) {
		List<Element> eleList = modules.elements("module");
		for (Element element : eleList) {
			Attribute att = element.attribute("sourcePath");
			String sourcePathValue = att.getStringValue();
			if (sourcePathValue.equals(path))
				return true;
		}
		return false;
	}

	public Boolean hasLib(String libPath) {
		String sourceFolder = sourceFolderPath.getStringValue();
		libPath = libPath.replace(sourceFolder + "/", "");

		Element libraryPath = compiler.element("libraryPath");
		List<Element> eleList = libraryPath.elements("libraryPathEntry");
		for (Element element : eleList) {
			Attribute att = element.attribute("path");
			String sourcePathValue = att.getStringValue();
			if (sourcePathValue.equals(libPath)) {
				return true;
			}
		}
		return false;
	}

	public Boolean addModule(String sourcePath) {
		if (hasModule(sourcePath))
			return false;
		String sourceFolder = sourceFolderPath.getStringValue();
		String mainApplicationPathStr = sourceFolder + "/"
				+ mainApplicationPath.getStringValue();
		String destPath = sourcePath.replace(".as", ".swf").replace(
				sourceFolder + "/", "");
		Element module = modules.addElement("module");
		module.addAttribute("application", mainApplicationPathStr);
		module.addAttribute("destPath", destPath);
		module.addAttribute("optimize", "true");
		module.addAttribute("sourcePath", sourcePath);
		return true;
	}

	public Boolean removeModule(String sourcePath) {
		List<Element> eleList = modules.elements("module");
		for (Element element : eleList) {
			Attribute att = element.attribute("sourcePath");
			String sourcePathValue = att.getStringValue();
			if (sourcePathValue.equals(sourcePath)) {
				element.detach();
				return true;
			}
		}
		return false;
	}

	public Boolean addLib(String libPath, int kind) {
		String sourceFolder = sourceFolderPath.getStringValue();
		libPath = libPath.replace(sourceFolder + "/", "");
		Element libraryPath = compiler.element("libraryPath");

		Element libraryPathEntry = libraryPath.addElement("libraryPathEntry");

		libraryPathEntry.addAttribute("kind", String.valueOf(kind));
		libraryPathEntry.addAttribute("linkType", "1");
		libraryPathEntry.addAttribute("path", libPath);
		if(kind ==ActonScriptPropertiesConst.libraryPathEntry_SWC )
			libraryPathEntry.addAttribute("useDefaultLinkType", "false");
		return true;
	}

	public Boolean removeLib(String libPath) {
		String sourceFolder = sourceFolderPath.getStringValue();
		libPath = libPath.replace(sourceFolder + "/", "");

		Element libraryPath = compiler.element("libraryPath");
		List<Element> eleList = libraryPath.elements("libraryPathEntry");
		for (Element element : eleList) {
			Attribute att = element.attribute("path");
			String sourcePathValue = att.getStringValue();
			if (sourcePathValue.equals(libPath)) {
				element.detach();
				return true;
			}
		}
		return false;
	}
	
	public Boolean isApplication(String appPath){
		String sourceFolder = sourceFolderPath.getStringValue();
		String mainApplicationPathStr ;
		List<Element> eleList  =apps.elements("application");
		for (Element element : eleList) {
			Attribute att = element.attribute("path");
			mainApplicationPathStr=sourceFolder + "/"+att.getStringValue();
			if(appPath.equals(mainApplicationPathStr))
				return true;
		}
		return false;
	}

	

}
