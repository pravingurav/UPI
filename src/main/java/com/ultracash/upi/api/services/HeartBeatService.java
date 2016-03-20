package com.ultracash.upi.api.services;

import java.util.Date;

import com.ultracash.upi.api.constants.Constants;
import com.ultracash.upi.models.HbtMsgType;
import com.ultracash.upi.models.HeadType;
import com.ultracash.upi.models.PayConstant;
import com.ultracash.upi.models.PayTrans;
import com.ultracash.upi.models.ReqHbt;
import com.ultracash.upi.models.ReqHbt.HbtMsg;

public class HeartBeatService {

	public static ReqHbt getHeartBeat() {
		HeadType head = new HeadType();
		head.setMsgId("" + System.currentTimeMillis());
		head.setOrgId(Constants.orgId);
		head.setVer(Constants.version);
		Date currDate = new Date();
        String timestamp = Constants.sdf.format(currDate);
        head.setTs(timestamp);
        
        PayTrans txn =  new PayTrans();
        txn.setId("" + System.currentTimeMillis());
        txn.setNote("Heartbeat");
        txn.setType(PayConstant.HBT);
        txn.setTs(timestamp);
        
        HbtMsg hbtMsg = new HbtMsg();
        hbtMsg.setType(HbtMsgType.ALIVE);
        hbtMsg.setValue("NA");
        
        ReqHbt reqHbt = new ReqHbt();
        reqHbt.setHead(head);
        reqHbt.setTxn(txn);
        reqHbt.setHbtMsg(hbtMsg);
  
		return reqHbt;
	}
}
