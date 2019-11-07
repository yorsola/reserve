package com.ac.reserve.common.utils.log;

public class ThreadLogInfo {
    private String url;
    private String ipAddress;
    private String partnerId;
    private String methodName;
    private String paramNames;
    private Long startTime;
    private Long endTime;

    /**
     * @param url
     * @param ipAddress
     * @param partnerId
     * @param methodName
     * @param paramNames
     * @param startTime
     * @param endTime
     */
    protected ThreadLogInfo(String url, String ipAddress, String partnerId, String methodName, String paramNames,
            Long startTime, Long endTime) {
        super();
        this.url = url;
        this.ipAddress = ipAddress;
        this.partnerId = partnerId;
        this.methodName = methodName;
        this.paramNames = paramNames;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress
     *            the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return the partnerId
     */
    public String getPartnerId() {
        return partnerId;
    }

    /**
     * @param partnerId
     *            the partnerId to set
     */
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName
     *            the methodName to set
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * @return the paramNames
     */
    public String getParamNames() {
        return paramNames;
    }

    /**
     * @param paramNames
     *            the paramNames to set
     */
    public void setParamNames(String paramNames) {
        this.paramNames = paramNames;
    }

    /**
     * @return the startTime
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            the startTime to set
     */
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     *            the endTime to set
     */
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

}
