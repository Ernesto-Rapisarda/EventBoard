package it.sad.students.eventboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExampleStaticController {

    @RequestMapping("/")    //indica a spring che quel metodo action risponde al path /
    @ResponseBody               //indica che tutto il contenuto restituito dal metodo, farà parte della pagina
    public String index(){
        return "Test!";
    }

    @RequestMapping("/demo")
    @ResponseBody
    public String demo(){
        return "questa è una demo test!";
    }
}
