package no.systema.jservices.tvinn.sad.z.maintenance.felles.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//Application
//import no.systema.jservices.model.dao.entities.GenericTableColumnsDao;
import no.systema.jservices.model.dao.services.BridfDaoServices;
import no.systema.jservices.tvinn.sad.z.maintenance.felles.controller.rules.SAD012R_U;
import no.systema.jservices.tvinn.sad.z.maintenance.felles.model.dao.entities.KodtlbDao;
import no.systema.jservices.tvinn.sad.z.maintenance.felles.model.dao.services.KodtlbDaoServices;
import no.systema.jservices.tvinn.sad.z.maintenance.felles.jsonwriter.JsonTvinnMaintFellesResponseWriter;
//rules


/**
 * Service Response Controller
 * 
 * This class is the bridge and entry point to the syjservices-layer.
 * All communication to the outside world is done through this gateway.
 * 
 * @author oscardelatorre
 * @date Okt 21, 2016
 * 
 */

@Controller
public class TvinnMaintFellesResponseOutputterController_SAD012R {
	private static Logger logger = Logger.getLogger(TvinnMaintFellesResponseOutputterController_SAD012R.class.getName());
	
	/**
	 * FreeForm Source:
	 * 	 File: 		KODTLB
	 * 	 PGM:		SAD012
	 * 	 Member: 	SAD Felles Maintenance - SELECT LIST or SELECT SPECIFIC
	 *  
	 * 
	 * @return
	 * @Example SELECT *: http://gw.systema.no:8080/syjservicestn/syjsSAD012R.do?user=OSCAR
	 * @Example SELECT specific: http://gw.systema.no:8080/syjservicestn/syjsSAD012R.do?user=OSCAR&klbkod=D
	 * 
	 */
	@RequestMapping(value="syjsSAD012R.do", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String syjsRList( HttpSession session, HttpServletRequest request) {
		JsonTvinnMaintFellesResponseWriter jsonWriter = new JsonTvinnMaintFellesResponseWriter();
		StringBuffer sb = new StringBuffer();
		
		try{
			logger.info("Inside syjsSAD012R");
			//TEST-->logger.info("Servlet root:" + AppConstants.VERSION_SYJSERVICES);
			String user = request.getParameter("user");
			
			//Check ALWAYS user in BRIDF
            String userName = this.bridfDaoServices.findNameById(user);
            //DEBUG --> logger.info("USERNAME:" + userName + "XX");
            String errMsg = "";
			String status = "ok";
			StringBuffer dbErrorStackTrace = new StringBuffer();
			
			//Start processing now
			if(userName!=null && !"".equals(userName)){
				//bind attributes is any
				KodtlbDao dao = new KodtlbDao();
				ServletRequestDataBinder binder = new ServletRequestDataBinder(dao);
	            binder.bind(request);
	            //At this point we now know if we are selecting a specific or all the db-table content (select *)
	            List list = null;
				//do SELECT
				logger.info("Before SELECT ...");
				if(dao.getKlbkod()!=null && !"".equals(dao.getKlbkod())){
					logger.info("findById");
					list = this.kodtlbDaoServices.findById(dao.getKlbkod(), dbErrorStackTrace);
				}else{
					logger.info("getList (all)");
					list = this.kodtlbDaoServices.getList(dbErrorStackTrace);
				}
				//process result
				if (list!=null){
					//write the final JSON output
					sb.append(jsonWriter.setJsonResult_SAD012R_GetList(userName, list));
				}else{
					//write JSON error output
					errMsg = "ERROR on SELECT: list is NULL?  Try to check: <DaoServices>.getList";
					status = "error";
					logger.info("After SELECT:" + " " + status + errMsg );
					sb.append(jsonWriter.setJsonSimpleErrorResult(userName, errMsg, status, dbErrorStackTrace));
				}
			}else{
				//write JSON error output
				errMsg = "ERROR on SELECT";
				status = "error";
				dbErrorStackTrace.append("request input parameters are invalid: <user>, <other mandatory fields>");
				sb.append(jsonWriter.setJsonSimpleErrorResult(userName, errMsg, status, dbErrorStackTrace));
			}
			
		}catch(Exception e){
			//write std.output error output
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			return "ERROR [JsonResponseOutputterController]" + writer.toString();
		}
		session.invalidate();
		return sb.toString();
	}
	
	/**
	 * 
	 * Update Database DML operations
	 * File: 	KODTLB
	 * PGM:		SAD012
	 * Member: 	SAD Felles Maintenance - SELECT LIST or SELECT SPECIFIC
	 * 
	 * @Example UPDATE: http://gw.systema.no:8080/syjservicestn/syjsSAD012R_U.do?user=OSCAR&mode=U/A/D
	 *
	 * @param session
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="syjsSAD012R_U.do", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String syjsR_U( HttpSession session, HttpServletRequest request) {
		JsonTvinnMaintFellesResponseWriter jsonWriter = new JsonTvinnMaintFellesResponseWriter();
		StringBuffer sb = new StringBuffer();
		
		try{
			logger.info("Inside syjsSAD012R_U.do");
			//TEST-->logger.info("Servlet root:" + AppConstants.VERSION_SYJSERVICES);
			String user = request.getParameter("user");
			String mode = request.getParameter("mode");
			//Check ALWAYS user in BRIDF
            String userName = this.bridfDaoServices.findNameById(user);
            //DEBUG --> logger.info("USERNAME:" + userName + "XX");
            String errMsg = "";
			String status = "ok";
			StringBuffer dbErrorStackTrace = new StringBuffer();
			
			//bind attributes is any
			KodtlbDao dao = new KodtlbDao();
			ServletRequestDataBinder binder = new ServletRequestDataBinder(dao);
            binder.bind(request);
            logger.info("KLBXXX:" + dao.getKlbxxx());
            //rules
            SAD012R_U rulerLord = new SAD012R_U();
			//Start processing now
			if(userName!=null && !"".equals(userName)){
				int dmlRetval = 0;
				if("D".equals(mode)){
					if(rulerLord.isValidInputForDelete(dao, userName, mode)){
						dmlRetval = this.kodtlbDaoServices.delete(dao, dbErrorStackTrace);
					}else{
						//write JSON error output
						errMsg = "ERROR on DELETE: invalid?  Try to check: <DaoServices>.delete";
						status = "error";
						sb.append(jsonWriter.setJsonSimpleErrorResult(userName, errMsg, status, dbErrorStackTrace));
					}
				}else{
				  if(rulerLord.isValidInput(dao, userName, mode)){
						logger.info("Before UPDATE ...");
						List<KodtlbDao> list = new ArrayList<KodtlbDao>();
						
						//do ADD
						if("A".equals(mode)){
							list = this.kodtlbDaoServices.findById(dao.getKlbkod(), dbErrorStackTrace);
							//check if there is already such a code. If it does, stop the update
							if(list!=null && list.size()>0){
								//write JSON error output
								errMsg = "ERROR on UPDATE: Code exists already";
								status = "error";
								sb.append(jsonWriter.setJsonSimpleErrorResult(userName, errMsg, status, dbErrorStackTrace));
							}else{
								dmlRetval = this.kodtlbDaoServices.insert(dao, dbErrorStackTrace);
							}
						}else if("U".equals(mode)){
							 dmlRetval = this.kodtlbDaoServices.update(dao, dbErrorStackTrace);
						}
						
				  }else{
						//write JSON error output
						errMsg = "ERROR on UPDATE: invalid (rulerLord)?  Try to check: <DaoServices>.update";
						status = "error";
						sb.append(jsonWriter.setJsonSimpleErrorResult(userName, errMsg, status, dbErrorStackTrace));
				  }
				}
				//----------------------------------
				//check returns from dml operations
				//----------------------------------
				if(dmlRetval<0){
					//write JSON error output
					errMsg = "ERROR on UPDATE: invalid?  Try to check: <DaoServices>.insert/update/delete";
					status = "error";
					sb.append(jsonWriter.setJsonSimpleErrorResult(userName, errMsg, status, dbErrorStackTrace));
				}else{
					//OK UPDATE
					sb.append(jsonWriter.setJsonSimpleValidResult(userName, status));
				}
				
			}else{
				//write JSON error output
				errMsg = "ERROR on UPDATE";
				status = "error";
				dbErrorStackTrace.append("request input parameters are invalid: <user>, <other mandatory fields>");
				sb.append(jsonWriter.setJsonSimpleErrorResult(userName, errMsg, status, dbErrorStackTrace));
			}
			
		}catch(Exception e){
			//write std.output error output
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			return "ERROR [JsonResponseOutputterController]" + writer.toString();
		}
		session.invalidate();
		return sb.toString();
	}
	
	//----------------
	//WIRED SERVICES
	//----------------
	@Qualifier ("kodtlbDaoServices")
	private KodtlbDaoServices kodtlbDaoServices;
	@Autowired
	@Required
	public void setKodtlbDaoServices (KodtlbDaoServices value){ this.kodtlbDaoServices = value; }
	public KodtlbDaoServices getKodtlbDaoServices(){ return this.kodtlbDaoServices; }
	
	
	@Qualifier ("bridfDaoServices")
	private BridfDaoServices bridfDaoServices;
	@Autowired
	@Required
	public void setBridfDaoServices (BridfDaoServices value){ this.bridfDaoServices = value; }
	public BridfDaoServices getBridfDaoServices(){ return this.bridfDaoServices; }
	
}

