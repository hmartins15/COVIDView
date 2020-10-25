package com.COVID.COVIDView.Controller;

import java.io.IOException;
import java.util.List;

import javax.naming.InvalidNameException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
// withou the ui you cant call the addAttribute
import org.springframework.ui.Model;

import com.COVID.COVIDView.Exception.FieldNotFound;
import com.COVID.COVIDView.Service.COVIDService;

@org.springframework.stereotype.Controller
public class Controller {

	@Autowired
	COVIDService covidService;

	@GetMapping("/")
	public String getCOVIDData(Model model) throws IOException, InterruptedException {
		model.addAttribute("COVIDListEU", covidService.getDataEu());
		model.addAttribute("COVIDListWW", covidService.getDataWorldWide());
		return "Display";
	}

	@GetMapping("/sort+{field}")
	public String getSortedCOVIDData(@PathVariable("field") String field, Model model)
			throws IOException, InterruptedException {
		try {
			model.addAttribute("COVIDListWW", covidService.getDataWorldWide());
			model.addAttribute("COVIDListEU", covidService.getDataSorted(field));
			return "Display";
		} catch (FieldNotFound ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid URL not valid Table column name", ex);
		}

	}

}
