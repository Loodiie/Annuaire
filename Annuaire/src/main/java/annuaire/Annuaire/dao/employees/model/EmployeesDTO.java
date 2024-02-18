package annuaire.Annuaire.dao.employees.model;

import java.sql.Timestamp;

public class EmployeesDTO {
    int idEmployee;
    String nomEmployee;
    String prenomEmployee;
    int idService;
    String posteEmployee;
    String fixeEmployee;
    String mailEmployee;
    Timestamp dateNaissance;
    Timestamp dateEmbauche;
    Boolean admin;

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNomEmployee() {
        return nomEmployee;
    }

    public void setNomEmployee(String nomEmployee) {
        this.nomEmployee = nomEmployee;
    }

    public String getPrenomEmployee() {
        return prenomEmployee;
    }

    public void setPrenomEmployee(String prenomEmployee) {
        this.prenomEmployee = prenomEmployee;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getPosteEmployee() {
        return posteEmployee;
    }

    public void setPosteEmployee(String posteEmployee) {
        this.posteEmployee = posteEmployee;
    }

    public String getFixeEmployee() {
        return fixeEmployee;
    }

    public void setFixeEmployee(String fixeEmployee) {
        this.fixeEmployee = fixeEmployee;
    }

    public String getMailEmployee() {
        return mailEmployee;
    }

    public void setMailEmployee(String mailEmployee) {
        this.mailEmployee = mailEmployee;
    }

    public Timestamp getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Timestamp dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Timestamp getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(Timestamp dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
