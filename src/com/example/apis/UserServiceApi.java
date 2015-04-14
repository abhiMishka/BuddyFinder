package com.example.apis;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import com.example.model.User;

/**
 * Created by Rishav Jain on 02-04-2015.
 * Interface for Retrofit
 */
public interface UserServiceApi {

    String USER_SERVICE_PATH = "/user";

    String USER_ID_QUERY_PARAMETER = "id";
    String USER_ID_SEARCH_PATH = USER_SERVICE_PATH + "/search/findById";

    String USER_MEMBER_EVENT_QUERY_PARAMETER = "event";
    String USER_MEMBER_EVENTS_PATH = "/user/memberOfEvents";

    @POST(USER_SERVICE_PATH)
    boolean addUser(@Body User u);

    @GET(USER_SERVICE_PATH)
    List<User> getUsers();

    @POST(USER_MEMBER_EVENTS_PATH)
    boolean addEvent(@Query(USER_MEMBER_EVENT_QUERY_PARAMETER) String eventId, @Query(USER_ID_QUERY_PARAMETER) String userId);

    @DELETE(USER_MEMBER_EVENTS_PATH)
    boolean deleteEvent(@Query(USER_MEMBER_EVENT_QUERY_PARAMETER) String eventId, @Query(USER_ID_QUERY_PARAMETER) String userId);

    @GET(USER_ID_SEARCH_PATH)
    User findById(@Query(USER_ID_QUERY_PARAMETER) String id);
}
