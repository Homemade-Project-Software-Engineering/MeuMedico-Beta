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

    //variables - name, password, email /users/user_id
    public static final String PUT_USER = "/users/";

    //variable - id_user
    public static final String DELETE_ID_USUARIO = "/users/";

    //variable - id_user, id_caregiver
    public static final String POST_RELATIONSHIP = "/relationships";

    //variable - id_user, id_caregiver
    public static final String DELETE_RELATIONSHIP = "/relationships_remove";

    //variables - id_user, name, checked, horario, description
    public static final String POST_ALARM_USERS = "/users/";
    public static final String POST_ALARM_ALARMS = "/alarms";

    //variables - id_user, name, checked, hour /users/user_id/alarms/id
    public static final String PUT_ALARM_USERS = "/users/";
    public static final String PUT_ALARM_ALARMS = "/alarms/";


    //variables - id_user, id_activity /users/user_id/alarms/id
    public static final String DELETE_ALARM_USERS = "/users/";
    public static final String DELETE_ALARM_ALARMS = "/alarms/";
}
