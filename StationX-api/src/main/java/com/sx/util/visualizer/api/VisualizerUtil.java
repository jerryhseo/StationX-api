package com.sx.util.visualizer.api;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.sx.constant.RepositoryTypes;

import java.io.IOException;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.ValidatorException;

/**
 * @author jerry
 */
public class VisualizerUtil {
	public static VisualizerConfig getVisualizerConfig( RenderRequest renderRequest, PortletDisplay portletDisplay, User user ) {
		VisualizerConfig config = new VisualizerConfig();
		
		Object showBorders = renderRequest.getParameter("showBorders");
		
		PortletPreferences preferences = portletDisplay.getPortletSetup();
		if( showBorders != null ){
			try {
				config.showBorders = Boolean.valueOf((String)showBorders);
				preferences.setValue("portletSetupShowBorders", String.valueOf(showBorders));
				preferences.store();
			}
			catch( IOException | ReadOnlyException | ValidatorException e){
				e.printStackTrace();
			}
		}
		else {
			config.showBorders = false;
		}
		
		config.namespace = portletDisplay.getNamespace();
		config.portletId = portletDisplay.getId();
		
		config.portletWidth = preferences.getValue("portletWidth", "");
		config.portletHeight = preferences.getValue("portletHeight", "");
		config.portletScroll = preferences.getValue("portletScroll", "");
		
		config.disabled = ParamUtil.getString(renderRequest, "disabled", "false");
		config.connector = ParamUtil.getString(renderRequest, "connector", "");
		
		config.menuOptions = ParamUtil.getString(renderRequest, "menuOptions", "" );

		if( config.menuOptions.isEmpty() ) {
			JSONObject jsonMenuOptions = null;
			try {
				jsonMenuOptions = JSONFactoryUtil.createJSONObject( ParamUtil.getString(renderRequest, "menuOptions", "{}" ) );
				jsonMenuOptions.put("menu",GetterUtil.getBoolean(preferences.getValue("menu", "true")));
				jsonMenuOptions.put("sample",GetterUtil.getBoolean(preferences.getValue("sample", "true")));
				jsonMenuOptions.put("openLocalFile",GetterUtil.getBoolean(preferences.getValue("openLocalFile", "true")));
				jsonMenuOptions.put("openServerFile",GetterUtil.getBoolean(preferences.getValue("openServerFile", "true")));
				jsonMenuOptions.put("save",GetterUtil.getBoolean(preferences.getValue("save", "true")));
				jsonMenuOptions.put("saveAtLocal",GetterUtil.getBoolean(preferences.getValue("saveAtLocal", "true")));
				jsonMenuOptions.put("download",GetterUtil.getBoolean(preferences.getValue("download", "true")));
				jsonMenuOptions.put("upload",GetterUtil.getBoolean(preferences.getValue("upload", "true")));
			} catch( JSONException e ) {
				jsonMenuOptions = JSONFactoryUtil.createJSONObject();
			}
			
			config.menuOptions = jsonMenuOptions.toString();
		}
		
		System.out.println("Menu Options: "+config.menuOptions);

		//Set initial repository 
		config.initData = ParamUtil.getString(renderRequest, "initData", "{}");
		String portletRepository = GetterUtil.getString(preferences.getValue("portletRepository", RepositoryTypes.USER_HOME.toString()));
		JSONObject jsonInitData = null;
		
		try {
			jsonInitData = JSONFactoryUtil.createJSONObject( config.initData );
			String repositoryType = jsonInitData.getString("repositoryType", portletRepository);
			jsonInitData.put("repositoryType", repositoryType );
			String userScreenName = jsonInitData.getString("user", user.getScreenName() );
			jsonInitData.put("user", userScreenName );
			config.initData = jsonInitData.toString();
		} catch( JSONException e ) {
			jsonInitData = JSONFactoryUtil.createJSONObject();
		}
		
		
		//System.out.println("Menu Options: "+config.menuOptions);
		
		return config;
	}
}