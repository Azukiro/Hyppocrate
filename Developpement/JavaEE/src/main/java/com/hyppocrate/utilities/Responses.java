package com.hyppocrate.utilities;

import com.hyppocrate.components.LangManager;

import javax.ws.rs.core.Response;
import java.util.HashMap;

public class Responses {

    private static final LangManager langManager = new LangManager();

    public static final Object GENERIC_NULL = new HashMap<String, Object>();

    // des méthodes de développeur vraiment très fainéant
    // ouais je suis un fainéant j'ai fait ça juste pour avoir à écrire moins de code
    // en plus ça fait du code en une seule ligne c'est plus lisible (lol ou pas)

    public static Response nullResponse() {
        return Response.ok(GENERIC_NULL).build();
    }


    public static Response errorResponse(String idErrorMessage) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("error", langManager.getString(idErrorMessage));
        return Response.ok(result).build();
    }

    public static Response objectOrError(Object object, String idErrorMessage) {
        if (object == null) return errorResponse(idErrorMessage);
        return Response.ok(object).build();
    }

    public static Response objectOrCustomNull(Object object) {
        if (object == null) return nullResponse();
        return Response.ok(object).build();
    }

    public static Response objectOrCustomNull(HashMap<String, Object> object) {
        if (object == null) return nullResponse();
        return Response.ok(object).build();
    }
}
