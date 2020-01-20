package com.sunny.service.person.rest;

import com.sunny.service.person.PersonApplication;
import com.sunny.service.person.rest.domain.PersonPayLoad;
import com.sunny.service.person.rest.util.PersonServiceAPITestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Base64Utils;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("FieldCanBeLocal")
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = PersonApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonServiceAPITest {

    @Autowired
    private MockMvc mockMvc;

    private static final String PERSON_SERVICE_RESOURCE_PATH = "/api/v1/persons";

    private static final String DEF_USER = "admin";

    private static final String DEF_PASSWORD = "password";

    private final static String AUTH = DEF_USER + ":" + DEF_PASSWORD;

    private final static String INVALID_AUTH = "invalid:" + DEF_PASSWORD;

    private final static String FIRST_NAME = "John";

    private final static String LAST_NAME = "Doe";

    private final static short AGE = 27;

    private final static String FAVORITE_COLOR = "blue";

    @Test
    public void testGetAllPersons() throws Exception {

        this.mockMvc.perform(get(PERSON_SERVICE_RESOURCE_PATH).
                header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes())))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testGetAllPersonsEmptyWithNoSecurity() throws Exception {

        this.mockMvc.perform(get(PERSON_SERVICE_RESOURCE_PATH))
                .andDo(print()).andExpect(status().is(401));
    }

    @Test
    public void testCreateUser() throws Exception {
        List<String> hobbies = getHobbies();
        MvcResult mvcResult = createUserDefault(hobbies);
        String content = mvcResult.getResponse().getContentAsString();
        PersonPayLoad personPayLoad = PersonServiceAPITestUtil.convertFrom(content,
                PersonPayLoad.class);
        Assert.assertTrue(personPayLoad.getPersonId() > 0);
    }

    private MvcResult createUserDefault(List<String> hobbies) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders
                    .post(PERSON_SERVICE_RESOURCE_PATH)
                    . header(HttpHeaders.AUTHORIZATION,
                            "Basic " + Base64Utils.
                                    encodeToString(AUTH.getBytes()))
                    .content(PersonServiceAPITestUtil.
                            getPersonAsJsonString(FIRST_NAME,LAST_NAME,AGE,
                                    FAVORITE_COLOR,hobbies))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").
                            value(FIRST_NAME))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists()).andReturn();
    }

    @Test
    public void testUpdateUserFirstName() throws Exception {
        List<String> hobbies = getHobbies();
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post(PERSON_SERVICE_RESOURCE_PATH)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,LAST_NAME,AGE,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").
                        value(FIRST_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        PersonPayLoad personPayLoad = PersonServiceAPITestUtil.convertFrom(content,
                PersonPayLoad.class);
        long personId = personPayLoad.getPersonId();
        String newFirstName = "Dr " + FIRST_NAME;
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(PERSON_SERVICE_RESOURCE_PATH + "/" + personId)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(newFirstName,LAST_NAME,AGE,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").
                        value(newFirstName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").
                        value(personId));
    }

    @Test
    public void testUpdateUserLastName() throws Exception {
        List<String> hobbies = getHobbies();
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post(PERSON_SERVICE_RESOURCE_PATH)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,LAST_NAME,AGE,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").
                        value(FIRST_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        PersonPayLoad personPayLoad = PersonServiceAPITestUtil.convertFrom(content,
                PersonPayLoad.class);
        long personId = personPayLoad.getPersonId();
        String newLastName = LAST_NAME + "Kr";
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(PERSON_SERVICE_RESOURCE_PATH + "/" + personId)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,newLastName,AGE,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").
                        value(newLastName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").
                        value(personId));
    }

    @Test
    public void testUpdateUserAge() throws Exception {
        List<String> hobbies = getHobbies();
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post(PERSON_SERVICE_RESOURCE_PATH)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,LAST_NAME,AGE,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").
                        value(""+AGE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        PersonPayLoad personPayLoad = PersonServiceAPITestUtil.convertFrom(content,
                PersonPayLoad.class);
        long personId = personPayLoad.getPersonId();
        short newAge = 45;
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(PERSON_SERVICE_RESOURCE_PATH + "/" + personId)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,LAST_NAME,newAge,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").
                        value(""+newAge))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").
                        value(personId));
    }


    @Test
    public void testUpdateUserHobbies() throws Exception {
        List<String> hobbies = getHobbies();
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post(PERSON_SERVICE_RESOURCE_PATH)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,LAST_NAME,AGE,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        PersonPayLoad personPayLoad = PersonServiceAPITestUtil.convertFrom(content,
                PersonPayLoad.class);
        long personId = personPayLoad.getPersonId();
        String newHobby = "reading";
        hobbies.add(newHobby);
        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .put(PERSON_SERVICE_RESOURCE_PATH + "/" + personId)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,LAST_NAME,AGE,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").
                        value(personId)).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        personPayLoad = PersonServiceAPITestUtil.convertFrom(content,
                PersonPayLoad.class);
        Assert.assertTrue(personPayLoad.getHobby().size() == 3);
        Assert.assertTrue(personPayLoad.getHobby().contains(newHobby));
    }

    private List<String> getHobbies() {
        List<String> hobbies = new ArrayList<>();
        hobbies.add("running");
        hobbies.add("cycling");
        return hobbies;
    }

    @Test
    public void testUpdateUserFavoriteColor() throws Exception {
        List<String> hobbies = getHobbies();
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post(PERSON_SERVICE_RESOURCE_PATH)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,LAST_NAME,AGE,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.favoriteColor").
                        value(FAVORITE_COLOR))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        PersonPayLoad personPayLoad = PersonServiceAPITestUtil.convertFrom(content,
                PersonPayLoad.class);
        long personId = personPayLoad.getPersonId();
        String favoriteColor = "prussian blue";
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(PERSON_SERVICE_RESOURCE_PATH + "/" + personId)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,LAST_NAME,AGE,
                                favoriteColor,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.favoriteColor").
                        value(favoriteColor))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").
                        value(personId));
    }

    @Test
    public void testGetUser() throws Exception {
        List<String> hobbies = getHobbies();
        MvcResult mvcResult = createUserDefault(hobbies);
        String content = mvcResult.getResponse().getContentAsString();
        PersonPayLoad personPayLoad = PersonServiceAPITestUtil.convertFrom(content,
                PersonPayLoad.class);
        long personId = personPayLoad.getPersonId();
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(PERSON_SERVICE_RESOURCE_PATH + "/" + personId)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,LAST_NAME,AGE,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").
                        value(FIRST_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").
                        value(LAST_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").
                        value(""+AGE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.favoriteColor").
                        value(FAVORITE_COLOR))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists());

    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<String> hobbies = getHobbies();
        createUserDefault(hobbies);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .get(PERSON_SERVICE_RESOURCE_PATH)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,LAST_NAME,AGE,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        List<PersonPayLoad>  personPayLoads = PersonServiceAPITestUtil.convertFrom(content,
                List.class);
        Assert.assertTrue(personPayLoads.size() == 1);

    }

    @Test
    public void testDeleteUser() throws Exception {
        List<String> hobbies = getHobbies();
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post(PERSON_SERVICE_RESOURCE_PATH)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,LAST_NAME,AGE,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.favoriteColor").
                        value(FAVORITE_COLOR))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personId").exists()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        PersonPayLoad personPayLoad = PersonServiceAPITestUtil.convertFrom(content,
                PersonPayLoad.class);
        long personId = personPayLoad.getPersonId();
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(PERSON_SERVICE_RESOURCE_PATH + "/" + personId)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(AUTH.getBytes())))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testIncorrectUser() throws Exception {
        List<String> hobbies = getHobbies();
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(PERSON_SERVICE_RESOURCE_PATH)
                . header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.
                                encodeToString(INVALID_AUTH.getBytes()))
                .content(PersonServiceAPITestUtil.
                        getPersonAsJsonString(FIRST_NAME,LAST_NAME,AGE,
                                FAVORITE_COLOR,hobbies))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

}
