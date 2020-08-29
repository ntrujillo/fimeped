package org.uce.fimeped.report.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uce.fimeped.domain.ClinicHistory;
import org.uce.fimeped.domain.CurrentIllness;
import org.uce.fimeped.domain.CurrentRevision;
import org.uce.fimeped.domain.Diagnostic;
import org.uce.fimeped.domain.Episode;
import org.uce.fimeped.domain.FamilyHistory;
import org.uce.fimeped.domain.Person;
import org.uce.fimeped.domain.PersonalHistory;
import org.uce.fimeped.domain.PhysicalExam;
import org.uce.fimeped.domain.Plan;
import org.uce.fimeped.domain.Reason;
import org.uce.fimeped.domain.VitalSign;
import org.uce.fimeped.report.domain.CurrentRevisionRecord;
import org.uce.fimeped.report.domain.DiagnosticRecord;
import org.uce.fimeped.report.domain.GenericRecord;
import org.uce.fimeped.report.domain.GenericRecordCode;
import org.uce.fimeped.report.domain.PhysicalExamRecord;
import org.uce.fimeped.report.domain.Report;
import org.uce.fimeped.report.domain.VitalSignRecord;
import org.uce.fimeped.repository.CurrentIllnessRepository;
import org.uce.fimeped.repository.CurrentRevisionRepository;
import org.uce.fimeped.repository.DiagnosticRepository;
import org.uce.fimeped.repository.EpisodeRepository;
import org.uce.fimeped.repository.EvolutionPrescriptionRepository;
import org.uce.fimeped.repository.FamilyHistoryRepository;
import org.uce.fimeped.repository.PersonalHistoryRepository;
import org.uce.fimeped.repository.PhysicalExamRepository;
import org.uce.fimeped.repository.PlanRepository;
import org.uce.fimeped.repository.ReasonRepository;
import org.uce.fimeped.repository.VitalSignRepository;
import org.uce.fimeped.web.rest.util.PaginationUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;

@Controller
@RequestMapping("/report/")
public class JasperReportServlet  {
	
	


	private static final Logger logger = LoggerFactory.getLogger(JasperReportServlet.class);
	 

	@Inject
	private EpisodeRepository episodeService;

	@Inject
	private ReasonRepository reasonService;

	@Inject
	private FamilyHistoryRepository familyHistoryService;

	@Inject
	private PersonalHistoryRepository personalHistoryService;

	@Inject
	private CurrentIllnessRepository currentIllnessService;

	@Inject
	private CurrentRevisionRepository currentRevisionService;

	@Inject
	private DiagnosticRepository diagnosticService;

	@Inject
	private PlanRepository planService;

	@Inject
	private VitalSignRepository vitalSignService;
	
	@Inject
	private PhysicalExamRepository physicalExamService;


	

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

   @RequestMapping(value = "helloReport1", method = RequestMethod.GET)
   @ResponseBody
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   logger.debug("Start doPost in "+this.getClass().toString());
		String episode = req.getParameter("report.episodeId");

		if (episode == null) {
			resp.sendError(400, "Error trying to generate report. report.episodeId is null");

			return;
		}

		String type = req.getParameter("report.type") == null ? "PDF" : req.getParameter("report.type");

		Boolean isValidtype = Boolean.valueOf(JasperGeneratorEnum.isMember(type));
		if (!isValidtype.booleanValue()) {
			resp.sendError(400, "Error trying to generate report. Invalid type [" + type + "]");

			return;
		}

		try {

			long episodeId = Long.parseLong(episode);
			JasperGeneratorEnum enumtype = JasperGeneratorEnum.valueOf(type);
			JasperPrint jasperPrint;
			jasperPrint = this.getJasperPrintForm(episodeId);

			generateReport(req, resp, jasperPrint, "Formulario 002", enumtype);

		} catch (JRException e) {
			e.printStackTrace(resp.getWriter());
			resp.sendError(500, "Exception Generating report. " + e.getMessage());			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(resp.getWriter());
			resp.sendError(500, "Exception Generating report. " + e.getMessage());
		}
		 logger.debug("End doPost in "+this.getClass().toString());
	}

	/*
	 * private JasperPrint getJasperPrintEvolutionPrescription(long
	 * clinicHistory) throws FimepedException, JRException { String url =
	 * getServletContext().getRealPath("/");
	 * 
	 * JRBeanCollectionDataSource dsEvolutionPrescription = this
	 * .getDataSourceEvolutionPrescription(clinicHistory);
	 * 
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("SUBREPORT_DIR", url + "/jasper/");
	 * 
	 * JasperPrint jasperPrint = JasperFillManager.fillReport(url +
	 * "/jasper/evolution-prescription.jasper", params,
	 * dsEvolutionPrescription);
	 * 
	 * return jasperPrint;
	 * 
	 * }
	 */

	private JasperPrint getJasperPrintForm(long episodeId) throws JRException, Exception {
		logger.debug("Start getJasperPrintForm in "+this.getClass().toString());
		//String url = getServletContext().getRealPath("/");

		//logger.debug("url servlet context "+url);
		
		Episode e = episodeService.findOne(episodeId);		
		logger.debug("Episode "+e);
		if(e==null){
			throw new Exception("No se encontró episido con id "+episodeId);
		}
		ClinicHistory cl = e.getClinicHistory();
		logger.debug("Clinic History "+cl);
		if(cl==null){
			throw new Exception("No se pudo recupera la historia clinica del episodio con id "+episodeId);
		}
		Person p = e.getClinicHistory().getPerson();
		logger.debug("Person "+p);
		if(p==null){
			throw new Exception("No pudo recuperar el paciente ");
		}
		List<Report> dataSource = new ArrayList<Report>();
		Report ch = new Report();
		ch.setInstitution("UCE");
		ch.setFirstName(p.getFirstName() + " " + p.getSecondName());
		ch.setLastName(p.getPaternalSurname() + " " + p.getMaternalSurname());
		ch.setGender(p.getGender());
		ch.setClinicHistory(cl.getId());
		dataSource.add(ch);

		JRBeanCollectionDataSource dsReason = this.getDataSourceReason(e);

		JRBeanCollectionDataSource dsPersonalHistory = this
				.getDataSourcePersonalHistory(e);

		JRBeanCollectionDataSource dsFamilyHistory = this
				.getDataSourceFamilyHistory(e);

		JRBeanCollectionDataSource dsCurrentIllness = this
				.getDataSourceCurrentIllness(e);

		JRBeanCollectionDataSource dsCurrentRevision = this
				.getDataSourceCurrentRevision(e);

		JRBeanCollectionDataSource dsVitalSign = this.getDataSourceVitalSign(e);

		JRBeanCollectionDataSource dsPhysicalExam = this
				.getDataSourcePhysicalExam(e);

		JRBeanCollectionDataSource dsDiagnostic = this
				.getDataSourceDiagnostic(e);

		JRBeanCollectionDataSource dsPlan = this.getDataSourcePlan(e);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("SUBREPORT_DIR", "src/main/webapp/jasper/");
		params.put("TITLE_REASON", "MOTIVO DE CONSULTA");
		params.put("dsReason", dsReason);
		params.put("TITLE_PERSONAL_HISTORY", "ANTECEDENTES PERSONALES");
		params.put("dsPersonalHistory", dsPersonalHistory);
		params.put("TITLE_FAMILY_HISTORY", "ANTECEDENTES FAMILIARES");
		params.put("dsFamilyHistory", dsFamilyHistory);
		params.put("TITLE_CURRENT_ILLNESS", "ENFERMEDAD O PROBLEMA ACTUAL");
		params.put("dsCurrentIllness", dsCurrentIllness);
		params.put("TITLE_CURRENT_REVISION",
				"REVISION ACTUAL DE ORGANOS Y SISTEMAS");
		params.put("dsCurrentRevision", dsCurrentRevision);
		params.put("TITLE_VITAL_SIGN", "SIGNOS VITALES");
		params.put("dsVitalSign", dsVitalSign);
		params.put("TITLE_PHYSICAL_EXAM", "EXAMEN FISICO");
		params.put("dsPhysicalExam", dsPhysicalExam);
		params.put("TITLE_DIAGNOSTIC", "DIAGNOSTICOS");
		params.put("dsDiagnostic", dsDiagnostic);
		params.put("TITLE_PLAN", "PLANES");
		params.put("dsPlan", dsPlan);

		JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/webapp/jasper/form002.jasper", params,
				new JRBeanCollectionDataSource(dataSource, true));
		
		logger.debug("End getJasperPrintForm in "+this.getClass().toString());

		return jasperPrint;

	}

	private void generateReport(HttpServletRequest req, HttpServletResponse resp, JasperPrint jasperPrint,
			String reportFilename, JasperGeneratorEnum enumtype) throws IOException, JRException {
		
		logger.debug("Start generateReport in "+this.getClass().toString());
		logger.debug("Report name: "+reportFilename);
		logger.debug("Type: "+enumtype);
		switch (enumtype) {
		
		case HTML:
			generateHtmlReport(resp, req.getSession(false), jasperPrint, req.getContextPath());
			break;
		case XLS:
			generateXlsReport(resp, jasperPrint, reportFilename);
			break;
		case DOC:
			generateDocReport(resp, jasperPrint, reportFilename);
			break;
		default:
			generatePdfReport(resp, jasperPrint);
		}
		
		logger.debug("End generateReport in "+this.getClass().toString());
	}

	private void generateDocReport(HttpServletResponse resp, JasperPrint jasperPrint, String reportFilename)
			throws IOException, JRException {
		ServletOutputStream out = resp.getOutputStream();
		resp.setHeader("Content-Disposition", "attachment; filename=report-" + reportFilename + ".doc");

		resp.setContentType("application/vnd.ms-word");

		JRDocxExporter exporter = new JRDocxExporter();

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));

		exporter.exportReport();
		out.flush();
		out.close();
	}

	private void generateXlsReport(HttpServletResponse resp, JasperPrint jasperPrint, String reportFilename)
			throws IOException, JRException {
		ServletOutputStream out = resp.getOutputStream();
		resp.setHeader("Content-Disposition", "attachment; filename=report-" + reportFilename + ".xls");

		resp.setContentType("application/vnd.ms-excel");

		JRXlsExporter exporterXLS = new JRXlsExporter();

		exporterXLS.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(out));

		SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
		configuration.setOnePagePerSheet(Boolean.valueOf(true));
		configuration.setDetectCellType(Boolean.valueOf(true));
		configuration.setCollapseRowSpan(Boolean.valueOf(false));
		exporterXLS.setConfiguration(configuration);

		exporterXLS.exportReport();

		out.flush();
		out.close();
	}

	private void generateHtmlReport(HttpServletResponse resp, HttpSession session, JasperPrint jasperPrint,
			String contextPath) throws IOException, JRException {
		resp.setContentType("text/html");
		ServletOutputStream out = resp.getOutputStream();

		session.setAttribute("net.sf.jasperreports.j2ee.jasper_print", jasperPrint);

		HtmlExporter htmlExporter = new HtmlExporter();
		SimpleExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		SimpleHtmlExporterOutput exporterOutput = new SimpleHtmlExporterOutput(out);

		exporterOutput.setImageHandler(new WebHtmlResourceHandler(contextPath + "/cobis/web/reporting/actions/images?"
				+ "image" + "={0}" + "&uuid=" + UUID.randomUUID().toString()));

		SimpleHtmlReportConfiguration reportExportConfiguration = new SimpleHtmlReportConfiguration();
		reportExportConfiguration.setWhitePageBackground(Boolean.valueOf(false));
		reportExportConfiguration.setRemoveEmptySpaceBetweenRows(Boolean.valueOf(true));

		htmlExporter.setExporterInput(exporterInput);
		htmlExporter.setExporterOutput(exporterOutput);
		htmlExporter.setConfiguration(reportExportConfiguration);
		htmlExporter.exportReport();

		out.flush();
		out.close();
	}

	private void generatePdfReport(HttpServletResponse resp, JasperPrint jasperPrint) throws IOException, JRException {
		ServletOutputStream out = resp.getOutputStream();
		resp.setContentType("application/pdf");
		JasperExportManager.exportReportToPdfStream(jasperPrint, out);
		out.flush();
		out.close();
	}

	private JRBeanCollectionDataSource getDataSourceReason(Episode e) throws Exception {

		Page<Reason> list = reasonService.findByEpisode(e.getId(), PaginationUtil.generatePageRequest(1, 20));

		GenericRecord reason;
		List<GenericRecord> reportList = new ArrayList<GenericRecord>();
		for (Reason r : list) {
			reason = new GenericRecord();
			reason.setCode(r.getId());
			reason.setDate(e.getDate().toDate());
			reason.setDescription(r.getDescription());
			reportList.add(reason);

		}
		return new JRBeanCollectionDataSource(reportList, true);

	}

	private JRBeanCollectionDataSource getDataSourcePersonalHistory(Episode e) throws Exception {
		Page<PersonalHistory> list = personalHistoryService
				.findByClinicHistory(e.getClinicHistory().getId(), PaginationUtil.generatePageRequest(1, 20));

		GenericRecordCode record;
		List<GenericRecordCode> reportList = new ArrayList<GenericRecordCode>();
		for (PersonalHistory r : list) {
			record = new GenericRecordCode();
			record.setCode(r.getId());
			record.setDate(r.getDate().toDate());
			record.setDescription(r.getDescription());
			record.setCode1(r.getIllness());
			reportList.add(record);

		}
		return new JRBeanCollectionDataSource(reportList, true);

	}

	private JRBeanCollectionDataSource getDataSourceFamilyHistory(Episode e) throws Exception {
		Page<FamilyHistory> list = familyHistoryService.findByClinicHistory(e.getClinicHistory().getId(),
				 PaginationUtil.generatePageRequest(1, 20));

		GenericRecordCode record;
		List<GenericRecordCode> reportList = new ArrayList<GenericRecordCode>();
		for (FamilyHistory r : list) {
			record = new GenericRecordCode();
			record.setCode(r.getId());
			record.setDate(r.getDate().toDate());
			record.setDescription(r.getDescription());
			record.setCode1(r.getIllness());
			reportList.add(record);

		}
		return new JRBeanCollectionDataSource(reportList, true);

	}

	private JRBeanCollectionDataSource getDataSourceCurrentIllness(Episode e) throws Exception {

		Page<CurrentIllness> list = currentIllnessService.findByEpisode(e.getId(),  PaginationUtil.generatePageRequest(1, 20));
		GenericRecord record;
		List<GenericRecord> reportList = new ArrayList<GenericRecord>();
		for (CurrentIllness r : list) {
			record = new GenericRecord();
			record.setCode(r.getId());
			record.setDate(e.getDate().toDate());
			record.setDescription(r.getDescription());
			reportList.add(record);
		}
		return new JRBeanCollectionDataSource(reportList, true);

	}

	private JRBeanCollectionDataSource getDataSourceCurrentRevision(Episode e) throws Exception {

		Page<CurrentRevision> list = currentRevisionService.findByEpisode(e.getId(), PaginationUtil.generatePageRequest(1, 20));
		CurrentRevisionRecord record;
		List<CurrentRevisionRecord> reportList = new ArrayList<CurrentRevisionRecord>();
		for (CurrentRevision r : list) {
			record = new CurrentRevisionRecord();
			record.setCode(r.getId());
			record.setDate(e.getDate().toDate());
			record.setDescription(r.getDescription());
			record.setOrgan(r.getOrgan());
			record.setWeNe(r.getWeNe());
			reportList.add(record);

		}
		return new JRBeanCollectionDataSource(reportList, true);

	}

	private JRBeanCollectionDataSource getDataSourceVitalSign(Episode e) throws Exception {

		Page<VitalSign> list = vitalSignService.findByClinicHistory(e.getId(),  PaginationUtil.generatePageRequest(1, 20));
		VitalSignRecord record;
		List<VitalSignRecord> reportList = new ArrayList<VitalSignRecord>();
		for (VitalSign r : list) {
			record = new VitalSignRecord();
			record.setCode(r.getId());
			record.setDate(e.getDate().toDate());
			record.setBloodPressure(r.getBloodPressure());
			reportList.add(record);

		}
		return new JRBeanCollectionDataSource(reportList, true);

	}

	private JRBeanCollectionDataSource getDataSourcePhysicalExam(Episode e) throws Exception {

		Page<PhysicalExam> list = physicalExamService.findByEpisode(e.getId(), PaginationUtil.generatePageRequest(1, 20));
		PhysicalExamRecord record;
		List<PhysicalExamRecord> reportList = new ArrayList<PhysicalExamRecord>();
		for (PhysicalExam r : list) {
			record = new PhysicalExamRecord();
			record.setCode(r.getId());
			record.setDate(e.getDate().toDate());
			record.setDescription(r.getDescription());
			record.setBodyPart(r.getBodyPart());
			record.setWeNe(r.getWeNe());
			reportList.add(record);

		}
		return new JRBeanCollectionDataSource(reportList, true);

	}

	private JRBeanCollectionDataSource getDataSourceDiagnostic(Episode e) throws Exception {

		Page<Diagnostic> list = diagnosticService.findByEpisode(e.getId(),  PaginationUtil.generatePageRequest(1, 20));
		DiagnosticRecord record;
		List<DiagnosticRecord> reportList = new ArrayList<DiagnosticRecord>();
		for (Diagnostic r : list) {
			record = new DiagnosticRecord();
			record.setCode(r.getId());
			record.setDate(e.getDate().toDate());
			record.setDescription(r.getDescription());
			record.setCie(r.getCie());
			record.setCatalogPreDef(r.getPreDef());
			reportList.add(record);

		}
		return new JRBeanCollectionDataSource(reportList, true);

	}

	private JRBeanCollectionDataSource getDataSourcePlan(Episode e) throws Exception {

		Page<Plan> list = planService.findByEpisode(e.getId(),  PaginationUtil.generatePageRequest(1, 20));

		GenericRecord record;
		List<GenericRecord> reportList = new ArrayList<GenericRecord>();
		for (Plan r : list) {
			record = new GenericRecord();
			record.setCode(r.getId());
			record.setDate(e.getDate().toDate());
			record.setDescription(r.getDescription());
			reportList.add(record);

		}
		return new JRBeanCollectionDataSource(reportList, true);

	}

	/*
	 * private JRBeanCollectionDataSource getDataSourceEvolutionPrescription(
	 * Episode e) throws FimepedException {
	 * 
	 * PaginationList<EvolutionPrescription> list = fmService
	 * .getEvolutionPrescriptionByEpisode(e.getCode(),0,10);
	 * 
	 * EvolutionPrescriptionRecord record; List<EvolutionPrescriptionRecord>
	 * reportList = new ArrayList<EvolutionPrescriptionRecord>(); for
	 * (EvolutionPrescription r : list.getList()) { record = new
	 * EvolutionPrescriptionRecord(); record.setCode(r.getCode());
	 * record.setDate(r.getDate()); record.setEvolution(r.getEvolution());
	 * record.setPrescription(r.getPrescription()); reportList.add(record);
	 * 
	 * } return new JRBeanCollectionDataSource(reportList, true);
	 * 
	 * }
	 */

}