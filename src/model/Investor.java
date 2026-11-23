package model;

// Core investor entity — the person who invests in mutual funds
public class Investor {

    private String      investorId;        // Unique ID like "INV001"
    private String      name;
    private String      email;
    private String      phone;
    private Address     address;
    private BankAccount bankAccount;
    private Nominee     nominee;
    private RiskProfile riskProfile;       // Set after risk assessment
    private boolean     isActive;
    private String      registrationDate;  // yyyy-MM-dd

    // Constructor
    public Investor(String investorId, String name, String email, String phone,
                    Address address, BankAccount bankAccount, Nominee nominee,
                    String registrationDate) {
        this.investorId       = investorId;
        this.name             = name;
        this.email            = email;
        this.phone            = phone;
        this.address          = address;
        this.bankAccount      = bankAccount;
        this.nominee          = nominee;
        this.registrationDate = registrationDate;
        this.isActive         = true;
    }

    // Getters
    public String      getInvestorId()       { return investorId;       }
    public String      getName()             { return name;             }
    public String      getEmail()            { return email;            }
    public String      getPhone()            { return phone;            }
    public Address     getAddress()          { return address;          }
    public BankAccount getBankAccount()      { return bankAccount;      }
    public Nominee     getNominee()          { return nominee;          }
    public RiskProfile getRiskProfile()      { return riskProfile;      }
    public boolean     isActive()            { return isActive;         }
    public String      getRegistrationDate() { return registrationDate; }

    // Setters
    public void setInvestorId(String investorId)             { this.investorId       = investorId;       }
    public void setName(String name)                         { this.name             = name;             }
    public void setEmail(String email)                       { this.email            = email;            }
    public void setPhone(String phone)                       { this.phone            = phone;            }
    public void setAddress(Address address)                  { this.address          = address;          }
    public void setBankAccount(BankAccount bankAccount)      { this.bankAccount      = bankAccount;      }
    public void setNominee(Nominee nominee)                  { this.nominee          = nominee;          }
    public void setRiskProfile(RiskProfile riskProfile)      { this.riskProfile      = riskProfile;      }
    public void setActive(boolean active)                    { this.isActive         = active;           }
    public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }

    @Override
    public String toString() {
        return "[" + investorId + "] " + name +
               " | " + email + " | " + phone +
               " | Active: " + (isActive ? "Yes" : "No") +
               " | Joined: " + registrationDate;
    }
}
