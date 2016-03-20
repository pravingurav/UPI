package com.ultracash.upi.api.resources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultracash.upi.api.constants.Constants;
import com.ultracash.upi.models.HeadType;
import com.ultracash.upi.models.ObjectFactory;
import com.ultracash.upi.models.ReqTxnConfirmation;
import com.ultracash.upi.models.RespTxnConfirmation;

@Path(ResourceURL.REQUEST_TXN_CONFIRMATION)
public class RequestTxnConfirmation {

	private static final Logger logger = LoggerFactory
			.getLogger(RequestTxnConfirmation.class);

	@POST
	@Path("/urn:txnid:{tnxId}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public static RespTxnConfirmation respHbt(@PathParam("tnxId") String txnid, ReqTxnConfirmation reqTxnConfirmation) {
		logger.info("RequestTxnConfirmation: " + reqTxnConfirmation);
		ObjectFactory objectFactory = new ObjectFactory();
		RespTxnConfirmation respTxnConfirmation = objectFactory.createRespTxnConfirmation();
		try {
			respTxnConfirmation.setHead(new HeadType());
			respTxnConfirmation.getHead().setMsgId(reqTxnConfirmation.getHead().getMsgId());
			respTxnConfirmation.getHead().setVer(Constants.version);
			respTxnConfirmation.getHead().setOrgId(Constants.orgId);
			respTxnConfirmation.getHead().setTs(Constants.sdf.format(new Date()));
			respTxnConfirmation.setTxn(reqTxnConfirmation.getTxn());
			respTxnConfirmation.getTxn().setId(System.currentTimeMillis()+"");
			respTxnConfirmation.setResp(objectFactory.createRespType());
			respTxnConfirmation.getResp().setReqMsgId(reqTxnConfirmation.getHead().getMsgId());
			respTxnConfirmation.getResp().setResult("SUCCESS");
			respTxnConfirmation.getResp().setErrCode("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("ResponseTxnConfirmation: " + respTxnConfirmation);
		return respTxnConfirmation;
	}
}
