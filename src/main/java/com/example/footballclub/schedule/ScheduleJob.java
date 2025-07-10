package com.example.footballclub.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduleJob {
    @Autowired
    RestTemplate restTemplate;
    @Scheduled(cron = "0 */14 * ? * *")
    public void callCheckHealth() {
        String url = "https://football-club-be.onrender.com/api/v1/health";
        ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
        System.out.println(res);
    }
}
