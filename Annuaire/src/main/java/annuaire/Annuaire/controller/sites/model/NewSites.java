package annuaire.Annuaire.controller.sites.model;

public class NewSites {
    String nomSite;
    String telSite;
    String mailSite;
    String typeSite;
    int idAdresse;

    public String getNomSite() {
        return nomSite;
    }

    public void setNomSite(String nomSite) {
        this.nomSite = nomSite;
    }

    public String getTelSite() {
        return telSite;
    }

    public void setTelSite(String telSite) {
        this.telSite = telSite;
    }

    public String getMailSite() {
        return mailSite;
    }

    public void setMailSite(String mailSite) {
        this.mailSite = mailSite;
    }

    public String getTypeSite() {
        return typeSite;
    }

    public void setTypeSite(String typeSite) {
        this.typeSite = typeSite;
    }

    public int getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(int idAdresse) {
        this.idAdresse = idAdresse;
    }
}
