package models;

import java.util.*;

import javax.persistence.*;

import models.Person.Page;
import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;

/**
 * Person entity managed by JPA
 */
@Entity 
public class Person {

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;
    
    @Constraints.Required
    public String name;
    
    @Formats.DateTime(pattern="dd-MM-yyyy")
    public Date birthday;
    
    public String address;
    
    public String street;
    
    public Long cp;
    
    public String city;
    
    public String addressXML;
    
    
    public static Person findById(Long id) {
        return JPA.em().find(Person.class, id);
    }
    
    /**
     * Update this person.
     */
    public void update(Long id) {
    	this.id = id;
        JPA.em().merge(this);
    }
    
    /**
     * Insert this new person.
     */
    public void save() {
        JPA.em().persist(this);
    }
    
    /**
     * Delete this person.
     */
    public void delete() {
        JPA.em().remove(this);
    }
     
    /**
     * Return a page of person
     *
     * @param page Page to display
     * @param pageSize Number of persons per page
     * @param sortBy Person property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page page(int page, int pageSize, String sortBy, String order, String filter) {
        if(page < 1) page = 1;
        Long total = (Long)JPA.em()
            .createQuery("select count(p) from Person p where lower(p.name) like ?")
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .getSingleResult();
        List<Person> data = JPA.em()
            .createQuery("from Person p where lower(p.name) like ? order by p." + sortBy + " " + order)
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
        return new Page(data, total, page, pageSize);
    }
    
    /**
     * Used to represent a persons page.
     */
    public static class Page {
        
        private final int pageSize;
        private final long totalRowCount;
        private final int pageIndex;
        private final List<Person> list;
        
        public Page(List<Person> data, long total, int page, int pageSize) {
            this.list = data;
            this.totalRowCount = total;
            this.pageIndex = page;
            this.pageSize = pageSize;
        }
        
        public long getTotalRowCount() {
            return totalRowCount;
        }
        
        public int getPageIndex() {
            return pageIndex;
        }
        
        public List<Person> getList() {
            return list;
        }
        
        public boolean hasPrev() {
            return pageIndex > 1;
        }
        
        public boolean hasNext() {
            return (totalRowCount/pageSize) >= pageIndex;
        }
        
        public String getDisplayXtoYofZ() {
            int start = ((pageIndex - 1) * pageSize + 1);
            int end = start + Math.min(pageSize, list.size()) - 1;
            return start + " to " + end + " of " + totalRowCount;
        }
        
    }
    
}