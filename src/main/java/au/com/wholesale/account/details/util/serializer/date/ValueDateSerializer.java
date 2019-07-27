package au.com.wholesale.account.details.util.serializer.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ValueDateSerializer extends JsonSerializer<LocalDate> {
    private final DateTimeFormatter formatter;

    public ValueDateSerializer() {formatter = DateTimeFormatter.ofPattern("MMM dd,yyyy");}

    @Override
    public void serialize(LocalDate value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeString(value.format(formatter));
    }
}
