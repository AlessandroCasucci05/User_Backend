package com.project.example.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import com.project.example.entities.User;

public class CsvUtil {

    public List<User> convertToUsers(InputStream input) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            CSVParser csvParser = new CSVParser(reader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            List<User> users = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                User user = new User();
                user.setAddress(csvRecord.get("Address"));
                user.setEmail(csvRecord.get("Email"));
                user.setName(csvRecord.get("Name"));
                user.setUsername(csvRecord.get("Username"));
                users.add(user);
            }
            csvParser.close();
            return users;
        } catch (IOException e) {
            throw new Exception("Failed to parse the csv file");
        }
    }

    public boolean isThisCsvFormat(MultipartFile file){
        return "text/csv".equals(file.getContentType());
    }

}
