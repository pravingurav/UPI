package com.ultracash.upi.api.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultracash.upi.api.constants.Constants;
import com.ultracash.upi.api.services.HeartBeatService;
import com.ultracash.upi.models.ReqHbt;
import com.ultracash.upi.utility.HttpUtil;
import com.ultracash.upi.utility.SignatureUtil;

public class SendHeartBeatJob implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(SendHeartBeatJob.class);
	
	public SendHeartBeatJob() {
	}

	@Override
	public void run() {
		ReqHbt reqHbt = HeartBeatService.getHeartBeat();
		String txnId = reqHbt.getTxn().getId();
		String url = Constants.heartBeatURL + txnId;
		SignatureUtil<ReqHbt> signatureUtil = new SignatureUtil<ReqHbt>(ReqHbt.class);
		String xmlString = signatureUtil.getSignedXMLString(reqHbt);
		logger.info("ReqHbt: " + xmlString);
		String response = HttpUtil.postXMLString(url, xmlString);
		logger.info("Response: " + response);
	}
	
	public static void main(String[] args) {
		SendHeartBeatJob hbt = new SendHeartBeatJob();
		hbt.run();
	}
}
