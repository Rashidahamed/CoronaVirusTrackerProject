package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.example.demo.model.LocationStates;
import com.example.demo.services.CoronaVirusDataServices;

@Controller
public class HomeController 
{
	
	CoronaVirusDataServices crnService;
	
	@Autowired
	public void setCrnService(CoronaVirusDataServices crnService) {
		this.crnService = crnService;
	}


	@GetMapping("/")
	public String home(Model model)
	{
		List<LocationStates> allstates=crnService.getAllstates();
		int totalDeathsReported=allstates.stream().mapToInt(stat->stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates",allstates);
		model.addAttribute("totalDeathsReported",totalDeathsReported);
		return "home";
	}
	@RequestMapping(path="/collectChartData",produces=("application/json"))
	@ResponseBody
	public List<LocationStates>collectChartData(Model model)
	{
		System.out.println("Here View Chart Data.....");
		List<LocationStates> allstates=crnService.getAllstates();
		int totalDeathsReported=allstates.stream().mapToInt(stat->stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates",allstates);
		model.addAttribute("totalDeathsReported",totalDeathsReported);
		return allstates;
		
	}
	
	@GetMapping("/collectChartData/{countryIndex}")
    @ResponseBody
    public LocationStates collectChartData(@PathVariable int countryIndex) {
        System.out.println("Here View Chart Data for country index: " + countryIndex);
        List<LocationStates> allstates = crnService.getAllstates();

        if (countryIndex >= 0 && countryIndex < allstates.size()) {
            return allstates.get(countryIndex);
        } else {
            // You may want to handle an invalid index, for example, by returning a specific response or throwing an exception.
            return null;
        }
    }
	

	@GetMapping("/viewchart")
    public String viewchart(Model model) {
		System.out.println("viewchart displayed........");
        List<LocationStates> allStates = crnService.getAllstates();
        int totalDeathsReported=allStates.stream().mapToInt(stat->stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates",allStates);
		model.addAttribute("totalDeathsReported",totalDeathsReported);
		return "viewchart";
    }

	 @GetMapping("/viewcountry/{countryIndex}")
	    public String viewCountry(@PathVariable int countryIndex, Model model) {
	        List<LocationStates> allStates = crnService.getAllstates();

	        if (countryIndex >= 0 && countryIndex < allStates.size()) {
	            LocationStates selectedCountry = allStates.get(countryIndex);
	            model.addAttribute("selectedCountry", selectedCountry);
	        } else {
	            model.addAttribute("selectedCountry", null);
	        }

	        return "viewcountry";
	    }

    }


