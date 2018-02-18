package lu.kremi151.sushilist.serialization;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Calendar;

import lu.kremi151.sushilist.util.SushiEntry;
import lu.kremi151.sushilist.util.SushiList;

/**
 * Created by michm on 18.02.2018.
 */

public class SushiListParserHandler extends DefaultHandler {

    static final String ROOT_TAG = "order";
    static final String ENTRY = "entry";
    static final String NAME = "name";
    static final String PIECES = "pieces";
    static final String AMOUNT = "amount";
    static final String PRICE = "price";

    private SushiList list = null;
    private StringBuilder builder;
    private SushiEntry currentEntry;

    SushiListParserHandler(){}

    public SushiList getList(){
        return list;
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
            if(this.list == null){
                list = new SushiList();
                final String title = attributes.getValue("title");
                final String timestamp = attributes.getValue("timestamp");
                if(title != null)list.setTitle(title);
                if(timestamp != null){
                    try{
                        Calendar date = Calendar.getInstance();
                        date.setTimeInMillis(Long.parseLong(timestamp));
                        list.setDate(date);
                    }catch(NumberFormatException e){
                        throw new SAXException(e);
                    }
                }
            }else{
                throw new SAXException("Unexpected root tag \"" + ROOT_TAG + "\"");
            }
        } else if (localName.equalsIgnoreCase(ENTRY)){
            this.currentEntry = new SushiEntry();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);
        if (this.currentEntry != null){
            if (localName.equalsIgnoreCase(NAME)){
                currentEntry.setName(builder.toString());
            } else if (localName.equalsIgnoreCase(PIECES)){
                try{
                    currentEntry.setPieces(Integer.parseInt(builder.toString()));
                }catch(NumberFormatException e){
                    throw new SAXException(e);
                }
            } else if (localName.equalsIgnoreCase(AMOUNT)){
                try{
                    currentEntry.setAmount(Integer.parseInt(builder.toString()));
                }catch(NumberFormatException e){
                    throw new SAXException(e);
                }
            } else if (localName.equalsIgnoreCase(PRICE)){
                try{
                    currentEntry.setPrice(Float.parseFloat(builder.toString()));
                }catch(NumberFormatException e){
                    throw new SAXException(e);
                }
            } else if (localName.equalsIgnoreCase(ENTRY)){
                list.getEntries().add(currentEntry);
            }
            builder.setLength(0);
        }
    }

}
