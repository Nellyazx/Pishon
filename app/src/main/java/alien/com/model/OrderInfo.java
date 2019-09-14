package alien.com.model;

public class OrderInfo
{
    private String ordercustname;
    private String orderitem;
    private String orderquantity;
    private String orderprice;

    public OrderInfo(String ordercustname,String orderitem,String orderquantity,String orderprice)
    {
        this.ordercustname = ordercustname;
        this.orderitem = orderitem;
        this.orderquantity = orderquantity;
        this.orderprice= orderprice;

    }
    public String getOrdercustname()
    {
        return ordercustname;
    }

    public String getOrderitem() {
        return orderitem;
    }

    public String getOrderquantity() {
        return orderquantity;
    }

    public String getOrderprice() {
        return orderprice;
    }
}
