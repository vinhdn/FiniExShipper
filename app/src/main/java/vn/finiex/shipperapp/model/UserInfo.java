package vn.finiex.shipperapp.model;

public class UserInfo {
	private String aPayment;

	private String Phone;

	private String TotalOrder;

	private String aOutoMoney;

	private String UserID;

	private String RoleID;

	private String DistrictID;

	private String pShip;

	private String Active;

	private String Email;

	private String FullName;

	private String Address;

	private String Latitude;

	private String Longitude;

	private String LocaltionCurrent;

	private String pMoney;

	public String getAPayment() {
		return aPayment;
	}

	public void setAPayment(String aPayment) {
		this.aPayment = aPayment;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String Phone) {
		this.Phone = Phone;
	}

	public String getTotalOrder() {
		return TotalOrder;
	}

	public void setTotalOrder(String TotalOrder) {
		this.TotalOrder = TotalOrder;
	}

	public String getAOutoMoney() {
		return aOutoMoney;
	}

	public void setAOutoMoney(String aOutoMoney) {
		this.aOutoMoney = aOutoMoney;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String UserID) {
		this.UserID = UserID;
	}

	public String getRoleID() {
		return RoleID;
	}

	public void setRoleID(String RoleID) {
		this.RoleID = RoleID;
	}

	public String getDistrictID() {
		return DistrictID;
	}

	public void setDistrictID(String DistrictID) {
		this.DistrictID = DistrictID;
	}

	public String getPShip() {
		return pShip;
	}

	public void setPShip(String pShip) {
		this.pShip = pShip;
	}

	public String getActive() {
		return Active;
	}

	public void setActive(String Active) {
		this.Active = Active;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String Email) {
		this.Email = Email;
	}

	public String getFullName() {
		return FullName;
	}

	public void setFullName(String FullName) {
		this.FullName = FullName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String Latitude) {
		this.Latitude = Latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String Longitude) {
		this.Longitude = Longitude;
	}

	public String getLocaltionCurrent() {
		return LocaltionCurrent;
	}

	public void setLocaltionCurrent(String LocaltionCurrent) {
		this.LocaltionCurrent = LocaltionCurrent;
	}

	public String getPMoney() {
		return pMoney;
	}

	public void setPMoney(String pMoney) {
		this.pMoney = pMoney;
	}

	@Override
	public String toString() {
		return "ClassPojo [aPayment = " + aPayment + ", Phone = " + Phone + ", TotalOrder = " + TotalOrder
				+ ", aOutoMoney = " + aOutoMoney + ", UserID = " + UserID + ", RoleID = " + RoleID + ", DistrictID = "
				+ DistrictID + ", pShip = " + pShip + ", Active = " + Active + ", Email = " + Email + ", FullName = "
				+ FullName + ", Address = " + Address + ", Latitude = " + Latitude + ", Longitude = " + Longitude
				+ ", LocaltionCurrent = " + LocaltionCurrent + ", pMoney = " + pMoney + "]";
	}

}
