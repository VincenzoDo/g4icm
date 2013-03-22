package controllers;

import play.db.jpa.Transactional;
import play.mvc.*;

public class InstitutionController extends Controller {

	@Transactional(readOnly = true)
	public static Result list(int page, String sortBy, String order, String filter) {
		return TODO;
	}

	@Transactional(readOnly = true)
	public static Result edit(Long id) {
		return TODO;
	}

	@Transactional
	public static Result update(Long id) {
		return TODO;
	}

	@Transactional(readOnly = true)
	public static Result create() {
		return TODO;
	}

	@Transactional
	public static Result save() {
		return TODO;
	}

	@Transactional
	public static Result delete(Long id) {
		return TODO;
	}
}
