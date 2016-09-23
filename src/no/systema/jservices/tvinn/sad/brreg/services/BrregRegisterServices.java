package no.systema.jservices.tvinn.sad.brreg.services;

import java.util.List;

import no.systema.jservices.model.dao.services.CundfDaoServices;
import no.systema.jservices.tvinn.sad.brreg.proxy.OppslagHovedenhetRequest;

/**
 * 
 * Services using misc API resources in data.brreg.no
 * 
 * 
 * @author Fredrik Möller
 * @date Sep 22, 2016
 *
 */
public interface BrregRegisterServices {

	/**
	 * Retrieve kunder from {@link CundfDaoServices} where data is incorrupt related 
	 * to service {@link OppslagHovedenhetRequest}
	 * 
	 * 
	 * @param firmakode
	 */
	public List getInvalidaKunderEnhetsRegisteret(String firmaKode);


}
