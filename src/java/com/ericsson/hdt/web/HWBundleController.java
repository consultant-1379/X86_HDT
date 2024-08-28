/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.APPNumberDAO;
import com.ericsson.hdt.dao.HardwareConfigDAO;
import com.ericsson.hdt.dao.NoteDAO;
import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author eadrcle
 */
@Controller
@RequestMapping(value = "hw_bundle")
public class HWBundleController {

    @Autowired
    private APPNumberDAO appNumberDAO;

    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private HardwareConfigDAO hwConfigDAO;
    @Autowired
    private NoteDAO noteDAO;

    protected final Log logger = LogFactory.getLog(getClass());

    @RequestMapping(method = RequestMethod.GET)
    public String initRequest(Model model, HttpSession session) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            HWBundle hwBundle = new HWBundle();
            model.addAttribute("hwBundle", hwBundle);
            return "hardwareBundleEditor";

        }

    }

    @RequestMapping(value = "ajax", method = RequestMethod.GET)
    public String ajaxRequest(Model model, @ModelAttribute("hardwareBundles") List<HWBundle> hwBundle, HttpServletResponse response) {

        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);

        return "hardwareBundleAjax";
    }

    @RequestMapping(value = "EOSL", method = RequestMethod.GET)
    public String EOSL(Model model, @ModelAttribute("hardwareBundles") List<HWBundle> hwBundles, HttpSession session) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            model.addAttribute("hwBundles", hwBundles);
            return "EOSL";
        }
    }

    @RequestMapping(value = "EOSL", method = RequestMethod.POST)
    public String updateEOSL(Model model, @ModelAttribute("hardwareBundles") List<HWBundle> hwBundles, HttpServletRequest request, HttpSession session) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            Iterator<HWBundle> ihwBundles = hwBundles.iterator();
            User user = (User) session.getAttribute("user");
            while (ihwBundles.hasNext()) {
                HWBundle hwBundle = ihwBundles.next();
                if (!"".equalsIgnoreCase(request.getParameter(hwBundle.getId().toString()))) {
                    hwBundle.setEol(request.getParameter(hwBundle.getId().toString()));
                    hwConfigDAO.updateHardwareEOSLDate(hwBundle, user);

                }

                if (!"NONE".equalsIgnoreCase(request.getParameter(hwBundle.getId().toString() + "_status"))) {
                    if ("yes".equalsIgnoreCase(request.getParameter(hwBundle.getId().toString() + "_status"))) {
                        hwBundle.setEolStatus(true);
                    } else {
                        hwBundle.setEolStatus(false);
                    }
                    hwConfigDAO.updateHardwareEOSLStatus(hwBundle, user);
                }

            }
            return "administrator";

        }

    }

    @RequestMapping(value = "searchHardwareByName", method = RequestMethod.GET)
    public String findHardwareByName(HttpSession session) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            return "searchForHardwareBundle";

        }

    }

    @RequestMapping(value = "searchHardwareByName", method = RequestMethod.POST)
    public String searchForHardwareByName(Model model,
            @RequestParam(value = "searchOption") String searchOption,
            @RequestParam(value = "searchtext", required = false) String searchtext, HttpSession session) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            List<HWBundle> hwBundles = null;
            if ("hardwareDescription".equalsIgnoreCase(searchOption)) {
                hwBundles = hwConfigDAO.searchForHWBundleByDescription(searchtext);
            } else if ("appNumber".equalsIgnoreCase(searchOption)) {
                hwBundles = hwConfigDAO.searchForHWBundleWithAPPNumber(searchtext);
            } else if ("AppDescription".equalsIgnoreCase(searchOption)) {
                hwBundles = hwConfigDAO.searchForHWBundleByAPPDescription(searchtext);
            }
            Iterator<HWBundle> ihwBundles = hwBundles.iterator();
            while (ihwBundles.hasNext()) {
                HWBundle hwBundle = ihwBundles.next();
                List<APPNumber> appNumbers = appNumberDAO.getListHWAPPNumber(hwBundle);
                hwBundle.setAppList(appNumbers);
            }
            Iterator<HWBundle> allHWBundles = hwBundles.iterator();
            Map<HWBundle, List<Role>> currentRoles = new HashMap<HWBundle, List<Role>>();
            while (allHWBundles.hasNext()) {
                HWBundle hw = allHWBundles.next();
                List<Role> hardwareRoles = hwConfigDAO.findRolesWithHWBundle(hw);
                currentRoles.put(hw, hardwareRoles);
                if (hardwareRoles.size() > 0) {
                    hw.setAssignToRole(true);
                }

            }

            model.addAttribute("currentRoles", currentRoles);
            model.addAttribute("hwBundles", hwBundles);
            return "searchForHardwareBundle";
        }

    }

    @RequestMapping(value = "hardwareAlternative", method = RequestMethod.POST)
    public String hardwareAlternativeAjax(Model model, @RequestParam(value = "hardwareID", required = true) String hardwareID,
            @RequestParam("product_weight") String product_weight, @RequestParam(value = "network") String network,
            @RequestParam("version") String version, @RequestParam("bundle") String bundleID, @RequestParam("site") List<String> sites,
            @RequestParam(value = "role") String roleID, HttpServletResponse response, HttpSession session) {

        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        Product p = new Product(Integer.parseInt(product_weight));
        Network n = new Network(Integer.parseInt(network));
        Version v = new Version(version);
        Bundle b = new Bundle(Integer.parseInt(bundleID));
        Role role = new Role(Integer.parseInt(roleID));
        Site site = new Site(Integer.parseInt(sites.get(0)));
        List<Role> allRoles = (List<Role>) session.getAttribute("roles");
        Integer hwID = Integer.parseInt(hardwareID);
        role.setSite(site);
        HWBundle hwBundle = hwConfigDAO.getIndividualHWBundle(hwID);
        int objectIndex = allRoles.indexOf(role);
        if (objectIndex != -1) {
            Role r = allRoles.get(objectIndex);
            List<HWBundle> hw = r.getHardwareBundle();
            Iterator<HWBundle> ihw = hw.iterator();
            while (ihw.hasNext()) {
                HWBundle h = ihw.next();
                if (h.getId() == hwID.intValue()) {
                    h.setSelected(true);
                    hwBundle.setRevision(h.getRevision());
                    hwBundle.setExpectedResult(h.getExpectedResult());
                } else {
                    h.setSelected(false);
                }
            }
        }

        List<APPNumber> apps = appNumberDAO.getListHWAPPNumber(hwBundle);
        hwBundle.setAppList(apps);
        try {
            hwBundle.setNote(noteDAO.productHardwareNote(p, b, n, v, role, hwBundle));
        } catch (EmptyResultDataAccessException ex) {
        }

        model.addAttribute("site", site);
        model.addAttribute("role", role);
        model.addAttribute("hardware", hwBundle);
        return "hardwareBundleAjax";

    }

    @RequestMapping(value = "configuration", method = RequestMethod.GET)
    public String getAllHardwareConfiguration(Model model, @ModelAttribute("hardwareBundles") List<HWBundle> hwBundles, HttpSession session) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            Iterator<HWBundle> allHWBundles = hwBundles.iterator();
            Map<HWBundle, List<Role>> currentRoles = new HashMap<HWBundle, List<Role>>();
            while (allHWBundles.hasNext()) {
                HWBundle hw = allHWBundles.next();
                List<Role> hardwareRoles = hwConfigDAO.findRolesWithHWBundle(hw);
                currentRoles.put(hw, hardwareRoles);
                if (hardwareRoles.size() > 0) {
                    hw.setAssignToRole(true);
                }

            }
            //model.addAttribute("currentRoles", currentRoles);
            return "updateConfigAPP";

        }

    }

    @RequestMapping(value = "getRolesHardwareUsageAjax", method = RequestMethod.GET)
    public String getHardwareAppNumberMapping(Model model, @ModelAttribute("hardwareBundles") List<HWBundle> hwBundles, @RequestParam(value = "hardwareID", required = true) String hardwareID, HttpSession session, HttpServletRequest request) {

        List<Role> currentRoles = new ArrayList<Role>();
        HWBundle hw = hwConfigDAO.getIndividualHWBundle(Integer.parseInt(hardwareID));

        if (hw != null) {

            List<Role> hardwareRoles = hwConfigDAO.findRolesWithHWBundle(hw);
            currentRoles.addAll(hardwareRoles);
            model.addAttribute("currentRoles", currentRoles);
        }

        return "rolesHardwareUsageAjax";
    }

    @RequestMapping(value = "gethardwareBundleDescriptionAJax", method = RequestMethod.GET)
    public String getHardwareBundleDescription(Model model, @ModelAttribute("hardwareBundles") List<HWBundle> hwBundles, @RequestParam(value = "hardwareID", required = true) String hardwareID, HttpSession session, HttpServletRequest request) {
        HWBundle hw = hwConfigDAO.getIndividualHWBundle(Integer.parseInt(hardwareID));

        List<APPNumber> hwAPPNumber = appNumberDAO.getListHWAPPNumber(hw);
        hw.setAppList(hwAPPNumber);

        model.addAttribute("hardware", hw);

        return "hardwareBundleDescriptionAJax";
    }

    @RequestMapping(value = "getHardwareAppListAJax", method = RequestMethod.GET)
    public String getHardwareAppListAJax(Model model, @ModelAttribute("hardwareBundles") List<HWBundle> hwBundles, @RequestParam(value = "hardwareID", required = true) String hardwareID, HttpSession session, HttpServletRequest request) {
        HWBundle hw = hwConfigDAO.getIndividualHWBundle(Integer.parseInt(hardwareID));

        List<APPNumber> hwAPPNumber = appNumberDAO.getListHWAPPNumber(hw);
        hw.setAppList(hwAPPNumber);

        model.addAttribute("hardware", hw);

        return "updateHardwareAppListAjax";
    }

    @RequestMapping(value = "generation", method = RequestMethod.GET)
    public String getHardwareGenerationType(Model model, HttpSession session) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            return "updateHardwareGeneration";
        }

    }

    @RequestMapping(value = "generation", method = RequestMethod.POST)
    public String updateHardwareGenerationType(Model model, @ModelAttribute("hardwareBundles") List<HWBundle> hwBundles, HttpServletRequest request, HttpSession session) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            User user = (User) session.getAttribute("user");
            Iterator<HWBundle> ihwBundle = hwBundles.iterator();
            while (ihwBundle.hasNext()) {
                HWBundle hwBundle = ihwBundle.next();
                String generation = request.getParameter(hwBundle.getId().toString());
                if (!"none".equalsIgnoreCase(generation)) {
                    hwBundle.setGenerationType(generation);
                    hwConfigDAO.updateHardwareBundleGenerationType(hwBundle, user);
                }

            }
            return "updateHardwareGeneration";

        }

    }

    @RequestMapping(value = "addHardwareAPP", method = RequestMethod.GET)
    public String updateHWConfigAPPList(Model model, @ModelAttribute("hardwareBundles") List<HWBundle> hwBundles, HttpSession session) {

        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            List<APPNumber> appNumbers = appNumberDAO.get();

            Iterator<HWBundle> allHWBundles = hwBundles.iterator();
            Map<HWBundle, List<Role>> currentRoles = new HashMap<HWBundle, List<Role>>();
            while (allHWBundles.hasNext()) {
                HWBundle hw = allHWBundles.next();
                List<Role> hardwareRoles = hwConfigDAO.findRolesWithHWBundle(hw);
                //   currentRoles.put(hw, hardwareRoles);
                if (hardwareRoles.size() > 0) {
                    hw.setAssignToRole(true);
                }

            }

            //model.addAttribute("currentRoles", currentRoles);
            model.addAttribute("appList", appNumbers);
            return "updateHardwareAPPList";
        }

    }

    @RequestMapping(value = "updateHardwareConfigApp", method = RequestMethod.POST)
    public String updateProductReleaseRoleHardwareAPP(Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            int updatedRows = 0;
            List<HWBundle> hwBundles = hwConfigDAO.getAllHWBundles();
            Iterator<HWBundle> bundles = hwBundles.iterator();
            User user = (User) session.getAttribute("user");
            while (bundles.hasNext()) {
                HWBundle hw = bundles.next();
                List<APPNumber> hwAPPNumber = appNumberDAO.getListHWAPPNumber(hw);
                Iterator<APPNumber> apps = hwAPPNumber.iterator();
                while (apps.hasNext()) {
                    APPNumber app = apps.next();
                    String QTY = request.getParameter("QTY-" + hw.getId() + app.getId());
                    Integer value = Integer.parseInt(QTY);
                    if (app.getQty() != value) {
                        if (value == 0) {
                            hwConfigDAO.deleteHWBundleAPP(hw, app, user);
                        } else {
                            app.setQty(Integer.parseInt(QTY));
                            updatedRows = hwConfigDAO.updateHWConfigAPPQty(hw, app, user);
                        }
                    }
                }
            }
            return "administrator";
        }

    }

    @RequestMapping(value = "desc", method = RequestMethod.GET)
    public String getHardwareDescription(Model model, HttpSession session) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            return "updateHardwareDescription";

        }

    }

    @RequestMapping(value = "updateHardwareDescription", method = RequestMethod.POST)
    public String updateHardwareDescription(Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session) {

        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            int updatedRows = 0;
            User user = (User) session.getAttribute("user");
            List<HWBundle> hwBundles = hwConfigDAO.getAllHWBundles();
            Iterator<HWBundle> bundles = hwBundles.iterator();
            while (bundles.hasNext()) {
                HWBundle hw = bundles.next();
                String description = request.getParameter(hw.getId().toString());
                if (!(hw.getDesc().equalsIgnoreCase(description))) {
                    hw.setDesc(description);
                    updatedRows = hwConfigDAO.updateHWConfigDescription(hw, user);
                }
            }
            return "administrator";
        }

    }

    @RequestMapping(value = "updateHardwareConfigAppList", method = RequestMethod.POST)
    public String updateHardwareConfigAPPList(Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session) {

        // Lets remove the iterator as we are only updating one hardware ID.  20th April 2016 comment..
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            int updatedRows = 0;
            User user = (User) session.getAttribute("user");
            APPNumber app = new APPNumber();
            List<HWBundle> hwBundles = hwConfigDAO.getAllHWBundles();
            Iterator<HWBundle> bundles = hwBundles.iterator();
            while (bundles.hasNext()) {
                HWBundle hw = bundles.next();
                if (request.getParameter(hw.getId().toString()) != null) {
                    String newAPPID = request.getParameter(hw.getId().toString());
                    String qty = request.getParameter("QTY-" + hw.getId().toString());
                    if (!(newAPPID.equalsIgnoreCase("NONE"))) {
                        app.setId(Integer.parseInt(newAPPID));
                        if (!(qty.equalsIgnoreCase(""))) {
                            app.setQty(Integer.parseInt(qty));
                        } else {
                            app.setQty(1);
                        }
                        hwConfigDAO.updateHWConfigAPPList(hw, app, user);

                    }
                }

            }
            return "administrator";

        }

    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String getHardwareListForDeletion(Model model, @ModelAttribute("hardwareBundles") List<HWBundle> hwBundles, HttpSession session) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            Iterator<HWBundle> ihwBundles = hwBundles.iterator();
            while (ihwBundles.hasNext()) {
                HWBundle hw = ihwBundles.next();
                List<Role> roles = hwConfigDAO.findRolesWithHWBundle(hw);
                if (roles.size() > 0) {
                    hw.setAssignToRole(true);
                } else {
                    hw.setAssignToRole(false);

                }
            }
            return "deleteHardwareBundle";
        }

    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String deleteHWConfig(Model model, HttpServletRequest request, HttpSession session) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            int updatedRows = 0;
            User user = (User) session.getAttribute("user");
            List<HWBundle> hwBundles = hwConfigDAO.getAllHWBundles();
            Iterator<HWBundle> ihwBundles = hwBundles.iterator();
            while (ihwBundles.hasNext()) {
                HWBundle hw = ihwBundles.next();
                if (request.getParameter(hw.getId().toString()) != null) {
                    updatedRows += hwConfigDAO.delete(hw, user);
                }
            }
            return "administrator";

        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@Valid @ModelAttribute("hwBundle") HWBundle hwBundle, BindingResult result, Model model, HttpSession session, HttpServletRequest req) {
        if (!CheckUserCreditantials.isCorrect(session, "user", 2)) {
            return CheckUserCreditantials.redirect(2);
        } else {
            if (result.hasErrors()) {

                return "hardwareBundleEditor";

            } else {
                User user = (User) session.getAttribute("user");
                int hwID;
                hwID = hwConfigDAO.add(hwBundle, user);
                hwBundle.setId(hwID);
                List<APPNumber> appList = hwBundle.getAppList();
                Iterator<APPNumber> a = appList.iterator();
                while (a.hasNext()) {
                    APPNumber app = a.next();
                    String box = String.valueOf(app.getId());
                    String stringQTY = req.getParameter(box);
                    Integer qty = Integer.parseInt(stringQTY);
                    app.setQty(qty);
                }
                hwConfigDAO.addAPPNumbers(hwBundle, user);
            } // end else 

            return "redirect:/hw_bundle.htm";

        }

    }

    @ModelAttribute("hardwareBundles")
    public List<HWBundle> populateHardwareBundleList() {
        List<HWBundle> hwBundles = hwConfigDAO.getAllHWBundles();
        Iterator<HWBundle> bundles = hwBundles.iterator();
        while (bundles.hasNext()) {
            HWBundle hw = bundles.next();
            List<APPNumber> hwAPPNumber = appNumberDAO.getListHWAPPNumber(hw);
            hw.setAppList(hwAPPNumber);
        }

        return hwBundles;
    }

    @ModelAttribute("appList")
    public List<APPNumber> populateAPPNumberList() {

        List<APPNumber> apps = appNumberDAO.get();

        return apps;
    }

    @ModelAttribute("generations")
    public List<String> populateGenerationType() {

        List<String> generations = hwConfigDAO.getListHardwareGeneration();

        return generations;
    }

}
