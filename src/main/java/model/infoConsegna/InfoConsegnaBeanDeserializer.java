package model.infoConsegna;

import com.google.gson.*;
import java.lang.reflect.Type;

public class InfoConsegnaBeanDeserializer implements JsonDeserializer<InfoConsegnaBean> {

    @Override
    public InfoConsegnaBean deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        InfoConsegnaBean infoConsegnaBean = new InfoConsegnaBean();

        if (jsonObject.has("id")) {
            infoConsegnaBean.setId(jsonObject.get("id").getAsLong());
        } else {
            infoConsegnaBean.setId(-1);
        }

        if (jsonObject.has("idUtente")) {
            infoConsegnaBean.setIdUtente(jsonObject.get("idUtente").getAsLong());
        } else {
            infoConsegnaBean.setIdUtente(-1);
        }

        infoConsegnaBean.setCitta(jsonObject.get("citta").getAsString());
        infoConsegnaBean.setCap(jsonObject.get("cap").getAsInt());
        infoConsegnaBean.setVia(jsonObject.get("via").getAsString());
        infoConsegnaBean.setAltro(jsonObject.get("altro").getAsString());
        infoConsegnaBean.setDestinatario(jsonObject.get("destinatario").getAsString());

        if (jsonObject.has("isDefault")) {
            infoConsegnaBean.setDefault(jsonObject.get("isDefault").getAsBoolean());
        } else {
            infoConsegnaBean.setDefault(false);
        }

        return infoConsegnaBean;
    }
}
