package vn.finiex.shipperapp.model;

public class Order {
	private int _ID;

	private boolean IsLog;

    private String _EndDate;

    private String _Notes;

    private int _Status;

    private String ErrorMessage;

    private int _LadingID;

    private int _OrderID;

    private String _DateCreated;

    private String OldValue;

    private String Noilay;
    private String Noitra;

    private String Prices;
    private String PriceShip;

    private String OrderName;
    private int Status;

    private String Phone;

    private String Address;

    private String EndDate;

    private String NguoiNhan;

    private String Phone_AM;

    private String Notes;

    public boolean isLog() {
        return IsLog;
    }

    public void setLog(boolean log) {
        IsLog = log;
    }

    public String getNoilay() {
        return Noilay;
    }

    public void setNoilay(String noilay) {
        Noilay = noilay;
    }

    public String getPrices() {
        return Prices;
    }

    public void setPrices(String prices) {
        Prices = prices;
    }

    public String getPriceShip() {
        return PriceShip;
    }

    public void setPriceShip(String priceShip) {
        PriceShip = priceShip;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getNguoiNhan() {
        return NguoiNhan;
    }

    public void setNguoiNhan(String nguoiNhan) {
        NguoiNhan = nguoiNhan;
    }

    public String getPhone_AM() {
        return Phone_AM;
    }

    public void setPhone_AM(String phone_AM) {
        Phone_AM = phone_AM;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public boolean getIsLog ()
    {
        return IsLog;
    }

    public void setIsLog (boolean IsLog)
    {
        this.IsLog = IsLog;
    }

    public String get_EndDate ()
    {
        return _EndDate;
    }

    public void set_EndDate (String _EndDate)
    {
        this._EndDate = _EndDate;
    }

    public String get_Notes ()
    {
        return _Notes;
    }

    public void set_Notes (String _Notes)
    {
        this._Notes = _Notes;
    }

    public int get_Status ()
    {
        return _Status;
    }

    public void set_Status (int _Status)
    {
        this._Status = _Status;
    }

    public String getErrorMessage ()
    {
        return ErrorMessage;
    }

    public void setErrorMessage (String ErrorMessage)
    {
        this.ErrorMessage = ErrorMessage;
    }

    public int get_LadingID ()
    {
        return _LadingID;
    }

    public void set_LadingID (int _LadingID)
    {
        this._LadingID = _LadingID;
    }

    public int get_OrderID ()
    {
        return _OrderID;
    }

    public void set_OrderID (int _OrderID)
    {
        this._OrderID = _OrderID;
    }

    public String get_DateCreated ()
    {
        return _DateCreated;
    }

    public void set_DateCreated (String _DateCreated)
    {
        this._DateCreated = _DateCreated;
    }

    public String getOldValue ()
    {
        return OldValue;
    }

    public void setOldValue (String OldValue)
    {
        this.OldValue = OldValue;
    }

    public int get_ID ()
    {
        return _ID;
    }

    public void set_ID (int _ID)
    {
        this._ID = _ID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [IsLog = "+IsLog+", _EndDate = "+_EndDate+", _Notes = "+_Notes+", _Status = "+_Status+", ErrorMessage = "+ErrorMessage+", _LadingID = "+_LadingID+", _OrderID = "+_OrderID+", _DateCreated = "+_DateCreated+", OldValue = "+OldValue+", _ID = "+_ID+"]";
    }

    public String getNoitra() {
        return Noitra;
    }

    public void setNoitra(String noitra) {
        Noitra = noitra;
    }
}
