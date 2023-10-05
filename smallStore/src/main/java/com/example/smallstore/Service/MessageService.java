package com.example.smallstore.Service;

import com.example.smallstore.Dto.User.Email.EmailSaveRequest;
import com.example.smallstore.Entity.EmailAuth;
import com.example.smallstore.Error.ErrorException;
import com.example.smallstore.Repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
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
public class MessageService {
    private final EmailAuthRepository emailAuthRepository;

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
    public ResponseEntity sendMessage(String toNumber, String type) {
        createCode(); // 랜덤 인증 코드 생성
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(fromNumber);
        message.setTo(toNumber);
        message.setText("[어디있샵] 인증번호 "+randomCode+" 를 입력하세요.");

        // 확인하고 싶을때만 주석 풀어서 하기
        //SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
        this.saveDB(toNumber, type);
        return ResponseEntity.ok("성공 " + toNumber +","+fromNumber+"," + randomCode);
    }

    // 인증 확인
    public ResponseEntity verifySMS(String toNumber, String user_randCode) {
        EmailAuth emailAuth = emailAuthRepository.findByNumber(toNumber).orElseThrow();

        // 현재 시각 가져오기
        LocalDateTime currentDateTime = LocalDateTime.now();

        if(currentDateTime.isAfter(emailAuth.getCreatedDate().plusMinutes(10))){
            emailAuthRepository.deleteByNumber(toNumber);
            this.sendMessage(toNumber, emailAuth.getType());
            throw new ErrorException("인증 가능 시간이 지났습니다. 다시 시도하세요.", NOT_ALLOW_WRITE_EXCEPTION);
        }
        if(!user_randCode.equals(emailAuth.getRandomCode())){
            throw new ErrorException("랜덤 코드가 틀렸습니다. 다시 한번 시도하세요..", NOT_ALLOW_WRITE_EXCEPTION);
        }
        emailAuthRepository.deleteByNumber(toNumber);
        return ResponseEntity.ok("인증 성공하였습니다.");
    }

    // 데베에 저장
    public void saveDB(String number, String type) {
        if(emailAuthRepository.existsByNumber(number)){
            throw new ErrorException("이미 인증 번호를 보냈습니다..", NOT_ALLOW_WRITE_EXCEPTION);
        }
        EmailSaveRequest emailSaveRequest = new EmailSaveRequest();
        emailSaveRequest.setNumber(number);
        emailSaveRequest.setRandomCode(randomCode);
        emailSaveRequest.setType(type);
        emailAuthRepository.save(emailSaveRequest.toEntity());
    }
}
