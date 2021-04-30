package miu.edu.product.domain;

import java.util.Date;

public class Transaction {
    private long id;
    private TrasactionStatus status;
    private Date date;
    private double value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TrasactionStatus getStatus() {
        return status;
    }

    public void setStatus(TrasactionStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
