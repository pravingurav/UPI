package com.ultracash.upi.api.services;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.ultracash.upi.api.constants.Constants;
import com.ultracash.upi.models.AccountDetailType;
import com.ultracash.upi.models.AccountType;
import com.ultracash.upi.models.AddressType;
import com.ultracash.upi.models.AmountType;
import com.ultracash.upi.models.CredSubType;
import com.ultracash.upi.models.CredType;
import com.ultracash.upi.models.CredsType;
import com.ultracash.upi.models.CredsType.Cred;
import com.ultracash.upi.models.CredsType.Cred.Data;
import com.ultracash.upi.models.DeviceTagNameType;
import com.ultracash.upi.models.DeviceType;
import com.ultracash.upi.models.HeadType;
import com.ultracash.upi.models.IdentityConstant;
import com.ultracash.upi.models.IdentityType;
import com.ultracash.upi.models.InfoType;
import com.ultracash.upi.models.LinkType;
import com.ultracash.upi.models.MetaTagNameType;
import com.ultracash.upi.models.MobRegDetailsNameType;
import com.ultracash.upi.models.PayConstant;
import com.ultracash.upi.models.PayTrans;
import com.ultracash.upi.models.PayeeType;
import com.ultracash.upi.models.PayeesType;
import com.ultracash.upi.models.PayerConstant;
import com.ultracash.upi.models.PayerType;
import com.ultracash.upi.models.RatingType;
import com.ultracash.upi.models.Ref;
import com.ultracash.upi.models.RefType;
import com.ultracash.upi.models.ReqBalEnq;
import com.ultracash.upi.models.ReqListAccount;
import com.ultracash.upi.models.ReqListKeys;
import com.ultracash.upi.models.ReqListPsp;
import com.ultracash.upi.models.ReqOtp;
import com.ultracash.upi.models.ReqPay;
import com.ultracash.upi.models.ResultType;
import com.ultracash.upi.models.ReqPay.Meta;
import com.ultracash.upi.models.ReqRegMob;
import com.ultracash.upi.models.RespBalEnq;
import com.ultracash.upi.models.RespBalEnq.Payer;
import com.ultracash.upi.models.RespBalEnq.Payer.Bal;
import com.ultracash.upi.models.RespOtp;
import com.ultracash.upi.models.RespPay;
import com.ultracash.upi.models.RespPay.Resp;
import com.ultracash.upi.models.RespRegMob;
import com.ultracash.upi.models.RespType;
import com.ultracash.upi.models.RiskScoresType;
import com.ultracash.upi.models.RiskScoresType.Score;
import com.ultracash.upi.models.WhiteListedConstant;
import com.ultracash.upi.utility.SignatureGenerationUtil;

public class UserService {
	public static final String delimiter = "|";

	public static ReqListKeys getReqListKeys() {
		return getReqListKeys(false, false);
	}

	public static ReqListKeys getReqListKeys(boolean encodeDeviceIdBase64,
			boolean encodeDeviceIdHex) {
		HeadType head = new HeadType();
		head.setMsgId("" + System.currentTimeMillis());
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		PayTrans txn = new PayTrans();
		txn.setId("" + System.currentTimeMillis());
		txn.setNote("Account provider Listing");
		txn.setType(PayConstant.GET_TOKEN);
		txn.setTs(timestamp);
		txn.setRefId("2834473939393");
		txn.setRefUrl(Constants.refURL);

		Data data = new Data();
		data.setCode("NPCI");
		data.setKi("20150822");

		String deviceIdBase = "352356074698416";
		String deviceId = encodeDeviceIdBase64 ? Base64
				.encodeBase64String(StringUtils.getBytesUtf8(deviceIdBase))
				: (encodeDeviceIdHex ? Hex.encodeHexString(StringUtils
						.getBytesUtf8(deviceIdBase)) : deviceIdBase);
		String mobileNo = "9916525300";
		String appid = "com.example.nirankar.myapplication";
		String challengeBase = "NiynzbEGkYcnJ1fzCELy2zUxSfqkAaPA6ovFljUAT6nFFfcHD7meq/F/yEAZ5EnHR+fLp+6cjgaRw+mRF6KRlB7nD2P4q+mdcaCbGiMuee1gbJdZhRlS4FsC/3pHQHstsoRBEYjyVJ4oAFFnPV9HK3QIMyQun2tg8WIJevo3B5VocNYaKErjHXc47qk7MIfodaTId28Aznos9tUwJhLHXNCzSBBdzGxSM79LZRtA5l93f1fVzu0VRKKoUx8Dfe49qnut3GlsUQMTCCNcLBTiNdrfqd5a3w9JD/x3TUnF894jV88JUqzwZKLGxJy4e8d62V9UxD+Iyy4DF+PmgEI0eA==";
		String challangeEncodedHex = "EsUIUY7aMy5AY+IORHn9IEmkd/WPh4h6tzeq6o7coIEcGoDweR/Ec43C7xR8gtvRKOJGqDIDbQbnU+DjRz7IZD7a6N28r6jWojLlCSBR7Cn+Jwk6P4mnAxH5JfthTVy05O+C8CgTwTAydU+y5QTET1+GsZfh9bMYvl+FsgCPc1+QFeIW78MPvaNwU39BoYfwQ1qtCGp+Wmf6NlAau5RtWXn3koVdM52+2CJPjxnKFV1LIqTNph2oROsHYrjbOcfEmW61AjSyiVmCdQu74yAt+QXhRL7V5Nefzt0UHBQgTOuoumSqDhIH/8t/8I0UbwNfisgqF0v7gW1yi5Ye19+aBg==";
		String challangeEncodedBase64 = "f2TcWD3A2GY+EOvjtIkFKQAYVtyCNgCOrDtQE8vudXzgWfcWrP7Oy5fSSMHq35toYIKtNVgNkHtWETXEo63fXrDT76+95zWmJKlCD0J24vB6U5BOXMmx8YLe+Dg5yha4Usy9wcYrd02gPCOccPpApvkjrQBzAEViaECPcCg8OryqlUgOdVbrD8Sdl8pB4yH3GA3sUhZXZUzJgBpsfyxWUTB0hlJuNISbullxeWdY4f7Acp52F+D/8zYaMfpPXoHtOqD4NwiaRSVi8pFkoBv02aZbZhuMsTIX+dFavBxydUZEl5kI6YHIQmlKemkp+9OR1vi4r7lzVCwsGGtG2SrWBA==";

		String challenge = encodeDeviceIdBase64 ? challangeEncodedBase64
				: (encodeDeviceIdHex ? challangeEncodedHex : challengeBase);
		String decodedValue = deviceId + delimiter + appid + delimiter
				+ mobileNo + delimiter + challenge;
		// String value =
		// Base64.encodeBase64String(StringUtils.getBytesUtf8(decodedValue));
		data.setValue(decodedValue);

		Cred cred = new Cred();
		cred.setType(CredType.CHALLENGE);
		cred.setSubType(CredSubType.INITIAL);
		cred.setData(data);

		CredsType creds = new CredsType();
		creds.getCred().add(cred);

		ReqListKeys reqListKeys = new ReqListKeys();
		reqListKeys.setHead(head);
		reqListKeys.setTxn(txn);
		reqListKeys.setCreds(creds);

		return reqListKeys;
	}

	public static ReqListKeys getReqListKeys(JSONObject json,
			boolean encodeDeviceIdBase64, boolean encodeDeviceIdHex) {
		String txnId = null, refId = null;
		try {
			txnId = json.getString("txnId");
			refId = json.getString("refId");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PayConstant payConstant = null;
		payConstant = PayConstant.LIST_KEYS;

		HeadType head = new HeadType();
		head.setMsgId("UPTL" + System.currentTimeMillis());
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		PayTrans txn = new PayTrans();
		txn.setId(txnId);
		txn.setNote("Getting Token");
		txn.setType(payConstant);
		txn.setTs(timestamp);
		txn.setRefId(refId);
		txn.setRefUrl(Constants.refURL);

		ReqListKeys reqListKeys = new ReqListKeys();
		reqListKeys.setHead(head);
		reqListKeys.setTxn(txn);
		return reqListKeys;
	}

	public static ReqListPsp getReqListPsp() {
		HeadType head = new HeadType();
		head.setMsgId("" + System.currentTimeMillis());
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		PayTrans txn = new PayTrans();
		txn.setId("" + System.currentTimeMillis());
		txn.setNote("Listing PSPs");
		txn.setType(PayConstant.LIST_PSP);
		txn.setTs(timestamp);
		txn.setRefId("283447393sa93");
		txn.setRefUrl(Constants.refURL);

		ReqListPsp reqListPsp = new ReqListPsp();
		reqListPsp.setHead(head);
		reqListPsp.setTxn(txn);

		return reqListPsp;
	}

	public static ReqListAccount getReqListAccount() {
		HeadType head = new HeadType();
		head.setMsgId("" + System.currentTimeMillis());
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		PayTrans txn = new PayTrans();
		txn.setId("" + System.currentTimeMillis());
		txn.setNote("Listing Account");
		txn.setType(PayConstant.LIST_ACCOUNT);
		txn.setTs(timestamp);
		txn.setRefId(txn.getId());
		txn.setRefUrl(Constants.refURL);

		PayerType payerType = new PayerType();
		payerType.setAddr("raja@utplpsp");
		payerType.setName("Raja Mohata");

		payerType.setType(PayerConstant.PERSON);

		ReqListAccount.Link link = new ReqListAccount.Link();
		link.setType(LinkType.MOBILE);
		link.setValue("9886952313");

		ReqListAccount reqListAccount = new ReqListAccount();
		reqListAccount.setHead(head);
		reqListAccount.setTxn(txn);
		reqListAccount.setPayer(payerType);
		reqListAccount.setLink(link);

		return reqListAccount;
	}

	public static ReqBalEnq getAccountBalance() {
		String uniqueId = "" + System.currentTimeMillis();
		HeadType head = new HeadType();
		head.setMsgId(uniqueId);
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		RiskScoresType riskScoresType = new RiskScoresType();
		Score score = new Score();
		score.setProvider("utpl");
		score.setType("TXNRISK");
		score.setValue("0030");
		riskScoresType.getScore().add(score);

		PayTrans txn = new PayTrans();
		txn.setId(uniqueId);
		txn.setNote("Balance Enquiry");
		txn.setType(PayConstant.BAL_ENQ);
		txn.setTs(timestamp);
		txn.setRefId(uniqueId);
		txn.setRefUrl(Constants.refURL);
		txn.setRiskScores(riskScoresType);

		PayerType payerType = new PayerType();
		payerType.setType(PayerConstant.PERSON);
		payerType.setAddr("raja@" + Constants.handle);
		payerType.setName("Raja Mohata");
		payerType.setSeqNum("1");
		payerType.setCode(Constants.code);
		payerType.setInfo(new InfoType());
		payerType.getInfo().setIdentity(new IdentityType());
		payerType.getInfo().getIdentity().setType(IdentityConstant.ACCOUNT);
		payerType.getInfo().getIdentity().setId("12121");
		payerType.getInfo().getIdentity().setVerifiedName("Raja Mohata");
		payerType.getInfo().setRating(new RatingType());
		payerType.getInfo().getRating()
				.setVerifiedAddress(WhiteListedConstant.TRUE);

		DeviceType deviceType = new DeviceType();
		DeviceType.Tag app = new DeviceType.Tag();
		app.setName(DeviceTagNameType.APP);
		app.setValue("com.ultracash.pay");
		deviceType.getTag().add(app);
		DeviceType.Tag mobile = new DeviceType.Tag();
		mobile.setName(DeviceTagNameType.MOBILE);
		mobile.setValue("9916525300");
		deviceType.getTag().add(mobile);
		DeviceType.Tag geocode = new DeviceType.Tag();
		geocode.setName(DeviceTagNameType.GEOCODE);
		geocode.setValue("288177");
		deviceType.getTag().add(geocode);
		DeviceType.Tag location = new DeviceType.Tag();
		location.setName(DeviceTagNameType.LOCATION);
		location.setValue("Mumbai, Maharashtra");
		deviceType.getTag().add(location);
		DeviceType.Tag ip = new DeviceType.Tag();
		ip.setName(DeviceTagNameType.IP);
		ip.setValue("124.7.137.85");
		deviceType.getTag().add(ip);
		DeviceType.Tag type = new DeviceType.Tag();
		type.setName(DeviceTagNameType.TYPE);
		type.setValue("mob");
		deviceType.getTag().add(type);
		DeviceType.Tag id = new DeviceType.Tag();
		id.setName(DeviceTagNameType.ID);
		id.setValue("352356074698416");
		deviceType.getTag().add(id);
		DeviceType.Tag os = new DeviceType.Tag();
		os.setName(DeviceTagNameType.OS);
		os.setValue("android");
		deviceType.getTag().add(os);
		DeviceType.Tag capability = new DeviceType.Tag();
		capability.setName(DeviceTagNameType.CAPABILITY);
		capability.setValue("5200000200010004000639292929292");
		deviceType.getTag().add(capability);
		payerType.setDevice(deviceType);

		AccountType accountType = new AccountType();
		accountType.setAddrType(AddressType.ACCOUNT);
		AccountType.Detail actType = new AccountType.Detail();
		actType.setName(AccountDetailType.ACTYPE);
		actType.setValue("SAVINGS");
		accountType.getDetail().add(actType);
		AccountType.Detail ifsc = new AccountType.Detail();
		ifsc.setName(AccountDetailType.IFSC);
		ifsc.setValue(Constants.IFSC + "1234567");
		accountType.getDetail().add(ifsc);
		AccountType.Detail actNum = new AccountType.Detail();
		actNum.setName(AccountDetailType.ACNUM);
		actNum.setValue("9886952313");
		accountType.getDetail().add(actNum);
		payerType.setAc(accountType);
		CredsType credsType = new CredsType();
		CredsType.Cred cred = new CredsType.Cred();
		cred.setType(CredType.PIN);
		cred.setSubType(CredSubType.MPIN);
		CredsType.Cred.Data data = new CredsType.Cred.Data();
		data.setValue("1.0|nUvI7tm3F5qM0iPZe2dU1kQDzI1NRP75swpZxWPykMxEF8xlrM8O+hdqeDUIvOVFan8iAamMVLCKuFnY+mkKlWDWiBEY/UPlgMqZ3o7/VewCU2uS1Zr023wHkRhD6YvTa/KAl95tm85F7dTdYS8HLKug0b14aw7yLtCMZ3PV3JtSqMW7ppgNeWpxGSL8Wm+yJ2Io09Ls/5cjFnwmoWmofdo0jEO1BdE0LaBXujAFG/CgaYvngjm1w8JfsUbXDfpbWNgiJZLdvU53m+94h9ox97JKc2JUthr3aAjlwPzUwS9HXMk19q5u5fOOnIocbmm13Ge97l8+GW1PVS0zw4ah6Q==");
		data.setCode("NPCI");
		data.setKi("20150822");
		cred.setData(data);
		credsType.getCred().add(cred);
		payerType.setCreds(credsType);

		/*
		 * AmountType amountType = new AmountType(); amountType.setValue("100");
		 * amountType.setCurr("INR"); payerType.setAmount(amountType);
		 * 
		 * PayeesType payeesType = new PayeesType();
		 * 
		 * PayeeType payeeType = new PayeeType();
		 * payeeType.setName("UltraCash"); payeeType.setInfo(new InfoType());
		 * payeeType.getInfo().setIdentity(new IdentityType());
		 * payeeType.getInfo().getIdentity().setType(IdentityConstant.ACCOUNT);
		 * payeeType.getInfo().getIdentity().setId("323131332");
		 * payeeType.getInfo().getIdentity().setVerifiedName("Rajesh Khanna");
		 * payeeType.getInfo().setRating(new RatingType());
		 * payeeType.getInfo().getRating()
		 * .setVerifiedAddress(WhiteListedConstant.TRUE); payeeType.setCode("");
		 * payeeType.setAddr("9035004140@" + Constants.handle);
		 * payeeType.setSeqNum("1"); payeeType.setType(PayerConstant.PERSON);
		 * AccountType payeeAccountType = new AccountType();
		 * payeeAccountType.setAddrType(AddressType.ACCOUNT); AccountType.Detail
		 * payeeActType = new AccountType.Detail();
		 * payeeActType.setName(AccountDetailType.ACTYPE);
		 * payeeActType.setValue("SAVINGS");
		 * payeeAccountType.getDetail().add(payeeActType); AccountType.Detail
		 * payeeIFSC = new AccountType.Detail();
		 * payeeIFSC.setName(AccountDetailType.IFSC);
		 * payeeIFSC.setValue(Constants.IFSC + "1234567");
		 * payeeAccountType.getDetail().add(payeeIFSC); AccountType.Detail
		 * payeeAccountDetails = new AccountType.Detail();
		 * payeeAccountDetails.setName(AccountDetailType.ACNUM);
		 * payeeAccountDetails.setValue("9035004140");
		 * payeeAccountType.getDetail().add(payeeAccountDetails);
		 * payeeType.setAc(payeeAccountType); AmountType payerAmountType = new
		 * AmountType(); payerAmountType.setValue("100");
		 * payerAmountType.setCurr("INR"); payeeType.setAmount(payerAmountType);
		 * payeesType.getPayee().add(payeeType);
		 */
		ReqBalEnq reqPay = new ReqBalEnq();
		reqPay.setHead(head);
		reqPay.setTxn(txn);
		// reqPay.setPayees(payeesType);
		reqPay.setPayer(payerType);
		// reqPay.setMeta(meta);
		return reqPay;
	}

	public static ReqOtp reqOTP(JSONObject json) {
		String txnId_request = null, refId_request = null, msgId_request = null, appid_request = null, phone_request = null, geoCode_request = null, ipaddress_request = null, type_request = null, os_request = null;
		String deviceId_request = null;
		try {
			txnId_request = json.getString("txnId");
			refId_request = json.getString("refId");
			msgId_request = json.getString("msgId");
			appid_request = json.getString("appId");
			phone_request = json.getString("phone");
			geoCode_request = json.getString("geocode");
			ipaddress_request = json.getString("ipaddress");
			type_request = json.getString("type");
			os_request = json.getString("os");
			deviceId_request = json.getString("deviceId");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PayConstant payConstant = null;
		payConstant = PayConstant.OTP;

		HeadType head = new HeadType();
		head.setMsgId(msgId_request);
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		PayTrans txn = new PayTrans();
		txn.setId(txnId_request);
		txn.setNote("Fetching OTP");
		txn.setType(payConstant);
		txn.setTs(timestamp);
		txn.setRefId(refId_request);
		txn.setRefUrl(Constants.refURL);

		PayerType payerType = new PayerType();
		payerType.setType(PayerConstant.PERSON);
		payerType.setAddr(phone_request + "@" + Constants.handle);
		payerType.setName(phone_request);
		payerType.setSeqNum("1");
		payerType.setCode(Constants.code);

		DeviceType deviceType = new DeviceType();
		DeviceType.Tag app = new DeviceType.Tag();
		app.setName(DeviceTagNameType.APP);
		app.setValue(appid_request);
		deviceType.getTag().add(app);
		DeviceType.Tag mobile = new DeviceType.Tag();
		mobile.setName(DeviceTagNameType.MOBILE);
		mobile.setValue(phone_request);
		deviceType.getTag().add(mobile);
		DeviceType.Tag geocode = new DeviceType.Tag();
		geocode.setName(DeviceTagNameType.GEOCODE);
		geocode.setValue(geoCode_request);
		deviceType.getTag().add(geocode);
		DeviceType.Tag location = new DeviceType.Tag();
		location.setName(DeviceTagNameType.LOCATION);
		location.setValue("Bangalore, Karnataka");
		deviceType.getTag().add(location);
		DeviceType.Tag ip = new DeviceType.Tag();
		ip.setName(DeviceTagNameType.IP);
		ip.setValue(ipaddress_request);
		deviceType.getTag().add(ip);
		DeviceType.Tag type = new DeviceType.Tag();
		type.setName(DeviceTagNameType.TYPE);
		type.setValue(type_request);
		deviceType.getTag().add(type);
		DeviceType.Tag id = new DeviceType.Tag();
		id.setName(DeviceTagNameType.ID);
		id.setValue(deviceId_request);
		deviceType.getTag().add(id);
		DeviceType.Tag os = new DeviceType.Tag();
		os.setName(DeviceTagNameType.OS);
		os.setValue(os_request);
		deviceType.getTag().add(os);
		DeviceType.Tag capability = new DeviceType.Tag();
		capability.setName(DeviceTagNameType.CAPABILITY);
		capability.setValue("011001");
		deviceType.getTag().add(capability);
		payerType.setDevice(deviceType);

		AccountType accountType = new AccountType();
		accountType.setAddrType(AddressType.ACCOUNT);
		AccountType.Detail actType = new AccountType.Detail();
		actType.setName(AccountDetailType.ACTYPE);
		actType.setValue("SAVINGS");
		accountType.getDetail().add(actType);
		AccountType.Detail ifsc = new AccountType.Detail();
		ifsc.setName(AccountDetailType.IFSC);
		ifsc.setValue(Constants.IFSC + "1234567");
		accountType.getDetail().add(ifsc);
		AccountType.Detail actNum = new AccountType.Detail();
		actNum.setName(AccountDetailType.ACNUM);
		actNum.setValue(phone_request);
		accountType.getDetail().add(actNum);
		payerType.setAc(accountType);

		ReqOtp reqOtp = new ReqOtp();
		reqOtp.setHead(head);
		reqOtp.setTxn(txn);
		reqOtp.setPayer(payerType);

		return reqOtp;
	}

	public static RespOtp respOTP(JSONObject json) {
		String txnId_request = null, refId_request = null, msgId_request = null;
		try {
			txnId_request = json.getString("txnId");
			refId_request = json.getString("refId");
			msgId_request = json.getString("msgId");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PayConstant payConstant = null;
		payConstant = PayConstant.OTP;

		HeadType head = new HeadType();
		head.setMsgId(msgId_request);
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		PayTrans txn = new PayTrans();
		txn.setId(txnId_request);
		txn.setNote("Providing OTP");
		txn.setType(payConstant);
		txn.setTs(timestamp);
		txn.setRefId(refId_request);
		txn.setRefUrl(Constants.refURL);

		RespType respType = new RespType();
		respType.setReqMsgId(msgId_request);
		respType.setResult("SUCCESS");
		respType.setErrCode("");

		RespOtp respOtp = new RespOtp();
		respOtp.setHead(head);
		respOtp.setTxn(txn);
		respOtp.setResp(respType);

		return respOtp;
	}

	public static RespBalEnq respBalEnq(JSONObject json) {
		String txnId_request = null, refId_request = null, msgId_request = null, phone_request = null;
		try {
			txnId_request = json.getString("txnId");
			refId_request = json.getString("refId");
			msgId_request = json.getString("msgId");
			phone_request = json.getString("phone");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PayConstant payConstant = null;
		payConstant = PayConstant.BAL_ENQ;

		HeadType head = new HeadType();
		head.setMsgId(msgId_request);
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		RiskScoresType riskScoresType = new RiskScoresType();
		Score score = new Score();
		score.setProvider(Constants.IFSC);
		score.setType("TXNRISK");
		score.setValue("0030");
		riskScoresType.getScore().add(score);

		PayTrans txn = new PayTrans();
		txn.setId(txnId_request);
		txn.setNote("Balance Enquiry");
		txn.setType(payConstant);
		txn.setTs(timestamp);
		txn.setRefId(refId_request);
		txn.setRefUrl(Constants.refURL);
		txn.setRiskScores(riskScoresType);

		RespType respType = new RespType();
		respType.setReqMsgId(msgId_request);
		respType.setResult("SUCCESS");
		respType.setErrCode("");

		Bal bal = new Bal();
		RespBalEnq.Payer.Bal.Data data = new RespBalEnq.Payer.Bal.Data();
		data.setCode("70011");
		data.setKi("20160227");
		data.setValue(SignatureGenerationUtil.encrypt("12035"));
		bal.setData(data);

		Payer payerType = new Payer();
		payerType.setType(PayerConstant.PERSON.toString());
		payerType.setAddr(phone_request + "@" + Constants.handle);
		payerType.setName(phone_request);
		payerType.setSeqNum("1");
		payerType.setCode(Constants.code);
		payerType.setBal(bal);

		RespBalEnq respBalEnq = new RespBalEnq();
		respBalEnq.setHead(head);
		respBalEnq.setTxn(txn);
		respBalEnq.setResp(respType);
		respBalEnq.setPayer(payerType);

		return respBalEnq;
	}

	public static RespRegMob respRegMob(JSONObject json) {
		String txnId_request = null, refId_request = null, msgId_request = null;
		try {
			txnId_request = json.getString("txnId");
			refId_request = json.getString("refId");
			msgId_request = json.getString("msgId");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PayConstant payConstant = null;
		payConstant = PayConstant.REQ_REG_MOB;

		HeadType head = new HeadType();
		head.setMsgId(msgId_request);
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		PayTrans txn = new PayTrans();
		txn.setId(txnId_request);
		txn.setNote("Mobile Registration");
		txn.setType(payConstant);
		txn.setTs(timestamp);
		txn.setRefId(refId_request);
		txn.setRefUrl(Constants.refURL);

		RespType respType = new RespType();
		respType.setReqMsgId(msgId_request);
		respType.setResult("SUCCESS");
		respType.setErrCode("");

		RespRegMob respRebMob = new RespRegMob();
		respRebMob.setHead(head);
		respRebMob.setTxn(txn);
		respRebMob.setResp(respType);

		return respRebMob;
	}

	public static ReqRegMob getRegisterMobile(JSONObject json) {
		String txnId_request = null, refId_request = null, msgId_request = null, appid_request = null, phone_request = null, geoCode_request = null, ipaddress_request = null, type_request = null, os_request = null;
		String credType_request = null, credDataValue_request = null, deviceId_request = null;
		String location_request = null;
		try {
			txnId_request = json.getString("txnId");
			refId_request = json.getString("refId");
			msgId_request = json.getString("msgId");
			appid_request = json.getString("appId");
			phone_request = json.getString("phone");
			location_request = json.getString("location");
			geoCode_request = json.getString("geocode");
			ipaddress_request = json.getString("ipaddress");
			type_request = json.getString("type");
			os_request = json.getString("os");
			credType_request = json.getString("credType");
			credDataValue_request = json.getString("credDataValue");
			deviceId_request = json.getString("deviceId");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (org.apache.commons.lang3.StringUtils.isBlank(location_request)) {
			location_request = "Bangalore, Karnataka";
		}
		PayConstant payConstant = null;
		payConstant = PayConstant.REQ_REG_MOB;

		HeadType head = new HeadType();
		head.setMsgId(msgId_request);
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		PayTrans txn = new PayTrans();
		txn.setId(txnId_request);
		txn.setNote("Mobile Registration");
		txn.setType(payConstant);
		txn.setTs(timestamp);
		txn.setRefId(refId_request);
		txn.setRefUrl(Constants.refURL);

		PayerType payerType = new PayerType();
		payerType.setType(PayerConstant.PERSON);
		payerType.setAddr(phone_request + "@" + Constants.handle);
		payerType.setName(phone_request);
		payerType.setSeqNum("1");
		payerType.setCode(Constants.code);

		DeviceType deviceType = new DeviceType();
		DeviceType.Tag app = new DeviceType.Tag();
		app.setName(DeviceTagNameType.APP);
		app.setValue(appid_request);
		deviceType.getTag().add(app);
		DeviceType.Tag mobile = new DeviceType.Tag();
		mobile.setName(DeviceTagNameType.MOBILE);
		mobile.setValue(phone_request);
		deviceType.getTag().add(mobile);
		DeviceType.Tag geocode = new DeviceType.Tag();
		geocode.setName(DeviceTagNameType.GEOCODE);
		geocode.setValue(geoCode_request);
		deviceType.getTag().add(geocode);
		DeviceType.Tag location = new DeviceType.Tag();
		location.setName(DeviceTagNameType.LOCATION);
		location.setValue(location_request);
		deviceType.getTag().add(location);
		DeviceType.Tag ip = new DeviceType.Tag();
		ip.setName(DeviceTagNameType.IP);
		ip.setValue(ipaddress_request);
		deviceType.getTag().add(ip);
		DeviceType.Tag type = new DeviceType.Tag();
		type.setName(DeviceTagNameType.TYPE);
		type.setValue(type_request);
		deviceType.getTag().add(type);
		DeviceType.Tag id = new DeviceType.Tag();
		id.setName(DeviceTagNameType.ID);
		id.setValue(deviceId_request);
		deviceType.getTag().add(id);
		DeviceType.Tag os = new DeviceType.Tag();
		os.setName(DeviceTagNameType.OS);
		os.setValue(os_request);
		deviceType.getTag().add(os);
		DeviceType.Tag capability = new DeviceType.Tag();
		capability.setName(DeviceTagNameType.CAPABILITY);
		capability.setValue("011001");
		deviceType.getTag().add(capability);
		payerType.setDevice(deviceType);

		AccountType accountType = new AccountType();
		accountType.setAddrType(AddressType.ACCOUNT);
		AccountType.Detail actType = new AccountType.Detail();
		actType.setName(AccountDetailType.ACTYPE);
		actType.setValue("SAVINGS");
		accountType.getDetail().add(actType);
		AccountType.Detail ifsc = new AccountType.Detail();
		ifsc.setName(AccountDetailType.IFSC);
		ifsc.setValue(Constants.IFSC + "1234567");
		accountType.getDetail().add(ifsc);
		AccountType.Detail actNum = new AccountType.Detail();
		actNum.setName(AccountDetailType.ACNUM);
		actNum.setValue(phone_request);
		accountType.getDetail().add(actNum);
		payerType.setAc(accountType);

		CredsType credsType = new CredsType();
		CredsType.Cred cred = new CredsType.Cred();
		switch (credType_request) {

		case "MPIN":
			cred.setType(CredType.PIN);
			cred.setSubType(CredSubType.MPIN);
			break;

		case "OTP":
			cred.setType(CredType.OTP);
			cred.setSubType(CredSubType.SMS);
			break;

		default:
			break;
		}

		CredsType.Cred.Data data = new CredsType.Cred.Data();
		data.setValue(credDataValue_request);
		data.setCode("NPCI");
		data.setKi("20150822");
		cred.setData(data);
		CredsType.Cred.Skey skey = new CredsType.Cred.Skey();
		skey.setKi("20150822");
		skey.setValue("NPCI");
		cred.setSkey(skey);
		credsType.getCred().add(cred);

		ReqRegMob.RegDetails.Detail detail = new ReqRegMob.RegDetails.Detail();
		detail.setName(MobRegDetailsNameType.MOBILE);
		detail.setValue(phone_request);
		ReqRegMob.RegDetails regDetails = new ReqRegMob.RegDetails();
		regDetails.getDetail().add(detail);
		regDetails.setCreds(credsType);

		ReqRegMob reqRegMob = new ReqRegMob();
		reqRegMob.setHead(head);
		reqRegMob.setTxn(txn);
		reqRegMob.setRegDetails(regDetails);
		reqRegMob.setPayer(payerType);

		return reqRegMob;
	}

	public static ReqBalEnq getBalance(JSONObject json) {
		String txnId_request = null, refId_request = null, msgId_request = null, appid_request = null, phone_request = null, geoCode_request = null, ipaddress_request = null, type_request = null, os_request = null;
		String credDataValue_request = null, deviceId_request = null;
		try {
			txnId_request = json.getString("txnId");
			refId_request = json.getString("refId");
			msgId_request = json.getString("msgId");
			appid_request = json.getString("appId");
			phone_request = json.getString("phone");
			geoCode_request = json.getString("geocode");
			ipaddress_request = json.getString("ipaddress");
			type_request = json.getString("type");
			os_request = json.getString("os");
			credDataValue_request = json.getString("credDataValue");
			deviceId_request = json.getString("deviceId");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PayConstant payConstant = null;
		payConstant = PayConstant.BAL_ENQ;

		HeadType head = new HeadType();
		head.setMsgId(msgId_request);
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		RiskScoresType riskScoresType = new RiskScoresType();
		Score score = new Score();
		score.setProvider(Constants.IFSC);
		score.setType("TXNRISK");
		score.setValue("0030");
		riskScoresType.getScore().add(score);

		PayTrans txn = new PayTrans();
		txn.setId(txnId_request);
		txn.setNote("Balance Enquiry");
		txn.setType(payConstant);
		txn.setTs(timestamp);
		txn.setRefId(refId_request);
		txn.setRefUrl(Constants.refURL);
		txn.setRiskScores(riskScoresType);

		PayerType payerType = new PayerType();
		payerType.setType(PayerConstant.PERSON);
		payerType.setAddr(phone_request + "@" + Constants.handle);
		payerType.setName(phone_request);
		payerType.setSeqNum("1");
		payerType.setCode(Constants.code);

		DeviceType deviceType = new DeviceType();
		DeviceType.Tag app = new DeviceType.Tag();
		app.setName(DeviceTagNameType.APP);
		app.setValue(appid_request);
		deviceType.getTag().add(app);
		DeviceType.Tag mobile = new DeviceType.Tag();
		mobile.setName(DeviceTagNameType.MOBILE);
		mobile.setValue(phone_request);
		deviceType.getTag().add(mobile);
		DeviceType.Tag geocode = new DeviceType.Tag();
		geocode.setName(DeviceTagNameType.GEOCODE);
		geocode.setValue(geoCode_request);
		deviceType.getTag().add(geocode);
		DeviceType.Tag location = new DeviceType.Tag();
		location.setName(DeviceTagNameType.LOCATION);
		location.setValue("Bangalore, Karnataka");
		deviceType.getTag().add(location);
		DeviceType.Tag ip = new DeviceType.Tag();
		ip.setName(DeviceTagNameType.IP);
		ip.setValue(ipaddress_request);
		deviceType.getTag().add(ip);
		DeviceType.Tag type = new DeviceType.Tag();
		type.setName(DeviceTagNameType.TYPE);
		type.setValue(type_request);
		deviceType.getTag().add(type);
		DeviceType.Tag id = new DeviceType.Tag();
		id.setName(DeviceTagNameType.ID);
		id.setValue(deviceId_request);
		deviceType.getTag().add(id);
		DeviceType.Tag os = new DeviceType.Tag();
		os.setName(DeviceTagNameType.OS);
		os.setValue(os_request);
		deviceType.getTag().add(os);
		DeviceType.Tag capability = new DeviceType.Tag();
		capability.setName(DeviceTagNameType.CAPABILITY);
		capability.setValue("011001");
		deviceType.getTag().add(capability);
		payerType.setDevice(deviceType);

		AccountType accountType = new AccountType();
		accountType.setAddrType(AddressType.ACCOUNT);
		AccountType.Detail actType = new AccountType.Detail();
		actType.setName(AccountDetailType.ACTYPE);
		actType.setValue("SAVINGS");
		accountType.getDetail().add(actType);
		AccountType.Detail ifsc = new AccountType.Detail();
		ifsc.setName(AccountDetailType.IFSC);
		ifsc.setValue(Constants.IFSC + "1234567");
		accountType.getDetail().add(ifsc);
		AccountType.Detail actNum = new AccountType.Detail();
		actNum.setName(AccountDetailType.ACNUM);
		actNum.setValue(phone_request);
		accountType.getDetail().add(actNum);
		payerType.setAc(accountType);

		CredsType credsType = new CredsType();
		CredsType.Cred cred = new CredsType.Cred();
		cred.setType(CredType.PIN);
		cred.setSubType(CredSubType.MPIN);
		CredsType.Cred.Data data = new CredsType.Cred.Data();
		data.setValue(credDataValue_request);
		data.setCode("NPCI");
		data.setKi("20150822");
		cred.setData(data);
		credsType.getCred().add(cred);

		payerType.setCreds(credsType);

		ReqBalEnq reqBalEnq = new ReqBalEnq();
		reqBalEnq.setHead(head);
		reqBalEnq.setTxn(txn);
		reqBalEnq.setPayer(payerType);

		return reqBalEnq;
	}

	public static ReqPay getReqPay(JSONObject json) {

		String txnId_request = null, refId_request = null, msgId_request = null, appid_request = null, phone_request = null, geoCode_request = null, ipaddress_request = null, type_request = null, os_request = null;
		String credDataValue_request = null, deviceId_request = null, txnAmt_request = null, location_request = null, payee_phone_request = null;
		try {
			txnId_request = json.getString("txnId");
			refId_request = json.getString("refId");
			msgId_request = json.getString("msgId");
			appid_request = json.getString("appId");
			phone_request = json.getString("phone");
			geoCode_request = json.getString("geocode");
			ipaddress_request = json.getString("ipaddress");
			type_request = json.getString("type");
			os_request = json.getString("os");
			credDataValue_request = json.getString("credDataValue");
			deviceId_request = json.getString("deviceId");
			txnAmt_request = json.getString("txnAmt");
			payee_phone_request = json.getString("payee_phone");
			location_request = json.getString("location");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (org.apache.commons.lang3.StringUtils.isBlank(location_request)) {
			location_request = "Bangalore, Karnataka";
		}
		if (org.apache.commons.lang3.StringUtils.isBlank(payee_phone_request)) {
			payee_phone_request = "9916525300";
		}
		HeadType head = new HeadType();
		head.setMsgId(msgId_request);
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		Meta meta = new Meta();
		Meta.Tag payRequestStart = new Meta.Tag();
		payRequestStart.setName(MetaTagNameType.PAYREQSTART);
		payRequestStart.setValue(timestamp);
		Meta.Tag payRequestEnd = new Meta.Tag();
		payRequestEnd.setName(MetaTagNameType.PAYREQEND);
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		cal.add(Calendar.SECOND, 30);
		Date endDate = cal.getTime();
		payRequestEnd.setValue(Constants.sdf.format(endDate));
		meta.getTag().add(payRequestStart);
		meta.getTag().add(payRequestEnd);

		RiskScoresType riskScoresType = new RiskScoresType();
		Score score = new Score();
		score.setProvider("Ultracash");
		score.setType("TXNRISK");
		score.setValue("0030");
		riskScoresType.getScore().add(score);

		PayTrans txn = new PayTrans();
		txn.setId(txnId_request);
		txn.setNote("Paying via Ultracash");
		txn.setType(PayConstant.PAY);
		txn.setTs(timestamp);
		txn.setRefId(refId_request);
		txn.setRefUrl(Constants.refURL);
		txn.setRiskScores(riskScoresType);

		PayerType payerType = new PayerType();
		payerType.setType(PayerConstant.PERSON);
		payerType.setAddr(phone_request + "@" + Constants.handle);
		payerType.setName(phone_request);
		payerType.setSeqNum("1");
		payerType.setCode(Constants.code);

		payerType.setInfo(new InfoType());
		payerType.getInfo().setIdentity(new IdentityType());
		payerType.getInfo().getIdentity().setType(IdentityConstant.ACCOUNT);
		payerType.getInfo().getIdentity()
				.setId(new Random().nextInt(9999999) + "");
		payerType.getInfo().getIdentity().setVerifiedName(phone_request);
		payerType.getInfo().setRating(new RatingType());
		payerType.getInfo().getRating()
				.setVerifiedAddress(WhiteListedConstant.TRUE);

		DeviceType deviceType = new DeviceType();
		DeviceType.Tag app = new DeviceType.Tag();
		app.setName(DeviceTagNameType.APP);
		app.setValue(appid_request);
		deviceType.getTag().add(app);
		DeviceType.Tag mobile = new DeviceType.Tag();
		mobile.setName(DeviceTagNameType.MOBILE);
		mobile.setValue(phone_request);
		deviceType.getTag().add(mobile);
		DeviceType.Tag geocode = new DeviceType.Tag();
		geocode.setName(DeviceTagNameType.GEOCODE);
		geocode.setValue(geoCode_request);
		deviceType.getTag().add(geocode);
		DeviceType.Tag location = new DeviceType.Tag();
		location.setName(DeviceTagNameType.LOCATION);
		location.setValue(location_request);
		deviceType.getTag().add(location);
		DeviceType.Tag ip = new DeviceType.Tag();
		ip.setName(DeviceTagNameType.IP);
		ip.setValue(ipaddress_request);
		deviceType.getTag().add(ip);
		DeviceType.Tag type = new DeviceType.Tag();
		type.setName(DeviceTagNameType.TYPE);
		type.setValue(type_request);
		deviceType.getTag().add(type);
		DeviceType.Tag id = new DeviceType.Tag();
		id.setName(DeviceTagNameType.ID);
		id.setValue(deviceId_request);
		deviceType.getTag().add(id);
		DeviceType.Tag os = new DeviceType.Tag();
		os.setName(DeviceTagNameType.OS);
		os.setValue(os_request);
		deviceType.getTag().add(os);
		DeviceType.Tag capability = new DeviceType.Tag();
		capability.setName(DeviceTagNameType.CAPABILITY);
		capability.setValue("011001");
		deviceType.getTag().add(capability);
		payerType.setDevice(deviceType);

		AccountType accountType = new AccountType();
		accountType.setAddrType(AddressType.ACCOUNT);
		AccountType.Detail actType = new AccountType.Detail();
		actType.setName(AccountDetailType.ACTYPE);
		actType.setValue("SAVINGS");
		accountType.getDetail().add(actType);
		AccountType.Detail ifsc = new AccountType.Detail();
		ifsc.setName(AccountDetailType.IFSC);
		ifsc.setValue(Constants.IFSC + "1234567");
		accountType.getDetail().add(ifsc);
		AccountType.Detail actNum = new AccountType.Detail();
		actNum.setName(AccountDetailType.ACNUM);
		actNum.setValue(phone_request);
		accountType.getDetail().add(actNum);
		payerType.setAc(accountType);

		CredsType credsType = new CredsType();
		CredsType.Cred cred = new CredsType.Cred();
		cred.setType(CredType.PIN);
		cred.setSubType(CredSubType.MPIN);
		CredsType.Cred.Data data = new CredsType.Cred.Data();
		data.setValue(credDataValue_request);
		data.setCode("NPCI");
		data.setKi("20150822");
		cred.setData(data);
		credsType.getCred().add(cred);
		payerType.setCreds(credsType);

		AmountType amountType = new AmountType();
		amountType.setValue(txnAmt_request);
		amountType.setCurr("INR");
		payerType.setAmount(amountType);

		PayeesType payeesType = new PayeesType();

		PayeeType payeeType = new PayeeType();
		payeeType.setName("UltraCash");
		payeeType.setInfo(new InfoType());
		payeeType.getInfo().setIdentity(new IdentityType());
		payeeType.getInfo().getIdentity().setType(IdentityConstant.ACCOUNT);
		payeeType.getInfo().getIdentity()
				.setId(new Random().nextInt(9999999) + "");
		payeeType.getInfo().getIdentity().setVerifiedName(payee_phone_request);
		payeeType.getInfo().setRating(new RatingType());
		payeeType.getInfo().getRating()
				.setVerifiedAddress(WhiteListedConstant.TRUE);
		payeeType.setCode("");
		payeeType.setAddr(payee_phone_request + "@" + Constants.handle);
		payeeType.setSeqNum("2");
		payeeType.setType(PayerConstant.PERSON);
		AccountType payeeAccountType = new AccountType();
		payeeAccountType.setAddrType(AddressType.ACCOUNT);
		AccountType.Detail payeeActType = new AccountType.Detail();
		payeeActType.setName(AccountDetailType.ACTYPE);
		payeeActType.setValue("SAVINGS");
		payeeAccountType.getDetail().add(payeeActType);
		AccountType.Detail payeeIFSC = new AccountType.Detail();
		payeeIFSC.setName(AccountDetailType.IFSC);
		payeeIFSC.setValue(Constants.IFSC + "1234567");
		payeeAccountType.getDetail().add(payeeIFSC);
		AccountType.Detail payeeAccountDetails = new AccountType.Detail();
		payeeAccountDetails.setName(AccountDetailType.ACNUM);
		payeeAccountDetails.setValue(payee_phone_request);
		payeeAccountType.getDetail().add(payeeAccountDetails);
		payeeType.setAc(payeeAccountType);
		AmountType payerAmountType = new AmountType();
		payerAmountType.setValue(txnAmt_request);
		payerAmountType.setCurr("INR");
		payeeType.setAmount(payerAmountType);
		payeesType.getPayee().add(payeeType);

		ReqPay reqPay = new ReqPay();
		reqPay.setHead(head);
		reqPay.setTxn(txn);
		reqPay.setPayees(payeesType);
		reqPay.setPayer(payerType);
		reqPay.setMeta(meta);
		return reqPay;
	}

	public static RespPay respPay(ReqPay reqPay) {

		HeadType head = new HeadType();
		head.setMsgId("UPPR" + System.currentTimeMillis());
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
		String timestamp = Constants.sdf.format(currDate);
		head.setTs(timestamp);

		PayTrans payTrans = reqPay.getTxn();
		payTrans.setTs(timestamp);

		Resp resp = new Resp();
		resp.setReqMsgId(reqPay.getHead().getMsgId());
		resp.setResult(ResultType.SUCCESS);
		resp.setErrCode("");

		Ref payerRef = new Ref();
		PayerType payer = reqPay.getPayer();
		payerRef.setType(RefType.PAYER);
		payerRef.setSeqNum(payer.getSeqNum());
		payerRef.setSettAmount(payer.getAmount().getValue());
		payerRef.setSettCurrency(payer.getAmount().getCurr());
		payerRef.setAddr(payer.getAddr());
		payerRef.setApprovalNum(""+System.currentTimeMillis());
		payerRef.setRespCode("00");

		Ref payeeRef = new Ref();
		PayeeType payee = reqPay.getPayees().getPayee().get(0);
		payeeRef.setType(RefType.PAYEE);
		payeeRef.setSeqNum(payee.getSeqNum());
		payeeRef.setSettAmount(payee.getAmount().getValue());
		payeeRef.setSettCurrency(payee.getAmount().getCurr());
		payeeRef.setAddr(payee.getAddr());
		payeeRef.setApprovalNum("");
		payeeRef.setRespCode("");

		switch (reqPay.getTxn().getType()) {
		case CREDIT:
			payeeRef.setApprovalNum(""+System.currentTimeMillis());
			payeeRef.setRespCode("00");
			break;
		case DEBIT:
			payeeRef.setApprovalNum(""+System.currentTimeMillis());
			payeeRef.setRespCode("00");
			break;
		default:
			break;
		}

		resp.getRef().add(payerRef);
		resp.getRef().add(payeeRef);

		RespPay respPay = new RespPay();
		respPay.setHead(head);
		respPay.setTxn(payTrans);
		respPay.setResp(resp);

		return respPay;
	}
}
