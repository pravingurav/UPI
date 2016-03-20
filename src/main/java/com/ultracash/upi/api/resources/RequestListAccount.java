package com.ultracash.upi.api.resources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultracash.upi.api.constants.Constants;
import com.ultracash.upi.models.Ack;
import com.ultracash.upi.models.ObjectFactory;
import com.ultracash.upi.models.ReqListAccount;

@Path(ResourceURL.REQUEST_LIST_ACCOUNT)
public class RequestListAccount {

    private static final Logger logger = LoggerFactory.getLogger(RequestListAccount.class);

    @POST
    @Path("/urn:txnid:{tnxId}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public static Ack reqListAccount(@PathParam("tnxId") String txnid, ReqListAccount reqListAccount) {
        logger.info("RequestListAccount: " + reqListAccount);
        ObjectFactory objectFactory = new ObjectFactory();
        Ack ack = objectFactory.createAck();
        ack.setTs(Constants.sdf.format(new Date()));
        ack.setApi("ReqListAccount");
        try {
            ack.setReqMsgId(reqListAccount.getHead().getMsgId().toString());
        } catch (Exception e) {
            e.printStackTrace();
            ack.setErr("Message Id not available");
        }
        return ack;
    }

    @GET
    @Path("/dummy/urn:txnid:{tnxId}")
    @Produces(MediaType.APPLICATION_XML)
    public static ReqListAccount respHbt(@PathParam("tnxId") String txnid)
    {
        ObjectFactory objectFactory = new ObjectFactory();
        ReqListAccount ack = objectFactory.createReqListAccount();
        ack.setHead(objectFactory.createHeadType());
        ack.setLink(objectFactory.createReqListAccountLink());
        ack.setTxn(objectFactory.createPayTrans());
        ack.setPayer(objectFactory.createPayerType());
        return ack;
    }
}
