package org.urlMonitor.util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;

@Slf4j
public class HttpClientBuilder
{
   public static HttpClient buildClient()
   {
      HttpClient httpclient = new DefaultHttpClient();
      configureSSLHandling(httpclient);

      return httpclient;
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
