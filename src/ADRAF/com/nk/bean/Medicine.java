package ADRAF.com.nk.bean;

public class Medicine {
    private String encoding;
    private String name;
    private String adverseReaction;
    private String contraindication;


    public Medicine() {
    }

    public Medicine(String encoding, String name, String adverseReaction, String contraindication) {
        this.encoding = encoding;
        this.name = name;
        this.adverseReaction = adverseReaction;
        this.contraindication = contraindication;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdverseReaction() {
        return adverseReaction;
    }

    public void setAdverseReaction(String adverseReaction) {
        this.adverseReaction = adverseReaction;
    }

    public String getContraindication() {
        return contraindication;
    }

    public void setContraindication(String contraindication) {
        this.contraindication = contraindication;
    }
}
