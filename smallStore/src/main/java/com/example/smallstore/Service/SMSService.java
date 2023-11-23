package com.example.smallstore.Service;

import com.example.smallstore.Dto.ResponseDto;
import com.example.smallstore.Dto.User.SMS.SMSVerifyRequest;
import com.example.smallstore.Entity.SMSAuth;
import com.example.smallstore.Error.ErrorException;
import com.example.smallstore.Repository.SMSAuthRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.util.Random;

import static com.example.smallstore.Error.ErrorCode.NOT_ALLOW_WRITE_EXCEPTION;

@Service
@RequiredArgsConstructor
public class SMSService {
    private final SMSAuthRepository smsAuthRepository;

    @Value("${sms.key}")
    private String apiKey;
    @Value("${sms.secret}")
    private String apiSecretKey;
    @Value("${sms.fromNumber}")
    private String fromNumber;
    String randomCode = "";

    private DefaultMessageService messageService;

    @PostConstruct
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    // 인증 코드 생성
    public void createCode() {
        Random random = new Random();
        String rand = "";
        for (int i = 0; i < 6; i++) {
            rand += random.nextInt(10);
        }
        randomCode = rand;
        System.out.println(randomCode);
    }

    // sms 보내기
    public ResponseEntity sendMessage(String toNumber) {
        createCode(); // 랜덤 인증 코드 생성
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(fromNumber);
        message.setTo(toNumber);
        message.setText("[어디있샵] 인증번호 "+randomCode+" 를 입력하세요.");

        // 확인하고 싶을때만 주석 풀어서 하기
        //SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
        this.saveDB(toNumber, randomCode);
        return ResponseEntity.ok(ResponseDto.successRes(200, "문자 보내기 성공"));
    }

    // 인증 확인
    public ResponseEntity verifySMS(String toNumber, String randomCode) {
        SMSAuth emailAuth = smsAuthRepository.findByPhone(toNumber).orElseThrow();

        // 현재 시각 가져오기
        LocalDateTime currentDateTime = LocalDateTime.now();

        if(currentDateTime.isAfter(emailAuth.getCreatedDate().plusMinutes(10))){
            smsAuthRepository.deleteByPhone(toNumber);
            // 다시 문자 보냄.
            this.sendMessage(toNumber);
            return ResponseEntity.badRequest().body(ResponseDto.failRes(400, "인증 실패/인증 시간이 지남."));
        }
        if(!randomCode.equals(emailAuth.getRandomCode())){
            return ResponseEntity.badRequest().body(ResponseDto.failRes(401, "인증 실패/인증 코드 오류"));
        }
        smsAuthRepository.deleteByPhone(toNumber);
        return ResponseEntity.ok(ResponseDto.successRes(200, "인증 성공"));
    }

    // 데베에 저장
    public void saveDB(String toNumber, String randomCode) {
        SMSVerifyRequest smsVerifyRequest = new SMSVerifyRequest();
        smsVerifyRequest.setPhone(toNumber);
        smsVerifyRequest.setRandomCode(randomCode);
        smsAuthRepository.save(smsVerifyRequest.toEntity());
    }
}
