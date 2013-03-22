package controllers;

import play.mvc.*;

public class ApplicationController extends Controller {

	public static Result index() {
		return redirect(routes.PersonController.list(0, "name", "asc", ""));
	}
}