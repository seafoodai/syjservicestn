package no.systema.jservices.tvinn.sad.z.maintenance.sadimport.model.dao.services;
import java.io.Writer;
import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import no.systema.jservices.tvinn.sad.z.maintenance.sadimport.model.dao.mapper.HeadfMapper;
import no.systema.jservices.tvinn.sad.z.maintenance.sadimport.model.dao.entities.SadhHeadfDao;
import no.systema.main.util.DbErrorMessageManager;

/**
 * This service manage only UPDATEs
 * 
 * @author oscardelatorre
 * @date Jun 17, 2016
 * 
 */
public class HeadfDaoServicesImpl implements HeadfDaoServices {
	private static Logger logger = Logger.getLogger(HeadfDaoServicesImpl.class.getName());
	private DbErrorMessageManager dbErrorMessageMgr = new DbErrorMessageManager();
	
	/**
	 * @param errorStackTrace
	 * @return
	 */
	public List getList(StringBuffer errorStackTrace){
		List retval = new ArrayList();
		//N/A
		return retval;
	}
	
	/**
	 * 
	 */
	public List findById (String id, StringBuffer errorStackTrace ){
		List<SadhHeadfDao> retval = new ArrayList<SadhHeadfDao>();
		//N/A
		return retval;
	}
	
	/**
	 * 
	 * @param avd
	 * @param opd
	 * @param errorStackTrace
	 * @return
	 */
	public List findForUpdate(String avd, String opd, StringBuffer errorStackTrace){
		List<SadhHeadfDao> retval = new ArrayList<SadhHeadfDao>();
		try{
			StringBuffer sql= new StringBuffer();
			sql.append("select heavd, heopd, hetll, hetle ");
			sql.append("from headf ");
			sql.append("where heavd = ? ");
			sql.append("and heopd = ? ");
			retval = this.jdbcTemplate.query( sql.toString(), new Object[] { avd, opd }, new HeadfMapper());
		}catch(Exception e){
			Writer writer = this.dbErrorMessageMgr.getPrintWriter(e);
			logger.info(writer.toString());
			//Chop the message to comply to JSON-validation
			errorStackTrace.append(this.dbErrorMessageMgr.getJsonValidDbException(writer));
			retval = null;
		}
		return retval;
	}
	
	/**
	 * 
	 * @param dao
	 * @param errorStackTrace
	 * @return
	 */
	public int insert(Object daoObj, StringBuffer errorStackTrace){
		int retval = 0;
		//N/A
		return retval;
	}
	/**
	 * UPDATE
	 */
	public int update(Object daoObj, StringBuffer errorStackTrace){
		
		int retval = 0;
		try{
			
			SadhHeadfDao dao = (SadhHeadfDao)daoObj;
			StringBuffer sql = new StringBuffer();
			//DEBUG --> logger.info("mydebug");
			sql.append(" UPDATE headf SET hetll=?, hetle = ? ");
			sql.append(" WHERE heavd = ? ");
			sql.append(" AND heopd = ? ");
			
			//params
			retval = this.jdbcTemplate.update( sql.toString(), new Object[] { 
				dao.getHetll(), dao.getHetle(),
				//id
				dao.getHeavd(), dao.getHeopd() } );
			
		}catch(Exception e){
			Writer writer = this.dbErrorMessageMgr.getPrintWriter(e);
			logger.info(writer.toString());
			//Chop the message to comply to JSON-validation
			errorStackTrace.append(this.dbErrorMessageMgr.getJsonValidDbException(writer));
			retval = -1;
		}
		
		return retval;
	}
	/**
	 * DELETE
	 */
	public int delete(Object daoObj, StringBuffer errorStackTrace){
		int retval = 0;
		//N/A
		
		return retval;
	}
	/**                                                                                                  
	 * Wires jdbcTemplate                                                                                
	 *                                                                                                   
	 */                                                                                                  
	private JdbcTemplate jdbcTemplate = null;                                                            
	public void setJdbcTemplate( JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}          
	public JdbcTemplate getJdbcTemplate() {return this.jdbcTemplate;}                                    

}