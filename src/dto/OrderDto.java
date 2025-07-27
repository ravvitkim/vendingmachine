package dto;

import java.time.LocalDateTime;

public class OrderDto {
    private int u_id;
    private int p_id;
    private int price;
    private LocalDateTime order_date;

    @Override
    public String toString() {
        return "OrderDto{" +
                "u_id=" + u_id +
                ", p_id=" + p_id +
                ", price=" + price +
                ", order_date=" + order_date +
                '}';
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDateTime order_date) {
        this.order_date = order_date;
    }
}
