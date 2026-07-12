package com.weeklyreport.backend.report.service;

import com.weeklyreport.backend.exceptions.ServiceUnavailableException;
import com.weeklyreport.backend.report.dto.ReportResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Service
public class RagService {
    private final RestClient restClient;
    private static final Logger log = LoggerFactory.getLogger(RagService.class);

    public RagService (RestClient.Builder builder){

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        JdkClientHttpRequestFactory requestFactory =
                new JdkClientHttpRequestFactory(httpClient);

        requestFactory.setReadTimeout(Duration.ofSeconds(30));

        this.restClient = builder
                .baseUrl("http://localhost:8000")
                .requestFactory(requestFactory)
                .build();
    }

    public void sendReport(ReportResponseDTO report){

        log.info("### Sending report: {}", report);

        try{
            String response = restClient.post()
                    .uri("/rag/add-report")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(report)
                    .retrieve()
                    .body(String.class);

            log.info("### FastAPI response: {}", response);
            log.info("### report sending success to fastapi server");

        } catch (Exception ex){
            log.error("### RAG service unavailable: {}", ex.getMessage());
            throw new ServiceUnavailableException("Error sending report to fastapi server : "+ex.getMessage());
        }
    }
}
