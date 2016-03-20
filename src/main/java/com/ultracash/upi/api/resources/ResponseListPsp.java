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
import com.ultracash.upi.models.Ack;
import com.ultracash.upi.models.ObjectFactory;
import com.ultracash.upi.models.RespListPsp;

@Path(ResourceURL.RESPONSE_LIST_PSP)
public class ResponseListPsp {

    private static final Logger logger = LoggerFactory.getLogger(ResponseListPsp.class);

    @POST
    @Path("/urn:txnid:{tnxId}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public static Ack respHbt(@PathParam("tnxId") String txnid, RespListPsp respListPsp) {
        logger.info("ResponseListPsp: " + respListPsp);
        ObjectFactory objectFactory = new ObjectFactory();
        Ack ack = objectFactory.createAck();
        ack.setTs(Constants.sdf.format(new Date()));
        ack.setApi("RespListPsp");
        try {
            ack.setReqMsgId(respListPsp.getHead().getMsgId().toString());
        } catch (Exception e) {
            e.printStackTrace();
            ack.setErr("Message Id not available");
        }
        return ack;
    }
}
