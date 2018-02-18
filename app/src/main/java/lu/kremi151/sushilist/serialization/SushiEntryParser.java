package lu.kremi151.sushilist.serialization;

import android.content.Context;
import android.util.Xml;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import lu.kremi151.sushilist.util.SushiEntry;
import lu.kremi151.sushilist.util.SushiList;
import lu.kremi151.sushilist.util.SushiListReference;

import static lu.kremi151.sushilist.serialization.SushiListParserHandler.*;

/**
 * Created by michm on 18.02.2018.
 */

public class SushiEntryParser {

    private static final SAXParserFactory PARSER_FACTORY = SAXParserFactory.newInstance();

    public static SushiList parse(InputStream inputStream) throws SAXException{
        try {
            SAXParser parser = PARSER_FACTORY.newSAXParser();
            SushiListParserHandler handler = new SushiListParserHandler();
            parser.parse(inputStream, handler);
            return handler.getList();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new SAXException(e);
        }
    }

    public static void write(OutputStream outputStream, SushiList entries) throws SAXException{
        XmlSerializer serializer = Xml.newSerializer();
        Writer writer = new OutputStreamWriter(outputStream);
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", ROOT_TAG);
            //serializer.attribute("", "number", String.valueOf(messages.size()));
            for (SushiEntry entry : entries){
                serializer.startTag("", ENTRY);

                serializer.startTag("", NAME);
                serializer.text(entry.getName());
                serializer.endTag("", NAME);

                serializer.startTag("", PIECES);
                serializer.text(String.valueOf(entry.getPieces()));
                serializer.endTag("", PIECES);

                serializer.startTag("", AMOUNT);
                serializer.text(String.valueOf(entry.getAmount()));
                serializer.endTag("", AMOUNT);

                serializer.startTag("", PRICE);
                serializer.text(String.valueOf(entry.getPrice()));
                serializer.endTag("", PRICE);

                serializer.endTag("", ENTRY);
            }
            serializer.endTag("", ROOT_TAG);
            serializer.endDocument();

            writer.flush();
        } catch (Exception e) {
            throw new SAXException(e);
        }
    }

    public static List<File> getSavedLists(Context context){
        File dir = context.getFilesDir();
        return Arrays.asList(dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".xml");
            }
        }));
    }

    public static List<SushiListReference> getSavedReferences(Context context) throws IOException{
        List<File> files = getSavedLists(context);
        ArrayList<SushiListReference> refs = new ArrayList<>(files.size());
        for(File file : files){
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                SAXParser parser = PARSER_FACTORY.newSAXParser();
                SushiListTitleHandler handler = new SushiListTitleHandler();
                parser.parse(inputStream, handler);
                String title = handler.getTitle();
                Calendar date = handler.getDate();
                if(title == null){
                    title = "Unnamed (" + file.getName() + ")";
                }
                if(date == null){
                    date = Calendar.getInstance();
                }
                refs.add(new SushiListReference(title, date, file));
            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new IOException(e);
            } finally{
                if(inputStream != null){
                    inputStream.close();
                }
            }
        }
        return refs;
    }

}
