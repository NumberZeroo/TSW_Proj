package model.invoice;

import model.infoConsegna.InfoConsegnaBean;
import model.orderItem.OrderItemBean;

import java.sql.Date;
import java.util.List;

public class InvoiceBean {
    private long orderId;
    private Date date;
    private InfoConsegnaBean infoConsegna;
    private String customer;
    private List<OrderItemBean> orderItems;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public InfoConsegnaBean getInfoConsegna() {
        return infoConsegna;
    }

    public void setInfoConsegna(InfoConsegnaBean infoConsegna) {
        this.infoConsegna = infoConsegna;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<OrderItemBean> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemBean> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "InvoiceBean{" +
                "orderId=" + orderId +
                ", date=" + date +
                ", infoConsegna=" + infoConsegna +
                ", customer='" + customer + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}
