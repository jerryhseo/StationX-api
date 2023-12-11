package com.sx.util;

import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Random;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

public class SXPortalUtil {
	public static final String getAuthToken( PortletRequest portletRequest ) {
		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(portletRequest) );
		
		System.out.println("AuthToken: " +  AuthTokenUtil.getToken(httpServletRequest) );
		return AuthTokenUtil.getToken(httpServletRequest);
	}
	
	public static final String generatePortletInstanceId() {
		final int length = 12;
		
		String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		
		StringBuffer randomString = new StringBuffer(length);
		Random random = new Random();
		
		for( int i=0; i<length; i++ ) {
			int randomIndex = random.nextInt(alphanumericCharacters.length());
			char randomChar = alphanumericCharacters.charAt(randomIndex);
			randomString.append(randomChar);
		}
		
		return randomString.toString();
	}
}
