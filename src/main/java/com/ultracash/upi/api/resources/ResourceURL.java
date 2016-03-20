package com.ultracash.upi.api.resources;

import com.ultracash.upi.api.constants.Constants;

public class ResourceURL {
	
	public static final String USER = "/users";
	public static final String REQUEST = "/request";
	
	public static final String RESPONSE_HEART_BEAT = "/RespHbt/" + Constants.version;
	public static final String RESPONSE_LIST_KEYS = "/RespListKeys/" + Constants.version;
	
	public static final String RESPONSE_LIST_PSP = "/RespListPsp/" + Constants.version;
	
	public static final String RESPONSE_LIST_ACCOUNT = "/RespListAccount/" + Constants.version;
	public static final String REQUEST_LIST_ACCOUNT = "/ReqListAccount/" + Constants.version;
	
	public static final String REQUEST_PAY = "/ReqPay/" + Constants.version;
	public static final String RESPONSE_PAY = "/RespPay/" + Constants.version;
	
	public static final String REQUEST_AUTH_DETAILS = "/ReqAuthDetails/" + Constants.version;
	
	public static final String REQUEST_TXN_CONFIRMATION = "/ReqTxnConfirmation/" + Constants.version;
	public static final String RESPONSE_TXN_CONFIRMATION = "/RespTxnConfirmation/" + Constants.version;
	
	public static final String RESPONSE_BALANCE = "/RespBalEnq/" + Constants.version;
	public static final String REQUEST_BALANCE = "/ReqBalEnq/" + Constants.version;
	
	public static final String RESPONSE_OTP = "/RespOtp/" + Constants.version;
	public static final String REQUEST_OTP = "/ReqOtp/" + Constants.version;
	
	public static final String REQUEST_REGISTER_MOBILE = "/ReqRegMob/" + Constants.version;
	public static final String RESPONSE_REGISTER_MOBILE = "/RespRegMob/" + Constants.version;

}
