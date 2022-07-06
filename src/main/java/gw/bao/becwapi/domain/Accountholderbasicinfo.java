package gw.bao.becwapi.domain;

import javax.xml.bind.annotation.XmlElement;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@ToString
public class Accountholderbasicinfo {
	@XmlElement
	@Getter
	String firstname;
	@XmlElement
	@Getter
	String surname;
	@XmlElement
	@Getter
	String profilename;
	@XmlElement
	@Getter
	IdentityValues identityvalues;
	@XmlElement
	@Getter
	Defaultfris defaultfris;
	@XmlElement
	@Getter
	String loyaltypointsaccountfri;
	@XmlElement
	@Getter
	Acceptedtc acceptedtc;
	@XmlElement
	@Getter
	String accountholderstatus;
	@XmlElement
	@Getter
	String bankdomainname;
	@XmlElement
	@Getter
	Boolean hasparent;
	@XmlElement
	@Getter
	String languagecode;	
}
