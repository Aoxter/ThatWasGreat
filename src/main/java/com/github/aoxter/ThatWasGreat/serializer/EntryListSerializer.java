package com.github.aoxter.ThatWasGreat.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.aoxter.ThatWasGreat.model.Entry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntryListSerializer extends StdSerializer<List<Entry>> {

    public EntryListSerializer() {
        this(null);
    }

    public EntryListSerializer(Class<List<Entry>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Entry> entries, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Long> ids = new ArrayList<>();
        for (Entry entry : entries) {
            ids.add(entry.getId());
        }
        jsonGenerator.writeObject(ids);
    }
}
