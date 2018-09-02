package cn.edu.ncu.liuqing.banksavingsystem.entities;

import cn.edu.ncu.liuqing.banksavingsystem.connectDB.OperationsForDB;
import cn.edu.ncu.liuqing.banksavingsystem.dao.ClerkDAO;
import cn.edu.ncu.liuqing.banksavingsystem.exception.MyException;
import cn.edu.ncu.liuqing.banksavingsystem.tools.FileOP;
import cn.edu.ncu.liuqing.banksavingsystem.tools.InterestCalculator;
import javafx.fxml.FXML;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 职员实体类
 *          特征：职员工号（5位）、职员密码(8-18位随机数字字母)、身份证号(18位)、职员姓名（2-4个汉字）、入职时间、家庭住址、手机号码（11位数字）
 *
 *          功能: 开户
 *                销户
 *                查询用户信息
 *                办理定期存款手续
 *                办理定期取款手续
 *                办理活期存款手续
 *                办理活期取款手续
 *                为用户查询明细
 *                为用户的账户修改密码（6位）
 */
public class Clerk implements ClerkDAO {
    private short number;
    private String password;
    private String idCardNo;
    private String name;
    private Date inTime;
    private String address;
    private long phoneNumber;

    public Clerk() {
    }

    public Clerk(short number) {
        this.number = number;
    }


    public Clerk(String password, String idCardNo, String name, Date inTime, String address, long phoneNumber) {
        this.password = password;
        this.idCardNo = idCardNo;
        this.name = name;
        this.inTime = inTime;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /* 开定期帐户 */
    @Override
    public void addFixedAccount(FixedAccount account) {
        Connection connection = OperationsForDB.getConnection();
        String sql1 = "select max(No) from FixedAccount";
        String sql2 = "insert into FixedAccount values(?,?,?,?,?)";
        long maxNo = 0;
        try {
            Statement sta = connection.createStatement();
            ResultSet resultSet = sta.executeQuery(sql1);
            while (resultSet.next())
                maxNo = resultSet.getLong(1);
            if (maxNo == 0)
                maxNo = 51000000000000000L;
            account.setNo(maxNo + 1);
            PreparedStatement pSta = connection.prepareStatement(sql2);
            pSta.setLong(1,account.getNo());
            pSta.setInt(2,account.getPassword());
            pSta.setString(3,account.getName());
            pSta.setDate(4,new java.sql.Date(account.getOpenTime().getTime()));
            pSta.setBoolean(5,account.isSaving());
            pSta.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
        insertIntoFixedDetails(account.getNo(),new Timestamp(account.getOpenTime().getTime()),"开户",
                null,null,null,0,0,
                (short) FileOP.readFromFile());
    }

    /* 开活期账户 */
    @Override
    public void addDemandAccount(DemandAccount demandAccount) {
        Connection connection = OperationsForDB.getConnection();
        String sql1 = "select max(No) from DemandAccount";
        String sql2 = "insert into DemandAccount values(?,?,?,?)";
        String maxString = "0";
        BigInteger max = null;
        try {
            Statement sta = connection.createStatement();
            ResultSet resultSet = sta.executeQuery(sql1);
            while (resultSet.next()){
                maxString = resultSet.getString(1);
            }
            if (maxString == null || maxString.equals("0"))
                maxString = "6217002020050001000";
            max = new BigInteger(maxString);
            max = max.add(new BigInteger("1"));
            demandAccount.setNo(max.toString());
            PreparedStatement pSta = connection.prepareStatement(sql2);
            pSta.setString(1,demandAccount.getNo());
            pSta.setInt(2,demandAccount.getPassword());
            pSta.setString(3,demandAccount.getName());
            pSta.setDate(4,new java.sql.Date(demandAccount.getOpenTime().getTime()));
            pSta.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
        insertIntoDemandDetails(demandAccount.getNo(),new Timestamp(demandAccount.getOpenTime().getTime()),
                "开户",0,0,0,0,(short)FileOP.readFromFile());
    }

    /* 销定期户 */
    @Override
    public void deleteFixedAccount(FixedAccount account) throws MyException {
        Connection connection = OperationsForDB.getConnection();
        try {
            PreparedStatement sta1 = connection.prepareStatement("select * from FixedAccount where no = ?");
            sta1.setLong(1,account.getNo());
            ResultSet resultSet = sta1.executeQuery();
            if (resultSet.next()) {
                int password = resultSet.getInt("Password");
                if (password == account.getPassword()) {
                    PreparedStatement sta2 = connection.prepareStatement("delete from fixeddetails where No=?");
                    sta2.setLong(1,account.getNo());
                    sta2.execute();
                    PreparedStatement sta3 = connection.prepareStatement("delete from fixedaccount where No=?");
                    sta3.setLong(1,account.getNo());
                    sta3.execute();
                }
                else
                    throw new MyException("密码错误!");
            }
            else
                throw new MyException("该账户不存在!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
    }

    /* 销活期户 */
    @Override
    public void deleteDemandAccount(DemandAccount account) throws MyException {
        Connection connection = OperationsForDB.getConnection();
        try {
            PreparedStatement sta1 = connection.prepareStatement("select * from demandaccount where no=?");
            sta1.setString(1,account.getNo());
            ResultSet resultSet = sta1.executeQuery();
            if (resultSet.next()) {
                if (account.getPassword() == resultSet.getInt("Password")) {
                    PreparedStatement sta2 = connection.prepareStatement("delete from demanddetails where No=?");
                    sta2.setString(1,account.getNo());
                    sta2.execute();
                    PreparedStatement sta3 = connection.prepareStatement("delete from demandaccount where No=?");
                    sta3.setString(1,account.getNo());
                    sta3.execute();
                }
                else
                    throw new MyException("密码错误!");
            }
            else
                throw new MyException("账户不存在!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
    }

    /* 查询余额 */
    public double selectBalance(DemandAccount account) throws MyException {
        Connection con = OperationsForDB.getConnection();
        try {
            PreparedStatement sta1 = con.prepareStatement("select * from demandaccount " +
                    "where No=?");
            sta1.setString(1,account.getNo());
            ResultSet set = sta1.executeQuery();
            if (set.next()){
                account.setName(set.getString("Name"));
                if (account.getPassword() == set.getInt("Password")) {
                    PreparedStatement sta2 = con.prepareStatement("SELECT Balance FROM demanddetails " +
                            "WHERE No=? and OperationTime=(SELECT MAX(OperationTime) from demanddetails where No=?)");
                    sta2.setString(1,account.getNo());
                    sta2.setString(2,account.getNo());
                    ResultSet resultSet = sta2.executeQuery();
                    while (resultSet.next())
                        return resultSet.getDouble(1);
                }
                else {
                    throw new MyException("密码错误!");
                }
            } else
                throw new MyException("账户不存在!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(con);
        }
        return 0;
    }




     /* 办理活期存款 */

    public void demandDeposit(DemandAccount account, double money,double balance) {
        double interest = InterestCalculator.CalculateDemandInterest(account);
        insertIntoDemandDetails(account.getNo(),new Timestamp(new Date().getTime()),
                "现存", money,0,
                money + interest + balance,
                interest,(short) FileOP.readFromFile());
    }

    /* 办理活期取款 */

    public void demandWithdraw(DemandAccount account, double money,double balance) {
        double interest = InterestCalculator.CalculateDemandInterest(account);
        insertIntoDemandDetails(account.getNo(),new Timestamp(new Date().getTime()),
                "现支",0,money,balance - money + interest,interest,
                (short) FileOP.readFromFile());
    }

    /* 查询用户明细 */
    @Override
    public List<FixedDetails> queryDetails(FixedAccount account) throws MyException {
        List<FixedDetails> fixedDetails = new ArrayList<>();
        Connection connection = OperationsForDB.getConnection();
        try {
            PreparedStatement sta1 = connection.prepareStatement("select * from fixedaccount where No = ?");
            sta1.setLong(1,account.getNo());
            ResultSet set = sta1.executeQuery();
            if (set.next()) {
                PreparedStatement sta2  =connection.prepareStatement("select * from fixeddetails where No=?");
                sta2.setLong(1,account.getNo());
                ResultSet resultSet = sta2.executeQuery();
                while (resultSet.next()) {
                    FixedDetails details = new FixedDetails();
                    details.setNo(resultSet.getLong("No"));
                    details.setOperationTime(resultSet.getTimestamp("OperationTime"));
                    details.setAbstracts(resultSet.getString("abstract"));
                    details.setSavingTime(resultSet.getString("SavingTime"));
                    details.setBegin(resultSet.getDate("Begin"));
                    details.setEnd(resultSet.getDate("End"));
                    details.setMoney(resultSet.getDouble("Money"));
                    details.setBenefit(resultSet.getDouble("Benifition"));
                    details.setClerkNo(resultSet.getShort("ClerkNo"));
                    fixedDetails.add(details);
                }
            }
            else
                throw new MyException("账户不存在!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
        return fixedDetails;
    }
    @Override
    public List<DemandDetails> queryDetails(DemandAccount account) throws MyException {
        List<DemandDetails> demandDetails = new ArrayList<>();
        Connection connection = OperationsForDB.getConnection();
        try {
            PreparedStatement sta1 = connection.prepareStatement("select * from demandaccount where No = ?");
            sta1.setString(1,account.getNo());
            ResultSet set = sta1.executeQuery();
            if (set.next()) {
                PreparedStatement sta2  = connection.prepareStatement("select * from demanddetails where No=?");
                sta2.setString(1,account.getNo());
                ResultSet resultSet = sta2.executeQuery();
                while (resultSet.next()) {
                    DemandDetails details = new DemandDetails();
                    details.setNo(resultSet.getString("No"));
                    details.setOperationTime(resultSet.getTimestamp("OperationTime"));
                    details.setAbstracts(resultSet.getString("Abstract"));
                    details.setIn(resultSet.getDouble("In"));
                    details.setOut(resultSet.getDouble("Out"));
                    details.setBalance(resultSet.getDouble("Balance"));
                    details.setInterest(resultSet.getDouble("Interest"));
                    details.setClerkNo(resultSet.getShort("ClerkNo"));
                    demandDetails.add(details);
                }
            }
            else
                throw new MyException("账户不存在!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
        return demandDetails;
    }

     /* 为定期用户修改密码*/
    @Override
    public void changePassword(FixedAccount account, int newPassword1,int newPassword2) throws MyException {
        Connection connection = OperationsForDB.getConnection();
        try {
            PreparedStatement sta1 = connection.prepareStatement("select * from fixedaccount where no=?");
            sta1.setLong(1,account.getNo());
            ResultSet result = sta1.executeQuery();
            if (result.next()) {
                if (result.getInt("Password") == account.getPassword()) {
                    if (newPassword1 == newPassword2) {
                        PreparedStatement sta2 = connection.prepareStatement("update fixedaccount set" +
                                " Password=? where no=?");
                        sta2.setInt(1,newPassword1);
                        sta2.setLong(2,account.getNo());
                        sta2.execute();
                    }
                    else
                        throw new MyException("两次密码不一样!");
                }
                else
                    throw new MyException("原始密码错误!");
            }
            else
                throw new MyException("该账户不存在!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
    }

    /* 为活期用户修改密码 */
    @Override
    public void changePassword(DemandAccount account, int newPassword1,int newPassword2) throws MyException {
        Connection connection = OperationsForDB.getConnection();
        try {
            PreparedStatement sta1 = connection.prepareStatement("select * from demandaccount where no=?");
            sta1.setString(1,account.getNo());
            ResultSet result = sta1.executeQuery();
            if (result.next()) {
                if (result.getInt("Password") == account.getPassword()) {
                    if (newPassword1 == newPassword2) {
                        PreparedStatement sta2 = connection.prepareStatement("update demandaccount set" +
                                " Password=? where no=?");
                        sta2.setInt(1,newPassword1);
                        sta2.setString(2,account.getNo());
                        sta2.execute();
                    }
                    else
                        throw new MyException("两次密码不一样!");
                }
                else
                    throw new MyException("原始密码错误!");
            }
            else
                throw new MyException("该账户不存在!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
    }

    /* 插入明细表 */
    public  void insertIntoFixedDetails(long no, Timestamp operationTime, String abstracts,
                                       String savingTime, Date begin, Date end,
                                       double money,double benefit, short clerkNo) {
        Connection connection = OperationsForDB.getConnection();
        String sql = "insert into FixedDetails values(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement sta = connection.prepareStatement(sql);
            sta.setLong(1,no);
            sta.setTimestamp(2,operationTime);
            sta.setString(3,abstracts);
            sta.setString(4,savingTime);
            if (begin == null)
                sta.setDate(5,null);
            else
                sta.setDate(5,new java.sql.Date(begin.getTime()));
            if (end == null)
                sta.setDate(6,null);
            else
                sta.setDate(6,new java.sql.Date(end.getTime()));
            sta.setDouble(7,money);
            sta.setDouble(8,benefit);
            sta.setShort(9,clerkNo);
            sta.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
    }

    /* 插入明细表 */
    public void insertIntoDemandDetails(String no, Timestamp operationTime, String abstracts,
                                        double in, double out, double balance, double interest, short clerkNo) {
        Connection connection = OperationsForDB.getConnection();
        String sql = "insert into DemandDetails values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement sta = connection.prepareStatement(sql);
            sta.setString(1,no);
            sta.setTimestamp(2,operationTime);
            sta.setString(3,abstracts);
            sta.setDouble(4,in);
            sta.setDouble(5,out);
            sta.setDouble(6,balance);
            sta.setDouble(7,interest);
            sta.setShort(8,clerkNo);
            sta.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OperationsForDB.closeConnection(connection);
        }
    }

    /* 办理定期存款 */
    @Override
    public void fixedDeposit(FixedAccount account, double money) {
    }

    /* 办理定期取款 */
    @Override
    public void fixedWithdraw(FixedAccount account, double money) {
    }

    public short getNumber() {
        return number;
    }

    public void setNumber(short number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clerk)) return false;
        Clerk clerk = (Clerk) o;
        return phoneNumber == clerk.phoneNumber &&
                Objects.equals(number, clerk.number) &&
                Objects.equals(password, clerk.password) &&
                Objects.equals(idCardNo, clerk.idCardNo) &&
                Objects.equals(name, clerk.name) &&
                Objects.equals(inTime, clerk.inTime) &&
                Objects.equals(address, clerk.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, password, idCardNo, name, inTime, address, phoneNumber);
    }
}
