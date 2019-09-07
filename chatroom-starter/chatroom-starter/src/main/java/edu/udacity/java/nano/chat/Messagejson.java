package edu.udacity.java.nano.chat;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.StringWriter;

public class Messagejson {
    private JsonObject json;

    public  Messagejson(JsonObject json) {
        this.json = json;
    }

    public JsonObject getJson() {
        return json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }

    @Override
    public String toString(){
        StringWriter writer = new StringWriter();

        Json.createWriter(writer).write(json);

        return writer.toString();
    }
}
