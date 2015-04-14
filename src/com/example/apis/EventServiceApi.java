package com.example.apis;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import com.example.model.Event;

/**
 * Created by Rishav Jain on 09-04-2015.
 * Interface for Retrofit
 */
public interface EventServiceApi {

    String EVENT_SERVICE_PATH = "/event";
    String EVENT_CATEGORY_PATH = "/event/category";
    String EVENT_MEMBER_PATH = "/event/member";

    String EVENT_REPOSITORY_PATH = "/events";

    String EVENT_ID_QUERY_PARAMETER = "id";
    String EVENT_ID_SEARCH_PATH = EVENT_SERVICE_PATH + "/search/findById";

    String EVENT_MEMBER_QUERY_PARAMETER = "member";

    String OWNER_ID_QUERY_PARAMETER = "ownerId";
    String EVENT_OWNER_ID_SEARCH_PATH = EVENT_SERVICE_PATH + "/search/findByOwnerId";

    String CATEGORY_QUERY_PARAMETER = "category";
    String EVENT_CATEGORY_SEARCH_PATH = EVENT_SERVICE_PATH + "/search/findByCategory";

    @POST(EVENT_SERVICE_PATH)
    boolean addEvent(@Body Event u);

    @GET(EVENT_SERVICE_PATH)
    List<Event> getEvents();

    @GET(EVENT_CATEGORY_PATH)
    List<String> getEventCategories();

    @POST(EVENT_MEMBER_PATH)
    boolean addMember(@Query(EVENT_MEMBER_QUERY_PARAMETER) String memberId, @Query(EVENT_ID_QUERY_PARAMETER) String eventId);

    @DELETE(EVENT_MEMBER_PATH)
    boolean deleteMember(@Query(EVENT_MEMBER_QUERY_PARAMETER) String memberId, @Query(EVENT_ID_QUERY_PARAMETER) String eventId);

    @GET(EVENT_ID_SEARCH_PATH)
    Event findById(@Query(EVENT_ID_QUERY_PARAMETER) String id);

    @GET(EVENT_OWNER_ID_SEARCH_PATH)
    List<Event> findByOwnerId(@Query(OWNER_ID_QUERY_PARAMETER) String ownerId);

    @GET(EVENT_CATEGORY_SEARCH_PATH)
    List<Event> findByCategory(@Query(CATEGORY_QUERY_PARAMETER) String category);

}
