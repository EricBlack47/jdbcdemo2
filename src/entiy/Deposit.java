package entiy;

public class Deposit {
    private Integer savingID;
    private String savingName;
    private String descrip;

    public Integer getSavingID() {
        return savingID;
    }

    public void setSavingID(Integer savingID) {
        this.savingID = savingID;
    }

    public String getSavingName() {
        return savingName;
    }

    public void setSavingName(String savingName) {
        this.savingName = savingName;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String discrip) {
        this.descrip = discrip;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "savingID=" + savingID +
                ", savingName='" + savingName + '\'' +
                ", descrip='" + descrip + '\'' +
                '}';
    }
}
