package org.aeng.urlMonitor.shared.model;

import java.util.Date;

import org.aeng.urlMonitor.shared.model.type.AccessType;


/**
 * @author aeng Alex Eng - loones1595@gmail.com
 *
 */
public class UrlMonitor extends BaseModel
{
   private String name;
   private String description;
   
   private String url;
   private AccessType access;
   private String regexCheck;
   private String emailToIfFail;

   private Date startTime;
   private Date endTime;
   
   
   public String getName()
   {
      return name;
   }
   public void setName(String name)
   {
      this.name = name;
   }
   public String getDescription()
   {
      return description;
   }
   public void setDescription(String description)
   {
      this.description = description;
   }
   public String getUrl()
   {
      return url;
   }
   public void setUrl(String url)
   {
      this.url = url;
   }
   public AccessType getAccess()
   {
      return access;
   }
   public void setAccess(AccessType access)
   {
      this.access = access;
   }
   public String getRegexCheck()
   {
      return regexCheck;
   }
   public void setRegexCheck(String regexCheck)
   {
      this.regexCheck = regexCheck;
   }
   public String getEmailToIfFail()
   {
      return emailToIfFail;
   }
   public void setEmailToIfFail(String emailToIfFail)
   {
      this.emailToIfFail = emailToIfFail;
   }
   public Date getStartTime()
   {
      return startTime;
   }
   public void setStartTime(Date startTime)
   {
      this.startTime = startTime;
   }
   public Date getEndTime()
   {
      return endTime;
   }
   public void setEndTime(Date endTime)
   {
      this.endTime = endTime;
   }
   
   
}
