package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import java.io.*;
import java.util.*;

public class Converter {
    
    /*
        
        Consider the following CSV data, a portion of a database of episodes of
        the classic "Star Trek" television series:
        
        "ProdNum","Title","Season","Episode","Stardate","OriginalAirdate","RemasteredAirdate"
        "6149-02","Where No Man Has Gone Before","1","01","1312.4 - 1313.8","9/22/1966","1/20/2007"
        "6149-03","The Corbomite Maneuver","1","02","1512.2 - 1514.1","11/10/1966","12/9/2006"
        
        (For brevity, only the header row plus the first two episodes are shown
        in this sample.)
    
        The corresponding JSON data would be similar to the following; tabs and
        other whitespace have been added for clarity.  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data:
        
        {
            "ProdNums": [
                "6149-02",
                "6149-03"
            ],
            "ColHeadings": [
                "ProdNum",
                "Title",
                "Season",
                "Episode",
                "Stardate",
                "OriginalAirdate",
                "RemasteredAirdate"
            ],
            "Data": [
                [
                    "Where No Man Has Gone Before",
                    1,
                    1,
                    "1312.4 - 1313.8",
                    "9/22/1966",
                    "1/20/2007"
                ],
                [
                    "The Corbomite Maneuver",
                    1,
                    2,
                    "1512.2 - 1514.1",
                    "11/10/1966",
                    "12/9/2006"
                ]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String result = "{}"; // default return value; replace later!
        
        try {
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> rows = reader.readAll();
            reader.close();
            
            if (rows == null || rows.size() == 0) return result;
            
            String[] header = rows.get(0);
            
            JsonArray ColHeadings = new JsonArray();
            for (String h: header){
                ColHeadings.add(h);
            } 
            JsonArray prodNums = new JsonArray();
            JsonArray data = new JsonArray();
            
            for (int i = 1; i < rows.size(); i++){
                String[]row = rows.get(i);
                if (row == null || row.length == 0);
                prodNums.add(row[0]);
                
                JsonArray rowArr = new JsonArray();
                for( int j =1 ; j <header.length; j++){
                    String value = (j < row.length) ? row[j] : "";
                    if( value != null){
                        String trimmed = value.trim();
                        try{
                            int n = Integer.parseInt(trimmed);
                            rowArr.add(Integer.valueOf(n));
                        }
                        catch(NumberFormatException e){
                            rowArr.add(value);
                        }
                    }
                    else{
                        rowArr.add("");
                    }
                }
                data.add(rowArr);
            }
            JsonObject out = new JsonObject();
            out.put("ProdNums", prodNums);
            out.put("ColHeadings", ColHeadings);
            out.put("Data", data);
            
            result = out.toJson();
// INSERT YOUR CODE HERE
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
        
        String result = ""; // default return value; replace later!
        
        try {
            Object parsed = JsonValue
            if (!(parsed instanceof JsonObject)) return result;
            JsonObject obj = (JsonObject) parsed;

            JsonArray colHeadings = (JsonArray) obj.get("ColHeadings");
            JsonArray prodNums = (JsonArray) obj.get("ProdNums");
            JsonArray data = (JsonArray) obj.get("Data");

            StringWriter sw = new StringWriter();
            CSVWriter writer = new CSVWriter(sw);

            // Write header row (ColHeadings)
            String[] header = new String[colHeadings.size()];
            for (int i = 0; i < colHeadings.size(); i++) {
                Object h = colHeadings.get(i);
                header[i] = (h == null) ? "" : h.toString();
            }
            writer.writeNext(header);

            // For each entry in Data, prepend ProdNum and write CSV row
            for (int i = 0; i < data.size(); i++) {
                JsonArray rowArr = (JsonArray) data.get(i);
                String[] csvRow = new String[colHeadings.size()];

                
                Object pn = (i < prodNums.size()) ? prodNums.get(i) : "";
                csvRow[0] = (pn == null) ? "" : pn.toString();

              
                for (int j = 1; j < colHeadings.size(); j++) {
                    Object val = (j - 1 < rowArr.size()) ? rowArr.get(j - 1) : "";
                    csvRow[j] = (val == null) ? "" : val.toString();
                }

                writer.writeNext(csvRow);
            }

            writer.close();
            result = sw.toString();
            // INSERT YOUR CODE HERE
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
}
