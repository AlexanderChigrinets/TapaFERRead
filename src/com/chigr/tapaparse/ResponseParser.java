//=====================================================================
// project:   Informatica MDM Hub
//---------------------------------------------------------------------
// copyright: Informatica (c) 2003-2016.  All rights reserved.
//=====================================================================

package com.chigr.tapaparse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.chigr.tapatalk.MethodResponse;
import com.chigr.tapatalk.MethodResponseBase;
import com.chigr.tapatalk.MethodResponseFault;
import com.chigr.tapatalk.TapaArray;
import com.chigr.tapatalk.TapaBase64;
import com.chigr.tapatalk.TapaBaseValue;
import com.chigr.tapatalk.TapaBoolean;
import com.chigr.tapatalk.TapaDateTime;
import com.chigr.tapatalk.TapaDouble;
import com.chigr.tapatalk.TapaFault;
import com.chigr.tapatalk.TapaInt;
import com.chigr.tapatalk.TapaString;
import com.chigr.tapatalk.TapaStruct;
import com.chigr.tapatalk.TapaType;
import com.chigr.tapatalk.TapaValue;

/**
 * @author achigrin
 * @since 21.05.2016.
 */
public class ResponseParser extends DefaultHandler {
    private MethodResponseBase result;
    private StringBuffer tmp;
    private Stack stack = new Stack();

    public MethodResponseBase parse(String xml)
            throws ParserConfigurationException, IOException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes("utf-8"));
        try {
            saxParser.parse(bis, this);
        } catch (SAXException e) {
            result = null;
            throw e;
        }
        return result;
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start parsing XML RPC Response");
        result = new MethodResponse();
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End parsing XML RPC Response");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (localName) {
            case "struct":
                stack.push(new TapaStruct());
                break;
            case "array":
                stack.push(new TapaArray());
                break;
            case "value":
                stack.push(new TapaValue());
                break;
        }
        tmp = new StringBuffer();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // Here we do something on a closed tag
        switch (localName) {
            case "int":
            case "i4":
                stack.push(new TapaInt(tmp.toString()));
                break;
            case "dateTime.iso8601":
                stack.push(new TapaDateTime(tmp.toString()));
                break;
            case "boolean":
                stack.push(new TapaBoolean(tmp.toString()));
                break;
            case "double":
                stack.push(new TapaDouble(tmp.toString()));
                break;
            case "string":
                stack.push(new TapaString(tmp.toString()));
                break;
            case "param":
                completeParam();
                break;
            case "params":
                break;
            case "base64":
                try {
                    stack.push(new TapaBase64(tmp.toString()));
                } catch (Exception e) {
                    throw new SAXException("Error parsing base64: "+tmp.toString(), e);
                }
                break;
            case "name":
                stack.push(tmp.toString());
                break;
            case "struct":
            case "data":
                break;
            case "array":
                completeArray();
                break;
            case "value":
                completeValue();
                break;
            case "member":
                completeMember();
                break;
            case "fault":
                completeFault();
                break;
            case "methodResponse":
                completeMethodResponse();
                break;
            default:
                throw new SAXException("Unknown closing tag "+localName);
        }
    }

    private void completeArray() throws SAXException {
        // At this point in time on top of a stack we must have TapaArray followed by several TapaValue
        // All tapa Value must be placed into array
        List<TapaValue> values = new ArrayList<>();
        Object item = stack.pop();
        while (item instanceof TapaValue) {
            values.add((TapaValue) item);
            item = stack.pop();
        }
        if (item instanceof TapaArray) {
            TapaArray array = (TapaArray) item;
            for (int i = values.size() - 1; i > -1; i--) {
                array.addItem(values.get(i).getValue());
            }
            stack.push(array);
        }
        else throw new SAXException("Array was expected but found "+item.getClass().getName());
    }


    private void completeMethodResponse() throws SAXException {
    }

    private void completeParam() {
        TapaValue value = (TapaValue) stack.pop();
        ((MethodResponse)result).add(value.getValue());
    }

    private void completeFault() throws SAXException {
        try {
            TapaValue struct = (TapaValue) stack.pop();
            result = new MethodResponseFault(new TapaFault((TapaStruct) struct.getValue()));
        } catch (ClassCastException e) {
            throw new SAXException("Fault assumes Struct", e);
        }
    }

    private void completeValue() throws SAXException {
        Object item = stack.peek();
        if (item instanceof TapaValue) {
            // Here we deal with a default string type
            stack.push(new TapaString(tmp.toString()));
        }
        try {
            TapaBaseValue value = (TapaBaseValue) stack.pop();
            TapaValue tv = (TapaValue) stack.peek();
            tv.setValue(value);

        } catch (ClassCastException e) {
            throw new SAXException("Unexpected type while parsing \"value\"", e);
        }
    }

    private void completeMember() throws SAXException {
        // Here we expect string and TapaBaseValue on a stack
        TapaValue value;
        String name;
        try {
            value = (TapaValue) stack.pop();

            name = (String) stack.pop();

            TapaStruct struct = (TapaStruct) stack.pop();
            struct.addMember(name, value.getValue());
            stack.push(struct);

        } catch (ClassCastException cce) {
            throw new SAXException("Unexpected class on a stack while finalizing Member", cce);
        }


    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tmp.append(ch, start, length);
    }
}
