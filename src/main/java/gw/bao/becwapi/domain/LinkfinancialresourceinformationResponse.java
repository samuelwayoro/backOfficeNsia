package gw.bao.becwapi.domain;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@ToString
@XmlRootElement(namespace = "http://www.ericsson.com/em/emm/serviceprovider/v1_1/frontend", name = "linkfinancialresourceinformationresponse")
public class LinkfinancialresourceinformationResponse {
	@XmlElement
	@Getter
	Boolean valid;
}
