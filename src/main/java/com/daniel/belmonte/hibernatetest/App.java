package com.daniel.belmonte.hibernatetest;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Conexión a MySQL utilizando Hibernate
 *
 */
public class App 
{
	static SessionFactory sessionFactoryObj;
	static Session sessionObj;
	
	private static SessionFactory buildSessionFactory() {
		Configuration confObj = new Configuration();
		confObj.configure("hibernate.cfg.xml");
		confObj.addAnnotatedClass(Actores.class);
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(confObj.getProperties()).build();
		return sessionFactoryObj = confObj.buildSessionFactory(serviceRegistryObj);
	}
	
    public static void main( String[] args )
    {
        System.out.println( "Conexión a MySQL utilizando Hibernate" );
        
        sessionObj = buildSessionFactory().openSession();
		sessionObj.beginTransaction();
        
        try {
        	//Recuperar un sólo actor
    		/*Actores actor = (Actores)sessionObj.get(Actores.class, 1);
    		System.out.println(actor.getFirst_name());
    		sessionObj.delete(actor);*/
    		
    		//Listado de todos los actores
        	/*List<Actores> actoresList = sessionObj.createQuery("from Actores").getResultList();
        	
    		for(Actores actor: actoresList){
    			System.out.println(actor.getFirst_name());
    		}*/
        	
        	//Insertar un actor
        	Actores actoresObj = new Actores();
        	Date fechaObj = new Date(System.currentTimeMillis());
        	
        	actoresObj.setFirst_name("Dani");
        	actoresObj.setLast_name("Rodriguez");
        	actoresObj.setLast_update(fechaObj);
        	sessionObj.save(actoresObj);
        	sessionObj.getTransaction().commit();
        }
        catch(Exception sqlException){
        	if(sessionObj.getTransaction() != null)
        		sessionObj.getTransaction().rollback();

    		sqlException.printStackTrace();
        }
        finally {
        	if(sessionObj != null)
        		sessionObj.close();
        }
        
        System.out.println( "Fin de la conexión a MySQL utilizando Hibernate" );
    }
}
