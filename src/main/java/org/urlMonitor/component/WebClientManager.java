package org.urlMonitor.component;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebClientManager
{
   private final Map<Long, WebClient> webClientList = new HashMap<Long, WebClient>();
   
   private final HttpClient client = buildClient();
   
   public WebClient getOrCreateWebClient(Long id, String url)
   {
      if (webClientList.containsKey(id))
      {
         return webClientList.get(id);
      }
      else
      {
         return registerNewWebClient(id, url);
      }
   }

   private WebClient registerNewWebClient(Long id, String url)
   {
      WebClient newClient = new WebClientImpl(url,  client);
      webClientList.put(id, newClient);

      return newClient;
   }
   
   private HttpClient buildClient()
   {
      PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
      configureSSLHandling(cm);
      
      HttpClient httpclient = new DefaultHttpClient(cm);
      
      return httpclient;
   }

   private void configureSSLHandling(PoolingClientConnectionManager connectionManager)
   {
      Scheme http = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
      SSLSocketFactory sf = buildSSLSocketFactory();
      Scheme https = new Scheme("https", 443, sf);
      SchemeRegistry sr = connectionManager.getSchemeRegistry();
      sr.register(http);
      sr.register(https);
   }

   private SSLSocketFactory buildSSLSocketFactory()
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
