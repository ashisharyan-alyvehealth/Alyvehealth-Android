package com.health.immunity.retrofit;

import com.google.gson.JsonObject;
import com.health.immunity.HomeContainer.model.FitResponse;
import com.health.immunity.HomeContainer.model.JsonObjectResponse;
import com.health.immunity.HomeContainer.model.PEDORESPONSE;
import com.health.immunity.HomeContainer.model.TokenResponse;
import com.health.immunity.HomeContainer.model.updatrPedoIdResponse;
import com.health.immunity.act.model.SourceResponse;
import com.health.immunity.community.model.CommunityResponse;
import com.health.immunity.community.model.CommunityUserListResponse;
import com.health.immunity.community.model.DashboardResponse;
import com.health.immunity.community.model.InsightResponse;
import com.health.immunity.community.model.InvitResponse;
import com.health.immunity.community.model.InviteMemberResponse;
import com.health.immunity.community.model.LeaveCommunityResponse;
import com.health.immunity.community.model.NoticeResponse;
import com.health.immunity.community.model.ZoneResponse;
import com.health.immunity.insight.model.GetKpiCall;
import com.health.immunity.insight.model.GraphResponse;
import com.health.immunity.insight.model.MyCommunitiesResponse;
import com.health.immunity.login.model.GetTersmApi;
import com.health.immunity.login.model.LoginMobile;
import com.health.immunity.login.model.MobileOTPModel;
import com.health.immunity.login.model.OnBoardResponse;
import com.health.immunity.profile.model.GetProfileResponse;
import com.health.immunity.profile.model.ProfileResponse;
import com.health.immunity.profile.model.WorkEmailResponse;
import com.health.immunity.profile.model.updateZohoIdResponse;
//import com.health.immunity.blue.bluetooth.Model;
//import com.health.immunity.model.AQIResponse;
//import com.health.immunity.model.CommunityResponse;
//import com.health.immunity.model.CommunityUserListResponse;
//import com.health.immunity.model.DeviceResponse;
//import com.health.immunity.model.DiscoverModel;
//import com.health.immunity.model.FitResponse;
//import com.health.immunity.model.ForcefullyResponse;
//import com.health.immunity.model.GetKeyValueResponse;
//import com.health.immunity.model.GetKpiCall;
//import com.health.immunity.model.GetProfileResponse;
//import com.health.immunity.model.GetdeviceResposnse;
//import com.health.immunity.model.GraphResponse;
//import com.health.immunity.model.InvitResponse;
//import com.health.immunity.model.InviteMemberResponse;
//import com.health.immunity.model.JsonObjectRESPONSE;
//import com.health.immunity.model.JsonObjectResonse;
//import com.health.immunity.model.LeaveCommunityResponse;
//import com.health.immunity.model.MyCommunitiesResponse;
//import com.health.immunity.model.OrderSelfResponse;
//import com.health.immunity.model.PEDORESPONSE;
//import com.health.immunity.model.PendingInvitationsResponse;
//import com.health.immunity.model.ProximityResposnse;
//import com.health.immunity.model.SourceResponse;
//import com.health.immunity.model.TempResponse;
//import com.health.immunity.model.TokenRespose;
//import com.health.immunity.model.WorkEmailResponse;
//import com.health.immunity.model.ZoneResponse;
//import com.health.immunity.model.dashboard.DashboardResponse;
//import com.health.immunity.model.hot.hotResponse;
//import com.health.immunity.model.insight.InsightResponse;
//import com.health.immunity.model.login.LoginMobile;
//import com.health.immunity.model.login.MobileOTPModel;
//import com.health.immunity.model.notice.NoticeResponse;
//import com.health.immunity.model.onboard.OnBoardResponse;
//import com.health.immunity.model.profile.ProfileResponse;
//import com.health.immunity.model.terma_and_condition.GetTersmApi;
//import com.health.immunity.model.updateContentStatusResponse;
//import com.health.immunity.model.updateZohoIdResponse;
//import com.health.immunity.model.updatrPedoIdResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {


    @POST("login-mobile")
    @FormUrlEncoded
    Call<LoginMobile> loginCall(@Field("mobile") String mobile);

    @POST("login-mobile-verify")
    @FormUrlEncoded
    Call<MobileOTPModel> mobileOtpCall(@Field("mobile") String mobile,
                                       @Field("otp") String otp);
//
//    @POST("login-email")
//    @FormUrlEncoded
//    Call<LoginMobile> emailCall(@Field("email") String email);
//
//
//
//    @POST("login-email-verify")
//    @FormUrlEncoded
//    Call<MobileOTPModel> emailOtpCall(@Field("email") String mobile,
//                                      @Field("otp") String otp);
//
//    @POST("access-code")
//    @FormUrlEncoded
//    Call<MobileOTPModel> accessCodeCall(@Field("access_code") String access_code);
//
    @POST("user-info")
    Call<OnBoardResponse> onBoardCall(@Body JsonObject jsonObject);

    @POST("reOnboarding")
    Call<OnBoardResponse> hrOnBoardCall(@Header("Authorization") String token,
                                        @Body JsonObject jsonObject);
    @POST("saveUserKpi")
    @FormUrlEncoded
    Call<JsonObjectResponse> fitCall(@Header("Authorization") String token,
                                     @Field("data") String data);
//
//
//
    @POST("savePedometerUserKpi")
    @FormUrlEncoded
    Call<PEDORESPONSE> pedoCall(@Header("Authorization") String token,
                                @Field("data") String data);

    @POST("getHealthKpis")
    @FormUrlEncoded
    Call<GetKpiCall> GetKpiCall(@Header("Authorization") String token,
                                @Field("flag") String flag,
                                @Field("user") String user,
                                @Field("age_month") String age,
                                @Field("gender") String gender,
                                @Field("community") String community);
//
    @GET("check-in-status")
    Call<OnBoardResponse> checkInStatusCall(@Header("Authorization") String token);
//
//
    @POST("firebase/set_token")
    @FormUrlEncoded
    Call<TokenResponse> setToken(@Header("Authorization") String token,
                                 @Field("device_token") String devicetoken,
                                 @Field("device") String device);
//
//
//    @POST("firebase/send_notification")
//    @FormUrlEncoded
//    Call<TokenRespose> sendnotificaion(@Header("Authorization") String token,
//                                @Field("body") String devicetoken,
//                                @Field("title") String device);
//
//
//
    @POST("terms-condition")
    @FormUrlEncoded
    Call<GetTersmApi> getTermsCall(@Field("type") String type);
//
//
    @POST("terms-condition-save")
    @FormUrlEncoded
    Call<GetTersmApi> postTermsCall(@Field("mobile_number") String mobile_number,
                                    @Field("email") String email,
                                    @Field("device_id") String device_id,
                                    @Field("content_id") String content_id,
                                    @Field("id") String id,
                                    @Field("type") String type);
//
//    @POST("user-check-in")
//    Call<OnBoardResponse> checkInCall(@Header("Authorization") String token,
//                                      @Body JsonObject jsonObject);
//
    @POST("user-dashboard")
    Call<DashboardResponse> dashboardCall(@Header("Authorization") String token);

    @GET("organisation-guidance")
    Call<NoticeResponse> noticeCall(@Header("Authorization") String token);

    @GET("get-profile")
    Call<GetProfileResponse> getProfileCall(@Header("Authorization") String token);
//
//    @POST("notificationTest")
//    Call notificationTest();
//
//
    @GET("insights")
    Call<InsightResponse> insightCall(@Header("Authorization") String token);
//
    @Multipart
    @POST("update-profile")
    Call<ProfileResponse> profileCall(@Header("Authorization") String token,
                                      @Part("name") RequestBody name,
                                      @Part("phone_number") RequestBody phone_number,
                                      @Part("dob") RequestBody dob,
                                      @Part("email") RequestBody email,
                                      @Part("personal_email") RequestBody personalemail,
                                      @Part("office_lat") RequestBody officelat,
                                      @Part("office_lon") RequestBody officelon,
                                      @Part("home_lat") RequestBody homelat,
                                      @Part("home_lon") RequestBody homelon,
                                      @Part("home_address") RequestBody homeadd,
                                      @Part("office_address") RequestBody officeadd,
                                      @Part MultipartBody.Part images);
//    @Multipart
//    @POST("update-profile")
//    Call<ProfileResponse> updateimageprofileCall(@Header("Authorization") String token,
//                                                 @Part("phone_number") RequestBody phone_number,
//                                                 @Part MultipartBody.Part images);
//
//    @Multipart
//    @POST("update-profile")
//    Call<ProfileResponse> profileCallnew(@Header("Authorization") String token,
//                                      @Part("phone_number") RequestBody phone_number,
//                                      @Part("email") RequestBody email);
//
//
//    @POST("proximity")
//    Call<JSONArray> postOrder(@Header("Authorization") String token, @Body List<Model> cartList);
//
//    @POST("qr-user")
//    @FormUrlEncoded
//    Call<ProfileResponse> QRCodeCall(@Header("Authorization") String token,
//                                     @Field("qr_id") String qr_id,
//                                     @Field("latitute") String latitute,
//                                     @Field("longitute") String longitute);
//
    @POST("generatePersonalizedLink")
    @FormUrlEncoded
    Call<WorkEmailResponse> workEmailCall(@Header("Authorization") String token,
                                          @Field("email") String email,
                                          @Field("resend") String resend);
//
//    @POST("near-me")
//    @FormUrlEncoded
//    Call<hotResponse> hotCall(@Header("Authorization") String token,
//                                      @Field("latitude") String latitute,
//                                      @Field("longitude") String longitute);
//
//    @GET("home-location")
//    Call<hotResponse> homehotCall(@Header("Authorization") String token);
//
//    @GET("office-location")
//    Call<hotResponse> officehotCall(@Header("Authorization") String token);
//
//    @POST("other-location")
//    @FormUrlEncoded
//    Call<hotResponse> otherhotCall(@Header("Authorization") String token,
//                                           @Field("latitude") String latitute,
//                                           @Field("longitude") String longitute);
//
    @POST("extended-invit")
    @FormUrlEncoded
    Call<InvitResponse> ExtendedCall(@Header("Authorization") String token,
                                     @Field("invit_number") String number);

//    @POST("user-device")
//    @FormUrlEncoded
//    Call<DeviceResponse> DeviceCall(@Header("Authorization") String token,
//                                    @Field("device_name") String name);
//
//    @GET("infected-symptomatic")
//    Call<ProximityResposnse> proximityCall(@Header("Authorization") String token);
//
//    @GET("get-device")
//    Call<GetdeviceResposnse> Getdevice(@Header("Authorization") String token);
//
//    @POST("support")
//    @FormUrlEncoded
//    Call<DeviceResponse> SupportCall(@Header("Authorization") String token,
//                                     @Field("subject") String name,
//                                     @Field("message") String message);
//
//    @POST("proximityCounter")
//    @FormUrlEncoded
//    Call<DeviceResponse> proximityCounterCall(@Header("Authorization") String token,
//                                     @Field("counter") String counter);
//
//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/json"
//    })
//    @POST("locationcheck")
//    Call<JsonObjectResonse> postRawJSON(@Body JsonObject jsonObject);
//
//
//    @GET("get-contents")
//    Call<DiscoverModel> discoverCall(@Header("Authorization") String token);
//
    @POST("searchZone")
    @FormUrlEncoded
    Call<ZoneResponse> searchZoneCall(@Header("Authorization") String token,
                                      @Field("latitude") String latitude,
                                      @Field("longitude") String longitude,
                                      @Field("address") String address);
//
//
    @POST("createCommunity")
    @FormUrlEncoded
    Call<CommunityResponse> communityCall(@Header("Authorization") String token,
                                          @Field("community_name") String communityname,
                                          @Field("description") String description);

    @POST("inviteMember")
    @FormUrlEncoded
    Call<InviteMemberResponse> inviteMemberCall(@Header("Authorization") String token,
                                                @Field("community_id") String commid,
                                                @Field("invited_mobile_number") String invnum/*,
                                                @Field("invited_name") String invname*/);
//
    @GET("myCommunities")
    Call<MyCommunitiesResponse> myCommunitiesCall(@Header("Authorization") String token);

    @POST("invitationResponse")
    @FormUrlEncoded
    Call<LeaveCommunityResponse> invitationResponseCall(@Header("Authorization") String token,
                                                        @Field("community_id") String communityid,
                                                        @Field("member_status") String status);
    @POST("manage_member_status")
    @FormUrlEncoded
    Call<JSONObject> manageMemberStatus(@Header("Authorization") String token,
                                        @Field("community_id") int communityid,
                                        @Field("member_status") int status,
                                        @Field("user_id") int userid);

//
    @POST("leaveCommunity")
    @FormUrlEncoded
    Call<LeaveCommunityResponse> leaveCommunityResponseCall(@Header("Authorization") String token,
                                                            @Field("community_id") String communityid,
                                                            @Field("member_status") String status);
//
    @POST("communityUserList")
    @FormUrlEncoded
    Call<CommunityUserListResponse> communityUserListResponseCall(@Header("Authorization") String token,
                                                                  @Field("community_id") String communityid);
//
//
//    @GET("pendingInvitations")
//    Call<PendingInvitationsResponse> pendingInvitationsCall(@Header("Authorization") String token);
//
//
    @POST("getKpiGraphDetail")
    @FormUrlEncoded
    Call<GraphResponse> GraphResponseCall(@Header("Authorization") String token,
                                          @Field("type") String type,
                                          @Field("kpi_id") String kpiid,
                                          @Field("user") String user,
                                          @Field("age_month") String age,
                                          @Field("gender") String gender,
                                          @Field("community") String community);
//
//    @GET("?token=61f1e81979bcba5fd3738f937e433a3a7e59f11f")
//    Call<AQIResponse> AQICall();
//
//    @GET("onecall")
//    Call<TempResponse> TempCall(@Query("lat") double lat,
//                                @Query("lon") double lon,
//                                @Query("appid") String appid);
//
    @POST("externalToken")
    @FormUrlEncoded
    Call<SourceResponse> sourceCall(@Field("source") String source);
//
    @POST("sync/getGoogleFitData.php")
    @FormUrlEncoded
    Call<FitResponse> GoogleFitCall(@Field("auth_token") String token,
                                    @Field("step_data") String datastep,
                                    @Field("distance_data") String datadis,
                                    @Field("height_data") String datahei,
                                    @Field("weight_data") String datawei);
//
    @POST("updateZohoId")
    @FormUrlEncoded
    Call<updateZohoIdResponse> gitstatusCall(@Header("Authorization") String token,
                                             @Field("google_healthkit_status") String source);
    @POST("post_user_permission")
    @FormUrlEncoded
    Call<updatrPedoIdResponse> pedostatusCall(@Header("Authorization") String token,
                                              @Field("permission_type") int source,
                                              @Field("status") int status);
//
//
//
//    @GET("forceFullyUpdate")
//    Call<ForcefullyResponse> forcefullyCall();
//
//
//    @POST("getItemContent")
//    @FormUrlEncoded
//    Call<OrderSelfResponse> OrderSelfCall(@Header("Authorization") String token,
//                                              @Field("order_id") String orderidd,
//                                              @Field("date") String datee);
//
//
//    @POST("updateContentStatus")
//    @FormUrlEncoded
//    Call<updateContentStatusResponse> updateContentCall(@Header("Authorization") String token,
//                                                        @Field("order_id") String orderidd,
//                                                        @Field("content_id") String conid ,
//                                                        @Field("status") String status);
//
//    @POST("getKeyValue")
//    @FormUrlEncoded
//    Call<GetKeyValueResponse> getKeyValuecall(@Header("Authorization") String token,
//                                              @Field("key") String source);

}

