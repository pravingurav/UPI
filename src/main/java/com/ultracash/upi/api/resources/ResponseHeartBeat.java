package com.ultracash.upi.api.resources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ultracash.upi.api.constants.Constants;
import com.ultracash.upi.models.Ack;
import com.ultracash.upi.models.ObjectFactory;
import com.ultracash.upi.models.RespHbt;

@Path(ResourceURL.RESPONSE_HEART_BEAT)
public class ResponseHeartBeat {
	
	@POST
	@Path("/urn:txnid:{tnxId}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public static Ack respHbt(@PathParam("tnxId") String txnid, RespHbt respHbt) {
		ObjectFactory objectFactory = new ObjectFactory();
		Ack ack = objectFactory.createAck();
		ack.setTs(Constants.sdf.format(new Date()));
		ack.setApi("RespHbt");
		try {
			ack.setReqMsgId(respHbt.getHead().getMsgId().toString());
		} catch (Exception e) {
			e.printStackTrace();
			ack.setErr("Message Id not available");
		}
		return ack;
	}

    @GET
    @Path("/dummy/urn:txnid:{tnxId}")
    @Produces(MediaType.APPLICATION_XML)
    public static RespHbt respHbt(@PathParam("tnxId") String txnid)
    {
        ObjectFactory objectFactory = new ObjectFactory();
        RespHbt ack = objectFactory.createRespHbt();
        ack.setHead(objectFactory.createHeadType());
        ack.setResp(objectFactory.createRespType());
        ack.setTxn(objectFactory.createPayTrans());

        return ack;
    }
}
