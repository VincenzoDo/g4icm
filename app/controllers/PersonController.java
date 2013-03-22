package controllers;

import static play.data.Form.form;

import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;

public class PersonController extends Controller {

	@Transactional(readOnly = true)
	public static Result list(int page, String sortBy, String order,
			String filter) {
		return ok(views.html.persons.list.render(models.Person.page(page, 10, sortBy, order, filter),
				sortBy, order, filter));
	}
	
	@Transactional(readOnly = true)
	public static Result edit(Long id) {
		models.Person p = models.Person.findById(id);
		Form<models.Person> personForm = form(models.Person.class).fill(models.Person.findById(id));
		return ok(views.html.persons.editForm.render(id, personForm));
	}

	@Transactional
	public static Result update(Long id) {
		Form<models.Person> personForm = form(models.Person.class).bindFromRequest();
		if (personForm.hasErrors()) {
			return badRequest(views.html.persons.editForm.render(id, personForm));
		}
		personForm.get().update(id);
		flash("success", "Person " + personForm.get().name + " has been updated");
		return redirect(routes.PersonController.list(0, "name", "asc", ""));
	}
	
	@Transactional(readOnly = true)
	public static Result create() {
		Form<models.Person> personForm = form(models.Person.class);		
		return ok(views.html.persons.createForm.render(personForm));
	}
	
	@Transactional
	public static Result save() {
		Form<models.Person> personForm = form(models.Person.class).bindFromRequest();
		if (personForm.hasErrors()) {
			return badRequest(views.html.persons.createForm.render(personForm));
		}
		
		personForm.get().save();
		flash("success", "Person " + personForm.get().name + " has been created");
		
		return redirect(routes.PersonController.list(0, "name", "asc", ""));
	}
	
	
	@Transactional
	public static Result delete(Long id) {
		models.Person.findById(id).delete();
		flash("success", "Person has been deleted");
		return redirect(routes.PersonController.list(0, "name", "asc", ""));
	}
}
