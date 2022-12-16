CS611-Bank
Author:Shu Xing (shuxing@bu.edu), Tingzhou Yuan (tzyuan15@bu.edu),Srinivas Chellaboina (srinevas@bu.edu), Anoohya Veerapaneni (anuuv@bu.edu) 

-----------------------------------------------------
How to compile:
**This project uses external dependencies and building from source with just the terminal and javac wasn’t exactly ideal, either maven or other dependency manager was needed, and we couldn’t guarantee that**. Therefore we built a runnable jar. To actually run the project from source, please use intellij and run src/Main.main()
Otherwise: to run the jar:

Cd cs611-bank/out/artifacts/cs611_bank_jar
java -jar .\cs611-bank.jar
The project should boot.
-----------------------------------------------------


Class description:
 * @enumName: AccountType
 * @description: enum of AccountType, include Checking, Saving, Loan and Security.

 * @className: BankBackend
 * @description: class of BankBackend, used for mock user login and register.

 *@className: Config
 *@description: modifying the parameters in this class is equivalent to setting the system parameter. For example, the system time controversial and the interest rate number.

 *@className: DaoManager
 *@description: used to set JDBC connection to database.

 *@className: Account
 *@description: class of Account

 *@className: CheckingAccount
 *@description: class of CheckingAccount

 *@className: SavingAccount
 *@description: class of SavingAccount

 *@className: SecurityAccount
 *@description: class of SecurityAccount

 *@className: LoanAccount
 *@description: class of LoanAccount

 *@enumName: AccountState
 *@description: enum of AccountState, include open, closed and inactive

 *@enumName: CurrencyType
 *@description: enum of CurrencyType, include USD, EUR and GBP

 *@enumName: IRCalcMethod
 *@description: enum of IRCalcMethod, include simple and compound

 *@enumName: RateType
 *@description: enum of RateType, include save and loan

 *@enumName: TransactionType
 *@description: enum of TransactionType, include DEPOSIT, WITHDRAW, TRANSFER, CHARGE_FEE, INTEREST and STOCK.

 *@className: Balance
 *@description: class of Balance, have functions which can create balance, find balance and modify the balance value.

 *@className: DateTime
 *@description: class of DateTime, return current time of system.

 *@className: InterestRate
 *@description: manages all interest rates

 *@className: Report
 *@description: class of Report, return the report of transaction history of user.

 *@className: Stock
 *@description: class of Stock

 *@className: Transaction
 *@description: class of Transaction

 *@className: User
 *@description: class of User, have functions of get all kinds of Account, and set if a user is manager.

 *@interfaceName: AccountService 
 *@description: interface of AccountService, include some kind of service, this is implemented by AccountCtl

 *@interfaceName: DateTimeService
 *@description: interface of DateTimeService, include functions which can set system time, this interface is implemented by DateTimeCtl

 *@interfaceName: ISession
 *@description: interface of ISession, through the session we can get the user, implemented by SessionCtl.

 *@interfaceName: InterestRateService
 *@description: interface of InterestRateService, provide the service of interest rate, implemented by InterestRateCtl

 *@interfaceName: SQLService
 *@description: interface of SQLService, allows managers to run raw SQL queries on the db

 *@interfaceName: SessionService
 *@description: interface of SessionService, used to set session, implemented by SessionCtl.

 *@interfaceName: StockService
 *@description: interface of StockService, used to provide all kinds of service of stock, implemented by StockCtl.

 *@interfaceName SvcMgr 
 *@description the single endpoint for the frontend to call backend interfaces, returns the implementation of said interfaces

 *@interfaceName UserService
 *@description defines the userservice interface, which allows looking up and listing accounts.

 *@className AbstractUserFactory
 *@description defines the abstract methods: createUser

 *@className DefautlUserFactory
 *@description Defines the creation method of a normal user

 *@className ManagerFacotry
 *@description Defines the creation of a privileged user

 *@className MemorySession
 *@description Implements the ISession interface and allows the system to know the current user of the system


 *@className AccountCtl
 *@description implements accountservice: handleTransaction, getaccountbyid, listbalance…


 *@className DateTimeCtl
 *@description implements the time simulator

 *@className InterestRateCtl
 *@description implements the interest rate controller, allows users to start saving/loans

 *@className SessionCtl
 *@description implements the SessionService interface, allows system to know the current user of the system,

 *@className SqlCtl
 *@description proxy for the database

 *@className StockCtl
 *@description implements the stockmarketservice interface, allows stockmarket manager and users to trade stocks

 *@className UserCtl
 *@description implements the userservice interface, allows system to lookup users


 *@className AccountPanel
 *@description generic account panel, allows users to view their account state, composites several balanacesummaries


 *@className CheckingPanel
 *@description inherits accountpanel, has checking-specific buttons

 *@className LoanPanel
 *@description inherits accountPnael, has loan-spefic buttons

 *@className SavingPanel
 *@description inherits savingpanel, has saving-specific buttons

 *@className SecurityPanel
 *@description inherits accountpanel, has a link to stock market


 *@className AccountStateUI
 *@description a separate jframe that allows users to modify any account status

 *@className AccountSummary
 *@description a panel that can be integrated into any jfrmae that allows users to view summary of any account

 *@className BalanceSummary
 *@description a panel that can be integrated anywhere, allows users to view summary of any balance

 *@className moneyIOUI
 *@description a separate jframe allows users to deposit/withdraw money

 *@className TxnBuilderUI
 *@description a separate jframe , allows users to build a transaction to any other account

 *@className HomeUI
 *@description the login user’s landing page, has links to everywhere

 *@className LoanApplicationUI
 *@description a separate page allows users to create new loans

 *@className LoanUI
 *@description a separate page that allows users to view all past loans

 *@className SaveApplicationUI
 *@description a separate page allows users to create new saving requests

 *@className SavingUI
 *@description a separate page that allows users to view all past savings

 *@className Authentication
 *@description the user login/register page

 *@className StockUI
 *@description the stock market UI for all users, allows buy/sell/view earnings

 *@className ConfigMgr
 *@description a component for the manager panel, allows editing of config class variable values

 *@className MgmtPage
 *@description the jframe container for all manager actions

 *@className MgmtUserItem
 *@description a single user listing, can be composited inside mgmtpage to view briefings of all users

 *@className SQLMgr
 *@description a component for managers to run raw sql queries

 *@className StockMgr
 *@description a component for managers to add/remove/update stocks


 *@className AlertUI
 *@description a component for system to send useful contextual information to users

 *@className SystemMgr
 *@description the component to manage system running status: start a new login, time ratio adjustment…

 *@className UIContextMgr
 *@description workaround of restrictions of netbeans, stores contextual information for all jframe/jpanels.

-----------------------------------------------------
Please see sample.png under root dir.






