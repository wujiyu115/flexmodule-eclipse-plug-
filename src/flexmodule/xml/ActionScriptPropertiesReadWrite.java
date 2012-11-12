package flexmodule.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ActionScriptPropertiesReadWrite {
	public static ActonScriptPropertiesVo createVo(File file) {
		ActonScriptPropertiesVo aspropertiesVo = new ActonScriptPropertiesVo();
		SAXReader saxreader = new SAXReader();
		Document doc = null;
		Element root;
		Element modules;
		Element compiler;
		Attribute sourceFolderPath;
		Attribute mainApplicationPath;
		try {
			doc = saxreader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		if(null==doc)
			return null;
		root = doc.getRootElement();
		modules = root.element("modules");
		if (null == modules)
			root.addElement("modules");
		mainApplicationPath = root.attribute("mainApplicationPath");
		compiler = root.element("compiler");
		sourceFolderPath = compiler.attribute("sourceFolderPath");
		
		aspropertiesVo.setDoc(doc);
		aspropertiesVo.setRoot(root);
		aspropertiesVo.setModules(modules);
		aspropertiesVo.setCompiler(compiler);
		aspropertiesVo.setSourceFolderPath(sourceFolderPath);
		aspropertiesVo.setMainApplicationPath(mainApplicationPath);
		return aspropertiesVo;

	}

	public static void writeFile(String filePath, Document doc) {
		XMLWriter xmlwriter;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		try {
			xmlwriter = new XMLWriter(new FileWriter(filePath), format);
			xmlwriter.write(doc);
			xmlwriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
