package com.ultracash.upi.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class OkHttpUtil {
	private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);
	public static void main(String[] args) {
		String response = postSSLXML(
				"https://103.14.161.149:443/upi/ReqRegMob/1.0/urn:txnid:UPLK1458155340006",
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><upi:ReqRegMob xmlns:upi=\"http://npci.org/upi/schema/\"><Head msgId=\"MUPLK1458155340006\" orgId=\"410005\" ts=\"2016-03-16T19:09:00Z\" ver=\"1.0\"/><Txn id=\"UPLK1458155340006\" note=\"Mobile Registration\" refId=\"RUPLK1458155340006\" refUrl=\"http://www.npci.org.in/\" ts=\"2016-03-16T19:09:00Z\" type=\"ReqRegMob\"/><Payer addr=\"9886952313@utpl\" name=\"9886952313\" seqNum=\"1\" type=\"PERSON\"><Device><Tag name=\"APP\" value=\"com.ultracash.pay\"/><Tag name=\"MOBILE\" value=\"9886952313\"/><Tag name=\"GEOCODE\" value=\"12.8970792,77.5802889\"/><Tag name=\"LOCATION\" value=\"Bangalore, Karnataka\"/><Tag name=\"IP\" value=\"47.253.129.182\"/><Tag name=\"TYPE\" value=\"mob\"/><Tag name=\"ID\" value=\"867290027781557\"/><Tag name=\"OS\" value=\"android\"/><Tag name=\"CAPABILITY\" value=\"011001\"/></Device><Ac addrType=\"ACCOUNT\"><Detail name=\"ACTYPE\" value=\"SAVINGS\"/><Detail name=\"IFSC\" value=\"UTPL1234567\"/><Detail name=\"ACNUM\" value=\"9886952313\"/></Ac></Payer><RegDetails><Detail name=\"MOBILE\" value=\"9886952313\"/><Creds><Cred subType=\"MPIN\" type=\"PIN\"><Data code=\"NPCI\" ki=\"20150822\">1.0|Ac/mSY3/y56IzlK47655A+FeRKKLLATjd2AWO3JCUKDV0B4y5zzuvqu6RXTjMQ9tL67PhUkm9uPSd+sTNgg4a31I++vHwaTQVfym5viHe4oaP7jsZq1L7UHE/s+gB5xBItpEU8l98WKwNWJ+XyVnbwZBrSI2Q5tYT6hzMN0hySv57JPYU8t6TDO8pfoB93ex9qzS/az2g9lwMpIYwL5x6YUFoE5M4KXxEFtuiM68MDC/cL5flZUGQyKF7F2xIv7T8y5VEcaM98rVwSmgTYwecQhik46Fq4lLRXiC2EIP3W+mDF+66q7LEaxQHSexRWTnwB3tUcs+ZIOCuwkVE5BdWw==</Data></Cred></Creds></RegDetails><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><DigestValue>3fcPe5wffilMNeoYUPdHMeI/n75qEDVFLXdnf8bZAAg=</DigestValue></Reference></SignedInfo><SignatureValue>M3XxjT3eyFOqa1dEt/lOHmKdM3NH3owCWBy0Dos2v28w/jifrABA9H3RS90jzN7Nc6Tg+Li2zoF+\r\nqvtZZyFTvvGBFfF91rxM0ofr6UKe/7QP7YbzoBFh1nIsHR26m9bc9twLROFbjxgkNl+FLN18/HTK\r\nHYGsLDIdkPVxN1XPItiLgokAw4+2mzWpcWNfud/nUcuWL2ReiFJkcuHqBwvkflu5sjwsZNSSsvZ1\r\nSDEUGTreJoK5Z7+JXWCh0HNk16GENGRPEwD/0ywOknSBYlATarEG+nBa+bcAFfj86me5Q7F6NCD1\r\nqMf8joRxY+grvMWrSJ7tIObkY18JWmdrop/CdQ==</SignatureValue><KeyInfo><KeyValue><RSAKeyValue><Modulus>yfjxPfCngiqBBtaX27niughjirykAFqfvnO0vLvUK74FPN1W3BUD72tqUwN+6xVCZ1ciJhJcVUA4\r\nGPUXZrKDL1SV+zGIVmkZuHRE/ucVlRzQbm2ia6B8ezzS8ufk/z6XD8icD/NzSGarP/jtEK+/e/8q\r\nf3P1MFR3K2Bvy6M8ySo8qTySV/aXpIE8Ztbbcw2dD6rQi7+urlKY9XkpEztY++Hm7suhaHm4FgQb\r\nvqshBTl4xZh90DUqVUxXcQ1vgTEPGrbgTBIUiyWMwOFPNuwjmtXz/mfg/k8nGGRCh/cnEnUG1vvG\r\nWjeroFiovuv8J847an9MVfMCAusTiD5J/6HpUQ==</Modulus><Exponent>AQAB</Exponent></RSAKeyValue></KeyValue></KeyInfo></Signature></upi:ReqRegMob>");
		System.out.println(response);
	}

	public static String postSSLXML(String url, String requestStr) {
		String result = null;

		try {

			SSLContext sslContext = null;
			try {
				FileInputStream is = new FileInputStream(
						"/usr/share/tomcat8/webapps/upi/config/certs/ultracash.p12");
				KeyStore keystore = KeyStore.getInstance("PKCS12");
				keystore.load(((InputStream) is), "Ultracash".toCharArray());
				SSLContextBuilder sslContextBuilder = new SSLContextBuilder()
						.useProtocol("TLSv1.2").loadTrustMaterial(keystore,
								new TrustStrategy() {

									@Override
									public boolean isTrusted(
											X509Certificate[] paramArrayOfX509Certificate,
											String paramString)
											throws CertificateException {
										// TODO Auto-generated method stub
										return true;
									}
								});

				sslContext = sslContextBuilder.build();
			} catch (NoSuchAlgorithmException | KeyStoreException
					| KeyManagementException | CertificateException e) {
				e.printStackTrace();
			}

			OkHttpClient client = new OkHttpClient()
					.setSslSocketFactory(sslContext.getSocketFactory())
					.setConnectionSpecs(
							Arrays.asList(ConnectionSpec.MODERN_TLS))
					.setHostnameVerifier(new HostnameVerifier() {

						@Override
						public boolean verify(String hostname,
								SSLSession session) {
							if (hostname.contains("103.14.161.149")) {
								return true;
							}
							return false;
						}
					});

			MediaType mediaType = MediaType.parse("application/xml");
			RequestBody body = RequestBody.create(mediaType, requestStr);
			Request request = new Request.Builder().url(url).post(body)
					.addHeader("Content-Type", "application/xml")
					.addHeader("Accept", "application/xml").build();

			Response response = client.newCall(request).execute();
			 logger.info("ResponseCode: " + response.code());
			 logger.info("Response Headers"+response);
			result = response.body().string();
			logger.info("Response Body "+result);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}
}
