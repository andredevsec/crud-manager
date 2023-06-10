package model.dao;

import java.util.List;

import model.ServiceOrder;
import model.ModelException;

public interface ServiceOrderDAO {
	boolean save(ServiceOrder serviceorder) throws ModelException;
	boolean update(ServiceOrder serviceorder) throws ModelException;
	boolean delete(ServiceOrder serviceorder) throws ModelException;
	List<ServiceOrder> listAll() throws ModelException;
	ServiceOrder findById(int id) throws ModelException;
}
