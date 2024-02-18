package annuaire.Annuaire;

import annuaire.Annuaire.mapper.*;
import org.apache.catalina.mapper.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AnnuaireApplication {

	MapperAdressesAvecAdressesDTO mapperAdresses = new MapperAdressesAvecAdressesDTO();
	MapperServicesAvecServicesDTO mapperServices = new MapperServicesAvecServicesDTO();

	public static void main(String[] args) {
		SpringApplication.run(AnnuaireApplication.class, args);
	}

	@Bean
	public MapperAdressesAvecAdressesDTO mapperAdressesAvecAdressesDTO(){
		return new MapperAdressesAvecAdressesDTO();
	}
//	@Bean
//	public MapperSitesAvecSitesDTO mapperSitesAvecSitesDTO(){
//		return new MapperSitesAvecSitesDTO();
//	}
	@Bean
	public MapperServicesAvecServicesDTO mapperServicesAvecServicesDTO() {
		return new MapperServicesAvecServicesDTO();
	}
//	@Bean
//	public MapperEmployeesAvecEmployeesDTO mapperEmployeesAvecEmployeesDTO() {
//		return new MapperEmployeesAvecEmployeesDTO();
//	}
	@Bean
	public MapperAffiliationAvecAffiliationDTO mapperAffiliationAvecAffiliationDTO() {
		return new MapperAffiliationAvecAffiliationDTO(
				mapperAdresses, mapperServices
		);
	}



}
