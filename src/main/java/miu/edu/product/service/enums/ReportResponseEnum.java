package miu.edu.product.service.enums;

public enum ReportResponseEnum {
    TOTAL("total"),
    LIST("list");
    String value;

    public String value() {
        return this.value;
    }

    ReportResponseEnum(String value){
        this.value = value;
    }
}
