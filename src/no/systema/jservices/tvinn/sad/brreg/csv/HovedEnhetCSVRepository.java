package no.systema.jservices.tvinn.sad.brreg.csv;

import no.systema.jservices.tvinn.sad.brreg.proxy.entities.HovedEnhet;

public interface HovedEnhetCSVRepository {

	/**
	 * Retrieve a HovenEnhet by orgnr
	 * 
	 * @param orgNr, Integer
	 * @return HovedEnhet
	 */
	public HovedEnhet get(Integer orgNr); 
	
	
	/**
	 * Set full qualifier for cvs-file.
	 * 
	 * @param String pathAndFileName, path and full filename
	 */
	public void setFile(String pathAndFileName);
}
