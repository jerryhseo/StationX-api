package com.sx.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.sx.util.portlet.SXPortletURLUtil;

import java.util.Random;

import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpServletRequest;

public class SXPortalUtil {
	public static final String getAuthToken( PortletRequest portletRequest ) {
		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(portletRequest) );
		
		System.out.println("AuthToken: " +  AuthTokenUtil.getToken(httpServletRequest) );
		return AuthTokenUtil.getToken(httpServletRequest);
	}
	
	public static JSONObject createPortletInstanceId(  PortletRequest portletRequest, String portletName ) throws PortalException, PortletModeException, WindowStateException {
		final String instance = "_INSTANCE_";

		String portletInstanceId = SXPortalUtil.generatePortletInstanceId();
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String portletURL = SXPortletURLUtil.createURL(
				portletRequest, 
				themeDisplay, 
				portletName+instance+portletInstanceId,
				null,
				LiferayPortletMode.VIEW,
				LiferayWindowState.EXCLUSIVE );
		 
		JSONObject portletInstance = JSONFactoryUtil.createJSONObject();
		portletInstance.put( "url" , portletURL );
		portletInstance.put( "portletId", portletName+instance+portletInstanceId);
		portletInstance.put( "namespace", "_"+portletName+instance+portletInstanceId+"_" );
		
		return portletInstance;
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
