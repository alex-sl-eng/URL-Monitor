package org.urlMonitor.component;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.urlMonitor.model.type.StatusType;

public interface WebClient
{
   /**
    * Return Status of the url -
    *    if contentRegex empty, check if HTTP reponse is 200
    *    else if match found(contentRegex) in the http source content of the url if return HTTP 200 - OK
    * @param url
    * @param contentRegex
    * @return
    * @throws ClientProtocolException
    * @throws IOException
    */
   StatusType checkStatus(String url, String contentRegex) throws ClientProtocolException, IOException;
}
