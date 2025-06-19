package Model;

import java.util.Date;

public class User {
    private String fullName;
    private String email;
    private String password;
    private Date dateOfBirth;
    private String gender;
    private double balance; // Add balance field
        
     private double amountField;
    private String recipientAccount;
    private String pwd;
    

    // Getters and Setters
    public String getFullName() { 
        return fullName; 
    }
    public void setFullName(String fullName) { 
        this.fullName = fullName; 
    }
    
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    public Date getDateOfBirth() { 
        return dateOfBirth; 
    }
    public void setDateOfBirth(Date dateOfBirth) { 
        this.dateOfBirth = dateOfBirth; 
    }
       public String getGender() { 
        return gender; 
    }
    public void setgender(String gender) { 
        this.gender = gender ;
    }
    
  

    // Add getter and setter for balance
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
  
    // Getters and Setters
    public double getAmountField() { 
        return amountField; 
    }
    public void setAmountField(double amountField) { 
        this.amountField = amountField; 
    }
    
    public String getrecepientAccount() { 
        return recipientAccount; 
    }
    public void setrecepientAccount(String recipientAccount) { 
        this.recipientAccount = recipientAccount; 
    }
    public String getPwd(){
        return pwd;
}
    public void setPwd(String pwd){
        this.pwd=pwd;
    }

    public void setGender(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  
}

