package com.bwidlarz.todolist.JSON;

import com.bwidlarz.todolist.Database.Task;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Dell on 2016-06-09.
 */
public class JSON implements JsonSerializer<Task>{


    @Override
    public JsonElement serialize(final  Task srcTask,final  Type typeOfSrc,final  JsonSerializationContext context) {

        final JsonObject jsonObject = new JsonObject();
/*
     //   JsonPrimitive titleObject = new JsonPrimitive(srcTask.getTitle());
        JsonObject titleObject = new JsonObject();
        JsonObject descriptionObject = new JsonObject();
        JsonObject urlObject = new JsonObject();

        titleObject.addProperty("TITLE", srcTask.getTitle());
        descriptionObject.addProperty("DESCRIPTION", srcTask.getDescription());
        urlObject.addProperty("URL", srcTask.getImageUrl());
*/      jsonObject.addProperty("ID", srcTask.getProviderId());
        jsonObject.addProperty("TITLE", srcTask.getTitle());
        jsonObject.addProperty("DESCRIPTION", srcTask.getDescription());
        jsonObject.addProperty("CREATED", srcTask.getCreated());
        jsonObject.addProperty("TIME_END", srcTask.getEndTime());
        jsonObject.addProperty("URL", srcTask.getImageUrl());

        return jsonObject;
    }
}
