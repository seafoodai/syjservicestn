package no.systema.jservices.tvinn.sad.z.maintenance.sadimport.model.dao.services;
import java.util.*;

import no.systema.jservices.tvinn.sad.z.maintenance.sadimport.model.dao.entities.KodtlikDao;

/**
 * 
 * @author oscardelatorre
 * @date Mar 30, 2016
 * 
 */
public interface KodtlikDaoServices {
	public List<KodtlikDao> getList(StringBuffer errorStackTrace);
	public List<KodtlikDao> findById(String id, StringBuffer errorStackTrace);
	//DMLs
	public int insert(KodtlikDao dao, StringBuffer errorStackTrace);
	public int update(KodtlikDao dao, StringBuffer errorStackTrace);
	public int delete(KodtlikDao dao, StringBuffer errorStackTrace);
	
}
