package com.ultracash.upi.api.resources;

import java.io.StringReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultracash.upi.api.constants.Constants;
import com.ultracash.upi.api.services.UserService;
import com.ultracash.upi.models.Ack;
import com.ultracash.upi.models.ReqBalEnq;
import com.ultracash.upi.models.ReqListAccount;
import com.ultracash.upi.models.ReqListKeys;
import com.ultracash.upi.models.ReqListPsp;
import com.ultracash.upi.utility.HttpUtil;
import com.ultracash.upi.utility.SignatureUtil;

@Path(ResourceURL.USER)
public class UserResource {

	private static final Logger logger = LoggerFactory
			.getLogger(UserResource.class);

	
	@POST
	@Path("/ReqListKeys")
	public static String reqListKeys() {
		ReqListKeys reqListKeys = UserService.getReqListKeys();
		String txnId = reqListKeys.getTxn().getId();
		String url = Constants.reqListKeysURL + txnId;
		SignatureUtil<ReqListKeys> signatureUtil = new SignatureUtil<ReqListKeys>(
				ReqListKeys.class);
		String xmlString = signatureUtil.getSignedXMLString(reqListKeys);
		logger.info("ReqListKeys: " + xmlString);
		String response = HttpUtil.postXMLString(url, xmlString);
		logger.info("Response: " + response);
		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/GetToken")
	public static String reqListKeys(JSONObject json) {
		ReqListKeys reqListKeys = UserService
				.getReqListKeys(json, false, false);
		String txnId = reqListKeys.getTxn().getId();
		String url = Constants.reqListKeysURL + txnId;
		SignatureUtil<ReqListKeys> signatureUtil = new SignatureUtil<ReqListKeys>(
				ReqListKeys.class);
		String xmlString = signatureUtil.getSignedXMLString(reqListKeys);
		logger.info("ReqListKeys: " + xmlString);
		String response = HttpUtil.postXMLString(url, xmlString);
		logger.info("Response: " + response);
		
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Ack.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader("xml string here");
			Ack ack = (Ack) unmarshaller.unmarshal(reader);
			logger.info(ack.toString());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@GET
	// @POST
	// @Consumes(MediaType.APPLICATION_JSON)
	@Path("/GetBalance")
	public static String getBalance() {
		ReqBalEnq reqListKeys = UserService.getAccountBalance();
		String txnId = reqListKeys.getTxn().getId();
		String url = Constants.reqBalEnqURL + txnId;
		SignatureUtil<ReqBalEnq> signatureUtil = new SignatureUtil<ReqBalEnq>(
				ReqBalEnq.class);
		String xmlString = signatureUtil.getSignedXMLString(reqListKeys);
		logger.info("ReqListKeys: " + xmlString);
		String response = HttpUtil.postXMLString(url, xmlString);
		logger.info("Response: " + response);
		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/GetTokenEncoded")
	public static String reqListKeysEncoded(JSONObject json) {
		ReqListKeys reqListKeys = UserService.getReqListKeys(json, true, false);
		String txnId = reqListKeys.getTxn().getId();
		String url = Constants.reqListKeysURL + txnId;
		SignatureUtil<ReqListKeys> signatureUtil = new SignatureUtil<ReqListKeys>(
				ReqListKeys.class);
		String xmlString = signatureUtil.getSignedXMLString(reqListKeys);
		logger.info("ReqListKeys: " + xmlString);
		String response = HttpUtil.postXMLString(url, xmlString);
		logger.info("Response: " + response);
		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/GetTokenHex")
	public static String reqListKeysEncodedHex(JSONObject json) {
		ReqListKeys reqListKeys = UserService.getReqListKeys(json, false, true);
		String txnId = reqListKeys.getTxn().getId();
		String url = Constants.reqListKeysURL + txnId;
		SignatureUtil<ReqListKeys> signatureUtil = new SignatureUtil<ReqListKeys>(
				ReqListKeys.class);
		String xmlString = signatureUtil.getSignedXMLString(reqListKeys);
		logger.info("ReqListKeys: " + xmlString);
		String response = HttpUtil.postXMLString(url, xmlString);
		logger.info("Response: " + response);
		return response;
	}

	@POST
	@Path("/ReqListKeysEncoded")
	public static String reqListKeysEncoded() {
		ReqListKeys reqListKeys = UserService.getReqListKeys(true, false);
		String txnId = reqListKeys.getTxn().getId();
		String url = Constants.reqListKeysURL + txnId;
		SignatureUtil<ReqListKeys> signatureUtil = new SignatureUtil<ReqListKeys>(
				ReqListKeys.class);
		String xmlString = signatureUtil.getSignedXMLString(reqListKeys);
		logger.info("ReqListKeys: " + xmlString);
		String response = HttpUtil.postXMLString(url, xmlString);
		logger.info("Response: " + response);
		return response;
	}

	@POST
	@Path("/ReqListKeysEncodedHex")
	public static String reqListKeysEncodedHex() {
		ReqListKeys reqListKeys = UserService.getReqListKeys(false, true);
		String txnId = reqListKeys.getTxn().getId();
		String url = Constants.reqListKeysURL + txnId;
		SignatureUtil<ReqListKeys> signatureUtil = new SignatureUtil<ReqListKeys>(
				ReqListKeys.class);
		String xmlString = signatureUtil.getSignedXMLString(reqListKeys);
		logger.info("ReqListKeys: " + xmlString);
		String response = HttpUtil.postXMLString(url, xmlString);
		logger.info("Response: " + response);
		return response;
	}

	@POST
	@Path("/ReqListPsp")
	public static String reqListPsp() {
		ReqListPsp reqListPsp = UserService.getReqListPsp();
		String txnId = reqListPsp.getTxn().getId();
		String url = Constants.reqListPspURL + txnId;
		SignatureUtil<ReqListPsp> signatureUtil = new SignatureUtil<ReqListPsp>(
				ReqListPsp.class);
		String xmlString = signatureUtil.getSignedXMLString(reqListPsp);
		logger.info("ReqListPsp: " + xmlString);
		String response = HttpUtil.postXMLString(url, xmlString);
		logger.info("Response: " + response);
		return response;
	}

	@POST
	@Path("/ReqListAccount")
	public static String reqListAccount() {
		ReqListAccount reqListAccount = UserService.getReqListAccount();
		String txnId = reqListAccount.getTxn().getId();
		String url = Constants.reqListAccountURL + txnId;
		SignatureUtil<ReqListAccount> signatureUtil = new SignatureUtil<ReqListAccount>(
				ReqListAccount.class);
		String xmlString = signatureUtil.getSignedXMLString(reqListAccount);
		logger.info("ReqListAccount: " + xmlString);
		String response = HttpUtil.postXMLString(url, xmlString);
		logger.info("Response: " + response);
		return response;
	}

	public static void main(String[] args) {
		UserResource.reqListKeys();
	}
}
