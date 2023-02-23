package com.sx.util.visualizer.api;

public class VisualizerConfig {
	public boolean showBorders;
	public String portletWidth;
	public String portletHeight;
	public String portletScroll;
	public String disabled;
	public String initData;
	public String connector;
	public String menuOptions;
	public String namespace;
	public String portletId;
	
	public String getDisplayStyle() {
		String displayStyle = "";
		if( !portletHeight.isEmpty() ){
			displayStyle += "height:"+portletHeight+";";
		}
		if( !portletWidth.isEmpty() ){
			displayStyle += "width:"+portletWidth+";";
		}
		if( !portletScroll.isEmpty() ){
			displayStyle += "overflow:"+portletScroll+";";
		}
		
		return displayStyle;
	}
}
