package com.ultracash.upi.api.resources;

import java.io.StringReader;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultracash.upi.api.constants.Constants;
import com.ultracash.upi.api.services.UserService;
import com.ultracash.upi.models.Ack;
import com.ultracash.upi.models.ObjectFactory;
import com.ultracash.upi.models.ReqPay;
import com.ultracash.upi.models.RespPay;
import com.ultracash.upi.utility.OkHttpUtil;
import com.ultracash.upi.utility.SignatureUtil;

@Path(ResourceURL.REQUEST_PAY)
public class RequestPay {

	private static final Logger logger = LoggerFactory
			.getLogger(RequestPay.class);

	@POST
	@Path("/urn:txnid:{tnxId}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public static Ack respHbt(@PathParam("tnxId") String txnid, String X) {
		logger.info("ReqPay: " + X);

		String msgId = null;
		ReqPay reqPay = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ReqPay.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(X);
			 reqPay = (ReqPay) unmarshaller.unmarshal(reader);
			msgId = reqPay.getHead().getMsgId().toString();
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObjectFactory objectFactory = new ObjectFactory();
		Ack ack = objectFactory.createAck();
		ack.setTs(Constants.sdf.format(new Date()));
		ack.setApi("ReqPay");
		try {
			ack.setReqMsgId(msgId.toString());
			logger.info(" Ack ReqPay: " + ack);
			new Thread(new ResponsePaySender(reqPay)).start();
		} catch (Exception e) {
			e.printStackTrace();
			ack.setErr("Message Id not available");
		}

		return ack;
	}
}

class ResponsePaySender implements Runnable {
	private static final Logger logger = LoggerFactory
			.getLogger(ResponsePaySender.class);
	private ReqPay reqPay = null;

	public ResponsePaySender(ReqPay reqPay) {
		this.reqPay = reqPay;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			Thread.sleep(100L);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("Posting the Resp again");
		RespPay respPay = UserService.respPay(reqPay);
		String txnIdReturned = respPay.getTxn().getId();
		String url = Constants.respPayURL + txnIdReturned;
		SignatureUtil<RespPay> signatureUtil = new SignatureUtil<RespPay>(
				RespPay.class);
		String xmlString = signatureUtil.getSignedXMLString(respPay);
		logger.info("RespPay: " + xmlString);
		String response = OkHttpUtil.postSSLXML(url, xmlString);

		logger.info("Response (RespPay): " + response);
		return;

	}
}