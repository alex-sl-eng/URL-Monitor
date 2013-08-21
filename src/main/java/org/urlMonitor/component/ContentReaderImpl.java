package org.urlMonitor.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.urlMonitor.exception.HttpReadContentException;
import org.urlMonitor.util.HttpClientBuilder;

public class ContentReaderImpl implements ContentReader
{
   private static final Long ALLOW_IDLE_TIME = 1L;

   private final HttpClient httpclient;

   public ContentReaderImpl(String url)
   {
      httpclient = HttpClientBuilder.buildClient(ALLOW_IDLE_TIME);
   }

   @Override
   public String readContent(String url) throws HttpReadContentException, ClientProtocolException, IOException
   {
      HttpGet httpget = new HttpGet(url);

      // Execute the request
      HttpResponse response = httpclient.execute(httpget);

      // Examine the response status
      if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
      {
         throw new HttpReadContentException("Error reading content from url: " + url);
      }

      // Get hold of the response entity
      HttpEntity entity = response.getEntity();
      StringBuilder sb = new StringBuilder();
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

      httpclient.getConnectionManager().closeExpiredConnections();
      return sb.toString();
   }
}
