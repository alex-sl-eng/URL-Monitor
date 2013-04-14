/**
 * 
 */
package org.aeng.urlMonitor.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.aeng.urlMonitor.server.exception.HttpReadContentException;
import org.aeng.urlMonitor.shared.model.UrlMonitor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 * 
 */
public class HttpUtil
{
   public static String readContent(UrlMonitor urlMonitor) throws HttpReadContentException, ClientProtocolException, IOException
   {
      StringBuilder sb = new StringBuilder();

      HttpClient httpclient = new DefaultHttpClient();

      // Prepare a request object
      HttpGet httpget = new HttpGet(urlMonitor.getUrl());

      // Execute the request
      HttpResponse response = httpclient.execute(httpget);

      // Examine the response status
      if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
      {
         throw new HttpReadContentException("Error reading content from url: " + urlMonitor.getUrl());
      }

      // Get hold of the response entity
      HttpEntity entity = response.getEntity();

      if (entity != null)
      {
         InputStream instream = entity.getContent();

         // read it with BufferedReader
         BufferedReader br = new BufferedReader(new InputStreamReader(instream, "UTF-8"));

         String line = br.readLine();
         while (line != null)
         {
            sb.append(line);
            line = br.readLine();
         }
         br.close();
      }

      return sb.toString();
   }
}