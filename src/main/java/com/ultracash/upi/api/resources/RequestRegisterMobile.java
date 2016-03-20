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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultracash.persistence.RedisPersistence;
import com.ultracash.upi.api.constants.Constants;
import com.ultracash.upi.api.services.UserService;
import com.ultracash.upi.models.Ack;
import com.ultracash.upi.models.ObjectFactory;
import com.ultracash.upi.models.ReqRegMob;
import com.ultracash.upi.models.RespRegMob;
import com.ultracash.upi.utility.OkHttpUtil;
import com.ultracash.upi.utility.SignatureUtil;

@Path(ResourceURL.REQUEST_REGISTER_MOBILE)
public class RequestRegisterMobile {

	private static final Logger logger = LoggerFactory
			.getLogger(RequestRegisterMobile.class);

	@POST
	@Path("/urn:txnid:{tnxId}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public static Ack respHbt(@PathParam("tnxId") String txnid, String X) {
		// RedisPersistence.getInstance().putString("ReqRegMob" + txnid, X);
		logger.info("ReqRegMob: " + X);

		String msgId = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ReqRegMob.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(X);
			ReqRegMob ReqRegMob = (ReqRegMob) unmarshaller.unmarshal(reader);
			msgId = ReqRegMob.getHead().getMsgId().toString();
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObjectFactory objectFactory = new ObjectFactory();
		Ack ack = objectFactory.createAck();
		ack.setTs(Constants.sdf.format(new Date()));
		ack.setApi("ReqRegMob");
		try {
			ack.setReqMsgId(msgId.toString());
			logger.info(" Ack ReqRegMob: " + ack);
			new Thread(new ResponseRegMobSender(txnid,msgId.toString())).start();
		} catch (Exception e) {
			e.printStackTrace();
			ack.setErr("Message Id not available");
		}

		
		return ack;
	}
}

class ResponseRegMobSender implements Runnable {
	private static final Logger logger = LoggerFactory
			.getLogger(ResponseOtpSender.class);
	private String txnid = null;
	private String msgid = null;

	public ResponseRegMobSender(String txnid,String msgid) {
		this.txnid = txnid;
		this.msgid = msgid;
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
		String jsonStr = RedisPersistence.getInstance().getString(
				"ReqRegMob" + txnid);
		logger.info("Json to be used for creating ReqRegMob" + jsonStr);
		JSONObject json = null;
		try {
			json = new JSONObject(jsonStr);
			System.out.println(jsonStr);
			//String txnId = "UPRO" + System.currentTimeMillis();
			json.put("txnId", txnid);
			json.put("msgId", msgid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RespRegMob respOTP = UserService.respRegMob(json);
		String txnIdReturned = respOTP.getTxn().getId();
		String url = Constants.respRegMobURL + txnIdReturned;
		SignatureUtil<RespRegMob> signatureUtil = new SignatureUtil<RespRegMob>(
				RespRegMob.class);
		String xmlString = signatureUtil.getSignedXMLString(respOTP);
		logger.info("RespRegMob: " + xmlString);
		String response = OkHttpUtil.postSSLXML(url, xmlString);

		logger.info("Response (RespRegMob): " + response);
		return;

	}
}