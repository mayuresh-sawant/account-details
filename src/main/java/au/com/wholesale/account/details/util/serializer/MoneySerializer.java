package au.com.wholesale.account.details.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class MoneySerializer extends JsonSerializer<BigDecimal> {
    private static final int SCALE = 4;
    private final NumberFormat myFormat;

    public MoneySerializer() {
        myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {

        value = value.setScale(SCALE, RoundingMode.DOWN);
        jsonGenerator.writeString(myFormat.format(value));
    }
}

