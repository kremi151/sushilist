package lu.kremi151.sushilist.serialization;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import static lu.kremi151.sushilist.serialization.SushiListParserHandler.*;

/**
 * Created by michm on 18.02.2018.
 */

public class SushiListTitleHandler extends DefaultHandler {

    private String title = null;
    private StringBuilder builder;

    SushiListTitleHandler(){}

    public String getTitle(){
        return title;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equalsIgnoreCase(ROOT_TAG)){
            if(this.title == null){
                this.title = attributes.getValue("title");
            }else{
                throw new SAXException("Unexpected root tag \"" + ROOT_TAG + "\"");
            }
        }
    }
}
