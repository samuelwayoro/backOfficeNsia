package gw.bao.becwapi.domain;

import javax.xml.bind.annotation.XmlElement;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@ToString
public class Defaultfri {
	@XmlElement
	@Getter
	String fri;
	@XmlElement
	@Getter
	String currency;
}
