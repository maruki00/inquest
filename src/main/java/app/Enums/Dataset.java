package app.Enums;

public enum Dataset {
    ApacheAccess("apache-access");
    private final String val;
    Dataset(String val){
        this.val = val;
    }

    @Override
    public String toString(){
        return this.val;
    }
}
