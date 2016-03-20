package com.ultracash.upi.api.constants;

import java.text.SimpleDateFormat;

public class Constants {

	public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	public final static String version = "1.0";
	public final static String orgId = "700011";
	public final static String refURL = "http://www.npci.org.in/";
	public final static String IFSC = "AABM";
	public final static String handle = "ultracash";
	public final static String NBIN = "3011";
	public final static String IIN = "500011";
	
	public final static String code = "0000";

	public final static String baseURLPrefix = "https://103.14.161.149:443/upi/";

	public final static String baseURLSuffix = "/"+version+"/urn:txnid:";

	public final static String heartBeat = "ReqHbt";
	public final static String heartBeatURL = baseURLPrefix + heartBeat	+ baseURLSuffix;
	
	public final static String reqListKeys = "ReqListKeys";
	public final static String reqListKeysURL = baseURLPrefix + reqListKeys	+ baseURLSuffix;
	
	public final static String reqListPsp = "ReqListPsp";
	public final static String reqListPspURL = baseURLPrefix + reqListPsp + baseURLSuffix;
	
	public final static String reqListAccount = "ReqListAccount";
	public final static String reqListAccountURL = baseURLPrefix + reqListAccount + baseURLSuffix;
	
	public final static String reqPay = "ReqPay";
	public final static String reqPayURL = baseURLPrefix + reqPay + baseURLSuffix;
	public final static String respPay = "RespPay";
	public final static String respPayURL =  baseURLPrefix + respPay + baseURLSuffix;
	
	public final static String reqBalEnq = "ReqBalEnq";
	public final static String reqBalEnqURL =  baseURLPrefix + reqBalEnq + baseURLSuffix;
	public final static String respBalEnq = "RespBalEnq";
	public final static String respBalEnqURL =  baseURLPrefix + respBalEnq + baseURLSuffix;
	
	public final static String reqRegMob = "ReqRegMob";
	public final static String reqRegMobURL = baseURLPrefix + reqRegMob	+ baseURLSuffix;
	public final static String respRegMob = "RespRegMob";
	public final static String respRegMobURL = baseURLPrefix + respRegMob	+ baseURLSuffix;
	
	public final static String reqOtp = "ReqOtp";
	public final static String reqOtpURL = baseURLPrefix + reqOtp	+ baseURLSuffix;
	public final static String respOtp = "RespOtp";
	public final static String respOtpURL = baseURLPrefix + respOtp	+ baseURLSuffix;

	private Constants() {
	}
	
}
