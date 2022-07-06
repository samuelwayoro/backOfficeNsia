/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw.bao.becwapi.domain;

/**
 *
 * @author samuel
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HttpClientResponse {
    	String httpResponseBody;
	Integer httpResponseCode;
	String httpResponseHeader; //Catch freeflow header here
}
