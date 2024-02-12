package uc.no.identity.datalayer.users;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import uc.no.identity.annotations.Id;
import uc.no.identity.annotations.NotNull;
import uc.no.identity.annotations.SqlType;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class UsersDto  implements Serializable {

    public UsersDto () {
        role = "User";
        language = "nbNO";
    }
    
    @SqlType(isId = true, notNull = true)
    private String id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SqlType(fieldName = "UserName", fieldType = "Varchar (200)")
    private String userName;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String fullName;
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private String passwordHash;
        
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    
     
     private String role;
     public String getRole() {
         return role;
        }
        
        public void setRole(String role) {
            this.role = role;
        }
        
        private String language;
        public String getLanguage() {
            return language;
        }
        
        public void setLanguage(String language) {
            this.language = language;
        }
        
    }
    