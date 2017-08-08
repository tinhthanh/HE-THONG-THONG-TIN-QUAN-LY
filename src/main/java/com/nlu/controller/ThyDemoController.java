package com.nlu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/thy-demo")
public class ThyDemoController {

	@RequestMapping(value="")
	public String  index () {
		
	return  "thydemo/index" ;
	}
	@RequestMapping( value="/login" , method = RequestMethod.POST)
	public @ResponseBody String login( String user , String pass) {
		// dat ten dung nha 
		if(user.equals("thy")) {
			if(pass.equals("thy")){
				return "true" ;
			}else{
				return "mat khau khong dung" ;
			}
		}else{
			return "tai khoan k dung" ;
		}
	}
}
