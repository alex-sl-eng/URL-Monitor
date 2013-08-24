package org.urlMonitor.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.urlMonitor.model.type.StatusType;

public class WebClientImpl implements WebClient
{
   private final HttpClient httpClient;
   private final HttpGet httpGet;
   private HttpResponse httpResponse;

   public WebClientImpl(String url, HttpClient httpClient)
   {
      this.httpClient = httpClient;
      this.httpGet = new HttpGet(url);
   }

   @Override
   public StatusType checkStatus(String url, String contentRegex) throws ClientProtocolException, IOException
   {
      // Execute the request
      httpResponse = httpClient.execute(httpGet);

      StatusType result = StatusType.Failed;

      if (!isHttpResponseOK(httpResponse))
      {
         result = StatusType.Failed;
      }
      else
      {
         if (!StringUtils.isEmpty(contentRegex))
         {
            // Get hold of the response entity
            HttpEntity entity = httpResponse.getEntity();
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
               instream.close();
            }

            Pattern pattern = Pattern.compile(contentRegex);
            Matcher matcher = pattern.matcher(sb.toString());

            if (matcher.find())
            {
               result = StatusType.Pass;
            }
         }
         else
         {
            result = StatusType.Pass;
         }
      }

      close();
      return result;
   }

   private boolean isHttpResponseOK(HttpResponse response)
   {
      return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
   }

   @Override
   public void close()
   {
      EntityUtils.consumeQuietly(httpResponse.getEntity());
      httpGet.releaseConnection();
      httpClient.getConnectionManager().closeIdleConnections(1L, TimeUnit.SECONDS);
      httpClient.getConnectionManager().closeExpiredConnections();
   }
}
