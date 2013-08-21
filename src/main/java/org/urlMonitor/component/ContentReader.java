package org.urlMonitor.component;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.urlMonitor.exception.HttpReadContentException;

public interface ContentReader
{
   String readContent(String url) throws HttpReadContentException, ClientProtocolException,
         IOException;
}
