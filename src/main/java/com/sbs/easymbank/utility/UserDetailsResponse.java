
package com.sbs.easymbank.utility;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "userDetailsResponse")
public class UserDetailsResponse {

    protected String responseCode;
    @XmlElement(name = "KYCrspFirstname")
    protected String kyCrspFirstname;
    @XmlElement(name = "KYCrspLastname")
    protected String kyCrspLastname;
    @XmlElement(name = "KYCrspIdRefNo")
    protected String kyCrspIdRefNo;
    @XmlElement(name = "KYCrspDOB")
    protected String kyCrspDOB;
    @XmlElement(name = "KYCrspType")
    protected String kyCrspType;
    protected String mobileNumber;
    protected String message;
    
    public String getResponseCode() {
        return responseCode;
    }
    
    public void setResponseCode(String value) {
        this.responseCode = value;
    }

    public String getKYCrspFirstname() {
        return kyCrspFirstname;
    }

    public void setKYCrspFirstname(String value) {
        this.kyCrspFirstname = value;
    }

    public String getKYCrspLastname() {
        return kyCrspLastname;
    }
    
    public void setKYCrspLastname(String value) {
        this.kyCrspLastname = value;
    }

    public String getKYCrspIdRefNo() {
        return kyCrspIdRefNo;
    }
    
    public void setKYCrspIdRefNo(String value) {
        this.kyCrspIdRefNo = value;
    }

    public String getKYCrspDOB() {
        return kyCrspDOB;
    }

    public void setKYCrspDOB(String value) {
        this.kyCrspDOB = value;
    }

    public String getKYCrspType() {
        return kyCrspType;
    }

    public void setKYCrspType(String value) {
        this.kyCrspType = value;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String value) {
        this.mobileNumber = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

}
