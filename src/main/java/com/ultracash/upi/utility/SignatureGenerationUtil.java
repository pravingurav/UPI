package com.ultracash.upi.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Collections;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

import com.sun.jersey.core.util.Base64;
import org.w3c.dom.Document;

@WebListener
public class SignatureGenerationUtil implements ServletContextListener {

	private static PrivateKey privateKey = null;
	private static PublicKey publicKey = null;
	private static String password = "Ultracash";
	private static String alias = "ultracash";
	public static KeyStore keystore = null;
	private static Cipher encryptionCipher; 
	private static Cipher decryptionCipher; 
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			File signerFile = new File(event.getServletContext().getRealPath(
					"/")
					+ "config/certs/ultracash.p12");
			FileInputStream is = new FileInputStream(signerFile);
			keystore = KeyStore.getInstance("PKCS12");
			keystore.load(((InputStream) is), password.toCharArray());

			PrivateKey key = (PrivateKey) keystore.getKey(alias,
					password.toCharArray());
			privateKey = key;
			Certificate cert = keystore.getCertificate(alias);
			publicKey = cert.getPublicKey();
			
			encryptionCipher = Cipher.getInstance("RSA");
			decryptionCipher = Cipher.getInstance("RSA");
			encryptionCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			decryptionCipher.init(Cipher.DECRYPT_MODE, privateKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String encrypt(String rawData) {
		String encryptData = null;
		byte[] dectyptedText = null;
		try {
			dectyptedText = encryptionCipher.doFinal(rawData.getBytes());
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		encryptData = new String(Base64.encode(dectyptedText));
		return encryptData;
	}

	public static String decrypt(String encryptedData) {
		String decryptData = null;
		byte[] dectyptedText = null;
		try {
			dectyptedText = decryptionCipher.doFinal(Base64.decode(encryptedData));
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decryptData = new String(dectyptedText);
		return decryptData;
	}

	public static Document signDoc(Document doc) {
		XMLSignatureFactory xmlSigFactory = XMLSignatureFactory
				.getInstance("DOM");
		DOMSignContext domSignCtx = new DOMSignContext(privateKey,
				doc.getDocumentElement());
		Reference ref = null;
		SignedInfo signedInfo = null;
		try {
			ref = xmlSigFactory
					.newReference("", xmlSigFactory.newDigestMethod(
							DigestMethod.SHA256, null), Collections
							.singletonList(xmlSigFactory.newTransform(
									Transform.ENVELOPED,
									(TransformParameterSpec) null)), null, null);
			signedInfo = xmlSigFactory
					.newSignedInfo(
							xmlSigFactory.newCanonicalizationMethod(
									CanonicalizationMethod.INCLUSIVE,
									(C14NMethodParameterSpec) null),
							xmlSigFactory
									.newSignatureMethod(
											"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256",
											(SignatureMethodParameterSpec) null),
							Collections.singletonList(ref));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (InvalidAlgorithmParameterException ex) {
			ex.printStackTrace();
		}
		// Pass the Public Key File Path
		KeyInfo keyInfo = getKeyInfo(xmlSigFactory); // Create a new XML
														// Signature
		XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo,
				keyInfo);
		try {
			// Sign the document
			xmlSignature.sign(domSignCtx);
		} catch (MarshalException ex) {
			ex.printStackTrace();
		} catch (XMLSignatureException ex) {
			ex.printStackTrace();
		}
		return doc;
	}

	private static KeyInfo getKeyInfo(XMLSignatureFactory xmlSigFactory) {
		KeyInfo keyInfo = null;
		KeyValue keyValue = null;
		KeyInfoFactory keyInfoFact = xmlSigFactory.getKeyInfoFactory();
		try {
			keyValue = keyInfoFact.newKeyValue(publicKey);
		} catch (KeyException ex) {
			ex.printStackTrace();
		}
		keyInfo = keyInfoFact.newKeyInfo(Collections.singletonList(keyValue));
		return keyInfo;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}
}
