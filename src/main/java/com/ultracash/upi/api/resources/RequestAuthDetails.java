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
import com.ultracash.upi.models.ReqAuthDetails;

@Path(ResourceURL.REQUEST_AUTH_DETAILS)
public class RequestAuthDetails {

    private static final Logger logger = LoggerFactory.getLogger(RequestAuthDetails.class);

    @POST
    @Path("/urn:txnid:{tnxId}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public static Ack reqPay(@PathParam("tnxId") String txnid, ReqAuthDetails reqAuthDetails) {
        logger.info("RequestAuthDetails: " + reqAuthDetails);
        ObjectFactory objectFactory = new ObjectFactory();
        Ack ack = objectFactory.createAck();
        ack.setTs(Constants.sdf.format(new Date()));
        ack.setApi("ReqAuthDetails");
        try {
            ack.setReqMsgId(reqAuthDetails.getHead().getMsgId().toString());
        } catch (Exception e) {
            e.printStackTrace();
            ack.setErr("Message Id not available");
        }
        return ack;
    }

    @GET
    @Path("/dummy/urn:txnid:{tnxId}")
    @Produces(MediaType.APPLICATION_XML)
    public static ReqAuthDetails dummy(@PathParam("tnxId") String txnid)
    {
        ObjectFactory objectFactory = new ObjectFactory();
        ReqAuthDetails ack = objectFactory.createReqAuthDetails();
        ack.setHead(objectFactory.createHeadType());
        ack.setPayees(objectFactory.createPayeesType());
        ack.setTxn(objectFactory.createPayTrans());
        ack.setPayer(objectFactory.createPayerType());
        return ack;
    }
}
