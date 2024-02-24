package annuaire.Annuaire.controller.affiliation.model;

import annuaire.Annuaire.controller.services.model.Services;
import annuaire.Annuaire.controller.sites.model.Sites;
import annuaire.Annuaire.controller.employees.model.Employees;

public class Affiliation {
    Services services;
    Sites sites;
    Employees employees;


    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public Sites getSites() {
        return sites;
    }

    public void setSites(Sites sites) {
        this.sites = sites;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }
}
