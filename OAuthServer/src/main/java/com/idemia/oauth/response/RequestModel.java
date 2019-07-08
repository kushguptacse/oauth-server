package com.idemia.oauth.response;

import java.io.Serializable;

public class RequestModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4442887587665715154L;
	private String responseType;
	private String clientId;
	private String redirectUrl;

	/**
	 * @return the responseType
	 */
	public String getResponseType() {
		return responseType;
	}

	/**
	 * @param responseType the responseType to set
	 */
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the redirectUrl
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * @param redirectUrl the redirectUrl to set
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/**
	 * @param responseType
	 * @param clientId
	 * @param redirectUrl
	 */
	public RequestModel(String responseType, String clientId, String redirectUrl) {
		super();
		this.responseType = responseType;
		this.clientId = clientId;
		this.redirectUrl = redirectUrl;
	}

	/**
	 * 
	 */
	public RequestModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
