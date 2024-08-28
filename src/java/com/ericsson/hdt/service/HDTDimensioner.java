/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.service;

import com.ericsson.hdt.dao.APPNumberDAO;
import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.ComponentDAO;
import com.ericsson.hdt.dao.FormulaDAO;
import com.ericsson.hdt.dao.HardwareConfigDAO;
import com.ericsson.hdt.dao.NetworkDAO;
import com.ericsson.hdt.dao.NoteDAO;
import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.ProductDAO;
import com.ericsson.hdt.dao.RackDAO;
import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.dao.SystemConfigurationDAO;
import com.ericsson.hdt.dao.URLLinkDAO;
import com.ericsson.hdt.impl.APPNumberDAOImpl;
import com.ericsson.hdt.impl.BundleDAOImpl;
import com.ericsson.hdt.impl.ComponentDAOImpl;
import com.ericsson.hdt.impl.FormulaDAOImpl;
import com.ericsson.hdt.impl.HardwareConfigDAOImpl;
import com.ericsson.hdt.impl.NetworkDAOImpl;
import com.ericsson.hdt.impl.NoteDAOImpl;
import com.ericsson.hdt.impl.ParameterDAOImpl;
import com.ericsson.hdt.impl.ProductDAOImpl;
import com.ericsson.hdt.impl.RackDAOImpl;
import com.ericsson.hdt.impl.RoleDAOImpl;
import com.ericsson.hdt.impl.SystemConfigurationDAOImpl;
import com.ericsson.hdt.impl.URLLinkDAOImpl;
import com.ericsson.hdt.interfaces.IDimensioner;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.SystemDetails;
import com.ericsson.hdt.model.UrlLink;
import com.ericsson.hdt.model.Version;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author eadrcle
 */
public class HDTDimensioner implements IDimensioner {

    private final NoteDAO noteDAO = new NoteDAOImpl();

    private final ParameterDAO parameterDAO = new ParameterDAOImpl();

    private final RoleDAO roleDAO = new RoleDAOImpl();

    private final FormulaDAO formulaDAO = new FormulaDAOImpl();

    private final HardwareConfigDAO hwConfigDAO = new HardwareConfigDAOImpl();

    private final APPNumberDAO appNumberDAO = new APPNumberDAOImpl();

    private final URLLinkDAO urlLinkDAO = new URLLinkDAOImpl();

    private final ProductDAO productDAO = new ProductDAOImpl();

    private final NetworkDAO networkDAO = new NetworkDAOImpl();

    private final BundleDAO bundleDAO = new BundleDAOImpl();

    private final RackDAO rackDAO = new RackDAOImpl();

    private final ComponentDAO componentDAO = new ComponentDAOImpl();

    private final SystemConfigurationDAO systemConfigDAO = new SystemConfigurationDAOImpl();

    protected final Log logger = LogFactory.getLog(getClass());

    private List<Parameter> setParameterValues(Product product, Version version, Network network, Bundle bundle, Map<String, String> parameters, HttpSession session) {

        List<Parameter> releaseParameter = parameterDAO.getProductReleaseParameterList(product, version, network, bundle);
        Iterator<Parameter> ipar = releaseParameter.iterator();

        while (ipar.hasNext()) {
            Parameter parameter = ipar.next();
            // If Parameter is not enabled no need to check if value has changed..
            if (parameter.getEnabled() && parameter.getVisible() != 0) {

                if (!parameter.getParType().getType().equalsIgnoreCase("boolean")) {

                    parameter.setValue(Double.parseDouble(parameters.get(parameter.getId().toString())));

                } else {

                    if (parameters.get(parameter.getId().toString()) != null) {
                        parameter.setValue(1); // Checkbox Clicked

                    } else {
                        parameter.setValue(0); //Checkbox not Clicked
                    }

                }

            }

            //engine.put(parameter.getName(), parameter.getValue());
            List<Parameter> subPar = parameterDAO.getProductReleaseSubParameter(product, version, network, bundle, parameter);
            if (subPar != null) {
                Iterator<Parameter> isubPar = subPar.iterator();
                while (isubPar.hasNext()) {
                    Parameter subP = isubPar.next();
                    // If Parameter is not enabled no need to check if value has changed..
                    if (subP.getEnabled() && subP.getVisible() != 0) {

                        if (!(subP.getParType().getType().equalsIgnoreCase("boolean"))) {

                            subP.setValue(Double.parseDouble(parameters.get(subP.getId().toString())));

                        } else {

                            if (parameters.get(subP.getId().toString()) != null) {

                                subP.setValue(1);

                            } else {

                                subP.setValue(0);
                            }

                        }

                    }

                    //engine.put(subP.getName(), subP.getValue());
                }

                parameter.setSubParameters(subPar);

            }

        }  // End While Parameters

        return releaseParameter;

    }

    private List<Parameter> setParameterValues(Product product, Version version, Network network, Bundle bundle, HttpServletRequest request) {
        List<Parameter> releaseParameter = parameterDAO.getProductReleaseParameterList(product, version, network, bundle);

        Iterator<Parameter> iparameter = releaseParameter.iterator();
        String value;

        while (iparameter.hasNext()) {
            Parameter pp = iparameter.next();

            // If Parameter is not enabled no need to check if value has changed..
            if (pp.getEnabled() && pp.getVisible() == 1) {
                if (!(pp.getParType().getType().equalsIgnoreCase("boolean"))) {

                    value = request.getParameter(pp.getId().toString());
                    pp.setValue(Double.parseDouble(value));

                } else {

                    if (request.getParameter(pp.getId().toString()) != null) {
                        pp.setValue(1);

                    } else {
                        pp.setValue(0);
                    }

                }
            }

            List<Parameter> subPar = parameterDAO.getProductReleaseSubParameter(product, version, network, bundle, pp);
            if (subPar != null) {
                Iterator<Parameter> isubPar = subPar.iterator();
                while (isubPar.hasNext()) {
                    Parameter subP = isubPar.next();
                    // If Parameter is not enabled no need to check if value has changed..
                    // and it visible=1 which indicated that it is shown on the main parameters page.
                    if (subP.getEnabled() && subP.getVisible() == 1) {

                        if (!(subP.getParType().getType().equalsIgnoreCase("boolean"))) {

                            value = request.getParameter(subP.getId().toString());
                            subP.setValue(Double.parseDouble(value));

                        } else {

                            if (request.getParameter(subP.getId().toString()) != null) {
                                subP.setValue(1);

                            } else {
                                subP.setValue(0);
                            }

                        }
                    }

                }

                pp.setSubParameters(subPar);

            }

        }

        return releaseParameter;

    }

    public void dumpParametersIntoJavaScriptEngine(List<Parameter> parameters, ScriptEngine engine) {

        Iterator<Parameter> iparameter = parameters.iterator();
        while (iparameter.hasNext()) {
            Parameter parameter = iparameter.next();

            engine.put(parameter.getName(), parameter.getValue());
            if (parameter.getHasSubParameters()) {
                List<Parameter> subParameterList = parameter.getSubParameters();
                Iterator<Parameter> isubParameter = subParameterList.iterator();
                while (isubParameter.hasNext()) {
                    Parameter subParameter = isubParameter.next();
                    engine.put(subParameter.getName(), subParameter.getValue());
                }

            }

        }

    }

    private void assignRoleHardwareConfiguration(Role role, Product p, Version v, Network n, Bundle b, Set<String> hardwareTypes) {

        // Returning the Hardware with the associated result and the highest revision of Hardware first. This is then set as the selected
        // hardware to dislay to the user.
        List<HWBundle> roleHWBundle = hwConfigDAO.findRoleHardwareBundleWithResult(p, v, n, b, role);

        // If Hardware is not found in this bundle check other bundle.
        if (roleHWBundle.size() < 1) {

            roleHWBundle = hwConfigDAO.findRoleHardwareWithResult(p, v, n, role);

        }

        Iterator<HWBundle> iHWBundle = roleHWBundle.iterator();
        int selected = 1;
        while (iHWBundle.hasNext()) {
            HWBundle hw = iHWBundle.next();
            hardwareTypes.add(hw.getGenerationType());

            List<APPNumber> apps = appNumberDAO.getListHWAPPNumber(hw);

            hw.setAppList(apps);
            try {

                hw.setNote(noteDAO.productHardwareNote(p, b, n, v, role, hw));

            } catch (EmptyResultDataAccessException ex) {
                //logger.info("No Hardware Note Define for Hardware: " + role.getDescription());
            }

                    // Setting 
            if (selected == 1) {
                hw.setSelected(true);
                selected = 2;

            }

        }

        role.setHardwareBundle(roleHWBundle);

    }

    private Role findEnclosureRole(List<Role> roles, Site site) {
        Role enclosure = null;
        Iterator<Role> iroles = roles.iterator();
        while (iroles.hasNext()) {
            Role role = iroles.next();
            if (role.getSite().equals(site)) {
                if (role.getName().equalsIgnoreCase("enclosure")) {
                    enclosure = role;
                }
            }

        }

        return enclosure;

    }

    private void calculateEnclosure(List<Role> allRoles, Role enclosure, List<Parameter> parameters, Site site) {

        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        HashMap<APPNumber, Integer> appMap = new HashMap<APPNumber, Integer>(1);
        dumpParametersIntoJavaScriptEngine(parameters, engine);
        Iterator<Role> iroles = allRoles.iterator();

        while (iroles.hasNext()) {

            Role role = iroles.next();

            if (role.getSite().equals(site)) {
                List<HWBundle> hwBundles = role.getHardwareBundle();
                Iterator<HWBundle> ihwBundles = hwBundles.iterator();
                while (ihwBundles.hasNext()) {
                    HWBundle hwBundle = ihwBundles.next();
                    if (hwBundle.getSelected()) {
                        List<APPNumber> appNumbers = hwBundle.getAppList();
                        Iterator<APPNumber> iapps = appNumbers.iterator();
                        while (iapps.hasNext()) {
                            APPNumber appNumber = iapps.next();
                            if (appNumber.getExposeToEngine()) {
                                    
                                if (appMap.containsKey(appNumber)) {
                                    Integer currentValue = appMap.get(appNumber);
                                    appMap.put(appNumber, currentValue + appNumber.getQty());

                                } else {
                                    appMap.put(appNumber, appNumber.getQty());

                                }
                            }
                        }

                    }

                }

            }

        }

        // Passes list of exposed APP/INE numbers to the engine to used in calcaluting the formula.....
        Set<APPNumber> keys = appMap.keySet();
        Iterator<APPNumber> iapps = keys.iterator();
        while (iapps.hasNext()) {
            APPNumber appNumber = iapps.next();
            engine.put(appNumber.getStrippedName()+ "_QTY", appMap.get(appNumber));
        }
        
        
        try {

                engine.eval(enclosure.getFormula().getFormula());
            } catch (NullPointerException np) {
                logger.info("Role: " + enclosure.getName() + "[" + enclosure.getName() + "] Script was empty");
                enclosure.setExpectedResult("Role: " + enclosure.getName() + "[" + enclosure.getName() + "] Script was empty");
            } catch (ScriptException ex) {
                Logger.getLogger(HDTDimensioner.class.getName()).log(Level.SEVERE, null, ex);
                enclosure.setExpectedResult(ex.getMessage());
            }
        
        
        
        
        // If result variable is not null set the value, else leave the result message to be view by the Administrator.
        // This will no be show to frontend users and this variable is used to find the correct hardware bundle for the role.

        if (engine.get(enclosure.getVariableName()) != null) {

            enclosure.setExpectedResult(engine.get(enclosure.getVariableName()));
        }
       //logger.info(engine.get(enclosure.getVariableName()));

    }

    // Checking has this System defined a System Object. If it has check for additional information as defined in the system 
    // tables for that particular release, if this has been defined run the assocaited JavaScript to calculate the results that are 
    // defined system tables. If it hasn't been defined create a default System oBject to Dislay information to the 
    // User about their selected System such as the Product Name, release , associated bundle.
    //
    // 
    private SystemDetails dimensionSystemDetailsObject(Product p, Version v, Network n, List<Parameter> parameters, ScriptEngine engine) {

        SystemDetails systemDetailObject = systemConfigDAO.getDefinedProductSystemVariables(p, v, n);

        if (systemDetailObject != null) {

            Formula formula = formulaDAO.getIndividualFormula(systemDetailObject.getFormula().getName());
            dumpParametersIntoJavaScriptEngine(parameters, engine);

            try {
                engine.eval(formula.getFormula());

                Map<String, Map<String, Object>> systemVariables = systemDetailObject.getSystemVariableDetails();

                Set<String> variableName = systemVariables.keySet();
                Iterator<String> ivariableName = variableName.iterator();

                while (ivariableName.hasNext()) {
                    String VariableKey = ivariableName.next();

                    Map<String, Object> systemDescription = systemVariables.get(VariableKey);
                    Set<String> descriptionKey = systemDescription.keySet();
                    Iterator<String> idescriptionKey = descriptionKey.iterator();
                    while (idescriptionKey.hasNext()) {
                        String key = idescriptionKey.next();

                        systemDescription.put(key, engine.get(VariableKey));

                    }

                }

            } catch (NullPointerException np) {

            } catch (ScriptException ex) {
                Logger.getLogger(HDTDimensioner.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            systemDetailObject = new SystemDetails();

        }

        return systemDetailObject;

    }

    private Bundle determineSystemBundle(Product p, Version v, Network n, Bundle b, SystemDetails systemDetailObject) {

        List<Bundle> bundles = bundleDAO.getProductReleaseBundle(p, v, n);
        try {

            Bundle bundleSize = bundleDAO.getBundle((String) systemDetailObject.getSystemVariableDetails().get("bundle_size").get("Bundle"));

            if (bundles.contains(bundleSize)) {
                int index = bundles.indexOf(bundleSize);
                b = bundles.get(index);
            }
        } catch (NullPointerException np) {

        } catch (EmptyResultDataAccessException ex) {

        }

        return b;
    }

    @Override
    public Map<String, Object> calculateResult(Product product, Version version, Network network, Bundle selectedBundle, HttpServletRequest request, HttpSession session) {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        Product p = productDAO.getCombinedProduct(product);;
        Network n = network;
        Version v = version;
        Bundle b = bundleDAO.getBundle(selectedBundle.getID());
        String value = "";
        String networkName = "";
        int count = 1;

        Integer netWeight = new Integer(0);

        // Get the Name of the Product and Bundle from the ID using the DAO. Used in the view to show the User a text string 
        // instead of a primary Key ID.
        //Product productName  = productDAO.getCombinedProduct(p);
        //product.setWeighting(p.getWeighting());
        List<Network> net = networkDAO.getCombinedNetwork(n);

        Iterator<Network> inetwork = net.iterator();
        while (inetwork.hasNext()) {
            Network network1 = inetwork.next();

            netWeight += network1.getNetworkWeight();
            if (count > 1) {
                networkName += "_";
            }
            count++;
            networkName += network1.getName();
        }

        n.setName(networkName);
        //Bundle bundleName = bundleDAO.getBundle(b.getID());

        Map<String, Object> dimensioningModel = new HashMap<String, Object>();

        //List<Note> releaseNotes = noteDAO.getVisibileProductReleaseNotes(p, b, n, v);
        List<Parameter> releaseParameter = setParameterValues(p, v, n, b, request);
        List<UrlLink> helpMenuLinks = urlLinkDAO.getProductReleaseURLLinks(p, v, n, b);
        List<UrlLink> defaultLinks = urlLinkDAO.getDefaultLinks();
        List<UrlLink> allLinks = new ArrayList<UrlLink>();
        allLinks.addAll(defaultLinks);
        allLinks.addAll(helpMenuLinks);

        // Used to keep track of each roles Parameters on first pass all parameter will be the same but user may wish to change the parameter for a particular role.
        // This need to be capture and displayed in the BOM for the User.
        Map<Role, List<Parameter>> roleParameters = new HashMap<Role, List<Parameter>>();

        Parameter pa;

        List<Role> allRoles = new ArrayList<Role>(1);
        List<Site> sites = roleDAO.findNumberOfSites(p, v, n, b);

        if (sites.size() > 1) {

            Parameter geoPar = parameterDAO.getParameter("GEO_RED");
            if (releaseParameter.indexOf(geoPar) != -1) {

                int index = releaseParameter.indexOf(geoPar);
                pa = releaseParameter.get(index);

                if (pa.getValue() != 1) {
                    int lastElement = sites.size();
                    sites.remove(lastElement - 1);
                }

            } else {
                int lastElement = sites.size();
                sites.remove(lastElement - 1);

            }

        }

        SystemDetails systemDetailObject = dimensionSystemDetailsObject(p, v, n, releaseParameter, engine);
        b = determineSystemBundle(p, v, n, b, systemDetailObject);
        systemDetailObject.setProduct(p);
        systemDetailObject.setNetwork(n);
        systemDetailObject.setBundle(b);
        systemDetailObject.setVersion(v);
        List<Note> releaseNotes = noteDAO.getVisibileProductReleaseNotes(p, b, n, v);

        Iterator<Site> isites = sites.iterator();
        Note note = null;
        Set<String> hardwareTypes = new HashSet<String>();

        while (isites.hasNext()) {

            Site site = isites.next();
            List<Role> definedRoles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
            Iterator<Role> iroles = definedRoles.iterator();
            while (iroles.hasNext()) {
                Role role = iroles.next();
                role.setSite(site);
                engine = factory.getEngineByName("JavaScript");
                dumpParametersIntoJavaScriptEngine(releaseParameter, engine);

                roleParameters.put(role, releaseParameter);
                try {
                    role.setNote(noteDAO.getProductRelaseNotePerRole(p, b, n, v, role));
                } catch (EmptyResultDataAccessException ex) {

                }
                Formula f = formulaDAO.getFormulaPerRole(p, v, n, b, role);
                role.setFormula(f);
                try {
                    engine.eval(f.getFormula());
                } catch (NullPointerException np) {
                    logger.info("Role: " + role.getName() + "[" + f.getName() + "] Script was empty for ");
                    role.setExpectedResult("Role: " + role.getName() + "[" + f.getName() + "] Script was empty");
                } catch (ScriptException ex) {
                    logger.info("Role: " + role.getName() + "[" + f.getName() + "] Script Exception occurred--\n" + ex.getMessage());
                    role.setExpectedResult(ex.getMessage());
                }
                role.setExpectedResult(engine.get(role.getVariableName()));
                if (engine.get(role.getVariableName()) == null) {
                    logger.info("No Result return from the formula[" + f.getName() + "] for Role:[" + role.getName() + "]");
                    logger.info("Ensure the result variable [" + role.getVariableName() + "] is defined in the Formula.");
                    logger.info("Please check that the Javascript is correct!!!");
                    role.setExpectedResult("No Result return from the formula[" + f.getName() + "] for Role:[" + role.getName() + "]"
                            + " Ensure the result variable [" + role.getVariableName() + "] is defined in the Formula."
                            + " and check that the Javascript is correct!!!"
                    );
                }

                assignRoleHardwareConfiguration(role, p, v, n, b, hardwareTypes);

                allRoles.add(role);

            }

            Role enclosure = findEnclosureRole(allRoles, site);

            if (enclosure != null) {
                calculateEnclosure(allRoles, enclosure, releaseParameter, site);
                assignRoleHardwareConfiguration(enclosure, p, v, n, b, hardwareTypes);
            }

        }

        dimensioningModel.put("parameters", releaseParameter);
        dimensioningModel.put("hardwareGeneration", hardwareTypes);
        dimensioningModel.put("roles", allRoles);
        dimensioningModel.put("sites", sites);
        dimensioningModel.put("default_links", allLinks);
        dimensioningModel.put("notes", releaseNotes);
        dimensioningModel.put("systemObjectDetails", systemDetailObject);
        dimensioningModel.put("rolesParameter", roleParameters);

        return dimensioningModel;

    }

    @Override
    public Map<String, Object> changeHardwareGeneration(Product product, Version version, Network network, Bundle bundle, Map<String, String> parameters, HttpSession session) {

        Map<String, Object> dimensioningModel = new HashMap<String, Object>();
        Product p = product;
        Network n = network;
        Version v = version;
        Bundle b = bundle;
        Role enclosure = null;
        String value = "";
        String networkName = "";
        int count = 1;
        int selectedHardware = 0;
        List<Site> sites = (List<Site>) session.getAttribute("sites");
        Iterator<Site> isites = sites.iterator();
        Integer netWeight = new Integer(0);
        Set<String> hardwareTypes = new HashSet<String>();
        List<Role> roles = (List<Role>) session.getAttribute("roles");
        Iterator<Role> iroles = roles.iterator();
        List<Parameter> releaseParameter = (List<Parameter>) session.getAttribute("parameters");
        Boolean hardwareGenerationPresent = false;

        while (isites.hasNext()) {
            Site site = isites.next();
            iroles = roles.iterator();

            while (iroles.hasNext()) {

                selectedHardware = 0;
                Role role = iroles.next();
                if (role.getSite().equals(site)) {
                    hardwareGenerationPresent = false;
                    List<HWBundle> hwBundles = role.getHardwareBundle();
                    Iterator<HWBundle> ihw = hwBundles.iterator();
                    while (ihw.hasNext()) {
                        HWBundle hw = ihw.next();
                        hardwareTypes.add(hw.getGenerationType());
                        if (hw.getGenerationType().equalsIgnoreCase(parameters.get("hardwareGen"))) {
                            hardwareGenerationPresent = true;
                        }

                    }

                    if (hardwareGenerationPresent) {
                        ihw = hwBundles.iterator();
                        while (ihw.hasNext()) {
                            HWBundle hw = ihw.next();
                            if (selectedHardware == 0) {
                                if (hw.getGenerationType().equalsIgnoreCase(parameters.get("hardwareGen"))) {
                                    hw.setSelected(true);
                                    selectedHardware = 1;
                                } else {
                                    hw.setSelected(false);
                                }
                            } else {
                                hw.setSelected(false);

                            }
                        }
                    }

                }

            }
            enclosure = findEnclosureRole(roles, site);

            if (enclosure != null) {
                calculateEnclosure(roles, enclosure, releaseParameter, site);
                assignRoleHardwareConfiguration(enclosure, p, v, n, b, hardwareTypes);
            }

        }

        dimensioningModel.put("roles", roles);

        return dimensioningModel;

    }

    @Override
    public Map<String, Object> calculateIndividualHardware(Product p, Version v, Network n, Bundle b, Map<String, String> parameters, HttpSession session, Role role) {

        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        Map<String, Object> dimensioningModel = new HashMap<String, Object>();
        Role enclosure = null;
        Site site = null;

        Set<String> hardwareTypes = (Set<String>) session.getAttribute("hardwareGeneration");

        List<Role> sessionRoles = (List<Role>) session.getAttribute("roles");
        // Used to keep track of each roles Parameters on first pass all parameter will be the same but user may wish to change the parameter for a particular role.
        // This need to be capture and displayed in the BOM for the User.
        Map<Role, List<Parameter>> roleParameters = (HashMap<Role, List<Parameter>>) session.getAttribute("rolesParameter");

        if (sessionRoles.indexOf(role) != -1) {

            int index = sessionRoles.indexOf(role);
            role = sessionRoles.get(index);
            site = role.getSite();

        }

        List<Parameter> releaseParameter = setParameterValues(p, v, n, b, parameters, session);
        dumpParametersIntoJavaScriptEngine(releaseParameter, engine);

        if (roleParameters.containsKey(role)) {

            roleParameters.put(role, releaseParameter);

        }

        Formula f = role.getFormula();

        try {
            engine.eval(f.getFormula());
        } catch (NullPointerException np) {
            logger.info("Role: " + role.getName() + "[" + f.getName() + "] Script was empty");
            role.setExpectedResult("Role: " + role.getName() + "[" + f.getName() + "] Script was empty");
        } catch (ScriptException ex) {
            logger.info("Role: " + role.getName() + "[" + f.getName() + "] Script Exception occurred--\n" + ex.getMessage());
            role.setExpectedResult(ex.getMessage());
        }
        role.setExpectedResult(engine.get(role.getVariableName()));

        assignRoleHardwareConfiguration(role, p, v, n, b, hardwareTypes);

        enclosure = findEnclosureRole(sessionRoles, site);

        if (enclosure != null) {
            calculateEnclosure(sessionRoles, enclosure, releaseParameter, site);
            assignRoleHardwareConfiguration(enclosure, p, v, n, b, hardwareTypes);
        }

        dimensioningModel.put("r", role);
        dimensioningModel.put("site", role.getSite());
        dimensioningModel.put("rolesParameter", roleParameters);

        return dimensioningModel;
    }

    @Override
    public Map<String, Object> reCalculateAllRoles(Product product, Version version, Network network, Bundle bundle, Map<String, String> parameters, HttpSession session) {

        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        Map<String, Object> dimensioningModel = new HashMap<String, Object>();
        Product p = productDAO.getCombinedProduct(product);
        Network n = network;
        Version v = version;
        Bundle b = bundleDAO.getBundle(bundle.getID());
        String networkName = "";
        int count = 1;
        List<APPNumber> enclosureAPPList = null;
        Role enclosure = null;
        // Used to keep track of each roles Parameters on first pass all parameter will be the same but user may wish to change the parameter for a particular role.
        // This need to be capture and displayed in the BOM for the User.
        Map<Role, List<Parameter>> roleParameters = new HashMap<Role, List<Parameter>>();
        Integer netWeight = new Integer(0);

        // Get the Name of the Product and Bundle from the ID using the DAO. Used in the view to show the User a text string 
        // instead of a primary Key ID.
        Product productName = productDAO.getCombinedProduct(p);
        product.setWeighting(p.getWeighting());
        List<Network> net = networkDAO.getCombinedNetwork(n);
        //Network selectedNetwork = new Network();
        Iterator<Network> inetwork = net.iterator();
        while (inetwork.hasNext()) {
            Network network1 = inetwork.next();

            netWeight += network1.getNetworkWeight();
            if (count > 1) {
                networkName += "_";
            }
            count++;
            networkName += network1.getName();
        }

        //selectedNetwork.setNetworkWeight(netWeight);
        n.setName(networkName);
        Bundle bundleName = bundleDAO.getBundle(b.getID());
        // Set the List of Release Parameter
        List<Parameter> releaseParameter = setParameterValues(p, v, n, b, parameters, session);
        // Get a list of Default Link and the Links associated with the particular Release add them to the Model Object(Map)
        List<UrlLink> helpMenuLinks = urlLinkDAO.getProductReleaseURLLinks(p, v, n, b);
        List<UrlLink> defaultLinks = urlLinkDAO.getDefaultLinks();
        List<UrlLink> allLinks = new ArrayList<UrlLink>();
        allLinks.addAll(defaultLinks);
        allLinks.addAll(helpMenuLinks);
        Parameter pa = null;

        List<Role> allRoles = new ArrayList<Role>(1);
        List<Site> sites = roleDAO.findNumberOfSites(p, v, n, b);

        if (sites.size() > 1) {

            Parameter geoPar = parameterDAO.getParameter("GEO_RED");
            if (releaseParameter.indexOf(geoPar) != -1) {
                int index = releaseParameter.indexOf(geoPar);
                pa = releaseParameter.get(index);
                if (pa.getValue() != 1) {
                    int lastElement = sites.size();
                    sites.remove(lastElement - 1);
                }

            } else {
                int lastElement = sites.size();
                sites.remove(lastElement - 1);

            }

        }

        SystemDetails systemDetailObject = dimensionSystemDetailsObject(p, v, n, releaseParameter, engine);
        b = determineSystemBundle(p, v, n, b, systemDetailObject);
        systemDetailObject.setProduct(p);
        systemDetailObject.setNetwork(n);
        systemDetailObject.setBundle(b);
        systemDetailObject.setVersion(v);

        Iterator<Site> isites = sites.iterator();
        Note note = null;
        Set<String> hardwareTypes = new HashSet<String>();

        while (isites.hasNext()) {
            enclosureAPPList = new ArrayList<APPNumber>();
            Site site = isites.next();
            List<Role> definedRoles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
            Iterator<Role> iroles = definedRoles.iterator();
            while (iroles.hasNext()) {

                engine = factory.getEngineByName("JavaScript");
                dumpParametersIntoJavaScriptEngine(releaseParameter, engine);
                Role role = iroles.next();
                role.setSite(site);
                roleParameters.put(role, releaseParameter);
                try {
                    role.setNote(noteDAO.getProductRelaseNotePerRole(p, b, n, v, role));
                } catch (EmptyResultDataAccessException ex) {
                }
                Formula f = formulaDAO.getFormulaPerRole(p, v, n, b, role);
                role.setFormula(f);
                try {
                    engine.eval(f.getFormula());
                } catch (NullPointerException np) {
                    logger.info("Role: " + role.getName() + "[" + f.getName() + "] Script was empty");
                    //role.setExpectedResult("Role: "+role.getName()+"[" +f.getName()+ "] Script was empty");
                } catch (ScriptException ex) {
                    logger.info("Role: " + role.getName() + "[" + f.getName() + "] Script Exception occurred--\n" + ex.getMessage());
                    //role.setExpectedResult(ex.getMessage());
                }
                role.setExpectedResult(engine.get(role.getVariableName()));
                assignRoleHardwareConfiguration(role, p, v, n, b, hardwareTypes);

                allRoles.add(role);

            }
            enclosure = findEnclosureRole(allRoles, site);
            if (enclosure != null) {
                calculateEnclosure(allRoles, enclosure, releaseParameter, site);
                assignRoleHardwareConfiguration(enclosure, p, v, n, b, hardwareTypes);
            }

        }

        List<Note> notes = noteDAO.getVisibileProductReleaseNotes(p, b, n, v);

        dimensioningModel.put("notes", notes);

        dimensioningModel.put("parameters", releaseParameter);
        dimensioningModel.put("hardwareGeneration", hardwareTypes);
        dimensioningModel.put("roles", allRoles);
        dimensioningModel.put("sites", sites);
        dimensioningModel.put("default_links", allLinks);
        dimensioningModel.put("systemObjectDetails", systemDetailObject);
        dimensioningModel.put("rolesParameter", roleParameters);

        return dimensioningModel;
    }

    @Override
    public void updateHardwareAppQty(Map<String, String> parameters, HttpSession session) {

        String appName = parameters.get("HardwareApp");
        String qty = parameters.get("newQty");
        String roleText = "Role";
        String siteText = "Site";
        String hardwareText = "Hardware";
        String appText = "App";
        Role enclosure = null;
        SystemDetails systemDetailObject = (SystemDetails) session.getAttribute("systemObjectDetails");
        List<Parameter> releaseParameter = (List<Parameter>) session.getAttribute("parameters");
        Set<String> hardwareTypes = (Set<String>) session.getAttribute("hardwareGeneration");

        int roleIndex = appName.indexOf(roleText);
        int siteIndex = appName.indexOf(siteText);
        int hardwareIndex = appName.indexOf(hardwareText);
        int appIndex = appName.indexOf(appText);

        String roleID = appName.substring(roleIndex + roleText.length(), siteIndex);
        String siteID = appName.substring(siteIndex + siteText.length(), hardwareIndex);
        Site site = new Site(Integer.parseInt(siteID));
        Role r = new Role(Integer.parseInt(roleID));
        r.setSite(site);
        String hardwareID = appName.substring(hardwareIndex + hardwareText.length(), appIndex);
        HWBundle hwBundle = new HWBundle(Integer.parseInt(hardwareID));
        String appID = appName.substring(appIndex + appText.length(), appName.length());
        APPNumber appNumber = new APPNumber(Integer.parseInt(appID));
        List<Role> roles = (List<Role>) session.getAttribute("roles");
        if (roles.indexOf(r) != -1) {

            int index = roles.indexOf(r);
            Role role = roles.get(index);
            List<HWBundle> hw = role.getHardwareBundle();
            if (hw.indexOf(hwBundle) != -1) {

                int indexHW = hw.indexOf(hwBundle);
                HWBundle selectedHW = hw.get(indexHW);
                List<APPNumber> app = selectedHW.getAppList();
                if (app.indexOf(appNumber) != -1) {

                    int indexAPP = app.indexOf(appNumber);
                    APPNumber a = app.get(indexAPP);
                    a.setQty(Integer.parseInt(qty));
                }

            }

        }

        enclosure = findEnclosureRole(roles, site);
        if (enclosure != null) {
            calculateEnclosure(roles, enclosure, releaseParameter, site);
            assignRoleHardwareConfiguration(enclosure, systemDetailObject.getProduct(), systemDetailObject.getVersion(), systemDetailObject.getNetwork(), systemDetailObject.getBundle(), hardwareTypes);
        }

    }

    @Override
    public Map<String, Object> validateProductTestScript(Product p, Version v, Network n, Bundle b, Map<String, String> parameters, HttpServletRequest request, HttpSession session) {

        Map<String, Object> validationObject = new HashMap<String, Object>();
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");

        List<Parameter> releaseParameter = setParameterValues(p, v, n, b, parameters, session);

        dumpParametersIntoJavaScriptEngine(releaseParameter, engine);

        try {
            Formula f = formulaDAO.getProductReleaseTestScript(p, v, n, b);

            try {
                engine.eval(f.getFormula());
                Object validation = engine.get("validate");

                if (!(Boolean) validation) {
                    Object message = engine.get("message");
                    validationObject.put("message", message);

                }
                validationObject.put("validationPass", validation);

            } catch (ScriptException ex) {

                logger.info(ex.getMessage());
            }
        } catch (EmptyResultDataAccessException ex) {
            logger.info("No Validation Script found for " + p.getWeighting() + "  " + v.getName() + "  " + n.getNetworkWeight());
        } catch (NullPointerException np) {
            logger.info("Validation Script is Empty for " + p.getWeighting() + "  " + v.getName() + "  " + n.getNetworkWeight());
        }

        return validationObject;

    }

    @Override
    public Map<String, Object> validateProductTestScript(Product p, Version v, Network n, Bundle b, HttpServletRequest request, HttpSession session) {
        Map<String, Object> validationObject = new HashMap<String, Object>();
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");

        List<Parameter> releaseParameter = setParameterValues(p, v, n, b, request);

        dumpParametersIntoJavaScriptEngine(releaseParameter, engine);

        try {
            Formula f = formulaDAO.getProductReleaseTestScript(p, v, n, b);

            try {

                engine.eval(f.getFormula());
                Object validation = engine.get("validate");

                //logger.info("Validation Status" + validation);

                if (!(Boolean) validation) {
                    Object message = engine.get("message");
                    validationObject.put("message", message);

                }
                validationObject.put("validationPass", validation);

            } catch (ScriptException ex) {
                Object message = "Script Exception for " + ex.getMessage();
                validationObject.put("message", message);
                logger.info("Script Exception for " + ex.getMessage());
            }
        } catch (EmptyResultDataAccessException ex) {
            logger.info("No Validation Script found for " + p.getWeighting() + "  " + v.getName() + "  " + n.getNetworkWeight());
        } catch (NullPointerException np) {
            logger.info("Validation Script is Empty for " + p.getWeighting() + "  " + v.getName() + "  " + n.getNetworkWeight());
        }

        return validationObject;

    }

}
