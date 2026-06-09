package ADRAF.com.nk.bean;

public class Records {
    private String username;
    private String meName;
    private String symptom;
    private String days;
    private String reportTime;
    private String status;
    private String doctorOpinion;

    public Records(String username, String meName, String symptom, String days, String reportTime,String status ,String doctorOpinion) {
        this.username = username;
        this.meName = meName;
        this.symptom = symptom;
        this.days = days;
        this.reportTime = reportTime;
        this.status = status;
        this.doctorOpinion = doctorOpinion;
    }


    public Records(String meName, String symptom, String days) {
        this.meName = meName;
        this.symptom = symptom;
        this.days = days;
    }

    public Records(String username, String meName, String symptom, String days, String reportTime, String doctorOpinion) {
        this.username = username;
        this.meName = meName;
        this.symptom = symptom;
        this.days = days;
        this.reportTime = reportTime;
        this.doctorOpinion = doctorOpinion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMeName() {
        return meName;
    }

    public void setMeName(String meName) {
        this.meName = meName;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoctorOpinion() {
        return doctorOpinion;
    }

    public void setDoctorOpinion(String doctorOpinion) {
        this.doctorOpinion = doctorOpinion;
    }
}
