package util;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SpritesheetInfo {

    private List<SpritePojo> sprites = new ArrayList<>();

    public SpritesheetInfo(String source) {
        if (source.contains(".txt"))
                processSpritesTXT(source);
        else if (source.contains(".xml"))
                processSpritesXML(source);
        else assert false: "unknown format for spritesheet information";
    }

    private void processSpritesXML(String source) {
        try {
            File file = new File(source);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("SubTexture");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                NamedNodeMap nodeMap = node.getAttributes();
                sprites.add(new SpritePojo(
                        nodeMap.getNamedItem("name").getNodeValue().substring(0, nodeMap.getNamedItem("name").getNodeValue().indexOf(".png")),
                        Integer.parseInt(nodeMap.getNamedItem("x").getNodeValue()),
                        Integer.parseInt(nodeMap.getNamedItem("y").getNodeValue()),
                        Integer.parseInt(nodeMap.getNamedItem("width").getNodeValue()),
                        Integer.parseInt(nodeMap.getNamedItem("height").getNodeValue())
                ));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void processSpritesTXT(String source) {
        try(BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String line = reader.readLine();

            while(line != null) {
                String[] words = line.split("\\s+");
                sprites.add(new SpritePojo(
                        words[0], Integer.parseInt(words[2]), Integer.parseInt(words[3]), Integer.parseInt(words[4]), Integer.parseInt(words[5])
                ));

                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("The input spritesheet is not in the expected format: \n" + e);
        }
    }

    public List<SpritePojo> getSprites(){
        return this.sprites;
    }
}
