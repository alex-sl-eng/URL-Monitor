package org.urlMonitor.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@Service
@Scope("singleton")
@Slf4j
public class AvatarService
{
   private static final String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

   private static final String DEFAULT_AVATAR = "resources/images/user.png";

   public String getUserAvatar(String email, Integer size)
   {
      if (StringUtils.isEmpty(email))
      {
         return DEFAULT_AVATAR;
      }

      try
      {
         if (size != null)
         {
            return GRAVATAR_URL + getEmailHash(email) + "?s=" + size;
         }
         else
         {
            return GRAVATAR_URL + getEmailHash(email);
         }
      }
      catch (NoSuchAlgorithmException e)
      {
         log.error("Unable to generate email hash.");
         return DEFAULT_AVATAR;
      }
   }

   private String getEmailHash(String email) throws NoSuchAlgorithmException
   {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(email.getBytes());

      byte byteData[] = md.digest();

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < byteData.length; i++)
      {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
      }

      log.info("Digest(in hex format):: " + sb.toString());
      return sb.toString();
   }
}
