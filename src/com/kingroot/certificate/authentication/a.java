package com.kingroot.certificate.authentication;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.util.Base64;

public class a{
	private static RSAPublicKey a;
	private Properties b;
	
	static{
		byte[] bytes = Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCTrqGfyNYDKZEFfvXuYOu9mSCNu6ri10PMG2xJ5sBuUN2OFBT1W5n/dyUkR+Xgnd6w9arSFnU/8fpiP4DRZPL7pkmgzJvjoPqrreXO4nGRQtVbp6sD/gWCKsTlJ9bk01W32gfSOrCNch8BQJO8nE01ffnWmyRiqVTbuh9KEGgcwIDAQAB", 0);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
		try{
			KeyFactory factory = KeyFactory.getInstance("RSA");
			a = (RSAPublicKey)factory.generatePublic(spec);
		}catch(InvalidKeySpecException e){
			e.printStackTrace();
			a = null;
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace();
			a = null;
		}
	}
	private a(Properties p){
		b = p;
	}
	static a a(String zipFilePath, String assetName)
			throws IOException, NoSuchAlgorithmException, DataFormatException, InvalidKeyException, SignatureException{
		com.kingroot.certificate.authentication.a aObj = null;
		InputStream is = null;
		com.kingroot.sdk.a.b("KRSDKCertificate loadFromAsset >>>>>>>>>>");
		
		ZipFile zip = new ZipFile(zipFilePath);
		Enumeration enu = zip.entries();
		StringBuilder sb1 = new StringBuilder();
		sb1.append("assets");
		sb1.append(File.separatorChar);
		sb1.append(assetName);
		String assetFilePath = sb1.toString();
		while(enu.hasMoreElements()){
			ZipEntry entry = (ZipEntry) enu.nextElement();
			if(entry.isDirectory()){
				continue;
			}
			String entryName = entry.getName();
			com.kingroot.sdk.a.a("entry name : " + entryName);
			if(!assetFilePath.equals(entryName)){
				continue;
			}
			is = zip.getInputStream(entry);
			if(null == is){
				zip.close();
				return null;
			}
			break;
		}
		if(is==null){
			zip.close();
			return aObj;
		}
		try{
			int fileFlag = a(is);
			if(fileFlag != 0x5443524b){
				throw new DataFormatException("Not a kingroot sdk certification file");
			}
		}catch(DataFormatException e){
			is.close();
			zip.close();
			throw e;
		}
		try{
			int a = a(is);
			int b = a(is);
			byte[] buf1 = new byte[a];//v4
			byte[] buf2 = new byte[b];//v1
			is.read(buf2);
			Inflater inf = new Inflater();
			inf.setInput(buf2);
			if( inf.inflate(buf1) != a ){
				throw new DataFormatException("Unexpected data length");
			}
			inf.end();
			Properties p = new Properties();
			p.loadFromXML(new ByteArrayInputStream(buf1));
			int c = a(is);
			byte[] buf3 = new byte[c];
			is.read(buf3);
			Signature sig = Signature.getInstance("SHA1WithRSA");
			sig.initVerify(com.kingroot.certificate.authentication.a.a);
			sig.update(buf2);
			if( !sig.verify(buf3) ){
				throw new SignatureException("Bad signature");
			}
			aObj = new com.kingroot.certificate.authentication.a(p);
			return aObj;
		}finally{
			zip.close();
			is.close();
		}
	}
	private static int a(InputStream is) throws IOException{
		int a = is.read();
		int b = is.read();
		a = (b << 0x8) | a;
		int c = is.read();
		a = (c << 0x10) | a;
		int d = is.read();
		a = (d << 0x18 ) | a;
		return a;
	}
	String a(){
		return b.getProperty("channel_id");
	}
	String b(){
		StringBuilder sb = new StringBuilder();
		sb.append("getPackageCertMd5 : ");
		sb.append(b.getProperty("cert_md5"));
		com.kingroot.sdk.a.a(sb.toString());
		return b.getProperty("cert_md5");
	}
	boolean b(String str1, String str2){
		String packageName = getPackageName();
		if(!str1.equals(packageName)){
			com.kingroot.sdk.a.a("Certifacate Fail, PackageName wrong.");
			System.out.println("P F");
			return false;
		}
		System.out.println("P O");
		String cert_md5 = b();
		if(!str2.equals(cert_md5)){
			com.kingroot.sdk.a.a("Certifacate Fail, PackageMD5 wrong.");
			System.out.println("M F");
			return false;
		}
		com.kingroot.sdk.a.a("Certifacate Succeed.");
		System.out.println("M O");
		return true;
	}
	String getPackageName(){
		return b.getProperty("package_name");
	}
}