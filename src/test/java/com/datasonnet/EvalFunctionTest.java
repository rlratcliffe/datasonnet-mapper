package com.datasonnet;

import com.datasonnet.document.Document;
import com.datasonnet.document.StringDocument;
import com.datasonnet.util.TestResourceReader;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvalFunctionTest {

    @Test
    void testEvalFunction() throws Exception {
        String expectedValue = "{\"evaluatedValue\":{\"test\":\"HelloWorld\"}}";

        String dsVar = "{ test : 'HelloWorld' }";
        String datasonnet = "{ evaluatedValue: DS.Lang.datasonnet(dsVar) }";

        Map<String, Document> variables = new HashMap<>();
        variables.put("dsVar", new StringDocument(dsVar, "text/plain"));

        Mapper mapper = new Mapper(datasonnet, variables.keySet(), true);
        String mapped = mapper.transform(new StringDocument("{}", "application/json"), variables, "application/json").getContentsAsString();

        JSONAssert.assertEquals(expectedValue, mapped, true);

        dsVar = "{ test : anotherVar }";
        variables.put("anotherVar", new StringDocument("HelloWorld", "text/plain"));

        mapper = new Mapper(datasonnet, variables.keySet(), true);
        mapped = mapper.transform(new StringDocument("{}", "application/json"), variables, "application/json").getContentsAsString();

        JSONAssert.assertEquals(expectedValue, mapped, true);
    }


}
