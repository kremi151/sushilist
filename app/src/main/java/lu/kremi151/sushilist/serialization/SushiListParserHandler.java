package lu.kremi151.sushilist.serialization;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import lu.kremi151.sushilist.SushiEntry;

/**
 * Created by michm on 18.02.2018.
 */

public class SushiListParserHandler extends DefaultHandler {

    static final String ROOT_TAG = "entries";
    static final String ENTRY = "entry";
    static final String NAME = "name";
    static final String PIECES = "pieces";
    static final String AMOUNT = "amount";
    static final String PRICE = "price";

    private List<SushiEntry> entries;
    private StringBuilder builder;
    private SushiEntry currentEntry;

    SushiListParserHandler(){}

    public List<SushiEntry> getEntries(){
        return entries;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        entries = new ArrayList<SushiEntry>();
        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equalsIgnoreCase(ENTRY)){
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
                entries.add(currentEntry);
            }
            builder.setLength(0);
        }
    }

}
