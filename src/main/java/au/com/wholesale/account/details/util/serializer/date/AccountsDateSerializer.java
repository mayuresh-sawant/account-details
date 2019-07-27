package au.com.wholesale.account.details.util.serializer.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AccountsDateSerializer extends JsonSerializer<LocalDate> {
    private final DateTimeFormatter formatter;

    public AccountsDateSerializer() {formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");}

    @Override
    public void serialize(LocalDate value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeString(value.format(formatter));
    }
}
