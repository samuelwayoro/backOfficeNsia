package com.sbs.easymbank.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="logs")
@XmlRootElement
@Cacheable(false)
@NamedQueries({@javax.persistence.NamedQuery(name="Logs.findAll", query="SELECT l FROM Logs l order by l.dateLog desc "), 
    @javax.persistence.NamedQuery(name="Logs.findByIdlogs", query="SELECT l FROM Logs l WHERE l.idlogs = :idlogs"),
    @javax.persistence.NamedQuery(name="Logs.findByAction", query="SELECT l FROM Logs l WHERE l.action = :action"),
    @javax.persistence.NamedQuery(name="Logs.findByDate", query="SELECT l FROM Logs l WHERE l.dateLog = :dateLog"), 
    @javax.persistence.NamedQuery(name="Logs.findByLogin", query="SELECT l FROM Logs l WHERE l.login = :login"),
    @javax.persistence.NamedQuery(name="Logs.findByModule", query="SELECT l FROM Logs l WHERE l.module = :module")})
public class Logs
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional=false)
    @TableGenerator(name="LOGS_IDLOGS_SEQ",table="SEQUENCE",pkColumnName = "SEQ_NAME",valueColumnName = "SEQ_COUNT",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "LOGS_IDLOGS_SEQ")
  @NotNull
  @Column(name="idlogs")
  private Integer idlogs;
  @Size(max=255)
  @Column(name="action")
  private String action;
  @Column(name="machine")
  private String machine;
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateLog;
  @Size(max=255)
  @Column(name="message")
  private String message;
  @Size(max=255)
  @Column(name="login")
  private String login;
  @Size(max=255)
  @Column(name="module")
  private String module;
  @Transient
  private String agence;
  @JoinColumn(name = "USERS", referencedColumnName = "IDUSERS")
    @ManyToOne
    private Users users;

  
  public Logs() {}
  
  public Logs(Users users){
      this.users = users;
  }
  
  public Logs(Integer idlogs)
  {
    this.idlogs = idlogs;
  }
  
  public Integer getIdlogs()
  {
    return this.idlogs;
  }
  
  public void setIdlogs(Integer idlogs)
  {
    this.idlogs = idlogs;
  }
  
  public String getAction()
  {
    return this.action;
  }
  
  public void setAction(String action)
  {
    this.action = action;
  }

    public Date getDateLog() {
        return dateLog;
    }

    public void setDateLog(Date dateLog) {
        this.dateLog = dateLog;
    }
  

  
  public String getLogin()
  {
    return this.login;
  }
  
  public void setLogin(String login)
  {
    this.login = login;
  }
  
  public String getModule()
  {
    return this.module;
  }
  
  public void setModule(String module)
  {
    this.module = module;
  }

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }
  
  
  public int hashCode()
  {
    int hash = 0;
    hash += (this.idlogs != null ? this.idlogs.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object)
  {
    if (!(object instanceof Logs)) {
      return false;
    }
    Logs other = (Logs)object;
    if (((this.idlogs == null) && (other.idlogs != null)) || ((this.idlogs != null) && (!this.idlogs.equals(other.idlogs)))) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return "com.sbs.easymbank.entities.Logs[ idlogs=" + this.idlogs + " ]";
  }
  
  public String getMachine()
  {
    return this.machine;
  }
  
  public void setMachine(String machine)
  {
    this.machine = machine;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
  
  
}
