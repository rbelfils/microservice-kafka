package com.rbelfils.microservice.json;


import com.ewolff.microservice.shipping.Shipment;
import com.ewolff.microservice.shipping.events.ShipmentDeserializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonParseTest {

    @Test
    public void testParser() throws IOException {

        String fileName = "order-exemple.json";
        ObjectMapper objectMapper = new ObjectMapper();
        //Get file from resources
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        String jsonString = readLineByLineJava8(file.getAbsolutePath());


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode actualObj = mapper.readTree(jsonString);
        final Shipment shipment = mapper.treeToValue(actualObj, Shipment.class);

        System.out.println(shipment);

    }

    //Example 1

//Read file content into string with - Files.lines(Path path, Charset cs)

    private static String readLineByLineJava8(String filePath)
    {

        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

//Example 2

//Read file content into string with - Files.readAllBytes(Path path)

    private static String readAllBytesJava7(String filePath)
    {
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }

//Example 3

//Read file content into string with - using BufferedReader and FileReader
//You can use this if you are still not using Java 8

    private static String usingBufferedReader(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
