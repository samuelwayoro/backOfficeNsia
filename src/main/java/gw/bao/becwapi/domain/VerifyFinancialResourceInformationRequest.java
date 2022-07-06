package gw.bao.becwapi.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

//Request inverse. reason why Getter is set here instead of Setter
@FieldDefaults(level=AccessLevel.PRIVATE)
@ToString
@XmlRootElement(namespace = "http://www.ericsson.com/em/emm/serviceprovider/v1_2/backend/client", name="verifyfinancialresourceinformationrequest")
public class VerifyFinancialResourceInformationRequest {
	@XmlElement
	@Getter
	String resource;
	@XmlElement
	@Getter
	AccountHolderids accountholderids;
}
