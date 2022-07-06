package gw.bao.becwapi.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@ToString
public class Defaultfris {
	@XmlElement
	@Getter
	List<Defaultfri> defaultfri;
}
