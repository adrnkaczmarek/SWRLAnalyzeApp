package analyzer.controllers;

import analyzer.utils.SWRLReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ViewController
{
    private String filePath = "C:\\Users\\adrn.kaczmarek\\IdeaProjects\\SWRLAnalyze\\src\\main\\resources\\family.xml";
    private Map<String, String> rulesMap;


    @RequestMapping("/index")
    public String index(Model model)
    {
        SWRLReader reader = new SWRLReader(filePath);
        rulesMap = reader.getRules();
        String[] rules = rulesMap.values().toArray(new String[0]);
        String[] rulesNames = rulesMap.keySet().toArray(new String[0]);
        List<String> classes_list = reader.getClasses();
        classes_list.add(0, "None");
        String[] classes = classes_list.toArray(new String[0]);
        model.addAttribute("classes", classes);
        model.addAttribute("rules", rules);
        model.addAttribute("rulesNames", rulesNames);

        return "index";
    }

    @RequestMapping("/filter")
    public String filter(Model model, @RequestParam("filter") String filter)
    {
        SWRLReader reader = new SWRLReader(filePath);
        String[] rules;

        if(filter.equals("None"))
        {
            rules = rulesMap.values().toArray(new String[0]);
        }
        else
        {
            rules = getRulesFromMap(reader.getRulesNamesForClass(filter));
        }

        model.addAttribute("rules", rules);
        return "swrl_list";
    }

    @RequestMapping("/filterFunctional")
    public String filterAsFunctionalProperty(Model model)
    {
        SWRLReader reader = new SWRLReader(filePath);
        String[] rules = getRulesFromMap(reader.getFunctionalObjectProperty());
        model.addAttribute("rules", rules);
        return "swrl_list";
    }

    @RequestMapping("/filterSymmetric")
    public String filterAsSymmetricProperty(Model model)
    {
        SWRLReader reader = new SWRLReader(filePath);
        String[] rules = getRulesFromMap(reader.getSymmetricObjectProperty());
        model.addAttribute("rules", rules);
        return "swrl_list";
    }

    @RequestMapping("/filterAsSubOfProperty")
    public String filterAsSubOfProperty(Model model, @RequestParam("rule") String rule)
    {
        SWRLReader reader = new SWRLReader(filePath);
        String[] rules = getRulesFromMap(reader.getSubOfObjectProperty(rule));
        model.addAttribute("rules", rules);
        return "swrl_list";
    }

    @RequestMapping("/filterAsInverseProperty")
    public String filterAsInverseProperty(Model model, @RequestParam("rule") String rule)
    {
        SWRLReader reader = new SWRLReader(filePath);
        String[] rules = getRulesFromMap(reader.getInverseObjectProperty(rule));
        model.addAttribute("rules", rules);
        return "swrl_list";
    }

    private String[] getRulesFromMap(List<String> rulesNames)
    {
        List<String> rulesTmp = new ArrayList<>();

        for (String rule : rulesNames)
        {
            if (rulesMap.containsKey(rule))
                rulesTmp.add(rulesMap.get(rule));
            else
                rulesTmp.add(rule + "()");
        }

        return rulesTmp.toArray(new String[0]);
    }
}
