package com.ultracash.upi.api.resources;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultracash.persistence.RedisPersistence;
import com.ultracash.upi.api.constants.Constants;
import com.ultracash.upi.api.services.UserService;
import com.ultracash.upi.models.Ack;
import com.ultracash.upi.models.ReqBalEnq;
import com.ultracash.upi.models.ReqListKeys;
import com.ultracash.upi.models.ReqOtp;
import com.ultracash.upi.models.ReqPay;
import com.ultracash.upi.models.ReqRegMob;
import com.ultracash.upi.models.RespBalEnq;
import com.ultracash.upi.utility.HttpUtil;
import com.ultracash.upi.utility.OkHttpUtil;
import com.ultracash.upi.utility.SignatureGenerationUtil;
import com.ultracash.upi.utility.SignatureUtil;

@Path(ResourceURL.REQUEST)
public class RequestResource {

	private static final Logger logger = LoggerFactory
			.getLogger(RequestResource.class);

	private static final long timeout_in_milli_sec = 3000L;
	private static final long thread_wait_time_in_milli_sec = 500L;
	private static final long retry_loop_count = (timeout_in_milli_sec / thread_wait_time_in_milli_sec);

	@GET
	@Path("/GetXMLPayLoad")
	@Produces(MediaType.APPLICATION_JSON)
	public static JSONObject reqListKeys() {
		String response = null;
		try {
			JSONObject json = new JSONObject();

			String txnId = "UPLK" + System.currentTimeMillis();
			json.put("txnId", txnId);
			json.put("refId", "R" + txnId);
			json.put("txnType", "LIST_KEYS");

			ReqListKeys reqListKeys = UserService.getReqListKeys(json, false,
					false);
			String txnIdReturned = reqListKeys.getTxn().getId();
			String url = Constants.reqListKeysURL + txnIdReturned;
			SignatureUtil<ReqListKeys> signatureUtil = new SignatureUtil<ReqListKeys>(
					ReqListKeys.class);
			String xmlString = signatureUtil.getSignedXMLString(reqListKeys);
			logger.info("ReqListKeys: " + xmlString);
			response = HttpUtil.postXMLString(url, xmlString);
			logger.info("Response: " + response);
			String error = null;
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Ack.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(response);
				Ack ack = (Ack) unmarshaller.unmarshal(reader);
				error = ack.getErr();
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boolean responseRecieved = StringUtils.isNotEmpty(error);
			int counter = 0;
			while (!responseRecieved) {
				try {
					Thread.sleep(thread_wait_time_in_milli_sec);
					response = RedisPersistence.getInstance().getString(
							"RespListKeys" + txnIdReturned);
					logger.info("Response :" + response);
					if (response != null) {
						break;
					} else {
						counter++;
					}
					logger.info("Counter :" + counter);
					if (counter > retry_loop_count) {
						break;
					}
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e));
			logger.info("Error Occured");
		}
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("xmlPayLoad", response);
		JSONObject jsonObject = new JSONObject(responseMap);
		return jsonObject;
	}

	@POST
	@Path("/RequestOTP")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static JSONObject reqOTP(JSONObject request) {
		String response = null;
		try {

			JSONObject json = new JSONObject();

			String txnId = "UPRO" + System.currentTimeMillis();
			json.put("txnId", txnId);
			json.put("refId", "R" + txnId);
			json.put("txnType", "ReqOtp");
			json.put("msgId", "M" + txnId);
			json.put("appId", request.getString("appId"));
			json.put("phone", request.getString("phone"));
			json.put("geocode", request.getString("geocode"));
			json.put("ipaddress", request.getString("ipaddress"));
			json.put("type", request.getString("type"));
			json.put("os", request.getString("os"));
			json.put("deviceId", request.getString("deviceId"));
			json.put("location", request.getString("location"));
			
			RedisPersistence.getInstance().putString("ReqOtp" + txnId,
					json.toString());

			ReqOtp reqRegMob = UserService.reqOTP(json);
			String txnIdReturned = reqRegMob.getTxn().getId();
			String url = Constants.reqOtpURL + txnIdReturned;
			SignatureUtil<ReqOtp> signatureUtil = new SignatureUtil<ReqOtp>(
					ReqOtp.class);
			String xmlString = signatureUtil.getSignedXMLString(reqRegMob);
			logger.info("RespOtp: " + xmlString);
			response = OkHttpUtil.postSSLXML(url, xmlString);
			logger.info("Response (RespOtp): " + response);
			String error = null;
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Ack.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(response);
				Ack ack = (Ack) unmarshaller.unmarshal(reader);
				error = ack.getErr();
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boolean responseRecieved = StringUtils.isNotEmpty(error);
			int counter = 0;
			while (!responseRecieved) {
				try {
					Thread.sleep(thread_wait_time_in_milli_sec);
					response = RedisPersistence.getInstance().getString(
							"RespOtp" + txnIdReturned);
					logger.info("Response(RespOtp) :" + response);
					if (response != null) {
						break;
					} else {
						counter++;
					}
					logger.info("Counter(RespOtp) :" + counter);
					if (counter > retry_loop_count) {
						break;
					}
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e));
			logger.info("Error Occured");
		}
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", response);
		JSONObject jsonObject = new JSONObject(responseMap);
		return jsonObject;
	}

	@POST
	@Path("/RegisterMobile")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static JSONObject registerMobile(JSONObject request) {
		String response = null;
		try {

			JSONObject json = new JSONObject();
			String txnId = null;
			try {
				if (StringUtils.isNotEmpty(request.getString("txnId"))) {
					txnId = request.getString("txnId");
				}
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
				txnId = "UPRM" + System.currentTimeMillis();
			}

			json.put("txnId", txnId);
			json.put("refId", "R" + txnId);
			json.put("txnType", "ReqRegMob");
			json.put("msgId", "M" + txnId);
			json.put("appId", request.getString("appId"));
			json.put("phone", request.getString("phone"));
			json.put("geocode", request.getString("geocode"));
			json.put("ipaddress", request.getString("ipaddress"));
			json.put("type", request.getString("type"));
			json.put("os", request.getString("os"));
			json.put("credType", request.getString("credType"));
			json.put("credSubType", request.getString("credSubType"));
			json.put("credDataValue", request.getString("credDataValue"));
			json.put("deviceId", request.getString("deviceId"));
			json.put("location", request.getString("location"));
			
			RedisPersistence.getInstance().putString("ReqRegMob" + txnId,
					json.toString());

			ReqRegMob reqRegMob = UserService.getRegisterMobile(json);
			String txnIdReturned = reqRegMob.getTxn().getId();
			String url = Constants.reqRegMobURL + txnIdReturned;
			SignatureUtil<ReqRegMob> signatureUtil = new SignatureUtil<ReqRegMob>(
					ReqRegMob.class);
			String xmlString = signatureUtil.getSignedXMLString(reqRegMob);
			logger.info("ReqRegMob: " + xmlString);
			response = OkHttpUtil.postSSLXML(url, xmlString);
			logger.info("Response (ReqRegMob): " + response);
			String error = null;
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Ack.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(response);
				Ack ack = (Ack) unmarshaller.unmarshal(reader);
				error = ack.getErr();
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boolean responseRecieved = StringUtils.isNotEmpty(error);
			int counter = 0;
			while (!responseRecieved) {
				try {
					Thread.sleep(thread_wait_time_in_milli_sec);
					response = RedisPersistence.getInstance().getString(
							"RespRegMob" + txnIdReturned);
					logger.info("Response(RespRegMob) :" + response);
					if (response != null) {
						break;
					} else {
						counter++;
					}
					logger.info("Counter(RespRegMob) :" + counter);
					if (counter > retry_loop_count) {
						break;
					}
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e));
			logger.info("Error Occured");
		}
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", response);
		JSONObject jsonObject = new JSONObject(responseMap);
		return jsonObject;
	}

	@POST
	@Path("/BalanceEnquiry")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static JSONObject balanceEnquiry(JSONObject request) {
		String response = null;
		try {

			JSONObject json = new JSONObject();

			String txnId = null;
			try {
				if (StringUtils.isNotEmpty(request.getString("txnId"))) {
					txnId = request.getString("txnId");
				}
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
				txnId = "UPBE" + System.currentTimeMillis();
			}
			json.put("txnId", txnId);
			json.put("refId", "R" + txnId);
			json.put("txnType", "ReqRegMod");
			json.put("msgId", "M" + txnId);
			json.put("appId", request.getString("appId"));
			json.put("phone", request.getString("phone"));
			json.put("geocode", request.getString("geocode"));
			json.put("ipaddress", request.getString("ipaddress"));
			json.put("type", request.getString("type"));
			json.put("os", request.getString("os"));
			json.put("credType", request.getString("credType"));
			json.put("credSubType", request.getString("credSubType"));
			json.put("credDataValue", request.getString("credDataValue"));
			json.put("deviceId", request.getString("deviceId"));
			json.put("location", request.getString("location"));
			
			RedisPersistence.getInstance().putString("ReqBalEnq" + txnId,
					json.toString());
			ReqBalEnq reqBalEnq = UserService.getBalance(json);
			String txnIdReturned = reqBalEnq.getTxn().getId();
			String url = Constants.reqBalEnqURL + txnIdReturned;
			SignatureUtil<ReqBalEnq> signatureUtil = new SignatureUtil<ReqBalEnq>(
					ReqBalEnq.class);
			String xmlString = signatureUtil.getSignedXMLString(reqBalEnq);
			logger.info("ReqBalEnq: " + xmlString);
			response = OkHttpUtil.postSSLXML(url, xmlString);
			logger.info("Response (ReqBalEnq): " + response);
			String error = null;
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Ack.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(response);
				Ack ack = (Ack) unmarshaller.unmarshal(reader);
				error = ack.getErr();
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boolean responseRecieved = StringUtils.isNotEmpty(error);
			int counter = 0;
			while (!responseRecieved) {
				try {
					Thread.sleep(thread_wait_time_in_milli_sec);
					response = RedisPersistence.getInstance().getString(
							"RespBalEnq" + txnIdReturned);
					logger.info("Response(RespBalEnq) :" + response);
					if (response != null) {
						break;
					} else {
						counter++;
					}
					logger.info("Counter(RespBalEnq) :" + counter);
					if (counter > retry_loop_count) {
						break;
					}
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e));
			logger.info("Error Occured");
		}
		String virtualAddress = null, balance = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(RespBalEnq.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(response);
			RespBalEnq ReqOtp = (RespBalEnq) unmarshaller.unmarshal(reader);
			virtualAddress = ReqOtp.getPayer().getAddr();
			balance = ReqOtp.getPayer().getBal().getData().getValue();
			if (balance != null) {
				balance = SignatureGenerationUtil.decrypt(balance);
			}
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", response);
		if (StringUtils.isNumericSpace(balance)) {
			responseMap.put("balance", balance);
		}
		if (StringUtils.isNotBlank(virtualAddress)) {
			responseMap.put("virtualAddress", virtualAddress);
		}
		JSONObject jsonObject = new JSONObject(responseMap);
		return jsonObject;
	}

	@POST
	@Path("/RequestPay")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public static JSONObject requestPay(JSONObject request) {
		String response = null;
		try {

			JSONObject json = new JSONObject();

			String txnId = null;
			try {
				if (StringUtils.isNotEmpty(request.getString("txnId"))) {
					txnId = request.getString("txnId");
				}
			} catch (Exception e) {
				logger.info(ExceptionUtils.getStackTrace(e));
				txnId = "UPRP" + System.currentTimeMillis();
			}
			json.put("txnId", txnId);
			json.put("refId", "R" + txnId);
			json.put("txnType", "ReqPay");
			json.put("msgId", "M" + txnId);
			json.put("appId", request.getString("appId"));
			json.put("phone", request.getString("phone"));
			json.put("geocode", request.getString("geocode"));
			json.put("ipaddress", request.getString("ipaddress"));
			json.put("type", request.getString("type"));
			json.put("location", request.getString("location"));
			json.put("os", request.getString("os"));
			json.put("credType", request.getString("credType"));
			json.put("credSubType", request.getString("credSubType"));
			json.put("credDataValue", request.getString("credDataValue"));
			json.put("deviceId", request.getString("deviceId"));
			json.put("txnAmt", request.getString("txnAmt"));
			try{
				json.put("payee_phone", request.getString("payee_phone"));
			}catch(Exception e){
				logger.error(ExceptionUtils.getStackTrace(e));
			}

			ReqPay reqPay = UserService.getReqPay(json);
			String txnIdReturned = reqPay.getTxn().getId();
			String url = Constants.reqPayURL + txnIdReturned;
			SignatureUtil<ReqPay> signatureUtil = new SignatureUtil<ReqPay>(
					ReqPay.class); 
			String xmlString = signatureUtil.getSignedXMLString(reqPay);
			logger.info("ReqPay: " + xmlString);
			response = OkHttpUtil.postSSLXML(url, xmlString);
			logger.info("Response (ReqPay): " + response);
			String error = null;
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Ack.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(response);
				Ack ack = (Ack) unmarshaller.unmarshal(reader);
				error = ack.getErr();
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boolean responseRecieved = StringUtils.isNotEmpty(error);
			int counter = 0;
			while (!responseRecieved) {
				try {
					Thread.sleep(thread_wait_time_in_milli_sec);
					response = RedisPersistence.getInstance().getString(
							"RespPay" + txnIdReturned);
					logger.info("Response(RespPay) :" + response);
					if (response != null) {
						break;
					} else {
						counter++;
					}
					logger.info("Counter(RespPay) :" + counter);
					if (counter > retry_loop_count) {
						break;
					}
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e));
			logger.info("Error Occured");
		}
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", response);
		JSONObject jsonObject = new JSONObject(responseMap);
		return jsonObject;
	}
}
