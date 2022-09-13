package org.apache.cxf.binding.soap.interceptor;

import org.apache.cxf.binding.soap.SoapBindingConstants;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.model.SoapOperationInfo;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.service.model.*;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import javax.xml.namespace.QName;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SOAPActionWithoutQuotesTest {
    private IMocksControl control;
    private SoapPreProtocolOutInterceptor interceptor;

    @Before
    public void setUp() {
        control = EasyMock.createNiceControl();
        interceptor = new SoapPreProtocolOutInterceptor();
    }

    @Test
    public void testRequestorOutboundSoapAction() throws Exception {
        SoapMessage message = setUpMessage();
        Map<String, List<String>> transportHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        List<String> headerList = new ArrayList<>();
        headerList.add("http://foo/bar/SEI/opReq");
        transportHeaders.put(SoapBindingConstants.SOAP_ACTION,headerList);
        message.put(Message.PROTOCOL_HEADERS,transportHeaders);
        interceptor.handleMessage(message);
        control.verify();

        Map<String, List<String>> reqHeaders
                = CastUtils.cast((Map<?, ?>)message.get(Message.PROTOCOL_HEADERS));
        assertNotNull(reqHeaders);
        List<String> soapaction = reqHeaders.get("soapaction");
        assertTrue(null != soapaction && soapaction.size() == 1);
        assertEquals("\"http://foo/bar/SEI/opReq\"", soapaction.get(0));
    }

    private SoapMessage setUpMessage() throws Exception {

        SoapMessage message = new SoapMessage(new MessageImpl());
        Exchange exchange = new ExchangeImpl();
        BindingOperationInfo bop = setUpBindingOperationInfo("http://foo/bar",
                "opReq",
                "opResp",
                SOAPActionWithoutQuotesTest.SEI.class.getMethod("op", new Class[0]));
        SoapOperationInfo sop = new SoapOperationInfo();
        sop.setAction("http://foo/bar/SEI/opReq");
        bop.addExtensor(sop);
        exchange.put(BindingOperationInfo.class, bop);
        message.setExchange(exchange);
        message.put(Message.REQUESTOR_ROLE, Boolean.TRUE);

        control.replay();
        return message;
    }

    private BindingOperationInfo setUpBindingOperationInfo(String nsuri,
                                                           String opreq,
                                                           String opresp,
                                                           Method method) {
        ServiceInfo si = new ServiceInfo();
        InterfaceInfo iinf = new InterfaceInfo(si,
                new QName(nsuri, method.getDeclaringClass().getSimpleName()));
        OperationInfo opInfo = iinf.addOperation(new QName(nsuri, method.getName()));
        opInfo.setProperty(Method.class.getName(), method);
        opInfo.setInput(opreq, opInfo.createMessage(new QName(nsuri, opreq), MessageInfo.Type.INPUT));
        opInfo.setOutput(opresp, opInfo.createMessage(new QName(nsuri, opresp), MessageInfo.Type.INPUT));

        return new BindingOperationInfo(null, opInfo);
    }

    private interface SEI {
        String op();
    }

}
