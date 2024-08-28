/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.view;

import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.SystemDetails;
import com.ericsson.hdt.model.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 *
 * @author eadrcle
 */
public class ExcelReport extends AbstractExcelView{
    
    private static final short MAIN_HEADER_FONTSIZE = 14;
    private static final short SUB_HEADER_FONTSIZE = 12;
    private static final short DEFAULT_FONTSIZE = 10;
    private static final String DEFAULT_FONT_NAME = "Calibri";
    private static final String SHEET_TITLE = "HDT Report";
    private static final String MAIN_HEADER = "HDT Dimensioning Report";
    private static final String SUB_HEADER_1 = "Overview";
    private static final String SUB_HEADER_2 = "Dimensioning Results";
    private static final String CUSTOMER_NAME = "Customer Name";
    private static final String CONF_NAME = "Configuration Name";
    private static final String SYSTEM = "System";
    private static final String NETWORK = "Network";
    private static final String HDT_CUSTOM_CONFIG = "Custom Configuration";
    private static final String HDT_USER = "HDT User";
    private static final String HDT_APP_NUM = "APP";
    private static final String HDT_QTY = "Qty";
    private static final String HDT_USER_QTY = "User Qty";
    private static final String HDT_DESCRIPTION = "Description";
    private static final String HDT_EOL = "EOL";
    private static final String HDT_LOD = "LOD";    
    private static final String HDT_IMPORTANT_NOTES = "Important Notes";
    private static final String HDT_NOTE = "NOTE: ";
    private static final String HDT_WARN = "WARNING: ";
    private static final String HDT_PARAMETER_DEFAULT = "Default";
    private static final String HDT_PARAMETER_CURRENT = "Current";    
    private static final String HDT_CHANGED_PARAMETERS = "Manually overridden Dimensioning Parameters for ";
    private static final String HDT_CHANGED_APP_QTY = "Manually overridden APP Quantities for ";
    private static final String HDT_CHANGED_APP_QTY_WARN = "Please refer to notes on changing APP Number Quantities!";
    private static final String HDT_SHOW_HIDE_GROUP = "Click '+' to expand group, '-' to hide.";
    private static final String HDT_VER = "HDT Software Version";
    private static final String HDT_BUILD = "Build: ";
    private static final String HDT_DB_VER = "HDT Database Version";
    private static final String DATE_STR = "Date, Time";
    private static final String BUNDLE = "Bundle";
    private static final String VERSION = "Release";
    private int colNum = 0;
    private int maxColNum = 0;
    
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        
        
         Row row;
        int rowNumber;
        List<Site> sites = (List<Site>) model.get("sites");
        Iterator<Site> isite = sites.iterator();
        Boolean boldFontYes = true;
        Boolean boldFontNo = false;
        
        
        Map<Role,List<Parameter>> roleParameters = (HashMap<Role,List<Parameter>>)model.get("rolesParameter");
        logger.info("Size of Parameter List for All Roles : " + roleParameters.size());
        
        
        buildIntroductionSheet(model,workbook);
        
        while(isite.hasNext()){
            Site site = isite.next();
            
            HSSFSheet sheet = workbook.createSheet("Site " + site.getId());
            
            DataFormat format = workbook.createDataFormat();
            short numberFmt = format.getFormat("##");
            

        //
        // Create some fonts.
        //
        Font mainHeaderFont = setupFontStyle(workbook,DEFAULT_FONT_NAME,MAIN_HEADER_FONTSIZE,boldFontYes);
        Font subHeaderFont = setupFontStyle(workbook,DEFAULT_FONT_NAME,SUB_HEADER_FONTSIZE,boldFontYes);
        Font boldFont =  setupFontStyle(workbook,DEFAULT_FONT_NAME,DEFAULT_FONTSIZE,boldFontYes);
        Font normalFont = setupFontStyle(workbook,DEFAULT_FONT_NAME,DEFAULT_FONTSIZE,boldFontNo);
        Font redNormalFont = setupFontStyle(workbook,DEFAULT_FONT_NAME,DEFAULT_FONTSIZE,IndexedColors.RED.getIndex(),boldFontNo);
        Font redBoldFont = setupFontStyle(workbook,DEFAULT_FONT_NAME,DEFAULT_FONTSIZE,IndexedColors.RED.getIndex(),boldFontYes);
        
         //
        // Create some cell styles.
        //
       
       
        
        CellStyle mainHeaderCellStyle = setupCellStyle(workbook,mainHeaderFont);    
        CellStyle subHeaderCellSyle = setupCellStyle(workbook,subHeaderFont);      
        CellStyle boldCellSyle = setupCellStyle(workbook,boldFont);          
        CellStyle normalCellSyle = setupCellStyle(workbook,normalFont);
        CellStyle redNormalCellSyle = setupCellStyle(workbook,redNormalFont);     
        CellStyle redBoldCellSyle = setupCellStyle(workbook,redBoldFont);
        CellStyle redNormalNumberCellSyle = setupCellStyle(workbook,numberFmt,redNormalFont);
        CellStyle detailsTableCellStyle = setupCellStyle(workbook,IndexedColors.LIGHT_GREEN.getIndex(),CellStyle.SOLID_FOREGROUND,normalFont);
        CellStyle detailsTableHeaderStyle = setupCellStyle(workbook,IndexedColors.LIGHT_GREEN.getIndex(),CellStyle.SOLID_FOREGROUND,boldFont);
        CellStyle highlightedCellSyle = setupCellStyle(workbook,IndexedColors.LIGHT_YELLOW.getIndex(),CellStyle.SOLID_FOREGROUND,normalFont);;
        CellStyle normalNumberCellSyle =  setupCellStyle(workbook,numberFmt,normalFont);
       
       
        

        // 
        // Row creation starts here.
        //

        // Create row.
       
        rowNumber=0;

        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), subHeaderCellSyle, SUB_HEADER_2);
        row = sheet.createRow(rowNumber++);
        resetColNum();
        
        // Retrieve all the roles from the model.

        List<Role> roleList = (List<Role>) model.get("selectedBOMRoles");
        Iterator<Role> iroles = roleList.iterator();
      while(iroles.hasNext()){
          Role r = iroles.next();
          if(r.getSite().equals(site)){
              
              
              resetColNum();
              
              row = sheet.createRow(rowNumber++);
          
          createCell(row, increaseColNum(), boldCellSyle, r.getDescription());
          row = sheet.createRow(rowNumber++);
          resetColNum();
          row = sheet.createRow(rowNumber++);
          resetColNum();
          
          List<HWBundle> hw = r.getHardwareBundle();
          Iterator<HWBundle> ihw = hw.iterator();
          
                  while(ihw.hasNext()){
                      HWBundle hwBundle = ihw.next();
                      
                      if(hwBundle.getSelected()){
                          List<APPNumber> apps = hwBundle.getAppList();
                          Iterator<APPNumber> iapp = apps.iterator();
                          createCell(row, increaseColNum(), boldCellSyle, HDT_APP_NUM);
                          createCell(row, increaseColNum(), boldCellSyle, HDT_DESCRIPTION);
                          createCell(row, increaseColNum(), boldCellSyle, HDT_QTY);
                          createCell(row, increaseColNum(), boldCellSyle, HDT_EOL);
                          createCell(row, increaseColNum(), boldCellSyle, HDT_LOD);
                          resetColNum();
                          while(iapp.hasNext()){
                              
                              APPNumber app  = iapp.next();
                                row = sheet.createRow(rowNumber++);
                                createCell(row, increaseColNum(), normalCellSyle, app.getName());
                                createCell(row, increaseColNum(), normalCellSyle, app.getDescription());
                                createCell(row, increaseColNum(), normalNumberCellSyle,app.getQty().toString());
                                createCell(row, increaseColNum(), normalCellSyle, "##-##-##");
                                createCell(row, increaseColNum(), normalCellSyle, "##-##-##");
                                
                                 resetColNum();
                          }
                          
                        
                          
                      }
                  }
              
               row = sheet.createRow(rowNumber++);
               resetColNum();
               CreationHelper ch = workbook.getCreationHelper();
                String tmp = "Dimensioning Parameters  for " + r.getDescription();
                RichTextString rts = ch.createRichTextString(tmp);
                rts.applyFont(1, tmp.length(), boldFont);
                row = sheet.createRow(rowNumber++);
                createCell(row, increaseColNum(), normalCellSyle, rts);
                int rowStart = rowNumber;
                row = sheet.createRow(rowNumber++);
                resetColNum();
               
                   row = sheet.createRow(rowNumber++);
                   createCell(row, increaseColNum(), boldCellSyle, "Name");
                   createCell(row, increaseColNum(), boldCellSyle, "Description");
                    createCell(row, increaseColNum(), boldCellSyle, "Value");
                    resetColNum();
               if(roleParameters.containsKey(r)){
                   
                
                   
                   List<Parameter> roleParameter = roleParameters.get(r);
                   Iterator<Parameter> iparameter = roleParameter.iterator();
                   row = sheet.createRow(rowNumber++);
                
                while(iparameter.hasNext()){
                    Parameter parameter = iparameter.next();
                    createCell(row, increaseColNum(), normalCellSyle, parameter.getName());
                    createCell(row, increaseColNum(), normalCellSyle, parameter.getDesc());
                    if(parameter.getValue()!=0){
                        createCell(row, increaseColNum(), normalNumberCellSyle, parameter.getValue());
                    }else{
                        createCell(row, increaseColNum(), normalNumberCellSyle, "0");
                    }
                    
                    resetColNum();
                    row = sheet.createRow(rowNumber++);
                    
                    
                }
               }
               
               int rowEnd = rowNumber;
               sheet.groupRow(rowStart, rowEnd);
               sheet.setRowGroupCollapsed(rowStart, true);
               row = sheet.createRow(rowNumber++);
               
                
                resetColNum();
               
       
              
          }
          
          
      }
      
      
        row = sheet.createRow(rowNumber++);
        row = sheet.createRow(rowNumber++);
        

        
        // Must be last call to take all widths into account.
        adjustWidths(sheet);  
            
        }
  
    }

    
    
   
    
    private  void buildIntroductionSheet(Map<String,Object> model,HSSFWorkbook workbook){
        Row row;
        int rowNumber;
        HSSFSheet sheet = workbook.createSheet("Information");
        DataFormat format = workbook.createDataFormat();
        short numberFmt = format.getFormat("#");
        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String todayDate = dateFormat.format(dateNow);
        
        
        
        //
        // Create some fonts.
        //
        Font mainHeaderFont = setupFontStyle(workbook,DEFAULT_FONT_NAME,MAIN_HEADER_FONTSIZE,true);
        Font subHeaderFont = setupFontStyle(workbook,DEFAULT_FONT_NAME,SUB_HEADER_FONTSIZE,true);
        Font boldFont = setupFontStyle(workbook,DEFAULT_FONT_NAME,DEFAULT_FONTSIZE,true); 
        Font normalFont = setupFontStyle(workbook,DEFAULT_FONT_NAME,DEFAULT_FONTSIZE,false); 
        Font redNormalFont = setupFontStyle(workbook,DEFAULT_FONT_NAME,DEFAULT_FONTSIZE,IndexedColors.RED.getIndex(),false); 
        Font redBoldFont = setupFontStyle(workbook,DEFAULT_FONT_NAME,DEFAULT_FONTSIZE,IndexedColors.RED.getIndex(),true); 
       
        // Create some cell styles.
        //
        CellStyle mainHeaderCellStyle = setupCellStyle(workbook,mainHeaderFont);    
        CellStyle subHeaderCellSyle = setupCellStyle(workbook,subHeaderFont);      
        CellStyle boldCellSyle = setupCellStyle(workbook,boldFont);          
        CellStyle normalCellSyle = setupCellStyle(workbook,normalFont);
        CellStyle redNormalCellSyle = setupCellStyle(workbook,redNormalFont);     
        CellStyle redBoldCellSyle = setupCellStyle(workbook,redBoldFont);
        CellStyle redNormalNumberCellSyle = setupCellStyle(workbook,numberFmt,redNormalFont);
        CellStyle detailsTableCellStyle = setupCellStyle(workbook,IndexedColors.LIGHT_GREEN.getIndex(),CellStyle.SOLID_FOREGROUND,normalFont);
        CellStyle detailsTableHeaderStyle = setupCellStyle(workbook,IndexedColors.LIGHT_GREEN.getIndex(),CellStyle.SOLID_FOREGROUND,boldFont);
        
        // 
        // Row creation starts here.
        //

        // Create row.
        rowNumber = 0;
        resetColNum();
        row = sheet.createRow(rowNumber++);
        //setupCellStyle  mainHeaderCellStyle
        createCell(row, colNum, mainHeaderCellStyle , MAIN_HEADER);
        // Create row.
        row = sheet.createRow(rowNumber++);
        createCell(row, colNum, subHeaderCellSyle, SUB_HEADER_1);
        // Empty row.
        row = sheet.createRow(rowNumber++);
        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, CUSTOMER_NAME);
        if(model.get("user")!=null){
            User user = (User)model.get("user");
            createCell(row, increaseColNum(), normalCellSyle, user.getEmail() );
        }else{
            
            createCell(row, increaseColNum(), normalCellSyle, "Demo");
            
        }
        
        SystemDetails systemDetailObject = (SystemDetails) model.get("systemObjectDetails");
        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, SYSTEM);
        createCell(row, increaseColNum(), normalCellSyle, systemDetailObject.getProduct().getName());

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, NETWORK);
        createCell(row, increaseColNum(), normalCellSyle, systemDetailObject.getNetwork().getName());
        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, VERSION);
        createCell(row, increaseColNum(), normalCellSyle, systemDetailObject.getVersion().getDesc());

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, BUNDLE);
        createCell(row, increaseColNum(), normalCellSyle, systemDetailObject.getBundle().getName());

        

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, HDT_DB_VER);
        createCell(row, increaseColNum(), normalCellSyle, (String) model.get("hdt_db_ver"));

        // Create row.
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, DATE_STR);
        createCell(row, increaseColNum(), normalCellSyle, todayDate);
        resetColNum();
        
        row = sheet.createRow(rowNumber++);
        resetColNum();
        row = sheet.createRow(rowNumber++);
        
        


        // Render the Notes.
        List<Note> noteList = (List<Note>) model.get("notes");
        if(noteList!=null){
            
            
           createCell(row, increaseColNum(), redBoldCellSyle, "Release Note");
           
            for (Note note : noteList) {
            if (note.isVisible()) {
                // Create row.
                resetColNum();
                row = sheet.createRow(rowNumber++);
                createCell(row, increaseColNum(), boldCellSyle, note.getNote());
                
                
            }
        }  
            
        }
        
        
        resetColNum();
        row = sheet.createRow(rowNumber++);
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), redBoldCellSyle, HDT_IMPORTANT_NOTES);
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, "Localised Power Supply Units have to be chosen separately in the ECP tool.");
        resetColNum();
        row = sheet.createRow(rowNumber++);
        
        createCell(row, increaseColNum(), boldCellSyle, "Data is subject to change up to respective GA dates.");
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, "If you wish to purchase the HW preconfigured you need to fit on 1 Enclosure. To do this set EBAS and OCS APP quantities to zero.");
        
        resetColNum();
        row = sheet.createRow(rowNumber++);
        createCell(row, increaseColNum(), boldCellSyle, "Altering quantities may lead to unsupported configurations and any alterations must be reviewed / approved before ordering");

        adjustWidths(sheet);  
        
    }
   
    
    private void resetColNum() {
        colNum = 0;
    }

    private int increaseColNum() {
        int retVal = colNum;

        colNum++;
        if (colNum > maxColNum) {
            maxColNum = colNum;
        }

        // Return the old value before increasing.
        return retVal;
    }

    // Adjust optimal column width.
    private void adjustWidths(Sheet sheet) {
        for (int col = 0; col < maxColNum; col++) {
            sheet.autoSizeColumn(col);
        }
    }

    // Create a cell and update the maximum column width for later adjustment.
    private void createCell(Row row, int index, CellStyle style, String value) {
        Cell cell = row.createCell(index);

        if (style != null) {
            cell.setCellStyle(style);
        }
        cell.setCellValue(value);

        
    }

    private void createCell(Row row, int index, CellStyle style, Integer value) {
        Cell cell = row.createCell(index);
        if (style != null) {
            cell.setCellStyle(style);
        }
       
        cell.setCellValue(value);

        
    }
    
    
    private void createCell(Row row, int index, CellStyle style, Double value) {
        Cell cell = row.createCell(index);

        if (style != null) {
            cell.setCellStyle(style);
        }
        cell.setCellValue(value);

        
    }

    private void createCell(Row row, int index, CellStyle style, RichTextString value) {
        Cell cell = row.createCell(index);

        if (style != null) {
            cell.setCellStyle(style);
        }
        cell.setCellValue(value);

        
    }
    
    
    private CellStyle setupCellStyle(HSSFWorkbook workbook,Font font){
         CellStyle cellStyle = workbook.createCellStyle();   
         cellStyle.setFont(font);
         cellStyle.setAlignment(CellStyle.ALIGN_GENERAL);
        
        
        return cellStyle;
    }
    
     
    private CellStyle setupCellStyle(HSSFWorkbook workbook,short numberFmt,Font font){
         CellStyle cellStyle = workbook.createCellStyle();   
         cellStyle.setFont(font);
         cellStyle.setAlignment(CellStyle.ALIGN_GENERAL);
         cellStyle.setDataFormat(numberFmt);
         
         
        
        return cellStyle;
    }
    private CellStyle setupCellStyle(HSSFWorkbook workbook,short foreGroundColor,short fillColor,Font font){
         CellStyle cellStyle = workbook.createCellStyle();   
         cellStyle.setFont(font);
         cellStyle.setAlignment(CellStyle.ALIGN_GENERAL);
         cellStyle.setFillForegroundColor(foreGroundColor);
         cellStyle.setFillPattern(fillColor);
        
        return cellStyle;
    }
    
    
    private Font  setupFontStyle(HSSFWorkbook workbook,String fontName,short fontSize,Boolean boldWeight){
        Font fontStyle = workbook.createFont();
        fontStyle.setFontName(fontName);
        fontStyle.setFontHeightInPoints(fontSize);
        if(boldWeight){
             fontStyle.setBoldweight(Font.BOLDWEIGHT_BOLD);
        }
        
        return fontStyle;
    }
    
    private Font  setupFontStyle(HSSFWorkbook workbook,String fontName,short fontSize,short fontColor,Boolean boldWeight){
        Font fontStyle = workbook.createFont();
        fontStyle.setFontName(fontName);
        fontStyle.setFontHeightInPoints(fontSize);
        if(boldWeight){
             fontStyle.setBoldweight(Font.BOLDWEIGHT_BOLD);
        }
        
        fontStyle.setColor(fontColor);
        
        return fontStyle;
    }

    
    
}
