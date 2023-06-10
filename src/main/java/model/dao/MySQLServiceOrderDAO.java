package model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ServiceOrder;
import model.ModelException;
import model.Company;

public class MySQLServiceOrderDAO implements ServiceOrderDAO {

	@Override
	public boolean save(ServiceOrder serviceorder) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO serviceorder VALUES (DEFAULT, ?, ?, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		
		db.setString(1, serviceorder.getDescription());
		db.setDouble(2, serviceorder.getValueos());
		db.setDate(3, serviceorder.getStart() == null ? new Date() : serviceorder.getStart());
			
		if (serviceorder.getEnd() == null)
			db.setNullDate(4);
		else db.setDate(4, serviceorder.getEnd());

		db.setInt(5, serviceorder.getCompany().getId());
		
		return db.executeUpdate() > 0;	
	}

	@Override
	public boolean update(ServiceOrder serviceorder) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE serviceorder "
				+ " SET description = ?, "
				+ " valueos = ?, "
				+ " start = ?, "
				+ " end = ?, "
				+ " company_id = ? "
				+ " WHERE id = ?; ";
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, serviceorder.getDescription());
		db.setDouble(2, serviceorder.getValueos());
		
		db.setDate(3, serviceorder.getStart() == null ? new Date() : serviceorder.getStart());
		
		if (serviceorder.getEnd() == null)
			db.setNullDate(4);
		else db.setDate(4, serviceorder.getEnd());
		
		db.setInt(5, serviceorder.getCompany().getId());
		db.setInt(6, serviceorder.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(ServiceOrder serviceorder) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM serviceorder "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, serviceorder.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<ServiceOrder> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		
		List<ServiceOrder> serviceorders = new ArrayList<ServiceOrder>();
			
		// Declara uma instrução SQL
		String sqlQuery = " SELECT s.id as 'serviceorder_id', s.*, c.* \n"
				+ " FROM serviceorder s \n"
				+ " INNER JOIN companies c \n"
				+ " ON s.company_id = c.id;";
		
		db.createStatement();
	
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Company company = new Company(db.getInt("company_id"));
			company.setName(db.getString("name"));
			company.setRole(db.getString("role"));
			company.setStart(db.getDate("start"));
			company.setEnd(db.getDate("end"));
			
			ServiceOrder serviceorder = new ServiceOrder(db.getInt("serviceorder_id"));
			serviceorder.setDescription(db.getString("description"));
			serviceorder.setValue(db.getDouble("valueos"));
			serviceorder.setStart(db.getDate("start"));
			serviceorder.setEnd(db.getDate("end"));
			serviceorder.setCompany(company);
			
			serviceorders.add(serviceorder);
		}
		
		return serviceorders;
	}

	@Override
	public ServiceOrder findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sql = "SELECT * FROM serviceorder WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		ServiceOrder s = null;
		while (db.next()) {
			s = new ServiceOrder(id);
			s.setDescription(db.getString("description"));
			s.setValue(db.getDouble("valueos"));
			s.setStart(db.getDate("start"));
			s.setEnd(db.getDate("end"));
			
			CompanyDAO companyDAO = DAOFactory.createDAO(CompanyDAO.class); 
			Company company = companyDAO.findById(db.getInt("company_id"));
			s.setCompany(company);
			
			break;
		}
		
		return s;
	}
}
