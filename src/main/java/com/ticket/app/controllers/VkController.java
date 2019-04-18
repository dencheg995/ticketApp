package com.ticket.app.controllers;


import com.ticket.app.config.interfce.VkConfig;
import com.ticket.app.service.interfaces.ClientService;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vk")
public class VkController {

    private final VkConfig vkConfig;

    private final ClientService clientService;

    public VkController(VkConfig vkConfig, ClientService clientService) {
        this.vkConfig = vkConfig;
        this.clientService = clientService;
    }

    @PostMapping(value = "/get-token")
    public String hello() {
        String client_id = "client_id=" + vkConfig.getApplicationId();
        String url = "https://oauth.vk.com/authorize?client_id=".concat(
                client_id).concat("&").concat(
                "display=page&").concat(
                "redirect_uri=http://localhost:8080/vk/token&").concat(
                "scope=offline,ads,notifications,groups,wall,questions,offers,pages,notes,docs,video,audio,photos,friends,notify,account&").concat(
                "response_type=code&v=5.92");
        return "redirect:" + url;
    }

//    @GetMapping("/token")
//    public Principal getToken(@RequestParam String code,
//                             Principal principal) throws ClientException, ApiException {
//        TransportClient transportClient = HttpTransportClient.getInstance();
//        VkApiClient vk = new VkApiClient(transportClient);
//        UserAuthResponse authResponse = vk.oauth()
//                .userAuthorizationCodeFlow(Integer.parseInt(vkConfig.getApplicationId()), vkConfig.getSecretCode(), "http://localhost:8080/vk/token", code)
//                .execute();
//        //​
//        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
//        //UsersGetQuery user = vk.users().get(actor).userIds(actor.getId().toString());
//        Role role = new Role();
//        role.setId(2l);
//        role.setRoleName("USER");
//        List<Role> roleList = new ArrayList<>();
//        roleList.add(role);
//        Client client = new Client();
//        client.setVkId(actor.getId().toString());
//        client.setVkToken(actor.getAccessToken());
//        client.setFirstName("Денис");
//        client.setLastName("Гребенюк");
//        client.setPassword("123");
//        client.setRole(roleList);
//        vk.account().getProfileInfo(actor);
//        clientService.addClient(client);
//        System.out.println(vk.account().getProfileInfo(actor).executeAsString());
//        return principal;
//    }

}
