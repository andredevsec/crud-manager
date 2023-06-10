package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ServiceOrder;
import model.ModelException;
import model.Company;
import model.dao.ServiceOrderDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/serviceorders", "/serviceorder/form", "/serviceorder/insert", "/serviceorder/delete", "/serviceorder/update"})
public class ServiceOrderController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = req.getRequestURI();
		//retorna: "crud-manager/company/form"

		switch (action) {
		case "/crud-manager/serviceorder/form": {
			//PrintWriter p = resp.getWriter();
			//p.print("mapa ok");

			CommonsController.listCompany(req);
			//
			req.setAttribute("action", "insert");
			//forward: eh interno: nao tem navegador
			ControllerUtil.forward(req, resp, "/form-serviceorder.jsp");
			break;
		}
		case "/crud-manager/serviceorder/update": {
			
			String idStr = req.getParameter("serviceorderId");
			int idServiceOrder = Integer.parseInt(idStr);
			
			ServiceOrderDAO dao = DAOFactory.createDAO(ServiceOrderDAO.class);
			ServiceOrder serviceorder = null;
			
			try {
				serviceorder = dao.findById(idServiceOrder);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			CommonsController.listCompany(req);
			req.setAttribute("action", "update");
			req.setAttribute("serviceorder", serviceorder);
			
			ControllerUtil.forward(req, resp, "/form-serviceorder.jsp");		
			
			break;
		}
		default:
			listServiceOrder(req);

			ControllerUtil.transferSessionMessagesToRequest(req);

			ControllerUtil.forward(req, resp, "/serviceorders.jsp");
		}
	}

	private void listServiceOrder(HttpServletRequest req) {
		// TODO Auto-generated method stub
		ServiceOrderDAO dao = DAOFactory.createDAO(ServiceOrderDAO.class);

		List<ServiceOrder> serviceorders = null;
		try {
			serviceorders = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}

		if (serviceorders != null)
			req.setAttribute("serviceorders", serviceorders);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String action = req.getRequestURI();


		switch (action) {
		case "/crud-manager/serviceorder/delete":

			deleteServiceOrder(req, resp);
			break;
		case "/crud-manager/serviceorder/insert": {

			insertServiceOrder(req, resp);
			break;
		}
		case "/crud-manager/serviceorder/update": {
			updateServiceOrder(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}
		//redireciona a pagina
		ControllerUtil.redirect(resp, req.getContextPath()+"/serviceorders");
	}

	private void updateServiceOrder(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
		String serviceorderIdStr = req.getParameter("serviceorderId");
		String serviceorderDescription = req.getParameter("description");
		Double valueos = Double.parseDouble(req.getParameter("valueos"));
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		//company: nome do select
		Integer companyId = Integer.parseInt(req.getParameter("company"));

		ServiceOrder os = new ServiceOrder(Integer.parseInt(serviceorderIdStr));
		os.setDescription(serviceorderDescription);
		os.setValue(valueos);
		os.setStart(ControllerUtil.formatDate(start));
		os.setEnd(ControllerUtil.formatDate(end));
		os.setCompany(new Company(companyId));
		
		
		ServiceOrderDAO dao = DAOFactory.createDAO(ServiceOrderDAO.class);
		
		try {
			if (dao.update(os)) {
				ControllerUtil.sucessMessage(req, "Ordem de serviço atualizada com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Ordem de serviço não pode ser atualizada.");
			}				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
	}

	private void deleteServiceOrder(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub

		String serviceorderIdParameter = req.getParameter("id");

		int serviceorderId = Integer.parseInt(serviceorderIdParameter);

		ServiceOrderDAO dao = DAOFactory.createDAO(ServiceOrderDAO.class);
		
		try {
			ServiceOrder serviceorder = dao.findById(serviceorderId);
			
			if (serviceorder == null)
				throw new ModelException("Ordem não encontrado para deleçãa.");
			
			if (dao.delete(serviceorder)) {
				ControllerUtil.sucessMessage(req, "Ordem de serviço deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Ordem de serviço id não pode ser deletada. Há dados relacionados a ordem.");
			}
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

	}

	private void insertServiceOrder(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		//pega dados do form
		String serviceorderDescription = req.getParameter("description");
		Double valueos = Double.parseDouble(req.getParameter("valueos"));
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		//company: nome do select
		Integer companyId = Integer.parseInt(req.getParameter("company"));

		ServiceOrder os = new ServiceOrder();
		os.setDescription(serviceorderDescription);
		//comp.setRole("nos");
		os.setValue(valueos);
		os.setStart(ControllerUtil.formatDate(start));
		os.setEnd(ControllerUtil.formatDate(end));
		os.setCompany(new Company(companyId));
		//persistencia
		ServiceOrderDAO dao = DAOFactory.createDAO(ServiceOrderDAO.class);

		try {
			if (dao.save(os)) {
				ControllerUtil.sucessMessage(req, "Ordem de serviço id salva com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Ordem de serviço id não pode ser salva.");
			}
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}