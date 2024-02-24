package annuaire.Annuaire.controller.sites.model;

public class Sites {
    int idSite;
    String nomSite;
    String telSite;
    String mailSite;
    String typeSite;
    String villeSite;

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }

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

    public String getTypeSite() {return typeSite;}

    public void setTypeSite(String typeSite) {
        this.typeSite = typeSite;
    }

    public String getVilleSite() {
        return villeSite;
    }

    public void setVilleSite(String villeSite) {
        this.villeSite = villeSite;
    }
}

