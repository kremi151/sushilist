package lu.kremi151.sushilist.serialization;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Calendar;

import static lu.kremi151.sushilist.serialization.SushiListParserHandler.*;

/**
 * Created by michm on 18.02.2018.
 */

public class SushiListMetaParserHandler extends DefaultHandler {

    private String title = null;
    private Calendar date = null;
    private StringBuilder builder;

    SushiListMetaParserHandler(){}

    public String getTitle(){
        return title;
    }

    public Calendar getDate(){
        return date;
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
            if(this.date == null){
                try{
                    final String rawTimestamp = attributes.getValue("timestamp");
                    if(rawTimestamp != null){
                        long timestamp = Long.parseLong(rawTimestamp);
                        date = Calendar.getInstance();
                        date.setTimeInMillis(timestamp);
                    }
                }catch(NumberFormatException e){
                    throw new SAXException(e);
                }
            }else{
                throw new SAXException("Unexpected root tag \"" + ROOT_TAG + "\"");
            }
        }
    }
}
