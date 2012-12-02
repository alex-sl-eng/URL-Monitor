/**
 * 
 */
package org.aeng.urlMonitor.shared.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author aeng
 *
 */
public class BaseModel implements IsSerializable
{
   private Long id;
   
   private String lastModifiedBy;
   private Date lastModifiedDate;
   
   private String createdBy;
   private Date createdDate;
   
   
   public String getLastModifiedBy()
   {
      return lastModifiedBy;
   }
   public void setLastModifiedBy(String lastModifiedBy)
   {
      this.lastModifiedBy = lastModifiedBy;
   }
   public Date getLastModifiedDate()
   {
      return lastModifiedDate;
   }
   public void setLastModifiedDate(Date lastModifiedDate)
   {
      this.lastModifiedDate = lastModifiedDate;
   }
   public String getCreatedBy()
   {
      return createdBy;
   }
   public void setCreatedBy(String createdBy)
   {
      this.createdBy = createdBy;
   }
   public Date getCreatedDate()
   {
      return createdDate;
   }
   public void setCreatedDate(Date createdDate)
   {
      this.createdDate = createdDate;
   }
}
