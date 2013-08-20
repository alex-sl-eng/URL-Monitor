/**
 * 
 */
package org.urlMonitor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.urlMonitor.exception.HttpReadContentException;
import org.urlMonitor.model.Monitor;

/**
 * 
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 * 
 */
@Slf4j
public class HttpUtil
{
   public static String readContent(Monitor monitor) throws HttpReadContentException, ClientProtocolException,
         IOException
   {
      HttpClient httpclient = new DefaultHttpClient();
      httpclient.getConnectionManager().closeIdleConnections(new Long(1L), TimeUnit.MINUTES);
      
      configureSSLHandling(httpclient);

      // Prepare a request object
      HttpGet httpget = new HttpGet(monitor.getUrl());

      // Execute the request
      HttpResponse response = httpclient.execute(httpget);

      // Examine the response status
      if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
      {
         throw new HttpReadContentException("Error reading content from url: " + monitor.getUrl());
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

      httpclient.getConnectionManager().shutdown();
      return sb.toString();
   }

   private static void configureSSLHandling(HttpClient httpclient)
   {
      Scheme http = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
      SSLSocketFactory sf = buildSSLSocketFactory();
      Scheme https = new Scheme("https", 443, sf);
      SchemeRegistry sr = httpclient.getConnectionManager().getSchemeRegistry();
      sr.register(http);
      sr.register(https);
   }

   private static SSLSocketFactory buildSSLSocketFactory()
   {
      TrustStrategy ts = new TrustStrategy()
      {
         public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
               throws java.security.cert.CertificateException
         {
            return true;
         }
      };

      SSLSocketFactory sf = null;

      try
      {
         /* build socket factory with hostname verification turned off. */
         sf = new SSLSocketFactory(ts, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      }
      catch (NoSuchAlgorithmException e)
      {
         log.error("Failed to initialize SSL handling.", e);
      }
      catch (KeyManagementException e)
      {
         log.error("Failed to initialize SSL handling.", e);
      }
      catch (KeyStoreException e)
      {
         log.error("Failed to initialize SSL handling.", e);
      }
      catch (UnrecoverableKeyException e)
      {
         log.error("Failed to initialize SSL handling.", e);
      }

      return sf;
   }
}
