package com.ultracash.upi.utility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	public static String postXMLString(String url, String xmlString) {
			
		String result = null;
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustStrategy()
	            {
	                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
	                {
	                    return true;
	                }
	            }).loadKeyMaterial(SignatureGenerationUtil.keystore, "Ultracash".toCharArray());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build(), NoopHostnameVerifier.INSTANCE);
			StringEntity entity = new StringEntity(xmlString);
			entity.setContentType("application/xml");
			HttpPost post = new HttpPost(url);
			post.setEntity(entity);
			HttpClient client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
	        HttpResponse response = client.execute(post);
	        logger.info("ResponseCode: " + response.getStatusLine().getStatusCode());
	        result = EntityUtils.toString(response.getEntity());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
