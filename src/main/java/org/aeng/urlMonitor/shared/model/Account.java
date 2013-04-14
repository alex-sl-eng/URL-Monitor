/**
 * 
 */
package org.aeng.urlMonitor.shared.model;

/**
 * @author aeng
 *
 */
public class Account extends BaseModel
{
   private String username;
   private String hashPass;
   
   private String email;
   private Boolean activate;
   
   
   public String getUsername()
   {
      return username;
   }
   public void setUsername(String username)
   {
      this.username = username;
   }
   public String getHashPass()
   {
      return hashPass;
   }
   public void setHashPass(String hashPass)
   {
      this.hashPass = hashPass;
   }
   public String getEmail()
   {
      return email;
   }
   public void setEmail(String email)
   {
      this.email = email;
   }
   public Boolean getActivate()
   {
      return activate;
   }
   public void setActivate(Boolean activate)
   {
      this.activate = activate;
   }
}
