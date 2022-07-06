package gw.bao.becwapi.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.datatype.XMLGregorianCalendar;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@ToString
public class Acceptedtc {
	@XmlElement
	@Getter
	String acceptedtcversion;
	@XmlElement
	@Getter
	XMLGregorianCalendar acceptedtcdate;
}
