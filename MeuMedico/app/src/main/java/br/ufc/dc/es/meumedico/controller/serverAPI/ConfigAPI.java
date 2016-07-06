package br.ufc.dc.es.meumedico.controller.serverAPI;

public class ConfigAPI {

    public static final String BASE_URL = "https://apidoctor.herokuapp.com";

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";
    public static final String PUT = "PUT";

    //variable - id_user
    public static final String GET_ID_USUARIO = "/users/id_usuario.json";

    //variables - name, password, email
    public static final String POST_USER = "/users";

    //variable - id_user
    public static final String DELETE_ID_USUARIO = "/users/id_usuario";

    //variable - id_user, id_caregiver
    public static final String POST_RELATIONSHIP = "/relationships";

    //variable - id_user, id_caregiver
    public static final String DELETE_RELATIONSHIP = "/relationships_remove";

    //variables - id_user, name, checked, horario, description
    public static final String POST_ALARM = "/users/user_id/alarms";

    //variables - id_user, name, checked, hour
    public static final String PUT_ALARM = "/users/user_id/alarms/id";

    //variables - id_user, id_activity
    public static final String DELETE_ALARM = "/users/user_id/alarms/id";
}
