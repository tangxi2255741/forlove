package com.txr.forlove.common.utils.orderXml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * 关注点解析。
 * 
 * @since 2015年6月8日
 * @author yanglei
 */
@NotThreadSafe
public class WatchedNodeHandler extends DefaultHandler {
	private Map<String, WatchedNode> keySet = Collections.emptyMap();

	private final Map<String, Object> map = new HashMap<String, Object>();
	private final StringBuilder val = new StringBuilder();
    private String childKey = null;
	public WatchedNodeHandler(Map<String, WatchedNode> keyMap) {
		super();
		this.keySet = keyMap;
	}

	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		val.append(new String(ch, start, length));
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (keySet.containsKey(qName)) {
			WatchedNode info = keySet.get(qName);
			if(!map.containsKey(info.getKeyName())){
				map.put(info.getKeyName(), info.getFormatter().formate(val.toString()));
			}
		}
        //  取xml里面的子节点，目前只取发票类型
        else if("Key".equals(qName)){
            childKey = val.toString();
        } else if("Val".equals(qName)){
            setMapByVal();
        }
		val.setLength(0);
	}

    private void setMapByVal(){
        if("IdInvoiceType".equals(childKey)){
            map.put("invoiceType", Integer.parseInt(val.toString()));
        }else if("IdInvoicePutType".equals(childKey)){
            map.put("IdInvoicePutType", Integer.parseInt(val.toString()));
        }else if("Userlevel".equals(childKey)){
            map.put("Userlevel", Integer.parseInt(val.toString()));
        }else if("invoice_kaipiaofangshi".equals(childKey)){
            map.put("invoice_kaipiaofangshi", Integer.parseInt(val.toString()));
        }else if("generalInvoiceConsignee".equals(childKey)){
            map.put("generalInvoiceConsignee",val.toString());
        }else if("IsPutBookInvoice".equals(childKey)){
            map.put("IsPutBookInvoice",val.toString());
        }

    }

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		// override super method!
	}

	public Map<String, Object> getResult() {
		return map;
	}


	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		val.setLength(0);
	}


}

