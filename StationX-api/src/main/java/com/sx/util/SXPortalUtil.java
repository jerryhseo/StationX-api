package com.sx.util;

import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

public class SXPortalUtil {
	public static final String getAuthToken( PortletRequest portletRequest ) {
		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(portletRequest) );
		
		System.out.println("AuthToken: " +  AuthTokenUtil.getToken(httpServletRequest) );
		return AuthTokenUtil.getToken(httpServletRequest);
	}
}
