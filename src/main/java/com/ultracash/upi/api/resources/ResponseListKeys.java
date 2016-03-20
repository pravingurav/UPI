package com.ultracash.upi.api.resources;

import java.io.StringReader;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultracash.persistence.RedisPersistence;
import com.ultracash.upi.api.constants.Constants;
import com.ultracash.upi.models.Ack;
import com.ultracash.upi.models.ObjectFactory;
import com.ultracash.upi.models.RespListKeys;

@Path(ResourceURL.RESPONSE_LIST_KEYS)
public class ResponseListKeys {

	private static final Logger logger = LoggerFactory
			.getLogger(ResponseListKeys.class);

	@POST
	@Path("/urn:txnid:{tnxId}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public static Ack respHbt(@PathParam("tnxId") String txnid, String X) {
		RedisPersistence.getInstance().putString("RespListKeys" + txnid, X);
		logger.info("RespListKeys: " + X);

		String msgId = null;
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RespListKeys.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(X);
			RespListKeys respListKeys = (RespListKeys) unmarshaller
					.unmarshal(reader);
			msgId = respListKeys.getHead().getMsgId().toString();
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObjectFactory objectFactory = new ObjectFactory();
		Ack ack = objectFactory.createAck();
		ack.setTs(Constants.sdf.format(new Date()));
		ack.setApi("RespListKeys");
		try {
			ack.setReqMsgId(msgId.toString());
		} catch (Exception e) {
			e.printStackTrace();
			ack.setErr("Message Id not available");
		}
		return ack;
	}
}
